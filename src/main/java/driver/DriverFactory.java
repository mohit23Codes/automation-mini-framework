package driver;

import config.ConfigManager; // To read browser, headless, and timeout settings
import io.github.bonigarcia.wdm.WebDriverManager; // To manage browser driver binaries

import java.time.Duration;

import org.openqa.selenium.WebDriver; // Selenium WebDriver interface
import org.openqa.selenium.chrome.ChromeDriver; // ChromeDriver implementation
import org.openqa.selenium.chrome.ChromeOptions; // Chrome options (headless, args, etc.)

public class DriverFactory {

	private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	public static void initDriver() {

		if (driverThreadLocal.get() == null) {

			String browser = ConfigManager.get("browser");

			WebDriver driver = null;

			switch (browser.toLowerCase()) {

			case "chrome":

				WebDriverManager.chromedriver().setup();

				ChromeOptions options = new ChromeOptions();
				
				System.out.println(">>> headless flag = " + ConfigManager.getBool("headless"));

				if (ConfigManager.getBool("headless")) {

					options.addArguments("--headless");
					options.addArguments("--disable-gpu");
					options.addArguments("--window-size=1920,1080");
				}

				driver = new ChromeDriver(options);
				break;

			}
			
			driver.manage().window().maximize();
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getLong("implicit.wait")));
			
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.getLong("page.load.timeout")));
			
			driverThreadLocal.set(driver);

		}
	}
	
	public static WebDriver getDriver() {
		
		return driverThreadLocal.get();
	}
	
	
	public static void quitDriver() {
		
		WebDriver driver = driverThreadLocal.get();
		
		if(driver != null) {
			
			driver.quit();
			driverThreadLocal.remove();
		}
	}

}
