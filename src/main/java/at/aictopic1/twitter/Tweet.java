package at.aictopic1.twitter;

import java.util.Date;

public class Tweet {
    //Tweet Message

    //TODO find out what fields from JSON data we need and add
    //class fields accordingly. 
    // ***************************************************************
    // use twitter4j.status interface
    // ***************************************************************
    //
    //the JSON data we have can be read in lessTweets.txt 
    //(only the first lines extracted from tweets.txt file from TUWEL
    // so that we can open it in a text editor)
    private final String text;
    private final Date created_at;
    private final int user_follower_count;
    private final String user_name;
    
    private String preprocessedText;
    
    private double classified;
    private String classification;

    public Tweet(String text){
        this.text = text;
        this.created_at = new Date();
        this.user_follower_count = 0;
        this.user_name="";
    }

    public Tweet(String text, Date created_at, int user_follower_count, String user_name) {
        this.text = text;
        this.created_at = created_at;
        this.user_follower_count = user_follower_count;
        this.user_name=user_name;
    }

    public String getText() {
        return this.text;
    }

    public Date getCreatedAt() {
        return this.created_at;
    }
    
    public int getUserFollower() {
        return this.user_follower_count;
    }

    public String getUserName() {
        return this.user_name;
    }
    
    public String getPreprocessedText() {
        return this.preprocessedText;
    }

    public void setPreprocessedText(String text) {
        this.preprocessedText = text;
    }
    
    public String getClassification() {
        return this.classification;
    }
    
    public void setClassification(String classification) {
        this.classification=classification;
    }
    
    public double getClassified() {
        return this.classified;
    }
    
    public void setClassified(double classified) {
        this.classified = classified;
    }
}
