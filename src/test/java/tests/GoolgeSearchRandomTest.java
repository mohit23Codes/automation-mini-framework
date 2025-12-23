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

public class GoolgeSearchRandomTest extends BaseTest{
	
	private static final Logger log = LogManager.getLogger(GoolgeSearchRandomTest.class);
	
	@Test
	public void searchShootingStars() {

		log.info("Thread: {} - Running searchSeleniumWebDriver", Thread.currentThread().getName());

		GoogleHomePage googleHomePage = new GoogleHomePage();
		GoogleResultsPage googleResultsPage = googleHomePage.searchTopic("Shooting Stars");

		int count = googleResultsPage.getResultCount();

		log.info("Found {} results for Selenium WebDriver", count); // Log number of results

		Assert.assertTrue(count > 0, // Condition: expect at least one result
				"Expected at least one result for Shooting Stars");

	}

}
