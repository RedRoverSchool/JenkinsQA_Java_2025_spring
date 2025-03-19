package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PodkovaTest {
    @Test
    public void testTextBox() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com");

        WebElement elementsSection = driver.findElement(By.xpath("//h5[text()='Elements']"));
        elementsSection.click();

        WebElement textBoxSection = driver.findElement(By.xpath("//span[text()='Text Box']"));
        textBoxSection.click();

        driver.findElement(By.id("userName")).sendKeys("Nick");
        driver.findElement(By.id("userEmail")).sendKeys("nick@gmail.com");
        driver.findElement(By.id("currentAddress")).sendKeys("325 Main St");
        driver.findElement(By.id("permanentAddress")).sendKeys("576 Main St");

        driver.findElement(By.cssSelector("#submit")).click();

        String outputName = driver.findElement(By.id("name")).getText();
        String outputEmail = driver.findElement(By.id("email")).getText();
        String outputCurrentAddress = driver.findElement(By.xpath("//p[@id='currentAddress']")).getText();
        String outputPermanentAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']")).getText();

        Assert.assertEquals(outputName, "Name:Nick");
        Assert.assertEquals(outputEmail, "Email:nick@gmail.com");
        Assert.assertTrue(outputCurrentAddress.contains("Current Address :325 Main St"));
        Assert.assertTrue(outputPermanentAddress.contains("Permananet Address :576 Main St"));

        driver.quit();
    }

    @Test
    public void testButton() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo.guru99.com/test/radio.html");

        WebElement option1 = driver.findElement(By.xpath("//input[@value='Option 1']"));
        option1.click();
        Assert.assertTrue(option1.isSelected());

        WebElement option2 = driver.findElement(By.xpath("//input[@value='Option 2']"));
        option2.click();
        Assert.assertTrue(option2.isSelected());

        WebElement option3 = driver.findElement(By.xpath("//input[@value='Option 3']"));
        option3.click();
        Assert.assertTrue(option3.isSelected());

        WebElement checkbox1 = driver.findElement(By.xpath("//input[@value='checkbox1']"));
        checkbox1.click();
        Assert.assertTrue(checkbox1.isSelected());

        WebElement checkbox2 = driver.findElement(By.xpath("//input[@value='checkbox2']"));
        checkbox2.click();
        Assert.assertTrue(checkbox2.isSelected());

        WebElement checkbox3 = driver.findElement(By.xpath("//input[@value='checkbox3']"));
        checkbox3.click();
        Assert.assertTrue(checkbox3.isSelected());

        driver.quit();
    }
}
