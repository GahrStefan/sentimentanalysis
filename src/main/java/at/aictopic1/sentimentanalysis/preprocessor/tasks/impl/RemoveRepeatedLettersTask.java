package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

/**
 * Replace repeated letter (multiple characters > 2 next to each other) by two instances
 * e.g. happpyyyyy => happyy
 */
public class RemoveRepeatedLettersTask extends AbstractReplaceTask{
    public RemoveRepeatedLettersTask() {
        super("(.)\\1{1,}", "$1$1");
    }
}
