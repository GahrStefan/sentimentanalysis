package at.aictopic1.sentimentanalysis.preprocessor;

import at.aictopic1.twitter.Tweet;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.IPreprocessor;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;
import at.aictopic1.sentimentanalysis.preprocessor.tasks.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Twitter specific preprocessing instance.
 */
public class TwitterPreprocessor extends AbstractPreprocessor implements IPreprocessor {

    public TwitterPreprocessor(String language) {
        super(language);
        init();
    }

    /**
     * Load tasks
     */
    private void init(){
        addTask(new LowerTask());
        addTask(new RemoveUsernameTask());
        addTask(new RemoveUrlTask());
        addTask(new RemoveWordsStartingWithNumericalLetterTask());
        addTask(new RemoveNumericalsTask());
        addTask(new ReplaceCompanyNameTask());
        addTask(new HashtagRemoveTask());
        addTask(new SmileyTask());
        addTask(new RemovePunctuationTask());
        addTask(new RemoveRepeatedLettersTask());
        addTask(new ReplaceAbbreviationsTask(getLanguage()));
        addTask(new RemoveStopWordsTask(getLanguage()));
        addTask(new StemmingTask(getLanguage()));


        addTask(new RemoveMultipleSpacesTask());
        addTask(new TrimTask());
    }

    @Override
    public Tweet preprocess(Tweet tweet, List<String> keywords) {
        String processedText = tweet.getText();
        if(keywords == null) keywords = new ArrayList<String>();

        for(ITask task : getTasks()){
            processedText = task.doWork(processedText, keywords);
        }

        tweet.setPreprocessedText(processedText);
        return tweet;
    }
}
