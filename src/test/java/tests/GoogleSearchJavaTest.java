package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.GoogleHomePage;
import pages.GoogleResultsPage;

public class GoogleSearchJavaTest extends BaseTest{
	
	
	private static final Logger log = LogManager.getLogger(GoogleSearchJavaTest.class);
	
	@Test(groups = {"smoke"})
	public void searchJava() {

		log.info("Thread: {} - Running searchsearchJava", Thread.currentThread().getName());

		GoogleHomePage googleHomePage = new GoogleHomePage();
		GoogleResultsPage googleResultsPage = googleHomePage.searchTopic("Java");

		int count = googleResultsPage.getResultCount();

		log.info("Found {} results for JAVA", count); // Log number of results

		Assert.assertTrue(count > 0, // Condition: expect at least one result
				"Expected at least one result for Java");
	}


}
