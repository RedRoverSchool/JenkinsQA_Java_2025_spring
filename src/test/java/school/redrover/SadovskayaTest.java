package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SadovskayaTest {
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
}
