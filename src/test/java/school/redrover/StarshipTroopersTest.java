package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StarshipTroopersTest {
    //hot-fix
    private WebDriver driver;

    @BeforeMethod
    public void driverUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void driverDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDemoQAForm () throws InterruptedException {

        driver.get("https://demoqa.com/");

        driver.findElement(By.xpath("//h5[text()='Forms']")).click();
        driver.findElement(By.xpath("//span[text()='Practice Form']")).click();

        driver.findElement(By.id("firstName")).sendKeys("MARINA");
        driver.findElement(By.id("lastName")).sendKeys("IVANOV");
        driver.findElement(By.id("userEmail")).sendKeys("ALEX@IVANOV.COM");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[3]/div[2]/div[3]")).click();
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[11]/div/button")).click();

        Thread.sleep(1000);

        String result = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        Assert.assertEquals(result,"Thanks for submitting the form");

        String result2 = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/table/tbody/tr[2]/td[2]")).getText();
        Assert.assertEquals(result2,"ALEX@IVANOV.COM");
    }

}
