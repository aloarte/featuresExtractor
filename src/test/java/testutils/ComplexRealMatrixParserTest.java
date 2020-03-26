package testutils;

import org.junit.Before;
import org.junit.Test;
import utils.ComplexRealMatrixParser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ComplexRealMatrixParserTest {


    @Before
    public void startUp() {

    }

    @Test
    public void parseRealComplexTest() {
        double[] realMatrix = new double[]{5, -3, 45};
        double[] realAbsMatrix = new double[]{5, 3, 45};

        double[] complexMatrix = new double[]{5, 0, -3, 0, 45, 0};


        double[] calculatedComplexMatrix = ComplexRealMatrixParser.parseFromRealToComplex(realMatrix);
        for (int i = 0; i < calculatedComplexMatrix.length; i++) {
            assertThat(calculatedComplexMatrix[i], is(complexMatrix[i]));
        }

        double[] calculatedRealMatrix = ComplexRealMatrixParser.parseAbsValueFromComplexToReal(calculatedComplexMatrix);
        for (int i = 0; i < calculatedRealMatrix.length; i++) {
            assertThat(calculatedRealMatrix[i], is(realAbsMatrix[i]));
        }

    }


}
