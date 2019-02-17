import data.AudioFeatures;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import utils.TestUtils;

public class FeaturesExtractorTest {

    /**
     * Test several audio sources to check if the output is always the same
     */
    @Test
    @Ignore
    public void fullFeaturesIntegrityTest(){

        //Extract the audio features
        AudioFeatures audioFeaturesExtracted = FeaturesExtractor.processAudioSource(loadAudioFromMP3File());

        //Load previous audio features from a file
        AudioFeatures controlAudioFeatures = TestUtils.loadAudioFeaturesFromFile();
        Assert.assertEquals(audioFeaturesExtracted,controlAudioFeatures);

        // ...

        // Assert audio input #2....

        // ...


    }

    /**
     * Load audio byte[] from an audio in mp3 format
     * @return
     */
    private byte[] loadAudioFromMP3File() {
        //Load audio input data from the file "audioSampled.mp3
        // "

        //Read and return the value
        return new byte[0];
    }
}
