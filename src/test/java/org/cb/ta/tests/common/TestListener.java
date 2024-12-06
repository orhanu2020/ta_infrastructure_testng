package org.cb.ta.tests.common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    ReportManager reportManager;

    @Override
    public void onStart(ITestContext context) {
        reportManager = new ReportManager();
    }

    @Override
    public void onTestStart(ITestResult result) {
        reportManager.start(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        reportManager.pass("PASS");
        reportManager.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {


        if(result instanceof BaseTest) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getDriver();
            reportManager.fail(result.getThrowable().toString(),
                    takesScreenshot.getScreenshotAs(OutputType.BASE64));
        } else {
            reportManager.fail(result.getThrowable().toString());
        }
        reportManager.flush();

    }
}
