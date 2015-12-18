package pageobjects;

import enums.UserOrgRoles;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utils.AccessData;
import utils.Service;
import utils.WindowOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Users implements utils.AccessData {
    //XPATH examples
    //div[text()="HX 5"]/parent::*/parent::*/following-sibling::*/child::div[2]
    //div[text()="hansoftxtest5@gmail.com"]/parent::*/parent::*/following-sibling::*/child::div[1]/child::*/child::option[1]

    final static String MAIN_MENU_INITIALS_CSS = ".avatar-initials";
    final static String MAIN_MENU_IMAGE_CSS = ".avatar-image";
    final static String USER_MAIN_MENU_MEMBERS_LIST_CSS = "div.app-menu-choice.js-org-admin";
    final static String USER_ORG_MENU_CLOSE_BUTTON_CSS = ".popup-window-toolbar-button.mod-cancel.js-popup-cancel";
    final static String USER_ORG_MENU_DISMISS_CSS = ".toolpopup-popup-bg";

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void loginUser(WebDriver driver, String testURL, String testLogin, String testPassword) throws InterruptedException {
        driver.get("https://test.hansoftx.com/logout");
//        driver.manage().window().maximize();
        WindowOperations.resizeWindowforIdea(driver);
        driver.get(AccessData.TESTURL);
        Thread.sleep(3000);
        driver.findElement(By.id("form-email")).clear();
        driver.findElement(By.id("form-email")).sendKeys(testLogin);
        driver.findElement(By.id("form-psw")).clear();
        driver.findElement(By.id("form-psw")).sendKeys(testPassword);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("button.btn-submit.js-tap-indication")).click();

        if (isElementPresent(By.cssSelector(MAIN_MENU_INITIALS_CSS), driver))
            assertTrue(isElementPresent(By.cssSelector(MAIN_MENU_INITIALS_CSS), driver));
        else if (isElementPresent(By.cssSelector(MAIN_MENU_IMAGE_CSS), driver))
            assertTrue(isElementPresent(By.cssSelector(MAIN_MENU_IMAGE_CSS), driver));

//        try {
//            assertTrue(isElementPresent(By.cssSelector(MAIN_MENU_INITIALS_CSS), driver));
//        } catch (AssertionError e) {
//            assertTrue(isElementPresent(By.cssSelector(MAIN_MENU_IMAGE_CSS), driver));
//        }
    }

    public static void openMainMenu(WebDriver driver) {
        final String MAIN_MENU_INITIALS="html/body/div[3]/div[1]/div[1]/div[1]/a/div/span";
        final String MAIN_MENU_IMAGE="html/body/div[3]/div[1]/div[1]/div[1]/a/div/img";

        if (isElementPresent(By.xpath(MAIN_MENU_INITIALS), driver)){
            driver.findElement(By.xpath(MAIN_MENU_INITIALS)).click();
        }
        else if (isElementPresent(By.xpath(MAIN_MENU_IMAGE), driver)){
            driver.findElement(By.xpath(MAIN_MENU_IMAGE)).click();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void userNotFound(WebDriver driver) {
        driver.get(AccessData.TESTURL);
        driver.findElement(By.id("form-email")).clear();
        driver.findElement(By.id("form-email")).sendKeys(AccessData.WRONGTESTLOGIN);
        driver.findElement(By.id("form-psw")).clear();
        driver.findElement(By.id("form-psw")).sendKeys(AccessData.WRONGTESTPASSWORD);
        driver.findElement(By.cssSelector("button.btn-submit.js-tap-indication")).click();
        assertEquals("User not found", driver.findElement(By.cssSelector("div.form-message.firepath-matching-node")).getText());
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void userSignOut(WebDriver driver) {
        try {
            driver.findElement(By.cssSelector(".avatar-initials")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.cssSelector(".avatar-image")).click();
        }
        driver.findElement(By.cssSelector("div.app-menu-choice.js-signout")).click();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void usersOpenMembersList(WebDriver driver) throws InterruptedException {
        openMainMenu(driver);
        driver.findElement(By.cssSelector(USER_MAIN_MENU_MEMBERS_LIST_CSS)).click();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void closeEditOrganizationMenu(WebDriver driver) {
        driver.findElement(By.cssSelector(USER_ORG_MENU_CLOSE_BUTTON_CSS)).click();
        driver.findElement(By.cssSelector(USER_ORG_MENU_DISMISS_CSS)).click();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void usersAddToOrganization(WebDriver driver, int numberOfUsers, String[] testNamesForTest, boolean isLogged) throws InterruptedException {
        final String ADD_USER_FIELD_XPATH_BEGIN = "html/body/div[5]/div/div/div[1]/div/div[2]/div[3]/div[2]/";
        final String ADD_USER_FIELD_XPATH_END = "/div[1]/input";

        String name;
        String dynamicPart;

        openMainMenu(driver);
        driver.findElement(By.cssSelector("div.app-menu-choice.js-org-invite")).click();
        driver.findElement(By.id("invitations")).click();

        for (int i = 0; i < numberOfUsers; i++) {
            name = Integer.toString(i + 1);
            if (i == 0) dynamicPart = "div";
            else dynamicPart = "div[" + name + "]";
            if (isLogged) System.out.println(testNamesForTest[i]);
            if (isLogged) System.out.println(ADD_USER_FIELD_XPATH_BEGIN + dynamicPart + ADD_USER_FIELD_XPATH_END);
            driver.findElement(By.xpath(ADD_USER_FIELD_XPATH_BEGIN + dynamicPart + ADD_USER_FIELD_XPATH_END)).sendKeys(testNamesForTest[i]);
            if (isLogged)
                System.out.println(Service.nowTime() + " User" + (i + 1) + ": " + testNamesForTest[i] + " was added:");
            driver.findElement(By.cssSelector(".workspaceedit-add-button.js-workspacedit-add-invitation")).click();
        }
        driver.findElement(By.cssSelector(".ui-button-blue.js-workspacedit-sendinvitations")).click();
        closeEditOrganizationMenu(driver);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void usersRemoveAllFromOrganization(WebDriver driver, boolean isLogged) throws InterruptedException {

        String userName;
        final String USER_SECOND_USER_EXIST_XPATH = "html/body/div[5]/div[2]/div/div[1]/div/div[2]/div[2]/div/div[3]/div[2]/div[2]/div[2]/div[2]/div";
        final String USER_LAST_USER_NAME_XPATH = "html/body/div[5]/div[2]/div/div[1]/div/div[2]/div[2]/div/div[3]/div[2]/div[1]/div[1]/div[2]/div[1]";
        final String USER_LAST_USER_REMOVE_BUTTON_XPATH = "html/body/div[5]/div[2]/div/div[1]/div/div[2]/div[2]/div/div[3]/div[2]/div[1]/div[2]/div[2]/div";
        final String USER_LAST_USER_REMOVE_CONFIRM_BUTTON_XPATH = "html/body/div[5]/div[2]/div/div[1]/div/div[2]/div[2]/div/div[3]/div[2]/div[1]/div[2]/div";
        usersOpenMembersList(driver);
        while (isElementPresent(By.xpath(USER_SECOND_USER_EXIST_XPATH), driver)) {
            userName = driver.findElement(By.xpath(USER_LAST_USER_NAME_XPATH)).getText();
            if (isLogged) {
                System.out.println(userName + " was removed " + driver.findElement(By.xpath(USER_LAST_USER_REMOVE_BUTTON_XPATH)).isDisplayed());
            }
            Thread.sleep(500);
            driver.findElement(By.xpath(USER_LAST_USER_REMOVE_BUTTON_XPATH)).click();
            Thread.sleep(500);
            driver.findElement(By.xpath(USER_LAST_USER_REMOVE_CONFIRM_BUTTON_XPATH)).click();
        }
        closeEditOrganizationMenu(driver);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void usersSetupPermission(WebDriver driver, String userEmail, UserOrgRoles userOrgRoles) throws InterruptedException {
        String userRole = "";
        switch (userOrgRoles) {
            case ADMINISTRATOR: {
                userRole = "Administrator";
            }
            break;
            case FULL_MEMBER: {
                userRole = "Full member";
            }
            break;
            case RESTRICTED: {
                userRole = "Restricted";
            }
            break;
            default: {
            }
            break;
        }
        String SELECT_XPATH = "//div[text()=\"" + userEmail + "\"]/parent::*/parent::*/following-sibling::*/child::div[1]/child::*";
        new Select(driver.findElement(By.xpath(SELECT_XPATH))).selectByVisibleText(userRole);
        Thread.sleep(2000);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void usersParticularUserRemoval(WebDriver driver, String userEmail) throws InterruptedException {
        String elementXPath = "//div[text()=\"" + userEmail + "\"]";
        String DELETE_XPATH = "//div[text()=\"" + userEmail + "\"]/parent::*/parent::*/following-sibling::*/child::div[2]/div";
        Service service = new Service();
        service.startCount();
        new WebDriverWait(driver, 120).until(ExpectedConditions.presenceOfElementLocated(By.xpath(DELETE_XPATH)));
        String DELETE_CONFIRM_XPATH = "//div[text()=\"" + userEmail + "\"]/parent::*/parent::*/following-sibling::*/div";
        driver.findElement(By.xpath(DELETE_XPATH)).click();
        driver.findElement(By.xpath(DELETE_CONFIRM_XPATH)).click();
        service.stopCount();
        System.out.println("Waiting... " + service.getTimeDurationInSeconds() + "Seconds");
        Thread.sleep(2000);
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static ArrayList usersGetListOfUsers(WebDriver driver) throws InterruptedException {
        ArrayList listOfOrgUsers = new ArrayList();
        Users.usersOpenMembersList(driver);
        List<WebElement> list = driver.findElements(By.cssSelector(".workspacedit-username"));
        for (WebElement element : list) {
            listOfOrgUsers.add(element.getText());
        }
        return listOfOrgUsers;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void createNewOrganization(WebDriver driver, String orgName) throws InterruptedException {
        final String ORGANIZATION_SELECT_AXPATH = "html/body/div[4]/div[2]/div/div/div[14]/div";
        final String ORGANIZATION_SELECT_XPATH = "//div[contains(text(),'Select Organization')]";

        final String ORGANIZATION_CREATE_BUTTON_CSS = ".workspace-popup-button.workspace-popup-action.workspace-popup-action-create." +
                "js-createorganization.js-tap-indication.js-tap-indication-onwhite.js-tap-direct";
        final String ORGANIZATION_CREATE_ENTER_NAME_CSS = ".menu-popup-userfield.createorganization-input.js-createorganization-name";
        final String ORGANIZATION_CREATE_NEXT_BUTTON_CSS = ".popup-window-toolbar-button.mod-done.js-createorganization-next";

        openMainMenu(driver);
        Thread.sleep(5000);
        driver.findElement(By.xpath(ORGANIZATION_SELECT_XPATH)).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector(ORGANIZATION_CREATE_BUTTON_CSS)).click();
        driver.findElement(By.cssSelector(ORGANIZATION_CREATE_ENTER_NAME_CSS)).sendKeys(orgName);
        driver.findElement(By.cssSelector(ORGANIZATION_CREATE_NEXT_BUTTON_CSS)).click();
        driver.findElement(By.cssSelector(ORGANIZATION_CREATE_NEXT_BUTTON_CSS)).click();
        Thread.sleep(5000);
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void makeCurrentOrgStandard(WebDriver driver) throws InterruptedException {
        final String ORGANIZATION_BILLING_UPGRADE_TO_STANDARD_BUTTON_CSS = ".ui-button-green.mod-full-width.js-workspacedit-billing";

        openMainMenu(driver);
        driver.findElement(By.cssSelector(USER_MAIN_MENU_MEMBERS_LIST_CSS)).click();
        driver.findElement(By.id("billing")).click();
        driver.findElement(By.cssSelector(ORGANIZATION_BILLING_UPGRADE_TO_STANDARD_BUTTON_CSS)).click();
        driver.findElement(By.xpath("//input[@id='customer-business']")).sendKeys("1");
        driver.findElement(By.cssSelector(".popup-userfield.workspaceedit-customer-input.js-workspacedit-customer-address1")).sendKeys("1");
        driver.findElement(By.cssSelector(".popup-userfield.workspaceedit-customer-input.js-workspacedit-customer-zip")).sendKeys("1");
        driver.findElement(By.cssSelector(".popup-userfield.workspaceedit-customer-input.js-workspacedit-customer-city")).sendKeys("1");
        new Select(driver.findElement(By.cssSelector(".ui-dropdown-select.mod-full-width.js-ui-dropdown." +
                "js-tap-indication.js-tap-direct.js-select-customer-country-option"))).selectByVisibleText("Ukraine");
        driver.findElement(By.cssSelector(".ui-button-green.mod-full-width.workspaceedit-customer.js-workspacedit-customer")).click();
        Thread.sleep(10000);
//        driver.findElement(By.xpath())
        driver.findElement(By.xpath("html/body/div[1]/div[1]/div/div[2]/div/form/label/input")).sendKeys("1");
        driver.findElement(By.id("expiration")).sendKeys("1");

    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------
    private static boolean isElementPresent(By by, WebDriver driver) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
