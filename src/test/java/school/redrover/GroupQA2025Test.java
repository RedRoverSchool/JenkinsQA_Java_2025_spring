package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class GroupQA2025Test {

    WebDriver driver;

    @BeforeTest
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    void teardown() {
        driver.quit();
    }

    @Test
    public void uitapSampleAppEmptyLoginEmptyPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        WebElement linkVerifyText = driver.findElement(By.linkText("Sample App"));
        linkVerifyText.click();

        WebElement textWelcomeUser = driver.findElement(By.xpath("//button[@class = 'btn btn-primary']"));
        textWelcomeUser.click();

        WebElement labelTextDanger = driver.findElement(By.xpath("//label[@class = 'text-danger']"));
        String labelTextDangerText = labelTextDanger.getText();

        Assert.assertEquals(labelTextDangerText, "Invalid username/password");
    }

    @Test
    public void uitapSampleAppCorrectLoginEmptyPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        WebElement linkVerifyText = driver.findElement(By.linkText("Sample App"));
        linkVerifyText.click();

        WebElement inputUserName = driver.findElement(By.xpath("//input[@name = 'UserName']"));
        inputUserName.sendKeys("UserName");

        WebElement textWelcomeUser = driver.findElement(By.xpath("//button[@class = 'btn btn-primary']"));
        textWelcomeUser.click();

        WebElement labelTextDanger = driver.findElement(By.xpath("//label[@class = 'text-danger']"));
        String labelTextDangerText = labelTextDanger.getText();

        Assert.assertEquals(labelTextDangerText, "Invalid username/password");
    }

    @Test
    public void uitapSampleAppEmptyLoginCorrectPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        WebElement linkVerifyText = driver.findElement(By.linkText("Sample App"));
        linkVerifyText.click();

        WebElement inputPassword = driver.findElement(By.xpath("//input[@name = 'Password']"));
        inputPassword.sendKeys("pwd");

        WebElement textWelcomeUser = driver.findElement(By.xpath("//button[@class = 'btn btn-primary']"));
        textWelcomeUser.click();

        WebElement labelTextDanger = driver.findElement(By.xpath("//label[@class = 'text-danger']"));
        String labelTextDangerText = labelTextDanger.getText();

        Assert.assertEquals(labelTextDangerText, "Invalid username/password");
    }

    @Test
    public void uitapSampleAppCorrectLoginCorrectPasswordTest() {

        driver.get("http://uitestingplayground.com/");

        WebElement linkVerifyText = driver.findElement(By.linkText("Sample App"));
        linkVerifyText.click();

        WebElement inputUserName = driver.findElement(By.xpath("//input[@name = 'UserName']"));
        inputUserName.sendKeys("UserName");

        WebElement inputPassword = driver.findElement(By.xpath("//input[@name = 'Password']"));
        inputPassword.sendKeys("pwd");

        WebElement textWelcomeUser = driver.findElement(By.xpath("//button[@class = 'btn btn-primary']"));
        textWelcomeUser.click();

        WebElement labelTextSuccess = driver.findElement(By.xpath("//label[@class = 'text-success']"));
        String labelTextSuccessText = labelTextSuccess.getText();

        Assert.assertEquals(labelTextSuccessText, "Welcome, UserName!");
    }
   @ Test
    public void linkIsClickableTest(){
       driver.get("http://uitestingplayground.com/");
       WebElement link = driver.findElement(By.cssSelector("[href=\"/dynamicid\"]"));

       Assert.assertTrue(link.isEnabled());
   }
}
