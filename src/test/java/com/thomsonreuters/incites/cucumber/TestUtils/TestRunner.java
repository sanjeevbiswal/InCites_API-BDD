package com.thomsonreuters.incites.cucumber.TestUtils;

import com.thomsonreuters.cucumberfactory.typesfactory.Factory.TypeFactory;
import com.thomsonreuters.grs.cucumberreport.GrsReport;
import com.thomsonreuters.incites.cucumber.helpers.ConfigReader;
import com.thomsonreuters.incites.cucumber.helpers.baseElements;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", /*"json:target/cucumber-report.json",*/ "junit:target/cucumber-junit-report/cuc.xml"}
        , glue = {"com.thomsonreuters.incites.cucumber"}
        , features = {"src/test/resources"}
        , tags = {"@verify_Add_OrgEntity_0001"}
)

public class TestRunner extends baseElements {

    @BeforeClass
    public static void BeforeClass() {
        grsReport = new GrsReport(ConfigReader.URL, ConfigReader.TEST_CYCLE, "http://grs.amers1.cis.trcloud/nggrs/harvest/ServiceConnector.asmx", "WOK_API");
        TypeFactory.setReport(grsReport);
    }

    @AfterClass
    public static void CreateReport() throws Exception {

        grsReport.closeSuite();
/*
        Scenario scenario = BeforeAfter.lastScenario;
        Field f = null;
        try {
            f = scenario.getClass().getDeclaredField("reporter");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        f.setAccessible(true);
        JUnitReporter reporter = (JUnitReporter) f.get(scenario);

        reporter.done();
        reporter.close();
        GReport.setUrl("http://grs.amers1.cis.trcloud/nggrs/harvest/ServiceConnector.asmx");
        GReport.Deliver("InCites");
*/
    }

}
