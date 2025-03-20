package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupJavaTerminatorsTest {
    @Test

    public void authorizationSwagLabs() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement login = driver.findElement(By.cssSelector("#user-name"));
        WebElement password = driver.findElement(By.cssSelector("#password"));
        WebElement loginButton = driver.findElement(By.cssSelector("#login-button"));

        login.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        Thread.sleep(5000);


        String pageTitle = driver.getTitle();

        assertEquals(pageTitle,"Swag Labs");

        driver.quit();
    }

    @Test(description = "Check, that Sauce Labs Backpack has been added to the cart and total price is 32.39")
    public void totalOrderPriceTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("shopping_cart_container")).click();
        driver.findElement(By.id("checkout")).click();

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("90-021");
        driver.findElement(By.id("continue")).click();

        String expectedRes = driver.findElement(By.xpath("//div[@data-test='total-label']")).getText();

        Assert.assertEquals(expectedRes, "Total: $32.39");

        driver.quit();
    }
}