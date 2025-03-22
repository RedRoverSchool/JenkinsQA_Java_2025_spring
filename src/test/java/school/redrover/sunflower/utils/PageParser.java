package school.redrover.sunflower.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageParser {
    private WebDriver driver;
    private Map<String, WebElement> elementsMap = new HashMap<>();
    private CookiesManager cookiesManager;

    public PageParser(WebDriver driver) {
        this.driver = driver;
        this.cookiesManager = new CookiesManager(driver);
        PageFactory.initElements(driver, this);
    }

    // Автоматический сбор элементов [[2]][[5]]
    public void parsePage() {
        List<WebElement> allElements = driver.findElements(By.xpath("//*"));
        for (WebElement element : allElements) {
            String id = element.getAttribute("id");
            String className = element.getAttribute("class");
            String tagName = element.getTagName();

            if (id != null && !id.isEmpty()) {
                elementsMap.put(id, element);
            } else if (className != null && !className.isEmpty()) {
                elementsMap.put(className + "_" + tagName, element);
            }
        }
    }

    // Генерация POM-класса [[3]]
    public String generatePOM() {
        StringBuilder pomCode = new StringBuilder();
        pomCode.append("public class GeneratedPage {\n");

        for (Map.Entry<String, WebElement> entry : elementsMap.entrySet()) {
            String locatorType = entry.getKey().contains("_") ? "className" : "id";
            String locatorValue = entry.getKey().contains("_") ?
                    entry.getKey().split("_")[0] : entry.getKey();

            pomCode.append(String.format(
                    "    @FindBy(%s = \"%s\")\n    private WebElement %s;\n\n",
                    locatorType, locatorValue, entry.getKey().replaceAll("[^a-zA-Z]", "_")
            ));
        }

        pomCode.append("}");
        return pomCode.toString();
    }

    // Работа с попапами [[7]]
    public void handlePopup(String popupId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(popupId)));
        // Генерация отдельного POM для попапа
        new PopupParser(driver, popupId).parse();
    }

    // Работа с куками [[1]][[4]]
    public void saveCookies(String filename) {
        cookiesManager.saveCookiesToFile(filename);
    }

    public void loadCookies(String filename) {
        cookiesManager.loadCookiesFromFile(filename);
    }

    // Вложенный класс для обработки попапов
    private class PopupParser {
        private WebDriver driver;
        private String popupId;

        public PopupParser(WebDriver driver, String popupId) {
            this.driver = driver;
            this.popupId = popupId;
        }

        public void parse() {
            // Аналогичная логика парсинга, специфичная для попапов
        }
    }
}

// Отдельный класс для управления куками
class CookiesManager {
    private WebDriver driver;

    public CookiesManager(WebDriver driver) {
        this.driver = driver;
    }

    public void saveCookiesToFile(String filename) {
        // Реализация сохранения кук в файл [[4]]
    }

    public void loadCookiesFromFile(String filename) {
        // Реализация загрузки кук из файла [[1]]
    }
}