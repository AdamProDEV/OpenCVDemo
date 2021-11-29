package dev;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import sun.reflect.misc.FieldUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class OpenCvTest {

    static WebDriver webDriver;
    static DevTools devTools;
    static ComparingImages comparingImages;
    static boolean openChrome = false;

    @BeforeAll
    static void beforeAll() {

        comparingImages = new ComparingImages();
        if (!openChrome)
            return;
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    static void afterAll() {
        if (!openChrome)
            return;
        webDriver.quit();
    }

    @Test
    void saveScreenshotTest() throws IOException {

        webDriver.get("https://www.bankhapoalim.co.il/he");
        saveScreenshot();
    }

    @Test
    void compareTwoImagesTest() throws Exception {

        double result = comparingImages.compareTwoImages(
                new File("src/test/resources/images/image.jpg"),
                new File("src/test/resources/images/image1.jpg")
        );

        System.out.println("Compare: " + Math.round((100 - result)) + "%");
    }

    private void saveScreenshot() throws IOException {

        FileUtils.copyFile(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE),
                new File("src/test/resources/images/image1.jpg"));
    }
}
