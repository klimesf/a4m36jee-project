package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test for destinations.
 *
 * @author slavion3
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DestinationTest {

    private WebDriver driver;

    private String destinationDetailName = "Prague";
    private String name = "Pilsen";
    private String lon = "52.52";
    private String lat = "-74.01";

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void detailDestination() {

        System.out.println("Detail destination ".concat(destinationDetailName));

        List<WebElement> webElementList = driver.findElements(By.cssSelector("table tr"));
        int id = -1;
        for(WebElement element : webElementList){
            if(element.getText().contains(destinationDetailName)){
                driver.findElement(By.id("listDestinationsForm:destinationsTableListDestinations:"+id+":destinationDetailBtn")).click();
                break;
            }
            id++;
        }

        List<WebElement> webElementList2 = driver.findElements(By.cssSelector("form"));
        boolean correctData = false;
        for(WebElement element : webElementList2){
            if(element.getText().contains("Latitude: 50.0755381")){
                correctData = true;
            }
        }

        Assert.assertTrue(correctData);

    }

    @Test
    public void createDestination() {

        System.out.println("Create destination ".concat(name));

        driver.findElement(By.id("logoutListDestinationForm:homeReservationListFlightsBtn")).click();
        driver.findElement(By.id("selectEntityForm:indexDestination")).click();
        driver.findElement(By.id("listDestinationsForm:createListDestinationsBtn")).click();
        driver.findElement(By.id("createDestinationForm:createDestinationName")).sendKeys(name);
        driver.findElement(By.id("createDestinationForm:createDestinationLat")).sendKeys(lat);
        driver.findElement(By.id("createDestinationForm:createDestinationLon")).sendKeys(lon);
        driver.findElement(By.id("createDestinationForm:createDestinationBtn")).click();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("table tr"));
        boolean successfullyAdded = false;
        for(WebElement element : webElementList){
            if(element.getText().contains(name)){
                successfullyAdded = true;
            }
        }
        Assert.assertTrue(successfullyAdded);

    }

    @Test
    public void deleteDestination() {

        System.out.println("Delete destination ".concat(name));


    }
}
