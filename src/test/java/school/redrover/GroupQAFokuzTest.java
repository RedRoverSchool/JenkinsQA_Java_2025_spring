package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;


public class GroupQAFokuzTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testWebForm() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement textInput = driver.findElement(By.xpath("//*[@id='my-text-id']"));
        textInput.sendKeys("Denis N");
        Assert.assertEquals(textInput.getAttribute("value"),
                "Denis N", "Текстовое поле содержит неверное значение");

        WebElement password = driver.findElement(By.xpath("//*[@name='my-password']"));
        password.sendKeys("123456");
        Assert.assertEquals(password.getAttribute("value"),
                "123456", "Поле пароля содержит неверное значение");

        WebElement textArea = driver.findElement(By.xpath("//*[@name='my-textarea']"));
        textArea.sendKeys("This is a text");
        Assert.assertEquals(textArea.getAttribute("value"),
                "This is a text", "Текстовая область содержит неверное значение");

        WebElement radioButton1 = driver.findElement(By.xpath("//*[@id='my-radio-2']"));
        radioButton1.click();
        Assert.assertTrue(radioButton1.isSelected(), "Радио-кнопка не выбрана");

        WebElement btnSubmit = driver.findElement(By.xpath("//*[@class='btn btn-outline-primary mt-3']"));
        btnSubmit.click();

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("submitted"), "Форма не была отправлена");
    }

    @Test
    public void btnClick() {
        driver.get("https://demoqa.com/buttons");

        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement DoubleClickMe = driver.findElement(By.xpath("//*[@id='doubleClickBtn']"));
        actions.doubleClick(DoubleClickMe).perform();

        WebElement rightClickMe = driver.findElement(By.xpath("//*[@id='rightClickBtn']"));
        actions.contextClick(rightClickMe).perform();

        WebElement clickMe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Click Me']")));
        actions.click(clickMe).perform();

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='doubleClickMessage']"))
                .isDisplayed(), "Кнопка была не нажата");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='rightClickMessage']"))
                .isDisplayed(), "Кнопка была не правым кликом");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='dynamicClickMessage']"))
                .isDisplayed(), "Кнопка была не нажата");
    }

    @Test
    public void radioButton() {
        driver.get("https://demoqa.com/radio-button");

        Actions actions = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement btnYes = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='yesRadio']"))
        );
        actions.click(btnYes).perform();

        WebElement btnImpressive = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='impressiveRadio']"))
        );
        actions.click(btnImpressive).perform();

        WebElement btnNo = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='noRadio']"))
        );
        actions.click(btnNo).perform();


        String textYes = btnYes.getText();
        Assert.assertEquals(textYes, "Yes", "Текст элемента не совпадает с 'Yes'");
        String textImp = btnImpressive.getText();
        Assert.assertEquals(textImp, "Impressive", "Текст элемента не совпадает с 'Impressive'");
        String textNo = btnNo.getText();
        Assert.assertEquals(textNo, "No", "Текст элемента не совпадает с 'No'");
    }


    @Test
    public void testFormPractice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demoqa.com/automation-practice-form");

        WebElement firstNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));
        firstNameInput.sendKeys("Denis");

        WebElement lastNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("lastName")));
        lastNameInput.sendKeys("Novicov");

        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userEmail")));
        emailInput.sendKeys("denisnovicov@example.com");

        WebElement phoneInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userNumber")));
        phoneInput.sendKeys("7999999999");

        WebElement genderRadio = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Male')]")
        ));
        genderRadio.click();

        WebElement subjectInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("subjectsInput")));
        subjectInput.sendKeys("English");
        subjectInput.sendKeys(Keys.ENTER);

        subjectInput.sendKeys("Commerce");
        subjectInput.sendKeys(Keys.ENTER);

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement sportsCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[@for='hobbies-checkbox-1']")
        ));
        sportsCheckbox.click();

        WebElement readingCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Reading')]")
        ));
        readingCheckbox.click();

        WebElement musicCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Music')]")
        ));
        musicCheckbox.click();

        WebElement addressInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("currentAddress")));
        addressInput.sendKeys("Prague, Vodickova 123/34");

        WebElement stateDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='state']/div/div[2]")));
        stateDropdown.click();

        WebElement stateOption = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[text()='NCR']")
        ));
        stateOption.click();

        WebElement cityDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("city")));
        cityDropdown.click();

        WebElement cityOption = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[text()='Delhi']")
        ));
        cityOption.click();

        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submit")));
        submitButton.click();

        WebElement modalTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='modal-title h4' and text()='Thanks for submitting the form']")
        ));
        Assert.assertTrue(modalTitle.isDisplayed(), "Модальное окно не отображается");

        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']")
        ));
        Assert.assertTrue(table.isDisplayed(), "Таблица не отображается");

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Student Name']/following-sibling::td")
                )).getText(),
                "Denis Novicov",
                "Неверное имя студента"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Student Email']/following-sibling::td")
                )).getText(),
                "denisnovicov@example.com",
                "Неверный email"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Gender']/following-sibling::td")
                )).getText(),
                "Male",
                "Неверный пол"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Mobile']/following-sibling::td")
                )).getText(),
                "7999999999",
                "Неверный номер телефона"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Date of Birth']/following-sibling::td")
                )).getText(),
                "15 March,2025",
                "Неверная дата рождения"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Subjects']/following-sibling::td")
                )).getText(),
                "English, Commerce",
                "Неверные предметы"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Hobbies']/following-sibling::td")
                )).getText(),
                "Sports, Reading, Music",
                "Неверные хобби"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='Address']/following-sibling::td")
                )).getText(),
                "Prague, Vodickova 123/34",
                "Неверный адрес"
        );

        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//td[text()='State and City']/following-sibling::td")
                )).getText(),
                "NCR Delhi",
                "Неверный штат и город"
        );
        WebElement closeButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@id='closeLargeModal']")
        ));
        closeButton.click();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
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