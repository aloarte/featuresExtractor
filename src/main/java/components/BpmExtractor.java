package components;

import libs.CustomUtils;
import model.BpmFeatures;
import model.ModuleParams;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BpmExtractor {


    public BpmFeatures extractBpm(INDArray shortTermFeatures, ModuleParams moduleParams) {
        double timeWindowSize = moduleParams.getShortTermWindowSize() / (float) moduleParams.getFrequencyRate();
        //(double) (Math.round(moduleParams.getShortTermWindowSize()/(float)moduleParams.getFrequencyRate()  * 0.01) / 0.01)
        timeWindowSize = 0.01;

        int[] selectedFeaturesIndex = new int[]{0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

        int maxBeatTime = (int) (2.0 / timeWindowSize);

        INDArray histAll = Nd4j.zeros(maxBeatTime);

        for (int ii = 0; ii < selectedFeaturesIndex.length; ii++) {
            int i = selectedFeaturesIndex[ii];
            double beatThreshold = 2.0 * calculateBeatThreshold(shortTermFeatures, i);
            if (beatThreshold <= 0) {
                beatThreshold = 0.0000000000000001;
            }

            List<Integer> rowPositionDiffs = new ArrayList<>();
            Map<Integer, Double> maximPeakDetected = peakDetection(shortTermFeatures.getRow(i), beatThreshold);


            int previousKey = 0;
            boolean skipFirstElement = true;

            for (int key : maximPeakDetected.keySet()) {

                if (skipFirstElement) {
                    skipFirstElement = false;
                } else {
                    rowPositionDiffs.add(key - previousKey);
                }
                previousKey = key;

            }


            org.nd4j.linalg.api.ops.impl.transforms.Histogram hOp = new org.nd4j.linalg.api.ops.impl.transforms.Histogram(Nd4j.create(rowPositionDiffs), maxBeatTime);
            Nd4j.getExecutioner().exec(hOp);

            INDArray rowHistogram = hOp.z().div(shortTermFeatures.columns());

            histAll = histAll.add(rowHistogram);


//            int timesElement = calculateRepeatTimes(rowPositionDiffs,2);
//            System.out.println("asdasd");


        }

        Nd4j.arange(0.5, maxBeatTime + 1.5);

        //Index of the highest value

        int maxIndex = histAll.argMax().getInt(0);
        double ratio = histAll.getDouble(maxIndex) / (double) histAll.sumNumber();
        //INDArray histEdges = Nd4j.arange(maxBeatTime+1.5).add(0.5);
        //pythonmagic(histEdges);
        INDArray histEdges = Nd4j.arange(1, maxBeatTime + 1);

        INDArray sixtyValues = Nd4j.zeros(maxBeatTime).add(60);

        INDArray bpms = sixtyValues.div(histEdges);

        return new BpmFeatures();
    }

    private void pythonmagic(INDArray histEdges) {
        INDArray retIndArray = Nd4j.zeros(histEdges.columns() - 1);

        NdIndexIterator iterator = new NdIndexIterator(retIndArray.columns());

        double prevValues = 0;
        boolean skip = true;

        while (iterator.hasNext()) {
            int[] nextIndex = iterator.next();
            if (skip) {
                skip = false;
            } else {
                retIndArray.putScalar(nextIndex[0] - 1, (prevValues + histEdges.getDouble(nextIndex)) / 2);
            }
            prevValues = histEdges.getDouble(nextIndex);
        }

    }

    private int calculateRepeatTimes(List<Integer> rowPositionDiffs, int elementToCount) {
        int cnt = 0;
        for (Integer value : rowPositionDiffs) {
            if (value == elementToCount) cnt++;
        }
        return cnt;
    }

    private TreeMap<Integer, Double> peakDetection(INDArray shortTermFeaturesRow, double beatThreshold) {
        List<Map<Integer, Double>> peaksDetected = CustomUtils.peak_detection(indArrayRowToDoubleList(shortTermFeaturesRow), beatThreshold);

        // Copy all data from hashMap into TreeMap
        return new TreeMap<>(peaksDetected.get(0));
    }


    private List<Double> indArrayRowToDoubleList(INDArray shortTermFeaturesRow) {
        double[] doubleVector = shortTermFeaturesRow.toDoubleVector();
        List<Double> returnDoubleList = new ArrayList<>();

        for (double rowElement : doubleVector) {
            returnDoubleList.add(rowElement);
        }

        return returnDoubleList;
    }

    private double calculateBeatThreshold(INDArray shortTermFeatures, int i) {
        INDArray firstSlice = shortTermFeatures.getRow(i).get(NDArrayIndex.interval(0, shortTermFeatures.columns() - 1));
        INDArray secondSlice = shortTermFeatures.getRow(i).get(NDArrayIndex.interval(1, shortTermFeatures.columns()));
        return org.nd4j.linalg.ops.transforms.Transforms.abs(firstSlice.sub(secondSlice)).mean(1).getDouble(0);


    }
}
