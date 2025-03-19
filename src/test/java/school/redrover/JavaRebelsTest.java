package school.redrover;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import static org.testng.Assert.*;
public class JavaRebelsTest {

    WebDriver driver;
    String baseUrl = "http://tech-avito-intern.jumpingcrab.com/advertisements/";
    String title = "Avito";

    @BeforeMethod
    void setup() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterMethod
    void teardown() {
        driver.quit();
    }

    @Test
    public void checkTitle() throws InterruptedException {

        assertEquals(driver.getTitle(), title);
    }

    @Test
    public void createNewForm() throws InterruptedException {
        Thread.sleep(3000);

        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/button[3]"));
        createButton.click();
        Thread.sleep(3000);

        WebElement createFormById = driver.findElement(By.xpath("/html/body/div[3]/div[3]/div"));
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(d -> createFormById.isDisplayed());

        WebElement nameField = driver.findElement(By.name("name"));
        WebElement priceField = driver.findElement(By.name("price"));
        WebElement descriptionField = driver.findElement(By.name("description"));
        WebElement imageUrlField = driver.findElement(By.name("imageUrl"));

        Faker faker = new Faker();
        String fakerName = faker.name().name();

        nameField.sendKeys(fakerName);
        priceField.sendKeys("1000");
        descriptionField.sendKeys("Test");
        imageUrlField.sendKeys("https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png");
        Thread.sleep(2000);
        WebElement submitButton = driver.findElement(By.xpath("//button[text()=\"Сохранить\"]"));
        submitButton.click();

        driver.get(baseUrl);

        WebElement search = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div/input"));
        search.sendKeys(fakerName);
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div/button"));
        searchButton.click();
        Thread.sleep(5000);

        WebElement item = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div/a/div/div/div/h4"));
        assertEquals(item.getText(), fakerName);
    }
}