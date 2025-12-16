package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleResultsPage extends BasePage {
		
	
	private static final String RESULT_TITLE = "//h3[contains(normalize-space(.), '%s')]";
	
    @FindBy(id = "react-duckbar") // Find all <h3> elements (Google result titles)
    private WebElement filtersAndTopicsTab; // Collection of result titles
	
    // List of result titles (h3 elements)
    @FindBy(css = "h3") // Find all <h3> elements (Google result titles)
    private List<WebElement> resultTitles; // Collection of result titles
    
    public GoogleResultsPage() {
    	
    	super();
    	
    	if(!isElementPresent(filtersAndTopicsTab)) {
            throw new IllegalStateException(
                    "Google Results Page not loaded. 'search' container not found.");
    	}
    }
    
    
    // Check if a result with a given title exists
    public boolean isResultWithTitlePresent(String title) {
        By locator = byXpath(RESULT_TITLE, title);
        try {
            String text = getText(driver.findElement(locator));
            return title.contains(text.trim());
        } catch (Exception e) {
            return false;
        }
    }

    // Get count of result titles displayed
    public int getResultCount() {
        return resultTitles.size();
    }

}
