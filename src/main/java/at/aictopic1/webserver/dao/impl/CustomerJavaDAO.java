package at.aictopic1.webserver.dao.impl;

import at.aictopic1.webserver.Helper;
import at.aictopic1.webserver.dao.ICustomerDAO;
import at.aictopic1.webserver.dto.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class CustomerJavaDAO implements ICustomerDAO{

    private Map<String, Customer> customers;

    public CustomerJavaDAO(){
        customers = new HashMap<String, Customer>();
    }

    @Override
    public void add(String name) {
        name = Helper.capitalize(name);
        if(customers.containsKey(name)) return;

        Customer c = new Customer(name);
        customers.put(name, c);
    }

    @Override
    public void remove(String name) {
        name = Helper.capitalize(name);
        if(!customers.containsKey(name)) return;

        customers.remove(name);
    }

    @Override
    public List<String> getAll() {
        return new ArrayList<String>(customers.keySet());
    }

    @Override
    public void addKeyword(String customer, String keyword) {
        if(!customers.containsKey(customer)) return;

        customers.get(customer).addKeyword(keyword);
    }

    @Override
    public void removeKeyword(String customer, String keyword) {
        if(!customers.containsKey(customer)) return;
        customers.get(customer).removeKeyword(keyword);
    }

    @Override
    public List<String> getAllKeywords(String customer) {
        if(!customers.containsKey(customer)) return new ArrayList<String>();

        return customers.get(customer).getKeywords();
    }

    @Override
    public List<String> getAllKeywordsOverall() {
        List<String> keywords = new ArrayList<String>();

        for (String customer : customers.keySet()){
            keywords.addAll(customers.get(customer).getKeywords());
        }

        return keywords;
    }

    @Override
    public boolean onlyOneCustomerContainsKeyword(String keyword) {
        int count = 0;

        for (String customer : customers.keySet()){
            if(customers.get(customer).getKeywords().contains(keyword)){
                count++;
            }
        }

        return count == 1;
    }
}
