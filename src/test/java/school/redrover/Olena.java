package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Olena{

    @Test
    public void testGoogle() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("tomsmith");

        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        driver.findElement(By.xpath("//*[@id=\"login\"]/button/i")).click();

        Assert.assertTrue(driver
                .findElement(By.xpath("//*[@id=\"flash\"]"))
                .getText()
                .contains("You logged into a secure area!"));

        driver.quit();
    }
}
