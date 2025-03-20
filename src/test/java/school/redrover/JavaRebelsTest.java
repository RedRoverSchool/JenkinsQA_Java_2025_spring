package school.redrover;

import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class JavaRebelsTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://tech-avito-intern.jumpingcrab.com/advertisements/";
    private static final String TITLE = "Avito";

    @BeforeMethod
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @AfterMethod
    void teardown() {
        driver.quit();
    }

    @Test
    public void checkTitle() {
        assertEquals(driver.getTitle(), TITLE);
    }

    @Test
    public void createNewForm() {
        openCreateForm();
        String generatedName = fillAndSubmitForm();
        verifyItemInList(generatedName);
    }

    private void openCreateForm() {
        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/button[3]"));
        createButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[3]/div")));
    }

    private String fillAndSubmitForm() {
        Faker faker = new Faker();
        String fakerName = faker.name().name();

        driver.findElement(By.name("name")).sendKeys(fakerName);
        driver.findElement(By.name("price")).sendKeys("1000");
        driver.findElement(By.name("description")).sendKeys("Test");
        driver.findElement(By.name("imageUrl")).sendKeys("https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png");

        WebElement submitButton = driver.findElement(By.xpath("//button[text()=\"Сохранить\"]"));
        submitButton.click();

        return fakerName;
    }

    private void verifyItemInList(String itemName) {
        driver.get(BASE_URL);

        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div/input")));
        searchField.sendKeys(itemName);

        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div/button"));
        searchButton.click();

        WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[text()=\"" + itemName + "\"]")));

        assertEquals(item.getText(), itemName);
    }
}