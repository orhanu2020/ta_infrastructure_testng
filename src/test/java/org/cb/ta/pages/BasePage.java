package org.cb.ta.pages;

import lombok.Getter;
import org.cb.ta.tests.common.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BasePage {

    protected String baseURL;
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(this.driver, this);
    }


    public void navigate() {
        this.driver.get(this.baseURL);
    }
}
