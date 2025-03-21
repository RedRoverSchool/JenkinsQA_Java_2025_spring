package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupAQARookiesTest {

    @Test
    public void findIphoneInOnlinerCatalog() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("https://www.onliner.by/");
        Assert.assertEquals(driver.getTitle(), "Onlíner");

        driver.findElement(By.xpath("//*[@id='fast-search']/div/input")).sendKeys("Iphone");
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.modal-iframe")));
        Thread.sleep(2000);

        driver.findElement(By.xpath("//a[contains(text(), 'Телефон Apple iPhone 16e 128GB (белый)')]")).click();
        Assert.assertEquals(driver.getTitle(), "iPhone 16e 128GB белый (Айфон 16е) купить в Минске");

        driver.quit();
    }

    @Test
    public void testDuckDuckGo() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://duckduckgo.com/");
        driver.findElement(By.id("searchbox_input")).sendKeys("Selenium");
        driver.findElement(By.cssSelector("button[aria-label='Search']")).click();

        Thread.sleep(1000);

        String value = driver.findElement(By.xpath(
                "//a[@href='https://www.selenium.dev/'][@data-testid='result-extras-url-link']/div/p/span")).getText();

        driver.quit();
        Assert.assertEquals(value, "https://www.selenium.dev");
    }

    @Test
    public void testAddProductToTheCart() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://theweldercatherine.ru/");

        driver.findElement(By.xpath("//a[@title='Порционный горячий шоколад \"Гала-Мокко\" The Welder Catherine & UNICAVA']")).click();
        driver.findElement(By.id("on_cart")).click();
        Thread.sleep(1000);

        if (!driver.findElements(By.className("modal-min-order")).isEmpty()) {
            new Actions(driver).moveByOffset(300, 400).click().perform();
        }

        driver.findElement(By.className("go-to-cart")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='bx-soa-item-title']/a")).getText(), "Порционный горячий шоколад \"Гала-Мокко\" The Welder Catherine & UNICAVA");

        driver.quit();
    }

    @Test
    public void testBookOldFarmhouse() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationintesting.online/");
        Thread.sleep(500);

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Vasiliy");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("qwerty@mailto.ru");
        driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("+345456789234");
        driver.findElement(By.cssSelector("input#subject")).sendKeys("The Old Farmhouse, Shady Street, Newfordburyshire, NE1 410S");
        driver.findElement(By.cssSelector("textarea.form-control")).sendKeys("Hello! I and my family, we want to book your house.");
        driver.findElement(By.xpath("//button[@id='submitContact']")).click();
        Thread.sleep(500);

        String heading = driver.findElement(By.xpath("//h2[contains(text(),'Thanks for getting in touch')]")).getText();
        driver.quit();

        Assert.assertEquals(heading, "Thanks for getting in touch Vasiliy!");
    }

    @Test
    public void testSelenium() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        Assert.assertEquals(title, "Web form");

        WebElement textBox = driver.findElement(By.xpath("//*[@name = 'my-textarea']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        Thread.sleep(1000);

        textBox.sendKeys("Привет, я автотест");
        submitButton.click();

        Thread.sleep(1500);

        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        Assert.assertEquals(value, "Received!");

        driver.quit();
    }

    @Test
    public void testErartaSearch() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.erarta.com");

        Thread.sleep(4000);

        WebElement header1 = driver.findElement(By.xpath("//h1[text()='проведите незабываемый день']"));

        WebElement search = driver.findElement(By.cssSelector("svg.header__search-svg"));
        search.click();

        WebElement searchInput = driver.findElement(By.cssSelector("input.search-popup__input"));
        searchInput.sendKeys("весна");

        Thread.sleep(1000);

        WebElement searchButton = driver.findElement(By.cssSelector("button.search-popup__submit"));
        searchButton.click();

        Thread.sleep(1000);

        WebElement searchResult = driver.findElement(By.cssSelector("a.search-page__result-title"));
        String resultText = searchResult.getText();

        Assert.assertTrue(resultText.contains("весн") || resultText.contains("весен"));

        driver.quit();
    }
    @Test
    public void testBankTransactions() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button[ng-click='customer()']")).click();
        Thread.sleep(2000);

        Select dropdownLogin = new Select(driver.findElement(By.id("userSelect")));
        dropdownLogin.selectByValue("2");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(2000);

        Select dropdownAccount = new Select(driver.findElement(By.id("accountSelect")));
        dropdownAccount.selectByValue("number:1005");
        driver.findElement(By.xpath("//div[@ng-hide='noAccount']//button[contains(text(),'Deposit')]")).click();
        Thread.sleep(1000);
        WebElement inputElement = driver.findElement(By.cssSelector("input[placeholder='amount']"));
        inputElement.sendKeys("1500");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Transactions']")).click();
        Thread.sleep(1000);

        WebElement table = driver.findElement(By.xpath("//tbody"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement lastRow = rows.get(rows.size() - 1);
        List<WebElement> cells = lastRow.findElements(By.tagName("td"));
        String amountCellText = cells.get(1).getText();
        String transactionTypeCellText = cells.get(2).getText();

        driver.quit();

        Assert.assertEquals(amountCellText, "1500");
        Assert.assertEquals(transactionTypeCellText, "Credit");
    }
}
