/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis;

import at.aictopic1.sentimentanalysis.machinelearning.impl.BasicClassifier;
import at.aictopic1.sentimentanalysis.preprocessor.TwitterPreprocessor;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.IPreprocessor;
import at.aictopic1.twitter.AICTwitter;
import at.aictopic1.twitter.Tweet;

import static java.lang.Math.log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 
 */
public class Console {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        String[] cmd;
        List<Tweet> exampleTweets = new ArrayList<Tweet>();
        System.out.print("AIC:> ");

        while ((s = in.readLine()) != null && s.length() != 0) {
            cmd = s.split(" ");
            if (cmd[0].equalsIgnoreCase("register")) {
                AICTwitter twitter = new AICTwitter();
                twitter.register(cmd[1]);
                twitter.startDownload();
                System.out.println(cmd[1] + " registered successfully.");
            }

            if (cmd[0].equalsIgnoreCase("show")) {
                AICTwitter twitter = new AICTwitter();
                List<Tweet> tweets = twitter.getTweets(cmd[1]);
                for (int i = tweets.size() - 1, j = 0; j < 5; i--, j++) {
                    Tweet tweet = tweets.get(i);
                    System.out.print("[" + tweet.getCreatedAt() + "] ");
                    System.out.print(tweet.getUserName() + ": ");
                    System.out.println(tweet.getText());
                }
            }

            if (cmd[0].equalsIgnoreCase("store")) {
                AICTwitter twitter = new AICTwitter();
                List<Tweet> tweets = twitter.getTweets(cmd[1]);
                for (int i = tweets.size() - 1, j = 0; j < 5; i--, j++) {
                    Tweet tweet = tweets.get(i);
                    exampleTweets.add(tweet);
                    System.out.print("[" + tweet.getCreatedAt() + "] ");
                    System.out.print(tweet.getUserName() + ": ");
                    System.out.print(tweet.getText());
                    System.out.println("\n\n" + exampleTweets.size() + " tweets stored.");
                }
            }

            if (cmd[0].equalsIgnoreCase("preprocess")) {
                IPreprocessor tp = new TwitterPreprocessor("en");
                for (Tweet t : exampleTweets) {
                    t = tp.preprocess(t, new ArrayList<String>());
                    System.out.println("----");
                    System.out.println(t.getText());
                    System.out.println(t.getPreprocessedText());
                }
            }

            if (cmd[0].equalsIgnoreCase("classify")) {
                BasicClassifier bc = new BasicClassifier();
                bc.classify(exampleTweets);
                for (Tweet t : exampleTweets) {
                    System.out.print("[" + t.getCreatedAt() + "] ");
                    System.out.print(t.getText() + ": ");
                    System.out.println(t.getClassified());
                }
            }

            if (cmd[0].equalsIgnoreCase("result")) {
                double sum = 0;
                int positiv = 0;
                int negativ = 0;
                for (Tweet t : exampleTweets) {
                    double interimValue = t.getClassified();
                    double multiplier;
                    if (t.getUserFollower() >= 500) {
                        multiplier = 1;
                    } else if (t.getUserFollower() <= 1) {
                        multiplier = 0.1;
                    } else {
                        multiplier = log(t.getUserFollower()) / log(500);
                    }
                    sum += interimValue * multiplier;
                    if (t.getClassified() == 1) {
                        positiv++;
                    } else {
                        negativ++;
                    }
                }
                double finalValue = sum / exampleTweets.size();

                System.out.println("Found " + positiv + " positiv tweets.");
                System.out.println("Found " + negativ + " negativ tweets.");
                System.out.println("\nMean for related tweets: " + finalValue);
            }

            if (cmd[0].equalsIgnoreCase("help")) {
                System.out.println("Usage:");
                System.out.println("register <company>: add new company to filter");
                System.out.println("show <company>: shows last 5 tweets from this company");
                System.out.println("store <company>: stores 5 example tweets for demo");
                System.out.println("preprocess: preprocesses the twitter text");
                System.out.println("classify: classifies the preprocessed twitter text");
                System.out.println("result: shows the result over all tweets");
                System.out.println("help: shows this help");
                System.out.println("exit: quits console");
            }

            if (cmd[0].equalsIgnoreCase("exit")) {
                break;
            }
            System.out.print("AIC:> ");
        }
        // An empty line or Ctrl-Z terminates the program
    }

    private String register(String company) {
        return "Hello World!";
    }
}
