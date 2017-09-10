package com.zentest.integration.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Simple Employee Pojo class
 * 
 * @author prasannak
 *
 */
public class Employee {
    @SerializedName("id")
    private String employeeId;
    
    @SerializedName("first_name")
    private String firstName;
    
    @SerializedName("last_name")
    private String lastName;
    
    @SerializedName("preferred_name")
    private String preferredName;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("personal_email")
    private String personalEmail;
    
    @SerializedName("work_email")
    private String workEmail;
    
    @SerializedName("personal_phone")
    private String phone;
    
    @SerializedName("manager")
    private Manager managerRef;
    
    @SerializedName("subordinates")
    private Subordinate subordinateRef;
    
    private transient List<Employee> subordinates;
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPreferredName() {
        return preferredName;
    }
    
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPersonalEmail() {
        return personalEmail;
    }
    
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }
    
    public String getWorkEmail() {
        return workEmail;
    }
    
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<Employee> getSubordinates() {
        return subordinates;
    }
    
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Manager getManager() {
        return managerRef;
    }

    public void setManager(Manager manager) {
        this.managerRef = manager;
    }

    public Subordinate getSubordinateRef() {
        return subordinateRef;
    }

    public void setSubordinateRef(Subordinate subordinateRef) {
        this.subordinateRef = subordinateRef;
    }

    
    public class Manager {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    
    public class Subordinate {
        @SerializedName("url")
        private String url;
        
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
