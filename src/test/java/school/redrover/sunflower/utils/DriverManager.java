package school.redrover;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverManager {

    public static WebDriver getChromeDriver() {
        try {
            // Настраиваем URL для Selenium Grid Hub
            URL seleniumGridURL = new URL("http://selenium-hub:4444/wd/hub");
            
            // Создаем опции Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            
            // Создаем экземпляр RemoteWebDriver, который будет управлять браузером через Selenium Grid
            return new RemoteWebDriver(seleniumGridURL, options);
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось подключиться к Selenium Grid Hub", e);
        }
    }
}