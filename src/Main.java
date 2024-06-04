public class Main {
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("usage: <File class A> <File class B>" );
            return;
        }

        NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier(args[0], args[1]);

        naiveBayesClassifier.classifieDocument("document.txt");

    }
}