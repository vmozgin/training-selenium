package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page{

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/en/");
    }

    public WebElement firstProductButton() {
        return driver.findElements(By.className("product")).get(0);
    }

    public WebElement cartButton() {
        return driver.findElement(By.xpath("//div[@id='cart']/a[3]"));
    }
}
