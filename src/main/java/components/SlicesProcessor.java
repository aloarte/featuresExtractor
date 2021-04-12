package components;

import org.jtransforms.fft.DoubleFFT_1D;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;
import utils.ComplexRealMatrixParser;

import java.util.Arrays;

public class SlicesProcessor {

    /**
     * Get a INDArray representing the FastFourierTransform of the passed currentAudioSlice
     *
     * @param currentAudioSlice INDArray with the audio slice to calculate its FFT
     * @param fftWindowSize     Size of the FFT window
     * @return INDArray with the FFT already calculated
     */
    INDArray calculateFftFromAudioSlice(INDArray currentAudioSlice, int fftWindowSize) {

        //Parse data to double array
        double[] audioSliceValues = currentAudioSlice.toDoubleVector();

        //Fake the imaginary component of each real value before the fft calculation
        double[] audioSliceComplexValues = ComplexRealMatrixParser.parseFromRealToComplex(audioSliceValues);

        //Get the FastFourierTransform of the audioSlice
        DoubleFFT_1D fastFourierTransform = new DoubleFFT_1D(audioSliceComplexValues.length);
        fastFourierTransform.realForward(audioSliceComplexValues);

        //The second value is zero
        audioSliceComplexValues[1] = 0;

        double[] audioSliceRealValues = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(audioSliceComplexValues);

        //Replace the values
        audioSliceValues = Arrays.copyOfRange(audioSliceRealValues, 0, fftWindowSize);

        //Build a INDArray from the double[] with the FFT of slice
        return Nd4j.create(audioSliceValues).div(audioSliceValues.length);
    }

    /**
     * Get a INDArray normalized from the raw audioSamples array
     *
     * @param audioSamples Raw double array with the data read from the input audio
     * @return normalized INDArray containing the audioSamples
     */
    INDArray calculateNormalizedSamples(double[] audioSamples) {
        INDArray normalizedSamples = Nd4j.create(audioSamples, new int[]{audioSamples.length});

        normalizedSamples = normalizedSamples.div(Math.pow(2, 15));

        double mean = (Nd4j.mean(normalizedSamples, 0)).getDouble(0);
        double maxValue = (Transforms.abs(normalizedSamples.dup()).maxNumber()).doubleValue();

        return normalizedSamples.sub(mean).div((maxValue) + 0.0000000001);
    }
}
