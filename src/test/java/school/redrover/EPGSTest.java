package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EPGSTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
//        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testProba1() {
        driver.get("https://the-internet.herokuapp.com/");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement lnkContextMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Context Menu")));
            lnkContextMenu.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h3")));

        } catch (NoSuchElementException e) {
            System.out.println("Element not found!");
        }

        Assert.assertEquals(driver.findElement(By.tagName("h3")).getText(), "Context Menu", "<<< Text does not match! >>>");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}



