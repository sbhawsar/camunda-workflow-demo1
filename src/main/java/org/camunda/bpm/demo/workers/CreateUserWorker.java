package org.camunda.bpm.demo.workers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import org.json.JSONObject;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

/**
 * @author Surabhi.Bhawsar
 *
 */
public class CreateUserWorker {

    public static final String JAPIURL = "https://developers.thinkhr-qa.com";
    
    private final static Logger LOGGER = Logger
            .getLogger(CreateUserWorker.class.getName());

    /**
     * @param username
     * @param token
     */
    public static void createUser(String username, String email, String token) {
        
        if (username == null) {
            username = "sample" + UUID.randomUUID();
        } else {
            username = username + UUID.randomUUID();
        }
        
        System.out.println(token);
        JSONObject payload = new JSONObject();
        payload.put("userName", username);
        payload.put("email", email);
        payload.put("firstName", username);
        payload.put("lastName", username);
        payload.put("companyName", "ThinkHR Corporation");
        payload.put("role", "THR Employee");
        payload.put("mobile", "123456789");
        payload.put("phone", "(310) 782-3569");
        payload.put("customField1", "custom value for 1");
        payload.put("customField2", "custom value for 2");
        payload.put("customField3", "custom value for 3");
        payload.put("customField4", "custom value for 4");
        
        System.out.println(payload);
        
        HttpResponse<JsonNode> response = Unirest.post(JAPIURL + "/v1/users?brokerId=8148")
                .header("Authorization", String.join(" ", "Bearer", token))
                .header("Content-Type", "application/json").queryString("suppressEmail", "true")
                .body(payload).asJson();

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getBody().toString());
        } else {
            LOGGER.info("Response of create user " + response) ;
        }
    }
    
    public static String mapToString(Map<String, String> map) {  
        StringBuilder stringBuilder = new StringBuilder();  
       
        for (String key : map.keySet()) {  
         if (stringBuilder.length() > 0) {  
          stringBuilder.append("&");  
         }  
         String value = map.get(key);  
         try {  
          stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));  
          stringBuilder.append("=");  
          stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");  
         } catch (UnsupportedEncodingException e) {  
          throw new RuntimeException("This method requires UTF-8 encoding support", e);  
         }  
        }  
       
        return stringBuilder.toString();  
       }  

}
