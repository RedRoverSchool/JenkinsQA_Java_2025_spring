package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

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
    public void testFieldInput() throws InterruptedException {
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
    }

        @Test
        public void testSlider() {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            WebElement webForm = driver.findElement(By.xpath("//a[text() = 'Web form']"));
            webForm.click();

            WebElement rangeInput = driver.findElement(By.xpath("//input[@type='range']"));
            String initialValue = rangeInput.getAttribute("value");

            // Получаем минимальное и максимальное значение слайдера
            int minValue = Integer.parseInt(rangeInput.getAttribute("min"));
            int maxValue = Integer.parseInt(rangeInput.getAttribute("max"));

            // Вычисляем смещение для начальной позиции
            int sliderWidth = rangeInput.getSize().getWidth();
            int targetValue = minValue; // Начальная позиция
            int offset = (int) ((double) (targetValue - minValue) / (maxValue - minValue) * sliderWidth);

            // Перетаскиваем слайдер в начальную позицию
            Actions actions = new Actions(driver);
            actions.clickAndHold(rangeInput)
                    .moveByOffset(-offset, 0)
                    .release()
                    .perform();

            // Получаем значение слайдера после перетаскивания
            String finalValue = rangeInput.getAttribute("value");

            // Проверяем, что слайдер вернулся в начальную позицию
            assertEquals(finalValue, "5", "Слайдер не вернулся в начальную позицию");
        }

        @Test
                public void dataPucker () throws InterruptedException {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            WebElement webForm = driver.findElement(By.xpath("//a[text() = 'Web form']"));
            webForm.click();
        //Date picker
        WebElement dataPicker = driver.findElement(By.name("my-date"));
        dataPicker.click();
        Thread.sleep(1000);
        WebElement selectDate = driver.findElement(By.xpath("//td[text() = '15']"));
        selectDate.click();
        String data = dataPicker.getDomProperty("value");
        String selectDateText = "03/15/2025";
        assertEquals(data, selectDateText);
        driver.quit();
    }
    //Alert
    @Test
    public void testAlert() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        WebElement dialogBoxes = driver.findElement(By.xpath("//a[text() = 'Dialog boxes']"));
        dialogBoxes.click();
        WebElement dialogTitle = driver.findElement(By.className("display-6"));
        String dialogTitleText = dialogTitle.getText();
        assertEquals(dialogTitleText, "Dialog boxes");
        WebElement launchAlert = driver.findElement(By.id("my-alert"));
        launchAlert.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        System.out.println("Текст алерта: " + alertText);
        alert.accept();
        driver.quit();
    }
    //Launch confirm
    @Test
    public void testLaunchConfirm(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement dialogBoxes = driver.findElement(By.xpath("//a[text() = 'Dialog boxes']"));
        dialogBoxes.click();
        WebElement dialogTitle = driver.findElement(By.className("display-6"));
        String dialogTitleText = dialogTitle.getText();
        assertEquals(dialogTitleText, "Dialog boxes");
        WebElement launchAlert = driver.findElement(By.id("my-confirm"));
        launchAlert.click();
        // Ожидаем появления алерта
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        // Выбор действия: нажать "OK" или "Отмена"
        String action = "OK"; // Можете изменить на "Отмена" для другого действия
        if (action.equals("OK")) {
            // Нажимаем "OK"
            alert.accept();
            System.out.println("Нажата кнопка 'OK'");
        } else {
            // Нажимаем "Отмена"
            alert.dismiss();
            System.out.println("Нажата кнопка 'Отмена'");
        }
        driver.quit();
    }

    @Test
    public void testSearchByTextLamoda() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.lamoda.ru/");
        String titleWebBrowser = driver.getTitle();
        assertEquals(titleWebBrowser, "Интернет магазин одежды и обуви. Купить обувь, купить одежду, аксессуары в онлайн магазине Lamoda.ru");
        WebElement search = driver.findElement(By.className("_input_mh0i8_19"));
        search.click();
        search.sendKeys("куртка");
        search.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement result = driver.findElement(By.className("_titleText_1s7by_15"));
        String resultText = result.getText();
        assertEquals(resultText, "Товары по запросу «куртка»");
        driver.quit();
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
    @Test
    public void testSeleniumForm() throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textBox = driver.findElement(By.id("my-text-id"));
        WebElement textPassword = driver.findElement ( By.xpath ( "//input[@name='my-password']" ) );
        WebElement submitButton = driver.findElement(By.tagName("button"));

        textBox.sendKeys("Selenium");
        textPassword.sendKeys ( "555" );
        submitButton.click();

        Thread.sleep(1000);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();

        assertEquals(value, "Received!");

        driver.quit();
    }

    @AfterSuite
    public void teardown() {
        driver.quit();
    }
}

