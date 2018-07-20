package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseAreasPagePf extends AbstractPagePf {
    public BaseAreasPagePf(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Написать письмо")
    private WebElement newLetterButton;

    @FindBy(css = "[data-mnemo=\"drafts\"]")
    private WebElement draftLetterFolder;

    @FindBy(css = "[href='/messages/sent/']")
    private WebElement sentLettersFolder;

    @FindBy(css = "#PH_logoutLink")
    private WebElement logOffButton;

    @FindBy(css = ".b-datalist__item__subj")
    private List<WebElement> mailSubjects;

    private final static By MAIL_SUBJECTS_LOCATOR = By.cssSelector(".b-datalist__item__subj");

    private final static By SUBJECTS_WITH_BODY = By.cssSelector(".b-datalist__item__subj__snippet");

    static final UUID SUBJECT_TO_MAIL = UUID.randomUUID();
    public static final String MAIL_SUBJECT = SUBJECT_TO_MAIL.toString();
    WebDriverWait wait = new WebDriverWait(driver, 10);

    public NewLetterPagePf createNewLetter() {
        waitForElementEnabled(newLetterButton);
        newLetterButton.click();
        return new NewLetterPagePf(driver);
    }

    public DraftMailsPagePf openDraftFolder() {
        waitForElementEnabled(draftLetterFolder);
        draftLetterFolder.click();
        return new DraftMailsPagePf(driver);
    }

    public SentMailsPagePf openSentFolder() {
        waitForElementEnabled(sentLettersFolder);
        sentLettersFolder.click();
        return new SentMailsPagePf(driver);
    }

    public HomePagePf logOff() {
        waitForElementEnabled(logOffButton);
        logOffButton.click();
        return new HomePagePf(driver);
    }

    public List<String> getSubjectTextsOfMails() throws InterruptedException {
        driver.navigate().refresh();
        waitForElementPresent(MAIL_SUBJECTS_LOCATOR);
        List<WebElement> subjectsOfMailForTest = mailSubjects;
        List<String> textOfSubjects = new ArrayList<String>();
        for (WebElement subject : subjectsOfMailForTest) {
            String bodyOfMail = subject.findElement(SUBJECTS_WITH_BODY).getText();
            textOfSubjects.add(subject.getText().replace(bodyOfMail, "").trim());
        }
        return textOfSubjects;
    }
}
