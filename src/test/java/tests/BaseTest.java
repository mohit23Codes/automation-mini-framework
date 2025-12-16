package tests; // Package for test classes

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import config.ConfigManager; // To read base.url
import driver.DriverFactory; // To initialize and quit WebDriver
import org.openqa.selenium.WebDriver; // WebDriver type

public class BaseTest { // Base class for all test classes

    protected WebDriver driver; // Protected so child test classes can use it if needed

    // Runs before each @Test method
	@BeforeMethod(alwaysRun = true) // alwaysRun ensures it runs even if groups/filters are applied
    public void setUp() { // Setup browser before each test method
		
        DriverFactory.initDriver(); // Initialize WebDriver for this thread if not already present
        
        driver = DriverFactory.getDriver(); // Get WebDriver instance from DriverFactory
        
        String baseUrl = ConfigManager.get("base.url");
        driver.get(baseUrl);
        // Navigate to base URL defined in config
    }

    // Runs after each @Test method
	@AfterMethod(alwaysRun = true) // Ensure cleanup always runs
    public void tearDown() { // Cleanup after each test method
        DriverFactory.quitDriver(); // Quit and remove WebDriver for this thread
    }
}
