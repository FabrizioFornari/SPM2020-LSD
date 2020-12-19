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
public class SeleniumAdminTests {
	
	
	private static WebDriver driver;
	private static StringBuffer verificationErrors = new StringBuffer();
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver","src/test/java/lsd/smartparking/selenium/chromedriver.exe");
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
	  public void testLoginAdmin() throws Exception {
	    driver.get("http://localhost:8098/admin/login");
	    driver.findElement(By.xpath("//form")).click();
	    driver.findElement(By.id("adminUsername")).clear();
	    driver.findElement(By.id("adminUsername")).sendKeys("admin");
	    driver.findElement(By.id("adminPassword")).clear();
	    driver.findElement(By.id("adminPassword")).sendKeys("admin");
	    driver.findElement(By.id("adminButtonLogin")).click();
	    Thread.sleep(1000);
	  }
	
	@Test
	@Order(2)
	  public void toggleMunicipality() throws Exception {
	    driver.get("http://localhost:8098/admin/dashboard");
	    driver.findElement(By.id("municipalityButtonAction")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("municipalityButtonAction")).click();
	    Thread.sleep(1000);
	  }

}
