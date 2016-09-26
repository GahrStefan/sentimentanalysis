package at.aictopic1.webserver;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class Helper {
    public static String capitalize(String line)
    {
        StringBuilder sb = new StringBuilder();
        for(String word : line.split("\\s")){
            sb.append(word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase() + " ");
        }
        return sb.toString().trim();
    }

    /**
     * test=bla&foo=bar
     * @param params whole text
     * @param param name of parameter
     * @return value of parameter
     */
    public static String getParameter(String params, String param){
        try{
            params = URLDecoder.decode(params, "utf-8");
        }catch(Exception e){
            // ignore
        }

        Pattern pattern = Pattern.compile(String.format(".*%s=(.+?)(&.+)?$", param));
        Matcher matcher = pattern.matcher(params);
        if(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }
}
