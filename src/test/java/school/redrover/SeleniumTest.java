package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class SeleniumTest {

    @Test
    public void testSelenium() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals(title, "Web form");

        WebElement textBox = driver.findElement(By.xpath("//*[@name = 'my-textarea']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        Thread.sleep(1000);

        textBox.sendKeys("Привет, я автотест");
        submitButton.click();

        Thread.sleep(1500);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals(value, "Received!");

        driver.quit();
    }
}
