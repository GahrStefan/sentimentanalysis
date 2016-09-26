package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Remove @Username from tweet
 */
public class RemoveUsernameTask extends AbstractReplaceTask {
    public RemoveUsernameTask() {
        super("@[a-zA-Z0-9_]*", " ");
    }
}
