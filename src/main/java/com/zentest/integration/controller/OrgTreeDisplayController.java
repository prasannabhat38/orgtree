package com.zentest.integration.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zentest.integration.model.Employee;
import com.zentest.integration.model.EmployeeUIModel;
import com.zentest.integration.service.EmployeeService;
import com.zentest.integration.utils.JsonUtil;

@Controller
public class OrgTreeDisplayController {
    private static final Log LOG = LogFactory.getLog(OrgTreeDisplayController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    /**
     * Simple controller method to handle initial request
     * 
     * @return home/index jsp page
     */
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView home() {
       return new ModelAndView("index");
    }
    
    /**
     * Controller to handle display of Organization Tree for the company
     * 
     * @param response
     * @throws IOException
     */
    //TODO: See if Accepting CompanyId makes sense. 
    // Assumption is that Api Token is unique to the company 
    // therefore the Service will always work with single company
    @RequestMapping(value="/display", method=RequestMethod.GET)
    public void displayOrgTree(HttpServletResponse response) throws IOException {
        LOG.info("Generating OrgTree");
        
        Employee employee = employeeService.getCEO();
        EmployeeUIModel orgTree = employeeService.getOrgTree(employee);
        
        String jsonStr = JsonUtil.convertToJson(orgTree);
        LOG.debug(jsonStr);
        
        response.setContentType("application/json");
        response.getWriter().write(jsonStr);
    }
    
    //TODO: Enhancement to load subordinates on demand instead of loading the full Org Tree
    @RequestMapping(value="/subs", method=RequestMethod.GET)
    public void getSubordinates(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String empId = request.getParameter("id");
        String subUrl = request.getParameter("url");
        
        LOG.info("Fetching subordinates for empId: " + empId + " subordinates URL: " + subUrl);
        EmployeeUIModel employeeWithSubs = null; //employeeUtils.getSubordinates(empId, subUrl);
        String jsonStr = JsonUtil.convertToJson(employeeWithSubs);
        LOG.debug(jsonStr);
        
        response.setContentType("application/json");
        response.getWriter().write(jsonStr);
    }
    
}
