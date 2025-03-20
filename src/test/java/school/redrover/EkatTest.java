package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class EkatTest {

    @Test
    public void firstTest() {
        WebDriverManager.chromedriver();

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals(title, "Web form");

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals(value, "Received!");

        driver.quit();
    }

    @Test
    public void firstCoffeeTest() {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://coffee-cart.app/");

        String title = driver.getTitle();
        assertEquals(title, "Coffee cart");

        WebElement menuButton = driver.findElement(By.cssSelector("a[aria-label='Menu page']"));
        assertEquals(menuButton.getText(), "menu");

        WebElement cartButton = driver.findElement(By.cssSelector("a[aria-label='Cart page']"));
        assertEquals(cartButton.getText(), "cart (0)");

        WebElement gitButton = driver.findElement((By.cssSelector("a[aria-label='GitHub page']")));
        assertEquals(gitButton.getText(),"github");


        driver.quit();

    }
}
