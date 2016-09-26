package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Remove hashtags from text
 */
public class HashtagRemoveTask extends AbstractReplaceTask {
    public HashtagRemoveTask() {
        super("#", "hashtag");
    }
}
