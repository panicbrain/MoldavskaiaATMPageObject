import PageFactory.*;
import PageObjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static PageObjects.BaseAreasPage.MAIL_SUBJECT;
import static org.testng.Assert.*;

public class MailRuTestPageObjectPf {

    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    @Test(description = "mail.ru page should open and contain appropriate title")
    public void loginTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Open mail.ru page
        HomePagePf homePagePf = new HomePagePf(driver);
        homePagePf.open();
        assertEquals(homePagePf.driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");

        // clear the email address field
        homePagePf.clearLoginInput();

        //login to the mail box
        homePagePf.fillLoginInput("MoldavskaiaATM");
        homePagePf.fillPasswordInput("ghbvtcm46");
        IncomingMailsPagePf incomingMailsPagePf = homePagePf.signIn();

        // assert that the login was successful
        wait.until(ExpectedConditions.titleContains("Входящие - Почта Mail.Ru"));
        assertTrue(incomingMailsPagePf.driver.getTitle().contains("Входящие - Почта Mail.Ru"));

        //Create a new mail
        NewLetterPagePf newLetterPagePf = incomingMailsPagePf.createNewLetter();
        newLetterPagePf.fillEmailInput("ekaterinamoldavskaia18@gmail.com");
        newLetterPagePf.fillSubjectInput();
        newLetterPagePf.fillMailBodyInput("Text");

        // save the mail as draft
        newLetterPagePf.saveAsDraft();

        //open drafts folder
        DraftMailsPagePf draftMailsPagePf = newLetterPagePf.openDraftFolder();
        assertEquals(draftMailsPagePf.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that draft presents in the Draft folder
        assertTrue(draftMailsPagePf.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft of test email is absent in the folder");

        //Open saved draft
        newLetterPagePf = draftMailsPagePf.openLastSavedDraft();
        assertEquals(newLetterPagePf.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that all field contain the same information that before saving as draft
        assertEquals(newLetterPagePf.getMailAddress(), "ekaterinamoldavskaia18@gmail.com");
        assertEquals(newLetterPagePf.getMailSubject(), MAIL_SUBJECT);
        assertTrue(newLetterPagePf.getBodyText().contains("Text"), "Mail text is absent");

        // send email
        newLetterPagePf.sendMail();

        // assert that the draft disappeared from draft folder
        draftMailsPagePf = newLetterPagePf.openDraftFolder();
        assertFalse(draftMailsPagePf.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft stays in the folder");

        //assert the sent mail presents in Sent folder
        SentMailsPagePf sentMailsPagePf = draftMailsPagePf.openSentFolder();
        assertTrue(sentMailsPagePf.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The sent email is absent in the folder");

        //Log off
        homePagePf = sentMailsPagePf.logOff();
        assertEquals(homePagePf.driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");
    }
}
