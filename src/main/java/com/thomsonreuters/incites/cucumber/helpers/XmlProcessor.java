package com.thomsonreuters.incites.cucumber.helpers;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by Nagasandeep.Banda@thomsonreuters.com on 02/19/2016.
 */
public class XmlProcessor {

    private Document doc;

    public XmlProcessor() {
    }

    public static String getSqlQueryByScenarioName(String scName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("SQLqueries.xml");

            XPathFactory xpathfactory = XPathFactory.newInstance();
            XPath xpath = xpathfactory.newXPath();

            XPathExpression expr = xpath.compile(String.format("//SQLqueries//query[@query_name='%s']", scName));
            String result = expr.evaluate(doc);
            return result;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }


        return null;
    }

}
