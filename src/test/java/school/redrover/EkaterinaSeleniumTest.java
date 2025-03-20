package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class EkaterinaSeleniumTest {
    By submittedForm = By.xpath("//h1[text()='Form submitted']");

    @Test
    public void testSeleniumWebForms() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.findElement(By.id("my-text-id")).sendKeys("UserName");
        driver.findElement(By.name("my-password")).sendKeys("Password");
        driver.findElement(By.name("my-textarea")).sendKeys("RandomText");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();

        driver.quit();

        assertTrue(driver.findElement(submittedForm).isDisplayed(),"Form submitted");
    }
}
