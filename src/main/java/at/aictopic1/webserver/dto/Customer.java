package at.aictopic1.webserver.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Customer {
    private String name;
    private List<String> keywords;

    public Customer(String name){
        this.name = name;
        this.keywords = new ArrayList<String>();
        this.keywords.add(name);
    }

    public String getName(){ return this.name; }
    public List<String> getKeywords(){return new ArrayList<String>(this.keywords);}
    public void addKeyword(String keyword){
        if(this.keywords.contains(keyword)) return;

        this.keywords.add(keyword);
    }

    public void removeKeyword(String keyword){
        if(!this.keywords.contains(keyword)) return;
        this.keywords.remove(keyword);
    }
}
