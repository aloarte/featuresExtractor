package components;

import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static org.nd4j.linalg.ops.transforms.Transforms.pow;

public class EnergyProcessor {

    private static EnergyProcessor instance;

    private double epsValue;

    public static EnergyProcessor getInstance(double epsValue) {
        if (instance == null) {
            instance = new EnergyProcessor();
            instance.epsValue = epsValue;

        }
        return instance;
    }

    double extractEnergyEntropy(INDArray currentAudioSlice, int numOfShortBlocks) {


        //Computes entropy of energy
        int sliceLength = currentAudioSlice.length();
        double totalEnergy = (double) pow(currentAudioSlice, 2).sumNumber(); //total frame energy

        int subWinLength = (int) (Math.floor(sliceLength / numOfShortBlocks)); //subframe length
        //System.out.println("Shape X: " + x.shapeInfoToString());
        if (sliceLength != subWinLength * numOfShortBlocks) {
            currentAudioSlice = currentAudioSlice.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        INDArray subWindows = (currentAudioSlice.reshape(numOfShortBlocks, subWinLength).transpose()).dup();
        INDArray s = (pow(subWindows, 2).sum(0)).div(totalEnergy + epsValue);

        NdIndexIterator iter = new NdIndexIterator(s.rows(), s.columns());

        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double value = s.getDouble(nextIndex);
            s.put(nextIndex[0], nextIndex[1], (Math.log(value + epsValue) / Math.log(2)) * value);
        }
        return -((Double) s.sumNumber());
    }

    double extractEnergy(INDArray currentAudioSlice) {
        //Computes signal energy of frame
        return (double) (pow(currentAudioSlice, 2).sumNumber()) / currentAudioSlice.length();
    }
}
