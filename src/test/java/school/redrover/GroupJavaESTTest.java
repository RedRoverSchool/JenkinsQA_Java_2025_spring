package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GroupJavaESTTest {

    @Test
    public void testSuccessfulLogin(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        String title = driver.getTitle();
        assertEquals(title, "Swag Labs");


        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.name("login-button"));
        loginButton.click();

        WebElement header = driver.findElement(By.className("app_logo"));
        assertTrue(header.isDisplayed());

        driver.quit();

    }


}
