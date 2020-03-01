package testutils;

import java.nio.file.Paths;

public class TestingConstants {

    public final static double EPS_CONSTANT = 0.00000001;

    public final static int TEST_FREQUENCY_RATE = 22050;

    public final static int TEST_NFFT = 1102;

    public final static String TEST_RESOURCES_PATH = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
    public final static String TEST_SAMPLE = TEST_RESOURCES_PATH + "\\AudioTest.wav";
    public final static String TEST_SAMPLE_KNIFE = TEST_RESOURCES_PATH + "\\knife.wav";
    public final static String TEST_SAMPLE_KNIFE_READ = TEST_RESOURCES_PATH + "\\knife_samples.text";

    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\CurrentAudioSliceSample.txt";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\FFTCurrentAudioSliceSample.txt";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_FFT_P_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\FFTPreviousAudioSliceSample.txt";

    public final static String TEST_SAMPLE_SHORT_FEATURE = TEST_RESOURCES_PATH + "\\short_feature.text";
    public final static String TEST_SAMPLE_MID_FEATURE = TEST_RESOURCES_PATH + "\\mid_feature.text";
    public final static String TEST_SAMPLE_FEATURES = TEST_RESOURCES_PATH + "\\mid_feature_mean.text";


    //Mocked values for the whole AudioFeatures.
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
    public final static double TEST_AF_MFCC_1 = 0.511;
    public final static double TEST_AF_MFCC_2 = 0.512;
    public final static double TEST_AF_MFCC_3 = 0.513;
    public final static double TEST_AF_MFCC_4 = 0.514;
    public final static double TEST_AF_MFCC_5 = 0.515;
    public final static double TEST_AF_MFCC_6 = 0.516;
    public final static double TEST_AF_MFCC_7 = 0.517;
    public final static double TEST_AF_MFCC_8 = 0.518;
    public final static double TEST_AF_MFCC_9 = 0.519;
    public final static double TEST_AF_MFCC_10 = 0.511;
    public final static double TEST_AF_MFCC_11 = 0.512;
    public final static double TEST_AF_MFCC_12 = 0.513;
    public final static double TEST_AF_MFCC_13 = 0.514;


    //Real values
    public static final double TEST_AUDIO_ZCR_VALUE = 0.10435571687840291;
    public static final double TEST_AUDIO_ENERGY_VALUE = 0.017980906860628365;
    public static final double TEST_AUDIO_ENERGY_ENTROPY_VALUE = 0.0033633506543576165;
    public static final double TEST_AUDIO_SPECTRAL_CENTROID_VALUE = 0.08274951261117233;
    public static final double TEST_AUDIO_SPECTRAL_SPREAD_VALUE = 305.10231623145324;
    public static final double TEST_AUDIO_SPECTRAL_ENTROPY_VALUE = 5.949076192249108E-7;
    public static final double TEST_AUDIO_SPECTRAL_FLUX_VALUE = 77.0107192993164;
    public static final double TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE = 2.1875555375202166E-5;
    public static final double TEST_AUDIO_MFCCS_1 = 5.821354835973125E-4;
    public static final double TEST_AUDIO_MFCCS_2 = 4.952917855229025E-4;
    public static final double TEST_AUDIO_MFCCS_3 = 5.311981733152024E-4;
    public static final double TEST_AUDIO_MFCCS_4 = 6.283995438599152E-4;
    public static final double TEST_AUDIO_MFCCS_5 = 5.651391912323288E-4;
    public static final double TEST_AUDIO_MFCCS_6 = 4.701221411897436E-4;
    public static final double TEST_AUDIO_MFCCS_7 = 3.743868194459797E-4;
    public static final double TEST_AUDIO_MFCCS_8 = 1.331144927596793E-4;
    public static final double TEST_AUDIO_MFCCS_9 = -2.0828153199856634E-4;
    public static final double TEST_AUDIO_MFCCS_10 = -4.896850007259706E-4;
    public static final double TEST_AUDIO_MFCCS_11 = -5.750104673560285E-4;
    public static final double TEST_AUDIO_MFCCS_12 = -5.058110683553845E-4;
    public static final double TEST_AUDIO_MFCCS_13 = -5.858398045672257E-4;


    /**
     * 5.821354835973125E-4
     * 4.952917855229025E-4
     * 5.311981733152024E-4
     * 6.283995438599152E-4
     * 5.651391912323288E-4
     * 4.701221411897436E-4
     * 3.743868194459797E-4
     * 1.331144927596793E-4
     * -2.0828153199856634E-4
     * -4.896850007259706E-4
     * -5.750104673560285E-4
     * -5.058110683553845E-4
     * -5.858398045672257E-4
     */


}
