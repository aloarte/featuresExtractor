package testutils;

import libs.CustomOperations;
import org.apache.commons.lang3.ArrayUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class INDArrayUtils {

    private static void writeINDArrayFromFile(INDArray indArrayData, String filename) {

        double[] data = indArrayData.data().asDouble();

        Double[] dataDouble = ArrayUtils.toObject(data);


        ArrayList<Double> yourArray = new ArrayList<>(Arrays.asList(dataDouble));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(data.length + "\n");
            for (double line : yourArray) {
                bw.write(line + "");
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static INDArray readINDArrayFromFile(String fileName) {

        Scanner scan;
        File file = new File(fileName);
        try {
            scan = new Scanner(file);

            int arraySize = Integer.parseInt(scan.next());
            double[] readValue = new double[arraySize];

            for (int i = 0; i < arraySize; i++) {
                readValue[i] = Double.parseDouble(scan.next());

            }

            INDArray retIndArray = Nd4j.create(new int[]{1, 1});

            //retIndArray.put(0,Nd4j.create(readValue, new int[]{readValue.length}));

            return CustomOperations.append(retIndArray, Nd4j.create(readValue, new int[]{readValue.length}));

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }

    }
}
