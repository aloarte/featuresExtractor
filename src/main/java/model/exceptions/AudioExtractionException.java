package model.exceptions;

import model.enums.AudioExtractionExceptionType;

public class AudioExtractionException extends Exception {

    private AudioExtractionExceptionType extractionExceptionType;

    public AudioExtractionException(AudioExtractionExceptionType type, String message, Throwable cause) {
        super(message, cause);
        extractionExceptionType = type;
    }

    public AudioExtractionException(AudioExtractionExceptionType type, String message) {
        super(message);
        extractionExceptionType = type;
    }


    public AudioExtractionExceptionType getExtractionExceptionType() {
        return extractionExceptionType;
    }
}
