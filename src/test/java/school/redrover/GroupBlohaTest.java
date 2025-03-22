package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupBlohaTest {

    @Test
    public void magentoTest() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();


        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.id("ui-id-5")).click();
        driver.findElement(By.linkText("Tops")).click();
        driver.findElement(By.className("product-item-link")).click();
        driver.findElement(By.xpath("//span[text()='Add to Wish List']")).click();

        driver.quit();


    }

}
