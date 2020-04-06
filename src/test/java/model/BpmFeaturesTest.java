package model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static testutils.TestingConstants.*;

public class BpmFeaturesTest {

    private BpmFeatures SUT;

    private BpmFeatures builtSUT;

    @Before
    public void startUp() {
        builtSUT = new BpmFeatures(TEST_AUDIO_BPM_VALUE, TEST_AUDIO_BPM_DEVIATION);
        SUT = new BpmFeatures();
    }

    @Test
    public void setGetMethods() {
        SUT.setBpmValue(TEST_AF_BPM_VALUE);
        SUT.setBpmDeviation(TEST_AF_BPM_DEVIATION);
        assertThat(SUT.getBpmValue(), is(TEST_AF_BPM_VALUE));
        assertThat(SUT.getBpmDeviation(), is(TEST_AF_BPM_DEVIATION));
    }

    @Test
    public void toStringMethod() {
        String sutToString = builtSUT.toString();
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_BPM_VALUE)));
        assertTrue(sutToString.contains(String.valueOf(TEST_AUDIO_BPM_DEVIATION)));
    }

}
