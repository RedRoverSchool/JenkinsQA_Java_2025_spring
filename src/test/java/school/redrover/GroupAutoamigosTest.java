package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupAutoamigosTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    @Test
    public void testWebFormLink() {
        WebElement link = driver.findElement(By.linkText("Web form"));
        link.click();
        Assert.assertNotEquals(driver.getTitle(), "Страница не загрузилась: Web form");
        driver.navigate().back();
    }

    @Test
    public void testNavigationLink() {
        WebElement link = driver.findElement(By.linkText("Navigation"));
        link.click();
        Assert.assertNotEquals(driver.getTitle(), "Страница не загрузилась: Navigation");
        driver.navigate().back();
    }

    @Test
    public void testDropdownMenuLink() {
        WebElement link = driver.findElement(By.linkText("Dropdown menu"));
        link.click();
        Assert.assertNotEquals(driver.getTitle(), "Страница не загрузилась: Dropdown menu");
        driver.navigate().back();
    }
}