package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ZarinaDragDropTest {

    WebDriver driver = new ChromeDriver();

    @Test
    public void dragDropTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        WebElement title = driver.findElement(By.xpath("//h3[text()='Drag and Drop']"));
        String mainTitle = title.getText();
        Assert.assertEquals(mainTitle, "Drag and Drop");

        WebElement cubeA = driver.findElement(By.id("column-a"));
        WebElement cubeB = driver.findElement(By.id("column-b"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(cubeA, cubeB).build().perform();

        Thread.sleep(500);

        System.out.println("Success!");

        driver.quit();
    }
}
