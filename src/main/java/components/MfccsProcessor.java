package components;

import org.jtransforms.dct.DoubleDCT_1D;
import org.nd4j.linalg.api.iter.NdIndexIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.util.Arrays;

import static constants.ModuleConstants.EPS_CONSTANT;
import static org.nd4j.linalg.ops.transforms.Transforms.pow;


public class MfccsProcessor {
    private INDArray fbank;
    private INDArray freqs;


    public MfccsProcessor(int frequency_rate, int nFFT) {

        extractMfccInitFilterBanks(frequency_rate, nFFT);

    }

    public double[] extractMFCC(INDArray X, int n_mfcc) {
        /**
         Computes the MFCCs of a frame, given the fft mag

         ARGUMENTS:
         X:        fft magnitude abs(FFT)
         fbank:    filter bank (see mfccInitFilterBanks)
         RETURN
         ceps:     MFCCs (13 element vector)

         Note:    MFCC calculation is, in general, taken from the
         scikits.talkbox library (MIT Licence),
         #    with a small number of modifications to make it more
         compact and suitable for the pyAudioAnalysis Lib
         **/
        //INDArray mspec = Transforms.log(X.mmul( fbank.transpose()).add(eps),10);
        INDArray mspec = X.mmul(fbank.transpose()).add(EPS_CONSTANT);
        DoubleDCT_1D mydtc = new DoubleDCT_1D(mspec.length());
        double[] ceps = mspec.toDoubleVector();
        mydtc.forward(ceps, false);
        ceps = Arrays.copyOfRange(ceps, 0, n_mfcc);

        return ceps;
    }

    private void extractMfccInitFilterBanks(int frequency_rate, int nFFT) {
        //filterbank params
        double lowfreq = 133.33;
        double linsc = 200 / 3.0;
        double logsc = 1.0711703;
        int numLinFiltTotal = 13;
        int numLogFilt = 27;
        int nlogfil;
        if (frequency_rate < 8000) {
            nlogfil = 5;
        }

        //Num total of filters
        int nFiltTotal = numLinFiltTotal + numLogFilt;

        //Compute frequency points of the triangle
        INDArray freqs = Nd4j.zeros(nFiltTotal + 2);
        freqs.put(new INDArrayIndex[]{NDArrayIndex.interval(0, numLinFiltTotal)},
                Nd4j.arange(numLinFiltTotal).mul(linsc).add(lowfreq));
        //freqs[numLinFiltTotal:] = freqs[numLinFiltTotal-1] * logsc ** numpy.arange(1, numLogFilt + 3)
        INDArray aux = Nd4j.arange(1, numLogFilt + 3);
        NdIndexIterator iter = new NdIndexIterator(aux.length());
        while (iter.hasNext()) {
            int[] nextIndex = iter.next();
            double nextVal = aux.getDouble(nextIndex);
            //do something with the value
            aux.putScalar(nextIndex, Math.pow(logsc, nextVal));
        }
        freqs.put(new INDArrayIndex[]{NDArrayIndex.interval(numLinFiltTotal, freqs.length())},
                aux.mul(
                        freqs.getDouble(numLinFiltTotal - 1))
        );

        INDArray heights;
        heights = freqs.get(
                NDArrayIndex.interval(2, freqs.length())).sub(
                freqs.get(NDArrayIndex.interval(0, freqs.length() - 2)));
        heights = pow(heights, -1).mul(2.0);

        // Compute filterbank coeff (in fft domain, in bins)

        INDArray fbank = Nd4j.zeros(nFiltTotal, nFFT);
        INDArray nfreqs = Nd4j.arange(nFFT).div(nFFT).mul(frequency_rate);

        for (int i = 0; i < nFiltTotal; i++) {
            double lowTrFreq = freqs.getDouble(i);
            double cenTrFreq = freqs.getDouble(i + 1);
            double highTrFreq = freqs.getDouble(i + 2);

            INDArray lid = Nd4j.arange((lowTrFreq * nFFT / frequency_rate) + 1,
                    (cenTrFreq * nFFT / frequency_rate) + 1);
            double lslope = heights.getDouble(i) / (cenTrFreq - lowTrFreq);

            INDArray rid = Nd4j.arange((cenTrFreq * nFFT / frequency_rate) + 1,
                    (highTrFreq * nFFT / frequency_rate) + 1);
            double rslope = heights.getDouble(i) / (highTrFreq - cenTrFreq);
            fbank.putScalar(new int[]{i, lid.getInt(0)},
                    (lslope * (nfreqs.getDouble(lid.getInt(0)) - lowTrFreq)));
            fbank.putScalar(new int[]{i, (int) rid.getDouble(0)},
                    (rslope * (highTrFreq - nfreqs.getDouble(rid.getInt(0)))));
        }

        this.fbank = fbank;
        this.freqs = freqs;

    }
}
