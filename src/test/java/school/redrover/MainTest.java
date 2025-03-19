package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MainTest {

    @Test
    public void testSum() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        //driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals(title, "Web form");

        WebElement textBox = driver.findElement(By.id("my-text-id"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium123");
        submitButton.click();

        Thread.sleep(1000);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals(value,"Received!");

        driver.quit();
    }
}
