package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AudioFeaturesTest {

    private AudioFeatures SUT;

    @Before
    public void startUp() {
        SUT = new AudioFeatures();
    }

    @Test
    public void setGetMethod() {
        List<AudioShortFeatures> listAudioShortFeatures = new ArrayList<>();
        listAudioShortFeatures.add(new AudioShortFeatures());
        BpmFeatures bpmFeatures = new BpmFeatures();
        SUT.setAudioShortFeaturesList(listAudioShortFeatures);
        SUT.setBpmFeatures(bpmFeatures);
        assertThat(SUT.getAudioShortFeaturesList(), is(listAudioShortFeatures));
        assertThat(SUT.getBpmFeatures(), is(bpmFeatures));
    }

}
