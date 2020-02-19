package model;

import static constants.FeaturesNumbersConstants.MFCCS_FEATURES;

/**
 * Mel Frequency Cepstral Coefficients form a cepstral representation where the frequency bands are not linear but distributed according to the mel-scale.
 */
public class MFCCs {

    private double[] mfccsValues;


    /**
     * Default constructor.
     */
    public MFCCs() {
        this.mfccsValues = new double[MFCCS_FEATURES];
    }

    /**
     * Constructor of the MFCCs
     *
     * @param mfccsValues value of the mfccs vector
     */
    public MFCCs(double[] mfccsValues) {
        //Check that the passed mfccs vector has exactly 13 elements
        if (mfccsValues != null && mfccsValues.length == MFCCS_FEATURES) {
            this.mfccsValues = mfccsValues;
        } else this.mfccsValues = new double[MFCCS_FEATURES];

    }

    public double[] getMfccsValues() {
        return mfccsValues;
    }

    public void setMfccsValues(double[] mfccsValues) {
        this.mfccsValues = mfccsValues;
    }

    @Override
    public String toString() {
        return "----- MFCCs Features: -----\n " +
                "- Vector\n" +
                " [0]: " + getMfccsValues()[0] + "\n" +
                " [1]: " + getMfccsValues()[1] + "\n" +
                " [2]: " + getMfccsValues()[2] + "\n" +
                " [3]: " + getMfccsValues()[3] + "\n" +
                " [4]: " + getMfccsValues()[4] + "\n" +
                " [5]: " + getMfccsValues()[5] + "\n" +
                " [6]: " + getMfccsValues()[6] + "\n" +
                " [7]: " + getMfccsValues()[7] + "\n" +
                " [8]: " + getMfccsValues()[8] + "\n" +
                " [9]: " + getMfccsValues()[9] + "\n" +
                " [10]: " + getMfccsValues()[10] + "\n" +
                " [11]: " + getMfccsValues()[11] + "\n" +
                " [11]: " + getMfccsValues()[12] + "\n";
    }

}
