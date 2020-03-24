package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.*;

public class EnergyFeaturesTest {

    private EnergyFeatures SUT;

    private EnergyFeatures builtSUT;

    @Before
    public void startUp() {
        builtSUT = new EnergyFeatures(TEST_AUDIO_ENERGY_VALUE, TEST_AUDIO_ENERGY_ENTROPY_VALUE);
        SUT = new EnergyFeatures();
    }

    @Test
    public void setGetMethods() {
        SUT.setEnergy(TEST_AF_EF_E);
        SUT.setEntropyOfEnergy(TEST_AF_EF_EN);
        assertThat(SUT.getEnergy(), is(TEST_AF_EF_E));
        assertThat(SUT.getEntropyOfEnergy(), is(TEST_AF_EF_EN));
    }

    @Test
    public void toStringMethod() {
        String sutToString = builtSUT.toString();
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_ENERGY_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_ENERGY_ENTROPY_VALUE)));
    }

}
