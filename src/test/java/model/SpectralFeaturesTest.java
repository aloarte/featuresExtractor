package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.*;

public class SpectralFeaturesTest {

    private SpectralFeatures builtSUT;


    private SpectralFeatures SUT;

    @Before
    public void startUp() {
        builtSUT = new SpectralFeatures(TEST_AUDIO_SPECTRAL_CENTROID_VALUE, TEST_AUDIO_SPECTRAL_SPREAD_VALUE, TEST_AUDIO_SPECTRAL_ENTROPY_VALUE, TEST_AUDIO_SPECTRAL_FLUX_VALUE, TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE);
        SUT = new SpectralFeatures();

    }

    @Test
    public void setGetMethods() {
        SUT.setSpectralCentroid(TEST_AF_SF_C);
        SUT.setSpectralSpread(TEST_AF_SF_S);
        SUT.setSpectralEntropy(TEST_AF_SF_E);
        SUT.setSpectralFlux(TEST_AF_SF_F);
        SUT.setSpectralRolloff(TEST_AF_SF_R);
        assertThat(SUT.getSpectralCentroid(), is(TEST_AF_SF_C));
        assertThat(SUT.getSpectralSpread(), is(TEST_AF_SF_S));
        assertThat(SUT.getSpectralEntropy(), is(TEST_AF_SF_E));
        assertThat(SUT.getSpectralFlux(), is(TEST_AF_SF_F));
        assertThat(SUT.getSpectralRolloff(), is(TEST_AF_SF_R));

    }

    @Test
    public void toStringMethod() {
        String sutToString = builtSUT.toString();
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_SPECTRAL_CENTROID_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_SPECTRAL_SPREAD_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_SPECTRAL_ENTROPY_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_SPECTRAL_FLUX_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE)));
    }

}
