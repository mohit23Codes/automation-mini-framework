package listeners; // Package for listeners

import driver.DriverFactory; // To access current thread's WebDriver
import org.openqa.selenium.OutputType; // Output type for screenshot
import org.openqa.selenium.TakesScreenshot; // Interface for screenshot functionality
import org.openqa.selenium.WebDriver; // WebDriver type

import org.testng.ITestContext; // Test context (suite-level info)
import org.testng.ITestListener; // Interface for test lifecycle events
import org.testng.ITestResult; // Result of each test method

import io.qameta.allure.Allure; // Allure reporting utility

import org.apache.logging.log4j.Logger; // Logger
import org.apache.logging.log4j.LogManager; // Logger factory

import java.io.File; // For file operations
import java.io.IOException; // For IO exceptions
import java.io.InputStream; // For Allure attachment
import java.nio.file.Files; // For copying files
import java.nio.file.StandardCopyOption; // For copy options
import java.text.SimpleDateFormat; // For timestamp formatting
import java.util.Date; // For current date-time

public class TestListener implements ITestListener { // Listener for test events

    // Logger for this listener
    private static final Logger log = LogManager.getLogger(TestListener.class); // Logger instance

    // Called when a test method starts
    @Override
    public void onTestStart(ITestResult result) { // Test is starting
        log.info("Starting test: {} on thread: {}", // Log message template
                result.getName(), // Test method name
                Thread.currentThread().getName()); // Thread name
    }

    // Called when a test method succeeds
    @Override
    public void onTestSuccess(ITestResult result) { // Test passed
        log.info("Test passed: {}", result.getName()); // Log success
    }

    // Called when a test method fails
    @Override
    public void onTestFailure(ITestResult result) { // Test failed
        log.error("Test failed: {}", result.getName()); // Log failure
        // Capture screenshot on failure
        captureScreenshot(result); // Take screenshot and attach to Allure
    }

    // Called when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) { // Test skipped
        log.warn("Test skipped: {}", result.getName()); // Log skip
    }

    // Other lifecycle methods not used heavily (can be left empty)
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {} // Not used

    @Override
    public void onTestFailedWithTimeout(ITestResult result) { // Timeout failure
        onTestFailure(result); // Treat timeout as failure
    }

    @Override
    public void onStart(ITestContext context) { // Before any test in <test> tag
        log.info("Test context started: {}", context.getName()); // Log context start
    }

    @Override
    public void onFinish(ITestContext context) { // After all tests in <test> tag
        log.info("Test context finished: {}", context.getName()); // Log context finish
    }

    // Helper method to capture screenshot and attach to Allure
    private void captureScreenshot(ITestResult result) { // Called on failure
        // Get WebDriver for current thread
        WebDriver driver = DriverFactory.getDriver(); // Retrieve driver

        // If driver is null (e.g., setup failed), do nothing
        if (driver == null) { // Null check
            log.warn("WebDriver is null, cannot capture screenshot for {}", result.getName()); // Log warning
            return; // Exit method
        }

        try {
            // Cast WebDriver to TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver; // Enable screenshot capture

            // Capture screenshot as a temporary file
            File src = ts.getScreenshotAs(OutputType.FILE); // Take screenshot to file

            // Create timestamp string for filename uniqueness
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date()); // e.g., 20251210_132530_123

            // Get test method name
            String methodName = result.getMethod().getMethodName(); // Name of the failed method

            // Build final file path inside "screenshots" directory
            String filePath = "screenshots/" + methodName + "_" + timestamp + ".png"; // Relative path

            // Create File object for destination path
            File dest = new File(filePath); // Destination file

            // Ensure directory exists (create parent directories if needed)
            dest.getParentFile().mkdirs(); // Create "screenshots" folder if missing

            // Copy screenshot from temp location to our destination
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING); // Save screenshot file

            // Log location of saved screenshot
            log.info("Screenshot saved to {}", dest.getAbsolutePath()); // Log path

            // Attach screenshot to Allure report
            try (InputStream is = Files.newInputStream(dest.toPath())) { // Open saved file as InputStream
                Allure.addAttachment("Screenshot - " + methodName, "image/png", is, "png"); // Add as Allure attachment
            }

        } catch (IOException e) { // If any IO operation fails
            log.error("Failed to capture or attach screenshot for test: " + result.getName(), e); // Log error
        } catch (ClassCastException e) { // If driver is not TakesScreenshot capable
            log.error("Driver does not support screenshots for test: " + result.getName(), e); // Log error
        }
    }
}
