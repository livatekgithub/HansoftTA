import enums.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class General implements AccessData {

    /**
     * IDEAS FOR FURTHER TESTING
     * 1. Colourful Widgets
     * 2. Infinitive Removing
     * 3. Many Named Cards Creation
     */

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static String nowTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void loginUser(WebDriver driver) throws InterruptedException {
        driver.get(AccessData.TESTURL);
        driver.findElement(By.id("form-email")).clear();
        driver.findElement(By.id("form-email")).sendKeys(TESTLOGIN);
        driver.findElement(By.id("form-psw")).clear();
        driver.findElement(By.id("form-psw")).sendKeys(TESTPASSWORD);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("button.btn-submit.js-tap-indication")).click();
        assertTrue(isElementPresent(By.cssSelector("span.avatar-initials"), driver));
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void userNotFound(WebDriver driver) {
        driver.get(AccessData.TESTURL);
        driver.findElement(By.id("form-email")).clear();
        driver.findElement(By.id("form-email")).sendKeys(WRONGTESTLOGIN);
        driver.findElement(By.id("form-psw")).clear();
        driver.findElement(By.id("form-psw")).sendKeys(WRONGTESTPASSWORD);
        driver.findElement(By.cssSelector("button.btn-submit.js-tap-indication")).click();
        assertEquals("User not found", driver.findElement(By.cssSelector("div.form-message.firepath-matching-node")).getText());
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void loginUserWithCash(WebDriver driver, int numberofAttempts) throws InterruptedException {
        driver.navigate().to(AccessData.TESTURL);
        Thread.sleep(3000);
//        driver.findElement(By.id("form-email")).clear();
//        driver.findElement(By.id("form-email")).sendKeys(TESTLOGIN);
//        driver.findElement(By.id("form-psw")).clear();
//        driver.findElement(By.id("form-psw")).sendKeys(TESTLOGIN);
//        Thread.sleep(3000);
        for (int i = 1; i < numberofAttempts; i++) {
            driver.findElement(By.cssSelector("button.btn-submit.js-tap-indication")).click();
            Thread.sleep(3000);
            assertTrue(isElementPresent(By.cssSelector("span.avatar-initials"), driver));
            if (isElementPresent(By.cssSelector("span.avatar-initials"), driver))
                System.out.println("Attempt " + i + " was successful");
            else System.out.println("Attempt " + i + " was NOT successful");
            driver.findElement(By.cssSelector("span.avatar-initials")).click();
            driver.findElement(By.cssSelector("div.app-menu-choice.js-signout")).click();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void userSignOut(WebDriver driver) {
        driver.findElement(By.cssSelector("span.avatar-initials")).click();
        driver.findElement(By.cssSelector("div.app-menu-choice.js-signout")).click();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void pagesCreation(WebDriver driver, int number, PageSharingMode pageSharingMode) {

        final String ADD_PAGE_XPATH = "html/body/div[2]/div[2]/div[1]/img";
        final String DONE_BUTTON_XPATH = "html/body/div[3]/div/div/div[1]/div[2]/div/div[5]";
        final String TYPE_PAGENAME_XPATH = "html/body/div[3]/div/div/div[1]/div[1]/div[1]/input";
        final String SHARE_WITHORG_XPATH = "html/body/div[3]/div/div/div[1]/div[1]/div[3]/div[5]/div/img";
        String name;

        for (int i = 1; i <= number; i++) {
            name = "Page" + Integer.toString(i);
            driver.findElement(By.xpath(ADD_PAGE_XPATH)).click();
//            System.out.println(nowTime() + " Page " + i + " was created:");
            driver.findElement(By.xpath(TYPE_PAGENAME_XPATH)).clear();
            driver.findElement(By.xpath(TYPE_PAGENAME_XPATH)).sendKeys(name);
            if (pageSharingMode == PageSharingMode.PUBLIC) driver.findElement(By.xpath(SHARE_WITHORG_XPATH)).click();
            driver.findElement(By.xpath(DONE_BUTTON_XPATH)).click();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void pagesRemoving(WebDriver driver, int number) {

        final String OPEN_PAGEMENU_XPATH = "html/body/div[2]/div[2]/div[2]/div[1]/div/div/div[1]";
        final String DELETE_BUTTON_XPATH = "html/body/div[3]/div/div/div[1]/div[2]/div/div[1]";
        String name;

        for (int i = 1; i <= number; i++) {
            name = "Page" + Integer.toString(i);
            driver.findElement(By.xpath(OPEN_PAGEMENU_XPATH)).click();
            driver.findElement(By.xpath(DELETE_BUTTON_XPATH)).click();
            driver.findElement(By.xpath(DELETE_BUTTON_XPATH)).click();
//            System.out.println(nowTime() + " Page " + i + " was removed:");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void pagesArchiving(WebDriver driver, int number) {

        final String ADD_PAGE_XPATH = "html/body/div[2]/div[2]/div[1]/img";
        final String DONE_BUTTON_XPATH = "html/body/div[3]/div/div/div[1]/div[2]/div/div[5]";
        final String TYPE_PAGENAME_XPATH = "html/body/div[3]/div/div/div[1]/div[1]/div[1]/input";
        final String OPEN_PAGEMENU_XPATH = "html/body/div[2]/div[2]/div[2]/div[1]/div/div/div[1]";
        final String PAGE_ARCHIVE_XPATH = "html/body/div[3]/div/div/div[1]/div[2]/div/div[2]";
        String name;
        for (int i = 1; i <= number; i++) {
            name = "PageArch" + Integer.toString(i);
            driver.findElement(By.xpath(ADD_PAGE_XPATH)).click();
//            System.out.println(nowTime() + " PageArch " + i + " was created:");
            driver.findElement(By.xpath(TYPE_PAGENAME_XPATH)).clear();
            driver.findElement(By.xpath(TYPE_PAGENAME_XPATH)).sendKeys(name);
            driver.findElement(By.xpath(DONE_BUTTON_XPATH)).click();
            driver.findElement(By.xpath(OPEN_PAGEMENU_XPATH)).click();
            driver.findElement(By.xpath(PAGE_ARCHIVE_XPATH)).click();
            driver.findElement(By.xpath(PAGE_ARCHIVE_XPATH)).click();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void widgetsCreation(WebDriver driver, int number, WidgetState widgetState, WidgetColor widgetColor, boolean isLogged) {

        final String IDEA_MENU_XPATH = "html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div/div[4]/img";
        final String BOARD_MENU_XPATH = "html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div/div[4]/img";

        String name;
        String colorId;
        int colorCode = 0;
        for (int i = 1; i <= number; i++) {
            name = Integer.toString(i);
            //Create Idea Widget with Sequential Name 1,2,..
            driver.findElement(By.cssSelector("img.xaddbutton-icon")).click();
            driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[1]/div[2]/input")).sendKeys("Idea " + name);
            driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[1]/div[2]/button")).click();
            if (isLogged) System.out.println(nowTime() + " Idea " + name + " was created:");

            //Create Board Widget with Sequential Name 1,2,..
            driver.findElement(By.cssSelector("div.xaddbutton-maindiv.js-workspace-tracking-add-board > img.xaddbutton-icon")).click();
            driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/input")).sendKeys("Board " + name);
            driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/button")).click();
            if (isLogged) System.out.println(nowTime() + " Board " + name + " was created:");

            if (widgetState == WidgetState.COLLAPSED) {
                driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[1]/div[2]")).click();
                if (isLogged) System.out.println(nowTime() + " Idea " + name + " was collapsed:");
                driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/div")).click();
                if (isLogged) System.out.println(nowTime() + " Board " + name + " was collapsed:");
            }

            if (widgetColor == WidgetColor.RANDOM) {
                if (i % 7 == 0) colorCode = 8;
                else colorCode = i % 7;
                colorId = Integer.toString(colorCode);
            } else if (widgetColor == WidgetColor.DEFAULT) {
                colorId = "1";
            } else {
                colorId = Integer.toString(widgetColor.ordinal());
            }

            if (isLogged)
                System.out.println(nowTime() + " Color=" + WidgetColor.values()[colorCode] + " Set up for Board");
            driver.findElement(By.xpath(BOARD_MENU_XPATH)).click();
            driver.findElement(By.id(colorId)).click();

            if (isLogged)
                System.out.println(nowTime() + " Color=" + WidgetColor.values()[colorCode] + " Set up for Idea");
            driver.findElement(By.xpath(IDEA_MENU_XPATH)).click();
            driver.findElement(By.id(colorId)).click();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public static void widgetsRemoval(WebDriver driver) {

    }

    //------------------------------------------------------------------------------------------------------------------------------------------------
    public static void firstIdeaCardsGeneration(WebDriver driver, int number) {
        String name;
        String dynamicPart;
        final String IDEA_CARD_X_PATH_FIRST_PART = "html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/";
        final String IDEA_CARD_X_PATH_BUTTON = "/div[1]/div[1]/button";
        final String IDEA_CARD_X_PATH_TEXT = "/div[1]/textarea";
        final String IDEA_CARD_X_PATH_ADD_CARD = "/div";


        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div/div")).click();
        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div[1]/div[1]/textarea")).sendKeys("Idea Card 1");
        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div[1]/div[1]/div[1]/button")).click();

        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div[2]/div")).click();
        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div[2]/div[1]/textarea")).sendKeys("Idea Card 2");
        driver.findElement(By.xpath("html/body/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div/div[1]/div[2]/div/div/div[2]/div[1]/div[1]/button")).click();

        for (int i = 3; i <= number; i++) {
            name = Integer.toString(i);
            dynamicPart = "div[" + name + "]";
//            System.out.println(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_ADD_CARD);
            driver.findElement(By.xpath(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_ADD_CARD)).click();

//            System.out.println(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_TEXT);
            driver.findElement(By.xpath(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_TEXT)).sendKeys("Idea Card " + name);

//            System.out.println(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_BUTTON);
            driver.findElement(By.xpath(IDEA_CARD_X_PATH_FIRST_PART + dynamicPart + IDEA_CARD_X_PATH_BUTTON)).click();
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------
    public static void columnsCreation(WebDriver driver, int number) {

        final String ADD_COLUMN_X_PATH_FIRST_PART = "html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[2]/div/div/";
        final String RENAME_COLUMN_X_PATH_SECOND_PART = "html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[2]/div/div/";

        String counterString;
        String dynamicPart;
        String currentXpathForAdding;
        String currentXpathForRenaming;
        int shiftedValue;

        for (int i = 1; i <= number; i++) {
            shiftedValue = i + 2;
            counterString = Integer.toString(shiftedValue);
            dynamicPart = "div[" + counterString + "]";
            currentXpathForAdding = ADD_COLUMN_X_PATH_FIRST_PART + dynamicPart + "/div";
            currentXpathForRenaming = RENAME_COLUMN_X_PATH_SECOND_PART + dynamicPart + "/div[1]/input";

//            System.out.println("Log: " + currentXpathForAdding);
            driver.findElement(By.xpath(currentXpathForAdding)).click();

//            System.out.println("Log: " + currentXpathForRenaming);
            driver.findElement(By.xpath(currentXpathForRenaming)).sendKeys("Column " + shiftedValue);
//            System.out.println("Column" + counterString + " created. Xpath=" + currentXpathForAdding);
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------

    public static void firstBoardCardsGeneration(WebDriver driver, int numberX, int numberY) {
        String stringCounterX;
        String stringCounterY;
        String dynamicPartX;
        String dynamicPartY;
        String currentXpathForAdding;
        String currentXpathForNaming;
        String currentXpathForSaving;

        final String BOARD_CARD_X_PATH_FIRST_PART = "html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[1]/div[2]/";
        final String BOARD_CARD_X_PATH_FIRST_PART_V2 = "html/body/div[2]/div[1]/div[1]/div[3]/div/div[2]/div/div[1]/div[2]/div/div/";
        final String BOARD_CARD_X_PATH_ADD_CARD = "/div";
        final String BOARD_CARD_X_PATH_TEXT = "/div/textarea";
        final String BOARD_CARD_X_PATH_BUTTON = "/div/div[1]/button";
        final String BOARD_CARD_X_PATH_MEDIUM = "/div[2]/";

        if (numberY > 2) columnsCreation(driver, numberY - 2);

        for (int i = 1; i <= numberX; i++) {
            stringCounterX = Integer.toString(i);
            if (i == 1) dynamicPartX = "div";
            else dynamicPartX = "div[" + stringCounterX + "]";

            for (int j = 1; j <= numberY; j++) {
                stringCounterY = Integer.toString(j);
                dynamicPartY = "div[" + stringCounterY + "]";

                currentXpathForAdding = BOARD_CARD_X_PATH_FIRST_PART_V2 + dynamicPartY + BOARD_CARD_X_PATH_MEDIUM + dynamicPartX + BOARD_CARD_X_PATH_ADD_CARD;
                currentXpathForNaming = BOARD_CARD_X_PATH_FIRST_PART_V2 + dynamicPartY + BOARD_CARD_X_PATH_MEDIUM + dynamicPartX + BOARD_CARD_X_PATH_TEXT;
                currentXpathForSaving = BOARD_CARD_X_PATH_FIRST_PART_V2 + dynamicPartY + BOARD_CARD_X_PATH_MEDIUM + dynamicPartX + BOARD_CARD_X_PATH_BUTTON;

//                System.out.println(currentXpathForAdding);
                driver.findElement(By.xpath(currentXpathForAdding)).click();

//                System.out.println(currentXpathForNaming);
                driver.findElement(By.xpath(currentXpathForNaming)).sendKeys("Board Card " + stringCounterX + stringCounterY);

//                System.out.println(currentXpathForSaving);
                driver.findElement(By.xpath(currentXpathForSaving)).click();

            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------
    public static void todoCardCreation(WebDriver driver, int number, TodoCardStatus todoCardStatus) throws InterruptedException {

        String currentXpathForAdding;
        String currentXpathForNaming;
        String currentXpathForSaving;
        String dynamicPart;
        String cardName;
        String stringNumber;

        final String TODO_BUTTON_XPATH = "//div/div/div[3]";
        final String TODO_MARK_AS_COMPLETED_XPATH = "html/body/div[2]/div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[1]/div";
        final String TODO_ADDCARD_XPATH = "html/body/div[2]/div[1]/div[2]/div/div[2]/div/div[1]/div/";
        final String TODO_ADDCARD_XPATH_LAST = "/div";
        final String TODO_ENTERCARD_XPATH_LAST = "/div/textarea";
        final String TODO_SAVECARD_XPATH_LAST = "/div/div[1]/button";

        if (!driver.findElement(By.cssSelector(".workspace-sidepanel-title-content")).isDisplayed())
            driver.findElement(By.xpath(TODO_BUTTON_XPATH)).click();

        Thread.sleep(3000);
        if (todoCardStatus != TodoCardStatus.MARKFINISHED)
            for (int i = 1; i <= number; i++) {
                stringNumber = Integer.toString(i);
                cardName = "TODO Card " + stringNumber;
                dynamicPart = "div[" + i + "]";

                if (i == 1) currentXpathForAdding = TODO_ADDCARD_XPATH + "div" + TODO_ADDCARD_XPATH_LAST;
                else currentXpathForAdding = TODO_ADDCARD_XPATH + dynamicPart + TODO_ADDCARD_XPATH_LAST;

                driver.findElement(By.xpath(currentXpathForAdding)).click();
                Thread.sleep(300);
                System.out.println("Log: " + General.nowTime() + " " + currentXpathForAdding);

                currentXpathForNaming = TODO_ADDCARD_XPATH + dynamicPart + TODO_ENTERCARD_XPATH_LAST;
                driver.findElement(By.xpath(currentXpathForNaming)).sendKeys(cardName);
                Thread.sleep(300);
                System.out.println("Log: " + General.nowTime() + " " + currentXpathForNaming);

                currentXpathForSaving = TODO_ADDCARD_XPATH + dynamicPart + TODO_SAVECARD_XPATH_LAST;
                driver.findElement(By.xpath(currentXpathForSaving)).click();
                Thread.sleep(300);
                System.out.println("Log: " + General.nowTime() + " " + currentXpathForSaving);
                System.out.println("TODO Card " + stringNumber + " were created");
            }

        if (todoCardStatus != TodoCardStatus.CREATEUNFINISHED)
            for (int i = 1; i <= number; i++) {
                Thread.sleep(1000);
                driver.findElement(By.xpath(TODO_MARK_AS_COMPLETED_XPATH)).click();
            }
    }

    private static boolean isElementPresent(By by, WebDriver driver) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

