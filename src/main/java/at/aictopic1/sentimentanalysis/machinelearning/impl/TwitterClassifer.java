/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import at.aictopic1.twitter.Tweet;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;
import java.util.Enumeration;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.RemovePercentage;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * 
 */
public class TwitterClassifer {

    private String algorithm;
    private Classifier trainedModel;
    private Instances dataStructure;
    private static String modelDir = "Other Stuff/models/";
    //private Instances predictSet; 
//    private static Tweet[] examples;

    public static void main(String[] args) {

        TwitterClassifer trainer = new TwitterClassifer("NaiveBayes");

    }

    public TwitterClassifer(String algorithm) {
        this.algorithm = algorithm;

        if (!loadModel()) {
            System.out.println("loadModel failed, training new.");
            trainModel();
        }

        classify(new Tweet[]{new Tweet("Blah")});
    }

    public void trainModel() {
        Instances trainingData = loadTrainingData();

        System.out.println("Class attribute: " + trainingData.classAttribute().toString());

        // Partition dataset into training and test sets
        RemovePercentage filter = new RemovePercentage();

        filter.setPercentage(10);

        Instances testData = null;

        // Split in training and testdata
        try {
            filter.setInputFormat(trainingData);

            testData = Filter.useFilter(trainingData, filter);
        } catch (Exception ex) {
            //Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error getting testData: " + ex.toString());
        }

        // Train the classifier
        Classifier model = (Classifier) new NaiveBayes();

        try {
            // Save the model to fil
            // serialize model
            weka.core.SerializationHelper.write(modelDir + algorithm + ".model", model);
        } catch (Exception ex) {
            Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Set the local model 
        this.trainedModel = model;

        try {
            model.buildClassifier(trainingData);
        } catch (Exception ex) {
            //Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error training model: " + ex.toString());
        }

        try {
            // Evaluate model
            Evaluation test = new Evaluation(trainingData);
            test.evaluateModel(model, testData);

            System.out.println(test.toSummaryString());

        } catch (Exception ex) {
            //Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error evaluating model: " + ex.toString());
        }
    }

    public Instances loadTrainingData() {

        try {
            //DataSource source = new DataSource("C:\\Users\\David\\Documents\\Datalogi\\TU Wien\\2014W_Advanced Internet Computing\\Labs\\aic_group2_topic1\\Other Stuff\\training_dataset.arff");
            DataSource source = new DataSource("C:\\Users\\David\\Documents\\Datalogi\\TU Wien\\2014W_Advanced Internet Computing\\Labs\\Data sets\\labelled.arff");

//            System.out.println("Data Structure pre processing: " + source.getStructure());
            Instances data = source.getDataSet();

            // Get and save the dataStructure of the dataset
            dataStructure = source.getStructure();
            try {
                // Save the datastructure to file
                // serialize dataStructure
                weka.core.SerializationHelper.write(modelDir + algorithm + ".dataStruct", dataStructure);
            } catch (Exception ex) {
                Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Set class index
            data.setClassIndex(2);

            // Giving attributes unique names before converting strings
            data.renameAttribute(2, "class_attr");
            data.renameAttribute(0, "twitter_id");

            // Convert String attribute to Words using filter
            StringToWordVector filter = new StringToWordVector();

            filter.setInputFormat(data);

            Instances filteredData = Filter.useFilter(data, filter);
            
            
            System.out.println("filteredData struct: " + filteredData.attribute(0));
            System.out.println("filteredData struct: " + filteredData.attribute(1));
            System.out.println("filteredData struct: " + filteredData.attribute(2));


            return filteredData;

        } catch (Exception ex) {
            System.out.println("Error loading training set: " + ex.toString());
            return null;
//Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Returns array of tweets with their classification 
    public Integer classify(Tweet[] tweets) {
        // TEST

        // Generate two tweet examples
        Tweet exOne = new Tweet("This is good and fantastic");
        exOne.setPreprocessedText("This is good and fantastic");
        Tweet exTwo = new Tweet("Horribly, terribly bad and more");
        exTwo.setPreprocessedText("Horribly, terribly bad and more");
        Tweet exThree = new Tweet("I want to update lj and read my friends list, but I\\'m groggy and sick and blargh.");
        exThree.setPreprocessedText("I want to update lj and read my friends list, but I\\'m groggy and sick and blargh.");
        Tweet exFour = new Tweet("bad hate worst sick");
        exFour.setPreprocessedText("bad hate worst sick");
        tweets = new Tweet[]{exOne, exTwo, exThree, exFour};
        // TEST

        // Load model
//        loadModel();
        // Convert Tweet to Instance type 
        // Get String Data
        // Create attributes for the Instances set
        Attribute twitter_id = new Attribute("twitter_id");
//        Attribute body = new Attribute("body");

        FastVector classVal = new FastVector(2);
        classVal.addElement("pos");
        classVal.addElement("neg");

        Attribute class_attr = new Attribute("class_attr", classVal);

        // Add them to a list
        FastVector attrVector = new FastVector(3);
//        attrVector.addElement(twitter_id);
//        attrVector.addElement(new Attribute("body", (FastVector) null));
//        attrVector.addElement(class_attr);

        // Get the number of tweets and then create predictSet
        int numTweets = tweets.length;
        Enumeration structAttrs = dataStructure.enumerateAttributes();

//        ArrayList<Attribute> attrList = new ArrayList<Attribute>(dataStructure.numAttributes());
        while (structAttrs.hasMoreElements()) {
            attrVector.addElement((Attribute) structAttrs.nextElement());
        }
        Instances predictSet = new Instances("predictInstances", attrVector, numTweets);
//        Instances predictSet = new Instances(dataStructure);
        predictSet.setClassIndex(2);

        // init prediction
        double prediction = -1;

        System.out.println("PredictSet matches source structure: " + predictSet.equalHeaders(dataStructure));

        System.out.println("PredSet struct: " + predictSet.attribute(0));
        System.out.println("PredSet struct: " + predictSet.attribute(1));
        System.out.println("PredSet struct: " + predictSet.attribute(2));
        // Array to return predictions 
        //double[] tweetsClassified = new double[2][numTweets];
        //List<Integer, Double> tweetsClass = new ArrayList<Integer, Double>(numTweets);
        for (int i = 0; i < numTweets; i++) {
            String content = (String) tweets[i].getPreprocessedText();

            System.out.println("Tweet content: " + content);

//            attrList
            Instance tweetInstance = new Instance(predictSet.numAttributes());

            tweetInstance.setDataset(predictSet);

            tweetInstance.setValue(predictSet.attribute(0), i);
            tweetInstance.setValue(predictSet.attribute(1), content);
            tweetInstance.setClassMissing();

            predictSet.add(tweetInstance);

            try {
                // Apply string filter
                StringToWordVector filter = new StringToWordVector();

                filter.setInputFormat(predictSet);
                Instances filteredPredictSet = Filter.useFilter(predictSet, filter);

                // Apply model
                prediction = trainedModel.classifyInstance(filteredPredictSet.instance(i));
                filteredPredictSet.instance(i).setClassValue(prediction);
                System.out.println("Classification: " + filteredPredictSet.instance(i).toString());
                System.out.println("Prediction: " + prediction);

            } catch (Exception ex) {
                Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return 0;
    }

    public Integer classify(Tweet tweet) {

        // Apply model 
        return 0;
    }

    // Loads the model
    // Tries to load the model and dataStructure from file
    // Returns: True on succesful load, False if not
    public boolean loadModel() {
        if (new File(modelDir + algorithm + ".model").isFile()
                && new File(modelDir + algorithm + ".dataStruct").isFile()) {
            try {
                this.trainedModel = (Classifier) weka.core.SerializationHelper.read(modelDir + algorithm + ".model");
                this.dataStructure = (Instances) weka.core.SerializationHelper.read(modelDir + algorithm + ".dataStruct");
                return true;
            } catch (Exception ex) {
                Logger.getLogger(TwitterClassifer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return false;
        }
        // Default: Return false
        return false;
    }
}
