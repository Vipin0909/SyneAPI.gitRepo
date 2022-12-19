
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features/UnixConnect.feature",glue= {"stepDefination"},
plugin={ 
		}
)
public class TestRunner {

	// this is change in the file

}