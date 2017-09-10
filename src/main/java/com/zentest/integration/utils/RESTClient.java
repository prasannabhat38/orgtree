package com.zentest.integration.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RESTClient {
    private static final Log LOG = LogFactory.getLog(RESTClient.class);
    
    private static final String AUTH_HEADER = "Authorization";
    
    @Value("${api.key}")
    private String API_KEY;
    
    @Value("${api.base.url}")
    private String BASE_URL;
    
    public String doGet(String url) throws ClientProtocolException {
        CloseableHttpClient client = HttpClients.createDefault();

        LOG.info("URL: " + url);
        HttpGet httpGet = new HttpGet(url);
        setRequestHeaders(httpGet);

        String response = executeRequest(client, httpGet);
        LOG.debug("Response: {}" + response);

        return response;
    }
    
    private void setRequestHeaders(HttpGet httpGet) {
        httpGet.addHeader(AUTH_HEADER, "Bearer " + this.API_KEY);
    }
    
    private static String executeRequest(CloseableHttpClient client, HttpUriRequest httpRequest) throws ClientProtocolException {
        String response = null;

        try {
            LOG.debug("Executing request");
            response = client.execute(httpRequest, new RESTClient.HttpResponseHandler());
        } catch(ClientProtocolException e) {
            LOG.error("Exception occurred", e);
            throw e;
        } catch (IOException e) {
            LOG.error("Exception occurred", e);
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch(IOException e) {
                LOG.error("Exception occurred while closing connection", e);
                throw new RuntimeException(e);
            }
        }

        return response;
    }
    
    static class HttpResponseHandler implements ResponseHandler<String> {

        @Override
        public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            int status = httpResponse.getStatusLine().getStatusCode();
            LOG.debug("Http status code: " + status);
            HttpEntity entity = httpResponse.getEntity();

            if (status >= 200 && status < 300) {
                return entity != null ? EntityUtils.toString(entity) : null;
            }

            LOG.error("Unexpected response status: " +  status);
            throw new ClientProtocolException(EntityUtils.toString(entity));
        }
    }

    public String formURL(String path) {
        return BASE_URL + path;
    }
}
