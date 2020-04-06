package model.exceptions;

import model.enums.AudioAnalysisExceptionType;
import model.enums.DataParseExceptionType;

public class DataParseException extends AudioAnalysisException {

    private DataParseExceptionType dataParserExceptionSubtype;

    public DataParseException(DataParseExceptionType subtype, String message, Throwable reason) {
        super(AudioAnalysisExceptionType.AudioProcessing, message, reason);
        dataParserExceptionSubtype = subtype;
    }

    public DataParseException(DataParseExceptionType subtype, String message) {
        super(AudioAnalysisExceptionType.AudioParsing, message);
        dataParserExceptionSubtype = subtype;
    }

    public DataParseExceptionType getDataParserExceptionSubtype() {
        return dataParserExceptionSubtype;
    }
}
