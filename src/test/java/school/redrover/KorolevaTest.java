package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KorolevaTest {
    @Test
    public void testStNina() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://stninaorlando.org");

        WebElement input = driver.findElement(By.xpath("//input[@class='required email']"));
        input.sendKeys("natasha");

        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("//input[@id='mc-embedded-subscribe']"));
        button.click();

        WebElement page = driver.findElement(By.xpath("//div[@class='formstatus error']"));
        String pageText = page.getText();
        Assert.assertEquals(pageText,"There are errors below");

        driver.quit();
    }
}
