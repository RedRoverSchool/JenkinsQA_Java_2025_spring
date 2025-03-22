package school.redrover.sunflower.manus;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import school.redrover.sunflower.utils.PageParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Test runner for the enhanced PageParser.
 * This class performs real-world testing on accessible websites.
 */
public class PageParserTestRunner {

    private static final String OUTPUT_DIR = "/home/ubuntu/parser_project/output";
    private static final int MAX_DEPTH = 2;

    public static void main(String[] args) {
        // Create output directory
        new File(OUTPUT_DIR).mkdirs();

        // Test on dev.hype.vote
        testWebsite("https://dev.hype.vote/", "dev_hype_vote");

        // Test on buysellvouchers.com
        testWebsite("https://www.buysellvouchers.com/", "buysellvouchers");

        System.out.println("Testing completed. Check output directory for results: " + OUTPUT_DIR);
    }

    private static void testWebsite(String baseUrl, String outputSubdir) {
        WebDriver driver = null;
        try {
            System.out.println("\n\n========================================");
            System.out.println("Testing PageParser on: " + baseUrl);
            System.out.println("========================================");

            // Create output subdirectory
            String websiteOutputDir = OUTPUT_DIR + "/" + outputSubdir;
            new File(websiteOutputDir).mkdirs();

            // Initialize WebDriver with headless option for server environment
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Create parser
            PageParser pageParser = new PageParser(driver);

            // Test 1: Basic page parsing
            System.out.println("\nTest 1: Basic page parsing");
            driver.get(baseUrl);
            int elementCount = pageParser.parsePage();
            System.out.println("Collected " + elementCount + " elements from homepage");

            // Test 2: Generate POM for homepage
            System.out.println("\nTest 2: Generate POM for homepage");
            String pomCode = pageParser.generatePOM();
            System.out.println("Generated POM class with " + pomCode.split("\n").length + " lines");
            Files.writeString(Paths.get(websiteOutputDir, "HomePage.java"), pomCode);
            System.out.println("Saved homepage POM to: " + websiteOutputDir + "/HomePage.java");

            // Test 3: Path-based POM generation (limited depth for testing)
            System.out.println("\nTest 3: Path-based POM generation");
            try {
                Map<String, String> pathToPOMMap = pageParser.generatePOMForAllPaths(baseUrl, MAX_DEPTH);
                System.out.println("Generated " + pathToPOMMap.size() + " POM classes for different paths");

                // Save individual POM files
                int fileCount = 0;
                for (Map.Entry<String, String> entry : pathToPOMMap.entrySet()) {
                    String path = entry.getKey();
                    String code = entry.getValue();

                    // Create sanitized filename from path
                    String filename = path.replace(baseUrl, "")
                            .replaceAll("[^a-zA-Z0-9]", "_")
                            .replaceAll("_+", "_");
                    if (filename.isEmpty() || filename.equals("_")) {
                        filename = "HomePage";
                    } else {
                        filename = filename.substring(0, 1).toUpperCase() + filename.substring(1) + "Page";
                    }

                    Files.writeString(Paths.get(websiteOutputDir, filename + ".java"), code);
                    fileCount++;
                }
                System.out.println("Saved " + fileCount + " POM class files to: " + websiteOutputDir);

            } catch (Exception e) {
                System.out.println("Error during path-based POM generation: " + e.getMessage());
                e.printStackTrace();
            }

            // Test 4: Save all POM classes to files with proper package structure
            System.out.println("\nTest 4: Save all POM classes with package structure");
            try {
                String packageOutputDir = websiteOutputDir + "/package_structure";
                List<String> generatedFiles = pageParser.saveAllPOMClassesToFiles(packageOutputDir, baseUrl, MAX_DEPTH);
                System.out.println("Generated " + generatedFiles.size() + " POM class files with package structure");
                System.out.println("Files saved to: " + packageOutputDir);

                // List the first few files
                int filesToShow = Math.min(5, generatedFiles.size());
                System.out.println("Sample of generated files:");
                for (int i = 0; i < filesToShow; i++) {
                    System.out.println("  - " + generatedFiles.get(i));
                }

            } catch (Exception e) {
                System.out.println("Error during package structure generation: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nTesting completed for: " + baseUrl);

        } catch (Exception e) {
            System.out.println("Error testing website " + baseUrl + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
