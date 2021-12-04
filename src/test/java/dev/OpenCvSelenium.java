package dev;

import dev.openCvMatcher.OpenCvMatcher;
import nu.pattern.OpenCV;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.io.File;
import java.time.Duration;

public class OpenCvSelenium {

    static WebDriver webDriver;

    @BeforeAll
    static void beforeAll() {

        OpenCV.loadShared();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    static void afterAll() {
        webDriver.quit();
    }

    @Test
    void matchButton() throws Exception{

        String screenshot = "src/test/resources/images/seleniumMatcher/screenshot.png";
        String button = "src/test/resources/images/tool.png";
        webDriver.get("https://www.bankhapoalim.co.il/he");
//        Thread.sleep(5000);
        FileUtils.copyFile(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE), new File(screenshot));
        new OpenCvMatcher().run(new String[]{screenshot, button});
    }

}
