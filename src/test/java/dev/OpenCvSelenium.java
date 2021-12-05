package dev;

import dev.openCvMatcher.OpenCvMatcher;
import nu.pattern.OpenCV;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.io.File;
import java.time.Duration;

public class OpenCvSelenium {

    static WebDriver webDriver;

    @BeforeAll
    static void beforeAll() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    static void afterAll() {
        webDriver.quit();
    }

    @Test
    void matchButton() throws Exception {

        String screenshot = "src/test/resources/images/seleniumMatcher/screenshot.png";
        String button = "src/test/resources/images/username.png";
//        webDriver.get("https://www.bankhapoalim.co.il/he");
        webDriver.get("https://www.bankhapoalim.co.il/he/login");

        FileUtils.copyFile(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE), new File(screenshot));

        Point point = new OpenCvMatcher().match(screenshot, button);
        System.out.println(point);
        tapByCoordinate((int) point.x, (int) point.y, webDriver);
        sendKeysByCoordinate("my username", (int) point.x, (int) point.y, webDriver);
        Thread.sleep(3000);

        button = "src/test/resources/images/password.png";
        point = new OpenCvMatcher().match(screenshot, button);
        System.out.println(point);
        sendKeysByCoordinate("123456", (int) point.x, (int) point.y, webDriver);
        Thread.sleep(3000);

        button = "src/test/resources/images/enter.png";
        point = new OpenCvMatcher().match(screenshot, button);
        System.out.println(point);
        tapByCoordinate((int) point.x, (int) point.y, webDriver);
        Thread.sleep(3000);

    }

    private void tapByCoordinate(int x, int y, WebDriver webDriver) {
        System.out.println(webDriver.manage().window().getSize());
        System.out.println(x + " : " + y);
//        (new TouchActions(webDriver))
//                .moveByOffset(x, y)
//                .click()
//                .moveByOffset(-x, -y)
//                .build().perform();

        Actions actions = new Actions(webDriver);
        actions.moveByOffset(x, y).click().build().perform();

    }

    private void sendKeysByCoordinate(String text, int x, int y, WebDriver webDriver) {
        System.out.println(x + " : " + y);
//        (new TouchActions(webDriver))
//                .moveByOffset(x , y)
//                .click()
//                .sendKeys(text)
//                .moveByOffset(-x, -y)
//                .build().perform();

        Actions actions = new Actions(webDriver);
        actions.moveByOffset(x, y).click()
                .sendKeys(text)
                .build().perform();
    }



}
