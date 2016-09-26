package at.aictopic1.sentimentanalysis.machinelearning.impl;

import at.aictopic1.sentimentanalysis.machinelearning.Classifier;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import at.aictopic1.twitter.Tweet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class BasicClassifier implements Classifier {

    private StringToWordVector str2wordFilter;
    protected FilteredClassifier fcClassifier;
    private final Map<Integer, Tweet> tweetSet;
    private Instances testSet;
    private Instances trainSet;
    private FastVector fvWekaAttributes;
    private SupportedClassifier algorithm;
    private final String modelDir = "Other Stuff/models/";
    private String trainingData;
    private boolean distributeOn;

    private PropertiesReader _propertiesReader;

    private enum SupportedClassifier {

        J48, NAIVEBAYES;
    }

    /**
     * Constructor
     */
    public BasicClassifier() {
        this.tweetSet = new HashMap();
        this.distributeOn=false;
        this._propertiesReader = PropertiesReader.getInstance();
        this.trainingData =_propertiesReader.get("twitter.trainingFile");
        this.learn();
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
        
    }
    
    /**
     * @param useKernelEstimator
     * @param useSupervisedDiscretization 
     * 
     * set options of NaiveBayesClassifier
     */
    public void setNaiveBayesClassifierOptions(boolean useKernelEstimator,boolean useSupervisedDiscretization)
    {
        
    }
    
    /**
     * @param argDistrOn 
     * 
     * determines wheter to use distribution or not
     */
    public void useDistribution(boolean argDistrOn)
    {
        this.distributeOn=argDistrOn;
    }

    /**
     * @param tweets 
     * classifies a list of tweets
     * stores a value between -1 and 1 (-1 ->really negative, 1 -> really positive, 0 -> neutral etc.) in each tweet
     */
    public void classify(List<Tweet> tweets) {
        System.out.print("Create Instances ...");
        this.newTestInstances();
        System.out.print(" done.\nAdding Tweets to the set ...");
        for (Tweet t : tweets) {
            this.addTweet(t);
        }
        System.out.print(" done. [" + this.testSet.numInstances() + " Instances added]\nClassify Testset ...");
        this.classifyTestset();
        System.out.print(" done.\n");
    }

    public void setTrainData(String trainingData)
    {
        this.trainingData=trainingData;
    }
    
    /**
     * trains the classifier with the standard arffFile
     */
    public void learn() {
        this.learn(this.trainingData);
    }
    
    
    /**
     * @param arffFile 
     * 
     * trains the classifier with arffFile
     */
    private void learn(String arffFile) {

        //load arff
        BufferedReader breaderTrain = null;
        try {
            breaderTrain = new BufferedReader(new FileReader(arffFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set instances
        this.trainSet = null;
        try {
            this.trainSet = new Instances(breaderTrain);
        } catch (IOException ex) {
            Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.trainSet.setClassIndex(this.trainSet.numAttributes() - 1);

        try {
            //close breader
            breaderTrain.close();
        } catch (IOException ex) {
            Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        //meta-classifier
        this.fcClassifier = new FilteredClassifier();
        this.setFilter();
        this.setClassifier();

        try {
            this.fcClassifier.buildClassifier(this.trainSet);
        } catch (Exception ex) {
            Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * classifies test set (simple)
     * values:
     * -1: negative
     * 1: positive
     */
    private void classifyTestset() {
        //make predictions
        for (int i = 0; i < this.testSet.numInstances(); i++) {
            try {
                double pred;
                int preferedPred;
                /*
                attributes (in order): (neg,pos)
                
                outcome: in -1..1
                */
                if (this.distributeOn)
                {
                    double[] pxs = (this.fcClassifier.distributionForInstance(this.testSet.instance(i)));
                    pred=this.classifyDistributeOn(pxs);
                    preferedPred = (int) this.classifyDistributeOff((this.fcClassifier.classifyInstance(this.testSet.instance(i)) ));
                } else {
                    pred = this.classifyDistributeOff((this.fcClassifier.classifyInstance(this.testSet.instance(i)) ));
                    preferedPred= (int) pred;
                }
                this.tweetSet.get(i + 1).setClassified(pred);
                
                if (preferedPred==1) {
                    this.tweetSet.get(i + 1).setClassification("negative");
                } else if (preferedPred==2) {
                    this.tweetSet.get(i + 1).setClassification("neutral");
                } else if (preferedPred==3) {
                    this.tweetSet.get(i + 1).setClassification("positive");
                } else {
                    this.tweetSet.get(i + 1).setClassification("unclassified");
                }
                
            } catch (Exception ex) {
                Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(this.fcClassifier.classifierTipText());
    }
    
    /**
     * @param pxs
     * @return 
     * 
     * classifies test set (simple)
     * values:
     * 1: negative
     * 2: neutral
     * 3: positive
     */
    protected double classifyDistributeOn(double[] pxs)
    {
        return pxs[0]+pxs[1]*3;
    }
    
    /**
     * @param pxs
     * @return 
     * 
     * classifies test set (simple)
     * values:
     * 1: negative
     * 2: neutral
     * 3: positive
     */
    protected double classifyDistributeOff(double pxs)
    {
        return pxs*2+1;
    }

    /**
     * create test instances
     */
    protected void newTestInstances() {
        // Declare two numeric attributes
        //FastVector attr1 = new FastVector();
        //attr1.addElement(new Attribute("attr", (FastVector) null));
        Attribute Attribute1 = new Attribute("Document", (FastVector) null);

        // Declare the class attribute along with its values
        FastVector fvNominalVal = new FastVector(2);
        fvNominalVal.addElement("neg");
        fvNominalVal.addElement("pos");
        Attribute ClassAttribute = new Attribute("WEKAclass", fvNominalVal);

        this.setTestSet(Attribute1, ClassAttribute);
    }
    
    protected void setTestSet(Attribute Attribute1,Attribute ClassAttribute)
    {
        // Declare the feature vector
        this.fvWekaAttributes = new FastVector(2);
        this.fvWekaAttributes.addElement(Attribute1);
        this.fvWekaAttributes.addElement(ClassAttribute);

        // Create an empty training set
        this.testSet = new Instances("Testset", this.fvWekaAttributes, 10);
        // Set class index
        this.testSet.setClassIndex(1);
    }

    /**
     * @param tweet 
     * 
     * adds tweet to testSet
     */
    private void addTweet(Tweet tweet) {
        // Create the instance
        Instance newInstance = new Instance(2);

        // set values
        newInstance.setValue((Attribute) this.fvWekaAttributes.elementAt(0), tweet.getPreprocessedText());
        newInstance.setValue((Attribute) this.fvWekaAttributes.elementAt(1), "neg");

        // add the instance
        this.testSet.add(newInstance);
        this.tweetSet.put(this.testSet.numInstances(), tweet);
    }

    /**
     * sets string2Word filter
     * string2Word filter is applied to text such that the outcome makes sense to the classifiers
     */
    private void setFilter() {
        //filter
        this.str2wordFilter = new StringToWordVector(100); //keeps 100 words
        //.. other options
        this.fcClassifier.setFilter(this.str2wordFilter);
    }
    
    /**
     * @param newWordsToKeep : nr of words to keep
     * @param outputWordCounts : count the words of each document
     * @param tfidfTransform : if true outputWordCounts is activated as well
     * @param normalizeDocLength : take document length into account
     * 
     */
    public void parameterizeString2WordVector(int newWordsToKeep, boolean outputWordCounts, boolean tfidfTransform, boolean normalizeDocLength)
    {
        //words to keep
        this.str2wordFilter.setWordsToKeep(newWordsToKeep);
        if (tfidfTransform){ 
            // if tfidfTransform is true outputWordscount is also true!
            this.str2wordFilter.setOutputWordCounts(true);
        } else {                          
            //word count on
            this.str2wordFilter.setOutputWordCounts(outputWordCounts);
        }
        
        //idf and tft transform
        this.str2wordFilter.setIDFTransform(tfidfTransform);
        this.str2wordFilter.setTFTransform(tfidfTransform);
        
        
        // 0=not normalize/1=normalize all data/2=normalize test data only
        
        //normalizeDocLength
        if (normalizeDocLength)
        {
            this.str2wordFilter.setNormalizeDocLength( new SelectedTag(1, StringToWordVector.TAGS_FILTER));
        } else {
            this.str2wordFilter.setNormalizeDocLength( new SelectedTag(0, StringToWordVector.TAGS_FILTER));
        }
        
        try {
            this.fcClassifier.buildClassifier(this.trainSet);
        } catch (Exception ex) {
            Logger.getLogger(BasicClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sets classifier
     */
    protected void setClassifier() {

        //classifier
        NaiveBayes usedClassifier = new NaiveBayes();
        //NaiveBayesMultinomial usedClassifier = new NaiveBayesMultinomial();
        //RandomForest usedClassifier = new RandomForest();
        //J48 usedClassifier =  new J48();
        //IBk usedClassifier = new IBk(); //k nearest neighbour
        //.. other options
        this.fcClassifier.setClassifier(usedClassifier);
    }
    
    /**
     Save the model to file
     */
    private void saveModel() {
        try {
            weka.core.SerializationHelper.write(modelDir + algorithm.toString() + ".model", fcClassifier);
        } catch (Exception ex) {
            Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Load model from file to local variable
     *
     * @return True on succesful load, False if not
     */
    public boolean loadModel() {
        if (new File(modelDir + algorithm + ".model").isFile()) {
            try {
                this.fcClassifier = (FilteredClassifier) weka.core.SerializationHelper.read(modelDir + algorithm.toString() + ".model");
                return true;
            } catch (Exception ex) {
                Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }
}