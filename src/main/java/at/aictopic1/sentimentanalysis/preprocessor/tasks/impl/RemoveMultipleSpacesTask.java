package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Replace multiple whitespaces to a single one
 */
public class RemoveMultipleSpacesTask extends AbstractReplaceTask {
    public RemoveMultipleSpacesTask() {
        super("\\s+", " ");
    }
}
