package components;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static org.nd4j.linalg.ops.transforms.Transforms.pow;

public class EnergyProcessor {

    private double epsValue;

    public EnergyProcessor(double epsValue) {
        this.epsValue = epsValue;
    }


    double extractEnergyEntropy(INDArray currentAudioSlice, int numOfShortBlocks) {


        //Computes entropy of energy
        double Eol = (double) pow(currentAudioSlice, 2).sumNumber(); //total frame energy
        int L = currentAudioSlice.length();

        int subWinLength = (int) (Math.floor(L / numOfShortBlocks)); //subframe length
        //System.out.println("Shape X: " + x.shapeInfoToString());
        if (L != subWinLength * numOfShortBlocks) {
            currentAudioSlice = currentAudioSlice.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        //System.out.println("Shape X: " + x.shapeInfoToString());
        INDArray subWindows = currentAudioSlice.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double) (pow(subWindows, 2).sumNumber()) / (Eol + epsValue);
        double Entropy = -s * Math.log(s + epsValue);
        return Entropy;
    }

    double extractEnergy(INDArray currentAudioSlice) {
        //Computes signal energy of frame
        return (double) (pow(currentAudioSlice, 2).sumNumber()) / currentAudioSlice.length();
    }
}
