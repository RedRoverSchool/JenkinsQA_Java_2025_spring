package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Group_JavaQATest {
    By firstName = By.id("request-pricing_firstName");
    By lastName = By.id("request-pricing_lastName");
    By countryOfResidence = By.xpath("//*[@data-id='request-pricing_country']");
    By countryOfResidenceSelect = By.xpath("//span[text()='United States']");
    By state = By.xpath("//*[@id='request-pricing_state']/following-sibling::button");
    By selectState = By.xpath("//*[@id='request-pricing']//select[@id='request-pricing_state']/parent::div//a[span[text()='Alaska']]");
    By telephoneNumber = By.id("request-pricing_phone");
    By email = By.id("request-pricing_email");
    By voyageOfInterest = By.xpath("//*[@id='request-pricing_voyageOfInterest']/following-sibling::button");
    By voyageOfInterestSelect = By.xpath("//span[contains(text(),'168 nights')]");
    By submitButton = By.xpath("//*[@id='request-pricing']//button[@type='submit']");
    By conformationPage = By.xpath("//div[contains(text(),'Your Personal Consultant')]/ancestor::article//h2");

    By username = By.name("username");
    By password = By.name("password");
    By loginButton = By.xpath("//button[@type='submit']");
    By leaveTab = By.xpath("//span[text()='Leave']");
    By entitlementsTab = By.xpath("//span[contains(text(),'Entitlements')] ");
    By addEntitlements = By.xpath("//a[contains(text(),'Add')] ");
    By employeeName = By.xpath("//input[@placeholder='Type for hints...']");
    By leaveTypeSelect = By.xpath("//div[contains(text(),'Select')]");
    By leaveType = By.xpath("//span[contains(text(),'Matternity')]");
    By leavePeriod = By.xpath("//div[contains(text(),'2025-01-01')]");
    By entitlement = By.xpath("//label[text()='Entitlement']/parent::*/following-sibling::*/input");
    By saveButton = By.xpath("//button[@type='submit']");
    By confirmButton = By.xpath("//button[text()=' Confirm ']");
    By record = By.xpath("//div[contains(@class,'table')]/*[text()='CAN - Matternity']");
    By autocomplete = By.xpath("//input[@placeholder='Type for hints...']/parent::div/following-sibling::div");

    @Test
    public void testRequestQuote () throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa1-aws.rssc.com/request-quote");

        driver.findElement(firstName).sendKeys("Viviana");
        driver.findElement(lastName).sendKeys("Costa");
        driver.findElement(countryOfResidence).click();
        driver.findElement(countryOfResidenceSelect).click();
        Thread.sleep(3000);
        driver.findElement(state).click();
        Thread.sleep(3000);
        driver.findElement(selectState).click();
        driver.findElement(telephoneNumber).sendKeys("7865187654");
        driver.findElement(email).sendKeys("viviana@gmail.com");
        driver.findElement(voyageOfInterest).click();
        driver.findElement(voyageOfInterestSelect).click();
        driver.findElement(submitButton).click();
        Thread.sleep(5000);
        assertTrue(driver.findElement(conformationPage).isDisplayed(),"Thank you page is displayed");

        driver.quit();

    }

    @Test
    public void testAddEntitlements () throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        userName.sendKeys("Admin");
        driver.findElement(password).sendKeys("admin123");
        driver.findElement(loginButton).click();

        WebElement leave = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTab));
        leave.click();
        driver.findElement(entitlementsTab).click();
        driver.findElement(addEntitlements).click();
        driver.findElement(employeeName).sendKeys("John Smit");
        Thread.sleep(5000);
        WebElement autocompleteOption = wait.until(ExpectedConditions.visibilityOfElementLocated(autocomplete));
        autocompleteOption.click();
        driver.findElement(leaveTypeSelect).click();
        Thread.sleep(3000);
        WebElement leaveTypeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveType));
        leaveTypeOption.click();
        driver.findElement(leavePeriod).click();
        driver.findElement(entitlement).sendKeys("2.00");
        driver.findElement(saveButton).click();
        Thread.sleep(3000);
        driver.findElement(confirmButton).click();

        WebElement recordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(record));
        assertTrue(recordElement.isDisplayed(), "Record is displayed");

        driver.quit();
    }

    @Test
    public void testNewTabOpen() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://goodbeautychicago.com/");

        WebElement bookNowButton = driver.findElement(By.xpath("//span[contains(text(), \"Book now\")]"));
        Set<String> windowHandlesBefore = driver.getWindowHandles();
        assertEquals(windowHandlesBefore.size(), 1, "Initially more than 1 tab!");

        bookNowButton.click();
        Thread.sleep(4000);
        Set<String> windowHandlesAfter = driver.getWindowHandles();
        assertEquals(windowHandlesAfter.size(), 2, "New tab did not open!");

        driver.quit();
    }
    
    @Test
    public void testDashboardOrangePage() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement textBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            textBox.sendKeys("Admin");
            WebElement textBox1 = driver.findElement(By.name("password"));
            textBox1.sendKeys("admin123");
            WebElement submitButton = driver.findElement(By.tagName("button"));
            submitButton.click();

            WebElement pageName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h6")));
            String value = pageName.getText().trim();
            assertEquals("Dashboard", value);

            List<WebElement> sectionTitle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".oxd-layout-container .orangehrm-dashboard-widget-header p")));
            List<String> dashboardTitles = sectionTitle.stream()
                            .map(WebElement::getText)
                            .map(String::trim)
                            .collect(Collectors.toList());

            List<String> expectedTitles = Arrays.asList("Time at Work", "My Actions", "Quick Launch", "Buzz Latest Posts",
                    "Employees on Leave Today", "Employee Distribution by Sub Unit", "Employee Distribution by Location");

            assertTrue(dashboardTitles.containsAll(expectedTitles),
                    "Dashboard titles do not match expected titles. Found: " + dashboardTitles);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void testGardenplus() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.gardensplus.ca");

        Thread.sleep(1000);

        WebElement buttonDismiss = driver.findElement(By.className("woocommerce-store-notice__dismiss-link"));
        buttonDismiss.click();

        WebElement input = driver.findElement(By.id("woocommerce-product-search-field-0"));

        input.sendKeys("Lovely Lolly");

        WebElement buttonSearch = driver.findElement(By.xpath("//*[@id=\"woocommerce_product_search-2\"]/form/button"));

        buttonSearch.submit();

        WebElement productTitle = driver.findElement(By.xpath("//h1[@class='product_title entry-title']"));

        Assert.assertTrue(productTitle.getText().contains("Lovely Lolly"));

        driver.quit();
    }

    @Test
    public void testEliza() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        Thread.sleep(2000);

        driver.findElement(By.xpath("//div//ul[@class='leftmenu']//li//a[text()='About Us']")).click();

        WebElement customerLoginHeader = driver.findElement(By.xpath("//h2[text()='Customer Login']"));
        String text =  customerLoginHeader.getText();
        assertEquals(text, "Customer Login");

        WebElement title = driver.findElement(By.xpath("//h1[text()='ParaSoft Demo Website']"));
        String titleText = title.getText();
        assertEquals(titleText, "ParaSoft Demo Website");

        driver.findElement(By.xpath("//p//a[text()='www.parasoft.com']")).click();
        WebElement textNewPage =  driver.findElement(By.xpath("//h1[contains(text(),' Testing That Keeps Pac')]"));
        String textNew = textNewPage.getText();
        assertEquals(textNew, "Testing That Keeps Pace With Real-World Innovation");


        driver.quit();
    }


}
