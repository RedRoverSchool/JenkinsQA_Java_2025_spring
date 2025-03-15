package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class GroupAQARookiesTest {

    @Test
    public void testOnliner() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.onliner.by/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Onlíner");

        WebElement textBox = driver.findElement(By.xpath("//*[@id=\"fast-search\"]/div/input"));
        textBox.sendKeys("Iphone");

        WebElement iframe = driver.findElement(By.cssSelector("iframe.modal-iframe"));
        driver.switchTo().frame(iframe);
        Thread.sleep(2000);

        WebElement iphoneLink = driver.findElement(By.xpath("//a[contains(text(), 'Телефон Apple iPhone 16e 128GB (белый)')]"));
        iphoneLink.click();

        String title2 = driver.getTitle();
        Assert.assertEquals(title2, "iPhone 16e 128GB белый (Айфон 16е) купить в Минске");

        driver.quit();
    }

    @Test
    public void duckDuckGoTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://duckduckgo.com/");

        WebElement textBox = driver.findElement(By.id("searchbox_input"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[aria-label='Search']"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        Thread.sleep(1000);

        WebElement spanText = driver.findElement(By.xpath("//*[@id=\"r1-0\"]/div[2]/div/div/a/div/p/span"));
        String value = spanText.getText();
        assertEquals("https://www.selenium.dev", value);

        driver.quit();
    }

    @Test
    public void testAddProductToTheCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://theweldercatherine.ru/");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Интернет Магазин кофе The Welder Catherine — The Welder Catherine");

        WebElement product = driver.findElement(By.xpath("//a[@title='Порционный горячий шоколад \"Гала-Мокко\" The Welder Catherine & UNICAVA']"));
        product.click();

        WebElement addToCartButton = driver.findElement(By.id("on_cart"));
        addToCartButton.click();

        Thread.sleep(1000);

        WebElement modal = driver.findElement(By.className("modal-min-order"));
        WebElement minOrderText = modal.findElement(By.className("modal-card-min--text"));
        Assert.assertEquals(minOrderText.getText(), "Минимальный заказ");

        Thread.sleep(1000);

        Actions actions = new Actions(driver);
        actions.moveByOffset(300, 400).click().perform();

        WebElement goToCartButton = driver.findElement(By.className("go-to-cart"));
        goToCartButton.click();

        WebElement heading = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(heading.getText(), "оформление заказа");

        driver.quit();
    }

}
