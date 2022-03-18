package selenium.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.pages.CartPage;
import selenium.pages.MainPage;
import selenium.pages.ProductPage;

import java.util.List;

public class Application {

    private final WebDriver driver;

    private final MainPage mainPage;
    private final ProductPage productPage;
    private final CartPage cartPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void openMainPage() {
        mainPage.open();
    }

    public void addProductToCartAndCheckQuantityEquals(int quantityCount) {
        mainPage.firstProductButton().click();
        if (productPage.sizeOptionsIsPresent()) {
            productPage.sizeOptionsButton().get(1).click();
        }
        productPage.addToCartButton().click();
        productPage.waitQuantityEquals(quantityCount);
        productPage.homeButton().click();
    }

    public void removeAllItemsFromCart() {
        mainPage.open();
        mainPage.cartButton().click();
        while (cartPage.removeCartItemIsPresent()) {
            List<WebElement> rows = cartPage.dataTableRows();
            cartPage.removeItemButton().click();
            cartPage.waitDataTableRowsUpdate(rows);
        }
    }

    public void quit() {
        driver.quit();
    }
}
