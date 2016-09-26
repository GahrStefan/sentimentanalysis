package at.aictopic1.sentimentanalysis;

import at.aictopic1.twitter.TwitterHelper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Getting tweets
        String[] companies = {"apple","lego"};

        AppFunctionality app=new AppFunctionality();

        app.setNeutral(false);
        app.setClassifierNaiveBayesClassifier();
        /*
        app.setClassifierJ48();
        app.setClassifierNaiveBayesMultinomialClassifier();
        app.setClassifierRandomForestClassifier();
        app.setClassifierkNearestNeighbourClassifier();
        */
        
        app.setDistributeOn(true);
        boolean normalizeDocLength =false;
        int newWordsToKeep=100;
        boolean outputWordCounts=false;
        boolean tfidfTransform=false;
        app.parameterizeString2WordVector(newWordsToKeep, outputWordCounts, tfidfTransform, normalizeDocLength);

        for(String company : companies){
            System.out.println("Result for company " + company);

            app.setTweets(company);
            app.preprocess(company);

            app.classify();

            double [] results= TwitterHelper.evaluateClassified(app.tweets);
            double finalValue = results[0];
            int positive = (int) results[1];
            int neutral = (int) results[2];
            int negative = (int) results[3];

            //finalValue = sum/multiplierSum; // (sum/multiplierSum +1)/2 for 0..1

            System.out.println("\nFound " + positive + " positiv tweets.");
            System.out.println("Found " + negative + " negativ tweets.");
            System.out.println("Found " + neutral + " neutral tweets.");
            System.out.println("\nMean for related tweets: " + finalValue);
        }
        
    }
}
