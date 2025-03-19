package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CheripkoTest {

    @Test
    public void testAndromeda() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://duckduckgo.com/");

        WebElement input = driver.findElement(By.id("searchbox_input"));
        input.sendKeys("Andromeda");


        WebElement submitbutton = driver.findElement(By.xpath("(//*[name()='svg'][@class='iconButton_icon__K7FBb iconButton_size-20__Ql3lL'])[1]"));
        submitbutton.click();


        WebElement name = driver.findElement(By.xpath("(//h2[normalize-space()='Andromeda Galaxy'])[1]"));
        String nameText = name.getText();

        assertEquals(nameText, "Andromeda Galaxy");

        driver.quit();



    }

}
