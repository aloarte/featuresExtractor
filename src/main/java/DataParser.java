import model.AudioFeatures;
import org.nd4j.linalg.api.ndarray.INDArray;

public class DataParser {

    AudioFeatures parseAudioFeatures(INDArray extractedFeatures) {
        return new AudioFeatures();
    }
}
