package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserLogTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void browserLogTest() {
        doLogin();
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        List<WebElement> allRows = driver.findElements(By.xpath("//tr[@class='row']//td[3]//a"));
        List<WebElement> ducks = allRows.subList(3, allRows.size());

        for (int i = 0; i < ducks.size(); i++) {
            ducks.get(i).click();
            List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
            Assert.assertEquals(logs.size(), 0);
            driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
            allRows = driver.findElements(By.xpath("//tr[@class='row']//td[3]//a"));
            ducks = allRows.subList(3, allRows.size());
        }
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
