package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupJavaMastersTest {

    @Test
    public void testLogin() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement userNameTextField = driver.findElement(By.id("user-name"));
        userNameTextField.sendKeys("standard_user");

        WebElement passwordTextField = driver.findElement(By.cssSelector(".input_error.form_input#password"));
        passwordTextField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//form/input[@type='submit']"));
        loginButton.click();

        WebElement products = driver.findElement(By.xpath("//span[text()='Products']"));

        Assert.assertEquals(products.getText(), "Products");

        driver.quit();
    }

    @Test
    public void testShoppingCart() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement userNameField = driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        userNameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login-button\"]"));
        loginButton.click();

        WebElement backpackProduct = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        backpackProduct.click();

        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"add-to-cart\"]"));
        addToCartButton.click();

        WebElement cartLogo = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a"));
        cartLogo.click();

        WebElement itemQuantity = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[1]"));

        Assert.assertEquals(itemQuantity.getText(), "1");

        driver.quit();
    }

    @Test
    public void testLockedUserLogin() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement userNameTextField = driver.findElement(By.xpath("//form/div[1]/input[@class='input_error form_input']"));
        userNameTextField.sendKeys("locked_out_user");

        WebElement passwordTextField = driver.findElement(By.name("password"));
        passwordTextField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.xpath("//form/input[@type='submit']"));
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.tagName("h3"));

        Assert.assertEquals(errorMessage.getText(), "Epic sadface: Sorry, this user has been locked out.");

        driver.quit();
    }

    @Test
    public void testNavigationBtn() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        WebElement navigationBtn = driver.findElement(By.cssSelector(".btn[href=\"navigation1.html\"]"));
        navigationBtn.click();

        WebElement pageTitle = driver.findElement(By.cssSelector(".display-6"));
        String value = pageTitle.getText();

        Assert.assertEquals(value, "Navigation example");

        driver.quit();
    }

    @Test
    public void testUpdateCountsOnCartIcon() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement userNameTextField = driver.findElement(By.id("user-name"));
        userNameTextField.sendKeys("standard_user");

        WebElement passwordTextField = driver.findElement(By.id("password"));
        passwordTextField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartBtn.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_badge"));

        Assert.assertEquals(cartIcon.getText(), "1");

        driver.quit();

    }

    @Test
    public void testSuccessfulLoginPage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebElement usernameBox = driver.findElement(By.id("username"));
        usernameBox.sendKeys("student");

        WebElement passwordBox = driver.findElement(By.id("password"));
        passwordBox.sendKeys("Password123");

        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://practicetestautomation.com/logged-in-successfully/");

        WebElement successfulLoginMessage = driver.findElement(By.className("post-title"));
        Assert.assertEquals(successfulLoginMessage.getText(), "Logged In Successfully");

        driver.quit();
    }

    @Test
    public void testInvalidUserLogin() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebElement usernameBox = driver.findElement(By.id("username"));
        usernameBox.sendKeys("studen");

        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        Thread.sleep(3000);
        WebElement invalidUsernameMessage = driver.findElement(By.id("error"));
        Assert.assertEquals(invalidUsernameMessage.getText(), "Your username is invalid!");

        driver.quit();
    }

    @Test
    public void testRemoveItemFromCart() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        WebElement userNameTextField = driver.findElement(By.id("user-name"));
        userNameTextField.sendKeys("standard_user");

        WebElement passwordTextField = driver.findElement(By.id("password"));
        passwordTextField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement addToCartBtn = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addToCartBtn.click();

        WebElement removeFromCartBtn = driver.findElement(By.id("remove-sauce-labs-backpack"));

        Assert.assertEquals(removeFromCartBtn.getText(), "Remove");
        removeFromCartBtn.click();

        // Working properly only after second designation locator
        WebElement addToCartBtn1 = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Assert.assertEquals(addToCartBtn1.getText(), "Add to cart");

        driver.quit();
    }

    @Test
    public void testSelenuimMainPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        driver.findElement(By.xpath("//input[@id='my-text-id']")).sendKeys("Selenium");
        driver.findElement(By.xpath("//label[contains(text(),'Password')]/input[@class='form-control']")).sendKeys("12345");
        driver.findElement(By.xpath("//option[@value='3']")).click();
        driver.findElement(By.xpath("//input[@name='my-datalist']")).sendKeys("Seattle");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(2000);

        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='message']")).getText(),"Received!");

        driver.quit();
    }

    @Test
    public void testBasicHtmlPage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("student");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("123456789");
        driver.findElement(By.xpath("//input[@value='cb1']")).click();
        driver.findElement(By.xpath("//input[@value='rd2']")).click();
        driver.findElement(By.xpath("//option[contains(text(),'Selection Item 1')]")).click();
        driver.findElement(By.xpath("//option[@value='dd4']")).click();
        driver.findElement(By.xpath("//input[@value='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valueusername']")).getText(),"student");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valuepassword']")).getText(), "123456789");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valuecheckboxes0']")).getText(),"cb1");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valueradioval']")).getText(),"rd2");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valuemultipleselect0']")).getText(),"ms1");
        Assert.assertEquals(driver.findElement(By.xpath("//li[@id='_valuedropdown']")).getText(),"dd4");

        driver.quit();
    }

    @Test
    public void testProductPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        Thread.sleep(1000);

        driver.findElement(By.xpath("//div[contains(text(),'Sauce Labs Backpack')]")).click();

        Thread.sleep(1000);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_details_name large_size']")).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_details_desc large_size']")).getText(), "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertEquals(driver.findElement(By.xpath("//img[@class='inventory_details_img']")).getDomAttribute("src"), "/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_details_price']")).getText(), "$29.99");
        Assert.assertTrue(driver.findElement(By.xpath("//button[@id='add-to-cart']")).isDisplayed());

        driver.quit();
    }

    @Test
    public void testVisualUser() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("visual_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//img[@alt='Sauce Labs Backpack']")).getDomAttribute("src"), "/static/media/sl-404.168b1cce.jpg");

        driver.quit();
    }
}
