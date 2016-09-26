package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;
import at.aictopic1.sentimentanalysis.preprocessor.tasks.AbstractReplaceTask;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ReplaceCompanyNameTask implements ITask {
    String _regex = "#\\{company\\}";
    String _replace = "#companyname";

    @Override
    public String doWork(String text, List<String> keywords) {
        // do nothing if no company
        if(keywords.size() == 0) return text;

        for(String keyword : keywords){
            String regex = _regex.replace("\\{company\\}", keyword.toLowerCase());
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            text = matcher.replaceAll(_replace);
        }
/*
        if(company != null && company.length() > 0) {
            _regex = _regex.replace("\\{company\\}", company.toLowerCase());
            _pattern = Pattern.compile(_regex);
        }

        Matcher matcher = _pattern.matcher(text);
        return matcher.replaceAll(_replace);*/
        return text;
    }
}
