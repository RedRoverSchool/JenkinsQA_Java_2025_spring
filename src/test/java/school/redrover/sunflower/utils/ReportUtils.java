package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportUtils {

    private final WebDriver driver;

    public ReportUtils(WebDriver driver) {
        this.driver = driver;
        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs(); // Создаем папку и все необходимые родительские папки
        }
    }

    /**
     * Создание скриншота при ошибке
     */
    public void takeScreenshotOnFailure(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String screenshotName = "screenshots/" + testName + "_failure_" + timestamp + ".png";

            try (FileOutputStream fos = new FileOutputStream(screenshotName)) {
                fos.write(screenshotBytes);
                System.out.println("Скриншот сохранён: " + screenshotName);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании скриншота: " + e.getMessage());
        }
    }

    /**
     * Парсинг видимых элементов с текстом
     */
    public List<WebElement> parseVisibleElementsWithText() {
        List<WebElement> allElements = driver.findElements(By.xpath("//*"));
        List<WebElement> result = new ArrayList<>();

        for (WebElement element : allElements) {
            try {
                if (!element.isDisplayed()) continue;
                String text = element.getText().trim();
                if (!text.isEmpty()) result.add(element);
            } catch (Exception e) {
                System.out.println("Ошибка при обработке элемента: " + e.getMessage());
            }
        }

        return result;
    }

    /**
     * Генерация CSS селектора или XPath
     */
    public String getElementSelector(WebElement element) {
        try {
            String id = element.getAttribute("id");
            if (id != null && !id.isEmpty()) return "#" + id;

            String classes = element.getAttribute("class");
            if (classes != null && !classes.isEmpty()) return element.getTagName() + "." + classes.split("\\s+")[0];

            String name = element.getAttribute("name");
            if (name != null && !name.isEmpty()) return element.getTagName() + "[name='" + name + "']";

            return generateXPath(element);
        } catch (Exception e) {
            return element.toString();
        }
    }

    /**
     * Генерация XPath выражения для элемента
     */
    private String generateXPath(WebElement element) {
        String js = "function absoluteXPath(element) {" +
                "var comp, comps = [];" +
                "var parent = null;" +
                "var xpath = '';" +
                "var getPos = function(element) {" +
                "var position = 1, curNode;" +
                "if (element.nodeType == Node.ATTRIBUTE_NODE) {" +
                "return null;" +
                "}" +
                "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {" +
                "if (curNode.nodeName == element.nodeName) {" +
                "++position;" +
                "}" +
                "}" +
                "return position;" +
                "};" +

                "if (element instanceof Document) {" +
                "return '/';" +
                "}" +

                "for (; element && !(element instanceof Document); element = element.nodeType ==Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {" +
                "comp = comps[comps.length] = {};" +
                "switch (element.nodeType) {" +
                "case Node.TEXT_NODE:" +
                "comp.name = 'text()';" +
                "break;" +
                "case Node.ATTRIBUTE_NODE:" +
                "comp.name = '@' + element.nodeName;" +
                "break;" +
                "case Node.PROCESSING_INSTRUCTION_NODE:" +
                "comp.name = 'processing-instruction()';" +
                "break;" +
                "case Node.COMMENT_NODE:" +
                "comp.name = 'comment()';" +
                "break;" +
                "case Node.ELEMENT_NODE:" +
                "comp.name = element.nodeName;" +
                "break;" +
                "}" +
                "comp.position = getPos(element);" +
                "}" +

                "for (var i = comps.length - 1; i >= 0; i--) {" +
                "comp = comps[i];" +
                "xpath += '/' + comp.name.toLowerCase();" +
                "if (comp.position !== null && comp.position > 1) {" +
                "xpath += '[' + comp.position + ']';" +
                "}" +
                "}" +

                "return xpath;" +
                "}" +
                "return absoluteXPath(arguments[0]);";

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript(js, element);
    }

    /**
     * Создание ASCII-таблицы
     */
    public String buildTable(List<WebElement> elements) {
        StringBuilder table = new StringBuilder();

        String format = "| %-3s | %-10s | %-40s | %-30s |%n";
        String separator = "+-----+------------+------------------------------------------+--------------------------------+%n";

        table.append(String.format(separator));
        table.append(String.format(format, "№", "Tag Name", "Selector / XPath", "Text"));
        table.append(String.format(separator));

        int index = 1;
        for (WebElement element : elements) {
            String tagName = element.getTagName();
            String selector = getElementSelector(element);
            String text = element.getText().trim().replaceAll("\\s+", " ");

            String truncatedText = text.length() > 28 ? text.substring(0, 25) + "..." : text;

            table.append(String.format(format, index++, tagName, selector, truncatedText));
        }

        table.append(String.format(separator));
        table.append(String.format("Всего видимых элементов с текстом: %d%n", elements.size()));

        return table.toString();
    }

    /**
     * Сохранение ASCII-таблицы в файл
     */
    public void saveReportToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("Отчёт сохранён в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении отчёта: " + e.getMessage());
        }
    }

    /**
     * Сохранение списка элементов в CSV
     */
    public void saveCsvReport(String filename, List<WebElement> elements) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("№,Tag Name,Selector / XPath,Text\n");

            int index = 1;
            for (WebElement element : elements) {
                String tagName = element.getTagName();
                String selector = getElementSelector(element).replace(",", ";");
                String text = element.getText().trim().replaceAll("[\\r\\n]+", " ").replace(",", ";");

                writer.write(String.format("%d,%s,%s,%s%n", index++, tagName, selector, text));
            }

            System.out.println("CSV отчёт сохранён в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении CSV: " + e.getMessage());
        }
    }
}