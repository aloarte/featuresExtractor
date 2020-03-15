package testutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Utility class to read different data from paths
 */
public class SamplesReaderUtils {

    /**
     * Read data from a file with the short terms or the mid terms
     *
     * @param filePath Path of the file
     * @return double[][] with the data read
     */
    public static double[][] readFeatureData(String filePath) {
        try {
            File fileRead = new File(filePath);
            Scanner myReader = new Scanner(fileRead);

            List<double[]> doubleSamplesData = new ArrayList<>();
            while (myReader.hasNextLine()) {
                doubleSamplesData.add(readLane(myReader.nextLine()));
            }
            myReader.close();
            return parseFinalArray(doubleSamplesData);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Read the array of doubles with the raw audio data from a txt file
     *
     * @param path Path to the file
     * @return Raw data in a double array
     */
    public static double[] readSamplesFromFile(String path) {
        try {
            File fileRead = new File(path);
            Scanner myReader = new Scanner(fileRead);
            List<Double> doubleSamplesData = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String lane = myReader.nextLine();
                doubleSamplesData.add(Double.parseDouble(lane));

            }
            myReader.close();

            double[] retSamples = new double[doubleSamplesData.size()];
            for (int i = 0; i < retSamples.length; i++) {
                retSamples[i] = doubleSamplesData.get(i);
            }

            return retSamples;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extract the array of [68x1] elements with the features
     *
     * @param filePath Path of the file to read
     * @return double[] with the data read from the file
     */
    public static double[] readExtractedFeaturesData(String filePath) {
        try {
            File fileRead = new File(filePath);
            Scanner myReader = new Scanner(fileRead);

            int index = 0;
            //double[] doubleSamplesData = new double[136];
            List<Double> doubleSamplesData = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String lane = myReader.nextLine();
                String[] splittedLane = lane.split(",");
                for (String laneElement : splittedLane) {
                    doubleSamplesData.add(Double.parseDouble(laneElement));
                    index++;
                }
            }
            myReader.close();

            double[] retSamples = new double[doubleSamplesData.size()];
            for (int i = 0; i < retSamples.length; i++) {
                retSamples[i] = doubleSamplesData.get(i);
            }
            return retSamples;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }

    }


    public static double[] readLane(String readLane) {
        String[] elements = readLane.split(",");
        double[] doubleElements = new double[elements.length];
        int index = 0;
        for (String data : elements) {
            doubleElements[index] = Double.parseDouble(data);
            index++;
        }

        return doubleElements;
    }

    public static double[][] parseFinalArray(List<double[]> data) {
        double[][] dataParsed = new double[data.size()][data.get(0).length];
        int index = 0;
        for (double[] featureLane : data) {
            dataParsed[index] = featureLane;
            index++;
        }
        return dataParsed;

    }


}
