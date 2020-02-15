import model.*;
import model.enums.StatisticalMeasureType;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class DataParserTest {

    INDArray testFeatures;
    ModuleParams moduleParams;
    private double roundPrecision = 1000d;
    private DataParser SUT;

    @Before
    public void startUp() {
        SUT = new DataParser();

        moduleParams = new ModuleParams();
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.MEAN);
        moduleParams.setStatisticalMeasures(measureTypes);


        testFeatures = Nd4j.zeros(TOTAL_FEATURES);
        testFeatures.putScalar(0, TEST_AF_ZCR);
        testFeatures.putScalar(1, TEST_AF_EF_E);
        testFeatures.putScalar(2, TEST_AF_EF_EN);
        testFeatures.putScalar(3, TEST_AF_SF_C);
        testFeatures.putScalar(4, TEST_AF_SF_S);
        testFeatures.putScalar(5, TEST_AF_SF_E);
        testFeatures.putScalar(6, TEST_AF_SF_F);
        testFeatures.putScalar(7, TEST_AF_SF_R);
        testFeatures.putScalar(8, 0);
        testFeatures.putScalar(9, 0);
        testFeatures.putScalar(10, 0);
        testFeatures.putScalar(11, 0);
        testFeatures.putScalar(12, 0);
        testFeatures.putScalar(13, 0);
        testFeatures.putScalar(14, 0);
        testFeatures.putScalar(15, 0);
        testFeatures.putScalar(16, 0);
        testFeatures.putScalar(17, 0);
        testFeatures.putScalar(18, 0);
        testFeatures.putScalar(19, 0);
        testFeatures.putScalar(20, 0);
        testFeatures.putScalar(21, TEST_AF_CF_CV_1);
        testFeatures.putScalar(22, TEST_AF_CF_CV_2);
        testFeatures.putScalar(23, TEST_AF_CF_CV_3);
        testFeatures.putScalar(24, TEST_AF_CF_CV_4);
        testFeatures.putScalar(25, TEST_AF_CF_CV_5);
        testFeatures.putScalar(26, TEST_AF_CF_CV_6);
        testFeatures.putScalar(27, TEST_AF_CF_CV_7);
        testFeatures.putScalar(28, TEST_AF_CF_CV_8);
        testFeatures.putScalar(29, TEST_AF_CF_CV_9);
        testFeatures.putScalar(30, TEST_AF_CF_CV_10);
        testFeatures.putScalar(31, TEST_AF_CF_CV_11);
        testFeatures.putScalar(32, TEST_AF_CF_CV_12);
        testFeatures.putScalar(33, TEST_AF_CF_CE);

    }

    @Test
    public void parseAudioFeatures() throws Exception {
        List<AudioFeatures> parsedAudioFeatures = SUT.parseAudioFeatures(testFeatures, moduleParams);
        assertNotNull(parsedAudioFeatures);
        assertThat(parsedAudioFeatures.size(), is(moduleParams.getStatisticalMeasuresNumber()));
        assertThat((double) Math.round(parsedAudioFeatures.get(0).getZeroCrossingRate() * roundPrecision) / roundPrecision, is(TEST_AF_ZCR));
        assertThat(parsedAudioFeatures.get(0).getStatisticalMeasureType(), is(StatisticalMeasureType.MEAN));
        assertNotNull(parsedAudioFeatures.get(0).getEnergyFeatures());
        assertNotNull(parsedAudioFeatures.get(0).getSpectralFeatures());
        assertNotNull(parsedAudioFeatures.get(0).getMfcCs());
        assertNotNull(parsedAudioFeatures.get(0).getChromaFeatures());

    }

    @Test
    public void parseEnergyFeatures() throws Exception {
        EnergyFeatures parsedEnergyFeatures = SUT.parseEnergyFeatures(testFeatures, 0);
        assertNotNull(parsedEnergyFeatures);
        assertThat((double) Math.round(parsedEnergyFeatures.getEnergy() * roundPrecision) / roundPrecision, is(TEST_AF_EF_E));
        assertThat((double) Math.round(parsedEnergyFeatures.getEntropyOfEnergy() * roundPrecision) / roundPrecision, is(TEST_AF_EF_EN));
    }

    @Test
    public void parseSpectralFeatures() throws Exception {
        SpectralFeatures parsedSpectralFeatures = SUT.parseSpectralFeatures(testFeatures, 0);
        assertNotNull(parsedSpectralFeatures);
        assertThat((double) Math.round(parsedSpectralFeatures.getSpectralCentroid() * roundPrecision) / roundPrecision, is(TEST_AF_SF_C));
        assertThat((double) Math.round(parsedSpectralFeatures.getSpectralSpread() * roundPrecision) / roundPrecision, is(TEST_AF_SF_S));
        assertThat((double) Math.round(parsedSpectralFeatures.getSpectralEntropy() * roundPrecision) / roundPrecision, is(TEST_AF_SF_E));
        assertThat((double) Math.round(parsedSpectralFeatures.getSpectralFlux() * roundPrecision) / roundPrecision, is(TEST_AF_SF_F));
        assertThat((double) Math.round(parsedSpectralFeatures.getSpectralRolloff() * roundPrecision) / roundPrecision, is(TEST_AF_SF_R));
    }

//    @Ignore("parseMFFCS function isn't complete yet")
//    @Test
//    public void parseMFFCS() throws Exception {
//        SUT.parseMFFCS(testFeatures, 0);
//    }

    @Test
    public void parseChromaFeatures() throws Exception {
        ChromaFeatures parsedChromaFeatures = SUT.parseChromaFeatures(testFeatures, 0);
        assertNotNull(parsedChromaFeatures);
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[0] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_1));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[1] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_2));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[2] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_3));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[3] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_4));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[4] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_5));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[5] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_6));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[6] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_7));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[7] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_8));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[8] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_9));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[9] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_10));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[10] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_11));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaVector()[11] * roundPrecision) / roundPrecision, is(TEST_AF_CF_CV_12));
        assertThat((double) Math.round(parsedChromaFeatures.getChromaDeviation() * roundPrecision) / roundPrecision, is(TEST_AF_CF_CE));
    }
}
