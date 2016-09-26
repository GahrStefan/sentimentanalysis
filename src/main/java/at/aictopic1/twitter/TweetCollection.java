package at.aictopic1.twitter;

import java.util.List;

/**
 *
 */
public class TweetCollection {
    private int size;
    private List<Tweet> tweets;
    private int currentLine;
    private double result;
    private double positives;
    private double negatives;
    private double neutrals;
    private String customer;
    private String algorithm;

    public TweetCollection(List<Tweet> tweets, int currentLine){
        this.tweets = tweets;
        this.currentLine = currentLine;
        this.size = tweets.size();
    }

    public int getSize(){ return this.size;}

    public List<Tweet> getTweets(){return this.tweets;}

    public int getCurrentLine(){return this.currentLine;}

    public double getResult() {return this.result;}
    public void setResult(double result) {this.result = result;}

    public String getCustomer(){return this.customer;}
    public void setCustomer(String customer) {this.customer = customer;}

    public double getPositives() {
        return positives;
    }

    public void setPositives(double positives) {
        this.positives = positives;
    }

    public double getNegatives() {
        return negatives;
    }

    public void setNegatives(double negatives) {
        this.negatives = negatives;
    }

    public double getNeutrals() {
        return neutrals;
    }

    public void setNeutrals(double neutrals) {
        this.neutrals = neutrals;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
