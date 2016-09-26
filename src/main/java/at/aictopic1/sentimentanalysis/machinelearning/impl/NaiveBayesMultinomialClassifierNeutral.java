/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import weka.classifiers.bayes.NaiveBayesMultinomial;

/**
 *
 * 
 */
public class NaiveBayesMultinomialClassifierNeutral extends BasicClassifierNeutral{
    
    //no options
    
    /**
     * sets classifier
     */
    @Override
    protected void setClassifier() {

        //classifier
        NaiveBayesMultinomial usedClassifier = new NaiveBayesMultinomial();
        //.. other options
        this.fcClassifier.setClassifier(usedClassifier);
    }

}
