package school.redrover;

import net.datafaker.Faker;
import net.datafaker.providers.base.Text;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.SECONDS;
import static net.datafaker.providers.base.Text.*;
import static org.testng.Assert.*;

public class GroupCodeCraftTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 768));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSwagLabs() {
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        WebElement removeBackpack = driver.findElement
                (By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory ']"));
        String color = removeBackpack.getCssValue("border");
        int rgbIndex = color.indexOf("rgb");
        String removeBackpackText = removeBackpack.getText();

        WebElement cart = driver.findElement
                (By.xpath("//span[@class='shopping_cart_badge']"));

        driver.findElement
                (By.xpath("//button[@id='add-to-cart-sauce-labs-fleece-jacket']"))
                .click();
        String cartShopNumbers = cart.getText();

        cart.click();

        WebElement firstItem = driver.findElement
                (By.xpath("//a[@id='item_4_title_link']/div"));
        String firstItemText = firstItem.getText();
        WebElement secondItem = driver.findElement
                (By.xpath("//a[@id='item_5_title_link']/div"));
        String secondItemText = secondItem.getText();

        String firstPrice = driver.findElement
                        (By.xpath("//a[@id='item_4_title_link']/following-sibling::div[2]/div")).
                getText().substring(1);
        double firstPriceDouble = Double.parseDouble(firstPrice);

        String secondString = driver.findElement
                        (By.xpath("//a[@id='item_5_title_link']/following-sibling::div[2]/div")).
                getText().substring(1);
        double secondPriceDouble = Double.parseDouble(secondString);

        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        driver.findElement(By.xpath("//input[@id='first-name']")).
                sendKeys("Jane");
        driver.findElement(By.xpath("//input[@id='last-name']")).
                sendKeys("Doe");
        driver.findElement(By.xpath("//input[@id='postal-code']")).
                sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='continue']")).
                click();

        WebElement shippingInformation = driver.findElement
                (By.xpath("//div[@data-test='shipping-info-value']"));
        String shippingInformationText = shippingInformation.getText();

        double taxSum = (firstPriceDouble + secondPriceDouble) * 0.08;
        double totalPriceWithTax = firstPriceDouble + secondPriceDouble + taxSum;
        double totalPriceWithoutTax = firstPriceDouble + secondPriceDouble;

        double roundedTax = Math.round(taxSum * 100.0) / 100.0;
        double roundedPriceWithTax = Math.round(totalPriceWithTax * 100.0) / 100.0;
        double roundedPriceWithoutTax = Math.round(totalPriceWithoutTax * 100.0) / 100.0;

        WebElement itemTotal = driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"));
        WebElement tax = driver.findElement(By.xpath("//div[@class='summary_tax_label']"));
        WebElement total = driver.findElement(By.xpath("//div[@data-test='total-label']"));
        String itemTotalText = itemTotal.getText();
        String taxText = tax.getText();
        String totalText = total.getText();

        driver.findElement(By.xpath("//button[@id='finish']")).
                click();
        driver.findElement(By.xpath("//button[@id='back-to-products']")).
                click();

        WebElement swagLabsPageLogo = driver.findElement
                (By.xpath("//div[@class='app_logo']"));

