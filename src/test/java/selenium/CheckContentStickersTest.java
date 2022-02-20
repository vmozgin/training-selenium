package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CheckContentStickersTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkContentStickersTest() {
        driver.get("http://localhost/litecart/");
        List<WebElement> productsList = driver.findElements(By.className("image-wrapper"));

        for (WebElement product : productsList) {
            List<WebElement> stickersList = product.findElements(By.className("sticker"));
            Assert.assertTrue(isStickerListEqualsOne(stickersList));
        }
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }

    private boolean isStickerListEqualsOne(List<WebElement> stickers) {
        return stickers.size() == 1;
    }
}
