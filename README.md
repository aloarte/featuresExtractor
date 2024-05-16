[![Tests](https://github.com/aloarte/featuresExtractor/actions/workflows/ci.yml/badge.svg)](https://github.com/aloarte/featuresExtractor/actions/workflows/ci.yml)
[![Coverage Status](https://codecov.io/github/aloarte/featuresExtractor/coverage.svg?branch=master)](https://app.codecov.io/gh/aloarte/featuresExtractor)

# Audio Features Extractor
Extract several audio features from audio sources.

# <img src="java_icon.jpg" align="left" height="100"/> A Java library for audio feature extraction

*This doc contains general info. Click [here](https://github.com/aloarte/featuresExtractor/wiki) for the complete wiki*

## General



AudioFeaturesExtractor is a Java library (.jar) which performs the extraction of several audio features from different audio inputs. 

#### Behavior

The general process of audio features extraction follows this steps:

* Crop the raw input audio in chunks, calculating the *Fast Fourier Transform* of each one.
* Get the audio features of this chunk of audio. 
* Once all the audio input is processed, resume the information performing statistical operations (mean, standard deviation, etc ).
* Parse the values and return the info.

#### Audio Features

This library obtain the following audio features:

* **Zero crossing rate:** The rate of sign-changes of the signal during the duration of a particular frame.
* **Energy:** The sum of squares of the signal values, normalized by the respective frame length.
* **Entropy of energy:**  The entropy of sub-frames' normalized energies. It can be interpreted as a measure of abrupt changes.
* **Spectral centroid:** he center of gravity of the spectrum.
* **Spectral spread:** The second central moment of the spectrum.
* **Spectral entropy:** Entropy of the normalized spectral energies for a set of sub-frames.
* **Spectral flux:** The squared difference between the normalized magnitudes of the spectra of the two successive frames.
* **Spectral rolloff:** The frequency below which 90% of the magnitude distribution of the spectrum is concentrated
* **MFCCs:** Mel Frequency Cepstral Coefficients
* **Chroma Vector:** A 12-element representation of the spectral energy where the bins represent the 12 equal-tempered pitch classes of western-type music (semitone spacing).
* **Chroma deviation:** The standard deviation of the 12 chroma coefficients.



## Usage

To use this library, import it into your project using maven or gradle.

[TBC]

Once is imported, use the facade.AudioFeaturesManager class to extract the audio features:

```java
 AudioFeaturesManager audioFeaturesManager = new AudioFeaturesManager();
        List<AudioFeatures> extractedFeatures = audioFeaturesManager.processAudioSource(rawDoubleAudioData, moduleParams);

```

#### Configuration

By default, this library have a default configuration stored in the ModuleParams class. You can modify the behavior of the audio extraction library by adding your configuration to the ModuleParams class.

## Further reading

This library is both based on  the Tyiannak PyAudioAnalisys [python library](https://github.com/tyiannak/pyAudioAnalysis) and in the ["Clasificador de subgéneros de música electrónica"](https://eprints.ucm.es/44672/1/TFG_2017-Clasificacion_subgeneros_musica_electronica.pdf) bachelor work. 

## News

## Authors
[Álvaro Loarte Rodríguez](https://github.com/aloarte)
[Antonio Caparrini Lopez](https://github.com/Caparrini)



