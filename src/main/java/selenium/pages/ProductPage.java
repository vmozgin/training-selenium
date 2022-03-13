package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends Page{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean sizeOptionsIsPresent() {
        return isElementPresent(By.name("options[Size]"));
    }

    public List<WebElement> sizeOptionsButton() {
        return driver.findElements(By.xpath("//option"));
    }

    public WebElement addToCartButton() {
        return driver.findElement(By.name("add_cart_product"));
    }

    public ProductPage waitQuantityEquals(int count) {
        wait.until(ExpectedConditions.textToBe(By.className("quantity"), String.valueOf(count)));
        return this;
    }

    public WebElement homeButton() {
        return driver.findElement(By.className("general-0"));
    }
}
