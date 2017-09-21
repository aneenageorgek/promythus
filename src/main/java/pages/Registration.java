package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Registration {

    WebDriver driver;

    @FindBy(id = "signUpFirstName")
    WebElement firstName;
    @FindBy(id = "signUpmiddleName")
    WebElement middleName;
    @FindBy(id = "signUpLastName")
    WebElement lastName;
    @FindBy(id = "signUpCounty")
    WebElement country;
    @FindBy(id = "signUpAddress")
    WebElement address;
    @FindBy(id = "signUpPhone")
    WebElement phone;
    @FindBy(id = "signUpRegisterEmail")
    WebElement registerEmail;
    @FindBy(id = "signUpRegisterPassword")
    WebElement registerPassword;
    @FindBy(id = "signUpRegisterRePassword")
    WebElement registerRePassword;
    @FindBy(xpath = "//*[@id=\"createAccount\"]")
    WebElement createAccount;
    @FindBy(xpath = "//*[@id=\"userType\"]")
    WebElement scout;

    public Registration(WebDriver driver, String firstname,
                        String middlename, String lastname,String countryname,
                        String adress, String phoneno,
                        String registeremail, String registerpassword,
                        String registerrepassword)

    {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        firstName.sendKeys(firstname);
        middleName.sendKeys(middlename);
        lastName.sendKeys(lastname);
        country.sendKeys(countryname);
        address.sendKeys(adress);
        phone.sendKeys(phoneno);
        registerEmail.sendKeys(registeremail);
        registerPassword.sendKeys(registerpassword);
        registerRePassword.sendKeys(registerrepassword);
    }



    public void createaccount() {
        createAccount.click();
    }
    public void createScoutaccount() {
        scout.click();
    }



}






