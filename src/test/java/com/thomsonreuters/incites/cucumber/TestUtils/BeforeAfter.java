package com.thomsonreuters.incites.cucumber.TestUtils;

import com.thomsonreuters.incites.cucumber.Base.Instances;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BeforeAfter extends Instances {

    public static Scenario lastScenario;

    @Before
    public void setUp(Scenario scenario) {
        grsReport.setScenario(scenario,false);
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        try {
/*
            if (scenario.isFailed()) {
                //re-run test doesn't work
                //new Cucumber(TestRunner.class).run(new RunNotifier());
                takeScreenshot(scenario);
            }
*/
            grsReport.closeTest();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lastScenario = scenario;
            lastScenario.write(configReader.URL);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
                String name = String.format("target/htmlreports/%s", sdf.format(new Date()));
                FileUtils.copyDirectoryToDirectory(new File("target/cucumber-htmlreport"), new File(name));

                FileUtils.writeStringToFile(FileUtils.getFile(name + "/cucumber-htmlreport/report.js"), "});", true);
            } catch (FileNotFoundException e) {
            }
        }
        DeleteAllPagesInstances();
    }

    private void DeleteAllPagesInstances() {
        Instances pages = new Instances();
        try {
            Field[] fields = pages.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String type = field.getType().getTypeName();
                String name = field.getName();
                if (type.equalsIgnoreCase("int"))
                    field.set(pages, 0);
                else if (type.equalsIgnoreCase("boolean"))
                    field.set(pages, false);
                else field.set(pages, null);

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
