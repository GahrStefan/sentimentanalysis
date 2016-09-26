package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.List;

/**
 * Task thats trims the text
 */
public class TrimTask implements ITask {
    @Override
    public String doWork(String text, List<String> keywords) {
        return text.trim();
    }
}
