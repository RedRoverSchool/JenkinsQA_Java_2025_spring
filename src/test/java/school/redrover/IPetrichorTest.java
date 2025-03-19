package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class IPetrichorTest {
    private WebDriver driver;

    @BeforeMethod
    private void createDriver() {
        if(this.driver == null) {
            this.driver = new ChromeDriver();
        }
    }

    @AfterMethod
    private void quitDriver() {
        if(this.driver != null) {
            this.driver.quit();
        }
    }

    private static class Data {
        private static final String BUTTON_DEFAULT_COLOR = "rgba(247, 247, 247, 1)";

        private enum Lamp {
            LAMP1("lamp1", "rgba(255, 0, 0, 1)"),
            LAMP2("lamp2", "rgba(64, 64, 64, 1)"),
            LAMP3("lamp3", "rgba(64, 64, 64, 1)"),
            LAMP4("lamp4", "rgba(64, 64, 64, 1)");

            private final String id;
            private final String color;

            Lamp(String id, String color) {
                this.id = id;
                this.color = color;
            }

            private static Lamp byId(String id) {
                for (Lamp value : values()) {
                    if (value.id.equals(id)) {
                        return value;
                    }
                }
                return null;
            }
        }
    }

    @Test()
    public void testHomepageElementColorsOnLoad() {
        driver.get("https://blackboxpuzzles.workroomprds.com/puzzle31/");

        final List<WebElement> LAMPS_ELEMENTS = driver.findElements(By.cssSelector(".BigLamp"));
        final List<WebElement> BUTTONS_ELEMENTS = driver.findElements(By.cssSelector(".BigButton"));

        Assert.assertEquals(LAMPS_ELEMENTS.size(), 4);
        Assert.assertEquals(BUTTONS_ELEMENTS.size(), 9);

        LAMPS_ELEMENTS.forEach(element -> {
            final String elementId = element.getDomAttribute("id");
            Assert.assertNotNull(elementId);

            Data.Lamp lampDataId = Data.Lamp.byId(elementId);
            if (lampDataId != null) {
                Assert.assertEquals(element.getCssValue("background-color"), lampDataId.color);
            } else {
                Assert.fail("Lamp with id " + elementId + " not found.");
            }
        });

        BUTTONS_ELEMENTS.forEach(element -> {
            Assert.assertEquals(element.getCssValue("background-color"), Data.BUTTON_DEFAULT_COLOR);
        });
    }
}
