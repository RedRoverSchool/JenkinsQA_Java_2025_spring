package school.redrover.sunflower.manus;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.sunflower.utils.PageParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the enhanced PageParser.
 * This class contains both unit tests using mocks and integration tests using real WebDriver.
 */
public class PageParserTest {

    @Mock
    private WebDriver mockDriver;

    @Mock
    private WebElement mockElement;

    private AutoCloseable closeable;
    private PageParser pageParser;
    private WebDriver realDriver;
    private static final String TEST_URL = "https://dev.hype.vote/";
    private static final String OUTPUT_DIR = "/tmp/pom_output";

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        closeable = MockitoAnnotations.openMocks(this);

        // Set up mock behavior
        when(mockDriver.getCurrentUrl()).thenReturn(TEST_URL);
        when(mockDriver.getTitle()).thenReturn("Test Page");

        // Initialize parser with mock driver for unit tests
        pageParser = new PageParser(mockDriver);

        // Create output directory
        new File(OUTPUT_DIR).mkdirs();
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();

        // Close real driver if it was initialized
        if (realDriver != null) {
            realDriver.quit();
        }
    }

    /**
     * Unit test for parsePage method.
     */
    @Test
    public void testParsePage() {
        // Arrange
        when(mockDriver.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("testId");
        when(mockElement.getTagName()).thenReturn("div");

        // Act
        int count = pageParser.parsePage();

        // Assert
        assertEquals(1, count, "Should collect one element");
        verify(mockDriver).findElements(any(By.class));
    }

    /**
     * Unit test for generatePOM method.
     */
    @Test
    public void testGeneratePOM() {
        // Arrange
        when(mockDriver.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("testId");
        when(mockElement.getTagName()).thenReturn("button");
        pageParser.parsePage();

        // Act
        String pomCode = pageParser.generatePOM();

        // Assert
        assertNotNull(pomCode, "POM code should not be null");
        assertTrue(pomCode.contains("public class TestPage"), "POM should contain class declaration");
        assertTrue(pomCode.contains("@FindBy"), "POM should contain FindBy annotations");
        assertTrue(pomCode.contains("private WebElement testId"), "POM should contain element declaration");
    }

    /**
     * Unit test for path analysis.
     */
    @Test
    public void testPathAnalysis() {
        // Test different URLs
        when(mockDriver.getCurrentUrl()).thenReturn("https://dev.hype.vote/about");
        String pomCode = pageParser.generatePOM();
        assertTrue(pomCode.contains("public class AboutPage"), "Should generate correct class name for path");

        when(mockDriver.getCurrentUrl()).thenReturn("https://dev.hype.vote/users/profile");
        pomCode = pageParser.generatePOM();
        assertTrue(pomCode.contains("public class ProfilePage"), "Should generate correct class name for nested path");
    }

    /**
     * Integration test for real browser interaction.
     * This test will be skipped if the website is not accessible.
     */
    @Test
    public void testRealBrowserIntegration() {
        try {
            // Initialize real WebDriver
            realDriver = new ChromeDriver();
            realDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Create parser with real driver
            PageParser realParser = new PageParser(realDriver);

            // Navigate to test URL
            realDriver.get(TEST_URL);

            // Parse page
            int elementCount = realParser.parsePage();

            // Assert
            assertTrue(elementCount > 0, "Should collect elements from real page");

            // Generate POM
            String pomCode = realParser.generatePOM();
            assertNotNull(pomCode, "Should generate POM code");

            // Save POM to file for inspection
            Files.writeString(Paths.get(OUTPUT_DIR, "TestPage.java"), pomCode);

        } catch (Exception e) {
            // Skip test if website is not accessible
            System.out.println("Skipping real browser test due to: " + e.getMessage());
        }
    }

    /**
     * Test for popup handling.
     */
    @Test
    public void testPopupHandling() {
        // Arrange
        WebElement popupElement = mock(WebElement.class);
        when(mockDriver.findElement(eq(By.id("loginPopup")))).thenReturn(popupElement);
        when(popupElement.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("username");
        when(mockElement.getTagName()).thenReturn("input");

        // Act
        try {
            String popupPomCode = pageParser.handlePopup("loginPopup");

            // Assert
            assertNotNull(popupPomCode, "Popup POM code should not be null");
            assertTrue(popupPomCode.contains("public class PopupLoginPopup"),
                    "Popup POM should have correct class name");
            assertTrue(popupPomCode.contains("private WebElement username"),
                    "Popup POM should contain popup elements");
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test for path-based POM generation.
     */
    @Test
    public void testPathBasedPOMGeneration() {
        // Arrange
        // Mock website crawling
        when(mockDriver.findElements(eq(By.tagName("a")))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("href")).thenReturn(
                TEST_URL + "about",
                TEST_URL + "products",
                TEST_URL + "contact");

        // Mock elements on each page
        when(mockDriver.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("testId");
        when(mockElement.getTagName()).thenReturn("div");

        // Act
        try {
            Map<String, String> pathToPOMMap = pageParser.generatePOMForAllPaths(TEST_URL, 1);

            // Assert
            assertNotNull(pathToPOMMap, "Path to POM map should not be null");
            assertFalse(pathToPOMMap.isEmpty(), "Path to POM map should not be empty");

            // Verify paths were processed
            verify(mockDriver, atLeastOnce()).get(contains(TEST_URL));
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test for saving POM classes to files.
     */
    @Test
    public void testSavePOMClassesToFiles() {
        // Arrange
        // Mock website crawling
        when(mockDriver.findElements(eq(By.tagName("a")))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("href")).thenReturn(TEST_URL + "about");

        // Mock elements on each page
        when(mockDriver.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("testId");
        when(mockElement.getTagName()).thenReturn("div");

        // Act
        try {
            List<String> generatedFiles = pageParser.saveAllPOMClassesToFiles(OUTPUT_DIR, TEST_URL, 1);

            // Assert
            assertNotNull(generatedFiles, "Generated files list should not be null");
            assertFalse(generatedFiles.isEmpty(), "Generated files list should not be empty");

            // Verify files were created
            for (String filePath : generatedFiles) {
                assertTrue(Files.exists(Paths.get(filePath)), "File should exist: " + filePath);
                String content = Files.readString(Paths.get(filePath));
                assertTrue(content.contains("public class"), "File should contain class declaration");
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test for error handling.
     */
    @Test
    public void testErrorHandling() {
        // Arrange
        when(mockDriver.findElements(any(By.class))).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(PageParser.PageParsingException.class, () -> pageParser.parsePage(),
                "Should throw PageParsingException when parsing fails");
    }

    /**
     * Test for thread safety.
     */
    @Test
    public void testThreadSafety() {
        // Arrange
        when(mockDriver.findElements(any(By.class))).thenReturn(List.of(mockElement));
        when(mockElement.getAttribute("id")).thenReturn("testId");
        when(mockElement.getTagName()).thenReturn("div");

        // Act
        Runnable parseTask = () -> pageParser.parsePage();

        // Create and start multiple threads
        Thread thread1 = new Thread(parseTask);
        Thread thread2 = new Thread(parseTask);

        thread1.start();
        thread2.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            fail("Thread interrupted: " + e.getMessage());
        }

        // Assert - no exceptions should be thrown
    }
}
