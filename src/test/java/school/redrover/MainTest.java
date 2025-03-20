package school.redrover;

import com.google.errorprone.annotations.RestrictedApi;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MainTest {

    @Test
    public void testSum() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = driver.findElement(By.id("my-text-id"));
        WebElement submitButton = driver.findElement(By.tagName("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        sleep(1000);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();

        assertEquals(value, "Received!");

        driver.quit();
    }
    @Test
    public void testLockedOutUserLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Swag Labs");

        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("locked_out_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        sleep(1000);

        WebElement errorMessage = driver.findElement(By.xpath("//h3"));
        Assert.assertTrue(errorMessage.isDisplayed());

        sleep(1000);
        driver.quit();
    }

}
