package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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


    @Test
    public void testFormsSection() throws InterruptedException {

        //Forms - Practice Form section
        clickElement(By.xpath("//div[@id='app']//div[@class='category-cards']/div[2]"));
        clickElement(By.xpath("//span[normalize-space()='Practice Form']"));
        //Name
        fillTextInput(By.xpath("//input[@id='firstName']"), "John");
        //Surname
        fillTextInput(By.xpath("//input[@id='lastName']"), "Doe");
        //Email
        fillTextInput(By.xpath("//input[@id='userEmail']"), "johndoe@example.com");
        //Gender
        clickElement(By.cssSelector("label[for='gender-radio-1']"));
        //Mobile
        fillTextInput(By.xpath("//input[@id='userNumber']"), "1234567890");
        //Scroll down
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500);");
        //Date of birth picker
        clickElement(By.xpath("//input[@id='dateOfBirthInput']"));
        WebElement monthDropdown = driver.findElement(By.className("react-datepicker__month-select"));
        Select selectMonth = new Select(monthDropdown);
        selectMonth.selectByValue("1");
        WebElement yearDropdown = driver.findElement(By.className("react-datepicker__year-select"));
        Select selectYear = new Select(yearDropdown);
        selectYear.selectByValue("1987");
        Thread.sleep(1000);
        WebElement selectDay = driver.findElement(By.cssSelector(".react-datepicker__day--005"));
        selectDay.click();
        //Subjects
        fillTextInput(By.xpath("//input[@id='subjectsInput']"), "Maths");
        driver.findElement(By.xpath("//input[@id='subjectsInput']")).sendKeys(Keys.ENTER);
        //Hobbies
        clickElement(By.xpath("id(\"hobbiesWrapper\")/div[2]/div[2]/label[1]"));
        //Current Address
        fillTextInput(By.xpath("//textarea[@id='currentAddress']"), "814, Boulevard of broken dreams");
        //State and City
        clickElement(By.xpath("id(\"state\")/div[1]/div[1]/div[1]"));
        clickElement(By.xpath("//div[@id='state']//div[@class=' css-26l3qy-menu']//div[normalize-space()='NCR']"));
        //Select city
        clickElement(By.xpath("id(\"stateCity-wrapper\")/div[3]"));
        clickElement(By.xpath("//div[@id='city']//div[@class=' css-26l3qy-menu']//div[normalize-space()='Delhi']"));
        //Submit form
        clickElement(By.id("submit"));

        //Form Assertions
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.id("example-modal-sizes-title-lg")).getText(), "Thanks for submitting the form");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Student Name']]/td[2]")).getText(), "John Doe");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Student Email']]/td[2]")).getText(), "johndoe@example.com");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Gender']]/td[2]")).getText(), "Male");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Mobile']]/td[2]")).getText(), "1234567890");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Date of Birth']]/td[2]")).getText(), "05 February,1987");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Subjects']]/td[2]")).getText(), "Maths");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Hobbies']]/td[2]")).getText(), "Reading");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='Address']]/td[2]")).getText(), "814, Boulevard of broken dreams");
        Assert.assertEquals(driver.findElement(By.xpath("//tr[td[text()='State and City']]/td[2]")).getText(), "NCR Delhi");
        clickElement(By.id("closeLargeModal"));
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

    @Test
    public void testSubscribe() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://i-store.by/");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight / 2);");

        WebElement subButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-submit")));
        subButton.click();

        WebElement error = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".error.ng-star-inserted")));
        wait.until(ExpectedConditions.textToBePresentInElement(error, "Поле обязательно для заполнения"));
        String errorText = error.getText().trim();

        Assert.assertEquals(errorText, "Поле обязательно для заполнения");
        driver.quit();
    }


}
