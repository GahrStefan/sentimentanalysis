package at.aictopic1.sentimentanalysis.preprocessor.arffPreprocessing;

import at.aictopic1.sentimentanalysis.preprocessor.*;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.ArrayList;

/**
 * Twitter specific preprocessing instance.
 */
public class ArffPreprocessor extends TwitterPreprocessor {

    public ArffPreprocessor(String language) {
        super(language);
    }
    
    public String preprocess(String line) {
        
        int lSentiment=3;
        
        if (line.length()<=lSentiment+3)
        {
            return "";
        }
        String sentimentValue=line.substring(line.length()-4);
        line=line.substring(1,line.length()-6);
        
        
        for(ITask task : getTasks()){
            // in list should be company name
            line = task.doWork(line, new ArrayList<String>());
        }
        if (line.length()>0)
        {
            return "\""+line+"\","+sentimentValue;
        } else {
            return "";
        }
        
    }
}
