package com.farzan.springboot.webapp.cloudstorage.PageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SigninPage {

    @FindBy(id="inputUsername")
    WebElement usernameField;

    @FindBy(id="inputPassword")
    WebElement passwordField;

    @FindBy(id="submit-button")
    WebElement submitButton;

    public SigninPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password){
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();

    }
}
