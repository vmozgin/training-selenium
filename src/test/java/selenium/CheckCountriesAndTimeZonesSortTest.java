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

public class CheckCountriesAndTimeZonesSortTest {

    private WebDriver driver;

    @BeforeClass
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkCountriesAndTimeZonesSortTest() {
        doLogin();
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<String> countries = getCountries();
        Assert.assertTrue(isValuesSorted(countries));

        List<WebElement> elementsWithZones = getElementsWithZones();

        for (int i = 0; i < elementsWithZones.size(); i++) {
            elementsWithZones = getElementsWithZones();
            WebElement link = elementsWithZones.get(i).findElement(By.xpath("..//td[5]//a"));
            link.click();
            List<String> timeZones = getTimeZones();
            Assert.assertTrue(isValuesSorted(timeZones));
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        }
    }

    private void doLogin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    private boolean isValuesSorted(List<String> values) {
        List<String> sortedValues = values.stream().sorted().collect(Collectors.toList());
        return values.equals(sortedValues);
    }

    private List<WebElement> getElementsWithZones() {
        return driver.findElements(By.xpath("//tr[@class='row']//td[6]")).stream()
                .filter(e -> !e.getText().equals("0"))
                .collect(Collectors.toList());
    }

    private List<String> getCountries() {
        return driver.findElements(By.xpath("//tr[@class='row']//td[5]")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private List<String> getTimeZones() {
        return driver.findElements(By.xpath("//table[@id='table-zones']//td[3]//input[@type='hidden']")).stream()
                .map(t -> t.getAttribute("value"))
                .collect(Collectors.toList());
    }

    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }
}
