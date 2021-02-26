package com.farzan.springboot.webapp.cloudstorage.PageObjects;


import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    @FindBy(id="logout-button")
    WebElement logoutButton;

    @FindBy(id="note-title")
    WebElement noteTitleField;

    @FindBy(id="note-description")
    WebElement noteDescriptionField;

    @FindBy(id="nav-notes-tab")
    WebElement noteTab;

    @FindBy(id="nav-credentials-tab")
    WebElement credentialsTab;

    @FindBy(id="newNote")
    WebElement newNoteButton;

    @FindBy(id="save-note-changes")
    WebElement saveNoteButton;

    @FindBy(id="userTable")
    WebElement notesTable;

    @FindBy(id="credentialTable")
    WebElement credsTable;

    @FindBy(id="edit-note")
    WebElement noteEditButton;

    @FindBy(id="edit-creds")
    WebElement credsEditButton;

    @FindBy(id="delete-note")
    WebElement noteDeleteButton;

    @FindBy(id="delete-creds")
    WebElement credsDeleteButton;

    @FindBy(id="newCred")
    WebElement newCredButton;

    @FindBy(id="credential-url")
    WebElement urlField;

    @FindBy(id="credential-username")
    WebElement usernameField;

    @FindBy(id="credential-password")
    WebElement passwordField;

    @FindBy(id="save-creds-changes")
    WebElement credSubmitButton;


    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signout(){
        this.logoutButton.click();
    }

    public void clickOnNoteTab(){
        this.noteTab.click();
    }
    public void clickNewNote(){
        this.newNoteButton.click();
    }

    public void createNote(String noteTitle, String noteDescription) {

        this.noteTitleField.sendKeys(noteTitle);
        this.noteDescriptionField.sendKeys(noteDescription);
        this.saveNoteButton.click();
    }

    public void checkNote(String noteTitle){

        List<WebElement> notesList = this.notesTable.findElements(By.tagName("th"));

        Boolean changed = false;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equals(noteTitle)) {
                changed = true;
                break;
            }
        }
        Assertions.assertTrue(changed);
    }

    public void clickEditNote(){
        this.noteEditButton.click();
    }

    public void clickEditCreds(){
        this.credsEditButton.click();
    }

    public void clearNotenput(){
        this.noteTitleField.clear();
        this.noteDescriptionField.clear();
    }

    public void clickDeleteNote(){
        this.noteDeleteButton.click();
    }

    public void checkDeleteNote(String noteTitle){

        List<WebElement> notesList = this.notesTable.findElements(By.tagName("td"));

        Boolean noteDeleted = true;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equals(noteTitle)) {
                noteDeleted = false;
                break;
            }
        }
        Assertions.assertTrue(noteDeleted);
    }

    public void clickOnCredTab(){
        this.credentialsTab.click();
    }

    public void clickNewCredentials(){
        this.newCredButton.click();
    }

    public void saveCredentials(String url, String username, String password){
        this.urlField.sendKeys(url);
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.credSubmitButton.click();
    }

    public void clearCredsInput(){
        this.urlField.clear();
        this.usernameField.clear();
        this.passwordField.clear();
    }

    public void checkCredentials(String username){
        List<WebElement> credsList = this.credsTable.findElements(By.tagName("th"));

        Boolean credCreated = false;
        for (int i = 0; i < credsList.size(); i++) {
            WebElement element = credsList.get(i);
            if (element.getAttribute("innerHTML").equals(username)) {
                credCreated = true;
                break;
            }
        }
        Assertions.assertFalse(credCreated);
    }

    public void clickDeleteCredentials(){
        this.credsDeleteButton.click();
    }

    public void checkDeleteCreds(String username){
        List<WebElement> credsList = this.credsTable.findElements(By.tagName("td"));

        Boolean credsDeleted = true;
        for (int i = 0; i < credsList.size(); i++) {
            WebElement element = credsList.get(i);
            if (element.getAttribute("innerHTML").equals(username)) {
                credsDeleted = false;
                break;
            }
        }
        Assertions.assertTrue(credsDeleted);
    }
}
