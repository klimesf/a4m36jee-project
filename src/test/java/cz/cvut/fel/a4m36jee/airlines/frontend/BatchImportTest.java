package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test for Batch import.
 *
 * @author slavion3
 */
public class BatchImportTest {

    private WebDriver driver;

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void batchImport() {
        System.out.println("Batch import test");

        driver.findElement(By.id("logoutListDestinationForm:homeListDestinationsBtn")).click();
        driver.findElement(By.id("selectEntityForm:indexFlight")).click();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("#flightsTableListFlights tr"));
        int flightsCount = webElementList.size();

        driver.findElement(By.id("logoutListFlightsForm:homeListFlightsBtn")).click();
        driver.findElement(By.id("selectEntityForm:indexImport")).click();
        WebElement elem = driver.findElement(By.id("form:file"));
        elem.sendKeys("C:\\flightsImport.csv");
        driver.findElement(By.id("form:ImportBtn")).click();

        driver.findElement(By.id("selectEntityForm:indexFlight")).click();

        List<WebElement> webElementList2 = driver.findElements(By.cssSelector("#flightsTableListFlights tr"));
        int flightsNewCount = webElementList2.size();

        if(flightsNewCount > flightsCount){
            Assert.assertTrue(true);
        }else{
            Assert.assertTrue(false);
        }
    }

}
