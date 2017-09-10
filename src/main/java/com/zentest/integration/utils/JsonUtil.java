package com.zentest.integration.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zentest.integration.model.Employee;
import com.zentest.integration.model.EmployeeUIModel;

public class JsonUtil {
    private static Log LOG = LogFactory.getLog(JsonUtil.class);
    private static Gson gson;

    static {
        gson = new Gson();
    }
    
    public static String getMetaDataAttribute(String jsonStr, String attrName){
        JsonObject response  = gson.fromJson(jsonStr, JsonObject.class);
        
        if(response.get("data") != null) {
            JsonObject jsonObj = response.get("data").getAsJsonObject();
            if(jsonObj.get(attrName) != null)
                if(jsonObj.get(attrName) != null && !jsonObj.get(attrName).isJsonNull()) 
                    return jsonObj.get(attrName).getAsString();
        }
        
        return null;
    }
    
    public static JsonArray getArrayData(String jsonStr) {
        JsonObject response  = gson.fromJson(jsonStr, JsonObject.class);
        
        if(response.get("data") != null) {
            JsonArray arr = response.get("data").getAsJsonObject()
                                    .get("data").getAsJsonArray();
            
            return arr;
        }
        
        return new JsonArray();
    }
    
    public static String getPeopleURL(String companyJsonStr) {
        JsonArray jsonArr = getArrayData(companyJsonStr);
        
        if(jsonArr.size() != 0) {
            JsonObject jsonObj = jsonArr.get(0).getAsJsonObject();
            return jsonObj.get(AttributeConstants.PEOPLE)
                            .getAsJsonObject().get(AttributeConstants.URL).getAsString();
        }
        
        return null;
    }
    
    public static String getCompanyId(String companyJsonStr) {
        JsonArray jsonArr = getArrayData(companyJsonStr);
        
        if(jsonArr.size() != 0) {
            JsonObject jsonObj = jsonArr.get(0).getAsJsonObject();
            return jsonObj.get(AttributeConstants.ID).getAsString();
        }
        
        return null;
    }
    
    public static List<Employee> getEmployees(String jsonStr) {
        List<Employee> employees = new ArrayList<>();
        
        JsonArray empArr = getArrayData(jsonStr);
        
        if(empArr.size() != 0) {
            Type listType = new TypeToken<List<Employee>>() {}.getType();
            employees = gson.fromJson(empArr.toString(), listType);
        }
         
        return employees;
    }
    
    public static String convertToJson(List<Employee> employees) {
        StringBuilder jsonStr = new StringBuilder();
        for(Employee emp : employees) {
            jsonStr.append(gson.toJson(emp));
        }
        
        return jsonStr.toString();
    }
    
    public static String convertToJson(EmployeeUIModel empWrapper) {
        return gson.toJson(empWrapper);
    }
}
