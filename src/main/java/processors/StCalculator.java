package processors;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import static org.nd4j.linalg.ops.transforms.Transforms.pow;


public class StCalculator {

    private double EPS_CONSTANT = 0.00000001;


    double[] stSpectralCentroidAndSpread(INDArray X, int frequency_rate) {
        //Computes spectral centroid of frame (given abs(FFT))
        double[] res = new double[2];
        INDArray ind = Nd4j.arange(1, X.length()).mul(frequency_rate / (2.0 * X.length()));

        INDArray Xt = X.dup();
        Xt = Xt.div(Xt.maxNumber());
        double NUM = (double) ind.mul(Xt.dup().reshape(Xt.shape()[1], Xt.shape()[0])).sumNumber();
        double DEN = (double) Xt.sumNumber() + EPS_CONSTANT;

        double C = NUM / DEN;
        double S = (double) pow(ind.sub(C), 2).mul(Xt.dup().reshape(Xt.shape()[1], Xt.shape()[0])).sumNumber() / DEN;

        res[0] = C / (frequency_rate / 2);
        res[1] = S / (frequency_rate / 2);
        return res;
    }

    double stSpectralRollOff(INDArray x, double c, int frequency_rate) {
        //Computes spectral roll-off
        double mC = 0;
        double totalEnergy = (double) pow(x, 2).sumNumber();
        int fftLength = x.length();
        double Thres = c * totalEnergy;
        //# Find the spectral rolloff as the frequency position where
        // the respective spectral energy is equal to c*totalEnergy
        INDArray CumSum = pow(x, 2).cumsum(0).add(EPS_CONSTANT);
        for (int i = 0; i < CumSum.length(); i++) {
            if (CumSum.getDouble(i) > Thres) {
                mC = CumSum.getDouble(i) / fftLength;
                i = CumSum.length();
            }
        }
        return mC;

    }

    double stSpectralFlux(INDArray X, INDArray Xprev) {
        //Compute spectral flux as the sum of square distances
        double sumX = (double) X.sumNumber() + X.length() * EPS_CONSTANT;
        double sumPrevX = (double) Xprev.sumNumber() + Xprev.length() * EPS_CONSTANT;
        double F = (double) pow(X.div(sumX).sub(Xprev.div(sumPrevX)), 2).sumNumber();
        return F;
    }

    double stEnergyEntropy(INDArray x, int numOfShortBlocks) {
        //Computes entropy of energy
        double Eol = (double) pow(x, 2).sumNumber(); //total frame energy
        int L = x.length();

        int subWinLength = (int) (Math.floor(L / numOfShortBlocks)); //subframe length
        System.out.println("Shape X: " + x.shapeInfoToString());
        if (L != subWinLength * numOfShortBlocks) {
            x = x.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        System.out.println("Shape X: " + x.shapeInfoToString());
        INDArray subWindows = x.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double) (pow(subWindows, 2).sumNumber()) / (Eol + EPS_CONSTANT);
        double Entropy = -s * Math.log(s + EPS_CONSTANT);
        return Entropy;
    }

    double stEnergy(INDArray x) {
        //Computes signal energy of frame
        return (double) (pow(x, 2).sumNumber()) / x.length();
    }

    double stZCR(INDArray x) {
        //Computes zero crossing rate of frame
        int count = x.length();
        int countz = 0;

        for (int i = 0; i < x.length() - 1; i++) {
            if ((x.getDouble(0) > 0 && x.getDouble(i + 1) <= 0)
                    ||
                    (x.getDouble(i) < 0 && x.getDouble(i + 1) >= 0)) {
                countz++;
            }
        }

        countz = countz / 2;

        return countz / (count - 1.);
    }

    double stSpectralEntropy(INDArray X, int numOfShortBlocks) {
        //Computes the spectral entropy
        int L = X.length(); //samples
        double EoL = (double) pow(X, 2).sumNumber(); //Total energy

        int subWinLength = (int) (Math.floor(L / numOfShortBlocks)); //subframe length
        System.out.println("Shape X: " + X.shapeInfoToString());
        if (L != subWinLength * numOfShortBlocks) {
            X = X.get(NDArrayIndex.interval(0, subWinLength * numOfShortBlocks));
        }
        System.out.println("Shape X: " + X.shapeInfoToString());
        INDArray subWindows = X.reshape(subWinLength, numOfShortBlocks).dup();
        double s = (double) (pow(subWindows, 2).sumNumber()) / (EoL + EPS_CONSTANT);
        double En = -s * Math.log(s + EPS_CONSTANT);
        return En;
    }

}
