package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class LinksOpenInANewWindowTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void linksOpenInANewWindowTest() {
        doLogin();
        driver.findElement(By.xpath("//*[@id='app-'][3]")).click();
        driver.findElement(By.className("button")).click();
        String mainWindow = driver.getWindowHandle();
        List<WebElement> links = driver.findElements(By.className("fa-external-link"));

        for (WebElement element : links) {
            Set<String> oldWindows = driver.getWindowHandles();
            element.click();
            String newWindow = wait.until(anyWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return driver -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
