package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddNewItemTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void addNewItemTest() {
        doLogin();
        driver.findElement(By.xpath("//li[2]")).click();
        driver.findElement(By.xpath("//a[@class='button'][2]")).click();
        driver.findElement(By.xpath("//input[@name='status' and @value='1'] ")).click();
        driver.findElement(By.xpath("//input[@name='name[en]']")).sendKeys("Brick");
        driver.findElement(By.xpath("//input[@name='code']")).sendKeys("99");
        driver.findElement(By.xpath("//input[@name='product_groups[]' and @value='1-3']")).click();
        WebElement quantity = driver.findElement(By.xpath("//input[@name='quantity']"));
        quantity.clear();
        quantity.sendKeys("35");
        File file = new File("./src/main/resources/brick.jpeg");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        driver.findElement(By.xpath("//input[@name='date_valid_from']")).sendKeys(date.format(formatter));
        driver.findElement(By.xpath("//input[@name='date_valid_to']")).sendKeys(date.plusDays(15).format(formatter));
        driver.findElement(By.xpath("//ul[@class='index']//li[2]/a")).click();

        driver.findElement(By.xpath("//select[@name='manufacturer_id']/option[@value='1']")).click();
        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("Кирпич");
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("Construction brick");
        driver.findElement(By.xpath("//*[@class='trumbowyg-editor']")).sendKeys("Construction brick");
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Brick");
        driver.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("Description");
        driver.findElement(By.xpath("//ul[@class='index']//li[4]/a")).click();

        WebElement purchasePrice = driver.findElement(By.xpath("//input[@name='purchase_price']"));
        purchasePrice.clear();
        purchasePrice.sendKeys("0,35");
        driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']//option[@value='USD']")).click();
        driver.findElement(By.xpath("//input[@name='prices[USD]']")).sendKeys("0,35");
        driver.findElement(By.xpath("//input[@name='prices[EUR]']")).sendKeys("0,2");
        driver.findElement(By.name("save")).click();

        List<WebElement> rows = driver.findElements(By.className("row"));
        assertBrickInTheCatalog(rows);
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private boolean assertBrickInTheCatalog(List<WebElement> rows) {
        return rows.stream().anyMatch(r -> r.getText().equals("Brick"));
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
