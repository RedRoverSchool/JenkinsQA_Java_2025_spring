package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GroupQA2025Test {

    private WebDriver driver;

    @BeforeTest
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    void teardown() {
        driver.quit();
    }

    @Test
    public void testUitapSampleAppEmptyLoginEmptyPassword() {

        driver.get("http://uitestingplayground.com/");

        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppCorrectLoginEmptyPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'UserName']")).sendKeys("UserName");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppEmptyLoginCorrectPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'Password']")).sendKeys("pwd");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppCorrectLoginCorrectPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'UserName']")).sendKeys("UserName");
        driver.findElement(By.xpath("//input[@name = 'Password']")).sendKeys("pwd");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-success']")).getText(),
                "Welcome, UserName!"
        );
    }

    @Test
    public void linkIsClickableTest() {
        driver.get("http://uitestingplayground.com/");

        assertTrue(driver.findElement(By.cssSelector("[href=\"/dynamicid\"]")).isEnabled());
    }

    @Test
    public void getLinkName() {
        driver.get("http://uitestingplayground.com/");

        assertEquals(driver.findElement(By.cssSelector("[href=\"/dynamicid\"]")).getText(),"Dynamic ID");
    }

    @Test
    public void getLinkNameShorter() {
        driver.get("http://uitestingplayground.com/");

        assertEquals(driver.findElement(By.cssSelector("[href=\"/classattr\"]")).getText(), "Class Attribute");
    }
}
