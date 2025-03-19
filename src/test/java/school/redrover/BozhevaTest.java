package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class BozhevaTest {

    @Test
    public void testWebForm() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        driver.findElement(By.name("my-text")).sendKeys("Test Input");
        driver.findElement(By.name("my-password")).sendKeys("pass123");
        driver.findElement(By.name("my-textarea")).sendKeys("Привет всем кто учит Java.");

        WebElement returnLink = driver.findElement(By.xpath("//a[contains(text(), 'Return to index')]"));

        driver.findElement(By.name("my-select")).sendKeys("Two");

        WebElement inputField = driver.findElement(By.cssSelector("input[name='my-datalist']"));
        inputField.click();
        inputField.sendKeys("New York");

        driver.findElement(By.xpath("//input[@id='my-check-1']")).click();
        driver.findElement(By.xpath("//input[@id='my-radio-1']")).click();

        // элемент с DatePicker
        WebElement dateInput = driver.findElement(By.cssSelector("input[name='my-date']"));
        dateInput.sendKeys("17/03/2025");
        assertEquals("17/03/2025", dateInput.getAttribute("value"));


        WebElement slider = driver.findElement(By.name("my-range"));
        Actions actions = new Actions(driver);

        // Передвигаем ползунок
        actions.clickAndHold(slider).moveByOffset(50, 0).release().perform();
        driver.findElement(By.tagName("button")).click();

        WebElement element = driver.findElement(By.cssSelector("h1.display-6"));

        assertEquals(element.getText(), "Form submitted", "Текст 'Form submitted' не найден!");


        WebElement receivedMessage = driver.findElement(By.cssSelector("div.col-12.py-2"));


        Assert.assertTrue(receivedMessage.isDisplayed(), "Текст 'Received!' не найден!");


        driver.quit();
    }
}


