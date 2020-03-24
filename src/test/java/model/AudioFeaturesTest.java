package model;

import org.junit.Before;
import org.junit.Test;

import static model.enums.StatisticalMeasureType.Mean;
import static model.enums.StatisticalMeasureType.Unknown;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.TEST_AF_ZCR;

public class AudioFeaturesTest {

    private AudioFeatures SUT;

    @Before
    public void startUp() {
        SUT = new AudioFeatures();
    }

    @Test
    public void setGetMethod() {
        SUT.setStatisticalMeasureType(Mean);
        EnergyFeatures ef = new EnergyFeatures();
        ChromaFeatures cf = new ChromaFeatures();
        MFCCs mfccs = new MFCCs();
        SpectralFeatures sf = new SpectralFeatures();
        SUT.setEnergyFeatures(ef);
        SUT.setChromaFeatures(cf);
        SUT.setSpectralFeatures(sf);
        SUT.setMfcCs(mfccs);
        SUT.setZeroCrossingRate(TEST_AF_ZCR);
        assertThat(SUT.getEnergyFeatures(), is(ef));
        assertThat(SUT.getChromaFeatures(), is(cf));
        assertThat(SUT.getSpectralFeatures(), is(sf));
        assertThat(SUT.getMfcCs(), is(mfccs));
        assertThat(SUT.getZeroCrossingRate(), is(TEST_AF_ZCR));


    }

    @Test
    public void toStringMethod() {
        String sutToString = SUT.toString();
        assertTrue(sutToString.contains(Unknown.name()));
    }

}
