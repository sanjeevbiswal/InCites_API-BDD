package com.thomsonreuters.incites.cucumber.Pages_Definition;

import com.thomsonreuters.incites.cucumber.Base.Instances;
import com.thomsonreuters.incites.cucumber.Base.URLConstructor;
import com.thomsonreuters.incites.cucumber.helpers.DBConnector;
import com.thomsonreuters.incites.cucumber.helpers.XmlProcessor;
import com.thomsonreuters.incites.cucumber.jerseyHelpers.JerseyClient;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/18/2016.
 */
public class CheckValidUTsStepdefs extends Instances{

    @And("^I have verified the validity of the UT Number \"([^\"]*)\" using the database$")
    public void iHaveVerifiedTheValidityOfTheUTNumberUsingTheDatabase(String UTNumber) throws Throwable {
        DBConnector.getConnection();
        List<String> results=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName("Query_For_Single_UT_Number"),UTNumber));
        if (results.isEmpty()){
            isValidUTNumber=false;
            grsReport.addSubStep(true,String.format("%s UT Number is an Invalid UT Number as per the Database",UTNumber));
        }
        else{
            isValidUTNumber=true;
            grsReport.addSubStep(true,String.format("%s UT Number is a Valid UT Number as per the Database",UTNumber));
        }
        DBConnector.CloseConnection();
    }


    @Then("^The server provided validity should match with the database provided validity$")
    public void theServerProvidedValidityShouldMatchWithTheDatabaseProvidedValidity() throws Throwable {
        JSONObject obj=new JSONObject(responseValues.get("RESPONSE"));
        JSONArray returnedArray=obj.getJSONArray("api").getJSONObject(0).getJSONArray("rval");
        boolean result=(returnedArray.length()==0);
        grsReport.addSubStep(true,result?"Given UT Number is a Valid UT Number as per the Server ":"Given UT Number is not a Valid UT Number as per the Server ");
        grsReport.addSubStep((result==isValidUTNumber),(result==isValidUTNumber)?"Server UT Number validation and Database UT Number validation both are matching":"Server UT Number validation and Database UT Number validation both are not matching");
    }

    @Given("^I have got the response from Incites API path \"([^\"]*)\" in \"([^\"]*)\" format with an additional parameter \"([^\"]*)\" with the below multiple values$")
    public void iHaveGotTheResponseFromIncitesAPIPathInFormatWithAnAdditionalParameterWithTheBelowMultipleValues(String apiPath, String format, String additionalParam,List<String> data) throws Throwable {
        String acceptType=null;
        if(format.equalsIgnoreCase("json")){
            acceptType="application/json";
        }
        //as of now taking default format as json only
        else {
            acceptType="application/json";
        }
        sampleData=data;

        Map<String,String> requestParameters=new HashMap<String,String>();
        StringBuffer additionalParameters=new StringBuffer(additionalParam.trim());
        additionalParameters.append("=");
        data.forEach(str->additionalParameters.append(str+","));
        additionalParameters.deleteCharAt(((additionalParameters.length())-1));
        requestParameters.put("URL", URLConstructor.constructURL(apiPath,additionalParameters.toString(),format));
        requestParameters.put("ACCEPT_TYPE",acceptType);

        responseValues=JerseyClient.getResponseAsMapValues(requestParameters);
    }

    @And("^I have verified the validity of the above mentioned UT Numbers using the database$")
    public void iHaveVerifiedTheValidityOfTheAboveMentionedUTNumbersUsingTheDatabase() throws Throwable {
        DBConnector.getConnection();
        StringBuffer UTNumbers=new StringBuffer();
        sampleData.forEach(str->UTNumbers.append("'"+str+"',"));
        UTNumbers.deleteCharAt(((UTNumbers.length())-1));
        List<String> results=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName("Query_For_Multiple_UT_Numbers"),UTNumbers));
        resultFromDB=new HashMap<String,Boolean>();
        if (results.isEmpty()){
            sampleData.forEach(str->resultFromDB.put(str,Boolean.FALSE));
            grsReport.addSubStep(true,String.format("All the UT Numbers are Invalid as per the Database"));
        }
        else{
            Iterator<String> itr=sampleData.iterator();
            while(itr.hasNext()){
                String utnumber=itr.next();
                Iterator<String> itr2=results.iterator();
                while (itr2.hasNext()){
                    if(utnumber.equalsIgnoreCase(itr2.next())){
                        resultFromDB.put(utnumber,Boolean.TRUE);
                        break;
                    }
                }
                resultFromDB.putIfAbsent(utnumber,Boolean.FALSE);
            }
            grsReport.addSubStep(true,"Verified all the given UT Numbers with Database and Stored the results in a Map");
        }
        DBConnector.CloseConnection();
    }

    @And("^I have extracted the validity of the above mentioned UT Numbers from the server api$")
    public void iHaveExtractedTheValidityOfTheAboveMentionedUTNumbersFromTheServerApi() throws Throwable {
        JSONObject obj=new JSONObject(responseValues.get("RESPONSE"));
        JSONArray returnedArray=obj.getJSONArray("api").getJSONObject(0).getJSONArray("rval");
        int length=returnedArray.length();
        resultFromAPI=new HashMap<String,Boolean>();
        if(length==0){
            sampleData.forEach(str->resultFromAPI.put(str,Boolean.TRUE));
            grsReport.addSubStep(true,"All the given UT Numbers are Valid as per the Server API");
        }
        else{
            for(int i=0;i<length;i++){
                String utnumber=(String)returnedArray.getJSONObject(i).get("ISI_LOC");
                resultFromAPI.put(utnumber,Boolean.FALSE);
            }
            sampleData.forEach(str->resultFromAPI.putIfAbsent(str,Boolean.TRUE));
            grsReport.addSubStep(true,"All the given UT Numbers validity has been extracted from Server API");
        }
    }

    @Then("^The server provided results should match with the results obtained from database$")
    public void theServerProvidedResultsShouldMatchWithTheResultsObtainedFromDatabase() throws Throwable {
        sampleData.forEach(str->{
            boolean result=((resultFromAPI.get(str).booleanValue())==(resultFromDB.get(str).booleanValue()));
            grsReport.addSubStep(result,result?String.format("%s UT Number is %s according to both database and Server API",str,resultFromAPI.get(str).booleanValue()?"Valid":"not Valid"):String.format("%s UT Number validation results differs from API and Database. Database Validity is: ",str,resultFromDB.get(str).booleanValue()?"Valid":"not Valid"));
        });
    }
}
