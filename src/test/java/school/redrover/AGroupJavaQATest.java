package school.redrover;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AGroupJavaQATest {
    @Test
    public void testRequestQuote() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa1-aws.rssc.com/request-quote");

        driver.findElement(By.id("request-pricing_firstName")).sendKeys("Viviana");
        driver.findElement(By.id("request-pricing_lastName")).sendKeys("Costa");
        driver.findElement(By.xpath("//*[@data-id='request-pricing_country']")).click();
        driver.findElement(By.xpath("//span[text()='United States']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='request-pricing_state']/following-sibling::button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id='request-pricing']//select[@id='request-pricing_state']/parent::div//a[span[text()='Alaska']]")).click();
        driver.findElement(By.id("request-pricing_phone")).sendKeys("7865187654");
        driver.findElement(By.id("request-pricing_email")).sendKeys("viviana@gmail.com");
        driver.findElement(By.xpath("//*[@id='request-pricing_voyageOfInterest']/following-sibling::button")).click();
        driver.findElement(By.xpath("//span[contains(text(),'168 nights')]")).click();
        driver.findElement(By.xpath("//*[@id='request-pricing']//button[@type='submit']")).click();
        Thread.sleep(5000);
        assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Your Personal Consultant')]/ancestor::article//h2")).isDisplayed(), "Thank you page is displayed");

        driver.quit();
    }

    @Test
    public void testNewTabOpen() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1280, 920));
        driver.get("https://goodbeautychicago.com/");

        WebElement bookNowButton = driver.findElement(By.xpath("//span[text()=\"Book now\"]"));
        Set<String> windowHandlesBefore = driver.getWindowHandles();
        assertEquals(windowHandlesBefore.size(), 1, "Initially more than 1 tab!");

        bookNowButton.click();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        wait.until(value -> value.getWindowHandles().size() > 1);
        Set<String> windowHandlesAfter = driver.getWindowHandles();
        driver.quit();
        assertEquals(windowHandlesAfter.size(), 2, "New tab did not open!");
    }

    @Test
    public void testMarina() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement textBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement textBox1 = driver.findElement(By.name("password"));
            WebElement submitButton = driver.findElement(By.tagName("button"));

            textBox.sendKeys("Admin");
            textBox1.sendKeys("admin123");
            submitButton.click();

            WebElement pageName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h6")));
            String value = pageName.getText().trim();

            System.out.println("Extracted text: " + value);
            assertEquals("Dashboard", value);

        } finally {
            driver.quit();
        }
    }

    @Test
    public void testGardenplusSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.gardensplus.ca");
        Thread.sleep(1000);

        driver.findElement(By.className("woocommerce-store-notice__dismiss-link")).click();
        driver.findElement(By.id("woocommerce-product-search-field-0")).sendKeys("Lovely Lolly");
        driver.findElement(By.xpath("//*[@id='woocommerce_product_search-2']/form/button")).submit();
        Thread.sleep(1000);

        WebElement productTitle = driver.findElement(By.xpath("//h1[@class='product_title entry-title']"));    //(//h1[@"product_title entry-title"='Lovely Lolly');

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
        String text = customerLoginHeader.getText();
        assertEquals(text, "Customer Login");

        WebElement title = driver.findElement(By.xpath("//h1[text()='ParaSoft Demo Website']"));
        String titleText = title.getText();
        assertEquals(titleText, "ParaSoft Demo Website");

        driver.findElement(By.xpath("//p//a[text()='www.parasoft.com']")).click();
        WebElement textNewPage = driver.findElement(By.xpath("//h1[contains(text(),' Testing That Keeps Pac')]"));
        String textNew = textNewPage.getText();
        assertEquals(textNew, "Testing That Keeps Pace With Real-World Innovation");


        driver.quit();
    }

    @Test
    public void testAddEntitlements() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        userName.sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        WebElement leave = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Leave']")));
        leave.click();
        driver.findElement(By.xpath("//span[contains(text(),'Entitlements')] ")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Add')] ")).click();
        driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys("John Smit");
        Thread.sleep(5000);
        WebElement autocompleteOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Type for hints...']/parent::div/following-sibling::div")));
        autocompleteOption.click();
        driver.findElement(By.xpath("//div[contains(text(),'Select')]")).click();
        Thread.sleep(3000);
        WebElement leaveTypeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Matternity')]")));
        leaveTypeOption.click();
        driver.findElement(By.xpath("//div[contains(text(),'2025-01-01')]")).click();
        driver.findElement(By.xpath("//label[text()='Entitlement']/parent::*/following-sibling::*/input")).sendKeys("2.00");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[text()=' Confirm ']")).click();

        WebElement recordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'table')]/*[text()='CAN - Matternity']")));
        assertTrue(recordElement.isDisplayed(), "Record is displayed");

        driver.quit();
    }
}
