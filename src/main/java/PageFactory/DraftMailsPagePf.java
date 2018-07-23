package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class DraftMailsPagePf extends BaseAreasPagePf {
    public DraftMailsPagePf(WebDriver driver) {
        super(driver);
    }

    private final String toLocator = "[data-subject='%s']";

    public NewLetterPagePf openSavedDraft(String MAIL_SUBJECT) {
        driver.findElement(By.cssSelector(String.format(toLocator, MAIL_SUBJECT))).click();
        return new NewLetterPagePf(driver);
    }
}
