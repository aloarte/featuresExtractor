import org.junit.Before;
import org.junit.Test;
import testutils.WavUtils;

import java.io.File;
import java.io.IOException;

public class WavLoadTest {

    String pathFile;
    private WavUtils wavUtils;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();

    }

    @Test
    public void loadWavTest() throws IOException {
        String path = "G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\AudioTest.wav";
        String path2 = new File("src/test/resources/AudioTest.wav").getAbsolutePath();
        double[] samples = wavUtils.load_wav(path);

    }
}
