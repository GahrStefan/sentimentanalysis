package at.aictopic1.sentimentanalysis.preprocessor.interfaces;

import at.aictopic1.twitter.Tweet;

import java.util.List;

/**
 * Preprocessor interface
 */
public interface IPreprocessor {
    /**
     * Do specific tasks to preprocess the tweet text
     * @param tweet - Complete tweet instance
     * @return Complete tweet instance, with preprocessed text
     */
    public Tweet preprocess(Tweet tweet, List<String> keywords);
}
