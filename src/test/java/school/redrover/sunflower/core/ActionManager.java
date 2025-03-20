package school.redrover.sunflower.core;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.time.Duration;

public class ActionManager {
    private static final Logger logger = LoggerFactory.getLogger(ActionManager.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private final JavascriptExecutor js;

    public ActionManager(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    // Клики
    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Двойной клик
    public void doubleClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        actions.doubleClick(element).perform();
    }

    // Перетаскивание
    public void dragAndDrop(By source, By target) {
        WebElement src = wait.until(ExpectedConditions.visibilityOfElementLocated(source));
        WebElement trg = wait.until(ExpectedConditions.visibilityOfElementLocated(target));
        actions.dragAndDrop(src, trg).perform();
    }

    // Скролл к элементу
    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Отправка файла
    public void uploadFile(By locator, String filePath) {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        fileInput.sendKeys(new File(filePath).getAbsolutePath());
    }

    // Обновление страницы
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Масштабирование
    public void zoom(int level) {
        js.executeScript("document.body.style.zoom = '" + level + "%'");
    }

    // ... Другие методы (hover, sendKeys, и т.д.)
}