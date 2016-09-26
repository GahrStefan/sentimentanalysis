package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace abbreviations.
 * Abbreviations are stored in language specific txt file
 */
public class ReplaceAbbreviationsTask implements ITask {

    private PropertiesReader _propertiesReader;
    private final Map<String, String> _abbreviations = new HashMap<String, String>();
    private final Pattern _pattern = Pattern.compile("(.*)=(.*)");

    public ReplaceAbbreviationsTask(String language){
        _propertiesReader = PropertiesReader.getInstance();
        readInAbbreviations(language);
    }

    private void readInAbbreviations(String language){
        String filePath=getClass().getClassLoader().getResource(_propertiesReader.get("preprocessing.abbreviationsnaming", language)).getPath().replace("%20", " ");
        File file = new File(filePath);
        
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Matcher matcher = _pattern.matcher(line);
                if(matcher.matches()){
                    if(matcher.groupCount() >= 2){
                        _abbreviations.put(matcher.group(1), matcher.group(2));
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String doWork(String text, List<String> keywords) {
        for(Map.Entry<String, String> abbreviation : this._abbreviations.entrySet()){
            String regex = String.format("\\b%s\\b", abbreviation.getKey());
            text = text.replaceAll(regex, abbreviation.getValue());
        }

        return text;
    }
}
