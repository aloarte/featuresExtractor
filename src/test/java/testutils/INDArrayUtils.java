package testutils;

import org.apache.commons.lang3.ArrayUtils;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class INDArrayUtils {

    /**
     * Write the indArrayData (current audio slice, fft audio slice) into a file
     *
     * @param indArrayData Data to write
     * @param filename     name of the file to write
     */
    public static void writeINDArrayFromFile(INDArray indArrayData, String filename) {

        double[] data = indArrayData.data().asDouble();

        Double[] dataDouble = ArrayUtils.toObject(data);


        ArrayList<Double> yourArray = new ArrayList<>(Arrays.asList(dataDouble));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(data.length + "\n");
            for (double line : yourArray) {
                bw.write(line + "");
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read files containing a slice of data (current audio slice, fft audio slice)
     *
     * @param fileName name of the file where is the data
     * @return INDArray with the data read from the file
     */
    public static INDArray readAudioSliceFromFile(String fileName) {

        Scanner scan;
        File file = new File(fileName);
        try {
            scan = new Scanner(file);

            int arraySize = Integer.parseInt(scan.next());
            double[][] readValue = new double[1][arraySize];

            for (int i = 0; i < arraySize; i++) {
                readValue[0][i] = Double.parseDouble(scan.next());

            }


            return Nd4j.create(readValue);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }

    }

    /**
     * Read files with the extracted features (ShortTermsFeatures and MidTermsFeatures)
     *
     * @param fileName name of the file where is the data
     * @return INDArray with the data read from the file
     */
    public static INDArray readShortTermFeaturesFromFile(String fileName) {

        Scanner scan;
        File file = new File(fileName);
        try {
            scan = new Scanner(file);
            double[][] readFeatures = new double[TOTAL_FEATURES][];
            int featureIndex = 0;
            while (scan.hasNext()) {
                if (featureIndex == TOTAL_FEATURES) break;
                String rowFeature = scan.next();
                String[] rowFeatureValues = rowFeature.split(",");
                double[] rowFeatures = new double[rowFeatureValues.length];

                for (int i = 0; i < rowFeatureValues.length; i++) {
                    rowFeatures[i] = Double.parseDouble(rowFeatureValues[i]);
                }
                readFeatures[featureIndex] = rowFeatures;
                featureIndex++;

            }

            return Nd4j.create(readFeatures);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }

    }


    public static INDArray readMidTermFeaturesFromFile(String fileName) {
        Scanner scan;
        File file = new File(fileName);
        try {
            double[][] readValue = new double[68][];
            scan = new Scanner(file);
            int featureIndex = 0;
            int laneIndex = 0;
            while (scan.hasNext()) {
                String readLane = scan.next();
                if ((laneIndex < TOTAL_FEATURES) || (laneIndex >= (TOTAL_FEATURES + TOTAL_FEATURES) && laneIndex < (TOTAL_FEATURES * 2 + TOTAL_FEATURES))) {


                    String[] fileValue = readLane.split(",");
                    double[] readMidTermRowValue = new double[fileValue.length];

                    for (int i = 0; i < fileValue.length; i++) {
                        readMidTermRowValue[i] = Double.parseDouble(fileValue[i]);
                    }

                    readValue[featureIndex] = readMidTermRowValue;
                    featureIndex++;
                }
                laneIndex++;
            }

            return Nd4j.create(readValue);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }

    }

    public static INDArray readMeanMidTermFeaturesFromFile(String fileName) {
        Scanner scan;
        File file = new File(fileName);
        try {
            scan = new Scanner(file);
            double[] readValues = new double[TOTAL_FEATURES * 2];
            int featureIndex = 0;
            int laneIndex = 0;
            while (scan.hasNext()) {
                String rowFeature = scan.next();
                if ((laneIndex < TOTAL_FEATURES) || (laneIndex >= (TOTAL_FEATURES + TOTAL_FEATURES) && laneIndex < (TOTAL_FEATURES * 2 + TOTAL_FEATURES))) {
                    String[] fileValue = rowFeature.split(",");
                    readValues[featureIndex] = Double.parseDouble(fileValue[0]);
                    featureIndex++;

                }
                laneIndex++;
            }
            return Nd4j.create(readValues);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    /***
     * Write files with the extracted features (ShortTermsFeatures and MidTermsFeatures)
     * @param extractShortTermFeatures  Data to write
     * @param filename  name of the file to write
     */
    public static void writeAudioFeaturesFromFile(INDArray extractShortTermFeatures, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            NdIndexIterator itColumns = new NdIndexIterator(extractShortTermFeatures.rows());
            while (itColumns.hasNext()) {
                int[] nextIndexCol = itColumns.next();
                INDArray featureRow = extractShortTermFeatures.getRow(nextIndexCol[0]);
                NdIndexIterator itRows = new NdIndexIterator(featureRow.columns());
                while (itRows.hasNext()) {
                    int[] nextIndexRow = itRows.next();
                    bw.write(featureRow.getDouble(nextIndexRow[0]) + (itRows.hasNext() ? "," : ""));
                }
                if (itColumns.hasNext()) bw.newLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterate through extractShortTermFeatures & controlShortTermFeatures verifying that their values are the same,
     * rounding its values on several situations
     *
     * @param extractShortTermFeatures Extracted values
     * @param controlShortTermFeatures Control values
     * @param roundPrecision           Array with the precisions used on the verifications
     */
    public static void assertShortTermFeaturesData(INDArray extractShortTermFeatures, INDArray controlShortTermFeatures, double[] roundPrecision) {
        assertNotNull(extractShortTermFeatures);
        assertNotNull(controlShortTermFeatures);
        assertThat(extractShortTermFeatures.columns(), is(controlShortTermFeatures.columns()));
        assertThat(extractShortTermFeatures.rows(), is(controlShortTermFeatures.rows()));
        assertThat(extractShortTermFeatures.rows(), is(TOTAL_FEATURES));

        int[] cntPrecision = new int[]{0, 0, 0, 0, 0};
        NdIndexIterator iterator = new NdIndexIterator(extractShortTermFeatures.rows(), extractShortTermFeatures.columns());
        while (iterator.hasNext()) {
            int[] nextIndex = iterator.next();
            //System.out.println("[" + nextIndex[0]+"]["+nextIndex[1]);
            if (TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[0]) != TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[0])) {
                if (TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[1]) != TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[1])) {
                    if (TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[2]) != TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[2])) {
                        if (TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[3]) != TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[3])) {
                            cntPrecision[4]++;
                            assertThat(TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[4]), is(TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[4])));
                        } else {
                            cntPrecision[3]++;
                            assertThat(TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[3]), is(TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[3])));
                        }
                    } else {
                        cntPrecision[2]++;
                        assertThat(TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[2]), is(TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[2])));
                    }
                } else {
                    cntPrecision[1]++;
                    assertThat(TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[1]), is(TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[1])));
                }
            } else {
                cntPrecision[0]++;
                assertThat(TestUtils.getRoundDouble(controlShortTermFeatures.getDouble(nextIndex), roundPrecision[0]), is(TestUtils.getRoundDouble(extractShortTermFeatures.getDouble(nextIndex), roundPrecision[0])));
            }
        }

        System.out.println("Round precision " + roundPrecision[0] + " : " + cntPrecision[0]);
        System.out.println("Round precision " + roundPrecision[1] + " : " + cntPrecision[1]);
        System.out.println("Round precision " + roundPrecision[2] + " : " + cntPrecision[2]);
        System.out.println("Round precision " + roundPrecision[3] + " : " + cntPrecision[3]);
        System.out.println("Round precision " + roundPrecision[4] + " : " + cntPrecision[4]);

        System.out.println("Total elements " + controlShortTermFeatures.rows() * controlShortTermFeatures.columns());


        assertThat(cntPrecision[0] + cntPrecision[1] + cntPrecision[2] + cntPrecision[3] + cntPrecision[4], is(extractShortTermFeatures.columns() * extractShortTermFeatures.rows()));
    }

    /**
     * Iterate through extractMidTermFeatures & controlMidTermFeatures verifying that their values are the same,
     * rounding its values on several situations
     *
     * @param extractMidTermFeatures Extracted values
     * @param controlMidTermFeatures Control values
     * @param roundPrecision         Array with the precisions used on the verifications
     */
    public static void assertMidTermFeaturesData(INDArray extractMidTermFeatures, INDArray controlMidTermFeatures, double[] roundPrecision) {
        assertNotNull(extractMidTermFeatures);
        assertNotNull(controlMidTermFeatures);

        assertThat(extractMidTermFeatures.columns(), is(controlMidTermFeatures.columns()));
        assertThat(extractMidTermFeatures.rows(), is(controlMidTermFeatures.rows()));
        assertThat(extractMidTermFeatures.rows(), is(TOTAL_FEATURES * 2));


        int[] cntPrecision = new int[]{0, 0, 0, 0, 0};
        NdIndexIterator iterator = new NdIndexIterator(extractMidTermFeatures.rows(), extractMidTermFeatures.columns());
        while (iterator.hasNext()) {
            int[] nextIndex = iterator.next();
            if (TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[0]) != TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[0])) {
                if (TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[1]) != TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[1])) {
                    if (TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[2]) != TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[2])) {
                        if (TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[3]) != TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[3])) {
                            cntPrecision[4]++;
                            assertThat(TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[4]), is(TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[4])));
                        } else {
                            cntPrecision[3]++;
                            assertThat(TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[3]), is(TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[3])));
                        }
                    } else {
                        cntPrecision[2]++;
                        assertThat(TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[2]), is(TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[2])));
                    }
                } else {
                    cntPrecision[1]++;
                    assertThat(TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[1]), is(TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[1])));
                }
            } else {
                cntPrecision[0]++;
                assertThat(TestUtils.getRoundDouble(controlMidTermFeatures.getDouble(nextIndex), roundPrecision[0]), is(TestUtils.getRoundDouble(extractMidTermFeatures.getDouble(nextIndex), roundPrecision[0])));
            }
        }

        System.out.println("Round precision " + roundPrecision[0] + " : " + cntPrecision[0]);
        System.out.println("Round precision " + roundPrecision[1] + " : " + cntPrecision[1]);
        System.out.println("Round precision " + roundPrecision[2] + " : " + cntPrecision[2]);
        System.out.println("Round precision " + roundPrecision[3] + " : " + cntPrecision[3]);
        System.out.println("Round precision " + roundPrecision[4] + " : " + cntPrecision[4]);

        assertThat(cntPrecision[0] + cntPrecision[1] + cntPrecision[2] + cntPrecision[3] + cntPrecision[4], is(extractMidTermFeatures.columns() * extractMidTermFeatures.rows()));
    }

    /**
     * Iterate through extractMeanMidTermFeatures & controlMeanMidTermFeatures verifying that their values are the same,
     * rounding its values on several situations
     *
     * @param extractMeanMidTermFeatures Extracted values
     * @param controlMeanMidTermFeatures Control values
     * @param roundPrecision             Array with the precisions used on the verifications
     */
    public static void assertFeatures(INDArray extractMeanMidTermFeatures, INDArray controlMeanMidTermFeatures, double[] roundPrecision) {
        assertNotNull(extractMeanMidTermFeatures);
        assertNotNull(controlMeanMidTermFeatures);

        int[] cntPrecision = new int[]{0, 0, 0, 0, 0};
        NdIndexIterator iterator = new NdIndexIterator(extractMeanMidTermFeatures.rows());
        while (iterator.hasNext()) {
            int[] nextIndex = iterator.next();
            if (TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[0]) != TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[0])) {
                if (TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[1]) != TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[1])) {
                    if (TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[2]) != TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[2])) {
                        if (TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[3]) != TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[3])) {
                            cntPrecision[4]++;
                            assertThat(TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[4]), is(TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[4])));
                        } else {
                            cntPrecision[3]++;
                            assertThat(TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[3]), is(TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[3])));
                        }
                    } else {
                        cntPrecision[2]++;
                        assertThat(TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[2]), is(TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[2])));
                    }
                } else {
                    cntPrecision[1]++;
                    assertThat(TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[1]), is(TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[1])));
                }
            } else {
                cntPrecision[0]++;
                assertThat(TestUtils.getRoundDouble(controlMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[0]), is(TestUtils.getRoundDouble(extractMeanMidTermFeatures.getDouble(nextIndex), roundPrecision[0])));
            }
        }

        System.out.println("Round precision " + roundPrecision[0] + " : " + cntPrecision[0]);
        System.out.println("Round precision " + roundPrecision[1] + " : " + cntPrecision[1]);
        System.out.println("Round precision " + roundPrecision[2] + " : " + cntPrecision[2]);
        System.out.println("Round precision " + roundPrecision[3] + " : " + cntPrecision[3]);
        System.out.println("Round precision " + roundPrecision[4] + " : " + cntPrecision[4]);

        assertThat(cntPrecision[0] + cntPrecision[1] + cntPrecision[2] + cntPrecision[3] + cntPrecision[4], is(extractMeanMidTermFeatures.columns() * extractMeanMidTermFeatures.rows()));
    }


}
