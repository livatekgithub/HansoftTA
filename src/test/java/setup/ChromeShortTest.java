package setup; /**
 * Created by kyak on 03.08.2015.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.Users;
import utils.AccessData;
import utils.Service;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class ChromeShortTest {
    private WebDriver driver;
    private final String browser = "CHROME-SHORT";
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", AccessData.CHROME_DRIVER__PATH + "chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = AccessData.TESTURL;
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//20
    }

    @Test
    public void testLogin() throws Exception {
        Users.loginUser(driver,AccessData.TESTURL,AccessData.TESTLOGIN_SHORTTESTS,AccessData.TESTPASSWORD_SHORTTESTS);
        tests.Run.RunShort(driver, browser);
//        General.userSignOut(driver);
//        General.loginUserWithCash(driver,10);
    }

    @After
    public void tearDown() throws Exception {
        Service.takeScreenshot(driver, Service.nowTimeForFileName() + " " + browser + ".screenshot.png");

        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}

