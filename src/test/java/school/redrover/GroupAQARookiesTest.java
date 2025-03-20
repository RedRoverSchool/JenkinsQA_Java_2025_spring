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
    public void onlinerTest() throws InterruptedException {
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

    @Test
    public void testBookOldFarmhouse() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationintesting.online/");
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Vasiliy");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("qwerty@mailto.ru");
        driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("+345456789234");
        driver.findElement(By.cssSelector("input#subject")).sendKeys("The Old Farmhouse, Shady Street, Newfordburyshire, NE1 410S");
        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("Hello! I and my family, we want to book your house.");
        driver.findElement(By.xpath("//button[@id='submitContact']")).click();
        Thread.sleep(500);

        String heading = driver.findElement(By.xpath("//h2[contains(text(),'Thanks for getting in touch')]")).getText();
        driver.quit();

        assertEquals(heading, "Thanks for getting in touch Vasiliy!");
    }

    @Test
    public void testSelenium() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Web form");

        WebElement textBox = driver.findElement(By.xpath("//*[@name = 'my-textarea']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        Thread.sleep(1000);

        textBox.sendKeys("Привет, я автотест");
        submitButton.click();

        Thread.sleep(1500);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        Assert.assertEquals(value, "Received!");

        driver.quit();
    }

    @Test
    public void testErartaSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.erarta.com");

        Thread.sleep(4000);

        WebElement header1 = driver.findElement(By.xpath("//h1[text()='проведите незабываемый день']"));

        WebElement search = driver.findElement(By.cssSelector("svg.header__search-svg"));
        search.click();

        WebElement searchInput = driver.findElement(By.cssSelector("input.search-popup__input"));
        searchInput.sendKeys("весна");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.cssSelector("button.search-popup__submit"));
        searchButton.click();

        Thread.sleep(1000);

        WebElement searchResult = driver.findElement(By.cssSelector("a.search-page__result-title"));
        String resultText = searchResult.getText();

        Assert.assertTrue(resultText.contains("весн") || resultText.contains("весен"));

        driver.quit();
    }

}
