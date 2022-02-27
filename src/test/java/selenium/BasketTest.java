package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BasketTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void basketTest() {
        driver.get("http://localhost/litecart/en/");
        addCartProduct();
        waitQuantityEquals("1");
        driver.findElement(By.className("general-0")).click();
        addCartProduct();
        waitQuantityEquals("2");
        driver.findElement(By.className("general-0")).click();
        addCartProduct();
        waitQuantityEquals("3");
        driver.findElement(By.xpath("//div[@id='cart']/a[3]")).click();

        while (isElementPresent(By.name("remove_cart_item"))) {
            removeItemAndWaitTableUpdate();
        }
    }

    private void removeItemAndWaitTableUpdate() {
        List<WebElement> list = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']//tr"));
        removeItem();
        wait.until(ExpectedConditions.stalenessOf(list.get(0)));
    }

    private void addCartProduct() {
        driver.findElements(By.className("product")).get(0).click();
        clickOnOptionSizeIfPresent();
        driver.findElement(By.name("add_cart_product")).click();
    }

    private void waitQuantityEquals(String count) {
        wait.until(ExpectedConditions.textToBe(By.className("quantity"), count));
    }

    private void clickOnOptionSizeIfPresent() {
        if (isElementPresent(By.name("options[Size]"))) {
            driver.findElements(By.xpath("//option")).get(1).click();
        }
    }

    private boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    private void removeItem() {
        By removeItem = By.name("remove_cart_item");
        if (isElementPresent(removeItem)) {
            driver.findElement(removeItem).click();
        }
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
