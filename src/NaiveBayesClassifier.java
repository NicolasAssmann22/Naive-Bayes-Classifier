import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 *
 */

public class NaiveBayesClassifier {

    private final ClassData classDataNeg;
    private final ClassData classDataPos;


    /**
     * Constructs a Naive Bayes Classifier object.
     * Initializes the classifier by training it with the "neg" and "pos" directories
     * containing the negative and positive training data, respectively.
     */
    public NaiveBayesClassifier() {
        classDataNeg = train("neg");
        classDataPos = train("pos");
    }

    /**
     * Classifies a document based on the given training data.
     * This method reads the file from the "trainingData/unclassified" directory,
     * cleans it from unclassified words, and calculates the probabilities
     * for two classes (Positive and Negative). Based on these probabilities,
     * it determines which class has the higher probability.
     *
     * @param file The filename of the file in the "trainingData/unclassified" directory.
     */

    public void classifieDocument(String file)
    {

        file = "trainingData/unclassified/" + file;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder documentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                documentBuilder.append(line).append("\n");
            }
            String document = documentBuilder.toString();
            document = deleteUnclassifiedWords(document);
            double probabilityA = calculateTotalProbability(classDataNeg, document);
            double probabilityB =  calculateTotalProbability(classDataPos, document);
            System.out.println("probability " + classDataNeg.getDirectory() +  ": " + probabilityA +
                    "\nprobability " + classDataPos.getDirectory() +  ": " +  probabilityB);
            if(probabilityA<probabilityB) System.out.println("probability for class pos is higher");
            else System.out.println("probability for class neg is higher");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * First replaces every non-letter with a whitespace.
     * Deletes all words from the document that are not classified by the test data.
     * The document is filtered so that only words present in the training data
     * (both negative and positive class word lists) are retained.
     * @param document The document to be processed, as a String.
     * @return A String containing only the classified words from the document.
     */

    public String deleteUnclassifiedWords(String document)
    {

        document = document.replaceAll("[^a-zA-Z]", " ").toLowerCase();

        Map<String, Integer> wordListA =  classDataNeg.getWordCount();
        Map<String, Integer> wordListB =  classDataPos.getWordCount();
        String[] words = document.split("\\s+");

        StringJoiner sj = new StringJoiner(" ");

        for (String word : words) {
            if (wordListA.containsKey(word.toLowerCase()) || wordListB.containsKey(word.toLowerCase()) ) {
                sj.add(word);
            }
        }

        return sj.toString();
    }

    /**
     * Trains a ClassData object using documents from the specified directory.
     * This method reads all text files from the given directory, processes their content,
     * and adds them to the ClassData object for training purposes.
     *
     * @param directory The name of the directory within "trainingData" containing the training documents.
     * @return A ClassData object containing the training data from the specified directory.
     */
    public ClassData train(String directory)
    {
        ClassData classData = new ClassData(directory);

        File directoryPath = new File("trainingData/" + directory);
        if (!directoryPath.isDirectory())
        {
            System.out.println("Error, directory for trainingdata " + directory + " not found");
            System.exit(-1);
        }

        File[] filesList = directoryPath.listFiles();
        if(filesList == null)
        {
            System.out.println("Error, directory " + directory + " is empty");
            System.exit(-1);
        }

        for (File file : filesList) {
            if (!file.getName().endsWith(".txt")) continue;

            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                classData.addDocuments(content);
            } catch (IOException e) {
                System.err.println("Error while reading the file " + file.getName() + ": " + e.getMessage());
            }

        }

        return classData;
    }

    /**
     * Calculates the total probability of a document belonging to a specific class.
     *
     * @param classData The ClassData object representing the class for which the probability is calculated.
     * @param document The document for which the probability is calculated.
     * @return The total probability of the document belonging to the specified class.
     */
    public double calculateTotalProbability(ClassData classData, String document)
    {
        return calculateClassProbability(classData) * classData.calculateProbability(document);
    }

    /**
     * Calculates the probability of a class based on the training data.
     *
     * @param classData The ClassData object representing the class for which the probability is calculated.
     * @return The probability of the class.
     */
    public double calculateClassProbability(ClassData classData)
    {
        return (double) classData.getTrainingDocumentCounter() / (classDataNeg.getTrainingDocumentCounter() + classDataPos.getTrainingDocumentCounter());
    }
}

