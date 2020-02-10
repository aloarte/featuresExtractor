import fr.delthas.javamp3.Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.Thread.sleep;

public class TestApp {

    public static void convertFloatToByteArray(float f, byte[] b, int offset) {
        ByteBuffer.wrap(b, offset, 4).order(ByteOrder.LITTLE_ENDIAN).putFloat(f);
    }

    public static float convertByteArrayToFloat(byte[] b, int offset) {
        return ByteBuffer.wrap(b, offset, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static void playBytes(byte[] bytes, AudioFormat format) {
        int samples = bytes.length / 2;

        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(bytes), format, samples);
            clip.open(stream);
            clip.start();
            delay(3000);
            clip.stop();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playAudio(Path audio_path) {
        try (Sound sound = new Sound(new BufferedInputStream(Files.newInputStream(audio_path)))) {
            // no need to buffer the SoundInputStream

            // get sound metadata
            System.out.println(sound.getSamplingFrequency());

            // let's copy the decoded data samples into a file!
            System.out.println(sound.getAudioFormat());

            ByteArrayOutputStream os = new ByteArrayOutputStream();


            int read = sound.decodeFullyInto(os);
            // A sample takes 2 bytes
            int samples = read / 2;
            // Java sound API stuff ...
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(os.toByteArray()), sound.getAudioFormat(), samples);
            clip.open(stream);
            clip.start();

            System.out.println("Hello world");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static float[] floatMe(short[] pcms) {
        float[] floaters = new float[pcms.length];
        for (int i = 0; i < pcms.length; i++) {
            floaters[i] = pcms[i] / 32768.0f;
        }
        return floaters;
    }

    public static short[] shortMe(byte[] bytes) {
        short[] out = new short[bytes.length / 2]; // will drop last byte if odd number
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        for (int i = 0; i < out.length; i++) {
            out[i] = bb.getShort();
        }
        return out;
    }

    public static float[] byte2float(byte[] bytes) {
        return floatMe(shortMe(bytes));
    }

    public static byte[] short2byte(short[] shorts) {
        byte[] bytes = new byte[shorts.length * 2];
        for (int i = 0; i < shorts.length; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.putShort(shorts[i]);
            byte[] aux = buffer.array();
            bytes[2 * i] = aux[0];
            bytes[2 * i + 1] = aux[1];
        }
        return bytes;
    }

    public static short[] float2short(float[] floats) {
        short[] shorts = new short[floats.length];
        for (int i = 0; i < floats.length; i++) {
            shorts[i] = (short) (floats[i] * 32768);
        }
        return shorts;
    }

    public static byte[] float2byte(float[] floats) {
        byte[] bytes = null;
        bytes = short2byte(float2short(floats));
        return bytes;
    }


    public static void delay(int ms) {
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        /**
//         * Example: How to transform mp3 audio into INDArray of features
//         */
//        // Path of the wav input file
//        String path = "G:\\JavaProjects\\featuresExtractor\\appjava\\src\\main\\resources\\AudioSample.wav";
//        // Transform the input file into a float[] array
//        double[] samples = load_wav(path);
//        // Extract globalFeatures
//        INDArray features = AudioFeaturesExtractorC.globalFeatureExtraction(samples,22050,2205,2205, 2205, 2205);
//
//        System.out.print(features);
//
//
//    }
}