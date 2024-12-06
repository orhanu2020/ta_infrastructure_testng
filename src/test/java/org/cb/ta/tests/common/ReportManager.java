package org.cb.ta.tests.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

@Getter
public class ReportManager {
    private static ExtentReports extentReports;

    static {
        String path = System.getProperty("user.dir") + "\\target\\reports\\index.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(path);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
    }

    private final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    public void start(String name) {
        extentTestThreadLocal.set(extentReports.createTest(name));
    }

    public void pass(String message) {
        extentTestThreadLocal.get().pass(message);
    }

    public void fail(String message, String base64Screenshot) {
        extentTestThreadLocal.get().fail(message);
        extentTestThreadLocal.get().addScreenCaptureFromBase64String(base64Screenshot);
    }

    public void fail(String message) {
        extentTestThreadLocal.get().fail(message);
    }

    public void flush() {
        extentReports.flush();
    }
}
