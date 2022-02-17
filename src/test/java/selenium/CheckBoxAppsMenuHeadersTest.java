package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CheckBoxAppsMenuHeadersTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkBoxAppsMenuHeadersTest() {
        login();

        List<WebElement> elementList = driver.findElements(By.xpath("//*[@id='box-apps-menu']/li"));

        for (int i = 0; i < elementList.size(); i++) {
            elementList = driver.findElements(By.xpath("//*[@id='box-apps-menu']/li"));
            elementList.get(i).click();
            Assert.assertTrue(isElementPresent(driver, By.tagName("h1")));
        }
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }

    private void login() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
