package components;

import facade.DataParser;
import model.AudioFeatures;
import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;
import testutils.TestUtils;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class FeaturesProcessorTest {

    private INDArray currentSliceData;
    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;
    private FeaturesProcessor SUT;
    DataParser dataParser;

    private double roundPrecision = 1000000d;


    @Before
    public void startUp() {
        SUT = new FeaturesProcessor();

        dataParser = new DataParser();

        currentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE);
        fftPreviousSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE);

        System.out.println("currentSliceData[" + currentSliceData.shape()[0] + "][" + currentSliceData.shape()[1] + "]");
        System.out.println("fftCurrentSliceData[" + fftCurrentSliceData.shape()[0] + "][" + fftCurrentSliceData.shape()[1] + "]");
        System.out.println("fftPreviousSliceData[" + fftPreviousSliceData.shape()[0] + "][" + fftPreviousSliceData.shape()[1] + "]");

    }


    @Test
    public void extractFeaturesFromSlice() throws AudioExtractionException {

        INDArray extractedFeatures = SUT.extractFeaturesFromSlice(currentSliceData, fftCurrentSliceData, fftPreviousSliceData, TEST_FREQUENCY_RATE, TEST_NFFT);
        assertNotNull(extractedFeatures);
        assertThat(extractedFeatures.shape()[0], is(TOTAL_FEATURES));
        assertThat(extractedFeatures.shape()[1], is(1));

        AudioFeatures extractedFeaturesFromSlice = dataParser.parseAudioFeature(extractedFeatures, 0);
        assertNotNull(extractedFeaturesFromSlice);

        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getZeroCrossingRate(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_ZCR_VALUE, roundPrecision)));

        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getEnergyFeatures().getEnergy(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_ENERGY_VALUE, roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getEnergyFeatures().getEntropyOfEnergy(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_ENERGY_ENTROPY_VALUE, roundPrecision)));

        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getSpectralFeatures().getSpectralCentroid(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_SPECTRAL_CENTROID_VALUE, roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getSpectralFeatures().getSpectralEntropy(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_SPECTRAL_ENTROPY_VALUE, roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getSpectralFeatures().getSpectralFlux(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_SPECTRAL_FLUX_VALUE, roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getSpectralFeatures().getSpectralRolloff(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE, roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getSpectralFeatures().getSpectralSpread(), roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_SPECTRAL_SPREAD_VALUE, roundPrecision)));

        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[0], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[0], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[1], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[1], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[2], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[2], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[3], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[3], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[4], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[4], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[5], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[5], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[6], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[6], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[7], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[7], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[8], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[8], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[9], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[9], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[10], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[10], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getChromaFeatures().getChromaVector()[11], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_CHROMA_VECTORS[11], roundPrecision)));

        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[0], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[0], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[1], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[1], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[2], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[2], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[3], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[3], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[4], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[4], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[5], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[5], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[6], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[6], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[7], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[7], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[8], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[8], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[9], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[9], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[10], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[10], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[11], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[11], roundPrecision)));
        assertThat(TestUtils.getRoundDouble(extractedFeaturesFromSlice.getMfcCs().getMfccsValues()[12], roundPrecision), is(TestUtils.getRoundDouble(TEST_AUDIO_MFCCS[12], roundPrecision)));


    }

    @Test
    public void extractZeroCrossingRate() {
        double zeroCrossingRate = SUT.extractZeroCrossingRate(currentSliceData);
        assertThat(zeroCrossingRate, is(TEST_AUDIO_ZCR_VALUE));

    }
}
