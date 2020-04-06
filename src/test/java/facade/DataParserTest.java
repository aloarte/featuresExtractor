package facade;

import model.*;
import model.enums.AudioAnalysisExceptionType;
import model.enums.DataParseExceptionType;
import model.enums.StatisticalMeasureType;
import model.exceptions.DataParseException;
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
import static org.junit.Assert.*;
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

        testFeatures = Nd4j.zeros(TOTAL_FEATURES, 1);
        testFeatures.putScalar(new int[]{0, 0}, TEST_AF_ZCR);
        testFeatures.putScalar(new int[]{1, 0}, TEST_AF_EF_E);
        testFeatures.putScalar(new int[]{2, 0}, TEST_AF_EF_EN);
        testFeatures.putScalar(new int[]{3, 0}, TEST_AF_SF_C);
        testFeatures.putScalar(new int[]{4, 0}, TEST_AF_SF_S);
        testFeatures.putScalar(new int[]{5, 0}, TEST_AF_SF_E);
        testFeatures.putScalar(new int[]{6, 0}, TEST_AF_SF_F);
        testFeatures.putScalar(new int[]{7, 0}, TEST_AF_SF_R);

        testFeatures.putScalar(new int[]{8, 0}, TEST_AF_MFCC[0]);
        testFeatures.putScalar(new int[]{9, 0}, TEST_AF_MFCC[1]);
        testFeatures.putScalar(new int[]{10, 0}, TEST_AF_MFCC[2]);
        testFeatures.putScalar(new int[]{11, 0}, TEST_AF_MFCC[3]);
        testFeatures.putScalar(new int[]{12, 0}, TEST_AF_MFCC[4]);
        testFeatures.putScalar(new int[]{13, 0}, TEST_AF_MFCC[5]);
        testFeatures.putScalar(new int[]{14, 0}, TEST_AF_MFCC[6]);
        testFeatures.putScalar(new int[]{15, 0}, TEST_AF_MFCC[7]);
        testFeatures.putScalar(new int[]{16, 0}, TEST_AF_MFCC[8]);
        testFeatures.putScalar(new int[]{17, 0}, TEST_AF_MFCC[9]);
        testFeatures.putScalar(new int[]{18, 0}, TEST_AF_MFCC[10]);
        testFeatures.putScalar(new int[]{19, 0}, TEST_AF_MFCC[11]);
        testFeatures.putScalar(new int[]{20, 0}, TEST_AF_MFCC[12]);

        testFeatures.putScalar(new int[]{21, 0}, TEST_AF_CF_CV[0]);
        testFeatures.putScalar(new int[]{22, 0}, TEST_AF_CF_CV[1]);
        testFeatures.putScalar(new int[]{23, 0}, TEST_AF_CF_CV[2]);
        testFeatures.putScalar(new int[]{24, 0}, TEST_AF_CF_CV[3]);
        testFeatures.putScalar(new int[]{25, 0}, TEST_AF_CF_CV[4]);
        testFeatures.putScalar(new int[]{26, 0}, TEST_AF_CF_CV[5]);
        testFeatures.putScalar(new int[]{27, 0}, TEST_AF_CF_CV[6]);
        testFeatures.putScalar(new int[]{28, 0}, TEST_AF_CF_CV[7]);
        testFeatures.putScalar(new int[]{29, 0}, TEST_AF_CF_CV[8]);
        testFeatures.putScalar(new int[]{30, 0}, TEST_AF_CF_CV[9]);
        testFeatures.putScalar(new int[]{31, 0}, TEST_AF_CF_CV[10]);
        testFeatures.putScalar(new int[]{32, 0}, TEST_AF_CF_CV[11]);
        testFeatures.putScalar(new int[]{33, 0}, TEST_AF_CF_CE);

    }

    @Test
    public void parseAudioFeatures_validInput() throws Exception {
        List<AudioShortFeatures> parsedAudioShortFeatures = SUT.parseAudioFeatures(testFeatures, moduleParams);
        assertNotNull(parsedAudioShortFeatures);
        assertThat(parsedAudioShortFeatures.size(), is(moduleParams.getStatisticalMeasuresNumber()));
        assertThat(TestUtils.getRoundDouble(parsedAudioShortFeatures.get(0).getZeroCrossingRate(), roundPrecision), is(TEST_AF_ZCR));
        assertThat(parsedAudioShortFeatures.get(0).getStatisticalMeasureType(), is(StatisticalMeasureType.Mean));
        assertNotNull(parsedAudioShortFeatures.get(0).getEnergyFeatures());
        assertNotNull(parsedAudioShortFeatures.get(0).getSpectralFeatures());
        assertNotNull(parsedAudioShortFeatures.get(0).getMfcCs());
        assertNotNull(parsedAudioShortFeatures.get(0).getChromaFeatures());

    }

    @Test
    public void parseAudioFeatures_badInputs() {
        try {
            SUT.parseAudioFeatures(null, moduleParams);
            fail("This test should raise an exception");
        } catch (DataParseException e) {
            assertNotNull(e);
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioParsing));
            assertThat(e.getDataParserExceptionSubtype(), is(DataParseExceptionType.NullExtractedFeatures));
        }

        double[][] badInputRows = new double[][]{{0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1},
                {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1},
                {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}, {0.1}};

        try {
            SUT.parseAudioFeatures(Nd4j.create(badInputRows), moduleParams);
            fail("This test should raise an exception");
        } catch (DataParseException e) {
            assertNotNull(e);
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioParsing));
            assertThat(e.getDataParserExceptionSubtype(), is(DataParseExceptionType.IllegalElementNumber));
        }

        double[][] badInputCols = new double[][]{{0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2},
                {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2},
                {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2},
                {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2},
                {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.2}, {0.1, 0.1}};

        try {
            SUT.parseAudioFeatures(Nd4j.create(badInputCols), moduleParams);
            fail("This test should raise an exception");
        } catch (DataParseException e) {
            assertNotNull(e);
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioParsing));
            assertThat(e.getDataParserExceptionSubtype(), is(DataParseExceptionType.IllegalElementNumber));
        }

    }

    @Test
    public void parseAudioFeature_badInput() {
        try {
            SUT.parseAudioFeature(null, 0);
            fail("This test should raise an exception");
        } catch (DataParseException e) {
            assertNotNull(e);
            assertThat(e.getAudioAnalysisExceptionType(), is(AudioAnalysisExceptionType.AudioParsing));
            assertThat(e.getDataParserExceptionSubtype(), is(DataParseExceptionType.NullExtractedFeatures));
        }
    }


    @Test
    public void parseEnergyFeatures() {
        EnergyFeatures parsedEnergyFeatures = SUT.parseEnergyFeatures(testFeatures, 0);
        assertNotNull(parsedEnergyFeatures);
        assertThat(TestUtils.getRoundDouble(parsedEnergyFeatures.getEnergy(), roundPrecision), is(TEST_AF_EF_E));
        assertThat(TestUtils.getRoundDouble(parsedEnergyFeatures.getEntropyOfEnergy(), roundPrecision), is(TEST_AF_EF_EN));
    }

    @Test
    public void parseSpectralFeatures() {
        SpectralFeatures parsedSpectralFeatures = SUT.parseSpectralFeatures(testFeatures, 0);
        assertNotNull(parsedSpectralFeatures);
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralCentroid(), roundPrecision), is(TEST_AF_SF_C));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralSpread(), roundPrecision), is(TEST_AF_SF_S));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralEntropy(), roundPrecision), is(TEST_AF_SF_E));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralFlux(), roundPrecision), is(TEST_AF_SF_F));
        assertThat(TestUtils.getRoundDouble(parsedSpectralFeatures.getSpectralRolloff(), roundPrecision), is(TEST_AF_SF_R));
    }

    @Test
    public void parseMFFCS() {
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
    public void parseChromaFeatures() {
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
