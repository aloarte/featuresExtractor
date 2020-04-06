package model.exceptions;

import model.enums.ConfigurationExceptionType;
import model.enums.DataParseExceptionType;
import model.enums.ExtractionExceptionType;
import model.enums.ProcessingExceptionType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ExceptionsTest {

    @Test
    public void configurationException() {
        ConfigurationException SUTConfigException = new ConfigurationException(ConfigurationExceptionType.IncompatibleWindowSizes, "Test error", new RuntimeException("Test Runtime Configuration error"));
        assertNotNull(SUTConfigException);
        assertThat(SUTConfigException.getConfigurationExceptionSubtype(), is(ConfigurationExceptionType.IncompatibleWindowSizes));
        assertThat(SUTConfigException.getMessage(), is("Test error"));
        assertThat(SUTConfigException.getCause().getMessage(), is("Test Runtime Configuration error"));
    }

    @Test
    public void dataParserException() {
        DataParseException SUTDataParserException = new DataParseException(DataParseExceptionType.IllegalElementNumber, "Test error", new RuntimeException("Test Runtime DataParser error"));
        assertNotNull(SUTDataParserException);
        assertThat(SUTDataParserException.getDataParserExceptionSubtype(), is(DataParseExceptionType.IllegalElementNumber));
        assertThat(SUTDataParserException.getMessage(), is("Test error"));
        assertThat(SUTDataParserException.getCause().getMessage(), is("Test Runtime DataParser error"));
    }

    @Test
    public void extractionException() {
        ExtractionException SUTExtractionException = new ExtractionException(ExtractionExceptionType.BadAudioSourceRead, "Test error", new RuntimeException("Test Runtime Extraction error"));
        assertNotNull(SUTExtractionException);
        assertThat(SUTExtractionException.getExtractionExceptionSubtype(), is(ExtractionExceptionType.BadAudioSourceRead));
        assertThat(SUTExtractionException.getMessage(), is("Test error"));
        assertThat(SUTExtractionException.getCause().getMessage(), is("Test Runtime Extraction error"));
    }

    @Test
    public void processingException() {
        ProcessingException SUTProcessingException = new ProcessingException(ProcessingExceptionType.BadCurrentAudioSlice, "Test error", new RuntimeException("Test Runtime Processing error"));
        assertNotNull(SUTProcessingException);
        assertThat(SUTProcessingException.getAudioProcessingSubtype(), is(ProcessingExceptionType.BadCurrentAudioSlice));
        assertThat(SUTProcessingException.getMessage(), is("Test error"));
        assertThat(SUTProcessingException.getCause().getMessage(), is("Test Runtime Processing error"));
    }
}
