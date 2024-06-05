import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NaiveBayesClassifier {

    private final ClassData classDataA;
    private final ClassData classDataB;

    public NaiveBayesClassifier(String classAFile, String classBFile) {
        classDataA = train(classAFile);
        classDataB = train(classBFile);
    }

    public void classifieDocument(String file)
    {
        file = "trainingData/" + file;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder documentBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                documentBuilder.append(line).append("\n");
            }
            String document = documentBuilder.toString();
            document = deleteUnclassifiedWords(document);
            double probabilityA = calculateTotalProbability(classDataA, document);
            double probabilityB =  calculateTotalProbability(classDataB, document);
            System.out.println("probability " + classDataA.getFileName() +  ": " + probabilityA +
                    "\nprobability " + classDataB.getFileName() +  ": " +  probabilityB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String deleteUnclassifiedWords(String document)
    {

        Map<String, Integer> wordListA =  classDataA.getWordCount();
        Map<String, Integer> wordListB =  classDataB.getWordCount();
        String[] words = document.split("\\s+");

        StringJoiner sj = new StringJoiner(" ");

        for (String word : words) {
            if (wordListA.containsKey(word.toLowerCase()) || wordListB.containsKey(word.toLowerCase()) ) {
                sj.add(word);
            }
        }
        String result = sj.toString();
        System.out.println("Gültige Wörter: " + result);

        return result;
    }

    private ClassData train(String fileName)
    {
        ClassData classData = new ClassData(fileName);

        fileName = "trainingData/" + fileName;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                classData.countDocuments(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classData;
    }

    private double calculateTotalProbability(ClassData classData, String document)
    {
        return calculateClassProbability(classData) * classData.calculateProbability(document);
    }

    private double calculateClassProbability(ClassData classData)
    {
        return (double) classData.getTrainingDocumentCounter() / (classDataA.getTrainingDocumentCounter() + classDataB.getTrainingDocumentCounter());
    }
}

class ClassData
{
    private String fileName;
    private int trainingDocumentCounter;
    Map<String, Integer> wordCount = new HashMap<>();

    public ClassData(String fileName) {
        this.fileName = fileName;
        trainingDocumentCounter = 0;
    }

    public void countDocuments(String line)
    {
        trainingDocumentCounter ++;

        String[] words = line.split("\\s+");
        Map<String, Integer> lineWordCount = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();
            if (!word.isEmpty()) {
                if (!lineWordCount.containsKey(word)) {
                    lineWordCount.put(word, 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : lineWordCount.entrySet()) {
            String word = entry.getKey();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
    }

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

    private double calculateWordProbability(String word) {
        if (wordCount.containsKey(word)) {
            return ((double) wordCount.get(word) + 1) / (trainingDocumentCounter + 2);
        } else {
            return (double) 1 / (trainingDocumentCounter + 2);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public int getTrainingDocumentCounter() {
        return trainingDocumentCounter;
    }

    public Map<String, Integer> getWordCount() {
        return wordCount;
    }
}
