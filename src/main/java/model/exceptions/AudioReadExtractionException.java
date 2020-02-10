package model.exceptions;

import model.enums.AudioExtractionExceptionType;
import model.enums.AudioReadExtractionExceptionType;

public class AudioReadExtractionException extends AudioExtractionException {

    private AudioReadExtractionExceptionType readExtractionExceptionType;

    public AudioReadExtractionException(AudioReadExtractionExceptionType subtype, String message, Throwable reason) {
        super(AudioExtractionExceptionType.ReadRawAudioException, message, reason);
        readExtractionExceptionType = subtype;
    }

    public AudioReadExtractionException(AudioReadExtractionExceptionType subtype, String message) {
        super(AudioExtractionExceptionType.ReadRawAudioException, message);
        readExtractionExceptionType = subtype;
    }

    public AudioReadExtractionExceptionType getReadExtractionExceptionType() {
        return readExtractionExceptionType;
    }
}
