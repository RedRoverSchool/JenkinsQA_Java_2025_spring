package school.redrover.sunflower.pages.hype;

// --- ExampleTest.java ---


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import school.redrover.sunflower.utils.PageParser;

import java.util.List;
import java.util.Map;


public class ExampleTest {
    public static void main(String[] args) {
        // Setup WebDriver (using WebDriverManager)
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); //Optional: run in headless mode
        WebDriver driver = new ChromeDriver(options);


        try {
            // --- Test Basic Parsing and POM Generation ---
            PageParser parser = new PageParser(driver);
            driver.get("https://dev.hype.vote/");
            parser.parsePage();
            String className = "DevHypeVotePage";
            String packageName = "school.redrover.sunflower.pages";
            Map<String, WebElement> elements; //Access elementCollector
            elements = parser.elementCollector.collectElements();
            String pomCode = parser.pomGenerator.generatePOMClass(className, packageName, elements);
            System.out.println("Generated POM Code (Single Page):\n" + pomCode);

            // --- Test Multi-Page Parsing and Saving ---
            String baseDir = "src/main/java";  // Standard Maven source directory
            String baseUrl = "https://dev.hype.vote";
            int maxDepth = 2; // Example depth

            List<String> savedFiles = parser.saveAllPOMClassesToFiles(baseDir, baseUrl, maxDepth);
            System.out.println("\nSaved POM Files:");
            for (String filePath : savedFiles) {
                System.out.println(filePath);
            }

            // --- Test Cookie Saving and Loading ---
            driver.get("https://dev.hype.vote/");
            // ... Perform actions that set cookies (e.g., login) ...
            parser.saveCookies("cookies.txt");

            // In a separate test or later session:
            // driver = new ChromeDriver(); // New browser instance
            parser.loadCookies("cookies.txt");
            driver.get("https://dev.hype.vote/"); // Go to a page where cookies should be applied


        } finally {
            driver.quit(); // Always quit the driver
        }
    }
}