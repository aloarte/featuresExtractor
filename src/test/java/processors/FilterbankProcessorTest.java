package processors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testutils.WavUtils;

import static testutils.TestingConstants.TEST_AUDIO_SAMPLE_WAV_PATH;

public class FilterbankProcessorTest {

    private WavUtils wavUtils;

    private FilterbankProcessor SUT;

    @Before
    public void startUp() {
        wavUtils = new WavUtils();
        int freqRate = 1; //TODO: Check a good value for the freqRate
        int nFFT = 1;   //TODO: Check a good value for nFFT
        SUT = new FilterbankProcessor(freqRate, nFFT);
    }

    @Ignore
    @Test
    public void stMFCC() {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_AUDIO_SAMPLE_WAV_PATH);

        //double[] mfccValues = SUT.stMFCC();
//
//        System.out.print(mfccValues);

        //TODO: Check what stMFCC function gets and returns and test its functionallity
    }

    @Ignore
    @Test
    public void mfccInitFilterBanks() {

        // Transform the input file into a float[] array
        double[] samples = wavUtils.load_wav(TEST_AUDIO_SAMPLE_WAV_PATH);

        //double[] mfccValues = SUT.mfccInitFilterBanks();
//
//        System.out.print(mfccValues);

        //TODO: Check what stMFCC function gets and returns and test its functionallity
    }
}
