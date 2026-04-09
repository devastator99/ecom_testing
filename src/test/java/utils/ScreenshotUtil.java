package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        try {
            File directory = new File("screenshots");
            if (!directory.exists()) {
                directory.mkdir();
            }
            String screenshotPath = "screenshots/" + testName + ".png";
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(
                    src.toPath(),
                    Paths.get(screenshotPath),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return screenshotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
