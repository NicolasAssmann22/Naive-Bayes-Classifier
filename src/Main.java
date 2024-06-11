public class Main {
    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.out.println("usage: <Filename of the file for classification>" );
            return;
        }

        NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier();

        naiveBayesClassifier.classifieDocument(args[0]);

    }
}