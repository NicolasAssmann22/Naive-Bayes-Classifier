import java.util.HashMap;
import java.util.Map;


/**
 * Represents the training data for a specific class in the Naive Bayes Classifier.
 */
public class ClassData
{
    private String directory;
    private int trainingDocumentCounter;
    Map<String, Integer> wordCount = new HashMap<>();

    /**
     * Constructs a ClassData object with the specified directory.
     *
     * @param directory The directory representing the class.
     */
    public ClassData(String directory) {
        this.directory = directory;
        trainingDocumentCounter = 0;
    }


    /**
     * Adds a document to the ClassData object for training purposes.
     * Replaces every non-letter with a whitespace.
     * @param document The document to be added.
     */
    public void addDocuments(String document)
    {
        trainingDocumentCounter++;

        document = document.replaceAll("[^a-zA-Z]", " ").toLowerCase();


        String[] words = document.split("\\s+");
        Map<String, Integer> lineWordCount = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                lineWordCount.put(word, lineWordCount.getOrDefault(word, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : lineWordCount.entrySet()) {
            String word = entry.getKey();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
    }

    /**
     * Calculates the probability of a document, based of the words in the documents and the training data.
     *
     * @param document The document for which the probability is calculated.
     * @return The probability of the document belonging to this class.
     */
    public double calculateProbability(String document)
    {
        String[] words = document.split("\\s+");
        double probability = 1;
        for(String word : words)
        {
            double p = calculateWordProbability(word);
            probability *= p;

        }
        return probability;
    }

    /**
     * Calculates the probability of a word based on the training data using Laplace smoothing.
     * Laplace smoothing is applied to avoid zero probabilities for unseen words.
     *
     * @param word The word for which the probability is calculated.
     * @return The probability of the word based on the training data.
     */
    public double calculateWordProbability(String word) {
        if (wordCount.containsKey(word)) {
            return ((double) wordCount.get(word) + 1) / (trainingDocumentCounter + 2);
        } else {
            return (double) 1 / (trainingDocumentCounter + 2);
        }
    }

    /**
     * Gets the directory of this class.
     *
     * @return The directory representing the class.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Gets the number of training documents for this class.
     *
     * @return The number of training documents.
     */
    public int getTrainingDocumentCounter() {
        return trainingDocumentCounter;
    }

    /**
     * Gets the word count map for this class.
     *
     * @return The word count map.
     */
    public Map<String, Integer> getWordCount() {
        return wordCount;
    }
}

