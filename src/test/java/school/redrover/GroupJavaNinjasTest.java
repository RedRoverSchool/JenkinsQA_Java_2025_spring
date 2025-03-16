package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class GroupJavaNinjasTest {

    WebDriver driver;

    @BeforeSuite
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void test() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement webForm = driver.findElement(By.xpath("//a[text() = 'Web form']"));
        webForm.click();
        String titleWebBrowser = driver.getTitle();
        assertEquals(titleWebBrowser, "Hands-On Selenium WebDriver with Java");
        WebElement title = driver.findElement(By.xpath("//h1[@class='display-6']"));
        String titleText = title.getText();
        assertEquals(titleText, "Web form");
        WebElement textInput = driver.findElement(By.name("my-text"));
        textInput.sendKeys("Vasya");
        WebElement password = driver.findElement(By.name("my-password"));
        password.sendKeys("123456");
        WebElement textArea = driver.findElement(By.name("my-textarea"));
        textArea.sendKeys("Hello");
        // элемент недоступный для редактирования
        WebElement disabledInput = driver.findElement(By.name("my-disabled"));
        assertFalse(disabledInput.isEnabled());
        //элемент readonly
        WebElement readonlyInput = driver.findElement(By.name("my-readonly"));
        String readonlyText = readonlyInput.getDomAttribute("readonly");
        assertEquals(readonlyText, "true");
        //перетаскиваем слайдер в начальную позицию
        WebElement rangeInput = driver.findElement(By.xpath("//input[@type='range']"));
        int initialX = rangeInput.getLocation().getX();
        Actions actions = new Actions(driver);
        actions.clickAndHold(rangeInput)
                .moveByOffset(-initialX, 0)
                .release()
                .perform();
        //выбор цвета
        WebElement colorInput = driver.findElement(By.name("my-colors"));
        String colorText = colorInput.getDomAttribute("value");
        colorInput.click();

        //Date picker
        WebElement dataInput = driver.findElement(By.name("my-date"));
        dataInput.click();
        Thread.sleep(1000);
        WebElement selectDate = driver.findElement(By.xpath("//td[text() = '15']"));
        selectDate.click();
        String data = dataInput.getDomProperty("value");
        String selectDateText = "03/15/2025";
        assertEquals(data, selectDateText);
    }

    @Test
    public void testSearchLamoda() throws InterruptedException {
        driver.get("https://www.lamoda.ru/");
        String titleWebBrowser = driver.getTitle();
        assertEquals(titleWebBrowser, "Интернет магазин одежды и обуви. Купить обувь, купить одежду, аксессуары в онлайн магазине Lamoda.ru");
        WebElement search = driver.findElement(By.className("_input_mh0i8_19"));
        search.click();
        search.sendKeys("куртка");
        search.sendKeys(Keys.ENTER);
        Thread.sleep(10000);
        WebElement result = driver.findElement(By.className("_titleText_1s7by_15"));
        String resultText = result.getText();
        assertEquals(resultText, "Товары по запросу «куртка»");
    }

    @Test
    public void testShoesLamoda() throws InterruptedException {
        driver.get("https://www.lamoda.ru/");
        Thread.sleep(1000);
        WebElement shoes = driver.findElement(By.xpath("//a[@href='/c/15/shoes-women/?sitelink=topmenuW&l=4']"));
        shoes.click();
        Thread.sleep(3000);
    }

    @Test
    public void testSeleniumWD() throws InterruptedException {
        driver.get("https://www.selenium.dev/");
        Actions actions = new Actions(driver);
        WebElement blogLink = driver.findElement(By.xpath("//main//a[@href='/blog']"));

        assertEquals(blogLink.getText(), "MORE NEWS");
        actions.scrollToElement(blogLink).perform();
        assertTrue(blogLink.isDisplayed());
        blogLink.click();
        Thread.sleep(1000);

        String blogPageTitle = driver.getTitle();
        assertEquals(blogPageTitle, "Blog | Selenium");

        WebElement searchButton = driver.findElement(By.xpath("//header//button[@aria-label='Search']"));
        searchButton.click();
        Thread.sleep(1000);

        WebElement searchInput = driver.findElement(By.xpath("//input"));
        searchInput.sendKeys("Actions");
        searchInput.submit();
        Thread.sleep(1000);

        WebElement searchResultLink = driver.findElement(By.xpath("//ul/li//span[contains(text(),'Keyboard')]"));
        searchResultLink.click();

        WebElement headingText = driver.findElement(By.xpath("//h1"));
        assertEquals(headingText.getText(), "Keyboard actions");

    }

    @AfterSuite
    public void teardown() {
        driver.quit();
    }
}

