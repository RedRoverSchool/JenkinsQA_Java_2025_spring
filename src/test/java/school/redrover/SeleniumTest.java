package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SeleniumTest {

    @Test
    public void testSelenium() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("https://sauce-demo.myshopify.com/");

        WebElement catalog = driver.findElement(By.xpath("//*[@id='main-menu']/li[2]/a"));
        catalog.click();
        Thread.sleep(1000);
        WebElement shades = driver.findElement(By.id("product-3"));
        shades.click();
        Thread.sleep(1000);

        WebElement found = driver.findElement(By.xpath("//*[@id='product-form']/h1"));
        String value = found.getText();

        assertEquals(value, "Brown Shades");

        driver.quit();
    }
}
