package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Remove all words containing only numbers
 */
public class RemoveNumericalsTask extends AbstractReplaceTask {
    public RemoveNumericalsTask() {
        super("\\b\\d+\\b", " ");
    }
}
