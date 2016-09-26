package at.aictopic1.sentimentanalysis.preprocessor.interfaces;

import java.util.List;

/**
 * Task interface. A task is a single step in the preprocessing part.
 */
public interface ITask {

    /**
     * Do the task specific preprocessing work.
     * @param text The tweet text
     * @return Processed tweet text
     */
    public String doWork(String text, List<String> keywords);
}
