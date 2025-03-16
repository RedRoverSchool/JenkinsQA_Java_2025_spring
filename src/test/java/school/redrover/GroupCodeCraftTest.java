package school.redrover;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.*;

public class GroupCodeCraftTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSwagLabs() throws InterruptedException {

        driver.get("https://www.saucedemo.com");

        String title = driver.getTitle();
        assertEquals(title, "Swag Labs");

        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement backpack = driver.findElement
                (By.id("add-to-cart-sauce-labs-backpack"));
        backpack.click();

        WebElement removeBackpack = driver.findElement
                (By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory ']"));
        String getRemoveBackpackText = removeBackpack.getText();
        assertEquals(getRemoveBackpackText, "Remove");
        String color = removeBackpack.getCssValue("border");
        int rgbIndex = color.indexOf("rgb");
        assertEquals(color.substring(rgbIndex), "rgb(226, 35, 26)");

        WebElement cart = driver.findElement
                (By.xpath("//span[@class='shopping_cart_badge']"));
        String cartSize = cart.getText();
        assertEquals(cartSize, "1");

        WebElement jacket = driver.findElement
                (By.xpath("//button[@id='add-to-cart-sauce-labs-fleece-jacket']"));
        jacket.click();

        assertEquals(cart.getText(), "2");

        cart.click();

        WebElement firstItem = driver.findElement
                (By.xpath("//a[@id='item_4_title_link']/div"));
        String firstItemText = firstItem.getText();
        assertEquals(firstItemText, "Sauce Labs Backpack");

        WebElement secondItem = driver.findElement
                (By.xpath("//a[@id='item_5_title_link']/div"));
        String secondItemText = secondItem.getText();
        assertEquals(secondItemText, "Sauce Labs Fleece Jacket");

        String firstPrice = driver.findElement
                        (By.xpath("//a[@id='item_4_title_link']/following-sibling::div[2]/div")).
                getText();
        firstPrice = firstPrice.substring(1);

        assertEquals(firstPrice, "29.99");
        double firstPriceDouble = Double.parseDouble(firstPrice);

        String secondString = driver.findElement
                        (By.xpath("//a[@id='item_5_title_link']/following-sibling::div[2]/div")).
                getText();
        secondString = secondString.substring(1);

        assertEquals(secondString, "49.99");
        double secondPriceDouble = Double.parseDouble(secondString);

        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        driver.findElement(By.xpath("//input[@id='first-name']")).
                sendKeys("Poopa");
        driver.findElement(By.xpath("//input[@id='last-name']")).
                sendKeys("Loopa");
        driver.findElement(By.xpath("//input[@id='postal-code']")).
                sendKeys("322228");
        driver.findElement(By.xpath("//input[@id='continue']")).
                click();

        assertEquals(driver.findElement(By.xpath("//div[@data-test='shipping-info-value']"))
                .getText(), "Free Pony Express Delivery!");

        double tax = (firstPriceDouble + secondPriceDouble) * 0.08;
        double totalPriceWithTax = firstPriceDouble + secondPriceDouble + tax;
        double totalPriceWithoutTax = firstPriceDouble + secondPriceDouble;

        double roundedTax = Math.round(tax * 100.0) / 100.0;
        double roundedPriceWithTax = Math.round(totalPriceWithTax * 100.0) / 100.0;
        double roundedPriceWithoutTax = Math.round(totalPriceWithoutTax * 100.0) / 100.0;

        assertEquals(driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"))
                .getText(), "Item total: $" + roundedPriceWithoutTax);

        assertEquals(driver.findElement(By.xpath("//div[@class='summary_tax_label']"))
                .getText(), "Tax: $" + roundedTax + "0");

        assertEquals(driver.findElement(By.xpath("//div[@data-test='total-label']"))
                .getText(), "Total: $" + roundedPriceWithTax);

        driver.findElement(By.xpath("//button[@id='finish']")).
                click();

        assertEquals(driver.findElement
                        (By.xpath("//h2")).getText(),
                "Thank you for your order!");

        driver.findElement(By.xpath("//button[@id='back-to-products']")).
                click();

        assertEquals(driver.findElement
                        (By.xpath("//div[@class='app_logo']")).
                getText(), "Swag Labs");

        Thread.sleep(2000);
    }


    @Test
    public void testToolsQATestBox() throws InterruptedException {

        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://demoqa.com");

        driver.findElement
                        (By.xpath("//div[1][@class='card mt-4 top-card']"))
                .click();

        driver.findElement
                        (By.xpath("//div[@class='element-list collapse show']/descendant::li[@id='item-0']"))
                .click();

        String fullName = "Poopa Loopa";
        String eMail = "poopa@loopa.com";
        String currentAddress = "Pushkin's street, Kolotushkin's house";
        String permAddress = "Same as current";

        driver.findElement(By.xpath("//input[@placeholder='Full Name']"))
                .sendKeys(fullName);
        driver.findElement(By.xpath("//input[@id='userEmail']"))
                .sendKeys(eMail);
        driver.findElement(By.xpath("//textarea[@id='currentAddress']"))
                .sendKeys(currentAddress);
        driver.findElement(By.xpath("//textarea[@id='permanentAddress']"))
                .sendKeys(permAddress);

        WebElement submit = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submit']")));

        Thread.sleep(2000);

        action.click(submit).perform();

        WebElement fullNameWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='name']")));

