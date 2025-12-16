package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleHomePage extends BasePage {
	
	
    public GoogleHomePage() {
        super();
    }
	
	@FindBy(name = "q")
	private WebElement searchBox;
	
	public GoogleResultsPage searchTopic(String query) {
		
		type(searchBox, query, "Search Box");
		sendKey(searchBox, Keys.ENTER, "Search Box");
		return new GoogleResultsPage();
	}

}
