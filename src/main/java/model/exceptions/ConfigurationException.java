package model.exceptions;

import model.enums.AudioAnalysisExceptionType;

public class ConfigurationException extends AudioAnalysisException {
    public ConfigurationException(AudioAnalysisExceptionType type, String message, Throwable cause) {
        super(type, message, cause);
    }

    public ConfigurationException(AudioAnalysisExceptionType type, String message) {
        super(type, message);
    }
}
