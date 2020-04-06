package model;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RawAudioFeaturesTest {

    private RawAudioFeatures SUT;

    @Before
    public void startUp() {
        SUT = new RawAudioFeatures();
    }

    @Test
    public void setGetMethod() {
        INDArray indArray = Nd4j.zeros(1, 1);
        BpmFeatures bpmFeatures = new BpmFeatures();
        SUT.setMeanMidTermFeatures(indArray);
        SUT.setBpmFeatures(bpmFeatures);
        assertThat(SUT.getMeanMidTermFeatures(), is(indArray));
        assertThat(SUT.getBpmFeatures(), is(bpmFeatures));
    }

}
