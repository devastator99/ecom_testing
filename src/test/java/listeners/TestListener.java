package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;
import base.BaseTest;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest test = (BaseTest) result.getInstance();
        ScreenshotUtil.capture(test.driver, result.getName());
    }
}