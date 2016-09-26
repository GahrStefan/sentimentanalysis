package at.aictopic1.sentimentanalysis.preprocessor.arffPreprocessing;

import at.aictopic1.twitter.Tweet;
import at.aictopic1.sentimentanalysis.preprocessor.TwitterPreprocessor;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.IPreprocessor;
import at.aictopic1.twitter.AICTwitter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class CreateArff {
    public static void main(String[] args) {
        String outputFile = "Other Stuff/bearbeiteTweets.txt";

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        AICTwitter twitter = new AICTwitter();

        String[] ignoreTwitterAccounts = {

                "apple",
                "microsoft",
                "google",
                "samsung",
                "wal-mart",
                "hp",
                "siemens",
                "oracle",
                "sap",
                "amazon",

                "lockheed martin",
                "mcdonalds",
                "burgerking",
                "ibm",
                "oemv",

                "Top 10 Mac App",
                "Top 10 iTunes U",
                "Top 10 Music Video",
                "Top 10 Podcast",
                "Top 100 Album Songs",
                "Top 10 AudioBooks",
                "Top 10 Movies",
                "Apple App Store",
                "iPhone Top AppStore ",
                "iTunes Top 10 Songs",

                "Google Sport News",
                "Google Health News",
                "Google Trending",

                "HP Enterprise UK&I",

                "Siemens India",

                "SAP Product Support",

                "Amazon",

                "McDonald's",

                "Burger King",

                "IBM ExperienceOne",
                "IBM Smarter Commerce"
        };

        List<String> ignoreList = new ArrayList<String>();
        for (String ignore : ignoreTwitterAccounts) {
            ignoreList.add(ignore);
        }

        twitter.setIgnoreAccounts(ignoreList);
        /*
        // Getting tweets
        //String company = "google";
        //      
        //not included: "lego"
        String companies[] ={
                    "apple",
                    "microsoft",
                    "google",
                    "samsung",
                    "wal-mart",
                    "hp",
                    "siemens",
                    "oracle",
                    "sap",
                    "amazon",
                    
                    "lockheed martin",
                    "mcdonalds",
                    "burgerking",
                    "ibm",
                    "oemv"
            };
        */

        String companies[] = {"apple"};

        for (String company : companies) {

            // Register new company
            twitter.register(company);

            List<Tweet> status = twitter.getTweets(company);

            StringBuilder sb = new StringBuilder();

            System.out.println("\nPreprocessing...");
            IPreprocessor tp = new TwitterPreprocessor("en");
            for (Tweet t : tweets) {
                t = tp.preprocess(t, new ArrayList<String>());
                char[] stringArray = t.getPreprocessedText().toCharArray();
                String newLine = "";

                for (int i = 0; i < stringArray.length; i++) {
                    switch (stringArray[i]) {
                        case ';':
                        case '\'':
                        case '\\':
                        case '\"':
                            newLine += "\\";
                            break;
                    }
                    newLine += String.valueOf(stringArray[i]).toString();
                }

                sb.append("\"" + newLine + "\", ");
                sb.append(System.lineSeparator());
            }

            String wholeText = sb.toString();
        /*
        BasicClassifier bc = new BasicClassifier();
        bc.classify(tweets);

        double multiplierSum = 0;
        double sum = 0;
        int positiv = 0;
        int negativ = 0;
        for (Tweet t : tweets) {
            double interimValue = t.getClassified();
            double multiplier;
            if (t.getUserFollower()>=500) {
                multiplier = 1;
            } else if(t.getUserFollower()<=1) {
                multiplier = 0.1;
            } else {
                multiplier = log(t.getUserFollower())/log(500);
            }
            multiplierSum += multiplier;
            sum += interimValue*multiplier;
            if (t.getClassified()==1) {
                positiv++;
            } else {
                negativ++;
            }
        }
        double finalValue = sum/multiplierSum;//tweets.size();

        System.out.println("Found " + positiv + " positiv tweets.");
        System.out.println("Found " + negativ + " negativ tweets.");
        System.out.println("\nMean for related tweets: " + finalValue);
                
                */


            FileOutputStream fop = null;
            File file;
            //saving
            try {

                file = new File(outputFile);
                fop = new FileOutputStream(file);

                // if file doesnt exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }

                // get the content in bytes
                byte[] contentInBytes = wholeText.getBytes();

                fop.write(contentInBytes);
                fop.flush();
                fop.close();

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fop != null) {
                        fop.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
