package com.thomsonreuters.incites.cucumber.Pages_Definition;

import com.thomsonreuters.grs.cucumberreport.GrsMimeType;
import com.thomsonreuters.incites.cucumber.Base.Instances;
import com.thomsonreuters.incites.cucumber.Base.URLConstructor;
import com.thomsonreuters.incites.cucumber.helpers.DBConnector;
import com.thomsonreuters.incites.cucumber.helpers.XmlProcessor;
import com.thomsonreuters.incites.cucumber.jerseyHelpers.JerseyClient;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 3/15/2016.
 */
public class PostRequestAPIStepdefs extends Instances{

    @Given("^I have verified the presence of \"(.*?)\" having value \"(.*?)\" from the database using the query \"(.*?)\" and saved the result$")
    public void i_have_verified_the_presence_of_having_value_from_the_database_using_the_query_and_saved_the_result(String param, String value, String queryName) throws Throwable {
        inputValue=value;
        temp=param;
        DBConnector.getConnection();
        List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),inputValue));
        if (result.isEmpty()){
            rvalFromDBBeforeAPICall=false;
            grsReport.addSubStep(true,String.format("\"%s\" with value \"%s\" is not present in the Database Before the API Call",param,value));
        }
        else{
            rvalFromDBBeforeAPICall=true;
            grsReport.addSubStep(true,String.format("\"%s\" with value \"%s\" is present in the Database Before the API Call",param,value));
        }
        DBConnector.CloseConnection();
    }

    @Given("^I got the response from Incites API path \"(.*?)\" using \"(.*?)\" request with input parameter \"(.*?)\" having value as \"(.*?)\"$")
    public void i_got_the_response_from_Incites_API_path_using_request_with_input_parameter_having_value_as(String apiPath, String requestType, String param, String value) throws Throwable {
        temp=param;
        String inputJSONString="[{"+"\""+param+"\":\""+inputValue+"\"}]";
        grsReport.addAttachment("Generated below Post Request Input:\n\n"+inputJSONString,GrsMimeType.TXT);
        response= JerseyClient.getResponseFromPostRequest(URLConstructor.constructURLForPOSTRequest(apiPath),"application/json",inputJSONString);
        grsReport.addSubStep(true,"Recieved the Response From API and the response text file is attached in GRS");
    }

    @Given("^I have extracted the result of the API performed operation from the API provided response$")
    public void i_have_extracted_the_result_of_the_API_performed_operation_from_the_API_provided_response() throws Throwable {
        JSONObject obj=new JSONObject(response);
        rvalFromAPI=obj.getJSONArray("api").getJSONObject(0).getInt("rval");
        grsReport.addSubStep(true,String.format("Found rVal as \"%s\" from the API response",rvalFromAPI));
    }

    @Given("^I have extracted the result of the API performed operation from the database using the query \"(.*?)\"$")
    public void i_have_extracted_the_result_of_the_API_performed_operation_from_the_database_using_the_query(String queryName) throws Throwable {
        DBConnector.getConnection();
        List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),inputValue));
        if (result.isEmpty()){
            rvalFromDBAfterAPICall=false;
            grsReport.addSubStep(true,String.format("\"%s\" with value \"%s\" is not present in the Database After the API Call",temp,inputValue));
        }
        else{
            rvalFromDBAfterAPICall=true;
            grsReport.addSubStep(true,String.format("\"%s\" with value \"%s\" is present in the Database After the API Call",temp,inputValue));
        }
        DBConnector.CloseConnection();
    }

    @Then("^both the API and the database results should be matched$")
    public void both_the_API_and_the_database_results_should_be_matched() throws Throwable {
        if(rvalFromAPI==0){
            if(rvalFromDBBeforeAPICall==rvalFromDBAfterAPICall){
                grsReport.addSubStep(true,"Results Matched: API hasn't made any changes to Database and response is correct");
            }
            else {
                grsReport.addSubStep(false,"Results are not Matching: API response is incorrect and it may be malfunctioning");
            }
        }
        else if(rvalFromAPI==1){
            if(rvalFromDBBeforeAPICall==(!rvalFromDBAfterAPICall)){
                grsReport.addSubStep(true,"Results Matched: API has made changes to Database and response is correct");
            }
            else {
                grsReport.addSubStep(false,"Results are not Matching: API response is incorrect and it may be malfunctioning");
            }
        }
        else{
            grsReport.addSubStep(false,String.format("API is returning neither 0 value nor 1 value and it may be malfunctioning. Rval from API \"%s\"",rvalFromAPI));
        }
    }

    @Given("^I have verified the presence of parameters \"(.*?)\" having values \"(.*?)\" from the database using the query \"(.*?)\" and saved the result$")
    public void i_have_verified_the_presence_of_parameters_having_values_from_the_database_using_the_query_and_saved_the_result(String parameters, String values, String queryName) throws Throwable {
        inputValue=values;
        temp=parameters;
        DBConnector.getConnection();
        List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),inputValue.split(";")));
        if (result.isEmpty()){
            rvalFromDBBeforeAPICall=false;
            grsReport.addSubStep(true,"Row not found in the Database containing the passed values Before the API Call");
        }
        else{
            rvalFromDBBeforeAPICall=true;
            grsReport.addSubStep(true,"Rows found in the Database containing the passed values Before the API Call");
        }
        DBConnector.CloseConnection();
    }

    @Given("^I got the response from Incites API path \"(.*?)\" using \"(.*?)\" request with input parameters \"(.*?)\" having values as \"(.*?)\"$")
    public void i_got_the_response_from_Incites_API_path_using_request_with_input_parameters_having_values_as(String apiPath, String requestType, String parameters, String values) throws Throwable {
        temp=parameters;
        inputValue=values;
        StringBuffer str=new StringBuffer("");
        str.append("[{");
        String[] params=parameters.split(";");
        String[] vals=values.split(";");
        if(params.length!=vals.length)
            grsReport.addException("Input Data passed to test case is in incorrect format",false);
        for(int i=0;i<params.length;i++){
            str.append("\""+params[i]+"\":\""+vals[i]+"\",");
        }
        str.deleteCharAt((str.length())-1);
        str.append("}]");
        String inputJSONString=str.toString();
        grsReport.addAttachment("Generated below Post Request Input:\n\n"+inputJSONString,GrsMimeType.TXT);
        response= JerseyClient.getResponseFromPostRequest(URLConstructor.constructURLForPOSTRequest(apiPath),"application/json",inputJSONString);
        grsReport.addSubStep(true,"Recieved the Response From API and the response text file is attached in GRS");
    }

    @Given("^I have extracted the results of the API performed operation from the database using the query \"(.*?)\" for the passed parameters$")
    public void i_have_extracted_the_results_of_the_API_performed_operation_from_the_database_using_the_query_for_the_passed_parameters(String queryName) throws Throwable {
        DBConnector.getConnection();
        List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),inputValue.split(";")));
        if (result.isEmpty()){
            rvalFromDBAfterAPICall=false;
            grsReport.addSubStep(true,"Row not found with passed parameters in the Database After the API Call");
        }
        else{
            rvalFromDBAfterAPICall=true;
            grsReport.addSubStep(true,"Rows found with passed parameters in the Database After the API Call");
        }
        DBConnector.CloseConnection();
    }

    @Given("^I have verified the presence of below given parameters for all the mentioned rows from the database using the query \"(.*?)\" and saved the results$")
    public void i_have_verified_the_presence_of_below_given_parameters_for_all_the_mentioned_rows_from_the_database_using_the_query_and_saved_the_results(String queryName, List<String> inputValues) throws Throwable {
        temp=inputValues.get(0);
        sampleData=inputValues.subList(1,inputValues.size());
        resultFromDB=new HashMap<String,Boolean>();
        DBConnector.getConnection();
        for(String str:sampleData){
            List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),str.split(";")));
            if (result.isEmpty()){
                resultFromDB.put(str,false);
                grsReport.addSubStep(true,String.format("Row not found in the Database for \"%s\" Before the API Call",str));
            }
            else{
                resultFromDB.put(str,true);
                grsReport.addSubStep(true,String.format("Rows found in the Database for \"%s\" Before the API Call",str));
            }
        }
        DBConnector.CloseConnection();
    }

    @Given("^I have verified the presence of database rows for all the below mentioned entities from the database using the query \"(.*?)\" and saved the results$")
    public void i_have_verified_the_presence_of_database_rows_for_all_the_below_mentioned_entities_from_the_database_using_the_query_and_saved_the_results(String queryName, List<String> inputValues) throws Throwable {
        temp=inputValues.get(0);
        sampleData=inputValues.subList(1,inputValues.size());
        resultFromDB=new HashMap<String,Boolean>();
        DBConnector.getConnection();
        for(String str:sampleData){
            List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),str.split(";")));
            if (result.isEmpty()){
                resultFromDB.put(str,false);
                grsReport.addSubStep(true,String.format("Row not found in the Database with same ID for \"%s\" Before the API Call",str));
            }
            else{
                resultFromDB.put(str,true);
                grsReport.addSubStep(true,String.format("Row already exists in the Database with same ID for \"%s\" Before the API Call",str));
            }
        }
        DBConnector.CloseConnection();
    }

    @Given("^I got the response from Incites API path \"(.*?)\" using \"(.*?)\" request with above mentioned input parameters having values as mentioned above$")
    public void i_got_the_response_from_Incites_API_path_using_request_with_above_mentioned_input_parameters_having_values_as_mentioned_above(String apiPath, String requestType) throws Throwable {
        StringBuffer str=new StringBuffer("");
        str.append("[");
        String[] params=temp.split(";");
        for(String row:sampleData){
            str.append("{");
            String[] vals=row.split(";");
            if(params.length!=vals.length)
                grsReport.addException("Input Data passed to test case is in incorrect format",false);
            for(int i=0;i<params.length;i++){
                str.append("\""+params[i]+"\":\""+vals[i]+"\",");
            }
            str.deleteCharAt((str.length())-1);
            str.append("},");
        }
        str.deleteCharAt((str.length())-1);
        str.append("]");
        String inputJSONString=str.toString();
        grsReport.addAttachment("Generated below Post Request Input:\n\n"+inputJSONString,GrsMimeType.TXT);
        response= JerseyClient.getResponseFromPostRequest(URLConstructor.constructURLForPOSTRequest(apiPath),"application/json",inputJSONString);
        grsReport.addSubStep(true,"Recieved the Response From API and the response text file is attached in GRS");
    }

    @Given("^I have extracted the results of the API performed operation from the database using the query \"(.*?)\" for the above mentioned values$")
    public void i_have_extracted_the_results_of_the_API_performed_operation_from_the_database_using_the_query_for_the_above_mentioned_values(String queryName) throws Throwable {
        resultFromAPI=new HashMap<String,Boolean>();
        DBConnector.getConnection();
        for(String str:sampleData){
            List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),str.split(";")));
            if (result.isEmpty()){
                resultFromAPI.put(str,false);
                grsReport.addSubStep(true,String.format("Row not found in the Database for \"%s\" After the API Call",str));
            }
            else{
                resultFromAPI.put(str,true);
                grsReport.addSubStep(true,String.format("Rows found in the Database for \"%s\" After the API Call",str));
            }
        }
        DBConnector.CloseConnection();
    }

    @Then("^both the API and the database results should be matched for above mentioned values$")
    public void both_the_API_and_the_database_results_should_be_matched_for_above_mentioned_values() throws Throwable {
        if(rvalFromAPI==0){
            resultFromDB.forEach((row,result)->{
                if(resultFromAPI.get(row)!=result){
                    grsReport.addSubStep(false,String.format("Results not Matched for \"%s\": API may be malfunctioning",row));
                }
                else {
                    grsReport.addSubStep(true,String.format("Results Matched for \"%s\"",row));
                }
            });
        }
        else if(rvalFromAPI==1){
            resultFromDB.forEach((row,result)->{
                if(resultFromAPI.get(row)!=result){
                    grsReport.addSubStep(true,String.format("API inserted a row for \"%s\"",row));
                    countOfChangesMade++;
                }
                else {
                    grsReport.addSubStep(true,String.format("API hasn't made database changes for the row \"%s\"",row));
                }
            });
            if (countOfChangesMade==0){
                grsReport.addSubStep(false,"API hasn't affected a single row in the database but returning 1 value");
            }
            else {
                grsReport.addSubStep(true,"API has made changes to the database for some of the passed values");
            }
        }
        else{
            grsReport.addSubStep(false,"API is returning neither 0 value nor 1 value and it may be malfunctioning");
        }
    }

    @Given("^I have extracted the API response and validated it against database using the query\"(.*?)\" and stored the results$")
    public void i_have_extracted_the_API_response_and_validated_it_against_database_using_the_query_and_stored_the_results(String queryName) throws Throwable {
        JSONObject obj=new JSONObject(response);
        JSONArray rval=obj.getJSONArray("api").getJSONObject(0).getJSONArray("rval");
        resultFromAPI=new HashMap<String,Boolean>();
        DBConnector.getConnection();
        for (int i=0;i<rval.length();i++){
            if(rval.getString(i).contains("already exists")){
                resultFromAPI.put(sampleData.get(i),false);
            }
            else if(rval.getString(i).equalsIgnoreCase("1")){
                List<String> result=DBConnector.getDataFromTable(String.format(XmlProcessor.getSqlQueryByScenarioName(queryName),sampleData.get(i).split(";")));
                if (result.isEmpty()){
                    grsReport.addSubStep(false,"API is returning rval as \"1\" for "+sampleData.get(i)+" but it hasn't made changes to Database");
                }
                else{
                    resultFromAPI.put(sampleData.get(i),true);
                    grsReport.addSubStep(true,String.format("Row has been created in database for \"%s\" by the API",sampleData.get(i)));
                }

            }
            else{
                grsReport.addException("There is some problem with API response. Please check the attached API response for \"rval\"",false);
            }
        }
        DBConnector.CloseConnection();
        grsReport.addSubStep(true,"Successfully extracted the response from API and compared it against Database");
    }

    @Then("^the API response results should be matched with database results$")
    public void the_API_response_results_should_be_matched_with_database_results() throws Throwable {
        resultFromDB.forEach((row,result)->{
            if(resultFromAPI.get(row)==result){
                grsReport.addSubStep(false,String.format("Results not matched for \"%s\"",row));
            }
            else {
                grsReport.addSubStep(true,String.format("Results matched for \"%s\"",row));
            }
        });
    }
}
