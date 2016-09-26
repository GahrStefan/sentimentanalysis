package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Remove all words starting with a number
 */
public class RemoveWordsStartingWithNumericalLetterTask extends AbstractReplaceTask {
    public RemoveWordsStartingWithNumericalLetterTask() {
        super("\\b[0-9][a-zA-Z0-9]+\\b", " ");
    }
}
