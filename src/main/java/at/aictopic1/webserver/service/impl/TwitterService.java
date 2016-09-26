package at.aictopic1.webserver.service.impl;

import at.aictopic1.sentimentanalysis.machinelearning.impl.BasicClassifier;
import at.aictopic1.sentimentanalysis.machinelearning.Classifier;
import at.aictopic1.twitter.Tweet;
import at.aictopic1.sentimentanalysis.machinelearning.ClassifierHelper;
import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import at.aictopic1.sentimentanalysis.preprocessor.TwitterPreprocessor;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.IPreprocessor;
import at.aictopic1.twitter.AICTwitter;
import at.aictopic1.twitter.TweetCollection;
import at.aictopic1.twitter.TwitterConfiguration;
import at.aictopic1.twitter.TwitterHelper;
import at.aictopic1.webserver.dao.ICustomerDAO;
import at.aictopic1.webserver.dao.impl.CustomerJavaDAO;
import at.aictopic1.webserver.service.ITwitterService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TwitterService implements ITwitterService {

    private ICustomerDAO customerDAO;
    private boolean running = false;
    private PropertiesReader _propertiesReader;
    private TwitterConfiguration configuration;

    private AICTwitter twitter;
    private IPreprocessor tp = new TwitterPreprocessor("en");
    private Classifier bc = new BasicClassifier();

    private int line = 0;

    private static TwitterService instance;

    public static TwitterService getInstance(){
        if(instance == null) instance = new TwitterService();
        return instance;
    }

    public TwitterService(){
        customerDAO = new CustomerJavaDAO();
        this._propertiesReader = PropertiesReader.getInstance();
        this.configuration = new TwitterConfiguration();
        initTwitter();
    }

    private void initTwitter(){
        twitter = new AICTwitter(this.configuration.getJsonFile(), this.configuration.getLanguage());
        twitter.setIgnoreAccounts(this.configuration.getIgnoreAccounts());
        bc = ClassifierHelper.getClassifier(configuration);
        bc.useDistribution(configuration.isClassifier_useDistribution());
        bc.setTrainData(configuration.getTrainingFile());
        bc.parameterizeString2WordVector(configuration.getClassifier_newWordsToKeep(), configuration.isClassifier_outputWordCounts(), configuration.isClassifier_tfidfTransform(), configuration.isClassifier_normalizeDocLength());
    }

    @Override
    public void addCustomer(String name) {
        twitter.register(name);
        customerDAO.add(name);
    }

    @Override
    public void removeCustomer(String name) {
        twitter.unregister(name);
        customerDAO.remove(name);
    }

    @Override
    public List<String> getAllCustomers() {
        return customerDAO.getAll();
    }

    @Override
    public void addKeyword(String customer, String keyword) {
        twitter.register(keyword);
        customerDAO.addKeyword(customer, keyword);
    }

    @Override
    public void removeKeyword(String customer, String keyword) {
        // only unregister, if the keyword only appears once
        if(customerDAO.onlyOneCustomerContainsKeyword(keyword)){
            twitter.unregister(keyword);
        }

        customerDAO.removeKeyword(customer, keyword);
    }

    @Override
    public List<String> getAllKeywords(String customer) {
        return customerDAO.getAllKeywords(customer);
    }

    @Override
    public TwitterConfiguration getConfiguration() {
        return this.configuration;
    }
    @Override
    public void setConfig(TwitterConfiguration configuration) {
        stopCollecting();
        this.configuration = configuration;
        initTwitter();
    }

    @Override
    public void startCollecting() {
        this.twitter.startDownload();
        this.running = true;

        // set line
        TweetCollection col = twitter.getTweets2(new ArrayList<String>(), line);
        line = col.getCurrentLine();
    }

    @Override
    public void stopCollecting() {
        this.twitter.stopDownload();
        this.running = false;
    }

    @Override
    public String status() {
        if(running) return "collecting";
        else return "stopped";
    }

    @Override
    public List<String> getLastTweets() {
        TweetCollection col = twitter.getTweets2(new ArrayList<String>(), line);
        line = col.getCurrentLine();
        if(col.getSize() == 0)  return null;

        List<String> tweets = new ArrayList<String>();
        for(Tweet tweet : col.getTweets()){
            tweets.add(tweet.getText());
        }

        return tweets;
    }

    @Override
    public TweetCollection analyze(final String customer) {
        TweetCollection collection = twitter.getTweets2(customerDAO.getAllKeywords(customer), 0);

        // For output only
        collection.setCustomer(customer);
        collection.setAlgorithm(configuration.getAlgorithmOutput());

        for (Tweet tweet : collection.getTweets()) {
            tp.preprocess(tweet, new ArrayList<String>(){{add(customer);}});
        }

        bc.classify(collection.getTweets());

        double[] results = TwitterHelper.evaluateClassified(collection.getTweets());
        collection.setResult(results[0]);
        collection.setPositives(results[1]);
        collection.setNeutrals(results[2]);
        collection.setNegatives(results[3]);

        return collection;
    }
}
