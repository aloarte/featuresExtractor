package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.TEST_AF_CF_CE;
import static testutils.TestingConstants.TEST_AF_CF_CV;

public class ChromaFeaturesTest {

    private ChromaFeatures SUT;

    private ChromaFeatures builtSUT;


    @Before
    public void startUp() {
        builtSUT = new ChromaFeatures(TEST_AF_CF_CV, TEST_AF_CF_CE);
        SUT = new ChromaFeatures();
    }

    @Test
    public void setGetMethods() {
        SUT.setChromaVector(TEST_AF_CF_CV);
        SUT.setChromaDeviation(TEST_AF_CF_CE);
        assertThat(SUT.getChromaVector().length, is(TEST_AF_CF_CV.length));

        assertThat(SUT.getChromaVector()[0], is(TEST_AF_CF_CV[0]));
        assertThat(SUT.getChromaVector()[1], is(TEST_AF_CF_CV[1]));
        assertThat(SUT.getChromaVector()[2], is(TEST_AF_CF_CV[2]));
        assertThat(SUT.getChromaVector()[3], is(TEST_AF_CF_CV[3]));
        assertThat(SUT.getChromaVector()[4], is(TEST_AF_CF_CV[4]));
        assertThat(SUT.getChromaVector()[5], is(TEST_AF_CF_CV[5]));
        assertThat(SUT.getChromaVector()[6], is(TEST_AF_CF_CV[6]));
        assertThat(SUT.getChromaVector()[7], is(TEST_AF_CF_CV[7]));
        assertThat(SUT.getChromaVector()[8], is(TEST_AF_CF_CV[8]));
        assertThat(SUT.getChromaVector()[9], is(TEST_AF_CF_CV[9]));
        assertThat(SUT.getChromaVector()[10], is(TEST_AF_CF_CV[10]));
        assertThat(SUT.getChromaVector()[11], is(TEST_AF_CF_CV[11]));
        assertThat(SUT.getChromaDeviation(), is(TEST_AF_CF_CE));
    }

    @Test
    public void toStringMethod() {
        String sutToString = builtSUT.toString();
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[0])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[1])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[2])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[3])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[4])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[5])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[6])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[7])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[8])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[9])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[10])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CV[11])));
        assertTrue(sutToString.contains(String.valueOf(TEST_AF_CF_CE)));
    }

}
