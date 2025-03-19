package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

    public class LumaMainPageTest {

        WebDriver driver = new ChromeDriver();

        @Test
        public void homePageTest (){
            driver.get("https://magento.softwaretestingboard.com/");

            WebElement siteLogo = driver.findElement(By.xpath("//a[@class='logo']"));
            siteLogo.click();

            WebElement homePageTitle = driver.findElement(By.xpath("//span[@class='base']"));
            String textHomePageTitle = homePageTitle.getText();

            Assert.assertEquals(textHomePageTitle, "Home Page");

            driver.quit();
        }


    }


