package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DraftMailsPage extends BaseAreasPage {
    public DraftMailsPage(WebDriver driver) {
        super(driver);
    }

    private final static By DRAFTED_MAIL_SUBJECTS_LOCATOR = By.cssSelector(".b-datalist__item__subj");
    private final static By DRAFT_SUBJECTS_WITH_BODY = By.cssSelector(".b-datalist__item__subj__snippet");
    private String toLocator = "[data-subject='" + MAIL_SUBJECT + "']";
    private final By LAST_SAVED_DRAFT_LOCATOR = By.cssSelector(toLocator);

    public NewLetterPage openLastSavedDraft() {
        driver.findElement(LAST_SAVED_DRAFT_LOCATOR).click();
        return new NewLetterPage(driver);
    }
}
