package testutils;

import java.io.File;

public class TestingConstants {

    public final static double EPS_CONSTANT = 0.00000001;

    public final static String TEST_ABSOLUTE_RESOURCES_PATH = new File("src\\test\\resources").getAbsolutePath();

    // Path of the wav input file
    public final static String TEST_AUDIO_SAMPLE_WAV_PATH = TEST_ABSOLUTE_RESOURCES_PATH + "\\AudioSample.wav";

    public final static String TEST_SAMPLE = "G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\AudioTest.wav";

    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE = "G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\CurrentAudioSliceSample.txt";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE = "G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\FFTCurrentAudioSliceSample.txt";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_FFT_P_AUDIO_SLICE = "G:\\JavaProjects\\featuresExtractorGH\\src\\test\\resources\\FFTPreviousAudioSliceSample.txt";

    public final static double TEST_AF_ZCR = 0.111;

    public final static double TEST_AF_EF_E = 0.311;
    public final static double TEST_AF_EF_EN = 0.321;

    public final static double TEST_AF_SF_C = 0.411;
    public final static double TEST_AF_SF_S = 0.421;
    public final static double TEST_AF_SF_E = 0.431;
    public final static double TEST_AF_SF_F = 0.441;
    public final static double TEST_AF_SF_R = 0.451;

    public final static double TEST_AF_CF_CV_1 = 0.211;
    public final static double TEST_AF_CF_CV_2 = 0.212;
    public final static double TEST_AF_CF_CV_3 = 0.213;
    public final static double TEST_AF_CF_CV_4 = 0.214;
    public final static double TEST_AF_CF_CV_5 = 0.215;
    public final static double TEST_AF_CF_CV_6 = 0.216;
    public final static double TEST_AF_CF_CV_7 = 0.217;
    public final static double TEST_AF_CF_CV_8 = 0.218;
    public final static double TEST_AF_CF_CV_9 = 0.219;
    public final static double TEST_AF_CF_CV_10 = 0.211;
    public final static double TEST_AF_CF_CV_11 = 0.212;
    public final static double TEST_AF_CF_CV_12 = 0.213;
    public final static double TEST_AF_CF_CE = 0.221;


    public static final double TEST_AUDIO_ENERGY_VALUE = 0.017972755950900067;

    public static final double TEST_AUDIO_ENERGY_ENTROPY_VALUE = 0.003589624387873332;

    public static final double TEST_AUDIO_SPECTRAL_CENTROID_VALUE = 0.08358086981677823;
    public static final double TEST_AUDIO_SPECTRAL_SPREAD_VALUE = 304.5489028215897;
    public static final double TEST_AUDIO_SPECTRAL_ENTROPY_VALUE = 7.39471705284503E-7;
    public static final double TEST_AUDIO_SPECTRAL_FLUX_VALUE = 77.0106430053711;
    public static final double TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE = 2.185572259607687E-5;


}
