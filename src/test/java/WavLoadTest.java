import org.junit.Before;
import org.junit.Test;
import testutils.SamplesReaderUtils;
import testutils.TestUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.TEST_CONTROL_KNIFE_COMPLETE_WAV;
import static testutils.TestingConstants.TEST_KNIFE_301s_WAV;

public class WavLoadTest {

    String pathFile;
    private TestUtils testUtils;

    @Before
    public void startUp() {
        testUtils = new TestUtils();

    }

    @Test
    public void loadWavTest() throws IOException {
        double roundPrecision = 10000d;

        double[] samplesFromWav = testUtils.load_wav(TEST_KNIFE_301s_WAV);
        double[] samplesFromFile = SamplesReaderUtils.readSamplesFromFile(TEST_CONTROL_KNIFE_COMPLETE_WAV);

        assert samplesFromFile != null;

        assertThat(samplesFromFile.length, is(samplesFromWav.length));
        for (int i = 0; i < samplesFromFile.length; i++) {
            assertThat((double) Math.round(samplesFromFile[i] * roundPrecision) / roundPrecision, is((double) Math.round(samplesFromWav[i] * roundPrecision) / roundPrecision));
        }
    }
}
