import model.AudioFeatures;
import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import org.junit.Before;
import org.junit.Test;
import testutils.WavUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.TEST_SAMPLE;

public class AudioFeaturesManagerTest {

    private WavUtils wavUtils;

    private AudioFeaturesManager SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new AudioFeaturesManager();
    }

    @Test
    public void processAudioSource() throws Exception {


        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);

        ModuleParams moduleParams = new ModuleParams();
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.Mean);
        measureTypes.add(StatisticalMeasureType.StandardDeviation);

        moduleParams.setStatisticalMeasures(measureTypes);


        List<AudioFeatures> extractedFeatures = SUT.processAudioSource(samples, moduleParams);

        assertNotNull(extractedFeatures);
        assertThat(extractedFeatures.size(), is(2));


        //System.out.println(extractedFeatures.get(0).toString());
        //System.out.println(extractedFeatures.get(1).toString());

    }


}
