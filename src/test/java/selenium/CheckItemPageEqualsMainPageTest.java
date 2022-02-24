package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class CheckItemPageEqualsMainPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    // на главной странице и на странице товара совпадает текст названия товара
    @Test
    public void mainPageItemTextEqualsItemPage() {
        WebElement yellowDuckMainPage = openMainPageAndGetYellowDuck();
        String mainPageItemName = yellowDuckMainPage.findElement(By.className("name")).getText();

        openYellowDuckItemPage();
        String itemPageItemName = wait.until(presenceOfElementLocated(By.tagName("h1"))).getText();

        Assert.assertEquals(mainPageItemName, itemPageItemName);
    }

    // на главной странице и на странице товара совпадают цены (обычная и акционная)
    @Test
    public void mainPageItemPriceEqualsItemPage() {
        WebElement yellowDuckMainPage = openMainPageAndGetYellowDuck();
        String mainPageRegularPrice = yellowDuckMainPage.findElement(By.className("regular-price")).getText();
        String mainPageCampaignPrice = yellowDuckMainPage.findElement(By.className("campaign-price")).getText();

        openYellowDuckItemPage();
        String itemPageRegularPrice = driver.findElement(By.className("regular-price")).getText();
        String itemPageCampaignPrice = driver.findElement(By.className("campaign-price")).getText();

        Assert.assertEquals(itemPageRegularPrice, mainPageRegularPrice);
        Assert.assertEquals(itemPageCampaignPrice, mainPageCampaignPrice);
    }

    // обычная цена зачёркнутая и серая
    @Test
    public void regularPriceHasGrayColorAndTagS() {
        WebElement yellowDuckMainPage = openMainPageAndGetYellowDuck();
        WebElement mainPageRegularPrice = yellowDuckMainPage.findElement(By.className("regular-price"));

        Assert.assertEquals(mainPageRegularPrice.getAttribute("tagName"), "S");
        assertElementColorIsGray(mainPageRegularPrice);

        openYellowDuckItemPage();
        WebElement itemPageRegularPrice = driver.findElement(By.className("regular-price"));

        Assert.assertEquals(itemPageRegularPrice.getAttribute("tagName"), "S");
        assertElementColorIsGray(itemPageRegularPrice);
    }

    // акционная жирная и красная
    @Test
    public void campaignPriceHasRedColorAndTagStrong() {
        WebElement yellowDuckMainPage = openMainPageAndGetYellowDuck();
        WebElement mainPageCampaignPrice = yellowDuckMainPage.findElement(By.className("campaign-price"));

        Assert.assertEquals(mainPageCampaignPrice.getAttribute("tagName"), "STRONG");
        assertElementColorIsRed(mainPageCampaignPrice);

        openYellowDuckItemPage();
        WebElement itemPageCampaignPrice = driver.findElement(By.className("campaign-price"));

        Assert.assertEquals(itemPageCampaignPrice.getAttribute("tagName"), "STRONG");
        assertElementColorIsRed(itemPageCampaignPrice);
    }

    // акционная цена крупнее, чем обычная
    @Test
    public void campaignPriceIsMoreThenRegular() {
        WebElement yellowDuckMainPage = openMainPageAndGetYellowDuck();
        String mainPageRegularPrice = yellowDuckMainPage.findElement(By.className("regular-price")).getCssValue("font-size");
        String mainPageCampaignPrice = yellowDuckMainPage.findElement(By.className("campaign-price")).getCssValue("font-size");

        assertCampaignPriceIsMoreThenRegular(mainPageCampaignPrice, mainPageRegularPrice);

        openYellowDuckItemPage();
        String itemPageRegularPrice = driver.findElement(By.className("regular-price")).getCssValue("font-size");
        String itemPageCampaignPrice = driver.findElement(By.className("campaign-price")).getCssValue("font-size");

        assertCampaignPriceIsMoreThenRegular(itemPageCampaignPrice, itemPageRegularPrice);
    }

    private WebElement openMainPageAndGetYellowDuck() {
        driver.get("http://localhost/litecart/");

        return driver.findElement(By.xpath("//*[@id='box-campaigns']//a[1]"));
    }

    private void openYellowDuckItemPage() {
        WebElement yellowDuckMainPage = driver.findElement(By.xpath("//*[@id='box-campaigns']//a[1]"));
        yellowDuckMainPage.click();
    }

    private void assertElementColorIsGray(WebElement element) {
        String color = element.getCssValue("color");
        java.awt.Color colors = Color.fromString(color).getColor();

        Assert.assertEquals(colors.getRed(), colors.getGreen());
        Assert.assertEquals(colors.getRed(), colors.getBlue());
    }

    private void assertElementColorIsRed(WebElement element) {
        String color = element.getCssValue("color");
        java.awt.Color colors = Color.fromString(color).getColor();

        Assert.assertEquals(colors.getGreen(), 0);
        Assert.assertEquals(colors.getBlue(), 0);
    }

    private void assertCampaignPriceIsMoreThenRegular(String campaignPrice, String regularPrice) {
        double campaignPriceValue = Double.parseDouble(campaignPrice.substring(0, campaignPrice.length() - 2));
        double regularPriceValue = Double.parseDouble(regularPrice.substring(0, regularPrice.length() - 2));

        Assert.assertTrue(campaignPriceValue > regularPriceValue);
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
