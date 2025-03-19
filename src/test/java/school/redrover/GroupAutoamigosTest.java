package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupAutoamigosTest {
    private WebDriver driver;

    @BeforeMethod
      public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
    }

    @Test
    public void testWebFormLink() throws InterruptedException {
        checkLink("Web form");
    }

    @Test
    public void testNavigationLink() throws InterruptedException {
        checkLink("Navigation");
    }

    @Test
    public void testDropdownMenuLink() throws InterruptedException {
        checkLink("Dropdown menu");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // Метод для клика по ссылке и проверки, что страница открылась
    private void checkLink(String linkText) throws InterruptedException {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();

        Thread.sleep(2000);

        Assert.assertNotEquals(driver.getTitle() + "", "Страница не загрузилась: " + linkText);

        driver.navigate().back();
    }
}
