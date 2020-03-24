package model;


public class ChromaFeatures {

    /**
     * Constant that represents the number of elements that will have the chroma vector
     */
    public final int CHROMA_ELEMENTS = 12;

    //22-33
    /**
     * A 12-element representation of the spectral energy where the bins represent the 12 equal-tempered pitch classes of western-type music (semitone spacing).
     */
    private double[] chromaVector;

    //34
    /**
     * The standard deviation of the 12 chroma coefficients.
     */
    private double chromaDeviation;

    /**
     * Default constructor.
     */
    public ChromaFeatures() {
        this.chromaVector = new double[CHROMA_ELEMENTS];
        this.chromaDeviation = 0F;
    }

    /**
     * Constructor of the ChromaFeatures
     *
     * @param chromaVector    value of the chroma vector
     * @param chromaDeviation value of  the chroma vector deviation
     */
    public ChromaFeatures(double[] chromaVector, double chromaDeviation) {
        //Check that the passed chroma vector has exactly 12 elements
        if (chromaVector != null && chromaVector.length == CHROMA_ELEMENTS) {
            this.chromaVector = chromaVector;
        } else this.chromaVector = new double[CHROMA_ELEMENTS];

        this.chromaDeviation = chromaDeviation;
    }

    public double[] getChromaVector() {
        return chromaVector;
    }

    public void setChromaVector(double[] chromaVector) {
        this.chromaVector = chromaVector;
    }

    public double getChromaDeviation() {
        return chromaDeviation;
    }

    public void setChromaDeviation(double chromaDeviation) {
        this.chromaDeviation = chromaDeviation;
    }

    @Override
    public String toString() {
        return " Chroma Features:\n " +
                "- Chroma vector\n" +
                " [0]: " + getChromaVector()[0] + "\n" +
                " [1]: " + getChromaVector()[1] + "\n" +
                " [2]: " + getChromaVector()[2] + "\n" +
                " [3]: " + getChromaVector()[3] + "\n" +
                " [4]: " + getChromaVector()[4] + "\n" +
                " [5]: " + getChromaVector()[5] + "\n" +
                " [6]: " + getChromaVector()[6] + "\n" +
                " [7]: " + getChromaVector()[7] + "\n" +
                " [8]: " + getChromaVector()[8] + "\n" +
                " [9]: " + getChromaVector()[9] + "\n" +
                " [10]: " + getChromaVector()[10] + "\n" +
                " [11]: " + getChromaVector()[11] + "\n" +
                "- Chroma deviation: " + getChromaDeviation() + "\n";
    }
}
