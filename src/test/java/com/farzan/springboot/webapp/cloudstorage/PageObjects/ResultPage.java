package com.farzan.springboot.webapp.cloudstorage.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.List;

public class ResultPage {
    @FindBy(tagName = "a")
    List<WebElement> links;


    public ResultPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void clickOnHomeLink(String href){
        List<WebElement> anchors = this.links;
        Iterator<WebElement> i = anchors.iterator();

        while(i.hasNext()) {
            WebElement anchor = i.next();
            if(anchor.getAttribute("href").contains(href)) {
                anchor.click();
                break;
            }
        }
    }
}
