package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GroupQAFokuzTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFalseLoginDatartCz() throws InterruptedException {
        driver.get("https://www.datart.cz/");
        driver.findElement(By.id("c-p-bn")).click();

        driver.findElement(By.xpath("//*[@id='head-login-sign-up']")).click();
        driver.findElement(By.xpath("//*[@id='frm-login']")).sendKeys("test@example.com");
        driver.findElement(By.xpath("//*[@id='frm-password']")).sendKeys("test123");
        driver.findElement(By.xpath("//button[@class='btn btn-login']")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='errorMessage mb-3']"))
                .getText(),"Zadali jste špatné údaje. Zkuste to znovu.","Вы не вошли");
    }

    @Test
    public void testWebForm() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        driver.findElement(By.xpath("//*[@id='my-text-id']")).sendKeys("Denis N");
        driver.findElement(By.xpath("//*[@name='my-password']")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@name='my-textarea']")).sendKeys("This is a text");
        driver.findElement(By.xpath("//*[@id='my-radio-2']")).click();
        driver.findElement(By.xpath("//*[@class='btn btn-outline-primary mt-3']")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl);
        Assert.assertTrue(currentUrl.contains("submitted"), "Форма не была отправлена");
    }

    @Test
    public void testButtonClick() {
        driver.get("https://demoqa.com/buttons");

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        actions.doubleClick(driver.findElement(By.xpath("//*[@id='doubleClickBtn']")))
                .contextClick(driver.findElement(By.xpath("//*[@id='rightClickBtn']")))
                .click(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Click Me']"))))
        .perform();

        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id='doubleClickMessage']")).isDisplayed(),
                "Кнопка была не нажата");
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id='rightClickMessage']")).isDisplayed(),
                "Кнопка была не правым кликом");
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[@id='dynamicClickMessage']")).isDisplayed(),
                "Кнопка была не нажата");
    }

    @Test
    public void radioButton() {
        driver.get("https://demoqa.com/radio-button");

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement buttonYes = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='yesRadio']"))
        );
        actions.click(buttonYes).perform();

        WebElement buttonImpressive = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='impressiveRadio']"))
        );
        actions.click(buttonImpressive).perform();

        WebElement buttonNo = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='noRadio']"))
        );
        actions.click(buttonNo).perform();


        Assert.assertEquals(buttonYes.getText(), "Yes", "Текст элемента не совпадает с 'Yes'");
        Assert.assertEquals(buttonImpressive.getText(), "Impressive", "Текст элемента не совпадает с 'Impressive'");
        Assert.assertEquals(buttonNo.getText(), "No", "Текст элемента не совпадает с 'No'");
    }


    @Test
    public void testFormPractice() {

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demoqa.com/automation-practice-form");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName"))).sendKeys("Denis");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lastName"))).sendKeys("Novicov");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userEmail"))).sendKeys("denisnovicov@example.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userNumber"))).sendKeys("7999999999");

        WebElement dobInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='dateOfBirthInput']")));
        actions.moveToElement(dobInput).click().perform();
        WebElement yearSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='react-datepicker__year-select']")));

        Select yearDropdown = new Select(yearSelect);
        yearDropdown.selectByVisibleText("2000");
        WebElement monthSelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@class='react-datepicker__month-select']")));

        Select monthDropdown = new Select(monthSelect);
        monthDropdown.selectByVisibleText("December");
        WebElement dateElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='react-datepicker__day react-datepicker__day--012']")));
        actions.moveToElement(dateElement).click().perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(text(),'Male')]"))).click();

        WebElement subjectInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='subjectsInput']")));
        subjectInput.sendKeys("English");
        actions.sendKeys(Keys.ENTER).perform();
        subjectInput.sendKeys("Commerce");
        actions.sendKeys(Keys.ENTER).perform();

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[@for='hobbies-checkbox-1']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Reading')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Music')]"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='state']/div/div[2]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='NCR']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Delhi']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("currentAddress"))).sendKeys("Prague, Vodickova 123/34");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='state']/div/div[2]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='NCR']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Delhi']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit"))).click();

        WebElement modalTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='modal-title h4' and text()='Thanks for submitting the form']")));
        Assert.assertTrue(modalTitle.isDisplayed(), "Модальное окно не отображается");

        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']")));

        Assert.assertTrue(table.isDisplayed(), "Таблица не отображается");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td")).getText(),"Denis Novicov","Неверное имя студента");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Student Email']/following-sibling::td")).getText(),"denisnovicov@example.com","Неверный email");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(),"Male","Неверный пол");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td")).getText(),"7999999999","Неверный номер телефона");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Date of Birth']/following-sibling::td")).getText(),"12 December,2000","Неверная дата рождения");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Subjects']/following-sibling::td")).getText(),"English, Commerce","Неверные предметы");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Hobbies']/following-sibling::td")).getText(),"Sports, Reading, Music","Неверные хобби");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),"Prague, Vodickova 123/34","Неверный адрес");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State and City']/following-sibling::td")).getText(),"NCR Delhi","Неверный штат и город");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='closeLargeModal']"))).click();
    }



    @Test
    public void testClickPinterest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://wonderl.ink/@fokuz.photo/");
        Thread.sleep(5000);
        WebElement pinterestButton = driver.findElement(By.linkText("Pinterest"));
        Assert.assertTrue(pinterestButton.isDisplayed(), "Button 'Pinterest' not found on page.");
        pinterestButton.click();
        Thread.sleep(5000);
        Set<String> windowHandles = driver.getWindowHandles(); // Pinterest открывается в новой вкладке
        String originalWindow = driver.getWindowHandle();
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("pinterest"), "The switch did not happen.");
        driver.quit();

    }

    @Test
    public void testAddRemoveElements() throws InterruptedException {
// проверяет, что нажатие на кнопку Add добавляет элемент
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();

        Thread.sleep(1000);

        WebElement addRemoveElementsLink = driver.findElement(By.xpath("//li[2]/a"));
        addRemoveElementsLink.click();

        Thread.sleep(1000);

        WebElement addElementButton = driver.findElement(By.xpath("//div[@class='example']/button"));
        addElementButton.click();

        WebElement deleteButton = driver.findElement(By.xpath("//div[@id='elements']/button"));

        Assert.assertTrue(deleteButton.isDisplayed(), "Элемент не добавлен!");

        driver.quit();

    }

    @Test
    public void testFokuzNavigation() throws InterruptedException {
//  проверяет, что ссылка в навигационной панели хедера работает корректно
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.get("https://fokuz.photo/");
        driver.manage().window().maximize();

        Thread.sleep(1000);

        WebElement daBinIchLink = driver.findElement(By.xpath("//ul[@class='g-toplevel']/li[2]"));
        daBinIchLink.click();

        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://fokuz.photo/da-bin-ich/", "URL не соответствует ожидаемому!");

        driver.quit();

    }


}