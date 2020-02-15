package components;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static org.nd4j.linalg.ops.transforms.Transforms.pow;

public class EnergyProcessor {

    private double epsValue;

    public EnergyProcessor(double epsValue) {
        this.epsValue = epsValue;
    }

    double extractEnergyEntropy(INDArray x, int numOfShortBlocks) {
        //Computes entropy of energy
        double Eol = (double) pow(x, 2).sumNumber(); //total frame energy
        int L = x.length();

        int subWinLength = (int) (Math.floor(L / numOfShortBlocks)); //subframe length
        //System.out.println("Shape X: " + x.shapeInfoToString());
        if (L != subWinLength * numOfShortBlocks) {
            x = x.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        //System.out.println("Shape X: " + x.shapeInfoToString());
        INDArray subWindows = x.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double) (pow(subWindows, 2).sumNumber()) / (Eol + epsValue);
        double Entropy = -s * Math.log(s + epsValue);
        return Entropy;
    }

    double extractEnergy(INDArray x) {
        //Computes signal energy of frame
        return (double) (pow(x, 2).sumNumber()) / x.length();
    }
}
