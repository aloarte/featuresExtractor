package facade;

import model.*;
import model.enums.StatisticalMeasureType;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import testutils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static constants.FeaturesNumbersConstants.MFCCS_FEATURES;
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

        moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 1, 1, 1, 1);
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.Mean);
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

        testFeatures.putScalar(8, TEST_AF_MFCC[0]);
        testFeatures.putScalar(9, TEST_AF_MFCC[1]);
        testFeatures.putScalar(10, TEST_AF_MFCC[2]);
        testFeatures.putScalar(11, TEST_AF_MFCC[3]);
        testFeatures.putScalar(12, TEST_AF_MFCC[4]);
        testFeatures.putScalar(13, TEST_AF_MFCC[5]);
        testFeatures.putScalar(14, TEST_AF_MFCC[6]);
        testFeatures.putScalar(15, TEST_AF_MFCC[7]);
        testFeatures.putScalar(16, TEST_AF_MFCC[8]);
        testFeatures.putScalar(17, TEST_AF_MFCC[9]);
        testFeatures.putScalar(18, TEST_AF_MFCC[10]);
        testFeatures.putScalar(19, TEST_AF_MFCC[11]);
        testFeatures.putScalar(20, TEST_AF_MFCC[12]);

        testFeatures.putScalar(21, TEST_AF_CF_CV[0]);
        testFeatures.putScalar(22, TEST_AF_CF_CV[1]);
        testFeatures.putScalar(23, TEST_AF_CF_CV[2]);
        testFeatures.putScalar(24, TEST_AF_CF_CV[3]);
        testFeatures.putScalar(25, TEST_AF_CF_CV[4]);
        testFeatures.putScalar(26, TEST_AF_CF_CV[5]);
        testFeatures.putScalar(27, TEST_AF_CF_CV[6]);
        testFeatures.putScalar(28, TEST_AF_CF_CV[7]);
        testFeatures.putScalar(29, TEST_AF_CF_CV[8]);
        testFeatures.putScalar(30, TEST_AF_CF_CV[9]);
        testFeatures.putScalar(31, TEST_AF_CF_CV[10]);
        testFeatures.putScalar(32, TEST_AF_CF_CV[11]);
        testFeatures.putScalar(33, TEST_AF_CF_CE);

    }

    @Test
    public void parseAudioFeatures() throws Exception {
        List<AudioFeatures> parsedAudioFeatures = SUT.parseAudioFeatures(testFeatures, moduleParams);
        assertNotNull(parsedAudioFeatures);
        assertThat(parsedAudioFeatures.size(), is(moduleParams.getStatisticalMeasuresNumber()));
        assertThat(TestUtils.getRoundDouble(parsedAudioFeatures.get(0).getZeroCrossingRate(), roundPrecision), is(TEST_AF_ZCR));
        assertThat(parsedAudioFeatures.get(0).getStatisticalMeasureType(), is(StatisticalMeasureType.Mean));
        assertNotNull(parsedAudioFeatures.get(0).getEnergyFeatures());
        assertNotNull(parsedAudioFeatures.get(0).getSpectralFeatures());
        assertNotNull(parsedAudioFeatures.get(0).getMfcCs());
        assertNotNull(parsedAudioFeatures.get(0).getChromaFeatures());

    }

    @Test
    public void parseEnergyFeatures() throws Exception {
        EnergyFeatures parsedEnergyFeatures = SUT.parseEnergyFeatures(testFeatures, 0);
        assertNotNull(parsedEnergyFeatures);
        assertThat(TestUtils.getRoundDouble(parsedEnergyFeatures.getEnergy(), roundPrecision), is(TEST_AF_EF_E));
        assertThat(TestUtils.getRoundDouble(parsedEnergyFeatures.getEntropyOfEnergy(), roundPrecision), is(TEST_AF_EF_EN));
    }

    @Test
    public void parseSpectralFeatures() throws Exception {
        SpectralFeatures parsedSpectralFeatures = SUT.parseSpectralFeatures(testFeatures, 0);
        assertNotNull(parsedSpectralFeatures);
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralCentroid(), roundPrecision), is(TEST_AF_SF_C));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralSpread(), roundPrecision), is(TEST_AF_SF_S));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralEntropy(), roundPrecision), is(TEST_AF_SF_E));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralFlux(), roundPrecision), is(TEST_AF_SF_F));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralRolloff(), roundPrecision), is(TEST_AF_SF_R));
    }

    @Test
    public void parseMFFCS() throws Exception {
        MFCCs parsedMfccs = SUT.parseMFFCS(testFeatures, 0);
        assertNotNull(parsedMfccs);
        assertThat(parsedMfccs.getMfccsValues().length, is(MFCCS_FEATURES));

        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[0], roundPrecision), is(TEST_AF_MFCC[0]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[1], roundPrecision), is(TEST_AF_MFCC[1]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[2], roundPrecision), is(TEST_AF_MFCC[2]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[3], roundPrecision), is(TEST_AF_MFCC[3]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[4], roundPrecision), is(TEST_AF_MFCC[4]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[5], roundPrecision), is(TEST_AF_MFCC[5]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[6], roundPrecision), is(TEST_AF_MFCC[6]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[7], roundPrecision), is(TEST_AF_MFCC[7]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[8], roundPrecision), is(TEST_AF_MFCC[8]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[9], roundPrecision), is(TEST_AF_MFCC[9]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[10], roundPrecision), is(TEST_AF_MFCC[10]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[11], roundPrecision), is(TEST_AF_MFCC[11]));
        assertThat(TestUtils.getRoundDouble(parsedMfccs.getMfccsValues()[12], roundPrecision), is(TEST_AF_MFCC[12]));

    }

    @Test
    public void parseChromaFeatures() throws Exception {
        ChromaFeatures parsedChromaFeatures = SUT.parseChromaFeatures(testFeatures, 0);
        assertNotNull(parsedChromaFeatures);
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[0], roundPrecision), is(TEST_AF_CF_CV[0]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[1], roundPrecision), is(TEST_AF_CF_CV[1]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[2], roundPrecision), is(TEST_AF_CF_CV[2]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[3], roundPrecision), is(TEST_AF_CF_CV[3]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[4], roundPrecision), is(TEST_AF_CF_CV[4]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[5], roundPrecision), is(TEST_AF_CF_CV[5]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[6], roundPrecision), is(TEST_AF_CF_CV[6]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[7], roundPrecision), is(TEST_AF_CF_CV[7]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[8], roundPrecision), is(TEST_AF_CF_CV[8]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[9], roundPrecision), is(TEST_AF_CF_CV[9]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[10], roundPrecision), is(TEST_AF_CF_CV[10]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaVector()[11], roundPrecision), is(TEST_AF_CF_CV[11]));
        assertThat(TestUtils.getRoundDouble(parsedChromaFeatures.getChromaDeviation(), roundPrecision), is(TEST_AF_CF_CE));
    }
}
