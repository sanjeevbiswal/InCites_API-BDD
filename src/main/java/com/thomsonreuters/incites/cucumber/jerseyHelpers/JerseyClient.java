package com.thomsonreuters.incites.cucumber.jerseyHelpers;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.thomsonreuters.grs.cucumberreport.GrsMimeType;
import com.thomsonreuters.incites.cucumber.Base.Instances;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/18/2016.
 */
public class JerseyClient extends Instances{

    public static Map<String,String> getResponseAsMapValues(Map<String,String> requestProperties){
        Client client = Client.create();
        WebResource resourse = client.resource(requestProperties.get("URL"));
        requestProperties.putIfAbsent("ACCEPT_TYPE","application/json");
        ClientResponse response = resourse.accept(requestProperties.get("ACCEPT_TYPE")).get(ClientResponse.class);
        String responseString=response.getEntity(String.class);

        if (response.getStatus() != 200) {
            grsReport.addAttachment("Constructed Below URL:\n\n"+requestProperties.get("URL")+"\n\nRecieved Below Error Response:\n\n"+responseString, GrsMimeType.TXT);
            grsReport.addException("Unable to Fetch Response from Server. HTTP Error Code: " +response.getStatus(), false);
        }
        else{
            grsReport.addSubStep(true,String.format("Recieved SUCCESS HTTP code %s from Server",response.getStatus()));
            grsReport.addAttachment("Constructed Below URL:\n\n"+requestProperties.get("URL")+"\n\nRecieved below Response from Server:\n\n"+responseString, GrsMimeType.TXT);
        }

        Map<String,String> responseValues=new HashMap<>();
        responseValues.put("STATUS", Integer.toString(response.getStatus()));
        responseValues.put("RESPONSE",responseString);

        return responseValues;
    }

    public static String getResponseFromPostRequest(String URL,String acceptType,String postInputString){
        Client client = Client.create();
        WebResource resourse = client.resource(URL);
        ClientResponse response = resourse.accept(acceptType).post(ClientResponse.class,postInputString);
        String responseString=response.getEntity(String.class);

        if (response.getStatus() != 200) {
            grsReport.addAttachment("Constructed Below URL:\n\n"+URL+"\n\nRecieved Below Error Response:\n\n"+responseString, GrsMimeType.TXT);
            grsReport.addException("Unable to Fetch Response from Server. HTTP Error Code: " +response.getStatus(), false);
        }
        else{
            grsReport.addSubStep(true,String.format("Recieved SUCCESS HTTP code %s from Server",response.getStatus()));
            grsReport.addAttachment("Constructed Below URL:\n\n"+URL+"\n\nRecieved below Response from Server:\n\n"+responseString, GrsMimeType.TXT);
        }

        return responseString;
    }

}
