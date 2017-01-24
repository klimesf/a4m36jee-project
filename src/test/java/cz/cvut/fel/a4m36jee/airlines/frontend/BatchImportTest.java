package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

        System.out.println("Batch import");
        driver.findElement(By.id("logoutListDestinationForm:homeListDestinationsBtn")).click();
        driver.findElement(By.id("selectEntityForm:indexImport")).click();
        WebElement elem = driver.findElement(By.id("form:file"));
        elem.sendKeys("C:\\flightsImport.csv");
        driver.findElement(By.id("form:ImportBtn")).click();

        driver.findElement(By.id("selectEntityForm:indexFlight")).click();

    }

}
