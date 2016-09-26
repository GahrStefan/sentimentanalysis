package at.aictopic1.sentimentanalysis.machinelearning;

import at.aictopic1.twitter.Tweet;

import java.util.List;

public interface Classifier {
	
    /**
     * Classifies tweet.
     * @param tweet Preprocessed tweet to classify
     * stores a value between -1 and 1 (-1 ->really negative, 1 -> really positive, 0 -> neutral etc.) in each tweet
     */
    public void classify(List<Tweet> tweet);

    public void setTrainData(String path);
    public void learn();
        
    /**
     * @param newWordsToKeep : nr of words to keep
     * @param outputWordCounts : count the words of each document
     * @param tfidfTransform : if true outputWordCounts is activated as well
     * @param normalizeDocLength : take document length into account
     * 
     */
    public void parameterizeString2WordVector(int newWordsToKeep, boolean outputWordCounts, boolean tfidfTransform, boolean normalizeDocLength);
    
    /**
     * @param argDistrOn 
     * 
     * determines wheter to use distribution or not
     */
    public void useDistribution(boolean argDistrOn);
    
    
    /**
     * @param useKernelEstimator Use kernel density estimator rather than normal
     * @param useSupervisedDiscretization Use supervised discretization to process numeric attributes
     * 
     * set options of NaiveBayesClassifier
     */
    public void setNaiveBayesClassifierOptions(boolean useKernelEstimator,boolean useSupervisedDiscretization);
    
  
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
    public void setJ48OptionsClassifier(boolean binarySplits, float confidenceFactor,int minNumObj, int numFolds, boolean errorPruning, boolean saveInstanceData, int newSeed, boolean unpruned, boolean useLaplace);
    
    /**
     * @param maxDepth The maximum depth of the trees, 0 for unlimited.
     * @param newNumFeatures Number of features to consider
     * @param newNumTrees Number of trees to build.
     * @param seed Seed for random number generator.
     * 
     * set options of RandomForestClassifier
     */
    public void setRandomForestClassifierOptions(int maxDepth, int newNumFeatures, int newNumTrees, int seed);


    /**
     * @param newCrossValidate Sets whether hold-one-out cross-validation will be used to select the best k value.
     * @param k Set the number of neighbours the learner is to use.
     * @param newMeanSquared Sets whether the mean squared error is used rather than mean absolute error when doing cross-validation.
     * @param newWindowSize Sets the maximum number of instances allowed in the training pool.
     * 
     * set options of kNearestNeighbourClassifier
     */
    public void setkNearestNeighbourOptionsClassifier(boolean newCrossValidate, int k, boolean newMeanSquared, int newWindowSize);
}
