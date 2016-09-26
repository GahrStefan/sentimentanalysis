package at.aictopic1.sentimentanalysis.preprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Read properties file
 */
public class PropertiesReader {
    private Properties _config;
    private final Pattern _pattern = Pattern.compile("\\{[^\\}]*\\}");

    private static PropertiesReader instance;

    public static PropertiesReader getInstance(){
        if(instance == null)
            instance = new PropertiesReader();
        return instance;
    }

    public PropertiesReader(){
        _config = new Properties();
        InputStream stream = null;
        try{
            stream = getClass().getClassLoader().getResourceAsStream("sentimentanalysis.properties");
            _config.load(stream);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try{ stream.close(); }catch(Exception e){ }
        }
    }

    public String get(String key, String... args){
        if(!_config.containsKey(key)) return null;

        String value = _config.getProperty(key);
        Matcher matcher = _pattern.matcher(value);
        while(matcher.find()){
            int paramKey = getParamKey(matcher.group(0));
            value = matcher.replaceFirst(args[paramKey]);
            matcher = _pattern.matcher(value);
        }

        return value;
    }

    private int getParamKey(String value){
        return Integer.parseInt(value.substring(1,value.length() - 1));
    }
}
