package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SeleniumFormTest {
    By textBox = By.id("my-text-id");
    By passwordField = By.name("my-password");
    By textArea = By.name("my-textarea");
    By dropdownSelect = By.name("my-select");
    By selectOptionOne = By.xpath("//option[text()='One']");
    By dropdownDataList = By.name("my-datalist");
    By defaultCheckBox = By.id("my-check-2");
    By defaultRadio = By.id("my-radio-2");
    By datePicker = By.name("my-date");
    By date = By.xpath("//td[text()='17']");
    By submitButton = By.xpath("//button[text()='Submit']");
    By conformationPage = By.xpath("//h1[text()='Form submitted']");

    @Test
    public void testSelenium() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.findElement(textBox).sendKeys("Olga Sachkova");
        driver.findElement(passwordField).sendKeys("123456");
        driver.findElement(textArea).sendKeys("9441 Live Oak Pl");
        driver.findElement(dropdownSelect).click();
        driver.findElement(selectOptionOne).click();
        driver.findElement(dropdownDataList).sendKeys("Davie");
        driver.findElement(defaultCheckBox).click();
        driver.findElement(defaultRadio).click();
        driver.findElement(datePicker).click();
        driver.findElement(date).click();
        driver.findElement(submitButton).click();

        assertTrue(driver.findElement(conformationPage).isDisplayed(),"Verifying if the confirmation page is displayed");

        driver.quit();
    }
}
