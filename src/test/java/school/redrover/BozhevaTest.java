package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class BozhevaTest {

    private WebDriver driver;
    private static final String URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(URL);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testTextInput() {
        WebElement textField = driver.findElement(By.name("my-text"));
        textField.sendKeys("Test Input");
        Assert.assertEquals(textField.getAttribute("value"), "Test Input", "Поле 'my-text' содержит неверное значение!");
    }

    @Test
    public void testPasswordField() {
        WebElement passField = driver.findElement(By.name("my-password"));
        passField.sendKeys("pass123");
        Assert.assertEquals(passField.getAttribute("value"), "pass123", "Неверное значение");
    }

    @Test
    public void testTextareaField() {
        WebElement textareaField = driver.findElement(By.name("my-textarea"));
        textareaField.sendKeys("Привет всем");
        Assert.assertEquals(textareaField.getAttribute("value"), "Привет всем", "Неверное значение");
    }

    @Test
    public void testDisabledInput() {
        WebElement disabledInput = driver.findElement(By.name("my-disabled"));
        Assert.assertFalse(disabledInput.isEnabled(), "Поле не заблокировано, должно быть!");
    }

    @Test
    public void testReadonlyInput() {
        WebElement readonlyInput = driver.findElement(By.name("my-readonly"));
        Assert.assertNotNull(readonlyInput.getAttribute("readonly"), "Поле должно быть readonly!");
    }

    @Test
    public void testReturnLink() throws InterruptedException {
        WebElement returnLink = driver.findElement(By.xpath("//a[contains(text(), 'Return to index')]"));
        returnLink.click();
        Thread.sleep(3000);
        driver.navigate().back();
        Assert.assertNotEquals(driver.getTitle(), "Страница не загрузилась");
    }

    @Test
    public void testSelectOption() {
        Select select = new Select(driver.findElement(By.name("my-select")));
        select.selectByVisibleText("Two");
        String selectedText = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedText, "Two");
    }

    @Test
    public void testDataList() {
        WebElement dataList = driver.findElement(By.cssSelector("input[list='my-options']"));
        dataList.sendKeys("San Francisco");
        Assert.assertEquals(dataList.getAttribute("value"), "San Francisco", "Значение в поле ввода неверное!");
    }

    @Test
    public void testCheckbox() {
        WebElement checkbox = driver.findElement(By.xpath("//input[@id='my-check-1']"));
        checkbox.click();
        Assert.assertFalse(checkbox.isSelected(), "Чекбокс не должен быть выбран");
    }

    @Test
    public void testRadioButton() {
        WebElement radioButton = driver.findElement(By.xpath("//input[@id='my-radio-1']"));
        radioButton.click();
        Assert.assertTrue(radioButton.isSelected(), "Радиокнопка не выбрана");
    }

    @Test
    public void testDateInput() {
        WebElement dateInput = driver.findElement(By.cssSelector("input[name='my-date']"));
        dateInput.sendKeys("17/03/2025");
        Assert.assertEquals("17/03/2025", dateInput.getAttribute("value"));
    }

    @Test
    public void testSlider() {
        WebElement slider = driver.findElement(By.name("my-range"));
        String initialValue = slider.getAttribute("value");
        Actions actions = new Actions(driver);
        actions.clickAndHold(slider).moveByOffset(50, 0).release().perform();
        String newValue = slider.getAttribute("value");

        System.out.println("Начальное значение: " + initialValue);
        System.out.println("Новое значение: " + newValue);
        Assert.assertNotEquals("Слайдер не перемещен как нужно", initialValue, newValue);
    }

    @Test
    public void testSubmitButton() {
        driver.findElement(By.tagName("button")).click();
        WebElement element = driver.findElement(By.cssSelector("h1.display-6"));
        Assert.assertEquals(element.getText(), "Form submitted", "Текст 'Form submitted' не найден!");

        WebElement receivedMessage = driver.findElement(By.cssSelector("div.col-12.py-2"));
        Assert.assertTrue(receivedMessage.isDisplayed(), "Текст 'Received!' не найден!");
    }
}