        WebElement eMailWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='email']")));

        WebElement currentAddressWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='currentAddress']")));

        WebElement permAddressWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='permanentAddress']")));

        assertEquals(fullNameWeb.getText(), "Name:" + fullName);
        assertEquals(eMailWeb.getText(), "Email:" + eMail);
        assertEquals(currentAddressWeb.getText(), "Current Address :" + currentAddress);
        assertEquals(permAddressWeb.getText(), "Permananet Address :" + permAddress);

    }

    @Test
    public void bonigarciaWebForm() throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        driver.findElement(By.id("my-text-id")).sendKeys("aS1!@%&");
        driver.findElement(By.xpath("//input[@type='password']"))
                .sendKeys("aS1!@%&");
        driver.findElement(By.tagName("textarea")).sendKeys("aS1!@%&");

        boolean inputDisabledCheck = driver.findElement
                (By.xpath("//input[@name='my-disabled']")).isEnabled();

        assertFalse(inputDisabledCheck);

        assertNotNull(driver.findElement
                        (By.xpath("//input[@name='my-readonly']")).
                getDomAttribute("readonly"));

        WebElement dropDownMenu = driver.findElement
                (By.xpath("//select[@name='my-select']"));

        Select select = new Select(dropDownMenu);

        select.selectByContainsVisibleText("Open this select menu");
        select.selectByValue("1");
        select.selectByIndex(2);
        select.selectByContainsVisibleText("Three");

        assertEquals(driver.findElement
                (By.xpath("//option[text()='Three']")).getText(), "Three");

        WebElement dropDownMenuDataList = wait.until
                (ExpectedConditions.elementToBeClickable
                        (By.xpath("//input[@name='my-datalist']")));
        dropDownMenuDataList.click();

        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].value = 'San Francisco';", dropDownMenuDataList);
        dropDownMenuDataList.clear();
        dropDownMenuDataList.click();
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].value = 'New York';", dropDownMenuDataList);
        dropDownMenuDataList.clear();
        dropDownMenuDataList.click();
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].value = 'Seattle';", dropDownMenuDataList);
        dropDownMenuDataList.clear();
        dropDownMenuDataList.click();
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].value = 'Los Angeles';", dropDownMenuDataList);
        dropDownMenuDataList.clear();
        dropDownMenuDataList.click();
        ((JavascriptExecutor) driver).executeScript
                ("arguments[0].value = 'Chicago';", dropDownMenuDataList);

        WebElement fileInput = driver.findElement(By.xpath("//input[@name='my-file']"));

        // Создание временного txt-фаила для того, чтобы тест проходил у всех
        // После прогона файл удаляется
        File tempFile = File.createTempFile("temp", ".txt");
        FileUtils.writeStringToFile(tempFile, "This is a test file content.", "UTF-8");
        fileInput.sendKeys(tempFile.getAbsolutePath());
        tempFile.deleteOnExit();

        WebElement checkedCheckbox = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-check-1']")));
        WebElement defaultCheckbox = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-check-2']")));
        WebElement checkedRadio = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-radio-1']")));
        WebElement defaultRadio = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-radio-2']")));

        Thread.sleep(100);
        checkedCheckbox.click();

        assertFalse(checkedCheckbox.isSelected());
        checkedCheckbox.click();
        assertTrue(checkedCheckbox.isSelected());

        defaultCheckbox.click();
        assertTrue(defaultCheckbox.isSelected());
        defaultCheckbox.click();
        assertFalse(defaultCheckbox.isSelected());

        assertTrue(checkedRadio.isSelected());
        Thread.sleep(500);
        defaultRadio.click();
        assertFalse(checkedRadio.isSelected());
        assertTrue(defaultRadio.isSelected());

        WebElement colorPicker = driver.findElement
                (By.xpath("//input[@name='my-colors']"));

        colorPicker.click();

        String currentColor = colorPicker.getDomAttribute("value");
        assertEquals(currentColor, "#563d7c");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '#C47A12';", colorPicker);
        // Я так особо и не разобрался как с Color Picker работать, даже нейронки особо не помогают

        WebElement dateField = driver.findElement(By.xpath("//input[@name='my-date']"));
        dateField.click();

        Thread.sleep(100);

        WebElement previousMonth = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='datepicker-days']/descendant::th[@class='prev']")));

        for (int i = 0; i < 5; i++) {
            previousMonth.click();
        }
        WebElement monthAndYear = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//div[@class='datepicker-days']/descendant::th[@class='datepicker-switch']")));
        assertEquals(monthAndYear.getText(), "October 2024");

        WebElement nextMonth = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//div[@class='datepicker-days']/descendant::th[@class='next']")));

        for (int i = 0; i < 5; i++) {
            nextMonth.click();
        }
        assertEquals(monthAndYear.getText(), "March 2025");

        WebElement dayPick = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//div[@class='datepicker-days']/descendant::td[text()='2' and @class='day']")));

        dayPick.click();
        monthAndYear.click();

        WebElement monthClick = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//span[text()='Jun']")));
        monthClick.click();

        assertEquals(dateField.getDomProperty("value"), "03/02/2025");

        defaultRadio.click();

        WebElement range = driver.findElement(By.xpath("//input[@name='my-range']"));

        for (int i = 0; i < 11; i++) {
            String value = String.valueOf(i);
            js.executeScript("arguments[0].value = '" + value + "';", range);
            String valueCheck = range.getDomProperty("valueAsNumber");
            assertEquals(value, valueCheck);
        }

        WebElement submit = driver.findElement(By.tagName("button"));
        submit.click();
        driver.navigate().back();

    }

    @Test
    public void testBonigarciaWebFormXpath (){
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.findElement(By.xpath("//div/a[@href='web-form.html']")).click();
        WebElement header = driver.findElement(By.xpath("//h1[@class='display-6']"));
        String headerText = header.getText();

        Assert.assertEquals(headerText, "Web form");

        //xpath
        WebElement textInputByXpath = driver.findElement(By.xpath("//input[@name='my-text']"));
        textInputByXpath.sendKeys("Adelya");

    }
}