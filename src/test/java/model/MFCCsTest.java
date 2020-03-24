package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.TEST_AF_MFCC;
import static testutils.TestingConstants.TEST_AUDIO_MFCCS;

public class MFCCsTest {

    private MFCCs SUT;

    private MFCCs builtSUT;


    @Before
    public void startUp() {
        builtSUT = new MFCCs(TEST_AUDIO_MFCCS);
        SUT = new MFCCs();
    }

    @Test
    public void setGetMethods() {
        SUT.setMfccsValues(TEST_AF_MFCC);
        assertThat(SUT.getMfccsValues().length, is(TEST_AF_MFCC.length));

        assertThat(SUT.getMfccsValues()[0], is(TEST_AF_MFCC[0]));
        assertThat(SUT.getMfccsValues()[1], is(TEST_AF_MFCC[1]));
        assertThat(SUT.getMfccsValues()[2], is(TEST_AF_MFCC[2]));
        assertThat(SUT.getMfccsValues()[3], is(TEST_AF_MFCC[3]));
        assertThat(SUT.getMfccsValues()[4], is(TEST_AF_MFCC[4]));
        assertThat(SUT.getMfccsValues()[5], is(TEST_AF_MFCC[5]));
        assertThat(SUT.getMfccsValues()[6], is(TEST_AF_MFCC[6]));
        assertThat(SUT.getMfccsValues()[7], is(TEST_AF_MFCC[7]));
        assertThat(SUT.getMfccsValues()[8], is(TEST_AF_MFCC[8]));
        assertThat(SUT.getMfccsValues()[9], is(TEST_AF_MFCC[9]));
        assertThat(SUT.getMfccsValues()[10], is(TEST_AF_MFCC[10]));
        assertThat(SUT.getMfccsValues()[11], is(TEST_AF_MFCC[11]));
        assertThat(SUT.getMfccsValues()[12], is(TEST_AF_MFCC[12]));

    }

    @Test
    public void toStringMethod() {
        String sutToString = builtSUT.toString();
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[0])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[1])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[2])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[3])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[4])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[5])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[6])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[7])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[8])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[9])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[10])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[11])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_MFCCS[12])));

    }
}
