package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePagePf extends AbstractPagePf {
    public HomePagePf(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "login")
    private WebElement loginInput;

    @FindBy(css = ".input[type=password]")
    private WebElement passwordInput;

    @FindBy(css = ".o-control[type=submit]")
    private WebElement signInButton;

    public HomePagePf open() {
        driver.navigate().to("https://mail.ru/");
        return this;
    }

    public HomePagePf clearLoginInput() {
        loginInput.clear();
        return this;
    }

    public HomePagePf fillLoginInput(String login) {
        loginInput.sendKeys(login);
        return this;
    }

    public HomePagePf fillPasswordInput(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public IncomingMailsPagePf signIn() {
        signInButton.click();
        return new IncomingMailsPagePf(driver);
    }
}
