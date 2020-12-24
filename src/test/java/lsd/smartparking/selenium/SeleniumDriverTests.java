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
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumDriverTests {
	
	
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
	  public void testViewVehicle() throws Exception {
		driver.get("http://localhost:8098/login");
	    driver.findElement(By.xpath("//form")).click();
	    driver.findElement(By.id("emailLogin")).clear();
	    driver.findElement(By.id("emailLogin")).sendKeys("piatanesi.samuel@gmail.com");
	    driver.findElement(By.id("passwordLogin")).clear();
	    driver.findElement(By.id("passwordLogin")).sendKeys("password");
	    driver.findElement(By.id("buttonLogin")).click();
        WebDriverWait wait1 = new WebDriverWait(driver, 3);
	    wait1.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(1000);
	    driver.findElement(By.id("profileMenu")).click();
        WebDriverWait wait2 = new WebDriverWait(driver, 3);
	    wait2.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(1000);
	    driver.findElement(By.id("garageButton")).click();
	    WebDriverWait wait3 = new WebDriverWait(driver, 3);
	    wait3.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(1000);
	    driver.findElement(By.className("vehicle")).click();
	    WebDriverWait wait4 = new WebDriverWait(driver, 3);
	    wait4.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(1000);
	    driver.findElement(By.id("profileMenu")).click();
	    WebDriverWait wait5 = new WebDriverWait(driver, 3);
	    wait5.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	    Thread.sleep(1000);
	    driver.findElement(By.id("logoutButton")).click();
	    Thread.sleep(1000);
	  }

}
