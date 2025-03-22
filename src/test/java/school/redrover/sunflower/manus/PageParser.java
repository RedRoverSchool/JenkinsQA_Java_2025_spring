package school.redrover.sunflower.manus;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Enhanced PageParser for automatically generating Page Object Model classes from web pages.
 * This implementation follows best practices for Selenium automation and supports path-based POM generation.
 *
 * @author Improved by Manus
 * @version 2.0
 */
public class PageParser {
    private static final Logger logger = LoggerFactory.getLogger(PageParser.class);
    private final WebDriver driver;
    private final ElementCollector elementCollector;
    private final POMGenerator pomGenerator;
    private final PathAnalyzer pathAnalyzer;
    private final CookiesManager cookiesManager;
    private final WebDriverWait wait;
    private final Map<String, WebElement> elementsMap = new ConcurrentHashMap<>();
    private final int defaultTimeoutSeconds;

    /**
     * Creates a new PageParser with default timeout of 10 seconds.
     *
     * @param driver WebDriver instance to use for parsing
     */
    public PageParser(WebDriver driver) {
        this(driver, 10);
    }

    /**
     * Creates a new PageParser with specified timeout.
     *
     * @param driver WebDriver instance to use for parsing
     * @param timeoutSeconds timeout in seconds for waiting operations
     */
    public PageParser(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.defaultTimeoutSeconds = timeoutSeconds;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        this.elementCollector = new ElementCollector(driver, wait);
        this.pomGenerator = new POMGenerator();
        this.pathAnalyzer = new PathAnalyzer();
        this.cookiesManager = new CookiesManager(driver);
        PageFactory.initElements(driver, this);
        logger.info("PageParser initialized with timeout of {} seconds", timeoutSeconds);
    }

    /**
     * Parses the current page to collect elements.
     *
     * @return number of elements collected
     * @throws PageParsingException if parsing fails
     */
    public int parsePage() {
        logger.info("Starting page parsing at URL: {}", driver.getCurrentUrl());
        try {
            elementsMap.clear();
            Map<String, WebElement> collectedElements = elementCollector.collectElements();
            elementsMap.putAll(collectedElements);
            logger.info("Successfully parsed page, collected {} elements", elementsMap.size());
            return elementsMap.size();
        } catch (Exception e) {
            logger.error("Error parsing page: {}", e.getMessage(), e);
            throw new PageParsingException("Failed to parse page", e);
        }
    }

    /**
     * Generates a POM class for the current page.
     *
     * @return generated POM class code
     * @throws POMGenerationException if generation fails
     */
    public String generatePOM() {
        logger.info("Generating POM for current page: {}", driver.getCurrentUrl());
        try {
            if (elementsMap.isEmpty()) {
                logger.warn("No elements collected, parsing page first");
                parsePage();
            }

            String url = driver.getCurrentUrl();
            String className = pathAnalyzer.getPageClassNameFromPath(url);
            String packageName = pathAnalyzer.getPackageNameFromPath(url);

            String pomCode = pomGenerator.generatePOMClass(className, packageName, elementsMap, driver);
            logger.info("Successfully generated POM class: {}", className);
            return pomCode;
        } catch (Exception e) {
            logger.error("Error generating POM: {}", e.getMessage(), e);
            throw new POMGenerationException("Failed to generate POM", e);
        }
    }

    /**
     * Generates POM classes for all paths on a website.
     *
     * @param baseUrl base URL of the website
     * @param maxDepth maximum crawl depth
     * @return map of URL paths to generated POM classes
     * @throws POMGenerationException if generation fails
     */
    public Map<String, String> generatePOMForAllPaths(String baseUrl, int maxDepth) {
        logger.info("Generating POM classes for all paths on {} with max depth {}", baseUrl, maxDepth);
        try {
            Map<String, String> pathToPOMMap = new ConcurrentHashMap<>();
            Set<String> discoveredPaths = pathAnalyzer.discoverPaths(driver, baseUrl, maxDepth);

            for (String path : discoveredPaths) {
                String fullUrl = baseUrl + (path.startsWith("/") ? path : "/" + path);
                driver.get(fullUrl);
                wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

                parsePage();
                String pomCode = generatePOM();
                pathToPOMMap.put(path, pomCode);

                logger.info("Generated POM for path: {}", path);
            }

            logger.info("Successfully generated {} POM classes", pathToPOMMap.size());
            return pathToPOMMap;
        } catch (Exception e) {
            logger.error("Error generating POM for all paths: {}", e.getMessage(), e);
            throw new POMGenerationException("Failed to generate POM for all paths", e);
        }
    }

