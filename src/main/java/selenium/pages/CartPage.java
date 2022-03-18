package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends Page{

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean removeCartItemIsPresent() {
        return isElementPresent(By.name("remove_cart_item"));
    }

    public List<WebElement> dataTableRows() {
        return driver.findElements(dataTableRowsLocator());
    }

    public WebElement removeItemButton() {
        return driver.findElement(By.name("remove_cart_item"));
    }

    public CartPage waitDataTableRowsUpdate(List<WebElement> rows) {
        wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
        return this;
    }

    private By dataTableRowsLocator() {
        return By.xpath("//table[@class='dataTable rounded-corners']//td[contains(text(),'Duck')]");
    }
}
