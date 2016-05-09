package com.thomsonreuters.incites.cucumber.Base;

import com.thomsonreuters.incites.cucumber.helpers.ConfigReader;
import com.thomsonreuters.incites.cucumber.helpers.baseElements;

import java.util.List;
import java.util.Map;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/18/2016.
 */
public class Instances extends baseElements {
    public static Map<String,String> responseValues;
    public static ConfigReader configReader;
    public static Map<String,String> datesFromAPI;
    public static Map<String,String> datesFromDatabase;
    public static boolean isValidUTNumber;
    public static List<String> sampleData;
    public static Map<String,Boolean> resultFromDB;
    public static Map<String,Boolean> resultFromAPI;
    public static String response;
    public static String inputValue;
    public static int rvalFromAPI;
    public static boolean rvalFromDBBeforeAPICall;
    public static boolean rvalFromDBAfterAPICall;
    public static String temp;
    public static String constructedURL;
    public static int countOfChangesMade=0;
}
