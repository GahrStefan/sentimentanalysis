package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.List;

/**
 * Convert text to lower case
 */
public class LowerTask implements ITask {
    @Override
    public String doWork(String text, List<String> keywords) {
        return text.toLowerCase();
    }
}
