/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.aictopic1.sentimentanalysis.machinelearning.impl;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import weka.core.Attribute;
import weka.core.FastVector;

/**
 *
 *
 */
public class BasicClassifierNeutral extends BasicClassifier{

    private PropertiesReader _propertiesReader;

    public BasicClassifierNeutral()
    {
        super();
        this._propertiesReader = PropertiesReader.getInstance();
        this.setTrainData(_propertiesReader.get("twitter.trainingFileNeutral"));
        super.learn();
    }
    
    /**
     * create test instances
     */
    @Override
    protected void newTestInstances() {
        // Declare two numeric attributes
        //FastVector attr1 = new FastVector();
        //attr1.addElement(new Attribute("attr", (FastVector) null));
        Attribute Attribute1 = new Attribute("Document", (FastVector) null);

        // Declare the class attribute along with its values
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("neg");
        fvNominalVal.addElement("neut");
        fvNominalVal.addElement("pos");
        Attribute ClassAttribute = new Attribute("WEKAclass", fvNominalVal);
        
        this.setTestSet(Attribute1, ClassAttribute);
    }
    
    
    @Override
    protected double classifyDistributeOn(double[] pxs)
    {
        return (pxs[0]+pxs[1]*2+pxs[2]*3);
    }
    
    @Override
    protected double classifyDistributeOff(double pxs)
    {
        return pxs+1;
    }
}
