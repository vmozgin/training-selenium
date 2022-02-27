package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RegistrationNewPersonTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void registrationTest() {
        String password = "123456";
        String email = generateRandomEmail();
        driver.get("http://localhost/litecart/en/create_account");
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Владимир");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Путим");
        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("Кремль");
        driver.findElement(By.xpath("//input[@name='postcode']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Москва");
        driver.findElement(By.className("select2-selection__arrow")).click();
        WebElement us = driver.findElement(By.xpath("//span[@class='select2-results']//li[text()='United States']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", us);
        driver.findElement(By.xpath("//span[@class='select2-results']//li[text()='United States']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//select[@name='zone_code']/option[@value='GU']"))).click();
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+19851256");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@name='confirmed_password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@name='create_account']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//div[@id='box-account']//li[4]//a"))).click();
        wait.until(presenceOfElementLocated(By.xpath("//input[@name='email']"))).sendKeys(email);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@name='login']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//div[@id='box-account']//li[4]//a"))).click();
    }

    private String generateRandomEmail() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String randomString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return String.format("%s@mail.ru", randomString);
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
