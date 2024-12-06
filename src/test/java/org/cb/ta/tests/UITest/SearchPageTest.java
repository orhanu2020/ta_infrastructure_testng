package org.cb.ta.tests.UITest;

import org.cb.ta.pages.GoogleSearchPage;
import org.cb.ta.tests.common.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchPageTest extends BaseTest {

    // Each test case uses a new driver instance, therefore we
    // need to create new page instance for each test case
    // (no-reuse)
    @Test(priority = 0001, groups = { "ui"})
    public void search() {
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        googleSearchPage.navigate();
        waitAndWrite(googleSearchPage.getSearchBox(), "Test");
        waitAndClick(googleSearchPage.getSearchButton());
        Assert.assertTrue(true);
    }

    @Test(priority = 0002, groups = { "ui"})
    public void feelingLucky() {
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        googleSearchPage.navigate();
        waitAndClick(googleSearchPage.getFeelingLuckyButton());
        Assert.assertTrue(true);
    }
}
