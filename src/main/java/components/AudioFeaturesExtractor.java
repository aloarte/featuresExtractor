package components;

import libs.CustomOperations;
import model.ModuleParams;
import model.exceptions.AudioExtractionException;
import org.jtransforms.fft.DoubleFFT_1D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.Arrays;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;


public class AudioFeaturesExtractor {

    private FeaturesProcessor featuresProcessor;

    private StatisticsExtractor statisticsExtractor;


    public AudioFeaturesExtractor() {
        this.featuresProcessor = new FeaturesProcessor();
        this.statisticsExtractor = new StatisticsExtractor();
    }


    /**
     * Iterate the samples audio source into several windows taken in steps to extract the 32 features in each window.
     *
     * @param samples
     * @param frequency_rate
     * @param window
     * @param step
     * @return
     */
    INDArray extractAudioFeatures(double[] samples, int frequency_rate,
                                  int window, int step) throws AudioExtractionException {


        INDArray norm_samples = Nd4j.create(samples, new int[]{samples.length});

        norm_samples = norm_samples.div(Math.pow(2, 15));

        INDArray DC = Nd4j.mean(norm_samples, 0);
        Number MAX = Transforms.abs(norm_samples.dup()).maxNumber();

        norm_samples = norm_samples.sub(DC.getDouble(0)).div((double) (MAX) + 0.0000000001);

        int N = norm_samples.length();
        int current_pos = 0;
        int count_frames = 0;
        int nFFT = window / 2;

        INDArray matrixStFeatures = null;
        INDArray fftPreviousAudioSlice = null;
        while (current_pos + window - 1 < N) { //For each short-term window
            count_frames += 1;
            //Extract the audio slice from the whole audio source with Window as a size
            INDArray currentAudioSlice = norm_samples.get(
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
            double[] arr_X = currentAudioSlice.toDoubleVector();
            //Get the FastFourierTransform of the audioSlice
            DoubleFFT_1D fft = new DoubleFFT_1D(arr_X.length);
            fft.realForward(arr_X);


            arr_X = Arrays.copyOfRange(arr_X, 0, nFFT);

            /**
             double[] arr_X = new double[nFFT];
             for(int i=0; i<arr_X.length; i++){
             arr_X[i] = Math.abs(darr_x[i])/arr_X.length;
             }
             **/
            //Build a INDArray from the double[] with the FFT of slice
            INDArray fftAudioSlice = Nd4j.create(arr_X).div(arr_X.length);

            //Save the previous audio slice
            if (count_frames == 1) {
                fftPreviousAudioSlice = fftAudioSlice.dup();
            }

            //Extract the features from the different slices of audio from the same window
            INDArray curFV = featuresProcessor.extractFeaturesFromSlice(currentAudioSlice, fftAudioSlice, fftPreviousAudioSlice, frequency_rate, nFFT);


            fftPreviousAudioSlice = fftAudioSlice.dup();

            if (count_frames == 1) {
                matrixStFeatures = curFV;
            } else {
                matrixStFeatures = CustomOperations.append(matrixStFeatures, curFV);
            }

        }

        matrixStFeatures = matrixStFeatures.reshape(matrixStFeatures.length() / TOTAL_FEATURES, TOTAL_FEATURES);

        return matrixStFeatures.transpose();
    }


    public INDArray globalFeatureExtraction(double[] samples, int frequency_rate, int mtWin, int mtStep, int stWin, int stStep, final ModuleParams moduleParams) throws AudioExtractionException {

        //Extract the matrix with the [32 features] x [N window samples]
        INDArray matrixExtractedFeatures = extractAudioFeatures(samples, frequency_rate, stWin, stStep);

        //Apply statistic operations to each N sample for each of the 32 features. Extract a matrix of [32 features] x [N statistic operations]
        INDArray mtFeatures = statisticsExtractor.obtainAudioFeaturesStatistics(matrixExtractedFeatures, mtWin, mtStep, stStep, moduleParams);

        return (mtFeatures.mean(1));

    }





}
