package testutils;

import org.jtransforms.fft.DoubleFFT_1D;
import org.junit.Test;
import utils.ComplexRealMatrixParser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ComplexRealMatrixParserTest {


    @Test
    public void parseFromReralToComplexTest() {
        double[] realMatrix = new double[]{5, -3, 45};
        double[] complexMatrix = new double[]{5, 0, -3, 0, 45, 0};

        double[] calculatedComplexMatrix = ComplexRealMatrixParser.parseFromRealToComplex(realMatrix);
        for (int i = 0; i < calculatedComplexMatrix.length; i++) {
            assertThat(calculatedComplexMatrix[i], is(complexMatrix[i]));
        }

        double[] calculatedRealMatrix = ComplexRealMatrixParser.parseFromComplexToReal(calculatedComplexMatrix);
        for (int i = 0; i < calculatedRealMatrix.length; i++) {
            assertThat(calculatedRealMatrix[i], is(realMatrix[i]));
        }

    }

    @Test
    public void matrixFFTtest() {
        double[] testArray = new double[]{2, 4, 8, 2, 3, 5, 1, 2};
        double[] testArrayComplex = ComplexRealMatrixParser.parseFromRealToComplex(testArray);


        DoubleFFT_1D fastFourierTransform = new DoubleFFT_1D(testArrayComplex.length);
        fastFourierTransform.realForward(testArrayComplex);

        testArrayComplex = ComplexRealMatrixParser.parseFromComplexToReal(testArrayComplex);


    }
}
