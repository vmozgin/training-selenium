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
        doLogin();

        List<WebElement> leftBoxParentGroupsList = getLeftBoxParentGroups();
        for (int i = 0; i < leftBoxParentGroupsList.size(); i++) {
            leftBoxParentGroupsList = getLeftBoxParentGroups();
            leftBoxParentGroupsList.get(i).click();
            leftBoxParentGroupsList = getLeftBoxParentGroups();
            assertHeaderIsPresent();

            List<WebElement> leftBoxChildGroupsList = getLeftBoxChildGroups();
            for (int j = 0; j < leftBoxChildGroupsList.size(); j++) {
                leftBoxChildGroupsList.get(j).click();
                leftBoxChildGroupsList = getLeftBoxChildGroups();
                assertHeaderIsPresent();
            }
        }
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private List<WebElement> getLeftBoxParentGroups() {
        return driver.findElements(By.id("app-"));
    }

    private List<WebElement> getLeftBoxChildGroups() {
        return driver.findElements(By.xpath("//*[starts-with(@id,'doc')]"));
    }

    private void assertHeaderIsPresent() {
        Assert.assertTrue(isElementPresent(driver, By.tagName("h1")));
    }

    private boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
