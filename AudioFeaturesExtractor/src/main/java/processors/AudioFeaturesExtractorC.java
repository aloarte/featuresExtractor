package processors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import static org.nd4j.linalg.ops.transforms.Transforms.*;
import java.lang.Math;
import java.util.Arrays;

import libs.CustomOperations;
import libs.FFT;
import org.jtransforms.fft.DoubleFFT_1D;


public class AudioFeaturesExtractorC {
    static double EPS = 0.00000001;

    public static INDArray stFeatureExtraction(double[] samples, int frequency_rate,
                                               int window, int step){
        INDArray norm_samples = Nd4j.create(samples,new int[]{samples.length});

        norm_samples = norm_samples.div(Math.pow(2,15));

        INDArray DC = Nd4j.mean(norm_samples, 0);
        System.out.println("Mean on dimension zero"+ DC);
        Number MAX = Transforms.abs(norm_samples.dup()).maxNumber();
        System.out.println("Max number(abs):"+ MAX);

        norm_samples = norm_samples.sub(DC.getDouble(0)).div((double)(MAX) + 0.0000000001);

        int N = norm_samples.length();
        System.out.println("Num of samples: "+N);
        int current_pos = 0;
        int count_frames = 0;
        int nFFT = window/2;

        // Compute the triangular filter banks used in the mfcc calculation
        Filterbank filterbank = new Filterbank(frequency_rate, nFFT);
        ChromaFeatures chromaFeatures = new ChromaFeatures(nFFT,frequency_rate);

        int numOfTimeSpectralFeatures = 8;
        int numOfHarmonicFeatures = 0;
        int nceps = 13;
        int numOfChromaFeatures = 13;
        int totalNumOfFeatures = numOfTimeSpectralFeatures + nceps + numOfHarmonicFeatures + numOfChromaFeatures;

        INDArray stFeatures = null;
        INDArray Xprev = null;
        while( current_pos + window -1 < N){ //For each short-term window
            count_frames += 1;
            INDArray x = norm_samples.get(
                    new INDArrayIndex[]{NDArrayIndex.interval(
                            current_pos,current_pos+window)}
                            ).dup();
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


            if( count_frames==1){
                Xprev = X.dup();
            }

            //INDArray curFV = Nd4j.zeros(totalNumOfFeatures,1);
            INDArray curFV = Nd4j.zeros(totalNumOfFeatures,1);
            curFV.putScalar(0,stZCR(x));
            curFV.putScalar(1,stEnergy(x));
            curFV.putScalar(2,stEnergyEntropy(x, 10));
            double[] spectralCandS = stSpectralCentroidAndSpread(X, frequency_rate);
            curFV.putScalar(3, spectralCandS[0]);
            curFV.putScalar(4, spectralCandS[1]);
            curFV.putScalar(5, stSpectralEntropy(X,10));
            curFV.putScalar(6, stSpectralFlux(X, Xprev));
            curFV.putScalar(7, stSpectralRollOff(X, 0.90, frequency_rate));

            double [] stMFCCs = filterbank.stMFCC(X, nceps);


            curFV.put(new INDArrayIndex[]{
                    NDArrayIndex.interval(numOfTimeSpectralFeatures,numOfTimeSpectralFeatures+nceps)},
                    Nd4j.create(stMFCCs));

            INDArray stChromaFeaturesArr = chromaFeatures.stChromaFeatures(X);

            curFV.put(new INDArrayIndex[]{
                    NDArrayIndex.interval(numOfTimeSpectralFeatures+nceps,
                            numOfTimeSpectralFeatures+nceps+numOfChromaFeatures-1)
            }, stChromaFeaturesArr);

            curFV.put(numOfTimeSpectralFeatures+nceps+numOfChromaFeatures-1,
                    Nd4j.std(stChromaFeaturesArr));

            Xprev = X.dup();

            if(count_frames==1){
                stFeatures = curFV;
            }else{
                stFeatures = CustomOperations.append(stFeatures, curFV);
            }

        }
        stFeatures = stFeatures.reshape(stFeatures.length()/totalNumOfFeatures,totalNumOfFeatures);

        return stFeatures.transpose();
    }

