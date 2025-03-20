package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.io.function.IOConsumer.forEach;
import static org.testng.Assert.*;

public class GroupRedRoverJavaUTC3Test {

    private WebDriver driver;

    private WebDriverWait wait5;
    private WebDriverWait wait10;

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        }

        return wait5;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        }

        return wait10;
    }

    protected WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    protected void start() {
        driver = new ChromeDriver();
        // Получаем разрешение экрана
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Устанавливаем размер окна браузера (не больше разрешения экрана)
        int browserWidth = Math.min(1920, screenWidth);
        int browserHeight = Math.min(1080, screenHeight);
        Dimension dimension = new Dimension(browserWidth, browserHeight);
        driver.manage().window().setSize(dimension);
    }

    @AfterMethod
    protected void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait5 = null;
            wait10 = null;
        }
    }

    private boolean isDisplayed(WebElement badgeElement) {
        try {
            return badgeElement.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
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

        WebElement getName = getDriver().findElement(By.xpath("//h1[contains(text(),'Мой профиль')]"));

        Assert.assertEquals(getName.getText(), "Мой профиль");
    }

    @Test

    public void RickAstleyTest() throws InterruptedException {

        final String xPathPlayButton = "//button[@aria-keyshortcuts='k']";
        final String xPathReject = "//button[contains(@aria-label, 'Reject the use of cookies')]";

        getDriver().get("https://www.youtube.com/watch?v=hPr-Yc92qaY");

        try {
            WebElement rejectButton = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathReject)));
            rejectButton.click();
        } catch (TimeoutException e) {
            System.out.println("The 'Reject' button didn't appear");
        }

        getDriver().findElement(By.xpath(xPathPlayButton)).click();

        int count = 0;

        WebElement button = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathPlayButton)));

        for (int i = 0; i < 10; i++) {
            Thread.sleep(8000);
            button.click();
            count++;
        }

        Assert.assertEquals(count, 10);
    }

    @Test
    public void testCheckBox() {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        getDriver().findElement(By.cssSelector("#my-check-2")).click();

        getDriver().findElement(By.xpath("//button[@class='btn btn-outline-primary mt-3']")).click();

        WebElement message = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
        System.out.println("Actual message text: " + message.getText());
        Assert.assertEquals(message.getText(), "Received!");

    }

    @Test
    public void testAmazon() {
        getDriver().get("https://www.amazon.com/customer-preferences/edit?ie=UTF8&preferencesReturnUrl=%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%2C%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%26preferencesReturnUrl%3D%2F-%2Fes%2Fcustomer-preferences%2Fedit%3Fie%3DUTF8%26preferencesReturnUrl%3D%252F-%252Fes%252Fcustomer-preferences%252Fedit%253Fie%253DUTF8%2526preferencesReturnUrl%253D%25252Fs%25253Fk%25253Dball%252526language%25253Des_US%252526crid%25253DXEQUFPHXIKJE%252526sprefix%25253Dball%2525252Caps%2525252C380%252526ref%25253Dnb_sb_noss_1%2526ref_%253Dtopnav_lang_ais%26ref_%3Dtopnav_lang_ais%26ref_%3Dtopnav_lang_ais%26language%3Den_US%26currency%3DUSD&ref_=topnav_lang_ais");

        try {
            WebElement toaster = getWait5().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(@class, 'glow-toaster-button-dismiss')]")));
            toaster.click();
        } catch (TimeoutException e) {
            System.out.println("No toaster appeared within 5 seconds, moving on!");
        }

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("(//i[@class='a-icon a-icon-radio'])[2]"))).click();

        getDriver().findElement(By.id("icp-save-button")).click();

        getWait10().until(ExpectedConditions.textToBe(By.cssSelector(".nav-line-2 div"), "ES"));

        WebElement languageES = getDriver().findElement(By.cssSelector(".nav-line-2 div"));
        Assert.assertEquals(languageES.getText(), "ES");
    }

    @Test
    public void testImageFormatCheck() {
        // Открываем страницу
        getDriver().get("https://redrover.school/?lang=en");

        // Ожидаем, пока изображение загрузится
        WebElement image = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[contains(@alt, 'Sergey')]")));

        // Получаем URL изображения
        String imageUrl = image.getDomAttribute("src");
        String safeImageUrl = Objects.toString(imageUrl, "");

        // Проверяем, что ссылка заканчивается на .png
        Assert.assertTrue(safeImageUrl.toLowerCase().endsWith(".png"), "Image URL does not end with .png: " + safeImageUrl);
    }


    @Test
    public void testItemAddRemoveToCart() {
        getDriver().get("https://www.saucedemo.com/");

        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");

        getDriver().findElement(By.cssSelector("#login-button")).click();

        getDriver().findElement(By.cssSelector("#item_0_title_link > div")).click();
        getDriver().findElement(By.xpath("//*[@id='add-to-cart']")).click();

        WebElement badgeCart = driver.findElement(By.xpath("//*[@id='shopping_cart_container']/a/span"));
        assertEquals(badgeCart.getText(), "1");

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='shopping_cart_container']/a"))).click();
        getDriver().findElement(By.xpath("//*[@id='remove-sauce-labs-bike-light']")).click();

        getWait10().until(ExpectedConditions.invisibilityOf(badgeCart));

        assertFalse(isDisplayed(badgeCart));
    }

    @Test
    public void testShopsSearch() {
        getDriver().get("https://sadovy.ru/semena/");

        getDriver().findElement(By.xpath("//*[@id='search_input']")).sendKeys("S020130");
        getDriver().findElement(By.className("ty-search-magnifier")).click();

        WebElement searchResult = driver.findElement(By.className("product-title"));

        assertEquals(searchResult.getText(), "Томат Мохнатый шмель 0.05г (Семена Алтая)");
    }

    @Test
    public void testSeasonvar() {
        getDriver().get("http://seasonvar.ru/");
        WebElement field = getDriver().findElement(By.cssSelector("div .awesomplete input"));
        field.click();
        field.sendKeys("Severance");
        getDriver().findElement(By.cssSelector("button.btn.head-btn")).click();

        String nameText = getDriver().findElement(By.xpath("//div[@class='pgs-search-info']/a[2]")).getText();
        Assert.assertEquals(nameText, "Severance");
    }

    @Test
    public void testAuthenticationForm() {

        getDriver().get("https://the-internet.herokuapp.com/login");

        getDriver().findElement(By.id("username")).sendKeys("tomsmith");

        getDriver().findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        getDriver().findElement(By.xpath("//i[@class='fa fa-2x fa-sign-in']")).click();

        String messageText = getDriver().findElement(By.xpath("//h4[@class='subheader']")).getText();


        Assert.assertEquals(messageText, "Welcome to the Secure Area. When you are done click logout below.");

    }

    @Test
    public void testTPlus() {

        getDriver().get("https://www.tplusgroup.ru/");

        getDriver().findElement(By.xpath("//*[@id=\"website-menu\"]/ul/li[1]/a")).click();

        String value = getDriver().findElement(By.xpath("//*[@id=\"content-header\"]/h1")).getText();
        Assert.assertEquals(value, "О компании");

    }

    @Test

    public void testEbayRedirectionToLoginPage() {

        getDriver().get("https://www.ebay.com/");

        WebElement signInLink = getDriver().findElement(By.xpath("//*[@id=\"gh\"]/nav/div[1]/span[1]/span/a"));

        signInLink.click();

        String currentTitle = getDriver().getTitle();
        String currentUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(currentTitle, "Sign in or Register | eBay");
        Assert.assertEquals(currentUrl, "https://signin.ebay.com/ws/eBayISAPI.dll?SignIn&sgfl=gh&ru=https%3A%2F%2Fwww.ebay.com%2F");

        getDriver().quit();
    }

    @Test
    public void testHorizontalSliderTBank() {

        getDriver().get("https://www.tbank.ru/loans/cash-loan/realty/form/autoloan/");

        getWait5().until(d -> getDriver().getTitle() != null); //  проверка загрузки страницы

        WebElement sliderSum = getDriver().findElement(By.xpath("//div[@data-field-name='cashloan_calculator_amount_field']//div[@data-qa-type='uikit/Draggable']"));
        WebElement sliderYears = getDriver().findElement(By.xpath("//div[@data-field-name='cashloan_calculator_term_field']//div[@data-qa-type='uikit/Draggable']"));

        Actions actions = new Actions(getDriver());

        actions.dragAndDropBy(sliderSum, -77, 0).perform();
        actions.dragAndDropBy(sliderYears, -40, 0).perform();

        WebElement monthlyPayment = getDriver().findElement(By.xpath("//div[@data-qa-type='uikit/titleAndSubtitle.textPrimary']/div"));

        // Проверка значения
        Assert.assertEquals(monthlyPayment.getText(), "27 600 ₽");
    }

    @Test
    public void testHorizontalSlider() {
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));

        Actions actions = new Actions(driver);
        actions.dragAndDropBy(slider, 10, 0).perform();

        WebElement sliderValue = driver.findElement(By.xpath("//span[@id='range']"));
        Assert.assertEquals(sliderValue.getText(), "3");
    }

    @Test

    public void TestAlertButton() {
        getDriver().get("https://the-internet.herokuapp.com/javascript_alerts");

        getDriver().findElement(By.xpath("//button[@onclick='jsAlert()']")).click();

        getDriver().switchTo().alert().accept();

        String result = getDriver().findElement(By.xpath("//p[@id='result']")).getText();

        Assert.assertEquals(result, "You successfully clicked an alert");
    }

    @Test
    public void testYearsSliderTBank() {
        getDriver().get("https://www.tbank.ru/loans/cash-loan/realty/form/autoloan/");

        getWait5().until(d -> getDriver().getTitle() != null);

        WebElement slider = getDriver().findElement(By.xpath("//div[@data-field-name='cashloan_calculator_term_field']//div[@data-qa-type='uikit/Draggable']"));

        Actions actions = new Actions(getDriver());

        actions.dragAndDropBy(slider, 100, 0).perform();

        WebElement element = getDriver().findElement(By.xpath("//div[@data-field-name='cashloan_calculator_term_field']//input[@data-qa-type ='uikit/inlineInput.input' ]"));
        String inputValue = element.getAttribute("value");

        Assert.assertEquals(inputValue, "15\u00a0лет");
    }
}

