import data.AudioFeatures;

public class MainApplication {

    public static void main(String [] args){

        byte [] rawAudioSource = loadAudio("/USER/DATA/MUSIC/SONG");

        AudioFeatures features = FeaturesExtractor.processAudioSource(new byte[2]);

        //print audio features

    }


    /**
     * Get the audio from a file into an byte[] raw source
     * @param audioPath     Path where the audio file is
     * @return              Byte [] with the info
     */
    private static byte[] loadAudio(final String audioPath) {
        //Check the path
        if(audioPath!=null){
            //Open file

            //load into a byte []

            return new byte[0];
        }
        else{
            return null;
        }
    }
}
