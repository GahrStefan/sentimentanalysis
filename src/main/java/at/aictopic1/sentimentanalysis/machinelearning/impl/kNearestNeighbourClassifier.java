/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import weka.classifiers.lazy.IBk;

/**
 *
 * 
 */
public class kNearestNeighbourClassifier extends BasicClassifier{
    
    IBk usedClassifier;
    
    /**
     * @param newCrossValidate Sets whether hold-one-out cross-validation will be used to select the best k value.
     * @param k Set the number of neighbours the learner is to use.
     * @param newMeanSquared Sets whether the mean squared error is used rather than mean absolute error when doing cross-validation.
     * @param newWindowSize Sets the maximum number of instances allowed in the training pool.
     * 
     * set options of kNearestNeighbourClassifier
     */
    @Override
    public void setkNearestNeighbourOptionsClassifier(boolean newCrossValidate, int k, boolean newMeanSquared, int newWindowSize)
    {
        this.usedClassifier.setCrossValidate(newCrossValidate);
        this.usedClassifier.setKNN(k);
        this.usedClassifier.setMeanSquared(newMeanSquared);
        this.usedClassifier.setWindowSize(newWindowSize);
        
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
    
    
    /**
     * sets classifier
     */
    @Override
    protected void setClassifier() {

        //classifier
        this.usedClassifier = new IBk();
        //.. other options
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
}
