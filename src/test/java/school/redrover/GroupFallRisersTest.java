package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupFallRisersTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        action = new Actions(driver);
        driver.get("https://demoqa.com/");
    }

    @Test
    public void testCheck() {

        assertEquals(driver.getTitle(), "DEMOQA", "Title is not correct");

        clickElement(By.xpath("//div[@id='app']//div[@class='category-cards']/div[1]"));

        //text box section
        clickElement(By.xpath("//span[normalize-space()='Text Box']"));
        fillTextInput(By.xpath("//input[@id='userName']"), "John Doe");
        fillTextInput(By.xpath("//input[@id='userEmail']"), "email@example.com");
        fillTextInput(By.xpath("//textarea[@id='currentAddress']"), "3993 Boulevard Saint-Laurent");
        fillTextInput(By.xpath("//textarea[@id='permanentAddress']"), "Am Kanalschuppen 6");
        scrollToElement(By.xpath("(//button[normalize-space()='Submit'])[1]"));
        clickElement(By.xpath("(//button[normalize-space()='Submit'])[1]"));
        assertEquals( driver.findElement(By.xpath("//div[@id='output']")).getText(),
                """
                        Name:John Doe
                        Email:email@example.com
                        Current Address :3993 Boulevard Saint-Laurent\
                        
                        Permananet Address :Am Kanalschuppen 6""", "Text is not correct");

        //checkbox section
        clickElement(By.xpath("//span[normalize-space()='Check Box']"));
        clickElement(By.xpath("//button[@title='Toggle']//*[name()='svg']"));
        clickElement(By.xpath("(//button[@title='Toggle'])[2]"));
        clickElement(By.xpath(".//label[@for='tree-node-home']//span[@class='rct-checkbox']//*[name()='svg']"));
        String actualText = driver.findElement(By.id("result")).getText()
                .replace("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
        String expectedText = "You have selected : home desktop notes commands documents workspace react angular veu" +
                " office public private classified general downloads wordFile excelFile";
        assertEquals(actualText, expectedText, "Text is not correct");

        //radio button section
        clickElement(By.xpath("//span[normalize-space()='Radio Button']"));
        clickElement(By.xpath("//label[@for='yesRadio']"));
        clickElement(By.xpath("//label[@for='impressiveRadio']"));
        clickElement(By.xpath("//label[@for='noRadio']"));
        assertEquals(driver.findElement(By.xpath("//span[@class='text-success']")).getText(),
                "Impressive", "Text is not correct");

        //web table section
        clickElement(By.xpath("//span[normalize-space()='Web Tables']"));
        clickElement(By.xpath("//button[@id='addNewRecordButton']"));
        fillTextInput(By.xpath("//input[@id='firstName']"), "John");
        fillTextInput(By.xpath("//input[@id='lastName']"), "Doe");
        fillTextInput(By.xpath("//input[@id='userEmail']"), "email@example.com");
        fillTextInput(By.xpath("//input[@id='age']"), "25");
        fillTextInput(By.xpath("//input[@id='salary']"), "50000");
        fillTextInput(By.xpath("//input[@id='department']"), "IT");
        clickElement(By.xpath("//button[@id='submit']"));

        //buttons section
        clickElement(By.xpath("//span[normalize-space()='Buttons']"));
        doubleClick(By.xpath("//button[@id='doubleClickBtn']"));
        rightClick(By.xpath("//button[@id='rightClickBtn']"));
        clickElement(By.xpath("(//button[normalize-space()='Click Me'])[1]"));
        assertEquals(driver.findElement(By.id("doubleClickMessage")).getText(),
                "You have done a double click", "Text is not correct");
        assertEquals(driver.findElement(By.id("rightClickMessage")).getText(),
                "You have done a right click", "Text is not correct");
        assertEquals(driver.findElement(By.id("dynamicClickMessage")).getText(),
                "You have done a dynamic click", "Text is not correct");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        System.out.println("Click on element: " + locator);
    }

    private void fillTextInput(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        System.out.println("Fill text input: " + locator);
    }

    private void doubleClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.click();
        action.doubleClick(element).perform();
        System.out.println("Double-click on element: " + locator);
    }

    private void rightClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        action.contextClick(element).perform();
        System.out.println("Right-click on element: " + locator);
    }

    private void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
