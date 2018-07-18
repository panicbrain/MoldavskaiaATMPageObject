import PageObjects.DraftMailsPage;
import PageObjects.HomePage;
import PageObjects.IncomingMailsPage;
import PageObjects.NewLetterPage;
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

public class MailRuTestPageObject {

    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
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
        HomePage homepage = new HomePage(driver);
        homepage.open();
        assertEquals(homepage.driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        homepage.fillLoginInput("MoldavskaiaATM");
        homepage.fillPasswordInput("ghbvtcm46");
        IncomingMailsPage incomingMailsPage = homepage.signIn();

        // assert that the login was successful
        wait.until(ExpectedConditions.titleContains("Входящие - Почта Mail.Ru"));
        assertTrue(incomingMailsPage.driver.getTitle().contains("Входящие - Почта Mail.Ru"));

        //Create a new mail
        NewLetterPage newLetterPage = incomingMailsPage.createNewLetter();
        newLetterPage.fillEmailInput("ekaterinamoldavskaia18@gmail.com");
        newLetterPage.fillSubjectInput();
        newLetterPage.fillMailBodyInput("Text");

        // save the mail as draft
        newLetterPage.saveAsDraft();

        //open drafts folder
        DraftMailsPage draftMailsPage = newLetterPage.openDraftFolder();
        assertEquals(draftMailsPage.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that draft presents in the Draft folder
        assertTrue(draftMailsPage.getSubjectTextsOfDraftedMails().contains(MAIL_SUBJECT), "The draft of test email is absent in the folder");

        //Open saved draft

        newLetterPage = draftMailsPage.openLastSavedDraft();
        assertEquals(newLetterPage.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that all field contain the same information that before saving as draft
        assertEquals(newLetterPage.getMailAddress(), "ekaterinamoldavskaia18@gmail.com");
        assertEquals(newLetterPage.getMailSubject(), MAIL_SUBJECT);

   /*

        assertEquals(driver.findElement(By.cssSelector("[data-text='ekaterinamoldavskaia18@gmail.com']"))
                .getText(), "ekaterinamoldavskaia18@gmail.com");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object subToMail = js.executeScript("return document.getElementsByName('Subject')[0].value");
        assertEquals(subToMail, subjectToMail.toString());
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#tinymce")));
        assertTrue(driver.findElement(By.cssSelector("#tinymce")).getText().contains("Text"), "Mail text is absent");
        driver.switchTo().defaultContent();

        // send email
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-name='send']"))).click();

        // assert that the draft disappeared from draft folder
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".message-sent__title")));
        driver.findElement(By.cssSelector("[data-mnemo='drafts']")).click();
        wait.until(ExpectedConditions.titleIs("Черновики - Почта Mail.Ru"));
        List<WebElement> subjectsOfMailForTestAfterSending = driver.findElements(
                By.cssSelector(".b-datalist__item__subj"));
        List<String> textOfSubjectsAfterSending = new ArrayList<String>();
        for (WebElement subjectAfterSending : subjectsOfMailForTestAfterSending) {
            String bodyOfMail2 = subjectAfterSending.findElement(By.cssSelector(".b-datalist__item__subj__snippet"))
                    .getText();
            textOfSubjectsAfterSending.add(subjectAfterSending.getText().replace(bodyOfMail2, "").trim());
        }
        assertFalse(textOfSubjectsAfterSending.contains(
                subjectToMail.toString()), "The draft of test email is absent in the folder");

        //assert the sent mail presents in Sent folder
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[href='/messages/sent/']"))).click();
        wait.until(ExpectedConditions.titleIs("Отправленные - Почта Mail.Ru"));
        List<WebElement> sentMails = driver.findElements(By.cssSelector(".b-datalist__item__subj"));
        List<String> sentSubjects = new ArrayList<String>();
        for (WebElement sentMail : sentMails) {
            String bodyOfMail = sentMail.findElement(By.cssSelector(".b-datalist__item__subj__snippet")).getText();
            sentSubjects.add(sentMail.getText().replace(bodyOfMail, "").trim());
        }
        assertTrue(sentSubjects.contains(subjectToMail.toString()), "The sent test email is absent in the folder");

        //Log off
        driver.findElement(By.cssSelector("#PH_logoutLink")).click();
        assertEquals(driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");
    }*/
    }
}
