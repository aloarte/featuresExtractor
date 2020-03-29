package model.exceptions;

import model.enums.AudioAnalysisExceptionType;
import model.enums.ConfigurationExceptionType;

public class ConfigurationException extends AudioAnalysisException {

    private ConfigurationExceptionType subtype;

    public ConfigurationException(ConfigurationExceptionType subtype, String message, Throwable cause) {
        super(AudioAnalysisExceptionType.ExtractionConfiguration, message, cause);
        this.subtype = subtype;
    }

    public ConfigurationException(ConfigurationExceptionType subtype, String message) {
        super(AudioAnalysisExceptionType.ExtractionConfiguration, message);
        this.subtype = subtype;
    }

    public ConfigurationExceptionType getConfigurationExceptionSubtype() {
        return subtype;
    }
}
