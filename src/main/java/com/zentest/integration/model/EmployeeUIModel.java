package com.zentest.integration.model;

import java.util.List;

/**
 * Model class to represent a Employee - Subordinates relationship in Org tree format as 
 * specified by javascript library
 * 
 * @author prasannak
 *
 */
public class EmployeeUIModel {
    private String id;
    private String name;
    private String email;
    private String title;
    
    /**
     * Could have used subordinates instead of children, but the orgtee library expects
     * children name
     * 
     * TODO: revisit later for cleanup
     */
    private List<EmployeeUIModel> children;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EmployeeUIModel> getChildren() {
        return children;
    }

    public void setChildren(List<EmployeeUIModel> children) {
        this.children = children;
    }
}
