package com.thomsonreuters.incites.cucumber.helpers;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

import java.util.HashMap;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 2/18/2016.
 */
public class GRSConfigurationNew {

        public static HashMap<String, String> read() {
            XMLConfiguration config = null;
            HashMap map = new HashMap();

            try {
                config = new XMLConfiguration("GRS_Configuration.xml");
                config.setExpressionEngine(new XPathExpressionEngine());
                map.put("url", config.getString("//url"));
                map.put("backdoor", config.getString("//backdoor"));
                map.put("key",config.getString("//key"));
                map.put("profile", config.getString("//profile"));
                map.put("targetTestCycle", config.getString("//targetTestCycle"));
                map.put("task", config.getString("//task"));
                map.put("postRequestKey",config.getString("//postRequestKey"));
                return map;
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
}
