package lsd.smartparking.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumMunicipalityTests {
	
	
	private static WebDriver driver;
	private static StringBuffer verificationErrors = new StringBuffer();
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Order(1)
	  public void testAddNewParking() throws Exception {
		driver.get("http://localhost:8098/login");
	    driver.findElement(By.xpath("//form")).click();
	    driver.findElement(By.id("emailLogin")).clear();
	    driver.findElement(By.id("emailLogin")).sendKeys("castelfidardo@gmail.com");
	    driver.findElement(By.id("passwordLogin")).clear();
	    driver.findElement(By.id("passwordLogin")).sendKeys("password");
	    driver.findElement(By.id("buttonLogin")).click();
        WebDriverWait wait1 = new WebDriverWait(driver, 3);
	    wait1.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(3000);
	    driver.findElement(By.className("glass")).clear();
	    driver.findElement(By.className("glass")).sendKeys("Castelfidardo");
	    driver.findElement(By.className("glass")).sendKeys(Keys.ENTER);
	    WebDriverWait wait2 = new WebDriverWait(driver, 3);
	    wait2.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        Thread.sleep(2000);
	    driver.findElement(By.id("map")).click();
        WebDriverWait wait3 = new WebDriverWait(driver, 3);
	    wait3.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingName")).sendKeys("Parking Test");
        WebDriverWait wait4 = new WebDriverWait(driver, 3);
	    wait4.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingStartTime")).sendKeys("13:00");
        WebDriverWait wait5 = new WebDriverWait(driver, 3);
	    wait5.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingEndTime")).sendKeys("18:00");
        WebDriverWait wait6 = new WebDriverWait(driver, 3);
	    wait6.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingPrice")).clear();
	    driver.findElement(By.id("parkingPrice")).sendKeys("10");
        WebDriverWait wait7 = new WebDriverWait(driver, 3);
	    wait7.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingAddButton")).click();
	    Thread.sleep(2000);
	  }
	
	@Test
	@Order(2)
	  public void testEditNewParking() throws Exception {
		driver.get("http://localhost:8098/");
	    driver.findElement(By.className("glass")).clear();
	    driver.findElement(By.className("glass")).sendKeys("Castelfidardo");
	    driver.findElement(By.className("glass")).sendKeys(Keys.ENTER);
	    WebDriverWait wait2 = new WebDriverWait(driver, 3);
	    wait2.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        Thread.sleep(2000);
        WebElement parentDiv = driver.findElement(By.className("leaflet-marker-pane"));
        List<WebElement> markerList = parentDiv.findElements(By.tagName("img"));
        markerList.get(0).click();
        WebDriverWait wait3 = new WebDriverWait(driver, 3);
	    wait3.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingEditButton")).click();
        WebDriverWait wait4 = new WebDriverWait(driver, 3);
	    wait4.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingName")).clear();
	    driver.findElement(By.id("parkingName")).sendKeys(" Edited");
        WebDriverWait wait5 = new WebDriverWait(driver, 3);
	    wait5.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingStartTime")).sendKeys("17:00");
        WebDriverWait wait6 = new WebDriverWait(driver, 3);
	    wait6.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingEndTime")).sendKeys("18:00");
        WebDriverWait wait7 = new WebDriverWait(driver, 3);
	    wait7.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    driver.findElement(By.id("parkingSaveButton")).click();
	    Thread.sleep(2000);
	  }
	
	@Test
	@Order(3)
	  public void testRemoveNewParking() throws Exception {
		driver.get("http://localhost:8098/");
	    driver.findElement(By.className("glass")).clear();
	    driver.findElement(By.className("glass")).sendKeys("Castelfidardo");
	    driver.findElement(By.className("glass")).sendKeys(Keys.ENTER);
	    WebDriverWait wait2 = new WebDriverWait(driver, 3);
	    wait2.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        Thread.sleep(2000);
        WebElement parentDiv = driver.findElement(By.className("leaflet-marker-pane"));
        List<WebElement> markerList = parentDiv.findElements(By.tagName("img"));
        markerList.get(0).click();
        WebDriverWait wait3 = new WebDriverWait(driver, 3);
	    wait3.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(2000);
	    driver.findElement(By.id("parkingRemoveButton")).click();
	    Thread.sleep(2000);
	  }

}
