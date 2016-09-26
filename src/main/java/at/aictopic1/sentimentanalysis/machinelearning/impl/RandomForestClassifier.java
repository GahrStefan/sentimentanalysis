/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import weka.classifiers.trees.RandomForest;

/**
 *
 * 
 */
public class RandomForestClassifier extends BasicClassifier{
    
    RandomForest usedClassifier;
    
    /**
     * @param maxDepth The maximum depth of the trees, 0 for unlimited.
     * @param newNumFeatures Number of features to consider
     * @param newNumTrees Number of trees to build.
     * @param seed Seed for random number generator.
     * 
     * set options of RandomForestClassifier
     */
    @Override
    public void setRandomForestClassifierOptions(int maxDepth, int newNumFeatures, int newNumTrees, int seed) {
        this.usedClassifier.setMaxDepth(maxDepth);
        this.usedClassifier.setNumFeatures(newNumFeatures);
        this.usedClassifier.setNumTrees(newNumTrees);
        this.usedClassifier.setSeed(seed);
        
        this.fcClassifier.setClassifier(usedClassifier);
    }
    
    /**
     * sets classifier
     */
    @Override
    protected void setClassifier() {

        //classifier
        this.usedClassifier = new RandomForest();
        //.. other options
        this.fcClassifier.setClassifier(this.usedClassifier);
    }
    
}