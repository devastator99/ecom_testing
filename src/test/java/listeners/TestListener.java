package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> TEST_LOG = new ThreadLocal<>();
    private ExtentReports extentReports;

    @Override
    public void onStart(ITestContext context) {
        extentReports = ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(
                result.getTestClass().getName() + " :: " + result.getMethod().getMethodName()
        );
        TEST_LOG.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST_LOG.get().pass("Test passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest test = (BaseTest) result.getInstance();
        String screenshotPath = ScreenshotUtil.capture(test.driver, result.getName());

        TEST_LOG.get().fail(result.getThrowable());
        if (screenshotPath != null && new File(screenshotPath).exists()) {
            try {
                TEST_LOG.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                TEST_LOG.get().warning("Screenshot was captured but could not be attached to the report.");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST_LOG.get().skip("Test skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        TEST_LOG.get().warning("Test partially failed but is within the configured success percentage.");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
        TEST_LOG.remove();
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    private String toStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "";
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
