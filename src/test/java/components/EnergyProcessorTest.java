package components;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import testutils.INDArrayUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class EnergyProcessorTest {

    private EnergyProcessor SUT;
    private INDArray currentSliceData;

    @Before
    public void startUp() {
        SUT = EnergyProcessor.getInstance(EPS_CONSTANT);
        currentSliceData = INDArrayUtils.readAudioSliceFromFile(TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE);
    }

    @Test
    public void extractEnergy() {
        double energy = SUT.extractEnergy(currentSliceData);
        assertThat(energy, is(TEST_AUDIO_ENERGY_VALUE));
    }

    @Test
    public void extractEnergyEntropy() {
        double energyEntropy = SUT.extractEnergyEntropy(currentSliceData, 10);
        assertThat(energyEntropy, is(TEST_AUDIO_ENERGY_ENTROPY_VALUE));
    }
}
