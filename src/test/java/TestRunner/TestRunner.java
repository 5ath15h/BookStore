package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "../BookStore/src/test/resources/Features/getbooks.feature", glue = "stepdefinition")
public class TestRunner {
}
