package testutils;

import java.io.File;

public class TestingConstants {

    public final static double EPS_CONSTANT = 0.00000001;

    public final static String TEST_ABSOLUTE_RESOURCES_PATH = new File("src\\test\\resources").getAbsolutePath();

    // Path of the wav input file
    public final static String TEST_AUDIO_SAMPLE_WAV_PATH = TEST_ABSOLUTE_RESOURCES_PATH + "\\AudioSample.wav";

    public final static String TEST_SAMPLE = "G:\\JavaProjects\\featuresExtractor\\src\\test\\resources\\AudioTest.wav";


}
