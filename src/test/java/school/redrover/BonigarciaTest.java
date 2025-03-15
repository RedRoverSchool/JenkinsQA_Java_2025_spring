package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import java.util.List;


public class BonigarciaTest {

    @Test
    void testChaptersSize(){

        final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
        WebDriver driver = new ChromeDriver();

        driver.get(BASE_URL);
        List<WebElement> chapters = driver.findElements(By.cssSelector("h5.card-title"));

        assertEquals(chapters.size(), 6);

        driver.quit();
    }
}
