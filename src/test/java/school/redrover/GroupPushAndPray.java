package rover2025;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.goodfon.ru/");


        WebElement strokaPoiska = driver.findElement(By.className("js-search"));
        strokaPoiska.sendKeys(Keys.CONTROL + "a");
        strokaPoiska.sendKeys(Keys.DELETE);
        strokaPoiska.sendKeys("Дворцовая площадь");

        WebElement knopka = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div[1]/form/button"));
        knopka.click();


        String ssilka = driver.getCurrentUrl();

        Assert.assertEquals(ssilka, "https://www.goodfon.ru/search/?q=%D0%94%D0%B2%D0%BE%D1%80%D1%86%D0%BE%D0%B2%D0%B0%D1%8F+%D0%BF%D0%BB%D0%BE%D1%89%D0%B0%D0%B4%D1%8C");

        driver.quit();
    }
}
