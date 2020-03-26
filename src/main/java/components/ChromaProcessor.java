package components;

import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.accum.MatchCondition;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.BooleanIndexing;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.indexing.conditions.Conditions;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.HashSet;


public class ChromaProcessor {

    public static ChromaProcessor instance;
    private INDArray nChroma;
    private INDArray nFreqsPerChroma;

    private String[] chromaNames;

    public static ChromaProcessor getInstance(int frequency_rate, int nFFT) {
        if (instance == null) {
            instance = new ChromaProcessor();
            instance.extractChomaFeaturesIniti(frequency_rate, nFFT);
            instance.chromaNames = new String[]{"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
        }
        return instance;
    }


    public double[] extractChromaFeatures(INDArray fftAudioSlice) {
        INDArray spec = Transforms.pow(fftAudioSlice, 2);
        INDArray C = Nd4j.zeros(nChroma.length());

        if (nChroma.maxNumber().intValue() < nFreqsPerChroma.shape()[0]) {

            NdIndexIterator iter = new NdIndexIterator(nChroma.length());
            while (iter.hasNext()) {
                int[] nextIndex = iter.next();
                int nChromaValue = nChroma.getInt(nextIndex);
                double specValue = spec.getDouble(nextIndex) / nFreqsPerChroma.getInt(nChroma.getInt(nChromaValue));
                C.putScalar(nChromaValue, specValue);

            }
        } else {
            //TODO: for the execution with NFFT= 110 $ freqRate=22050 this else wont be reached. In other cases, implement this else with the solution
        }

        INDArray finalC = Nd4j.zeros(12);
        int newD = (int) Math.ceil(C.length() / 12.0) * 12;
        INDArray C2 = Nd4j.zeros(newD);
        C2.put(new INDArrayIndex[]{NDArrayIndex.interval(0, C.length())}, C);


        C2 = C2.reshape(C2.length() / 12, 12);

        finalC = Nd4j.sum(C2, 0).transpose();
        finalC = finalC.div(Nd4j.sum(spec));


        /**
         if(nChroma.maxNumber().intValue()<nChroma.shape()[0]){
         C = spec.div(nFreqsPerChroma);
         }else {
         INDArray I = CustomOperations.booleanOp(nChroma, Conditions.greaterThan(nChroma.length()));
         C.put(new INDArrayIndex[]{
         NDArrayIndex.interval(0,I-1)}, spec);
         C = C.div(nFreqsPerChroma);
         }*/

        return finalC.toDoubleVector();

    }

    private void extractChomaFeaturesIniti(int frequency_rate, int nFFT) {
        INDArray freqs = Nd4j.arange(nFFT);
        freqs = freqs.add(1).mul(frequency_rate).div(2 * nFFT);

        double Cp = 27.50;
        INDArray nChroma = freqs.dup();
        NdIndexIterator iter = new NdIndexIterator(nChroma.length());
        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double oldNextVal = nChroma.getDouble(nextIndex);
            double newNextVal = Math.round(12 * (Math.log(oldNextVal / Cp) / Math.log(2)));
            nChroma.putScalar(nextIndex, newNextVal);
        }

        INDArray nFreqsPerChroma = nChroma.dup();
        HashSet<Double> unique_set = new HashSet<>();
        iter = new NdIndexIterator(nChroma.length());
        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double nextVal = nChroma.getDouble(nextIndex);
            unique_set.add(nextVal);
        }

        INDArray uChoma = Nd4j.zeros(unique_set.size());
        int i = 0;
        for (Double d : unique_set) {
            uChoma.putScalar(i, d);
            MatchCondition op = new MatchCondition(nChroma, Conditions.equals(d));
            int countCond = Nd4j.getExecutioner().exec(op, Integer.MAX_VALUE).getInt(0);  //MAX_VALUE = "along all dimensions" or equivalently "for entire array"
            BooleanIndexing.replaceWhere(nFreqsPerChroma, countCond, Conditions.equals(d));
            i++;
        }

        this.nChroma = nChroma.transpose();
        this.nFreqsPerChroma = nFreqsPerChroma.transpose();


    }

}
