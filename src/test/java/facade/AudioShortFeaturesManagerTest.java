package facade;

import model.AudioFeatures;
import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testutils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class AudioShortFeaturesManagerTest {


    private TestUtils testUtils;

    private AudioFeaturesManager SUT;

    @Before
    public void startUp() {
        testUtils = new TestUtils();
        SUT = new AudioFeaturesManager();
    }

    @Ignore("The 301s sample of the song take too long.")
    @Test
    public void processAudioSource_301s() throws Exception {
        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_301s_WAV);

        ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.Mean);
        measureTypes.add(StatisticalMeasureType.StandardDeviation);

        moduleParams.setStatisticalMeasures(measureTypes);

        AudioFeatures audioFeatures = SUT.processAudioSource(samples, moduleParams);

        assertNotNull(audioFeatures);
        assertNotNull(audioFeatures.getAudioShortFeaturesList());
        assertNotNull(audioFeatures.getBpmFeatures());

        assertThat(audioFeatures.getAudioShortFeaturesList().size(), is(2));


//        System.out.println(extractedFeatures.get(0).toString());
//        System.out.println(extractedFeatures.get(1).toString());

    }


    @Test
    public void processAudioSource_30s() throws Exception {


        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_30s_WAV);

        ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.Mean);
        measureTypes.add(StatisticalMeasureType.StandardDeviation);

        moduleParams.setStatisticalMeasures(measureTypes);

        AudioFeatures audioFeatures = SUT.processAudioSource(samples, moduleParams);

        assertNotNull(audioFeatures);
        assertNotNull(audioFeatures.getAudioShortFeaturesList());
        assertNotNull(audioFeatures.getBpmFeatures());

        assertThat(audioFeatures.getAudioShortFeaturesList().size(), is(2));


//        System.out.println(extractedFeatures.get(0).toString());
//        System.out.println(extractedFeatures.get(1).toString());

    }


    @Test
    public void processAudioSource_10s() throws Exception {


        // Transform the input file into a float[] array
        double[] samples = testUtils.load_wav(TEST_KNIFE_10s_WAV);

        ModuleParams moduleParams = new ModuleParams(TEST_FREQUENCY_RATE, 0.01, 0.01, 1, 1);
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.Mean);
        measureTypes.add(StatisticalMeasureType.StandardDeviation);

        moduleParams.setStatisticalMeasures(measureTypes);

        AudioFeatures audioFeatures = SUT.processAudioSource(samples, moduleParams);

        assertNotNull(audioFeatures);
        assertNotNull(audioFeatures.getAudioShortFeaturesList());
        assertNotNull(audioFeatures.getBpmFeatures());

        assertThat(audioFeatures.getAudioShortFeaturesList().size(), is(2));


//        System.out.println(extractedFeatures.get(0).toString());
//        System.out.println(extractedFeatures.get(1).toString());


    }
}
