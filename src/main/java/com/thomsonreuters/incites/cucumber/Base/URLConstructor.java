package com.thomsonreuters.incites.cucumber.Base;

import com.thomsonreuters.incites.cucumber.helpers.ConfigReader;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/19/2016.
 */
public class URLConstructor extends Instances{
    public static String constructURL(String apiPath,String additionalParams,String format){
        String additionalParamsURL=null;
        if(!(additionalParams.equalsIgnoreCase("none")||additionalParams.equalsIgnoreCase(""))){
            if (additionalParams.startsWith("&")){
                additionalParamsURL=additionalParams;
            }
            else{
                additionalParamsURL="&"+additionalParams;
            }
        }

        String url=null;
        if (format.equalsIgnoreCase("json")){
            url=(ConfigReader.URL+ConfigReader.BASEPATH)+apiPath+"json?X-TR-API-APP-ID="+ConfigReader.KEY+((additionalParamsURL==null)?"":additionalParamsURL);
        }
        //as of now taking by default format as json
        else {
            url=(ConfigReader.URL+ConfigReader.BASEPATH)+apiPath+"json?X-TR-API-APP-ID="+ConfigReader.KEY+((additionalParamsURL==null)?"":additionalParamsURL);
        }

        grsReport.addSubStep(true,"URL is constructed as below : "+url);
        constructedURL=url;
        return url;
    }

    public static String constructURLForPOSTRequest(String apiPath){
        if (apiPath.charAt((apiPath.length())-1)=='/'){
            apiPath=apiPath.substring(0,((apiPath.length())-1));
        }
        String url=null;
        url=((ConfigReader.URL+ConfigReader.postRequestBASEPATH)+apiPath+"?X-TR-API-APP-ID="+ConfigReader.postRequestKey);
        grsReport.addSubStep(true,"URL is constructed as below : "+url);
        constructedURL=url;
        return url;
    }
}
