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

    public List<WebElement> dataTableCorners() {
        return driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']//tr"));
    }

    public WebElement removeItemButton() {
        return driver.findElement(By.name("remove_cart_item"));
    }

    public CartPage waitDataTableCornersUpdate() {
        List<WebElement> list = dataTableCorners();
        wait.until(ExpectedConditions.stalenessOf(list.get(0)));
        return this;
    }
}
