package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EvgenyZagrebelnikovTest {

    @Test
    public void testTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://world-weather.ru/pogoda/");

        WebElement archiveButton = driver.findElement(By.xpath("//*[@id=\"meny\"]/li[2]/a"));
        Thread.sleep(2000);
        archiveButton.click();

        WebElement textTitle = driver.findElement(By.xpath("//*[@id=\"content-left\"]/h1"));
        String text = textTitle.getText();

        Assert.assertEquals(text, "Архив погоды в мире");

        driver.quit();
    }

    @Test
    public void testSearchAndSelectCity() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://openweathermap.org/");

        WebElement searchInputFieldAndEnterCityName = driver.findElement(By.xpath("//*[@id=" +
                "\"weather-widget\"]/div[2]/div/div/div[2]/div[1]/div/input"));
        searchInputFieldAndEnterCityName.sendKeys("London");

        WebElement searchButtonClick = driver.findElement(By.xpath("//*[@id=\"weather-widget\"]" +
                "/div[2]/div/div/div[2]/div[1]/button"));
        searchButtonClick.click();
        Thread.sleep(2000);

        WebElement selectCityFromList = driver.findElement(By.xpath("//*[@id=\"weather-widget\"]/div[2]" +
                "/div/div/div[2]/div[1]/div/ul/li[2]"));
        Thread.sleep(1000);
        selectCityFromList.click();
        Thread.sleep(2000);

        WebElement checkCityTitle = driver.findElement(By.xpath("//*[@id='weather-widget']" +
                "/div[3]/div[1]/div[1]/div[1]/h2"));
        String text = checkCityTitle.getText();

        Assert.assertEquals(text, "London, CA");
        Thread.sleep(1000);

        driver.quit();

    }
}
