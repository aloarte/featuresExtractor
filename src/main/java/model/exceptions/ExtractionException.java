package model.exceptions;

import model.enums.AudioAnalysisExceptionType;
import model.enums.ExtractionExceptionType;

public class ExtractionException extends AudioAnalysisException {

    private ExtractionExceptionType extractionExceptionSubtype;

    public ExtractionException(ExtractionExceptionType subtype, String message, Throwable reason) {
        super(AudioAnalysisExceptionType.AudioExtraction, message, reason);
        extractionExceptionSubtype = subtype;
    }

    public ExtractionException(ExtractionExceptionType subtype, String message) {
        super(AudioAnalysisExceptionType.AudioExtraction, message);
        extractionExceptionSubtype = subtype;
    }

    public ExtractionExceptionType getExtractionExceptionSubtype() {
        return extractionExceptionSubtype;
    }
}
