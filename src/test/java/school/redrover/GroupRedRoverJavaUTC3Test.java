package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.*;

public class GroupRedRoverJavaUTC3Test {

    private WebDriver driver;

    private WebDriverWait wait5;
    private WebDriverWait wait10;

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            synchronized (WebDriverWait.class) {
                wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            }
        }

        return wait5;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            synchronized (WebDriverWait.class) {
                wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            }
        }

        return wait10;
    }

    protected WebDriver getDriver() {
        if (driver == null) {
            synchronized (WebDriver.class) {
                driver = new ChromeDriver();
            }
        }

        return driver;
    }

    @BeforeMethod
    protected void start() {
        getDriver();
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    protected void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAuthorization() {

        getDriver().get("https://school.qa.guru/cms/system/login?required=true");

        getDriver().findElement(By.name("email")).sendKeys("gjcjavxusj@zvvzuv.com");
        getDriver().findElement(By.name("password")).sendKeys("E3&i&d1B");
        getDriver().findElement(By.id("xdget33092_1_1")).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Профиль']"))).click();

        getDriver().findElement(By.xpath("//*[@target='_self']")).click();

        String getName = getDriver().findElement(By.xpath("//h1[contains(text(),'Мой профиль')]")).getText();

        Assert.assertEquals(getName, "Мой профиль");
    }

    @Test
    public void RickAstleyTest() throws InterruptedException {

        String xPathPlayButton = "//button[@aria-keyshortcuts='k']";
        String xPathReject = "//button[contains(@aria-label, 'Reject the use of cookies')]";

        getDriver().get("https://www.youtube.com/watch?v=hPr-Yc92qaY");

        WebElement rejectButton = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathReject)));

        if (rejectButton.isDisplayed()) {
            rejectButton.click();
        }

        getDriver().findElement(By.xpath(xPathPlayButton)).click();

        int count = 0;

        WebElement button = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathPlayButton)));

        for (int i = 0; i < 10; i++) {
            button.click();
            count++;
        }

        Assert.assertEquals(count, 10);
    }

    @Test
    public void testCheckBox() throws InterruptedException {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        getDriver().findElement(By.cssSelector("#my-check-2")).click();

        getDriver().findElement(By.xpath("//button[@class='btn btn-outline-primary mt-3']")).click();

        Thread.sleep(2000);

        String value = getDriver().findElement(By.id("message")).getText();

        Assert.assertEquals(value, "Received!");

    }

    @Test
    public void testAmazon() throws InterruptedException {
        getDriver().get("https://www.amazon.com/customer-preferences/edit?ie=UTF8&preferencesReturnUrl=%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%2C%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%26preferencesReturnUrl%3D%2F-%2Fes%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%26preferencesReturnUrl%3D%252F-%252Fes%252Fcustomer-preferences%252Fedit%253Fie%253DUTF8%2526preferencesReturnUrl%253D%25252Fs%25253Fk%25253Dball%252526language%25253Des_US%252526crid%25253DXEQUFPHXIKJE%252526sprefix%25253Dball%2525252Caps%2525252C380%252526ref%25253Dnb_sb_noss_1%2526ref_%253Dtopnav_lang_ais%26ref_%3Dtopnav_lang_ais%26ref_%3Dtopnav_lang_ais%26language%3Den_US%26currency%3DUSD&ref_=topnav_lang_ais");

        getDriver().findElement(By.xpath("//*[contains(@class, 'glow-toaster-button-dismiss')]")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.xpath("(//i[@class='a-icon a-icon-radio'])[2]")).click();

        getDriver().findElement(By.id("icp-save-button")).click();

        Thread.sleep(2000);

        WebElement languageES = getDriver().findElement(By.cssSelector(".nav-line-2 div"));
        Assert.assertEquals(languageES.getText(), "ES");
    }

    @Test
    public void itemAddRemoveToCartTest() throws InterruptedException {
        getDriver().get("https://www.saucedemo.com/");

        WebElement inputUsername = driver.findElement(By.id("user-name"));
        WebElement inputPassword = driver.findElement(By.id("password"));

        inputUsername.sendKeys("standard_user");
        inputPassword.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("#login-button"));
        loginButton.click();

        getDriver().findElement(By.cssSelector("#item_0_title_link > div")).click();
        getDriver().findElement(By.xpath("//*[@id=\"add-to-cart\"]")).click();

        WebElement badgeCart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
        assertEquals(badgeCart.getText(), "1");

        Thread.sleep(2000);

        getDriver().findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"remove-sauce-labs-bike-light\"]")).click();

        Thread.sleep(2000);

        assertFalse(isDisplayed(badgeCart));
    }

    boolean isDisplayed(WebElement badgeElement) {
        try {
            return badgeElement.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

}