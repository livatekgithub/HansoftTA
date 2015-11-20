package setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pageobjects.Users;
import utils.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IEShortTest {
    private WebDriver driver;
    private String baseUrl;
    private final String browser = "IE11.sh.";
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.ie.driver", AccessData.IE_DRIVER__PATH + "IEDriverServer.exe");
        driver = new InternetExplorerDriver();
        baseUrl = AccessData.TESTURL;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogin() throws Exception {
        Users.loginUser(driver,AccessData.TESTURL,AccessData.TESTLOGIN_SHORTTESTS,AccessData.TESTPASSWORD_SHORTTESTS);
        tests.Run.RunShort(driver, browser);
    }

    @After
    public void tearDown() throws Exception {
        Service.takeScreenshot(driver, Service.nowTimeForFileName() + browser+ ".screenshot.png");
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}


