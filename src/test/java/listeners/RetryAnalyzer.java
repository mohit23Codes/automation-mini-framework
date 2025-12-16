package listeners; // Package for TestNG listeners and retry logic

import config.ConfigManager; // To read retry.enabled and retry.count
import org.testng.IRetryAnalyzer; // Interface for retry analyzer
import org.testng.ITestResult; // Represents result of a test method

import org.apache.logging.log4j.Logger; // Logger interface
import org.apache.logging.log4j.LogManager; // Logger factory

public class RetryAnalyzer implements IRetryAnalyzer { // Class implementing retry logic

    // Logger for this class
    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class); // Log4j logger

    // Flag indicating if retry is enabled from config
    private final boolean retryEnabled; // Whether retry is turned on

    // Max number of retry attempts per test
    private final int maxRetryCount; // Max retries

    // Counter for how many times we've retried this particular test
    private int currentRetry = 0; // Starts at 0

    // Constructor reads configuration only once per analyzer instance
    public RetryAnalyzer() { // Called when TestNG creates analyzer for a test
        this.retryEnabled = ConfigManager.getBool("retry.enabled"); // Read retry.enabled
        this.maxRetryCount = ConfigManager.getInt("retry.count"); // Read retry.count
    }

    // Called by TestNG to decide whether to retry on failure
    @Override
    public boolean retry(ITestResult result) { // result has info about the failed test
        // If retry is disabled, never retry
        if (!retryEnabled) { // Check flag
            return false; // No retry
        }

        // If we have retries left
        if (currentRetry < maxRetryCount) { // Compare with max allowed
            currentRetry++; // Increase retry count
            // Log warning about retry attempt
            log.warn("Retrying test '{}' - attempt {}/{} (Thread: {})",
                    result.getName(), // Test method name
                    currentRetry, // Current attempt number
                    maxRetryCount, // Max attempts
                    Thread.currentThread().getName()); // Current thread name
            return true; // Tell TestNG to rerun this test
        }

        // No more retries allowed
        return false; // Stop retrying
    }
}