package school.redrover;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.fenum.qual.SwingElementOrientation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;

import static org.testng.Assert.assertTrue;

public class EkaterinaSeleniumTest {

    private final String BASE_URL = "https://www.selenium.dev/selenium/web/web-form.html";

    By textField = By.id("my-text-id");
    By passwordField = By.name("my-password");
    By textAreaField = By.name("my-textarea");
    By submitButton = By.xpath("//button[text()='Submit']");
    By submittedForm = By.xpath("//h1[text()='Form submitted']");
    Faker faker = new Faker();
    String username = faker.name().username();
    String password = faker.internet().password();
    String randomText = faker.lorem().sentence();


    @Test
    public void seleniumWebFormTest() {
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);

        driver.findElement(textField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(textAreaField).sendKeys(randomText);
        driver.findElement(submitButton).click();

        assertTrue(driver.findElement(submittedForm).isDisplayed(),"Form submitted");

        driver.quit();
    }
}