    public static INDArray mtFeatureExtraction(double[] samples, int frequency_rate,
                                               int mtWin, int mtStep,
                                               int stWin, int stStep){
        //Mid-Term feature extraction
        int mtWinRation = Math.round(mtWin/stStep);
        int mtStepRatio = Math.round(mtStep/stStep);

        INDArray stFeatures = stFeatureExtraction(samples, frequency_rate,
                stWin, stStep);

        int numOfFeatures = stFeatures.shape()[0];
        int mtWindows = (int)Math.ceil(stFeatures.shape()[1]/mtStepRatio);

        INDArray mtFeatures = Nd4j.zeros(numOfFeatures*2,mtWindows);

        for(int i=0; i<numOfFeatures; i++){
            int cur_p = 0;
            int j=0;
            int N = stFeatures.shape()[1];

            while( cur_p < N){
                int N1 = cur_p;
                int N2 = cur_p + mtWinRation;

                if(N2 > N) {
                    N2 = N;
                }

                INDArray cur_stFeatures = stFeatures.get(
                        new INDArrayIndex[]{
                                NDArrayIndex.point(i),
                                NDArrayIndex.interval(N1,N2)}
                );

                mtFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.point(i),
                        NDArrayIndex.point(j)
                },Nd4j.mean(cur_stFeatures));

                mtFeatures.put(new INDArrayIndex[]{
                        NDArrayIndex.point(i+numOfFeatures),
                        NDArrayIndex.point(j)
                },Nd4j.std(cur_stFeatures));

                cur_p += mtStepRatio;
                j++;
            }


        }

        return mtFeatures;
    }

    public static INDArray globalFeatureExtraction(double[] samples, int frequency_rate,
                                                   int mtWin, int mtStep,
                                                   int stWin, int stStep){
        INDArray mtFeatures = mtFeatureExtraction(samples, frequency_rate,
                mtWin, mtStep,
                stWin, stStep);

        return(mtFeatures.mean(1));

    }

    private static double[] stSpectralCentroidAndSpread(INDArray X, int frequency_rate) {
        //Computes spectral centroid of frame (given abs(FFT))
        double[] res = new double[2];
        INDArray ind = Nd4j.arange(1, X.length()).mul(frequency_rate/(2.0 * X.length()));

        INDArray Xt = X.dup();
        Xt = Xt.div(Xt.maxNumber());
        double NUM = (double)ind.mul(Xt.dup().reshape(Xt.shape()[1],Xt.shape()[0])).sumNumber();
        double DEN = (double)Xt.sumNumber()+EPS;

        double C = NUM/DEN;
        double S = (double)pow(ind.sub(C),2).mul(Xt.dup().reshape(Xt.shape()[1],Xt.shape()[0])).sumNumber()/DEN;

        res[0] = C / (frequency_rate/2);
        res[1] =  S / (frequency_rate/2);
        return res;
    }

    private static double stSpectralRollOff(INDArray x, double c, int frequency_rate) {
        //Computes spectral roll-off
        double mC = 0;
        double totalEnergy = (double)pow(x,2).sumNumber();
        int fftLength = x.length();
        double Thres = c*totalEnergy;
        //# Find the spectral rolloff as the frequency position where
        // the respective spectral energy is equal to c*totalEnergy
        INDArray CumSum = pow(x, 2).cumsum(0).add(EPS);
        for(int i=0; i<CumSum.length(); i++) {
            if (CumSum.getDouble(i) > Thres) {
                mC = CumSum.getDouble(i) / fftLength;
                i = CumSum.length();
            }
        }
        return mC;

    }

    private static double stSpectralFlux(INDArray X, INDArray Xprev) {
        //Compute spectral flux as the sum of square distances
        double sumX = (double)X.sumNumber()+X.length()*EPS;
        double sumPrevX = (double)Xprev.sumNumber()+Xprev.length()*EPS;
        double F = (double)pow(X.div(sumX).sub(Xprev.div(sumPrevX)), 2).sumNumber();
        return F;
    }

    private static double stEnergyEntropy(INDArray x, int numOfShortBlocks) {
        //Computes entropy of energy
        double Eol = (double)pow(x, 2).sumNumber(); //total frame energy
        int L = x.length();

        int subWinLength = (int)(Math.floor(L/numOfShortBlocks)); //subframe length
        System.out.println("Shape X: " + x.shapeInfoToString());
        if(L!=subWinLength*numOfShortBlocks){
            x = x.get(new INDArrayIndex[]{NDArrayIndex.interval(0, subWinLength*numOfShortBlocks)});
        }
        System.out.println("Shape X: " + x.shapeInfoToString());
        INDArray subWindows = x.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double)(pow(subWindows,2).sumNumber())/(Eol + EPS);
        double Entropy = -s*Math.log(s+EPS);
        return Entropy;
    }

    private static double stEnergy(INDArray x) {
        //Computes signal energy of frame
        return (double)(pow(x,2).sumNumber())/x.length();
    }

    private static double stZCR(INDArray x) {
        //Computes zero crossing rate of frame
        int count = x.length();
        int countz = 0;

        for(int i=0; i<x.length()-1; i++){
            if((x.getDouble(0) > 0 && x.getDouble(i+1) <=0)
                    ||
                    (x.getDouble(i) < 0 && x.getDouble(i+1) >=0)){
                countz++;
            }
        }

        countz = countz/2;

        return countz/(count-1.);
    }

    private static double stSpectralEntropy(INDArray X, int numOfShortBlocks) {
        //Computes the spectral entropy
        int L = X.length(); //samples
        double EoL = (double)pow(X,2).sumNumber(); //Total energy

        int subWinLength = (int)(Math.floor(L/numOfShortBlocks)); //subframe length
        System.out.println("Shape X: " + X.shapeInfoToString());
        if(L!=subWinLength*numOfShortBlocks){
            X = X.get(new INDArrayIndex[]{NDArrayIndex.interval(0, subWinLength*numOfShortBlocks)});
        }
        System.out.println("Shape X: " + X.shapeInfoToString());
        INDArray subWindows = X.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double)(pow(subWindows,2).sumNumber())/(EoL + EPS);
        double En = -s*Math.log(s+EPS);
        return En;
    }

}
