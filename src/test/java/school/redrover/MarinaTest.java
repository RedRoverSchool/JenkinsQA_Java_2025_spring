package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MarinaTest {

    @Test
    public void testLoginPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/login");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");

        Thread.sleep(1000);

        WebElement buttonLogin = driver.findElement(By.xpath("//*[@id='login']/button"));
        buttonLogin.click();

        Thread.sleep(1000);

        WebElement message = driver.findElement(By.xpath("//*[@id=\"flash\"]"));
        Assert.assertTrue(message.isDisplayed());
        Assert.assertTrue(message.getText().contains("You logged into a secure area!"));

        WebElement buttonLogout = driver.findElement(By.xpath("//*[@id=\"content\"]/div/a"));
        buttonLogout.click();

        driver.quit();


    }
}
