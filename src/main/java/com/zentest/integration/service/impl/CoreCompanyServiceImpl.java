package com.zentest.integration.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zentest.integration.service.CoreCompanyService;
import com.zentest.integration.utils.JsonUtil;
import com.zentest.integration.utils.RESTClient;

@Service
public class CoreCompanyServiceImpl implements CoreCompanyService{
    private static final Log LOG = LogFactory.getLog(CoreCompanyServiceImpl.class);
    
    public static final String COMPANIES_PATH = "companies";
    
    @Autowired
    private RESTClient restClient;
    
    @Override
    public String getCompanyId() {
        LOG.info("Fetching company id");
        
        try {
            String url = restClient.formURL(COMPANIES_PATH);
            String response = restClient.doGet(url);
            String companyId = JsonUtil.getCompanyId(response);
            
            LOG.info("Company Id: " + companyId);
            return companyId;
        } catch (ClientProtocolException e) {
            LOG.error("Exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPeopleURLEndPoint() {
        LOG.info("Fetching URL for people");
        
        try {
            String url = restClient.formURL(COMPANIES_PATH);
            String response = restClient.doGet(url);
            String peopleURL = JsonUtil.getPeopleURL(response);
            
            LOG.info("People URL : " + peopleURL);
            return peopleURL;
        } catch (ClientProtocolException e) {
            LOG.error("Exception occurred", e);
            throw new RuntimeException(e);
        }
    }

}
