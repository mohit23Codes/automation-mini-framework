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

public class GoogleSearchTestNGTest extends BaseTest{
	
	private static final Logger log = LogManager.getLogger(GoogleSearchTestNGTest.class);
	
	
	@Test(groups = {"regression", "ui"})
	public void searchTestNgTest() {

		log.info("Thread: {} - Running searchSearchTestNgTest", Thread.currentThread().getName());

		GoogleHomePage googleHomePage = new GoogleHomePage();
		GoogleResultsPage googleResultsPage = googleHomePage.searchTopic("TestNG Parallel Run");

		int count = googleResultsPage.getResultCount();

		log.info("Found {} results for Test NG Parallel Execution", count); // Log number of results

		Assert.assertTrue(count > 0, // Condition: expect at least one result
				"Expected at least one result for TestNG Parallel Run");

	}

}
