package at.aictopic1.twitter;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TwitterConfiguration {
    private PropertiesReader _propertiesReader;

    private String jsonFile;
    private String language;
    private String algorithm;
    private String trainingFile;

    private boolean classifier_neutral = false;
    private boolean classifier_normalizeDocLength = false;
    private int classifier_newWordsToKeep = 100;
    private boolean classifier_outputWordCounts = false;
    private boolean classifier_tfidfTransform = false;
    private boolean classifier_useDistribution = true;

    private boolean bayes_useSupervisedDiscretization;
    private boolean bayes_useKernelEstimator;

    private boolean j48_binarySplits;
    private float j48_confidenceFactor;
    private int j48_minNumObj;
    private int j48_numFolds;
    private boolean j48_errorPruning;
    private boolean j48_saveInstanceData;
    private int j48_newSeed;
    private boolean j48_unpruned;
    private boolean j48_useLaplace;

    private int rf_maxDepth;
    private int rf_newNumFeatures;
    private int rf_newNumTrees;
    private int rf_seed;

    private boolean kN_newCrossValidate;
    private int kN_k;
    private boolean kN_newMeanSquared;
    private int kN_newWindowSize;

    private List<String> ignoreAccounts;

    public TwitterConfiguration(){
        this._propertiesReader = PropertiesReader.getInstance();

        this.language = _propertiesReader.get("twitter.language");
        this.algorithm = _propertiesReader.get("twitter.algorithm");
        this.jsonFile = _propertiesReader.get("twitter.jsonfile");
        this.trainingFile = _propertiesReader.get("twitter.trainingFile");

        ignoreAccounts = new ArrayList<String>();
        //default ignore accounts
        for(String ignore : _propertiesReader.get("twitter.ignoreAccounts").split("\\s?,\\s?")){
            this.ignoreAccounts.add(ignore);
        }
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public List<String> getIgnoreAccounts() {
        return ignoreAccounts;
    }

    public void setIgnoreAccounts(String ignoreAccounts) {
        String[] split = ignoreAccounts.split("\\s?[,\\r?\\n]\\s?");

        for(String ignore : split){
            this.ignoreAccounts.add(ignore);
        }
    }

    public boolean isClassifier_neutral() {
        return classifier_neutral;
    }

    public void setClassifier_neutral(boolean classifier_neutral) {
        this.classifier_neutral = classifier_neutral;
    }

    public boolean isClassifier_normalizeDocLength() {
        return classifier_normalizeDocLength;
    }

    public void setClassifier_normalizeDocLength(boolean classifier_normalizeDocLength) {
        this.classifier_normalizeDocLength = classifier_normalizeDocLength;
    }

    public int getClassifier_newWordsToKeep() {
        return classifier_newWordsToKeep;
    }

    public void setClassifier_newWordsToKeep(int classifier_newWordsToKeep) {
        this.classifier_newWordsToKeep = classifier_newWordsToKeep;
    }

    public boolean isClassifier_outputWordCounts() {
        return classifier_outputWordCounts;
    }

    public void setClassifier_outputWordCounts(boolean classifier_outputWordCounts) {
        this.classifier_outputWordCounts = classifier_outputWordCounts;
    }

    public boolean isClassifier_tfidfTransform() {
        return classifier_tfidfTransform;
    }

    public void setClassifier_tfidfTransform(boolean classifier_tfidfTransform) {
        this.classifier_tfidfTransform = classifier_tfidfTransform;
    }

    public boolean isClassifier_useDistribution() {
        return classifier_useDistribution;
    }

    public void setClassifier_useDistribution(boolean classifier_useDistribution) {
        this.classifier_useDistribution = classifier_useDistribution;
    }

    public String getAlgorithmOutput(){
        return String.format("%s (neutral=%s, normalizeDocLength=%s, newWordsToKeep=%d, outputWordCounts=%s)", algorithm, classifier_neutral, classifier_normalizeDocLength, classifier_newWordsToKeep, classifier_outputWordCounts);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTrainingFile() {
        return trainingFile;
    }

    public void setTrainingFile(String trainingFile) {
        this.trainingFile = trainingFile;
    }

    public boolean isBayes_useSupervisedDiscretization() {
        return bayes_useSupervisedDiscretization;
    }

    public void setBayes_useSupervisedDiscretization(boolean bayes_useSupervisedDiscretization) {
        this.bayes_useSupervisedDiscretization = bayes_useSupervisedDiscretization;
    }

    public boolean isBayes_useKernelEstimator() {
        return bayes_useKernelEstimator;
    }

    public void setBayes_useKernelEstimator(boolean bayes_useKernelEstimator) {
        this.bayes_useKernelEstimator = bayes_useKernelEstimator;
    }

    public int getJ48_minNumObj() {
        return j48_minNumObj;
    }

    public void setJ48_minNumObj(int j48_minNumObj) {
        this.j48_minNumObj = j48_minNumObj;
    }

    public boolean isJ48_errorPruning() {
        return j48_errorPruning;
    }

    public void setJ48_errorPruning(boolean j48_errorPruning) {
        this.j48_errorPruning = j48_errorPruning;
    }

    public boolean isJ48_useLaplace() {
        return j48_useLaplace;
    }

    public void setJ48_useLaplace(boolean j48_useLaplace) {
        this.j48_useLaplace = j48_useLaplace;
    }

    public boolean isJ48_binarySplits() {
        return j48_binarySplits;
    }

    public void setJ48_binarySplits(boolean j48_binarySplits) {
        this.j48_binarySplits = j48_binarySplits;
    }

    public int getJ48_numFolds() {
        return j48_numFolds;
    }

    public void setJ48_numFolds(int j48_numFolds) {
        this.j48_numFolds = j48_numFolds;
    }

    public boolean isJ48_saveInstanceData() {
        return j48_saveInstanceData;
    }

    public void setJ48_saveInstanceData(boolean j48_saveInstanceData) {
        this.j48_saveInstanceData = j48_saveInstanceData;
    }

    public int getJ48_newSeed() {
        return j48_newSeed;
    }

    public void setJ48_newSeed(int j48_newSeed) {
        this.j48_newSeed = j48_newSeed;
    }

    public boolean isJ48_unpruned() {
        return j48_unpruned;
    }

    public void setJ48_unpruned(boolean j48_unpruned) {
        this.j48_unpruned = j48_unpruned;
    }

    public float getJ48_confidenceFactor() {
        return j48_confidenceFactor;
    }

    public void setJ48_confidenceFactor(float j48_confidenceFactor) {
        this.j48_confidenceFactor = j48_confidenceFactor;
    }

    public int getRf_maxDepth() {
        return rf_maxDepth;
    }

    public void setRf_maxDepth(int rf_maxDepth) {
        this.rf_maxDepth = rf_maxDepth;
    }

    public int getRf_newNumFeatures() {
        return rf_newNumFeatures;
    }

    public void setRf_newNumFeatures(int rf_newNumFeatures) {
        this.rf_newNumFeatures = rf_newNumFeatures;
    }

    public int getRf_newNumTrees() {
        return rf_newNumTrees;
    }

    public void setRf_newNumTrees(int rf_newNumTrees) {
        this.rf_newNumTrees = rf_newNumTrees;
    }

    public int getRf_seed() {
        return rf_seed;
    }

    public void setRf_seed(int rf_seed) {
        this.rf_seed = rf_seed;
    }

    public boolean iskN_newCrossValidate() {
        return kN_newCrossValidate;
    }

    public void setkN_newCrossValidate(boolean kN_newCrossValidate) {
        this.kN_newCrossValidate = kN_newCrossValidate;
    }

    public int getkN_k() {
        return kN_k;
    }

    public void setkN_k(int kN_k) {
        this.kN_k = kN_k;
    }

    public boolean iskN_newMeanSquared() {
        return kN_newMeanSquared;
    }

    public void setkN_newMeanSquared(boolean kN_newMeanSquared) {
        this.kN_newMeanSquared = kN_newMeanSquared;
    }

    public int getkN_newWindowSize() {
        return kN_newWindowSize;
    }

    public void setkN_newWindowSize(int kN_newWindowSize) {
        this.kN_newWindowSize = kN_newWindowSize;
    }
}
