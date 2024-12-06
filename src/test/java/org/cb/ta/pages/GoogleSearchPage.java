package org.cb.ta.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class GoogleSearchPage extends BasePage {
    @FindBy(xpath = "//textarea[@name='q']")
    private WebElement searchBox;

    @FindBy(xpath = "(//input[@value='Google Search'])")
    private WebElement searchButton;

    @FindBy(xpath = "//input[contains(@value, 'Feeling Lucky')][@role='button']")
    private WebElement feelingLuckyButton;

    public GoogleSearchPage() {
        this.baseURL = "https://www.google.com/";
    }
}
