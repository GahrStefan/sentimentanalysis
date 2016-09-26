package at.aictopic1.webserver.dao;

import java.util.List;

/**
 * 
 */
public interface ICustomerDAO {
    public void add(String name);
    public void remove(String name);
    public List<String> getAll();

    public void addKeyword(String customer, String keyword);
    public void removeKeyword(String customer, String keyword);
    public List<String> getAllKeywords(String customer);
    public List<String> getAllKeywordsOverall();
    public boolean onlyOneCustomerContainsKeyword(String keyword);
}
