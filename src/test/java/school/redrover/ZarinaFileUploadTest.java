package school.redrover;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class ZarinaFileUploadTest {

    WebDriver driver = new ChromeDriver();

    @Test
    public void uploadImageTest(){

        driver.get("https://the-internet.herokuapp.com/");
        WebElement title = driver.findElement(By.xpath("//h1[@class='heading']"));
        String mainTitle = title.getText();
        Assert.assertEquals(mainTitle, "Welcome to the-internet");

        WebElement fileUploadButton = driver.findElement(By.xpath("//a[@href='/upload']"));
        fileUploadButton.click();

        WebElement header = driver.findElement(By.xpath("//h3[text()='File Uploader']"));
        String headerTitle = header.getText();
        Assert.assertEquals(headerTitle, "File Uploader");

        WebElement uploadInput = driver.findElement(By.id("file-upload"));

        String filePath = System.getProperty("user.dir") + "\\src\\uploadFiles\\java.png";
        uploadInput.sendKeys(filePath);

        WebElement uploadButton = driver.findElement(By.id("file-submit"));
        uploadButton.click();

        WebElement successMessage = driver.findElement(By.xpath("//h3[text()='File Uploaded!']"));
        String message = successMessage.getText();
        Assert.assertEquals(message, "File Uploaded!");



        driver.quit();


    }
}
