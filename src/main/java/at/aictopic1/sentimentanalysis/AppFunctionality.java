/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis;


import at.aictopic1.sentimentanalysis.machinelearning.Classifier;
import at.aictopic1.sentimentanalysis.machinelearning.impl.*;
import at.aictopic1.sentimentanalysis.preprocessor.TwitterPreprocessor;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.IPreprocessor;
import at.aictopic1.twitter.AICTwitter;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.List;

import at.aictopic1.twitter.Tweet;
import at.aictopic1.twitter.TwitterConfiguration;

/**
 *
 * 
 */
public class AppFunctionality {
    
    String[] companies;

    List<Tweet> tweets;
    AICTwitter twitter;
    private TwitterConfiguration config;
    private IPreprocessor tp = new TwitterPreprocessor("en");
    
    Classifier bc;
    
    boolean useNeutralClassifier;

    public AppFunctionality()
    {
        this.config = new TwitterConfiguration();
        this.tweets = new ArrayList<Tweet>();
        this.twitter=new AICTwitter();
        this.twitter.setIgnoreAccounts(config.getIgnoreAccounts());
        this.bc = new BasicClassifierNeutral();
        this.useNeutralClassifier=false;
    }

    public void classify()
    {
        this.bc.classify(this.tweets);
    }

    public void setTweets(String company){
        this.tweets = this.twitter.getTweets(company);
    }
    
    public void preprocess(final String company)
    {    
        System.out.println("\nPreprocessing...");
        for(Tweet t: this.tweets){
            t = tp.preprocess(t, new ArrayList<String>(){{add(company);}});
        }
    }

    public void setNeutral(boolean useNeutralClassifier)
    {
        this.useNeutralClassifier=useNeutralClassifier;
    }
    
    public void setClassifierJ48()
    {
        if (this.useNeutralClassifier) {
            this.bc=new J48ClassifierNeutral();
        } else {
            this.bc=new J48Classifier();
        }
    }
    
    public void setClassifierNaiveBayesClassifier()
    {
        if (this.useNeutralClassifier) {
            this.bc=new NaiveBayesClassifierNeutral();
        } else {
            this.bc=new NaiveBayesClassifier();
        }
    }
    
    public void setClassifierNaiveBayesMultinomialClassifier()
    {
        if (this.useNeutralClassifier) {
            this.bc=new NaiveBayesMultinomialClassifierNeutral();
        } else {
            this.bc=new NaiveBayesMultinomialClassifier();
        }
    }
    
    public void setClassifierRandomForestClassifier()
    {
        if (this.useNeutralClassifier) {
            this.bc=new RandomForestClassifierNeutral();
        } else {
            this.bc=new RandomForestClassifier();
        }
    }
    
    public void setClassifierkNearestNeighbourClassifier()
    {
        if (this.useNeutralClassifier) {
            this.bc=new kNearestNeighbourClassifierNeutral();
        } else {
            this.bc=new kNearestNeighbourClassifier();
        }
    }

    void parameterizeString2WordVector(int newWordsToKeep, boolean outputWordCounts, boolean tfidfTransform, boolean normalizeDocLength) {
        this.bc.parameterizeString2WordVector(newWordsToKeep, outputWordCounts, tfidfTransform, normalizeDocLength);
    }

    void setDistributeOn(boolean useDistribution) {
        this.bc.useDistribution(useDistribution);
    }
    
    /**
     * @param binarySplits Use binary splits only.
     * @param confidenceFactor Set the value of CF.
     * @param minNumObj Set the value of minNumObj.
     * @param numFolds Set the value of numFolds.
     * @param errorPruning Set the value of reducedErrorPruning.
     * @param saveInstanceData Set whether instance data is to be saved.
     * @param newSeed Set the value of Seed.
     * @param unpruned Use unpruned tree.
     * @param useLaplace use Laplace.
     * 
     * set options of J48Classifier
     */
    public void setJ48OptionsClassifier(boolean binarySplits, float confidenceFactor, int minNumObj, int numFolds, boolean errorPruning, boolean saveInstanceData, int newSeed, boolean unpruned, boolean useLaplace) {
        this.bc.setJ48OptionsClassifier(binarySplits, confidenceFactor, minNumObj, numFolds, errorPruning, saveInstanceData, newSeed, unpruned, useLaplace);
    }

    /**
     * @param maxDepth The maximum depth of the trees, 0 for unlimited.
     * @param newNumFeatures Number of features to consider
     * @param newNumTrees Number of trees to build.
     * @param seed Seed for random number generator.
     * 
     * set options of RandomForestClassifier
     */
    public void setRandomForestClassifierOptions(int maxDepth, int newNumFeatures, int newNumTrees, int seed) {
        this.bc.setRandomForestClassifierOptions(maxDepth, newNumFeatures, newNumTrees, seed);
    }
    
    /**
     * @param newCrossValidate Sets whether hold-one-out cross-validation will be used to select the best k value.
     * @param k Set the number of neighbours the learner is to use.
     * @param newMeanSquared Sets whether the mean squared error is used rather than mean absolute error when doing cross-validation.
     * @param newWindowSize Sets the maximum number of instances allowed in the training pool.
     * 
     * set options of kNearestNeighbourClassifier
     */
    public void setkNearestNeighbourOptionsClassifier(boolean newCrossValidate, int k, boolean newMeanSquared, int newWindowSize)
    {
        this.bc.setkNearestNeighbourOptionsClassifier(newCrossValidate, k, newMeanSquared, newWindowSize);
    }
    
    /**
     * @param useKernelEstimator Use kernel density estimator rather than normal distribution for numeric attributes
     * @param useSupervisedDiscretization Use supervised discretization to process numeric attributes
     * 
     * set options of NaiveBayesClassifier
     */
    public void setNaiveBayesClassifierOptions(boolean useKernelEstimator,boolean useSupervisedDiscretization) {
        this.bc.setNaiveBayesClassifierOptions(useKernelEstimator, useSupervisedDiscretization);
    }
    
}
