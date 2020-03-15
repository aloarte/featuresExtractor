package components;

import model.exceptions.AudioExtractionException;
import org.junit.Before;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import testutils.SamplesReaderUtils;

import static constants.FeaturesNumbersConstants.TOTAL_FEATURES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static testutils.TestingConstants.*;

public class FeaturesProcessorTest {

    private INDArray currentSliceData;
    private INDArray fftCurrentSliceData;
    private INDArray fftPreviousSliceData;
    private FeaturesProcessor SUT;

    @Before
    public void startUp() {
        SUT = new FeaturesProcessor();
//        currentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE);
//        fftCurrentSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE);
//        fftPreviousSliceData = INDArrayUtils.readINDArrayFromFile(TEST_SAMPLE_DOUBLE_INDARRAY_FFT_P_AUDIO_SLICE);
//
//        System.out.println("currentSliceData["+currentSliceData.shape()[0]+"]["+currentSliceData.shape()[1]+"]");
//        System.out.println("fftCurrentSliceData["+fftCurrentSliceData.shape()[0]+"]["+fftCurrentSliceData.shape()[1]+"]");
//        System.out.println("fftPreviousSliceData["+fftPreviousSliceData.shape()[0]+"]["+fftPreviousSliceData.shape()[1]+"]");


        currentSliceData = Nd4j.create(SamplesReaderUtils.readExtractedFeaturesData((TEST_SAMPLE_DOUBLE_INDARRAY_C_AUDIO_SLICE_CONTROL)));
        fftCurrentSliceData = Nd4j.create(SamplesReaderUtils.readExtractedFeaturesData((TEST_SAMPLE_DOUBLE_INDARRAY_FFT_C_AUDIO_SLICE_CONTROL)));
        fftPreviousSliceData = Nd4j.create(SamplesReaderUtils.readExtractedFeaturesData((TEST_SAMPLE_DOUBLE_INDARRAY_FFT_P_AUDIO_SLICE_CONTROL)));

        System.out.println("currentSliceData[" + currentSliceData.shape()[0] + "][" + currentSliceData.shape()[1] + "]");
        System.out.println("fftCurrentSliceData[" + fftCurrentSliceData.shape()[0] + "][" + fftCurrentSliceData.shape()[1] + "]");
        System.out.println("fftPreviousSliceData[" + fftPreviousSliceData.shape()[0] + "][" + fftPreviousSliceData.shape()[1] + "]");

    }


    @Test
    public void extractFeaturesFromSlice() throws AudioExtractionException {

        INDArray extractedFeatures = SUT.extractFeaturesFromSlice(currentSliceData, fftCurrentSliceData, fftPreviousSliceData, 22050, 110);
        assertNotNull(extractedFeatures);
        assertThat(extractedFeatures.shape()[0], is(TOTAL_FEATURES));
        assertThat(extractedFeatures.shape()[1], is(1));

        //Get the control features from the python file
        INDArray pythonFeatures = Nd4j.create(SamplesReaderUtils.readExtractedFeaturesData(TEST_SAMPLE_CONTROL_SLICE_PYTHON_FEATURES));


    }

    @Test
    public void extractZeroCrossingRate() {
        double zeroCrossingRate = SUT.extractZeroCrossingRate(currentSliceData);
        assertThat(zeroCrossingRate, is(TEST_AUDIO_ZCR_VALUE));

    }
}
