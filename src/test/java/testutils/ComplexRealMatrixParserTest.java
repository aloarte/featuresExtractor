package testutils;

import org.jtransforms.dct.DoubleDCT_1D;
import org.jtransforms.fft.DoubleFFT_1D;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import utils.ComplexRealMatrixParser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE_KNIFE_22220;
import static testutils.TestingConstants.TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE_KNIFE;

public class ComplexRealMatrixParserTest {

    private INDArray currentSliceData;
    private INDArray currentFFTSliceData;

    @Before
    public void startUp() {
        currentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE_KNIFE_22220);
        currentFFTSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE_KNIFE);

    }

    @Test
    public void parseFromReralToComplexTest() {
        double[] realMatrix = new double[]{5, -3, 45};
        double[] complexMatrix = new double[]{5, 0, -3, 0, 45, 0};

        double[] calculatedComplexMatrix = ComplexRealMatrixParser.parseFromRealToComplex(realMatrix);
        for (int i = 0; i < calculatedComplexMatrix.length; i++) {
            assertThat(calculatedComplexMatrix[i], is(complexMatrix[i]));
        }

        double[] calculatedRealMatrix = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(calculatedComplexMatrix);
        for (int i = 0; i < calculatedRealMatrix.length; i++) {
            assertThat(calculatedRealMatrix[i], is(realMatrix[i]));
        }

    }

    @Test
    public void matrixFFTtest() {
        double[] testArray = new double[]{0, 1, 0};
        double[] testArrayComplex = ComplexRealMatrixParser.parseFromRealToComplex(testArray);


        DoubleFFT_1D fastFourierTransform = new DoubleFFT_1D(testArrayComplex.length);
        fastFourierTransform.realForward(testArrayComplex);

        testArrayComplex = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(testArrayComplex);


    }

    @Test
    public void matrixDCTtest() {
        double[] testArray = new double[]{1, 2, 3, 4};

        DoubleDCT_1D mydtc = new DoubleDCT_1D(testArray.length);
        mydtc.forward(testArray, true);

        System.out.println();


    }

    @Test
    public void realSliceFFT() {
        double[] testArrayComplex = ComplexRealMatrixParser.parseFromRealToComplex(currentSliceData.toDoubleVector());

        DoubleFFT_1D fastFourierTransform = new DoubleFFT_1D(testArrayComplex.length);
        fastFourierTransform.realForward(testArrayComplex);
        testArrayComplex[1] = 0;

        testArrayComplex = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(testArrayComplex);
        System.out.println("·");
    }

    @Test
    public void realSliceDCT() {
        double[] fftSliceValues = currentSliceData.toDoubleVector();

        DoubleDCT_1D mydtc = new DoubleDCT_1D(fftSliceValues.length);
        mydtc.forward(fftSliceValues, true);
        System.out.println("·");
    }


}
