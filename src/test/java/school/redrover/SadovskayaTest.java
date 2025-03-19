package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SadovskayaTest {
    @Test
    public void testSelenuimMainPage() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");


        WebElement textInputField = driver.findElement(By.xpath("//input[@id='my-text-id']"));
        textInputField.sendKeys("Selenium");

        WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Password')]/input[@class='form-control']"));
        passwordField.sendKeys("12345");

        WebElement dropdownSelect = driver.findElement(By.xpath("//option[@value='3']"));
        dropdownSelect.click();

        WebElement dropdownDatalist = driver.findElement(By.xpath("//input[@name='my-datalist']"));
        dropdownDatalist.sendKeys("Seattle");


        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        Thread.sleep(2000);


        WebElement result = driver.findElement(By.xpath("//p[@id='message']"));
        String value = result.getText();
        Assert.assertEquals(value,"Received!");

        driver.quit();

    }

    @Test
    public void testBasicHtmlPage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");

        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys("student");

        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("123456789");

        WebElement checkbox = driver.findElement(By.xpath("//input[@value='cb1']"));
        checkbox.click();

        WebElement radioButton = driver.findElement(By.xpath("//input[@value='rd2']"));
        radioButton.click();

        WebElement selectValues = driver.findElement(By.xpath("//option[contains(text(),'Selection Item 1')]"));
        selectValues.click();

        WebElement dropdownItem = driver.findElement(By.xpath("//option[@value='dd4']"));
        dropdownItem.click();

        WebElement submitButton = driver.findElement(By.xpath("//input[@value='submit']"));
        submitButton.click();

        WebElement usernameResult = driver.findElement(By.xpath("//li[@id='_valueusername']"));
        String value1 = usernameResult.getText();

        Assert.assertEquals(value1,"student");

        WebElement passwordResult = driver.findElement(By.xpath("//li[@id='_valuepassword']"));
        String value2 = passwordResult.getText();

        Assert.assertEquals(value2, "123456789");

        WebElement checkboxResult = driver.findElement(By.xpath("//li[@id='_valuecheckboxes0']"));
        String value3 = checkboxResult.getText();

        Assert.assertEquals(value3,"cb1");

        WebElement radioResult = driver.findElement(By.xpath("//li[@id='_valueradioval']"));
        String value4 = radioResult.getText();

        Assert.assertEquals(value4,"rd2");

        WebElement selectResult = driver.findElement(By.xpath("//li[@id='_valuemultipleselect0']"));
        String value5 = selectResult.getText();

        Assert.assertEquals(value5,"ms1");

        WebElement dropdownResult = driver.findElement(By.xpath("//li[@id='_valuedropdown']"));
        String value6 = dropdownResult.getText();

        Assert.assertEquals(value6,"dd4");



        driver.quit();
    }
}