    /**
     * Saves all generated POM classes to files.
     *
     * @param baseDirectory base directory to save files
     * @param baseUrl base URL of the website
     * @param maxDepth maximum crawl depth
     * @return list of generated file paths
     * @throws POMGenerationException if generation fails
     */
    public List<String> saveAllPOMClassesToFiles(String baseDirectory, String baseUrl, int maxDepth) {
        logger.info("Saving all POM classes to directory: {}", baseDirectory);
        try {
            Map<String, String> pathToPOMMap = generatePOMForAllPaths(baseUrl, maxDepth);
            List<String> generatedFiles = new ArrayList<>();

            for (Map.Entry<String, String> entry : pathToPOMMap.entrySet()) {
                String path = entry.getKey();
                String pomCode = entry.getValue();

                String packageName = pathAnalyzer.getPackageNameFromPath(path);
                String className = pathAnalyzer.getPageClassNameFromPath(path);

                String packagePath = packageName.replace('.', '/');
                String directoryPath = baseDirectory + "/" + packagePath;
                String filePath = directoryPath + "/" + className + ".java";

                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(pomCode);
                }

                generatedFiles.add(filePath);
                logger.info("Saved POM class to file: {}", filePath);
            }

            logger.info("Successfully saved {} POM classes to files", generatedFiles.size());
            return generatedFiles;
        } catch (Exception e) {
            logger.error("Error saving POM classes to files: {}", e.getMessage(), e);
            throw new POMGenerationException("Failed to save POM classes to files", e);
        }
    }

    /**
     * Handles a popup and generates a POM class for it.
     *
     * @param popupId ID of the popup element
     * @return generated POM class code for the popup
     * @throws PageParsingException if parsing fails
     */
    public String handlePopup(String popupId) {
        logger.info("Handling popup with ID: {}", popupId);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(popupId)));
            PopupParser popupParser = new PopupParser(driver, popupId, wait);
            Map<String, WebElement> popupElements = popupParser.parse();

            String className = "Popup" + popupId.substring(0, 1).toUpperCase() + popupId.substring(1);
            String packageName = pathAnalyzer.getPackageNameFromPath(driver.getCurrentUrl()) + ".popups";

            String pomCode = pomGenerator.generatePOMClass(className, packageName, popupElements, driver);
            logger.info("Successfully generated POM class for popup: {}", className);
            return pomCode;
        } catch (Exception e) {
            logger.error("Error handling popup: {}", e.getMessage(), e);
            throw new PageParsingException("Failed to handle popup", e);
        }
    }

    /**
     * Saves cookies to a file.
     *
     * @param filename file to save cookies to
     * @throws CookieOperationException if saving fails
     */
    public void saveCookies(String filename) {
        logger.info("Saving cookies to file: {}", filename);
        try {
            cookiesManager.saveCookiesToFile(filename);
            logger.info("Successfully saved cookies to file");
        } catch (Exception e) {
            logger.error("Error saving cookies: {}", e.getMessage(), e);
            throw new CookieOperationException("Failed to save cookies", e);
        }
    }

    /**
     * Loads cookies from a file.
     *
     * @param filename file to load cookies from
     * @throws CookieOperationException if loading fails
     */
    public void loadCookies(String filename) {
        logger.info("Loading cookies from file: {}", filename);
        try {
            cookiesManager.loadCookiesFromFile(filename);
            logger.info("Successfully loaded cookies from file");
        } catch (Exception e) {
            logger.error("Error loading cookies: {}", e.getMessage(), e);
            throw new CookieOperationException("Failed to load cookies", e);
        }
    }

    /**
     * Gets the collected elements map.
     *
     * @return map of element identifiers to WebElements
     */
    public Map<String, WebElement> getElementsMap() {
        return Collections.unmodifiableMap(elementsMap);
    }

    /**
     * Class for collecting elements from a web page.
     */
    private static class ElementCollector {
        private final WebDriver driver;
        private final WebDriverWait wait;
        private final Logger logger = LoggerFactory.getLogger(ElementCollector.class);
        private final List<ElementCollectionStrategy> strategies;

        /**
         * Creates a new ElementCollector.
         *
         * @param driver WebDriver instance
         * @param wait WebDriverWait instance
         */
        public ElementCollector(WebDriver driver, WebDriverWait wait) {
            this.driver = driver;
            this.wait = wait;
            this.strategies = new ArrayList<>();

            // Add strategies in order of efficiency
            strategies.add(new IdBasedStrategy());
            strategies.add(new NameBasedStrategy());
            strategies.add(new ClassBasedStrategy());
            strategies.add(new TagBasedStrategy());

            logger.info("ElementCollector initialized with {} strategies", strategies.size());
        }

        /**
         * Collects elements from the current page.
         *
         * @return map of element identifiers to WebElements
         */
        public Map<String, WebElement> collectElements() {
            logger.info("Collecting elements from page: {}", driver.getCurrentUrl());
            Map<String, WebElement> elements = new ConcurrentHashMap<>();

            // Apply all strategies
            for (ElementCollectionStrategy strategy : strategies) {
                elements.putAll(strategy.collectElements(driver));
            }

            // Handle iframes if present
            collectElementsFromIframes(elements);

            logger.info("Collected {} elements from page", elements.size());
            return elements;
        }

        /**
         * Collects elements from iframes.
         *
         * @param elements map to add iframe elements to
         */
        private void collectElementsFromIframes(Map<String, WebElement> elements) {
            try {
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                logger.info("Found {} iframes on page", iframes.size());

                if (iframes.isEmpty()) {
                    return;
                }

                // Store current context
                String currentWindow = driver.getWindowHandle();

                for (int i = 0; i < iframes.size(); i++) {
                    try {
                        WebElement iframe = iframes.get(i);
                        driver.switchTo().frame(iframe);

                        // Apply all strategies within iframe
                        for (ElementCollectionStrategy strategy : strategies) {
                            Map<String, WebElement> iframeElements = strategy.collectElements(driver);

                            // Add iframe prefix to keys
                            for (Map.Entry<String, WebElement> entry : iframeElements.entrySet()) {
                                elements.put("iframe" + i + "_" + entry.getKey(), entry.getValue());
                            }
                        }

                        // Switch back to main content
                        driver.switchTo().window(currentWindow);
                    } catch (Exception e) {
                        logger.warn("Error collecting elements from iframe {}: {}", i, e.getMessage());
                        driver.switchTo().window(currentWindow);
                    }
                }
            } catch (Exception e) {
                logger.error("Error handling iframes: {}", e.getMessage(), e);
            }
        }

        /**
         * Interface for element collection strategies.
         */
        private interface ElementCollectionStrategy {
            /**
             * Collects elements using this strategy.
             *
             * @param driver WebDriver instance
             * @return map of element identifiers to WebElements
             */
            Map<String, WebElement> collectElements(WebDriver driver);
        }

        /**
         * Strategy for collecting elements by ID.
         */
        private class IdBasedStrategy implements ElementCollectionStrategy {
            @Override
            public Map<String, WebElement> collectElements(WebDriver driver) {
                Map<String, WebElement> elements = new ConcurrentHashMap<>();
                List<WebElement> allElements = driver.findElements(By.xpath("//*[@id]"));

                for (WebElement element : allElements) {
                    try {
                        String id = element.getAttribute("id");
                        if (id != null && !id.isEmpty()) {
                            elements.put(id, element);
                        }
                    } catch (StaleElementReferenceException e) {
                        logger.debug("Stale element encountered during ID collection");
                    }
                }

                logger.info("Collected {} elements by ID", elements.size());
                return elements;
            }
        }

        /**
         * Strategy for collecting elements by name.
         */
        private class NameBasedStrategy implements ElementCollectionStrategy {
            @Override
            public Map<String, WebElement> collectElements(WebDriver driver) {
                Map<String, WebElement> elements = new ConcurrentHashMap<>();
                List<WebElement> allElements = driver.findElements(By.xpath("//*[@name]"));

                for (WebElement element : allElements) {
                    try {
                        String name = element.getAttribute("name");
                        if (name != null && !name.isEmpty()) {
                            elements.put("name_" + name, element);
                        }
                    } catch (StaleElementReferenceException e) {
                        logger.debug("Stale element encountered during name collection");
                    }
                }

                logger.info("Collected {} elements by name", elements.size());
                return elements;
            }
        }

        /**
         * Strategy for collecting elements by class.
         */
        private class ClassBasedStrategy implements ElementCollectionStrategy {
            @Override
            public Map<String, WebElement> collectElements(WebDriver driver) {
                Map<String, WebElement> elements = new ConcurrentHashMap<>();
                List<WebElement> allElements = driver.findElements(By.xpath("//*[@class]"));

                for (WebElement element : allElements) {
                    try {
                        String className = element.getAttribute("class");
                        String tagName = element.getTagName();

                        if (className != null && !className.isEmpty()) {
                            elements.put(className + "_" + tagName, element);
                        }
                    } catch (StaleElementReferenceException e) {
                        logger.debug("Stale element encountered during class collection");
                    }
                }

                logger.info("Collected {} elements by class", elements.size());
                return elements;
            }
        }

        /**
         * Strategy for collecting elements by tag.
         */
        private class TagBasedStrategy implements ElementCollectionStrategy {
            @Override
            public Map<String, WebElement> collectElements(WebDriver driver) {
                Map<String, WebElement> elements = new ConcurrentHashMap<>();
                List<String> interestingTags = Arrays.asList("a", "button", "input", "select", "textarea", "form");

                for (String tag : interestingTags) {
                    List<WebElement> tagElements = driver.findElements(By.tagName(tag));
                    int count = 0;

                    for (int i = 0; i < tagElements.size(); i++) {
                        try {
                            WebElement element = tagElements.get(i);
                            // Only add elements that don't already have ID or class
                            String id = element.getAttribute("id");
                            String className = element.getAttribute("class");

                            if ((id == null || id.isEmpty()) && (className == null || className.isEmpty())) {
                                elements.put(tag + "_" + i, element);
                                count++;
                            }
                        } catch (StaleElementReferenceException e) {
                            logger.debug("Stale element encountered during tag collection");
                        }
                    }

                    logger.debug("Collected {} elements with tag {}", count, tag);
                }

                logger.info("Collected {} elements by tag", elements.size());
                return elements;
            }
        }
    }

    /**
     * Class for generating POM classes.
     */
    private static class POMGenerator {
        private final Logger logger = LoggerFactory.getLogger(POMGenerator.class);

        /**
         * Generates a POM class.
         *
         * @param className name of the class to generate
         * @param packageName package name for the class
         * @param elements map of element identifiers to WebElements
         * @param driver WebDriver instance for additional information
         * @return generated POM class code
         */
        public String generatePOMClass(String className, String packageName, Map<String, WebElement> elements, WebDriver driver) {
            logger.info("Generating POM class {} in package {}", className, packageName);
            StringBuilder pomCode = new StringBuilder();

            // Package declaration
            pomCode.append("package ").append(packageName).append(";\n\n");

            // Imports
            pomCode.append("import org.openqa.selenium.*;\n");
            pomCode.append("import org.openqa.selenium.support.FindBy;\n");
            pomCode.append("import org.openqa.selenium.support.PageFactory;\n");
            pomCode.append("import org.openqa.selenium.support.ui.ExpectedConditions;\n");
            pomCode.append("import org.openqa.selenium.support.ui.WebDriverWait;\n");
            pomCode.append("import java.time.Duration;\n\n");

            // Class JavaDoc
            pomCode.append("/**\n");
            pomCode.append(" * Page Object Model for ").append(className).append("\n");
            pomCode.append(" * Generated automatically by PageParser\n");
            pomCode.append(" * URL: ").append(driver.getCurrentUrl()).append("\n");
            pomCode.append(" */\n");

            // Class declaration
            pomCode.append("public class ").append(className).append(" {\n");

            // WebDriver field
            pomCode.append("    private final WebDriver driver;\n");
            pomCode.append("    private final WebDriverWait wait;\n\n");

            // Element fields with @FindBy annotations
            for (Map.Entry<String, WebElement> entry : elements.entrySet()) {
                String elementName = formatElementName(entry.getKey());
                String locatorType = determineLocatorType(entry.getKey());
                String locatorValue = determineLocatorValue(entry.getKey(), entry.getValue());

                pomCode.append("    @FindBy(").append(locatorType).append(" = \"").append(locatorValue).append("\")\n");
                pomCode.append("    private WebElement ").append(elementName).append(";\n\n");
            }

            // Constructor
            pomCode.append("    /**\n");
            pomCode.append("     * Constructor for ").append(className).append("\n");
            pomCode.append("     * @param driver WebDriver instance\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(className).append("(WebDriver driver) {\n");
            pomCode.append("        this.driver = driver;\n");
            pomCode.append("        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));\n");
            pomCode.append("        PageFactory.initElements(driver, this);\n");

            // Add page verification if title is available
            String pageTitle = driver.getTitle();
            if (pageTitle != null && !pageTitle.isEmpty()) {
                pomCode.append("        if (!driver.getTitle().contains(\"").append(pageTitle).append("\")) {\n");
                pomCode.append("            throw new IllegalStateException(\"This is not the ").append(className)
                        .append(" page. Current page is: \" + driver.getCurrentUrl());\n");
                pomCode.append("        }\n");
            }

            pomCode.append("    }\n\n");

            // Methods for each element based on element type
            for (Map.Entry<String, WebElement> entry : elements.entrySet()) {
                String elementName = formatElementName(entry.getKey());
                String tagName = entry.getValue().getTagName();

                generateMethodsForElement(pomCode, elementName, tagName, entry.getValue());
            }

            // Navigation methods if links to other pages are detected
            generateNavigationMethods(pomCode, elements, packageName);

            // Close class
            pomCode.append("}\n");

            logger.info("Successfully generated POM class with {} elements", elements.size());
            return pomCode.toString();
        }

        /**
         * Formats an element name for use as a Java variable.
         *
         * @param key element identifier
         * @return formatted element name
         */
        private String formatElementName(String key) {
            // Replace non-alphanumeric characters with underscore
            String formatted = key.replaceAll("[^a-zA-Z0-9]", "_");

            // Ensure it's a valid Java identifier
            if (!formatted.isEmpty() && Character.isDigit(formatted.charAt(0))) {
                formatted = "element_" + formatted;
            }

            // Convert to camelCase if it contains underscores
            if (formatted.contains("_")) {
                StringBuilder camelCase = new StringBuilder();
                boolean capitalizeNext = false;

                for (int i = 0; i < formatted.length(); i++) {
                    char c = formatted.charAt(i);
                    if (c == '_') {
                        capitalizeNext = true;
                    } else if (capitalizeNext) {
                        camelCase.append(Character.toUpperCase(c));
                        capitalizeNext = false;
                    } else {
                        camelCase.append(c);
                    }
                }

                formatted = camelCase.toString();
            }

            return formatted;
        }

        /**
         * Determines the locator type based on the element identifier.
         *
         * @param key element identifier
         * @return locator type (id, className, name, tagName, etc.)
         */
        private String determineLocatorType(String key) {
            if (key.startsWith("name_")) {
                return "name";
            } else if (key.contains("_") && !key.startsWith("iframe")) {
                return "className";
            } else if (key.matches("^[a-z]+_\\d+$")) {
                return "tagName";
            } else if (key.startsWith("iframe")) {
                return "xpath";
            } else {
                return "id";
            }
        }

        /**
         * Determines the locator value based on the element identifier and WebElement.
         *
         * @param key element identifier
         * @param element WebElement
         * @return locator value
         */
        private String determineLocatorValue(String key, WebElement element) {
            if (key.startsWith("name_")) {
                return key.substring(5);
            } else if (key.contains("_") && !key.startsWith("iframe")) {
                return key.split("_")[0];
            } else if (key.matches("^[a-z]+_\\d+$")) {
                return key.split("_")[0];
            } else if (key.startsWith("iframe")) {
                // For iframe elements, create an XPath that switches to the iframe
                String[] parts = key.split("_", 2);
                int iframeIndex = Integer.parseInt(parts[0].substring(6));
                return "//iframe[" + (iframeIndex + 1) + "]//" + parts[1];
            } else {
                return key;
            }
        }

        /**
         * Generates methods for an element based on its tag name.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         * @param tagName tag name of the element
         * @param element WebElement
         */
        private void generateMethodsForElement(StringBuilder pomCode, String elementName, String tagName, WebElement element) {
            switch (tagName.toLowerCase()) {
                case "input":
                    String inputType = element.getAttribute("type");
                    if (inputType == null) inputType = "text";

                    switch (inputType.toLowerCase()) {
                        case "text":
                        case "email":
                        case "password":
                        case "search":
                        case "tel":
                        case "url":
                            generateTextInputMethods(pomCode, elementName);
                            break;
                        case "checkbox":
                        case "radio":
                            generateCheckboxMethods(pomCode, elementName);
                            break;
                        case "submit":
                        case "button":
                            generateButtonMethods(pomCode, elementName);
                            break;
                    }
                    break;
                case "button":
                    generateButtonMethods(pomCode, elementName);
                    break;
                case "a":
                    generateLinkMethods(pomCode, elementName);
                    break;
                case "select":
                    generateSelectMethods(pomCode, elementName);
                    break;
                case "textarea":
                    generateTextAreaMethods(pomCode, elementName);
                    break;
                default:
                    generateGenericMethods(pomCode, elementName);
                    break;
            }
        }

        /**
         * Generates methods for text input elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateTextInputMethods(StringBuilder pomCode, String elementName) {
            String methodName = "enter" + capitalize(elementName);

            pomCode.append("    /**\n");
            pomCode.append("     * Enters text into the ").append(elementName).append(" field\n");
            pomCode.append("     * @param text text to enter\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" ").append(methodName).append("(String text) {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        ").append(elementName).append(".clear();\n");
            pomCode.append("        ").append(elementName).append(".sendKeys(text);\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Gets the value of the ").append(elementName).append(" field\n");
            pomCode.append("     * @return current value\n");
            pomCode.append("     */\n");
            pomCode.append("    public String get").append(capitalize(elementName)).append("Value() {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        return ").append(elementName).append(".getAttribute(\"value\");\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates methods for checkbox elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateCheckboxMethods(StringBuilder pomCode, String elementName) {
            pomCode.append("    /**\n");
            pomCode.append("     * Checks the ").append(elementName).append(" checkbox if not already checked\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" check").append(capitalize(elementName)).append("() {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        if (!").append(elementName).append(".isSelected()) {\n");
            pomCode.append("            ").append(elementName).append(".click();\n");
            pomCode.append("        }\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Unchecks the ").append(elementName).append(" checkbox if already checked\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" uncheck").append(capitalize(elementName)).append("() {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        if (").append(elementName).append(".isSelected()) {\n");
            pomCode.append("            ").append(elementName).append(".click();\n");
            pomCode.append("        }\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Checks if the ").append(elementName).append(" checkbox is selected\n");
            pomCode.append("     * @return true if selected, false otherwise\n");
            pomCode.append("     */\n");
            pomCode.append("    public boolean is").append(capitalize(elementName)).append("Selected() {\n");
            pomCode.append("        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(\"").append(elementName).append("\")));\n");
            pomCode.append("        return ").append(elementName).append(".isSelected();\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates methods for button elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateButtonMethods(StringBuilder pomCode, String elementName) {
            pomCode.append("    /**\n");
            pomCode.append("     * Clicks the ").append(elementName).append(" button\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" click").append(capitalize(elementName)).append("() {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        ").append(elementName).append(".click();\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Checks if the ").append(elementName).append(" button is enabled\n");
            pomCode.append("     * @return true if enabled, false otherwise\n");
            pomCode.append("     */\n");
            pomCode.append("    public boolean is").append(capitalize(elementName)).append("Enabled() {\n");
            pomCode.append("        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(\"").append(elementName).append("\")));\n");
            pomCode.append("        return ").append(elementName).append(".isEnabled();\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates methods for link elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateLinkMethods(StringBuilder pomCode, String elementName) {
            pomCode.append("    /**\n");
            pomCode.append("     * Clicks the ").append(elementName).append(" link\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" click").append(capitalize(elementName)).append("Link() {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        ").append(elementName).append(".click();\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Gets the href attribute of the ").append(elementName).append(" link\n");
            pomCode.append("     * @return href attribute value\n");
            pomCode.append("     */\n");
            pomCode.append("    public String get").append(capitalize(elementName)).append("Href() {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        return ").append(elementName).append(".getAttribute(\"href\");\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates methods for select elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateSelectMethods(StringBuilder pomCode, String elementName) {
            pomCode.append("    /**\n");
            pomCode.append("     * Selects an option by visible text from the ").append(elementName).append(" dropdown\n");
            pomCode.append("     * @param text visible text of the option to select\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" select").append(capitalize(elementName)).append("ByText(String text) {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        new org.openqa.selenium.support.ui.Select(").append(elementName).append(").selectByVisibleText(text);\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Selects an option by value from the ").append(elementName).append(" dropdown\n");
            pomCode.append("     * @param value value attribute of the option to select\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" select").append(capitalize(elementName)).append("ByValue(String value) {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        new org.openqa.selenium.support.ui.Select(").append(elementName).append(").selectByValue(value);\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Gets the selected option text from the ").append(elementName).append(" dropdown\n");
            pomCode.append("     * @return text of the selected option\n");
            pomCode.append("     */\n");
            pomCode.append("    public String getSelected").append(capitalize(elementName)).append("Text() {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        return new org.openqa.selenium.support.ui.Select(").append(elementName).append(").getFirstSelectedOption().getText();\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates methods for textarea elements.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateTextAreaMethods(StringBuilder pomCode, String elementName) {
            String methodName = "enter" + capitalize(elementName);

            pomCode.append("    /**\n");
            pomCode.append("     * Enters text into the ").append(elementName).append(" textarea\n");
            pomCode.append("     * @param text text to enter\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" ").append(methodName).append("(String text) {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        ").append(elementName).append(".clear();\n");
            pomCode.append("        ").append(elementName).append(".sendKeys(text);\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Gets the text from the ").append(elementName).append(" textarea\n");
            pomCode.append("     * @return current text\n");
            pomCode.append("     */\n");
            pomCode.append("    public String get").append(capitalize(elementName)).append("Text() {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        return ").append(elementName).append(".getText();\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates generic methods for other element types.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elementName element name
         */
        private void generateGenericMethods(StringBuilder pomCode, String elementName) {
            pomCode.append("    /**\n");
            pomCode.append("     * Clicks the ").append(elementName).append(" element\n");
            pomCode.append("     * @return this page object\n");
            pomCode.append("     */\n");
            pomCode.append("    public ").append(getClassName(pomCode)).append(" click").append(capitalize(elementName)).append("() {\n");
            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
            pomCode.append("        ").append(elementName).append(".click();\n");
            pomCode.append("        return this;\n");
            pomCode.append("    }\n\n");

            pomCode.append("    /**\n");
            pomCode.append("     * Gets the text of the ").append(elementName).append(" element\n");
            pomCode.append("     * @return element text\n");
            pomCode.append("     */\n");
            pomCode.append("    public String get").append(capitalize(elementName)).append("Text() {\n");
            pomCode.append("        wait.until(ExpectedConditions.visibilityOf(").append(elementName).append("));\n");
            pomCode.append("        return ").append(elementName).append(".getText();\n");
            pomCode.append("    }\n\n");
        }

        /**
         * Generates navigation methods for links to other pages.
         *
         * @param pomCode StringBuilder to append methods to
         * @param elements map of element identifiers to WebElements
         * @param packageName package name for the class
         */
        private void generateNavigationMethods(StringBuilder pomCode, Map<String, WebElement> elements, String packageName) {
            // Find link elements that might navigate to other pages
            for (Map.Entry<String, WebElement> entry : elements.entrySet()) {
                if ("a".equalsIgnoreCase(entry.getValue().getTagName())) {
                    try {
                        String href = entry.getValue().getAttribute("href");
                        if (href != null && !href.isEmpty() && !href.startsWith("javascript") && !href.equals("#")) {
                            String elementName = formatElementName(entry.getKey());
                            String targetClassName = determineTargetClassName(href);

                            pomCode.append("    /**\n");
                            pomCode.append("     * Navigates to ").append(targetClassName).append(" by clicking the ").append(elementName).append(" link\n");
                            pomCode.append("     * @return new page object\n");
                            pomCode.append("     */\n");
                            pomCode.append("    public ").append(targetClassName).append(" navigateTo").append(targetClassName).append("() {\n");
                            pomCode.append("        wait.until(ExpectedConditions.elementToBeClickable(").append(elementName).append("));\n");
                            pomCode.append("        ").append(elementName).append(".click();\n");
                            pomCode.append("        return new ").append(targetClassName).append("(driver);\n");
                            pomCode.append("    }\n\n");
                        }
                    } catch (Exception e) {
                        // Skip this element if we can't get its href
                        logger.debug("Could not get href for element: {}", entry.getKey());
                    }
                }
            }
        }

        /**
         * Determines the target class name from a URL.
         *
         * @param url URL to analyze
         * @return target class name
         */
        private String determineTargetClassName(String url) {
            try {
                URL parsedUrl = new URL(url);
                String path = parsedUrl.getPath();

                // Remove leading and trailing slashes
                path = path.replaceAll("^/|/$", "");

                if (path.isEmpty()) {
                    return "HomePage";
                }

                // Split path into segments
                String[] segments = path.split("/");
                String lastSegment = segments[segments.length - 1];

                // Remove file extension if present
                lastSegment = lastSegment.replaceAll("\\.[^.]*$", "");

                // Convert to camel case
                StringBuilder className = new StringBuilder();
                for (String part : lastSegment.split("[^a-zA-Z0-9]")) {
                    if (!part.isEmpty()) {
                        className.append(capitalize(part));
                    }
                }

                if (className.length() == 0) {
                    className.append("Page").append(segments.length);
                }

                className.append("Page");
                return className.toString();
            } catch (Exception e) {
                // If URL parsing fails, use a generic name
                return "NextPage";
            }
        }

        /**
         * Capitalizes the first letter of a string.
         *
         * @param str string to capitalize
         * @return capitalized string
         */
        private String capitalize(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }

        /**
         * Extracts the class name from the POM code.
         *
         * @param pomCode StringBuilder containing the POM code
         * @return class name
         */
        private String getClassName(StringBuilder pomCode) {
            String code = pomCode.toString();
            int classIndex = code.lastIndexOf("public class ");
            if (classIndex == -1) {
                return "Page";
            }

            int startIndex = classIndex + "public class ".length();
            int endIndex = code.indexOf(" ", startIndex);
            if (endIndex == -1) {
                endIndex = code.indexOf("{", startIndex);
            }

            return code.substring(startIndex, endIndex);
        }
    }

    /**
     * Class for analyzing URL paths.
     */
    private static class PathAnalyzer {
        private final Logger logger = LoggerFactory.getLogger(PathAnalyzer.class);

        /**
         * Gets a class name from a URL path.
         *
         * @param url URL to analyze
         * @return class name
         */
        public String getPageClassNameFromPath(String url) {
            logger.info("Generating class name from URL: {}", url);
            try {
                URL parsedUrl = new URL(url);
                String path = parsedUrl.getPath();

                // Remove leading and trailing slashes
                path = path.replaceAll("^/|/$", "");

                if (path.isEmpty()) {
                    return "HomePage";
                }

                // Split path into segments
                String[] segments = path.split("/");
                String lastSegment = segments[segments.length - 1];

                // Remove file extension if present
                lastSegment = lastSegment.replaceAll("\\.[^.]*$", "");

                // Convert to camel case
                StringBuilder className = new StringBuilder();
                for (String part : lastSegment.split("[^a-zA-Z0-9]")) {
                    if (!part.isEmpty()) {
                        className.append(capitalize(part));
                    }
                }

                if (className.length() == 0) {
                    className.append("Page").append(segments.length);
                }

                className.append("Page");
                logger.info("Generated class name: {}", className);
                return className.toString();
            } catch (Exception e) {
                logger.error("Error generating class name: {}", e.getMessage(), e);
                return "DefaultPage";
            }
        }

        /**
         * Gets a package name from a URL path.
         *
         * @param url URL to analyze
         * @return package name
         */
        public String getPackageNameFromPath(String url) {
            logger.info("Generating package name from URL: {}", url);
            try {
                URL parsedUrl = new URL(url);
                String host = parsedUrl.getHost();
                String path = parsedUrl.getPath();

                // Remove leading and trailing slashes
                path = path.replaceAll("^/|/$", "");

                // Build package name from host (reversed) and path
                StringBuilder packageName = new StringBuilder("pages");

                // Add reversed host components
                String[] hostParts = host.split("\\.");
                for (int i = hostParts.length - 1; i >= 0; i--) {
                    String part = hostParts[i];
                    if (!part.isEmpty()) {
                        packageName.append(".").append(part.replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                    }
                }

                // Add path components
                if (!path.isEmpty()) {
                    String[] pathParts = path.split("/");
                    // Only use the first few path segments to avoid overly long package names
                    int maxSegments = Math.min(pathParts.length, 2);
                    for (int i = 0; i < maxSegments; i++) {
                        String part = pathParts[i];
                        if (!part.isEmpty()) {
                            packageName.append(".").append(part.replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                        }
                    }
                }

                logger.info("Generated package name: {}", packageName);
                return packageName.toString();
            } catch (Exception e) {
                logger.error("Error generating package name: {}", e.getMessage(), e);
                return "pages.default";
            }
        }

        /**
         * Discovers all paths on a website.
         *
         * @param driver WebDriver instance
         * @param baseUrl base URL of the website
         * @param maxDepth maximum crawl depth
         * @return set of discovered paths
         */
        public Set<String> discoverPaths(WebDriver driver, String baseUrl, int maxDepth) {
            logger.info("Discovering paths on {} with max depth {}", baseUrl, maxDepth);
            Set<String> discoveredPaths = new HashSet<>();
            Set<String> visitedUrls = new HashSet<>();
            Queue<UrlDepthPair> urlQueue = new LinkedList<>();

            // Start with the base URL
            urlQueue.add(new UrlDepthPair(baseUrl, 0));
            visitedUrls.add(baseUrl);
            discoveredPaths.add("/");

            while (!urlQueue.isEmpty()) {
                UrlDepthPair current = urlQueue.poll();
                String currentUrl = current.url;
                int currentDepth = current.depth;

                if (currentDepth >= maxDepth) {
                    continue;
                }

                try {
                    driver.get(currentUrl);
                    // Wait for page to load
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

                    // Find all links on the page
                    List<WebElement> links = driver.findElements(By.tagName("a"));
                    for (WebElement link : links) {
                        try {
                            String href = link.getAttribute("href");
                            if (href == null || href.isEmpty() || href.startsWith("javascript:") || href.equals("#")) {
                                continue;
                            }

                            // Only process links from the same domain
                            if (href.startsWith(baseUrl)) {
                                String path = new URL(href).getPath();
                                if (!discoveredPaths.contains(path)) {
                                    discoveredPaths.add(path);
                                }

                                if (!visitedUrls.contains(href)) {
                                    urlQueue.add(new UrlDepthPair(href, currentDepth + 1));
                                    visitedUrls.add(href);
                                }
                            }
                        } catch (Exception e) {
                            logger.debug("Error processing link: {}", e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    logger.warn("Error visiting URL {}: {}", currentUrl, e.getMessage());
                }
            }

            logger.info("Discovered {} paths", discoveredPaths.size());
            return discoveredPaths;
        }

        /**
         * Helper class for URL and depth pairs.
         */
        private static class UrlDepthPair {
            public final String url;
            public final int depth;

            public UrlDepthPair(String url, int depth) {
                this.url = url;
                this.depth = depth;
            }
        }

        /**
         * Capitalizes the first letter of a string.
         *
         * @param str string to capitalize
         * @return capitalized string
         */
        private String capitalize(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }

    /**
     * Class for handling popups.
     */
    private static class PopupParser {
        private final WebDriver driver;
        private final String popupId;
        private final WebDriverWait wait;
        private final Logger logger = LoggerFactory.getLogger(PopupParser.class);

        /**
         * Creates a new PopupParser.
         *
         * @param driver WebDriver instance
         * @param popupId ID of the popup element
         * @param wait WebDriverWait instance
         */
        public PopupParser(WebDriver driver, String popupId, WebDriverWait wait) {
            this.driver = driver;
            this.popupId = popupId;
            this.wait = wait;
            logger.info("PopupParser initialized for popup with ID: {}", popupId);
        }

        /**
         * Parses the popup to collect elements.
         *
         * @return map of element identifiers to WebElements
         */
        public Map<String, WebElement> parse() {
            logger.info("Parsing popup with ID: {}", popupId);
            Map<String, WebElement> popupElements = new ConcurrentHashMap<>();

            try {
                WebElement popupElement = driver.findElement(By.id(popupId));
                wait.until(ExpectedConditions.visibilityOf(popupElement));

                // Find all elements within the popup
                List<WebElement> elements = popupElement.findElements(By.xpath(".//*"));

                for (WebElement element : elements) {
                    try {
                        String id = element.getAttribute("id");
                        String className = element.getAttribute("class");
                        String tagName = element.getTagName();

                        if (id != null && !id.isEmpty()) {
                            popupElements.put(id, element);
                        } else if (className != null && !className.isEmpty()) {
                            popupElements.put(className + "_" + tagName, element);
                        }
                    } catch (StaleElementReferenceException e) {
                        logger.debug("Stale element encountered during popup parsing");
                    }
                }

                logger.info("Successfully parsed popup, collected {} elements", popupElements.size());
            } catch (Exception e) {
                logger.error("Error parsing popup: {}", e.getMessage(), e);
                throw new PageParsingException("Failed to parse popup", e);
            }

            return popupElements;
        }
    }

    /**
     * Custom exception for page parsing errors.
     */
    public static class PageParsingException extends RuntimeException {
        /**
         * Creates a new PageParsingException.
         *
         * @param message error message
         * @param cause cause of the error
         */
        public PageParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Custom exception for POM generation errors.
     */
    public static class POMGenerationException extends RuntimeException {
        /**
         * Creates a new POMGenerationException.
         *
         * @param message error message
         * @param cause cause of the error
         */
        public POMGenerationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Custom exception for cookie operation errors.
     */
    public static class CookieOperationException extends RuntimeException {
        /**
         * Creates a new CookieOperationException.
         *
         * @param message error message
         * @param cause cause of the error
         */
        public CookieOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

/**
 * Class for managing cookies.
 */
class CookiesManager {
    private static final Logger logger = LoggerFactory.getLogger(CookiesManager.class);
    private final WebDriver driver;

    /**
     * Creates a new CookiesManager.
     *
     * @param driver WebDriver instance
     */
    public CookiesManager(WebDriver driver) {
        this.driver = driver;
        logger.info("CookiesManager initialized");
    }

    /**
     * Saves cookies to a file.
     *
     * @param filename file to save cookies to
     * @throws IOException if saving fails
     */
    public void saveCookiesToFile(String filename) throws IOException {
        logger.info("Saving cookies to file: {}", filename);
        File file = new File(filename);
        file.createNewFile();

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            Set<Cookie> cookies = driver.manage().getCookies();
            for (Cookie cookie : cookies) {
                bufferedWriter.write(cookie.getName() + ";" +
                        cookie.getValue() + ";" +
                        cookie.getDomain() + ";" +
                        cookie.getPath() + ";" +
                        cookie.getExpiry() + ";" +
                        cookie.isSecure());
                bufferedWriter.newLine();
            }

            logger.info("Successfully saved {} cookies to file", cookies.size());
        } catch (Exception e) {
            logger.error("Error saving cookies to file: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Loads cookies from a file.
     *
     * @param filename file to load cookies from
     * @throws IOException if loading fails
     */
    public void loadCookiesFromFile(String filename) throws IOException {
        logger.info("Loading cookies from file: {}", filename);
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                String name = tokenizer.nextToken();
                String value = tokenizer.nextToken();
                String domain = tokenizer.nextToken();
                String path = tokenizer.nextToken();
                Date expiry = null;
                String expiryString = tokenizer.nextToken();
                if (!expiryString.equals("null")) {
                    expiry = new Date(Long.parseLong(expiryString));
                }
                boolean isSecure = Boolean.parseBoolean(tokenizer.nextToken());

                Cookie cookie = new Cookie(name, value, domain, path, expiry, isSecure);
                driver.manage().addCookie(cookie);
                count++;
            }

            logger.info("Successfully loaded {} cookies from file", count);
        } catch (Exception e) {
            logger.error("Error loading cookies from file: {}", e.getMessage(), e);
            throw e;
        }
    }
}
