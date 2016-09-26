package at.aictopic1.sentimentanalysis.machinelearning;

import at.aictopic1.sentimentanalysis.machinelearning.impl.*;
import at.aictopic1.twitter.TwitterConfiguration;

/**
 * 
 */
public class ClassifierHelper {
    public static Classifier getClassifier(TwitterConfiguration configuration){
        String algorithm = configuration.getAlgorithm();
        boolean neutral = configuration.isClassifier_neutral();

        Classifier classifier = null;

        if(algorithm.equals("J48")){
            if (neutral) classifier = new J48ClassifierNeutral();
            classifier = new J48Classifier();
            classifier.setJ48OptionsClassifier(configuration.isJ48_binarySplits(), configuration.getJ48_confidenceFactor(), configuration.getJ48_minNumObj(), configuration.getJ48_numFolds(), configuration.isJ48_errorPruning(), configuration.isJ48_saveInstanceData(), configuration.getJ48_newSeed(), configuration.isJ48_unpruned(), configuration.isJ48_useLaplace());
        }else if(algorithm.equals("NaiveBayes")){
            if (neutral) classifier = new NaiveBayesClassifierNeutral();
            classifier = new NaiveBayesClassifier();
            classifier.setNaiveBayesClassifierOptions(configuration.isBayes_useKernelEstimator(), configuration.isBayes_useSupervisedDiscretization());
        }else if(algorithm.equals("NaiveBayesMultinomial")){
            if (neutral) classifier = new NaiveBayesMultinomialClassifierNeutral();
            classifier = new NaiveBayesMultinomialClassifier();
            classifier.setNaiveBayesClassifierOptions(configuration.isBayes_useKernelEstimator(), configuration.isBayes_useSupervisedDiscretization());
        }else if(algorithm.equals("RandomForest")){
            if (neutral) classifier = new RandomForestClassifierNeutral();
            classifier = new RandomForestClassifier();
            classifier.setRandomForestClassifierOptions(configuration.getRf_maxDepth(), configuration.getRf_newNumFeatures(), configuration.getRf_newNumTrees(), configuration.getRf_seed());
        }else if(algorithm.equals("kNearestNeighbour")){
            if (neutral) classifier = new kNearestNeighbourClassifierNeutral();
            classifier = new kNearestNeighbourClassifier();
            classifier.setkNearestNeighbourOptionsClassifier(configuration.iskN_newCrossValidate(), configuration.getkN_k(), configuration.iskN_newMeanSquared(), configuration.getkN_newWindowSize());
        }
        return classifier;
    }
}
