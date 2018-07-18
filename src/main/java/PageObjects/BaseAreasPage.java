package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class BaseAreasPage extends AbstractPage {
    public BaseAreasPage(WebDriver driver) {
        super(driver);
    }

    private final static By NEW_LETTER_BUTTON = By.linkText("Написать письмо");
    private final static By DRAFT_LETTERS_FOLDER_LOCATOR = By.cssSelector("[data-mnemo=\"drafts\"]");
    private final static By SENT_LETTERS_FOLDER_LOCATOR = By.cssSelector("[href='/messages/sent/']");
    private final static By LOG_OFF_BUTTON_LOCATOR = By.cssSelector("#PH_logoutLink");

    static final UUID SUBJECT_TO_MAIL = UUID.randomUUID();
    public static final String MAIL_SUBJECT = SUBJECT_TO_MAIL.toString();


    public NewLetterPage createNewLetter(){
        driver.findElement(NEW_LETTER_BUTTON).click();
        return new NewLetterPage(driver);
    }

    public DraftMailsPage openDraftFolder(){
        driver.findElement(DRAFT_LETTERS_FOLDER_LOCATOR).click();
        return new DraftMailsPage(driver);
    }

    public SentMailsPage openSentFolder(){
        driver.findElement(SENT_LETTERS_FOLDER_LOCATOR).click();
        return new SentMailsPage(driver);
    }

    public HomePage logOff(){
        driver.findElement(LOG_OFF_BUTTON_LOCATOR).click();
        return new HomePage(driver);
    }

}
