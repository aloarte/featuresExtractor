package libs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomUtils {


    /**
     * Detects peaks (calculates local minima and maxima) in the
     * vector <code>values</code>. The resulting list contains
     * maxima at the first position and minima at the last one.
     * <p>
     * Maxima and minima maps contain the indice value for a
     * given position and the value from a corresponding vector.
     * <p>
     * A point is considered a maximum peak if it has the maximal
     * value, and was preceded (to the left) by a value lower by
     * <code>delta</code>.
     *
     * @param values  Vector of values for whom the peaks should be detected
     * @param delta   The precedor of a maximum peak
     * @param indices Vector of indices that replace positions in resulting maps
     * @return List of maps (maxima and minima pairs) of detected peaks
     */
    public static List<Map<Integer, Double>> peak_detection(List<Double> values, Double delta, List<Integer> indices) {
        //assert(indices != null);
        //assert(values.size() != indices.size());

        Map<Integer, Double> maxima = new HashMap<Integer, Double>();
        Map<Integer, Double> minima = new HashMap<Integer, Double>();
        List<Map<Integer, Double>> peaks = new ArrayList<Map<Integer, Double>>();
        peaks.add(maxima);
        peaks.add(minima);

        Double maximum = 0.0;
        Double minimum = 0.0;
        Integer maximumPos = 0;
        Integer minimumPos = 0;

        boolean lookForMax = true;

        Integer pos = 0;
        for (Double value : values) {
            if (value > maximum) {
                maximum = value;
                maximumPos = indices.get(pos);
            }

            if (value < minimum) {
                minimum = value;
                minimumPos = indices.get(pos);
            }

            if (lookForMax) {
                if (value < maximum - delta) {
                    maxima.put(maximumPos, value);
                    minimum = value;
                    minimumPos = indices.get(pos);
                    lookForMax = false;
                }
            } else {
                if (value > minimum + delta) {
                    minima.put(minimumPos, value);
                    maximum = value;
                    maximumPos = indices.get(pos);
                    lookForMax = true;
                }
            }

            pos++;
        }

        return peaks;
    }


    /**
     * Detects peaks (calculates local minima and maxima) in the
     * vector <code>values</code>. The resulting list contains
     * maxima at the first position and minima at the last one.
     * <p>
     * Maxima and minima maps contain the position for a
     * given value and the value itself from a corresponding vector.
     * <p>
     * A point is considered a maximum peak if it has the maximal
     * value, and was preceded (to the left) by a value lower by
     * <code>delta</code>.
     *
     * @param values Vector of values for whom the peaks should be detected
     * @param delta  The precedor of a maximum peak
     * @return List of maps (maxima and minima pairs) of detected peaks
     */
    public static List<Map<Integer, Double>> peak_detection(List<Double> values, Double delta) {
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < values.size(); i++) {
            indices.add(i);
        }

        return peak_detection(values, delta, indices);
    }

}