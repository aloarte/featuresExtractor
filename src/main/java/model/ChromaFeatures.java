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
    private float[] chromaVector;

    //34
    /**
     * The standard deviation of the 12 chroma coefficients.
     */
    private float chromaDeviation;

    /**
     * Default constructor.
     */
    public ChromaFeatures() {
        this.chromaVector = new float[CHROMA_ELEMENTS];
        this.chromaDeviation = 0F;
    }

    /**
     * Constructor of the ChromaFeatures
     *
     * @param chromaVector    value of the chroma vector
     * @param chromaDeviation value of  the chroma vector deviation
     */
    public ChromaFeatures(float[] chromaVector, float chromaDeviation) {
        //Check that the passed chroma vector has exactly 12 elements
        if (chromaVector != null && chromaVector.length == CHROMA_ELEMENTS) {
            this.chromaVector = chromaVector;
        } else this.chromaVector = new float[CHROMA_ELEMENTS];

        this.chromaDeviation = chromaDeviation;
    }

    public float[] getChromaVector() {
        return chromaVector;
    }

    public void setChromaVector(float[] chromaVector) {
        this.chromaVector = chromaVector;
    }

    public float getChromaDeviation() {
        return chromaDeviation;
    }

    public void setChromaDeviation(float chromaDeviation) {
        this.chromaDeviation = chromaDeviation;
    }
}
