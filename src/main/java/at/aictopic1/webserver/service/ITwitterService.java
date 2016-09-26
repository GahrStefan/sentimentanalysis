package at.aictopic1.webserver.service;

import at.aictopic1.twitter.TweetCollection;
import at.aictopic1.twitter.TwitterConfiguration;

import java.util.List;

/**
 * 
 */
public interface ITwitterService {
    public void addCustomer(String name);
    public void removeCustomer(String name);
    public List<String> getAllCustomers();

    public void addKeyword(String customer, String keyword);
    public void removeKeyword(String customer, String keyword);
    public List<String> getAllKeywords(String customer);

    public TwitterConfiguration getConfiguration();
    public void setConfig(TwitterConfiguration configuration);

    public void startCollecting();
    public void stopCollecting();
    public String status();
    public List<String> getLastTweets();

    public TweetCollection analyze(String customer);
}
