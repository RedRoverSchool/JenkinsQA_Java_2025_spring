package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyFirstTest {

        @Test
        public void firstTest () {

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();

            driver.get("https://touristpass.jp/en/fuji_shizuoka/");

            WebElement topMiniButton = driver.findElement(By.linkText("TOP"));

            topMiniButton.click();

            WebElement heading = driver.findElement(By.linkText("Tourist Pass"));
            String value = heading.getText();
            Assert.assertEquals(value,"Tourist Pass");

            driver.quit();
        }

}
