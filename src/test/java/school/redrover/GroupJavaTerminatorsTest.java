package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

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
}