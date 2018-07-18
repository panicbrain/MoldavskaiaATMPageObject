package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class DraftMailsPage extends BaseAreasPage {
    public DraftMailsPage(WebDriver driver) {
        super(driver);
    }

    WebDriverWait wait = new WebDriverWait(driver, 10);

    private final static By DRAFTED_MAIL_SUBJECTS_LOCATOR = By.cssSelector(".b-datalist__item__subj");
    private final static By DRAFT_SUBJECTS_WITH_BODY = By.cssSelector(".b-datalist__item__subj__snippet");
    private String toLocator = "[data-subject='" + MAIL_SUBJECT + "']";
    private final By LAST_SAVED_DRAFT_LOCATOR = By.cssSelector(toLocator);


    public List<String> getSubjectTextsOfDraftedMails() throws InterruptedException {
        wait.until(ExpectedConditions.titleIs("Черновики - Почта Mail.Ru"));
        waitForElementPresent(DRAFTED_MAIL_SUBJECTS_LOCATOR);
        List<WebElement> subjectsOfMailForTest = driver.findElements(DRAFTED_MAIL_SUBJECTS_LOCATOR);
        List<String> textOfSubjects = new ArrayList<String>();
        for (WebElement subject : subjectsOfMailForTest) {
            String bodyOfMail = subject.findElement(DRAFT_SUBJECTS_WITH_BODY).getText();
            textOfSubjects.add(subject.getText().replace(bodyOfMail, "").trim());
        }
        return textOfSubjects;
    }

    public NewLetterPage openLastSavedDraft() {
        driver.findElement(LAST_SAVED_DRAFT_LOCATOR).click();
        return new NewLetterPage(driver);
    }
}
