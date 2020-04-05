package components;

import model.BpmFeatures;
import model.ModuleParams;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class BpmExtractor {


    public BpmFeatures extractBpm(INDArray controlShortTermFeatures, ModuleParams moduleParams) {
        double timeWindowSize = moduleParams.getShortTermWindowSize() / (float) moduleParams.getFrequencyRate();
        //(double) (Math.round(moduleParams.getShortTermWindowSize()/(float)moduleParams.getFrequencyRate()  * 0.01) / 0.01)
        timeWindowSize = 0.01;

        int[] selectedFeaturesIndex = new int[]{0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

        int maxBeatTime = (int) (2.0 / timeWindowSize);

        INDArray histAll = Nd4j.zeros(maxBeatTime);

        for (int ii = 0; ii < selectedFeaturesIndex.length; ii++) {
            int i = selectedFeaturesIndex[ii];
            double beatThreshold = 2.0 * calculateBeatThreshold(controlShortTermFeatures, i);
            if (beatThreshold <= 0) {
                beatThreshold = 0.0000000000000001;
            }

            peakDetection(controlShortTermFeatures.getRow(i), beatThreshold);

        }
        return new BpmFeatures();
    }

    private int[] peakDetection(INDArray row, double beatThreshold) {
        return new int[2];
    }

    private double calculateBeatThreshold(INDArray shortTermFeatures, int i) {
        INDArray firstSlice = shortTermFeatures.getRow(i).get(NDArrayIndex.interval(0, shortTermFeatures.columns() - 1));
        INDArray secondSlice = shortTermFeatures.getRow(i).get(NDArrayIndex.interval(1, shortTermFeatures.columns()));
        return org.nd4j.linalg.ops.transforms.Transforms.abs(firstSlice.sub(secondSlice)).mean(1).getDouble(0);


    }
}