        assertEquals(removeBackpackText, "Remove");
        assertEquals(color.substring(rgbIndex), "rgb(226, 35, 26)");
        assertEquals(cartShopNumbers, "2");
        assertEquals(firstItemText, "Sauce Labs Backpack");
        assertEquals(secondItemText, "Sauce Labs Fleece Jacket");
        assertEquals(firstPrice, "29.99");
        assertEquals(secondString, "49.99");
        assertEquals(shippingInformationText, "Free Pony Express Delivery!");
        assertEquals(itemTotalText, "Item total: $" + roundedPriceWithoutTax);
        assertEquals(taxText, "Tax: $" + roundedTax + "0");
        assertEquals(totalText, "Total: $" + roundedPriceWithTax);
        assertEquals(swagLabsPageLogo.getText(), "Swag Labs");
    }


    @Test
    public void testToolsQATestBox() {
        driver.get("https://demoqa.com");

        driver.findElement
                (By.xpath("//div[1][@class='card mt-4 top-card']")).click();
        driver.findElement
                (By.xpath("//div[@class='element-list collapse show']/descendant::li[@id='item-0']"))
                .click();

        String fullName = "Jane Doe";
        String eMail = "janedoe@yahoo.com";
        String currentAddress = "Pushkin's street, Kolotushkin's house";
        String permAddress = "Same as current";

        driver.findElement(By.xpath("//input[@placeholder='Full Name']"))
                .sendKeys(fullName);
        driver.findElement(By.xpath("//input[@id='userEmail']"))
                .sendKeys(eMail);
        driver.findElement(By.xpath("//textarea[@id='currentAddress']"))
                .sendKeys(currentAddress);
        driver.findElement(By.xpath("//textarea[@id='permanentAddress']"))
                .sendKeys(permAddress);

        WebElement submit = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submit']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.click();

        WebElement fullNameWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='name']")));
        WebElement eMailWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='email']")));
        WebElement currentAddressWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='currentAddress']")));
        WebElement permAddressWeb = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='permanentAddress']")));

        assertEquals(fullNameWeb.getText(), "Name:" + fullName);
        assertEquals(eMailWeb.getText(), "Email:" + eMail);
        assertEquals(currentAddressWeb.getText(), "Current Address :" + currentAddress);
        assertEquals(permAddressWeb.getText(), "Permananet Address :" + permAddress);
    }

    @Test
    public void testWebForm() {
        var faker = new Faker(new Locale("en"));

        String fullName = faker.name().fullName();
        String password = faker.internet().password();
        String text = faker.lorem().sentence();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement name = driver.findElement(By.id("my-text-id"));
        name.sendKeys(fullName);

        WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
        passwordField.sendKeys(password);

        WebElement textArea = driver.findElement(By.tagName("textarea"));
        textArea.sendKeys(text);

        assertEquals(name.getDomProperty("value"), fullName);
        assertEquals(passwordField.getDomProperty("value"), password);
        assertEquals(textArea.getDomProperty("value"), text);
        assertFalse(driver.findElement
                (By.xpath("//input[@name='my-disabled']")).isEnabled());
        assertNotNull(driver.findElement
                        (By.xpath("//input[@name='my-readonly']")).
                getDomAttribute("readonly"));
    }

    @Test
    public void testWebFormSelect() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement dropDownMenu = driver.findElement
                (By.xpath("//select[@name='my-select']"));

        Select select = new Select(dropDownMenu);

        select.selectByContainsVisibleText("Open this select menu");
        String firstCheck = dropDownMenu.getDomProperty("value");

        select.selectByValue("1");
        String secondCheck = dropDownMenu.getDomProperty("value");

        select.selectByIndex(2);
        String thirdCheck = dropDownMenu.getDomProperty("value");

        select.selectByContainsVisibleText("Three");
        String fourthCheck = dropDownMenu.getDomProperty("value");

        assertEquals(firstCheck, "Open this select menu");
        assertEquals(secondCheck, "1");
        assertEquals(thirdCheck, "2");
        assertEquals(fourthCheck, "3");
    }

    @Test
    public void testCheckboxAndFileInput() throws IOException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement fileInput = driver.findElement(By.xpath("//input[@name='my-file']"));

        File tempFile = File.createTempFile("temp", ".txt");
        FileUtils.writeStringToFile(tempFile, "This is a test file content.", "UTF-8");
        fileInput.sendKeys(tempFile.getAbsolutePath());
        tempFile.deleteOnExit();

        WebElement checkedCheckbox = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-check-1']")));
        WebElement defaultCheckbox = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-check-2']")));
        WebElement checkedRadio = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-radio-1']")));
        WebElement defaultRadio = wait.until
                (ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='my-radio-2']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", defaultRadio);

        checkedCheckbox.click();
        defaultCheckbox.click();
        defaultRadio.click();
        checkedRadio.click();

        assertFalse(checkedCheckbox.isSelected());
        assertTrue(defaultCheckbox.isSelected());
        assertTrue(checkedRadio.isSelected());
        assertFalse(defaultRadio.isSelected());
        assertFalse(Objects.requireNonNull(fileInput.getDomProperty("value")).isEmpty());
    }

    @Test
    public void testLumaCreateAccount() throws InterruptedException {
        var faker = new Faker(new Locale("en"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName.toLowerCase() + lastName.toLowerCase() + "@gmail.com";
        String password = faker.text().text(Text.TextSymbolsBuilder.builder()
                .len(14)
                .with(EN_UPPERCASE, 3)
                .with(EN_LOWERCASE, 4)
                .with(DEFAULT_SPECIAL, 2)
                .with(DIGITS, 3).build());

        driver.get("https://magento.softwaretestingboard.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement acceptCookies = driver.findElement(By.className("css-1n36tvh"));
        if (acceptCookies.isDisplayed()) {
            acceptCookies.click();
        }
        WebElement createAccount = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div[1]/div/ul/li[3]/a")));
        createAccount.click();

        WebElement firstname = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.id("firstname")));
        firstname.sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password-confirmation")).sendKeys(password);
        driver.findElement(By.cssSelector(".action.submit.primary")).click();

        WebElement accountCreated = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div")));
        assertEquals(accountCreated.getText(), "Thank you for registering with Main Website Store.");
    }

    @Test
    public void demoQAFormTest() throws InterruptedException {
        //заходим на сайт
        driver.get("https://demoqa.com");

        //выбираем кнопку по её xPath и нажимаем
        WebElement elementButton = driver.findElement(By.xpath(("//*[@id=\"app\"]/div/div/div[2]/div/div[1]/div/div[2]")));
        elementButton.click();

        Thread.sleep(1000); //подождем немного

        WebElement webTables = driver.findElement(By.xpath("//*[@id=\"item-3\"]/span"));
        webTables.click();

        WebElement addButton = driver.findElement(By.id("addNewRecordButton"));
        addButton.click();

        //заполняем поля формы по очереди, ищем их по id
        WebElement inputFirstName = driver.findElement(By.id("firstName"));
        inputFirstName.sendKeys("Emiel Regis Rohellec");

        WebElement inputLastName = driver.findElement(By.id("lastName"));
        inputLastName.sendKeys("Terzieff-Godefroy");

        WebElement inputEmail = driver.findElement(By.id("userEmail"));
        inputEmail.sendKeys("garlic@forever.va");

        WebElement inputAge = driver.findElement(By.id("age"));
        inputAge.sendKeys("99");

        WebElement inputSalary = driver.findElement(By.id("salary"));
        inputSalary.sendKeys("50000");

        WebElement inputDepartment = driver.findElement(By.id("department"));
        inputDepartment.sendKeys("Geralt's company");

        //отправляем данные в форму
        WebElement submitButton1 = driver.findElement(By.id("submit"));
        submitButton1.click();

        //получаем текстовые данные из последней строки таблицы
        WebElement tableName4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[1]"));
        String addedName4 = tableName4.getText();

        WebElement tableLastName4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[2]"));
        String addedLastName4 = tableLastName4.getText();

        WebElement tableEmail4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[4]"));
        String addedEmail4 = tableEmail4.getText();

        WebElement tableAge4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[3]"));
        String addedAge4 = tableAge4.getText();

        WebElement tableSalary4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[5]"));
        String addedSalary4 = tableSalary4.getText();

        WebElement tableDepartment4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[6]"));
        String addedDepartment4 = tableDepartment4.getText();

        //проверяем соответствие сохранённых данных введённым ранее
        Assert.assertEquals(addedName4, "Emiel Regis Rohellec");
        Assert.assertEquals(addedLastName4, "Terzieff-Godefroy");
        Assert.assertEquals(addedEmail4, "garlic@forever.va");
        Assert.assertEquals(addedAge4, "99");
        Assert.assertEquals(addedSalary4, "50000");
        Assert.assertEquals(addedDepartment4, "Geralt's company");

        //изменяем данные имеющейся строки
        WebElement changeButton = driver.findElement(By.id("edit-record-3"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", changeButton);
        changeButton.click();

        WebElement editFirstName = driver.findElement(By.id("firstName"));
        editFirstName.clear();
        editFirstName.sendKeys("Jane");
        WebElement submitButton2 = driver.findElement(By.id("submit"));
        submitButton2.click();

        //проверяем, что данные изменились
        WebElement tableName3 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[3]/div/div[1]"));
        String addedName3 = tableName3.getText();
        Assert.assertEquals(addedName3, "Jane");

        //удаляем созданную ранее строку
        WebElement deleteButton = driver.findElement(By.id("delete-record-4"));
        deleteButton.click();

        //проверяем что строка пустая
        tableName4 = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[1]"));
        addedName4 = tableName4.getText();
        Assert.assertEquals(addedName4, " ");
    }

    @Test
    public void testDemoQa() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");

        WebElement buttForms = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div/div[2]/div/div[1]"));
        buttForms.click();
        Thread.sleep(400);

        WebElement buttElements = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[1]/span/div/div[1]"));
        buttElements.click();
        Thread.sleep(200);

        WebElement textBox = driver.findElement(By.xpath("//*[@id=\"item-0\"]/span"));
        textBox.click();

        WebElement fullNameBox = driver.findElement(By.xpath("//*[@id=\"userName\"]"));
        fullNameBox.click();
        fullNameBox.sendKeys("Ivan Ivanov");

        WebElement emailBox = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[2]/div[2]/input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", emailBox);
        emailBox.click();
        emailBox.sendKeys("ivanovich@yahoo.com");

        WebElement adresBox = driver.findElement(By.xpath("//*[@id=\"currentAddress\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", adresBox);
        adresBox.click();
        adresBox.sendKeys("Улица пушкина, Город Колотушкино");

        WebElement perAdresBox = driver.findElement(By.xpath("//*[@id=\"permanentAddress\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", perAdresBox);
        perAdresBox.click();
        perAdresBox.sendKeys("Темная");

        WebElement submitBox = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[5]/div/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBox);
        submitBox.click();
        Thread.sleep(300);

        WebElement answerName = driver.findElement(By.xpath("//div/p[text()=\"Ivan Ivanov\"]"));
        String getName = answerName.getText();
        Assert.assertEquals(getName, "Name:Ivan Ivanov");

        WebElement answerEmail = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[6]/div/p[2]"));
        String getEmail = answerEmail.getText();
        Assert.assertEquals(getEmail, "Email:ivanovich@yahoo.com");

        driver.quit();
    }

    @Test
    public void testBonigarciaWebFormXpath() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.findElement(By.xpath("//div/a[@href='web-form.html']")).click();
        WebElement header = driver.findElement(By.xpath("//h1[@class='display-6']"));
        String headerText = header.getText();

        Assert.assertEquals(headerText, "Web form");

        WebElement textInput = driver.findElement(By.xpath("//input[@name='my-text']"));
        textInput.sendKeys("Adelya");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='my-password']"));
        passwordInput.sendKeys("12345678");

        WebElement textareaInput = driver.findElement(By.xpath("//textarea[@name='my-textarea']"));
        textareaInput.sendKeys("something important");

        WebElement disabledInput = driver.findElement(By.xpath("//input[@placeholder='Disabled input']"));
        assertFalse(disabledInput.isEnabled());

        WebElement dropDownSelect = driver.findElement(By.xpath("//select"));
        Select openThisSelectMenu = new Select(dropDownSelect);
        openThisSelectMenu.selectByVisibleText("Three");

        WebElement readonlyInput = driver.findElement(By.xpath("//input[@value='Readonly input']"));
        String readonlyText = readonlyInput.getAttribute("value");
        Assert.assertEquals(readonlyText, "Readonly input");

        WebElement DropdownDataList = driver.findElement(By.xpath("//input[@list='my-options']"));
        DropdownDataList.sendKeys("Saint-Petersburg");

        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));
        String relativeFilePath = "src/uploadFiles/java.png";
        File fileToUpload = new File(relativeFilePath);
        String absoluteFilePath = fileToUpload.getAbsolutePath();
        fileInput.sendKeys(absoluteFilePath);

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        WebElement formSubmitted = driver.findElement(By.xpath("//h1[text()='Form submitted']"));

        assertTrue(formSubmitted.isDisplayed(), "Verifying if the confirmation page is displayed");

    }

    @Test
    public void LearningEnglish() throws InterruptedException {
        WebDriver driver = new ChromeDriver();


        driver.get("https://learningenglish.voanews.com/p/5610.html");


        driver.findElement(By.xpath("//*[@id=\"page\"]/div[1]/div/div/div[1]/label[2]")).click();


        WebElement textBox = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[1]/div[2]/div/form/input"));
        textBox.sendKeys("learning english");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"form-topSearchHeader\"]/button"));
        searchButton.click();

        Thread.sleep(1000);

        WebElement title = driver.findElement(By.xpath("//*[@id=\"search-results\"]/div[2]/div/ul/li[1]/div/div/a/h4"));
        String titleText = title.getText();

        assertEquals(titleText, "Learning English Podcast");

        driver.quit();
    }

    @Test
    public void testSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://krasivoe.by/");

        WebElement input = driver.findElement(By.xpath("//*[@id=\"search\"]/input"));
        input.click();
        input.sendKeys("браслет");

        Thread.sleep(1000);

        WebElement button = driver.findElement(By.xpath("//*[@id=\"search\"]/button"));
        button.click();

        WebElement cite = driver.findElement(By.xpath("//*[@id=\"mfilter-content-container\"]/h1"));
        String citeText = cite.getText();

        Assert.assertEquals(citeText, "ПОИСК - БРАСЛЕТ");


        driver.quit();
    }

    @Test
    public void testClickAndNewWindowTitle() throws InterruptedException {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        Thread.sleep(1000);

        driver.findElement(By.tagName("button")).click();
        Thread.sleep(1000);

        String newWindowTitle = driver.findElement(By.className("display-6")).getText();

        Assert.assertEquals(newWindowTitle, "Form submitted");
    }

    @Test
    public void testSelectSimpleDropDown() throws InterruptedException {
        driver.get("https://www.selenium.dev/selenium/web/selectPage.html");
        WebElement selectWithoutMultiple = driver.findElement(By.id("selectWithoutMultiple"));
        Thread.sleep(1000);

        Select simpleDropDown = new Select(selectWithoutMultiple);
        simpleDropDown.selectByValue("two");
        Thread.sleep(1000);

        String value = selectWithoutMultiple.getAttribute("value");

        Assert.assertEquals(value, "two");
    }

    @Test
    public void testLongList() throws InterruptedException {
        driver.get("https://www.selenium.dev/selenium/web/selectPage.html");
        Thread.sleep(1000);
        WebElement selectElement = driver.findElement(By.id("selectWithMultipleLongList"));

        Select select = new Select(selectElement);
        select.selectByVisibleText("six");
        Thread.sleep(1000);

        String value = selectElement.getAttribute("value");

        Assert.assertEquals(value, "six");
    }

    @Test
    public void testDQARadioButton() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com");

        WebElement firstBlock =
                driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div/div[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstBlock);
        Thread.sleep(500);
        firstBlock.click();

        Thread.sleep(500);
        WebElement sButton = driver.findElement(By.id("item-2"));
        sButton.click();

        Thread.sleep(500);
        WebElement radioButton = new WebDriverWait(driver, Duration.of(10, SECONDS))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='impressiveRadio']")));
        radioButton.click();

        Thread.sleep(500);
        WebElement message = driver.findElement(By.xpath("//div[2]/div[2]/p/span"));
        String value = message.getText();
        Assert.assertEquals(value, "Impressive");

        driver.quit();
    }

    @Test
    public void testBGDropDown() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/index.html");

        WebElement dropDown =
                driver.findElement(By.xpath("//div[1]/div/div/a[3]"));
        dropDown.click();

        Thread.sleep(500);
        WebElement sButton = driver.findElement(By.id("my-dropdown-1"));
        sButton.click();

        Thread.sleep(500);
        WebElement text = driver.findElement(By.xpath("//div[1]/div/ul/li[3]/a"));
        String value = text.getText();
        Assert.assertEquals(value, "Something else here");

        driver.quit();
    }

    @Test
    public void inputArrowUpTest() throws InterruptedException {
        driver.get("http://the-internet.herokuapp.com/inputs");
        WebElement inputField = driver.findElement(By.xpath("//input[@type='number']"));
        inputField.sendKeys(Keys.ARROW_UP);
        String fieldNumber = inputField.getAttribute("value");
        Assert.assertEquals(fieldNumber, "1");
    }

    @Test
    public void inputArrowDownTest() {
        driver.get("http://the-internet.herokuapp.com/inputs");
        WebElement inputField = driver.findElement(By.xpath("//input[@type='number']"));
        inputField.sendKeys(Keys.ARROW_DOWN);
        String fieldNumber = inputField.getAttribute("value");
        Assert.assertEquals(fieldNumber, "-1");
    }
}