package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;
import at.aictopic1.sentimentanalysis.preprocessor.tasks.helper.StanfordLemmatizer;
import edu.stanford.nlp.util.StringUtils;

import java.util.List;

/**
 * Replace words by their word stemming
 */
public class StemmingTask implements ITask {

    private String language;
    private StanfordLemmatizer lemmatizer;

    public StemmingTask(String language){
        this.language = language;
        this.lemmatizer = new StanfordLemmatizer();
    }

    @Override
    public String doWork(String text, List<String> keywords) {
        return StringUtils.join(lemmatizer.lemmatize(text), " ");

    }
}
