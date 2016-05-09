package com.thomsonreuters.incites.cucumber.Pages_Definition;

import com.thomsonreuters.incites.cucumber.Base.Instances;
import com.thomsonreuters.incites.cucumber.Base.URLConstructor;
import com.thomsonreuters.incites.cucumber.helpers.DBConnector;
import com.thomsonreuters.incites.cucumber.helpers.XmlProcessor;
import com.thomsonreuters.incites.cucumber.jerseyHelpers.JerseyClient;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/18/2016.
 */
public class IncitesLastUpdatedStepdefs extends Instances{


    @Given("^I have got the response from Incites API path \"([^\"]*)\" in \"([^\"]*)\" format with additional parameters \"([^\"]*)\"$")
    public void iHaveGotTheResponseFromIncitesAPIPathInFormatWithAdditionalParameters(String apiPath, String format, String additionalParams) throws Throwable {
        String acceptType=null;
        if(format.equalsIgnoreCase("json")){
            acceptType="application/json";
        }
        //as of now taking default format as json only
        else {
            acceptType="application/json";
        }

        Map<String,String> requestParameters=new HashMap<String,String>();
        requestParameters.put("URL", URLConstructor.constructURL(apiPath,additionalParams,format));
        requestParameters.put("ACCEPT_TYPE",acceptType);

        responseValues=JerseyClient.getResponseAsMapValues(requestParameters);
    }

    @And("^I have extracted the dates \"([^\"]*)\" and \"([^\"]*)\" from the response and the database$")
    public void iHaveExtractedTheDatesAndFromTheResponseAndTheDatabase(String INCITES_DATASET_UPDATED, String WOS_DATASET_INDEXED) throws Throwable {
        if (responseValues.get("STATUS").equalsIgnoreCase("200")){
            datesFromAPI=new HashMap<String,String>();
            JSONObject obj=new JSONObject(responseValues.get("RESPONSE"));
            JSONObject returnedValues=obj.getJSONArray("api").getJSONObject(0).getJSONArray("rval").getJSONObject(0);
            datesFromAPI.put("INCITES_DATASET_UPDATED",(String)returnedValues.get("INCITES_DATASET_UPDATED"));
            datesFromAPI.put("WOS_DATASET_INDEXED",(String)returnedValues.get("WOS_DATASET_INDEXED"));
            grsReport.addSubStep(true,String.format("INCITES_DATASET_UPDATED Date From Server API: '%s'",datesFromAPI.get("INCITES_DATASET_UPDATED")));
            grsReport.addSubStep(true,String.format("WOS_DATASET_INDEXED Date From Server API: '%s'",datesFromAPI.get("WOS_DATASET_INDEXED")));
        }

        datesFromDatabase = new HashMap<String, String>();
        DBConnector.getConnection();
        String date=DBConnector.getDataFromTable(XmlProcessor.getSqlQueryByScenarioName("Get_Incites_Dataset_Updated_Date")).get(0);
        datesFromDatabase.put("INCITES_DATASET_UPDATED", date.split(" ")[0]);
        grsReport.addSubStep((date!=null),(date==null)?"Recieving Null values from Database Check User Rights":String.format("Recieved INCITES_DATASET_UPDATED Date '%s' from DB",date.split(" ")[0]),false);

        date=DBConnector.getDataFromTable(XmlProcessor.getSqlQueryByScenarioName("Get_WOS_Dataset_Indexed_Date")).get(0);
        datesFromDatabase.put("WOS_DATASET_INDEXED", date.split(" ")[0]);
        grsReport.addSubStep((date!=null),(date==null)?"Recieving Null values from Database Check User Rights":String.format("Recieved WOS_DATASET_INDEXED Date '%s' from DB",date.split(" ")[0]),false);

        DBConnector.CloseConnection();

    }

    @Then("^API provided dates \"([^\"]*)\" and \"([^\"]*)\" should match with the database provided date values$")
    public void apiProvidedDatesAndShouldMatchWithTheDatabaseProvidedDateValues(String INCITES_DATASET_UPDATED, String WOS_DATASET_INDEXED) throws Throwable {
        boolean result=datesFromAPI.get(INCITES_DATASET_UPDATED).equalsIgnoreCase(datesFromDatabase.get(INCITES_DATASET_UPDATED));
        grsReport.addSubStep(result,result?String.format("INCITES_DATASET_UPDATED date '%s' is matching with Database Value '%s'",datesFromAPI.get(INCITES_DATASET_UPDATED),datesFromDatabase.get(INCITES_DATASET_UPDATED)):String.format("INCITES_DATASET_UPDATED date '%s' is not matching with Database Value '%s'",datesFromAPI.get(INCITES_DATASET_UPDATED),datesFromDatabase.get(INCITES_DATASET_UPDATED)),false);
        result=datesFromAPI.get(WOS_DATASET_INDEXED).equalsIgnoreCase(datesFromDatabase.get(WOS_DATASET_INDEXED));
        grsReport.addSubStep(result,result?String.format("WOS_DATASET_INDEXED date '%s' is matching with Database Value '%s'",datesFromAPI.get(WOS_DATASET_INDEXED),datesFromDatabase.get(WOS_DATASET_INDEXED)):String.format("WOS_DATASET_INDEXED date '%s' is not matching with Database Value '%s'",datesFromAPI.get(WOS_DATASET_INDEXED),datesFromDatabase.get(WOS_DATASET_INDEXED)),false);
    }

}
