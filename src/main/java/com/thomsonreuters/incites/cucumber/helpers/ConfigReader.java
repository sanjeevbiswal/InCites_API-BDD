package com.thomsonreuters.incites.cucumber.helpers;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

import java.util.HashMap;

/**
 * Created by mixeyes on 11/21/2014.
 * Modified by Nagasandeep.Banda@thomsonreuters.com on 18/02/2016
 */
public class ConfigReader extends baseElements{

    public static final String URL;
    public static final String BASEPATH;
    public static final String postRequestBASEPATH;
    public static final String KEY;
    public static final String postRequestKey;
    public static final String DB_HOST;
    public static final String DB_SERVICE_NAME;
    public static final String DB_USER_ID;
    public static final String DB_PASSWORD;
    public static final String TEST_CYCLE;

    private static XMLConfiguration config;

    static {
        try {
            config = new XMLConfiguration("config.xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        config.setExpressionEngine(new XPathExpressionEngine());
        HashMap<String, String> grs = GRSConfigurationNew.read();
        config.setExpressionEngine(new XPathExpressionEngine());
        String env = grs.get("url");

        KEY=grs.get("key");
        postRequestKey=grs.get("postRequestKey");
        URL = config.getString(String.format("//envvar[@id='%s']//url", env));
        BASEPATH=config.getString(String.format("//envvar[@id='%s']//basepath", env));
        postRequestBASEPATH=config.getString(String.format("//envvar[@id='%s']//postRequestBasePath", env));

        DB_HOST = config.getString(String.format("//db[@id='%s']/host", env));
        DB_SERVICE_NAME = config.getString(String.format("//db[@id='%s']//service_name", env));
        DB_USER_ID = config.getString(String.format("//db[@id='%s']//user_id", env));
        DB_PASSWORD = config.getString(String.format("//db[@id='%s']//password", env));
        TEST_CYCLE = grs.get("targetTestCycle");
    }


}
