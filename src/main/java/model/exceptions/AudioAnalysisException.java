package model.exceptions;

import model.enums.AudioAnalysisExceptionType;

public abstract class AudioAnalysisException extends Exception {

    private AudioAnalysisExceptionType audioAnalysisExceptionType;

    public AudioAnalysisException(AudioAnalysisExceptionType type, String message, Throwable cause) {
        super(message, cause);
        audioAnalysisExceptionType = type;
    }

    public AudioAnalysisException(AudioAnalysisExceptionType type, String message) {
        super(message);
        audioAnalysisExceptionType = type;
    }


    public AudioAnalysisExceptionType getAudioAnalysisExceptionType() {
        return audioAnalysisExceptionType;
    }
}
