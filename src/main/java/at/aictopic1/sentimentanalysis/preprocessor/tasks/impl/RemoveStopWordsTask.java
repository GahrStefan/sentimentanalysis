package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.PropertiesReader;
import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Remove stop words from text
 * Stopwords are store in language specific text file
 */
public class RemoveStopWordsTask implements ITask {

    PropertiesReader propertiesReader;
    private final List<String> stopWords = new ArrayList<String>();


    public RemoveStopWordsTask(String language){
        propertiesReader = PropertiesReader.getInstance();
        readStopwords(language);

    }

    private void readStopwords(String language){
        String filePath=getClass().getClassLoader().getResource(propertiesReader.get("preprocessing.stopwordnaming", language)).getPath().replace("%20", " ");
        File file = new File(filePath);
        
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stopWords.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String doWork(String text, List<String> keywords) {

        for(String stopword : this.stopWords){
            String regex = String.format("\\b%s\\b", stopword);
            text = text.replaceAll(regex, " ");
        }

        return text;
    }
}
