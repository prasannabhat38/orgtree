package com.zentest.integration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zentest.integration.model.Employee;
import com.zentest.integration.model.EmployeeUIModel;
import com.zentest.integration.service.CoreCompanyService;
import com.zentest.integration.service.EmployeeService;
import com.zentest.integration.utils.AttributeConstants;
import com.zentest.integration.utils.JsonUtil;
import com.zentest.integration.utils.RESTClient;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Log LOG = LogFactory.getLog(EmployeeServiceImpl.class);
    
    @Autowired
    private CoreCompanyService coreCompanyService;
    
    @Autowired
    private RESTClient restClient;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        String nextURL = coreCompanyService.getPeopleURLEndPoint();
        
        
        try {
            do {
                String response = restClient.doGet(nextURL);
                List<Employee> employees = JsonUtil.getEmployees(response);
                allEmployees.addAll(employees);
                
                nextURL = JsonUtil.getMetaDataAttribute(response, AttributeConstants.NEXT_URL);
            } while(nextURL != null);
        }
        catch (ClientProtocolException e) {
            LOG.error("Exception occurred", e);
            throw new RuntimeException(e);
        }
        
        LOG.info("Total number of employees: " + allEmployees.size());
        return allEmployees;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getCEO() {
        List<Employee> allEmployees = getAllEmployees();
        
        Employee ceo = null;
        
        for(Employee emp : allEmployees) {
            if(!emp.getPreferredName().equalsIgnoreCase("admin") && 
                    (emp.getManager() == null || emp.getManager().getUrl() == null)) 
            {
                ceo = emp;
                break;
            }
        }
        
        return ceo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeUIModel getOrgTree(Employee employee) {
        LOG.info("Loading OrgTree for employee id: " + employee.getEmployeeId() + " name: " + employee.getPreferredName());
        // If there is no subordinate link for the employee (Leaf node) just return simple object 
        if(employee.getSubordinateRef() == null || employee.getSubordinateRef().getUrl() == null) {
            EmployeeUIModel empUIModel = createEmployeeUIModel(employee);
            
            return empUIModel;
        }
        
        populateSubordinates(employee);
        
        EmployeeUIModel empUIModel = createEmployeeUIModel(employee);
        
        List<EmployeeUIModel> subordinates = new ArrayList<>();
        
        // Iterate over the Employee's subordinates to load subordinates for each of them recursively
        for(Employee subordinate : employee.getSubordinates()) {
            EmployeeUIModel subEmployeeUIModel = getOrgTree(subordinate);
            subordinates.add(subEmployeeUIModel);
        }
        
        empUIModel.setChildren(subordinates);
        
        return empUIModel;
    }
    
    /**
     * Helper function to load Subordinate objects corresponding to a Employee
     * makes Rest call to subordinates endpoint
     *  
     * @param emp
     */
    private void populateSubordinates(Employee emp) {
        LOG.info("Loading subordinates for Employee: " + emp.getEmployeeId());
        if(emp.getSubordinateRef() != null) {
            String nextURL = emp.getSubordinateRef().getUrl();
            List<Employee> subordinates = new ArrayList<>();
            
            try {
                do {
                    String response = restClient.doGet(nextURL);
                    List<Employee> reports = JsonUtil.getEmployees(response);
                    subordinates.addAll(reports);
                    
                    nextURL = JsonUtil.getMetaDataAttribute(response, AttributeConstants.NEXT_URL);
                } while(nextURL != null);
            } catch (Exception e) {
                LOG.error("Error while populating subordinates", e);
            }
            
            LOG.info("Number of subordinates: " + subordinates.size());
            emp.setSubordinates(subordinates);
        }
    }
    
    private EmployeeUIModel createEmployeeUIModel(Employee employee) {
        EmployeeUIModel empUIModel = new EmployeeUIModel();
        
        empUIModel.setId(employee.getEmployeeId());
        empUIModel.setName(employee.getFirstName() + " " + employee.getLastName());
        empUIModel.setEmail(employee.getWorkEmail());
        empUIModel.setTitle(employee.getTitle());
        
        return empUIModel;
    }
}
