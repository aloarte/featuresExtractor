package utils;

public class ComplexRealMatrixParser {

    /**
     * Parse a vector of real numbers into a new double vector with its imaginary part equals to 0
     *
     * @param realVector real matrix with size N
     * @return imaginaryMatrix with each imaginary part as 0. Size 2*N
     */
    public static double[] parseFromRealToComplex(double[] realVector) {
        double[] complexMatrix = new double[realVector.length * 2];
        for (int i = 0; i < realVector.length; i++) {
            complexMatrix[(i * 2)] = realVector[i];
            complexMatrix[(i * 2) + 1] = 0;
        }
        return complexMatrix;
    }

    /**
     * Parse a vector of complex numbers into a new double vector without its imaginary part.
     *
     * @param complexVector complex vector with size N
     * @return vector with only the real values from the complexValue vector. Size 2/N
     */
    public static double[] parseAbsValueFromComplexToReal(double[] complexVector) {
        double[] realMatrix = new double[complexVector.length / 2];
        for (int i = 0; i < complexVector.length; i += 2) {

            realMatrix[(i / 2)] = getAbsoluteValueFromComplexNumber(complexVector[i], complexVector[i + 1]);
        }
        return realMatrix;
    }

    public static double getAbsoluteValueFromComplexNumber(double realValue, double imaginaryValue) {
        return Math.sqrt(Math.pow(realValue, 2) + Math.pow(imaginaryValue, 2));
    }

}
