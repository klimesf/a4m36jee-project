package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.TestUtils;
import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test for flights.
 *
 * @author slavion3
 */
@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FlightTest {

    private WebDriver driver;

    private String name;
    private String seats;
    private String price;
    private String from;
    private String to;
    private String date;

    public FlightTest(String itemName, String itemSeats, String itemPrice, String itemFrom, String itemTo, String itemDate) {
        this.name = itemName;
        this.seats = itemSeats;
        this.price = itemPrice;
        this.from = itemFrom;
        this.to = itemTo;
        this.date = itemDate;
    }

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Parameterized.Parameters
    public static Collection<String[]> data() {
        return TestUtils.readCSVfileToCollection("src\\test\\resources\\flightsImport.csv");
    }

    @Test
    public void createFlightTest(){
        System.out.println("Create flight test - " + name);

        driver.findElement(By.id("listFlightsForm:createFlight")).click();
        driver.findElement(By.id("createFlightForm:createFlightName")).sendKeys(name);
        driver.findElement(By.id("createFlightForm:createFlightSeats")).sendKeys(seats);
        driver.findElement(By.id("createFlightForm:createFlightPrice")).sendKeys(price);
        Select select = new Select(driver.findElement(By.id("createFlightForm:createFlightNameFrom")));
        select.selectByVisibleText(from);
        Select select2 = new Select(driver.findElement(By.id("createFlightForm:createFlightNameTo")));
        select2.selectByVisibleText(to);
        driver.findElement(By.id("createFlightForm:createFlightDateOfDeparture")).sendKeys(date);
        driver.findElement(By.id("createFlightForm:createFlightBtn")).click();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("#flightsTableListFlights tr"));
        boolean successfullyAdded = false;
        for(WebElement element : webElementList){
            if(element.getText().contains(name)){
                successfullyAdded = true;
            }
        }
        Assert.assertTrue(successfullyAdded);
    }

    @Test
    public void deleteFlightTest() {
        System.out.println("Delete flight test - " + name);

        List<WebElement> webElementList = driver.findElements(By.cssSelector("#flightsTableListFlights tr"));
        for(WebElement element : webElementList){
            if(element.getText().contains(name)){
                String[] fields = element.getText().split(" ");
                driver.findElement(By.id("listFlightsForm:deleteListFlightsBtn-".concat(fields[0]))).click();
                break;
            }
        }

        driver.findElement(By.id("deleteFlightForm:deleteFlightBtn")).click();

        List<WebElement> webElementList2 = driver.findElements(By.cssSelector("#flightsTableListFlights tr"));
        boolean successfullyRemoved = true;
        for(WebElement element : webElementList2){
            if(element.getText().contains(name)){
                successfullyRemoved = false;
            }
        }

        Assert.assertTrue(successfullyRemoved);
    }

}
