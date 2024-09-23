package GameObject.RandomGenerator;

import java.util.Random;

import static java.lang.System.out;

public class RandomGenerator {

    public static final Random random = new Random();

    public static int generateGeometricNumber(int maxNumber) {

        double factor = 0.4;
        double[] probabilities = new double[maxNumber];

        double sum = 0;
        for (int i = 0; i < maxNumber; i++) {
            probabilities[i] = Math.pow(factor, i);
            sum += probabilities[i];
        }

        for (int i = 0; i < maxNumber; i++) {
            probabilities[i] /= sum;
        }

        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < maxNumber; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return i + 1;
            }
        }
        return maxNumber;
    }

    public static void printProbabilitiesSpreadsheet(int maxNumber){
        int[] numArrs=new int[maxNumber+1];
        for (int i=0;i<10_000_000;i++)
            numArrs[generateGeometricNumber(maxNumber)]++;
        for (int j=1;j<maxNumber+1;j++) {
            out.printf("%.3f%% \n", (numArrs[j] / 10_000_000D) * 100D);
        }
    }

    public static <T> T getRandomElement(T[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }


    public static <T> void shuffleArray(T[] arr)
    {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            T a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

}
