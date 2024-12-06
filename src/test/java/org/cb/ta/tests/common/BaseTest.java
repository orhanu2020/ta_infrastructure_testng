package org.cb.ta.tests.common;


import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {
    private ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    @Parameters({ "browser" })
    @BeforeMethod( alwaysRun = true )
    // We do not want previous session cache
    // to affect any following test scenarios
    // therefore we create new driver session
    // in each scenario
    public void beforeMethod(String browser) {
        DriverManager.createNewDriver(browser);
        wait.set(new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3)));
    }

    @AfterMethod( alwaysRun = true )
    public void afterMethod() {
        DriverManager.getDriver().close();
    }



    // See: https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/RandomStringUtils.html
    public String getRandomAlphanumericString(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public void waitAndWrite(WebElement webElement, String text) {
        wait.get().until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.sendKeys(text);
    }

    public void waitAndClick(WebElement webElement) {
        wait.get().until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.click();
    }

    public void waitFor(WebElement webElement, int seconds) {
        WebDriverWait webDriverWait =
                new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }
}
