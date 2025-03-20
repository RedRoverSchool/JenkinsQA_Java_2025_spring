package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SunFlowerPage {

    private final WebDriver driver;

    public SunFlowerPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement textInput() { return driver.findElement(By.cssSelector("input[name='my-text']")); }
    public WebElement passwordInput() { return driver.findElement(By.cssSelector("input[name='my-password']")); }
    public WebElement textArea() { return driver.findElement(By.cssSelector("textarea[name='my-textarea']")); }
    public WebElement disabledInput() { return driver.findElement(By.cssSelector("input[name='my-disabled']")); }
    public WebElement readOnlyInput() { return driver.findElement(By.cssSelector("input[name='my-readonly']")); }
    public WebElement submitButton() { return driver.findElement(By.cssSelector("button[type='submit']")); }
    public List<WebElement> radioButtons() { return driver.findElements(By.cssSelector("input[type='radio'][name='my-radio']")); }
    public List<WebElement> checkboxes() { return driver.findElements(By.cssSelector("input[type='checkbox'][name='my-check']")); }
    public WebElement colorDropdown() { return driver.findElement(By.cssSelector("input[name='my-colors']")); }
    public WebElement fileInput() { return driver.findElement(By.cssSelector("input[type='file'][name='my-file']")); }
}