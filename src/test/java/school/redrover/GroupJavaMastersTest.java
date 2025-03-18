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

}
