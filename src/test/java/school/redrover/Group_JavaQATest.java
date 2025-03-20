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
import java.util.Set;

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
    public void testNewTabOpen() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://goodbeautychicago.com/");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"post-460\"]/div/div/section[1]/div[2]/div/div/section[2]/div/div/div/div[2]/div/div/a/span/span"));

        Set<String> windowHandlesBefore = driver.getWindowHandles();
        assertEquals(windowHandlesBefore.size(), 1, "Initially more than 1 tab!");

        submitButton.click();
        Thread.sleep(4000);
        Set<String> windowHandlesAfter = driver.getWindowHandles();

        assertEquals(windowHandlesAfter.size(), 2, "New tab did not open!");

        driver.quit();
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
