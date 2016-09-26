package at.aictopic1.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

/**
 *
 *
 */
public class AICTwitter {
    private final AICStream stream;
    private PropertiesReader _propertiesReader;
    private String jsonFile;

    private List<String> ignoreAccounts;

    public AICTwitter(){
        this._propertiesReader = PropertiesReader.getInstance();
        this.jsonFile = _propertiesReader.get("twitter.jsonfile");
        this.stream = new AICStream(jsonFile);
        this.stream.addLang(_propertiesReader.get("twitter.language"));
        this._propertiesReader = PropertiesReader.getInstance();
        this.ignoreAccounts = new ArrayList<String>();

    }

    public AICTwitter(String jsonFile, String lang) {
        this._propertiesReader = PropertiesReader.getInstance();
        this.jsonFile = jsonFile;
        this.stream = new AICStream(jsonFile);
        this.stream.addLang(lang);
        this.ignoreAccounts = new ArrayList<String>();
    }

    public void startDownload() {
        this.stream.startStream();
    }

    public void stopDownload(){
        this.stream.stopStream();
    }

    public void register(String company) {
        this.stream.addTrack(company);
    }

    public void unregister(String company) {
        this.stream.remTrack(company);
    }

    public List<String> getIgnoreAccounts() {
        return ignoreAccounts;
    }

    public void setIgnoreAccounts(List<String> ignoreAccounts) {
        this.ignoreAccounts = ignoreAccounts;
    }

    public List<Tweet> getTweets(String company) {
        List<String> keywords = new ArrayList<String>();
        keywords.add(company);

        return getTweets2(keywords, 0).getTweets();
    }

    public TweetCollection getTweets2 (List<String> keywords, int lineNum){
        List<Tweet> tweets = new ArrayList<Tweet>();
        boolean filter = (keywords.size() > 0);

        File file = new File(jsonFile);
        BufferedReader reader = null;
        int tmpLineNum = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if(tmpLineNum >= lineNum) {
                    Status s = TwitterObjectFactory.createStatus(line);

                    // ignore users
                    if(this.ignoreAccounts.contains(s.getUser().getName())) continue;

                    if(!filter) {
                        // no keywords, simple add all
                        tweets.add(new Tweet(s.getText(), s.getCreatedAt(), s.getUser().getFollowersCount(), s.getUser().getName()));
                    }else{
                        // filter by keywords
                        HashtagEntity[] tagsArray = s.getHashtagEntities();
                        for (HashtagEntity tag : tagsArray) {
                            for(String keyword : keywords) {
                                if (tag.getText().equalsIgnoreCase(keyword)) {
                                    tweets.add(new Tweet(s.getText(), s.getCreatedAt(), s.getUser().getFollowersCount(), s.getUser().getName()));
                                }
                            }
                        }
                    }
                }

                tmpLineNum++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AICTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AICTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TwitterException ex) {
            Logger.getLogger(AICTwitter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(AICTwitter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return new TweetCollection(tweets, tmpLineNum);
    }
}
