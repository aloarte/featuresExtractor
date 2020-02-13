package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static testutils.TestingConstants.EPS_CONSTANT;

public class EnergyProcessorTest {

    private EnergyProcessor SUT;

    @Before
    public void startUp() {
        SUT = new EnergyProcessor(EPS_CONSTANT);
    }

    @Ignore
    @Test
    public void extractEnergy() {
        //double energy = SUT.extractEnergy();

        //TODO: Perform assertions against controlled values
    }

    @Ignore
    @Test
    public void extractEnergyEntropy() {
        //double stSpectralEntropy = SUT.extractEnergyEntropy();
        //TODO: Perform assertions against controlled values
    }
}
