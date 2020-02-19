package testutils;

import java.io.File;

public class WavUtils {

    public double[] load_wav(String input_file) {
        double[] buffer = new double[1];

        try {
            // Open the wav file specified as input_file
            WavFile wavFile = WavFile.openWavFile(new File(input_file));

            // Display information about the wav file
            int n_frames = (int) wavFile.getNumFrames();

            // Get the number of audio channels in the wav file
            int numChannels = wavFile.getNumChannels();

            // Create a buffer of n_frames frames
            buffer = new double[n_frames * numChannels];

            int framesRead;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            do {
                // Read frames into buffer
                framesRead = wavFile.readFrames(buffer, n_frames);

                // Loop through frames and look for minimum and maximum value
                for (int s = 0; s < framesRead * numChannels; s++) {
                    if (buffer[s] > max) max = buffer[s];
                    if (buffer[s] < min) min = buffer[s];
                }
            }
            while (framesRead != 0);

            // Close the wavFile
            wavFile.close();

            // Output the minimum and maximum value
        } catch (Exception e) {
            System.err.println(e);
        }
        return buffer;
    }
}
