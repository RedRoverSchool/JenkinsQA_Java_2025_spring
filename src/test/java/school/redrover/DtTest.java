package school.redrover;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DtTest {

    @Test
    public void testA() {

        WebDriver driver = new ChromeDriver();
        driver.get ("khl.ru");

        WebElement searchButton = driver.findElement();
        searchButton.click();

        WebElement searchButton = driver.findElement();
        searchButton.click();

        WebElement searchButton = driver.findElement();
        searchButton.click();

        Assert.assertEquals();
        driver.quit();
    }

}
