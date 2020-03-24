package components;

import org.nd4j.linalg.api.iter.NdIndexIterator;
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
        INDArray ind = Nd4j.arange(1, fftAudioSlice.length() + 1).mul(frequencyRate / (2.0 * fftAudioSlice.length()));

        INDArray fftCopy = fftAudioSlice.dup();
        fftCopy = fftCopy.div(fftCopy.maxNumber());
        double NUM = (double) ind.mul(fftCopy.dup().reshape(fftCopy.shape()[1], fftCopy.shape()[0])).sumNumber();
        double DEN = (double) fftCopy.sumNumber() + epsValue;

        double spectralCentroid = NUM / DEN;
        double spectralSpread = Math.sqrt((double) pow(ind.sub(spectralCentroid), 2).mul(fftCopy.dup().reshape(fftCopy.shape()[1], fftCopy.shape()[0])).sumNumber() / DEN);

        res[0] = spectralCentroid / (frequencyRate / 2);
        res[1] = spectralSpread / (frequencyRate / 2);
        return res;
    }

    double extractSpectralRollOff(INDArray fftAudioSlice) {
        double c = 0.90;
        //Computes spectral roll-off
        double totalEnergy = (double) pow(fftAudioSlice, 2).sumNumber();
        int fftLength = fftAudioSlice.length();
        double Thres = c * totalEnergy;
        //# Find the spectral rolloff as the frequency position where
        // the respective spectral energy is equal to c*totalEnergy
        INDArray CumSum = pow(fftAudioSlice, 2).cumsum(0).add(epsValue);
        for (int i = 0; i < CumSum.length(); i++) {
            if (CumSum.getDouble(i) > Thres) {
                return ((double) i) / fftLength;
            }
        }

        return 0;

    }

    double extractSpectralFlux(INDArray fftAudioSlice, INDArray fftPreviousAudioSlice) {

        //Compute spectral flux as the sum of square distances
        double sumX = (double) fftAudioSlice.sumNumber() + fftAudioSlice.length() * epsValue;
        double sumPrevX = (double) fftPreviousAudioSlice.sumNumber() + fftPreviousAudioSlice.length() * epsValue;
        return (double) pow(fftAudioSlice.div(sumX).sub(fftPreviousAudioSlice.div(sumPrevX)), 2).sumNumber();
    }

    double extractSpectralEntropy(INDArray fftAudioSlice) {

        int numOfShortBlocks = 10;

        //Computes entropy of energy
        int fftLenght = fftAudioSlice.length();
        double totalEnergy = (double) pow(fftAudioSlice, 2).sumNumber(); //total frame energy

        int subWinLength = (int) (Math.floor(fftLenght / numOfShortBlocks)); //subframe length
        //System.out.println("Shape X: " + x.shapeInfoToString());
        if (fftLenght != subWinLength * numOfShortBlocks) {
            fftAudioSlice = fftAudioSlice.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        INDArray subWindows = (fftAudioSlice.reshape(numOfShortBlocks, subWinLength).transpose()).dup();
        INDArray s = (pow(subWindows, 2).sum(0)).div(totalEnergy + epsValue);

        NdIndexIterator iter = new NdIndexIterator(s.rows(), s.columns());

        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double value = s.getDouble(nextIndex);
            s.put(nextIndex[0], nextIndex[1], (Math.log(value + epsValue) / Math.log(2)) * value);
        }
        return -((Double) s.sumNumber());

    }
}
