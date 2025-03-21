package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LKuletskaiaTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    private void createDriver() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1200, 800));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterMethod
    private void quitDriver() {
        if(this.driver != null) {
            this.driver.quit();
        }
    }

    @Test
    public void testLoginWithValidData() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        driver.findElement(By.cssSelector(".oxd-input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector(".oxd-button[type='submit']")).click();

        WebElement dashboardHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".oxd-text.oxd-topbar-header-breadcrumb-module")));

        Assert.assertEquals(dashboardHeader.getText(), "Dashboard", "Login failed!");
    }

    @Test
    public void testLogout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        driver.findElement(By.cssSelector(".oxd-input[name='password']")).sendKeys("admin123");
        driver.findElement(By.cssSelector(".oxd-button[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-userdropdown-tab"))).click();
        driver.findElement(By.cssSelector("a[href*='logout']")).click();

        WebElement titleLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".orangehrm-login-title")));
        Assert.assertEquals(titleLogin.getText(), "Login", "Logout failed!");
    }
}
