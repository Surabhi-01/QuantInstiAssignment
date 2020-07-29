package section1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.TestInstance;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QuantraApplication {

	 WebDriver driver = null;

	@Test
	public void QuantraTest() throws InterruptedException {
		
		//Driver Setup
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.get("https://quantra.quantinsti.com/");
		
		
		//User logs in to the application
		driver.findElement(By.xpath("//span[contains (text(),'Login')]")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("surabhi.gutch0105@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Surupass01");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Clicking the Browse Courses option and selecting the "Sentiment Analysis in Trading" course
		Actions actions = new Actions(driver);
		WebElement browseCourses = driver.findElement(By.xpath("//a[contains(text(),'Browse Courses') and @href='/courses']"));
		actions.moveToElement(browseCourses).perform();
		
		WebElement course = driver.findElement(By.xpath("//span[contains(text(),'Sentiment Analysis in Trading')]"));
		actions.moveToElement(course).perform();
		course.click();
		
		//Getting the Course Name
		System.out.println("Name of the course is: "+course.getText());
		
		//Getting Course Price
		WebElement coursename = driver.findElement(By.xpath("//*[contains (text(),'Sentiment Analysis in Trading')]"));
		WebElement courseprize = driver.findElement(By.xpath("(//div[@class='cd__data-unit__info']//span//span)[3]"));
		
		//Printing the Course prize on console
		System.out.println("Price for the Course is: "+courseprize.getText());
		
		driver.findElement(By.xpath("//span[contains (text(),'Enroll Now+')]")).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		List<WebElement> coursesinCart = driver.findElements(By.xpath("//h5[@class='cart-item-title']"));
		
		System.out.println("Number of courses in the cart: " +coursesinCart.size());
		
		String ciC = Integer.toString(coursesinCart.size()) ;
		
		WebElement cartCount = driver.findElement(By.xpath("//li[@class='item cart-unit d-cart-unit']//span"));
		String cC = cartCount.getText();
		
		
		//Verifying that the Course count matches with the image of Cart count
		System.out.println("Number of courses in the cart image: "+cartCount.getText());
		Assert.assertEquals(ciC, cC );
		
		System.out.println("Assert Passed");
		
		//Capturing Base Amount
		WebElement baseAmount = driver.findElement(By.xpath("//div[contains(text(),'Base Amount')]//following::div[1]"));
		System.out.println("The base amount is: "+baseAmount.getText());
		
		//Capturing Amount Payable
		WebElement amountPayable = driver.findElement(By.xpath("//h5[contains(text(),'Amount Payable')]//following::span[1]"));
		System.out.println("The Amount Payable is : "+amountPayable.getText());
	
		//Clicking on "View Details" button for any course
		List<WebElement> viewDetails = driver.findElements(By.xpath("//a[contains(text(),'View Details')]"));
		
		viewDetails.get(2).click();
		
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs2.get(1));
	    driver.close();
	    driver.switchTo().window(tabs2.get(0));
	    
	  //Clicking on "Remove" button for any course
	    List<WebElement> removeCourse = driver.findElements(By.xpath("//a[contains(text(),'Remove')]"));
	    removeCourse.get(3).click();
		Thread.sleep(2000);
	    
		//Capturing the message displayed after removal of Course
		WebElement hiddenStuff = driver.findElement(By.xpath("//div[@class='toasted-container top-center']/div"));
		if(hiddenStuff.isDisplayed()){
		String hiddenStuffReturns = hiddenStuff.getText();
		System.out.println(hiddenStuffReturns);
		}
		WebElement myDynamicElement1 = new WebDriverWait(driver, 10).until(
			    ExpectedConditions.visibilityOf(hiddenStuff)
			);
	   
		//Clicking on "Apply Coupon" button and capturing Error message
	    driver.findElement(By.xpath("//div[@class='coupon-btn-unit']//button")).click();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.findElement(By.className("coupon-modal__title"));
	    
	    driver.findElement(By.xpath("//input[@placeholder='Type coupon code']")).sendKeys("ABC");
	    driver.findElement(By.xpath("(//button[@type='button']//span)[8]")).click();
	    Thread.sleep(1000);
	    
	    WebElement errorItem = driver.findElement(By.xpath("//div[@class='coupon-alert-box']//span"));
	    if(errorItem.isDisplayed()){
	    String errorItemReturns = errorItem.getText();
	    System.out.println(errorItemReturns);
	    }
	    
	    
	    //Closing the modal, signing out and closing the browser
	    driver.findElement(By.xpath("//button[@class='close']")).click();
	    
	    driver.findElement(By.xpath("//div[@class='profile-pic-initials']")).click();
	    
	    
	   driver.findElement(By.xpath("(//li[@class='avatar-menu__list sub-nav-item logout']//a)[2]")).click();
	   
	   driver.quit();
	}
	
	
}

