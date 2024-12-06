package org.cb.ta.tests.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public class DriverManager {

    private static ThreadLocal<WebDriver> driverContainer = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverContainer.get();
    }

    public static void createNewDriver(String browser) {
        WebDriver driver = null;
        switch (browser.toLowerCase(Locale.US)) {
            case "chrome":
            default:
                driver = new ChromeDriver();
                break;
            case "chrome-headless":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "firefox-headless":
                // Create an object of Firefox Options class
                FirefoxOptions ffOptions = new FirefoxOptions();

                // Set Firefox Headless mode as TRUE
                ffOptions.addArguments("-headless");
                driver = new FirefoxDriver(ffOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driverContainer.set(driver);
    }
}
