package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

public class ZeroToQAHeroTest {

    @Test
    public void testProductToCart() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.manage().window().maximize();

        driver.get("https://www.demoblaze.com/");
        Thread.sleep(2000);

        List <WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, 'col-lg-4 col-md-6')]//h4/a[contains(@href, 'prod')]"));
        Assert.assertEquals(elements.size(), 9);

        String itemTitle = elements.get(0).getText();
        elements.get(0).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//a[@onclick='addToCart(1)']"))).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        WebElement cart = driver.findElement(By.xpath("//div//a[@href='cart.html']"));
        cart.click();

        String actualItemInCart =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//tr[@class='success']/td[2]"))).getText();
        Assert.assertEquals(actualItemInCart,itemTitle);
        driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {

        WebDriver driver = new ChromeDriver();


        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.saucedemo.com/");

        String title = driver.getTitle();
        assertEquals("Swag Labs", title);

        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement submitButton = driver.findElement(By.id("login-button"));
        Thread.sleep(1000);
        submitButton.click();

        WebElement pageName = driver.findElement(By.xpath("//*[@id='header_container']/div[2]/span"));
        assertEquals("Products", pageName.getText());

        driver.quit();
    }

    @Test
    public void testFailedLogin() {

        WebDriver driver = new ChromeDriver();

        String randomUserName = UUID.randomUUID().toString();
        String randomUserPswd = UUID.randomUUID().toString();
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.saucedemo.com/");

        String title = driver.getTitle();
        assertEquals("Swag Labs", title);

        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys(randomUserName);

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(randomUserPswd);

        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();

        WebElement error = driver.findElement(By.xpath("//*[@data-test='error']"));
        System.out.println(error.getText());
        assertEquals(expectedErrorMessage, error.getText());

        driver.quit();
    }

    @Test
    public void testLogIn() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demo.applitools.com/"); //открываем главную страницу

        WebElement usernameBox = driver.findElement(By.id("username"));
        usernameBox.sendKeys("Natasha_test");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("12345678!");

        WebElement SignIn = driver.findElement(By.id("log-in"));
        SignIn.click();

        driver.quit();
    }

    @Test
    public void testTabletka() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://tabletka.by/"); //открываем главную страницу

        WebElement textBox = driver.findElement(By.className("ls-select-input"));
        textBox.sendKeys("Валидол");

        WebElement submitLupa = driver.findElement(By.className("lupa"));
        submitLupa.click();

        WebElement search = driver.findElement(By.className("tooltip-info-header"));
        String searchText = search.getText();

        Assert.assertEquals(searchText, "ВАЛИДОЛ");

        driver.quit();
    }

    @Test
    public void testSortItems() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String expectedProductName = "Sauce Labs Onesie";

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get("https://www.saucedemo.com/");

        String title = driver.getTitle();
        assertEquals("Swag Labs", title);

        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();

        WebElement sortMenu = driver.findElement(By.xpath("//*[@data-test='product-sort-container']"));
        sortMenu.click();

        WebElement sortOption = driver.findElement(By.xpath("//*[@data-test='product-sort-container']/option[3]"));
        sortOption.click();

        WebElement productName = driver.findElement(By.xpath("//*[@id=\"item_2_title_link\"]/div"));

        assertEquals(expectedProductName, productName.getText());

        driver.quit();
    }

    @Test
    public void tesGlobalsqaCom() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(@class, 'btn btn-primary ')][1]"));
        submitButton.click();

        WebElement select  = driver.findElement(By.name("userSelect"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        select.click();

        WebElement choice = driver.findElement(By.xpath("//div[@class='form-group']/*/option[3]"));
        String value = choice.getText();
        assertEquals("Harry Potter", value);
        choice.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement login = driver.findElement(By.xpath("//button[@type='submit']"));
        String log = login.getText();
        assertEquals("Login", log);
        login.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement fin = driver.findElement(By.xpath("//span[contains(text(), 'Harry')]"));
        String finish = fin.getText();
        assertEquals("Harry Potter", finish);

        driver.quit();
    }

    @Test
    public void testSettingFilters(){
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://www.parasoft.com/");
        driver.manage().window().maximize();

        WebElement buttonTryMe = driver.findElement(By.linkText("Try Parasoft"));
        buttonTryMe.click();

        WebElement filterSolutions = driver.findElement(By.xpath("//h4[contains(text(),'Solutions')]"));
        WebElement searchButton = driver.findElement(By.cssSelector("input[name='_sf_submit']"));

        filterSolutions.click();
        WebElement filter_In_Solutions = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'Reporting & Analytics')]")));
        actions.moveToElement(filter_In_Solutions).click().perform();

        WebElement filterIndustries = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Industries')]")));
        filterIndustries.click();
        WebElement filter_In_Industries = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'Telecommunications')]")));
        actions.moveToElement(filter_In_Industries);
        actions.click(filter_In_Industries).perform();
        actions.moveToElement(searchButton).click().perform();

        List<WebElement> searchResultNames = driver.findElements(By.cssSelector("div.b-product-icon h4"));
        ArrayList<String> searchResults = new ArrayList<>(0);
        for(WebElement name: searchResultNames){
            searchResults.add(name.getText());
        }

        Assert.assertEquals(searchResults.toString(),
                "[C/C++test, C/C++test CT, Jtest, dotTEST, DTP, CTP, Selenic, SOAtest, Virtualize]",
                "Names should be the same!");

        driver.quit();
    }



}
