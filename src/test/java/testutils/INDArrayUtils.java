package testutils;

import org.apache.commons.lang3.ArrayUtils;
import org.nd4j.linalg.api.iter.NdIndexIterator;
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
            double[][] readValue = new double[1][arraySize];

            for (int i = 0; i < arraySize; i++) {
                readValue[0][i] = Double.parseDouble(scan.next());

            }

//
//            INDArray retINDarray = CustomOperations.append( Nd4j.create(new int[]{1}), Nd4j.create(readValue, new int[]{readValue.length}));
//
//            CustomOperations.delete(retINDarray,0);


            return Nd4j.create(readValue);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }

    }


    private static void writeINDArrayFilterBanks(INDArray indArrayData, String filename) {


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {

            NdIndexIterator iter = new NdIndexIterator(indArrayData.length());
            while (iter.hasNext()) {
                int[] nextIndex = iter.next();
                double nChromaValue = indArrayData.getDouble(nextIndex);

                bw.write(nextIndex[0] + "," + nChromaValue);
                bw.newLine();

            }


            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
