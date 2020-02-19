package model.enums;

public enum AudioExtractionExceptionType {
    ReadRawAudioException,
    BadCurrentAudioSlice, BadCurrentFftAudioSlice, BadPreviousAudioSlice,
    BadExtractedFeaturesMatrix, WrongNumberOfFeaturesExtracted, fftShapesMismatch

}
