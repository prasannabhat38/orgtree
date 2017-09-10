package com.zentest.integration.service;

/**
 * Service class for fetching Company information
 * 
 * @author prasannak
 *
 */
public interface CoreCompanyService {
    /**
     * Fetch uniqueId assigned by Zenefits for the company 
     * @return String Id value
     */
    public String getCompanyId();
    
    /**
     * Fetch the PeopleURL endpoint for the company
     * @return URL corresponding to fetching people/employee for the Company
     */
    public String getPeopleURLEndPoint();
}
