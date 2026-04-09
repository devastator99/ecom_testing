package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            File reportDirectory = new File("reports");
            if (!reportDirectory.exists()) {
                reportDirectory.mkdirs();
            }

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(new File(reportDirectory, "extent-report.html"));
            sparkReporter.config().setReportName("E-Commerce QA Automation Report");
            sparkReporter.config().setDocumentTitle("Automation Execution Report");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Framework", "Selenium + TestNG + Maven");
            extentReports.setSystemInfo("Application", "SauceDemo");
            extentReports.setSystemInfo("Environment", "QA");
        }

        return extentReports;
    }
}
