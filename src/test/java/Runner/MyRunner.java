package Runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src\\test\\java\\Features"}, // folder dari feature file
        glue= {"Steps"}, //path stepDefinition files
        plugin = {"pretty","html:target/cucumber-reports","json:target/json_output/cucumber.json",
                "junit:target/junit_xml/cucumber.xml"},
        monochrome = true, //merubah format console jadi lebih manusiawi
        strict = true, //akan check setiap langkah meskipun ada step definition yang terlewat
        dryRun = false // buat check antara step definition file & class
)

public class MyRunner {
}
