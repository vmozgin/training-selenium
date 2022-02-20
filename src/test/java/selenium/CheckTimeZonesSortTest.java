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
import java.util.stream.Collectors;

public class CheckTimeZonesSortTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkTimeZonesSortTest() {
        doLogin();
        openGeoZonesPage();

        List<WebElement> countries = getCountries();

        for (int i = 0; i < countries.size(); i++) {
            countries = getCountries();
            countries.get(i).click();
            List<String> zones = getZones();
            Assert.assertTrue(isValuesSorted(zones));
            openGeoZonesPage();
        }
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private void openGeoZonesPage() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
    }

    private List<WebElement> getCountries() {
        return driver.findElements(By.xpath("//tr[@class='row']//td[3]//a"));
    }

    private boolean isValuesSorted(List<String> values) {
        List<String> sortedValues = values.stream().sorted().collect(Collectors.toList());
        return values.equals(sortedValues);
    }

    private List<String> getZones() {
        return driver.findElements(By.xpath("//*[contains(@name, '[zone_code]')]//option[@selected='selected']")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
