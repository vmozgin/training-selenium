package selenium;

import org.testng.annotations.Test;

public class BasketTest extends TestBase {

    @Test
    public void basketTest() {
        app.openMainPage();

        int itemsCount = 3;
        for (int i = 1; i <= itemsCount; i++) {
            app.addProductToCartAndCheckQuantityEquals(i);
        }

        app.removeAllItemsFromCart();
    }
}
