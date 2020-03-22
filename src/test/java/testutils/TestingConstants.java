package testutils;

import java.nio.file.Paths;

public class TestingConstants {

    public final static double EPS_CONSTANT = 0.00000001;

    public final static int TEST_FREQUENCY_RATE = 22050;

    public final static int TEST_NFFT = 110;

    public final static String TEST_RESOURCES_PATH = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
    public final static String TEST_SAMPLE = TEST_RESOURCES_PATH + "\\AudioTest.wav";
    public final static String TEST_SAMPLE_KNIFE = TEST_RESOURCES_PATH + "\\knife.wav";
    public final static String TEST_SAMPLE_KNIFE_READ = TEST_RESOURCES_PATH + "\\knife_samples.text";

    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE_KNIFE_22220 = TEST_RESOURCES_PATH + "\\CurrentAudioSlice22220Knife";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE_KNIFE = TEST_RESOURCES_PATH + "\\FFTAudioSliceKnife";
    public final static String TEST_SAMPLE_DOUBLE_INDARRAY_PFFT_C_AUDIO_SLICE_KNIFE = TEST_RESOURCES_PATH + "\\previousFFTSliceKnife";

    public final static String TEST_SAMPLE_SHORT_FEATURE = TEST_RESOURCES_PATH + "\\short_feature.text";
    public final static String TEST_SAMPLE_MID_FEATURE = TEST_RESOURCES_PATH + "\\mid_feature.text";
    public final static String TEST_SAMPLE_FEATURES = TEST_RESOURCES_PATH + "\\mid_feature_mean.text";
    public final static String TEST_SAMPLE_CONTROL_SLICE_PYTHON_FEATURES = TEST_RESOURCES_PATH + "\\PythonSliceControlFeatures.txt";


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
    public static final double TEST_AUDIO_ZCR_VALUE = 0.03636363636363636;
    public static final double TEST_AUDIO_ENERGY_VALUE = 0.04695406827059659;
    public static final double TEST_AUDIO_ENERGY_ENTROPY_VALUE = 2.874689817428589;
    public static final double TEST_AUDIO_SPECTRAL_CENTROID_VALUE = 0.15805630805031856;
    public static final double TEST_AUDIO_SPECTRAL_SPREAD_VALUE = 0.20868712678225765;
    public static final double TEST_AUDIO_SPECTRAL_ENTROPY_VALUE = 0.33811086416244507;
    public static final double TEST_AUDIO_SPECTRAL_FLUX_VALUE = 0.011840171180665493;
    public static final double TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE = 0.03636363636363636;
    public static final double TEST_AUDIO_MFCCS_1 = -28.86255517506102;
    public static final double TEST_AUDIO_MFCCS_2 = -1.2354393376198858;
    public static final double TEST_AUDIO_MFCCS_3 = -1.8312703479389805;
    public static final double TEST_AUDIO_MFCCS_4 = 0.6518264577790077;
    public static final double TEST_AUDIO_MFCCS_5 = 0.8526818275001204;
    public static final double TEST_AUDIO_MFCCS_6 = 0.6133792078141663;
    public static final double TEST_AUDIO_MFCCS_7 = 0.6957112855826528;
    public static final double TEST_AUDIO_MFCCS_8 = 0.11897623126846145;
    public static final double TEST_AUDIO_MFCCS_9 = -0.017646815278058146;
    public static final double TEST_AUDIO_MFCCS_10 = 0.6221583996017679;
    public static final double TEST_AUDIO_MFCCS_11 = 1.1092659394905213;
    public static final double TEST_AUDIO_MFCCS_12 = 1.4013296137441476;
    public static final double TEST_AUDIO_MFCCS_13 = 1.1454753527108796;

}
