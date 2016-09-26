package at.aictopic1.sentimentanalysis.preprocessor.tasks.impl;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
public class SmileyTask implements ITask {
    private Map<String, String> converts = new HashMap<String, String>(){
        {
            put(":-) :) :D :o) :] :3 :c) :> =] 8) =) :} :^)", "happy");
            put(":-D 8-D 8D x-D xD X-D XD =-D =D =-3 =3 B^D", "laughing");
            put(":-))", "very happy");
            put(">:[ :-( :(  :-c :c :-< :< :-[ :[ :{", "sad");
            put(":-|| :@ >:(", "angry");
            put(":'-( :'(", "crying");
            put("D:< D: D8 D; D= DX v.v D-':", "disgust");
            put(">:O :-O :O :-o :o 8-0 O_O o-o O_o o_O o_o O-O", "suprise");
            put(":* :^*", "kiss");
            put(";-) ;) *-) *) ;-] ;] ;D ;^) :-,", "wink");
            put(">:P :-P :P X-P x-p xp XP :-p :p =p :-b :b d:", "playful");
            put(">:\\ >:/ :-/ :-. :/ :\\ =/ =\\ :L =L :S >.<", "annoyed");
            put(":| :-|", "indecision");
            put(":$ :-$", "embarrassed");
        }
    };

    @Override
    public String doWork(String text, List<String> keywords) {
        for(Map.Entry<String, String> entry : converts.entrySet()){
            for(String code : entry.getKey().split("\\s")){
                if(code.length() == 0) continue;
                if(text.contains(code))
                    text = text.replaceAll(escape(code), entry.getValue());
            }
        }

        return text;
    }

    private static String escape(String code){
        code = code.replaceAll("\\)", "\\\\)");
        code = code.replaceAll("\\(", "\\\\(");
        code = code.replaceAll("\\}", "\\\\}");
        code = code.replaceAll("\\{", "\\\\{");
        code = code.replaceAll("\\-", "\\\\-");
        code = code.replaceAll("\\|", "\\\\|");
        code = code.replaceAll("\\[", "\\\\[");
        code = code.replaceAll("\\]", "\\\\]");
        code = code.replaceAll("\\*", "\\\\*");

        return code;
    }

}
