
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import weka.classifiers.bayes.NaiveBayes;

/**
 *
 * 
 */
public class NaiveBayesClassifierNeutral extends BasicClassifierNeutral{
    
   
    NaiveBayes usedClassifier;
    
    /**
     * @param useKernelEstimator Use kernel density estimator rather than normal
     * @param useSupervisedDiscretization Use supervised discretization to process numeric attributes
     * 
     * set options of NaiveBayesClassifier
     */
    @Override
    public void setNaiveBayesClassifierOptions(boolean useKernelEstimator,boolean useSupervisedDiscretization) {
        this.usedClassifier.setUseKernelEstimator(useKernelEstimator);
        this.usedClassifier.setUseSupervisedDiscretization(useSupervisedDiscretization);
        
        this.fcClassifier.setClassifier(usedClassifier);
    }
    
    /**
     * sets classifier
     */
    @Override
    protected void setClassifier() {

        //classifier
        this.usedClassifier = new NaiveBayes();
        //.. other options
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
    
}