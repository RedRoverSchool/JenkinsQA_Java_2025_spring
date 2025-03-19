package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");

        Thread.sleep(5000);

        // WebElement button1 = driver.findElement(By.xpath("//*[@class = QS5gu sy4vM"));

        WebElement input =
                driver.findElement(By.id("APjFqb"));
        input.sendKeys("selenium");

        Thread.sleep(10000);

        WebElement button = driver.findElement(By.xpath("//input[@value='Поиск в Google']"));
        button.click();

        Thread.sleep(1000);

        WebElement cite = driver.findElement(By.xpath("//cite[1]"));
        String citeText = cite.getText();

        Assert.assertEquals(citeText, "https://www.selenium.dev");

        driver.quit();
    }
}
