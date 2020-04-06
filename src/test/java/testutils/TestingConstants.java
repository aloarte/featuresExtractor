package testutils;

import java.nio.file.Paths;

import static constants.ModuleConstants.HIGH_FREQUENCY_RATE;
import static constants.ModuleConstants.RECOMMENDED_FREQUENCY_RATE;

public class TestingConstants {

    public final static double EPS_CONSTANT = 0.00000001;

    public final static int TEST_FREQUENCY_RATE = RECOMMENDED_FREQUENCY_RATE;

    public final static int TEST_HIGH_FREQUENCY_RATE = HIGH_FREQUENCY_RATE + 1;

    public final static int TEST_LOW_FREQUENCY_RATE = 5512;


    public final static int TEST_NFFT = 110;

    public final static String TEST_RESOURCES_PATH = Paths.get("src", "test", "resources").toFile().getAbsolutePath();
    public final static String TEST_RESOURCES_PATH_CONTROL_VALUES = TEST_RESOURCES_PATH + "\\control_values\\";
    public final static String TEST_RESOURCES_PATH_WAV_FILES = TEST_RESOURCES_PATH + "\\wav_files\\";


    public final static String TEST_KNIFE_301s_WAV = TEST_RESOURCES_PATH_WAV_FILES + "knife_301s_22050fr.wav";
    public final static String TEST_KNIFE_301s_CONTROL_VALUES_MEAN = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_301s_22050fr_MEAN";
    public final static String TEST_KNIFE_301s_CONTROL_VALUES_MIDTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_301s_22050fr_MIDTERM";
    public final static String TEST_KNIFE_301s_CONTROL_VALUES_SHORTTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_301s_22050fr_SHORTERM";

    public final static String TEST_KNIFE_30s_WAV = TEST_RESOURCES_PATH_WAV_FILES + "knife_30s_22050fr.wav";
    public final static String TEST_KNIFE_30s_CONTROL_VALUES_MEAN = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_30s_22050fr_MEAN";
    public final static String TEST_KNIFE_30s_CONTROL_VALUES_MIDTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_30s_22050fr_MIDTERM";
    public final static String TEST_KNIFE_30s_CONTROL_VALUES_SHORTTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_30s_22050fr_SHORTERM";

    public final static String TEST_KNIFE_10s_WAV = TEST_RESOURCES_PATH_WAV_FILES + "knife_10s_22050fr.wav";
    public final static String TEST_KNIFE_10s_CONTROL_VALUES_MEAN = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_10s_22050fr_MEAN";
    public final static String TEST_KNIFE_10s_CONTROL_VALUES_MIDTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_10s_22050fr_MIDTERM";
    public final static String TEST_KNIFE_10s_CONTROL_VALUES_SHORTTERM = TEST_RESOURCES_PATH_CONTROL_VALUES + "knife_10s_22050fr_SHORTERM";


    public final static String TEST_CONTROL_KNIFE_COMPLETE_WAV = TEST_RESOURCES_PATH_WAV_FILES + "knife_301s_22050_wav_data";

    public final static String TEST_SAMPLE_INDARRAY_C_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\CurrentAudioSlice_Knife_22220";
    public final static String TEST_SAMPLE_INDARRAY_FFT_C_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\FFTCurrentAudioSlice_Knife_22220";
    public final static String TEST_SAMPLE_INDARRAY_FFT_P_AUDIO_SLICE = TEST_RESOURCES_PATH + "\\FFTPreviousSlice_Knife_22220";

//    public final static String TEST_SAMPLE_SHORT_FEATURE = TEST_RESOURCES_PATH + "\\knife_22050fr_SHORTERM";
//    public final static String TEST_SAMPLE_MID_FEATURE = TEST_RESOURCES_PATH + "\\knife_22050fr_MIDTERM";
//    public final static String TEST_SAMPLE_FEATURES = TEST_RESOURCES_PATH + "\\knife_22050fr_MEAN";
//    public final static String TEST_SAMPLE_CONTROL_SLICE_PYTHON_FEATURES = TEST_RESOURCES_PATH + "\\PythonSliceControlFeatures.txt";


    //Mocked values for the whole AudioFeatures.
    public final static double TEST_AF_ZCR = 0.111;
    public final static double TEST_AF_EF_E = 0.311;
    public final static double TEST_AF_EF_EN = 0.321;
    public final static double TEST_AF_SF_C = 0.411;
    public final static double TEST_AF_SF_S = 0.421;
    public final static double TEST_AF_SF_E = 0.431;
    public final static double TEST_AF_SF_F = 0.441;
    public final static double TEST_AF_SF_R = 0.451;

    public final static double[] TEST_AF_CF_CV = new double[]{
            0.211,
            0.212,
            0.213,
            0.214,
            0.215,
            0.216,
            0.217,
            0.218,
            0.219,
            0.211,
            0.212,
            0.213
    };

    public final static double TEST_AF_CF_CE = 0.221;


    public final static double[] TEST_AF_MFCC = new double[]{
            0.511,
            0.512,
            0.513,
            0.514,
            0.515,
            0.516,
            0.517,
            0.518,
            0.519,
            0.511,
            0.512,
            0.513,
            0.514};


    public static final double TEST_AF_BPM_VALUE = 0.61;
    public static final double TEST_AF_BPM_DEVIATION = 0.62;

    //Real values
    public static final double TEST_AUDIO_ZCR_VALUE = 0.0365296803652968;
    public static final double TEST_AUDIO_ENERGY_VALUE = 0.04695406827059659;
    public static final double TEST_AUDIO_ENERGY_ENTROPY_VALUE = 2.874689817428589;
    public static final double TEST_AUDIO_SPECTRAL_CENTROID_VALUE = 0.15805630805031856;
    public static final double TEST_AUDIO_SPECTRAL_SPREAD_VALUE = 0.20868712678225765;
    public static final double TEST_AUDIO_SPECTRAL_ENTROPY_VALUE = 0.33811086416244507;
    public static final double TEST_AUDIO_SPECTRAL_FLUX_VALUE = 0.011840171180665493;
    public static final double TEST_AUDIO_SPECTRAL_ROLLOFF_VALUE = 0.03636363636363636;

    public final static double[] TEST_AUDIO_CHROMA_VECTORS = new double[]{
            6.962777115404606E-4,
            3.2503227703273296E-4,
            0.006819647271186113,
            3.5835062590194866E-5,
            4.236512759234756E-4,
            0.008156128227710724,
            4.2059771658387035E-5,
            9.486882481724024E-4,
            0.0018542396137490869,
            0.0013018104946240783,
            0.17083711922168732,
            9.45154344663024E-4
    };


    public static final double[] TEST_AUDIO_MFCCS = new double[]{
            -28.437641002860616,
            -1.7293699202989485,
            -1.5999499383818492,
            0.6990686767434052,
            0.648084711212224,
            0.7989769889152714,
            0.6526604877038747,
            0.014680582198975152,
            0.1349264680267024,
            0.5429387161693577,
            1.0617413941496274,
            1.5218708314532219,
            1.0741691074174549
    };


    public static final double TEST_SLICE_STATS_MEAN = 0.10977169126272202;
    public static final double TEST_SLICE_STATS_STD = 0.08966296911239624;


    public static final double TEST_AUDIO_BPM_VALUE = 0.04695406827059659;
    public static final double TEST_AUDIO_BPM_DEVIATION = 2.874689817428589;
}
