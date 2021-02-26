package com.farzan.springboot.webapp.cloudstorage.PageObjects;


import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    @FindBy(id="logout-button")
    WebElement logoutButton;

//    @FindBy(id="noteSubmit")
//    WebElement noteSaveButton;

    @FindBy(id="note-title")
    WebElement noteTitleField;

    @FindBy(id="note-description")
    WebElement noteDescriptionField;

    @FindBy(id="nav-notes-tab")
    WebElement noteTab;

    @FindBy(id="newNote")
    WebElement newNoteButton;

    @FindBy(id="save-note-changes")
    WebElement saveNoteButton;

    @FindBy(id="userTable")
    WebElement notesTable;

    @FindBy(tagName = "th")
    WebElement eachNote;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signout(){
        this.logoutButton.click();
    }

    public void createNoteAndSeeIt(String noteTitle, String noteDescription){
        this.noteTab.click();
        this.newNoteButton.click();
        this.noteTitleField.sendKeys(noteTitle);
        this.noteDescriptionField.sendKeys(noteDescription);
        this.saveNoteButton.click();
        this.noteTab.click();

        List<WebElement> notesList= notesTable.findElements(By.tagName("th"));

        Boolean noteCreated = false;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equals(noteTitle)) {
                noteCreated = true;
                break;
            }
        }
        Assertions.assertTrue(noteCreated);
    }
}
