package model.exceptions;

import model.enums.AudioAnalysisExceptionType;
import model.enums.ProcessingExceptionType;

public class ProcessingException extends AudioAnalysisException {

    private ProcessingExceptionType audioProcessingSubtype;

    public ProcessingException(ProcessingExceptionType subtype, String message, Throwable reason) {
        super(AudioAnalysisExceptionType.AudioProcessing, message, reason);
        audioProcessingSubtype = subtype;
    }

    public ProcessingException(ProcessingExceptionType subtype, String message) {
        super(AudioAnalysisExceptionType.AudioProcessing, message);
        audioProcessingSubtype = subtype;
    }

    public ProcessingExceptionType getAudioProcessingSubtype() {
        return audioProcessingSubtype;
    }
}
