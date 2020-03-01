import org.junit.Before;
import org.junit.Test;
import testutils.SamplesReaderUtils;
import testutils.WavUtils;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.TEST_SAMPLE_KNIFE;
import static testutils.TestingConstants.TEST_SAMPLE_KNIFE_READ;

public class WavLoadTest {

    String pathFile;
    private WavUtils wavUtils;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();

    }

    @Test
    public void loadWavTest() throws IOException {
        double roundPrecision = 10000d;

        double[] samplesFromWav = wavUtils.load_wav(TEST_SAMPLE_KNIFE);
        double[] samplesFromFile = SamplesReaderUtils.readSamplesFromFile(TEST_SAMPLE_KNIFE_READ);

        assert samplesFromFile != null;

        assertThat(samplesFromFile.length, is(samplesFromWav.length));
        for (int i = 0; i < samplesFromFile.length; i++) {
            assertThat((double) Math.round(samplesFromFile[i] * roundPrecision) / roundPrecision, is((double) Math.round(samplesFromWav[i] * roundPrecision) / roundPrecision));
        }
    }
}
