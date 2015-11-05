package setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobjects.Users;
import utils.*;
import tests.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class FirefoxTest {
    private WebDriver driver;
    private final String browser="FIREFOX";
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = AccessData.TESTURL;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogin() throws Exception {
        Users.loginUser(driver);
        tests.Run.Run(driver,browser);
        Thread.sleep(3000);
//        General.userSignOut(driver);
    }

    @After
    public void tearDown() throws Exception {
        Service.takeScreenshot(driver,Service.nowTimeForFileName()+" "+browser+".screenshot.png");
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
