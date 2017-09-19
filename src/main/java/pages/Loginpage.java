package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Loginpage {

    WebDriver driver;

    @FindBy(xpath="/html/body/app/ui-view/auth-zone/div/ui-view/sign-in-form/spinner-container/div[1]/div/div/form/div[1]/input")
    WebElement loginEmail;

    @FindBy(xpath="/html/body/app/ui-view/auth-zone/div/ui-view/sign-in-form/spinner-container/div[1]/div/div/form/div[2]/input")
    WebElement passWord;

    @FindBy(id="login")
    WebElement loginButton;

    @FindBy(xpath="/html/body/app/ui-view/auth-zone/div/ui-view/sign-in-form/spinner-container/div[1]/div/div/form/div[3]")
    WebElement loginAlert;

    @FindBy(xpath = "/html/body/app/ui-view/auth-zone/div/ui-view/sign-in-form/spinner-container/div[1]/div/div/a")
    WebElement register;

    public Loginpage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public Loginpage(WebDriver driver, String userName, String password){
        this.driver=driver;
        PageFactory.initElements(this.driver, this);
        loginEmail.sendKeys(userName);
        passWord.sendKeys(password);
    }
    /*public void setEmailAddress(String emailAddress){
        loginEmail.sendKeys(emailAddress);
    }

    public String getEmailAddress(){
        return loginEmail.getText();
    }

    public void setPassWord(String password){
        loginEmail.sendKeys(password);
    }

    public String getPassword(){
        return passWord.getText();
    }*/


    public String getLoginAlert(){ return loginAlert.getText(); }

    public void clickLogin(){ loginButton.click(); }

    public void createAccount() {register.click();}


}


