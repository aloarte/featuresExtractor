package components;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static org.nd4j.linalg.ops.transforms.Transforms.pow;

public class SpectralProcessor {

    private static SpectralProcessor instance;
    private double epsValue;

    public static SpectralProcessor getInstance(double epsValue) {
        if (instance == null) {
            instance = new SpectralProcessor();
            instance.epsValue = epsValue;

        }
        return instance;
    }


    double[] extractSpectralCentroidAndSpread(INDArray fftAudioSlice, int frequencyRate) {
        //Computes spectral centroid of frame (given abs(FFT))
        double[] res = new double[2];
        INDArray ind = Nd4j.arange(1, fftAudioSlice.length()).mul(frequencyRate / (2.0 * fftAudioSlice.length()));

        INDArray Xt = fftAudioSlice.dup();
        Xt = Xt.div(Xt.maxNumber());
        double NUM = (double) ind.mul(Xt.dup().reshape(Xt.shape()[1], Xt.shape()[0])).sumNumber();
        double DEN = (double) Xt.sumNumber() + epsValue;

        double C = NUM / DEN;
        double S = (double) pow(ind.sub(C), 2).mul(Xt.dup().reshape(Xt.shape()[1], Xt.shape()[0])).sumNumber() / DEN;

        res[0] = C / (frequencyRate / 2);
        res[1] = S / (frequencyRate / 2);
        return res;
    }

    double extractSpectralRollOff(INDArray fftAudioSlice, double c, int frequency_rate) {
        //Computes spectral roll-off
        double mC = 0;
        double totalEnergy = (double) pow(fftAudioSlice, 2).sumNumber();
        int fftLength = fftAudioSlice.length();
        double Thres = c * totalEnergy;
        //# Find the spectral rolloff as the frequency position where
        // the respective spectral energy is equal to c*totalEnergy
        INDArray CumSum = pow(fftAudioSlice, 2).cumsum(0).add(epsValue);
        for (int i = 0; i < CumSum.length(); i++) {
            if (CumSum.getDouble(i) > Thres) {
                mC = CumSum.getDouble(i) / fftLength;
                i = CumSum.length();
            }
        }
        return mC;

    }

    double extractSpectralFlux(INDArray fftAudioSlice, INDArray fftPreviousAudioSlice) {

        //Compute spectral flux as the sum of square distances
        double sumX = (double) fftAudioSlice.sumNumber() + fftAudioSlice.length() * epsValue;
        double sumPrevX = (double) fftPreviousAudioSlice.sumNumber() + fftPreviousAudioSlice.length() * epsValue;
        double F = (double) pow(fftAudioSlice.div(sumX).sub(fftPreviousAudioSlice.div(sumPrevX)), 2).sumNumber();
        return F;
    }

    double extractSpectralEntropy(INDArray fftAudioSlice, int numOfShortBlocks) {
        //Computes the spectral entropy
        int L = fftAudioSlice.length(); //samples
        double EoL = (double) pow(fftAudioSlice, 2).sumNumber(); //Total energy

        int subWinLength = (int) (Math.floor(L / numOfShortBlocks)); //subframe length
        if (L != subWinLength * numOfShortBlocks) {
            fftAudioSlice = fftAudioSlice.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        INDArray subWindows = fftAudioSlice.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double) (pow(subWindows, 2).sumNumber()) / (EoL + epsValue);
        double En = -s * Math.log(s + epsValue);
        return En;
    }
}
