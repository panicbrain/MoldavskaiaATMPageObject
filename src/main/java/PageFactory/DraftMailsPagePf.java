package PageFactory;


import PageObjects.NewLetterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DraftMailsPagePf extends BaseAreasPagePf {
    public DraftMailsPagePf(WebDriver driver) {
        super(driver);
    }

    private String toLocator = "[data-subject='" + MAIL_SUBJECT + "']";
    private final By LAST_SAVED_DRAFT_LOCATOR = By.cssSelector(toLocator);

    public NewLetterPagePf openLastSavedDraft() {
        driver.findElement(LAST_SAVED_DRAFT_LOCATOR).click();
        return new NewLetterPagePf(driver);
    }
}
