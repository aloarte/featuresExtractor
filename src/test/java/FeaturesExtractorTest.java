import model.AudioFeatures;
import model.ModuleParams;
import model.enums.StatisticalMeasureType;
import org.junit.Before;
import org.junit.Test;
import testutils.WavUtils;

import java.util.ArrayList;
import java.util.List;

import static testutils.TestingConstants.TEST_SAMPLE;

public class FeaturesExtractorTest {


    String pathFile;
    private WavUtils wavUtils;

    private FeaturesExtractor SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        SUT = new FeaturesExtractor();
    }

    @Test
    public void processAudioSource() throws Exception {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_SAMPLE);

        ModuleParams moduleParams = new ModuleParams();
        List<StatisticalMeasureType> measureTypes = new ArrayList<>();
        measureTypes.add(StatisticalMeasureType.MEAN);
        moduleParams.setStatisticalMeasures(measureTypes);
        List<AudioFeatures> extractedFeatures = SUT.processAudioSource(samples, moduleParams);
        System.out.println();

    }


}
