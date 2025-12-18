package listeners; // Package for TestNG listeners

import org.testng.IAnnotationTransformer; // Interface to modify TestNG annotations at runtime
import org.testng.annotations.ITestAnnotation; // Represents @Test annotation data

import java.lang.reflect.Constructor; // For constructor reflection
import java.lang.reflect.Method; // For method reflection

public class RetryListener implements IAnnotationTransformer { // Listener to attach RetryAnalyzer

	// Called by TestNG for each @Test annotation at startup
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		// If no retry analyzer is explicitly set on the test
		// Check current retry analyzer
		// Attach our RetryAnalyzer class to this test method
		annotation.setRetryAnalyzer(RetryAnalyzer.class); // Set retry analyzer

	}
}
