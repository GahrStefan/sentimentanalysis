/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import weka.classifiers.trees.J48;

/**
 *
 * 
 */
public class J48ClassifierNeutral extends BasicClassifierNeutral{
    
    J48 usedClassifier;
    
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
    @Override
    public void setJ48OptionsClassifier(boolean binarySplits, float confidenceFactor,int minNumObj, int numFolds, boolean errorPruning, boolean saveInstanceData, int newSeed, boolean unpruned, boolean useLaplace)
    {
        this.usedClassifier.setBinarySplits(binarySplits);
        this.usedClassifier.setConfidenceFactor(confidenceFactor);
        this.usedClassifier.setMinNumObj(minNumObj);
        this.usedClassifier.setNumFolds(numFolds);
        this.usedClassifier.setReducedErrorPruning(errorPruning);
        this.usedClassifier.setSaveInstanceData(saveInstanceData);
        this.usedClassifier.setSeed(newSeed);
        this.usedClassifier.setUnpruned(unpruned);
        this.usedClassifier.setUseLaplace(useLaplace);
        
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
    
    /**
     * sets classifier
     */
    @Override
    protected void setClassifier() {

        //classifier
        this.usedClassifier = new J48();
        //.. other options
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
}