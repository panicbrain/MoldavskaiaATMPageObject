package PageFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewLetterPagePf extends BaseAreasPagePf {
    public NewLetterPagePf(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".js-input[data-original-name=To]")
    private WebElement emailAddressInput;

    @FindBy(name = "Subject")
    private WebElement subjectInput;

    @FindBy(css = "iframe")
    private WebElement frameMailBody;

    @FindBy(css = "#tinymce")
    private WebElement mailBodyInput;

    @FindBy(css = "#b-toolbar__right [data-name='saveDraft']")
    private WebElement saveAsDraftButton;

    @FindBy(css = "[data-mnemo=\"saveStatus\"]")
    private WebElement saveStatusMessage;

    @FindBy(css = "[data-text='ekaterinamoldavskaia18@gmail.com']")
    private WebElement filledEmailAddress;

    @FindBy(xpath = "//div[@data-name='send']")
    private WebElement sendMailButton;

    @FindBy(css = ".message-sent__title")
    private WebElement sentMailMessage;

    public NewLetterPagePf fillEmailInput(String email) {
        waitForElementVisible(emailAddressInput);
        emailAddressInput.sendKeys(email);
        return this;
    }

    public NewLetterPagePf fillSubjectInput() {
        waitForElementVisible(subjectInput);
        subjectInput.sendKeys(MAIL_SUBJECT);
        return this;
    }

    public NewLetterPagePf fillMailBodyInput(String mailBodyText) {
        driver.switchTo().frame(frameMailBody);
        waitForElementEnabled(mailBodyInput);
        mailBodyInput.sendKeys(mailBodyText);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPagePf saveAsDraft() {
        waitForElementEnabled(saveAsDraftButton);
        saveAsDraftButton.click();
        waitForElementVisible(saveStatusMessage);
        return this;
    }

    public String getMailAddress() {
        waitForElementVisible(filledEmailAddress);
        String mailAddress = filledEmailAddress.getText();
        return mailAddress;
    }

    public Object getMailSubject() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object subToMail = js.executeScript("return document.getElementsByName('Subject')[0].value");
        return subToMail;
    }

    public String getBodyText() {
        driver.switchTo().frame(frameMailBody);
        waitForElementEnabled(mailBodyInput);
        String bodyText = mailBodyInput.getText();
        driver.switchTo().defaultContent();
        return bodyText;
    }

    public void sendMail() {
        waitForElementEnabled(sendMailButton);
        sendMailButton.click();
        waitForElementVisible(sentMailMessage);
    }
}
