package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {

    @Test
    public void testProductsMenu(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/");

        WebElement productsButton = driver.findElement(By.xpath("//a[@href='/products']"));
        productsButton.click();

        WebElement textTitle = driver.findElement(By.xpath("//div[@class='features_items']/h2"));
        String text = textTitle.getText().toLowerCase();

        Assert.assertEquals(text, "all products");

        driver.quit();
    }
}
