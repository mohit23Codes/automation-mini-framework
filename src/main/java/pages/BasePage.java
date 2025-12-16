package pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.ConfigManager;
import driver.DriverFactory;

public abstract class BasePage {

	private final static Logger log = LogManager.getLogger(BasePage.class);

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage() {

		if (driver == null) {

			this.driver = DriverFactory.getDriver();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getLong("explicit.wait")));
		}
		PageFactory.initElements(driver, this);

	}

	protected void scrollIntoView(WebElement element) {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	// locator based scroll into view, used above method, also an example of method
	// overloading
	protected void scrollIntoView(By locator) {

		WebElement element = driver.findElement(locator);
		scrollIntoView(element);
	}

	protected void click(WebElement element) {

		int attempts = 0;
		try {

			while (attempts < 3) {

				wait.until(ExpectedConditions.visibilityOf(element));
				scrollIntoView(element);
				wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				return;
			}
		} catch (StaleElementReferenceException e) {

			attempts++;
			try {
				Thread.sleep(200 * attempts); // doing so that retries attempt is not wasted, maybe DOM/page is loading
												// and we are trying retry before that it should not happen
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		try {

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		} catch (Exception e) {
			throw new RuntimeException("Unable to click element after retries");
		}
	}

	protected void type(WebElement element, String text, String elementDescription) {

		log.info("Typing '{}' into {}", text, elementDescription);

		int attempts = 0;

		try {

			while (attempts < 3) {

				wait.until(ExpectedConditions.visibilityOf(element));
				scrollIntoView(element);
				element.clear();
				element.sendKeys(text);
				return;
			}

		} catch (StaleElementReferenceException e) {

			attempts++;
			log.warn("Stale element while typing into {}. Retrying attempt {}", elementDescription, attempts);
			sleep(attempts);
		}

		throw new RuntimeException("Not able to type after retries");
	}

	protected String getText(WebElement element) {

		int attempts = 0;

		try {

			while (attempts < 3) {

				return wait.until(ExpectedConditions.visibilityOf(element)).getText();
			}

		} catch (StaleElementReferenceException e) {

			attempts++;
			try {
				Thread.sleep(200 * attempts);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		throw new RuntimeException("Not able to get text after retries");
	}

	protected void sendKey(WebElement element, Keys key, String elementDescription) {

		log.info("Sending key '{}' to {}", key.name(), elementDescription);

		int attempts = 0;
		while (attempts < 3) {
			try {
				wait.until(ExpectedConditions.visibilityOf(element));
				scrollIntoView(element);
				element.sendKeys(key);
				return;
			} catch (StaleElementReferenceException e) {
				attempts++;
				log.warn("Stale element while sending key {} to {}. Retrying attempt {}", key.name(),
						elementDescription, attempts);
				sleep(attempts);
			}
		}
		throw new RuntimeException("Unable to send key " + key + " to element");
	}

	protected By byXpath(String template, Object... args) {

		String xpath = String.format(template, args);
		return By.xpath(xpath);
	}

	public boolean isElementPresent(WebElement element) {

		try {

			wait.until(ExpectedConditions.visibilityOf(element));
			return true;

		} catch (TimeoutException e) {
			return false;
		}

	}

	protected void sleep(int attempts) {

		try {
			Thread.sleep(200 * attempts);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
