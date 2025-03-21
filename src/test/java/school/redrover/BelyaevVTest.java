package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;

public class BelyaevVTest {

    @Test
    public void testCheckResultSearch() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement searchButton = driver.findElement(By.className("heading-search__open"));
        searchButton.click();

        WebElement searchText = driver.findElement(By.className("heading-search__input"));
        searchText.sendKeys("Api111");

        searchButton.click();

        WebElement foundText = driver.findElement(By.className("search__title"));
        String resultSearch = foundText.getText();

        Assert.assertEquals(resultSearch, "api");

        driver.quit();
    }

    @Test
    public void testAppearedHeader() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://thecode.media/");

        WebElement searchArea = driver.findElement(By.className("tab-questions"));

        Action myAction = new Actions(driver).doubleClick(searchArea).build();
        myAction.perform();

        WebElement foundText = driver.findElement(By.xpath("(//h1[@class='search__title'])"));
        String foundSearchTitle = foundText.getText();

        Assert.assertEquals(foundSearchTitle, "Как решить");

        driver.quit();
    }
}
