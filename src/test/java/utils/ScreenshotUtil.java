package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static void capture(WebDriver driver, String testName) {
        try {
            File directory = new File("screenshots");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), Paths.get("screenshots/" + testName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}