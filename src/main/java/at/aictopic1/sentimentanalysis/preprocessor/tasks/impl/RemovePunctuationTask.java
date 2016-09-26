package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Remove all punctuations from text
 */
public class RemovePunctuationTask extends AbstractReplaceTask{
    public RemovePunctuationTask() {
        super("\\p{P}", " ");
    }
}
