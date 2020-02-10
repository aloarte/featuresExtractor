package processors;

import libs.CustomOperations;
import org.jtransforms.fft.DoubleFFT_1D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.Arrays;

public class GeneralRawProcessor {

    private StCalculator stCalculator;

    public GeneralRawProcessor() {
        this.stCalculator = new StCalculator();
    }


    INDArray stFeatureExtraction(double[] samples, int frequency_rate,
                                 int window, int step) {
        INDArray norm_samples = Nd4j.create(samples, new int[]{samples.length});

        norm_samples = norm_samples.div(Math.pow(2, 15));

        INDArray DC = Nd4j.mean(norm_samples, 0);
        System.out.println("Mean on dimension zero" + DC);
        Number MAX = Transforms.abs(norm_samples.dup()).maxNumber();
        System.out.println("Max number(abs):" + MAX);

        norm_samples = norm_samples.sub(DC.getDouble(0)).div((double) (MAX) + 0.0000000001);

        int N = norm_samples.length();
        System.out.println("Num of samples: " + N);
        int current_pos = 0;
        int count_frames = 0;
        int nFFT = window / 2;

        // Compute the triangular filter banks used in the mfcc calculation
        FilterbankProcessor filterbankProcessor = new FilterbankProcessor(frequency_rate, nFFT);
        ChromaProcessor chromaProcessor = new ChromaProcessor(nFFT, frequency_rate);

        int numOfTimeSpectralFeatures = 8;
        int numOfHarmonicFeatures = 0;
        int nceps = 13;
        int numOfChromaFeatures = 13;
        int totalNumOfFeatures = numOfTimeSpectralFeatures + nceps + numOfHarmonicFeatures + numOfChromaFeatures;

        INDArray stFeatures = null;
        INDArray Xprev = null;
        while (current_pos + window - 1 < N) { //For each short-term window
            count_frames += 1;
            INDArray x = norm_samples.get(
                    NDArrayIndex.interval(
                            current_pos, current_pos + window)).dup();
            current_pos += step;

            /**
             float[] arr_x = x.toFloatVector();

             FFT fft = new FFT(arr_x.length);
             double[] darr_x = new double[arr_x.length];
             for(int i=0; i<arr_x.length;i++){
             darr_x[i] = arr_x[i];
             }
             fft.fft(darr_x,new double[arr_x.length]);
             **/
            double[] arr_X = x.toDoubleVector();
            DoubleFFT_1D fft = new DoubleFFT_1D(arr_X.length);
            fft.realForward(arr_X);
            arr_X = Arrays.copyOfRange(arr_X, 0, nFFT);

            /**
             double[] arr_X = new double[nFFT];
             for(int i=0; i<arr_X.length; i++){
             arr_X[i] = Math.abs(darr_x[i])/arr_X.length;
             }
             **/
            INDArray X = Nd4j.create(arr_X).div(arr_X.length);


            if (count_frames == 1) {
                Xprev = X.dup();
            }

            //INDArray curFV = Nd4j.zeros(totalNumOfFeatures,1);
            INDArray curFV = Nd4j.zeros(totalNumOfFeatures, 1);
            curFV.putScalar(0, stCalculator.stZCR(x));
            curFV.putScalar(1, stCalculator.stEnergy(x));
            curFV.putScalar(2, stCalculator.stEnergyEntropy(x, 10));
            double[] spectralCandS = stCalculator.stSpectralCentroidAndSpread(X, frequency_rate);
            curFV.putScalar(3, spectralCandS[0]);
            curFV.putScalar(4, spectralCandS[1]);
            curFV.putScalar(5, stCalculator.stSpectralEntropy(X, 10));
            curFV.putScalar(6, stCalculator.stSpectralFlux(X, Xprev));
            curFV.putScalar(7, stCalculator.stSpectralRollOff(X, 0.90, frequency_rate));

            double[] stMFCCs = filterbankProcessor.stMFCC(X, nceps);


            curFV.put(new INDArrayIndex[]{
                            NDArrayIndex.interval(numOfTimeSpectralFeatures, numOfTimeSpectralFeatures + nceps)},
                    Nd4j.create(stMFCCs));

            INDArray stChromaFeaturesArr = chromaProcessor.stChromaFeatures(X);

            curFV.put(new INDArrayIndex[]{
                    NDArrayIndex.interval(numOfTimeSpectralFeatures + nceps,
                            numOfTimeSpectralFeatures + nceps + numOfChromaFeatures - 1)
            }, stChromaFeaturesArr);

            curFV.put(numOfTimeSpectralFeatures + nceps + numOfChromaFeatures - 1,
                    Nd4j.std(stChromaFeaturesArr));

            Xprev = X.dup();

            if (count_frames == 1) {
                stFeatures = curFV;
            } else {
                stFeatures = CustomOperations.append(stFeatures, curFV);
            }

        }
        stFeatures = stFeatures.reshape(stFeatures.length() / totalNumOfFeatures, totalNumOfFeatures);

        return stFeatures.transpose();
    }

    INDArray mtFeatureExtraction(double[] samples, int frequency_rate,
                                 int mtWin, int mtStep,
                                 int stWin, int stStep) {
        //Mid-Term feature extraction
        int mtWinRation = Math.round(mtWin / stStep);
        int mtStepRatio = Math.round(mtStep / stStep);

        INDArray stFeatures = stFeatureExtraction(samples, frequency_rate,
                stWin, stStep);

        int numOfFeatures = stFeatures.shape()[0];
        int mtWindows = (int) Math.ceil(stFeatures.shape()[1] / mtStepRatio);

        INDArray mtFeatures = Nd4j.zeros(numOfFeatures * 2, mtWindows);

        for (int i = 0; i < numOfFeatures; i++) {
            int cur_p = 0;
            int j = 0;
            int N = stFeatures.shape()[1];

            while (cur_p < N) {
                int N1 = cur_p;
                int N2 = cur_p + mtWinRation;

                if (N2 > N) {
                    N2 = N;
                }

                INDArray cur_stFeatures = stFeatures.get(
                        NDArrayIndex.point(i),
                        NDArrayIndex.interval(N1, N2));

                mtFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.point(i),
                        NDArrayIndex.point(j)
                }, Nd4j.mean(cur_stFeatures));

                mtFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.point(i + numOfFeatures),
                        NDArrayIndex.point(j)
                }, Nd4j.std(cur_stFeatures));

                cur_p += mtStepRatio;
                j++;
            }


        }

        return mtFeatures;
    }

    public INDArray globalFeatureExtraction(double[] samples, int frequency_rate,
                                            int mtWin, int mtStep,
                                            int stWin, int stStep) {
        INDArray mtFeatures = mtFeatureExtraction(samples, frequency_rate,
                mtWin, mtStep,
                stWin, stStep);

        return (mtFeatures.mean(1));

    }
}
