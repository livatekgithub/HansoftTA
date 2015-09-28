import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

/**
 * Created by kyak on 23.09.2015.
 */
public class WindowOperations {

    public static void resizeWindowforIdea(WebDriver driver) {
        Point point = new Point(10, 10);
        Dimension dimension = new Dimension(1900, 700);
        driver.manage().window().setPosition(point);
        driver.manage().window().setSize(dimension);
    }
}
