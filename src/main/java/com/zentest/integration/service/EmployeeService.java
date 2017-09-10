package com.zentest.integration.service;

import java.util.List;

import com.zentest.integration.model.Employee;
import com.zentest.integration.model.EmployeeUIModel;

/**
 * Service class for fetching Employee information and relationships
 *  
 * @author prasannak
 *
 */
public interface EmployeeService {
    /**
     * Returns all employees for the company
     * <br>
     * Note: Employee object has basic attributes populated without Manager/Subordinate employees knitted 
     * </br>
     * @return
     */
    public List<Employee> getAllEmployees();
    
    /**
     * Fetch CEO of the company
     * 
     * <br>
     * (Assumption to be employee without any manager and also title attribute is not populated)
     * </br>
     * <br>
     * TODO: handle cases where there multiple CXOs
     * </br>
     * @return Employee object corresponding to CEO of the company
     */
    public Employee getCEO();
    
    /**
     * Loads the Org Tree (Manager -> subordinates)
     * 
     * @param employee
     * @return Returns the root node of the Org Tree 
     */
    public EmployeeUIModel getOrgTree(Employee employee);
}
