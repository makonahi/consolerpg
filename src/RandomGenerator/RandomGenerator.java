package RandomGenerator;

import Printer.Printer;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;

import static java.lang.System.out;

public class RandomGenerator {

    public static final Random random = new Random();

    public static int generateGeometricNumber(int maxNumber) {

        double factor = 0.7; // Adjust this factor (between 0 and 1) to control the steepness of the drop-off
        double[] probabilities = new double[maxNumber];

        // Calculate the geometric probabilities
        double sum = 0;
        for (int i = 0; i < maxNumber; i++) {
            probabilities[i] = Math.pow(factor, i);
            sum += probabilities[i];
        }

        // Normalize the probabilities to sum to 1
        for (int i = 0; i < maxNumber; i++) {
            probabilities[i] /= sum;
        }

        // Generate a random number based on the calculated probabilities
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < maxNumber; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return i + 1; // Return the number (1 to maxNumber)
            }
        }

        return maxNumber; // Fallback, should not be reached
    }

    public void printProbabilitiesSpreadsheet(int maxNumber) {
        printProbabilitiesSpreadsheet(maxNumber, 10_000_000);
    }

    public void printProbabilitiesSpreadsheet(int maxNumber, int testCount){
        int[] numArrs=new int[maxNumber+1];
        for (int i=0;i<testCount;i++)
            numArrs[generateGeometricNumber(maxNumber)]++;
        for (int j=1;j<maxNumber+1;j++)
            out.printf("%.3f\n", numArrs[j]/testCount*100D);
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

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getObjectFromFile(String dir){
        File objFile, dirFile=new File(dir);
        objFile = getRandomElement(dirFile.listFiles());
        if (!objFile.exists()) {
            Printer.fileErrorWhileReading(objFile.getAbsolutePath(), "файл не найден");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFile))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Printer.fileErrorWhileReading(objFile.getAbsolutePath(), e.getMessage());
            return null;
        }
    }

}
