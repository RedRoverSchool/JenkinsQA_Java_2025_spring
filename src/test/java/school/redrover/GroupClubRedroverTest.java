package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.assertEquals;


public class GroupClubRedroverTest {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeMethod
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);

    }

    @AfterMethod
    void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "pageData")
    public Object[][] providePageData() {
        return new Object[][]{
                {"Chapter 3. WebDriver Fundamentals", "web-form.html", "Web form"},
                {"Chapter 3. WebDriver Fundamentals", "navigation1.html", "Navigation example"},
                {"Chapter 3. WebDriver Fundamentals", "dropdown-menu.html", "Dropdown menu"},
                {"Chapter 3. WebDriver Fundamentals", "mouse-over.html", "Mouse over"},
                {"Chapter 3. WebDriver Fundamentals", "drag-and-drop.html", "Drag and drop"},
                {"Chapter 3. WebDriver Fundamentals", "loading-images.html", "Loading images"},
                {"Chapter 3. WebDriver Fundamentals", "slow-calculator.html", "Slow calculator"},
                {"Chapter 4. Browser-Agnostic Features", "long-page.html", "This is a long page"},
                {"Chapter 4. Browser-Agnostic Features", "infinite-scroll.html", "Infinite scroll"},
                {"Chapter 4. Browser-Agnostic Features", "shadow-dom.html", "Shadow DOM"},
                {"Chapter 4. Browser-Agnostic Features", "iframes.html", "IFrame"},
                {"Chapter 4. Browser-Agnostic Features", "cookies.html", "Cookies"},
                {"Chapter 4. Browser-Agnostic Features", "dialog-boxes.html", "Dialog boxes"},
                {"Chapter 4. Browser-Agnostic Features", "web-storage.html", "Web storage"},
                {"Chapter 5. Browser-Specific Manipulation", "geolocation.html", "Geolocation"},
                {"Chapter 5. Browser-Specific Manipulation", "notifications.html", "Notifications"},
                {"Chapter 5. Browser-Specific Manipulation", "get-user-media.html", "Get user media"},
                {"Chapter 5. Browser-Specific Manipulation", "multilanguage.html", "Multilanguage page"},
                {"Chapter 5. Browser-Specific Manipulation", "console-logs.html", "Console logs"},
                {"Chapter 7. The Page Object Model (POM)", "login-form.html", "Login form"},
                {"Chapter 7. The Page Object Model (POM)", "login-slow.html", "Slow login form"},
                {"Chapter 8. Testing Framework Specifics", "random-calculator.html", "Random calculator"},
                {"Chapter 9. Third-Party Integrations", "download.html", "Download files"},
                {"Chapter 9. Third-Party Integrations", "ab-testing.html", "A/B Testing"},
                {"Chapter 9. Third-Party Integrations", "data-types.html", "Data types"}
        };
    }

    @Test(dataProvider = "pageData", description = "Verify the functionality of all links on HomePage")
    void verifyHomePageLinks(String chapterName, String path, String title) {
        driver.get(BASE_URL);
        driver.findElement(By.xpath("//h5[text() = '" + chapterName + "']/../a[@href = '" + path + "']")).click();

        String actualUrl = driver.getCurrentUrl();
        String actualTitle = driver.findElement(By.className("display-6")).getText();
        assertEquals(BASE_URL + path, actualUrl, "The URLs don't match");
        assertEquals(title, actualTitle, "The titles don't match");
    }

    @Test(description = "Verify the functionality of all links on HomePage another way")
    void verifyHomePageLinksAnotherWay() {
        driver.get(BASE_URL);
        List<WebElement> chapters = driver.findElements(By.cssSelector(".card h5"));
        assertEquals(chapters.size(),6);

        List<WebElement> links = driver.findElements(By.cssSelector(".card a"));
        for (WebElement link : links) {
            link.click();
            driver.navigate().back();
        }
        assertEquals(links.size(),27);
    }
}
