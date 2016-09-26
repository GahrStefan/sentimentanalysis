package at.aictopic1.sentimentanalysis.preprocessor.tasks;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract replace class, do simplify further replacing tasks
 */
public class AbstractReplaceTask implements ITask {
    private String _replace;
    private Pattern _pattern;
    private String _regex;

    public AbstractReplaceTask(String regex, String replace){
        this._replace = replace;
        this._regex = regex;
        this._pattern = Pattern.compile(regex);
    }

    @Override
    public String doWork(String text, List<String> keywords) {
        Matcher matcher = _pattern.matcher(text);
        return matcher.replaceAll(_replace);
    }
}
