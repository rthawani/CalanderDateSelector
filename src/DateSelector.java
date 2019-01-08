import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class DateSelector {
	
	AndroidDriver driver;
	final static String APP_PATH = "/Users/riteshthawani/Documents/Fast Scheduler_1.0.7.apk";

	@BeforeMethod
	public void setup() throws MalformedURLException, Exception{
		// setup
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformVersion", "5.0");
		cap.setCapability("platformName", "Android");
		cap.setCapability("browserName", "Android");
		cap.setCapability("deviceName", "Galaxy S5");
		cap.setCapability("app", APP_PATH);
		
		// run
		
			driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), cap);
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
	}
	
	@Test (dataProvider="getData")
	public void selectDatesfromCalender(String date, String event, String time) throws Exception{
		//String date = "14/08/2016";
		driver.findElement(By.id("com.linever.fastscheduler.android:id/textYearMonth")).click();
		
		Date currentDate = new Date();
		
		SimpleDateFormat smd = new SimpleDateFormat("dd/MM/yyyy");
		Date datetoBeSelected = smd.parse(date);
		
		String monthyeardisplayed = driver.findElement(By.id("com.linever.fastscheduler.android:id/textYearMonth")).getText();
		System.out.println("Month displayed is :" +monthyeardisplayed);
		smd = new SimpleDateFormat("MMM");
		String monthtoselect = smd.format(datetoBeSelected);
		
		smd = new SimpleDateFormat("yyyy");
		String yeartoselect = smd.format(datetoBeSelected);
		
		String monthyeartobeselected = monthtoselect+" "+yeartoselect;
		System.out.println("Month Year to be selected :" +monthyeartobeselected);
		
		System.out.println(monthyeardisplayed.equals(monthyeartobeselected));
		
		while(!monthyeardisplayed.equals(monthyeartobeselected)){
			if (datetoBeSelected.compareTo(currentDate)==1)
				driver.findElement(By.id("com.linever.fastscheduler.android:id/imgNext")).click();
			else if (datetoBeSelected.compareTo(currentDate)==-1)
				driver.findElement(By.id("com.linever.fastscheduler.android:id/imgPrev")).click();
			Thread.sleep(1000);
			monthyeardisplayed = driver.findElement(By.id("com.linever.fastscheduler.android:id/textYearMonth")).getText();
		}
		smd = new SimpleDateFormat("d");
		String daytobeselected = smd.format(datetoBeSelected);
		
		System.out.println("Day to be selected :" +daytobeselected);
		
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+daytobeselected+"']")).click();
		
		//------------------------To shedule an appointment for that date --------
		
		driver.findElement(By.xpath("//android.widget.TextView")).click();
		driver.findElement(By.id("com.linever.fastscheduler.android:id/edMemo")).sendKeys(event);
		
		driver.findElement(By.xpath("//android.widget.ToggleButton[@text='"+time+"']")).click();
		
	}
	
	@DataProvider
	public Object[][] getData(){
		Object[][] data = new Object[3][3];
		
		data[0][0] = "15/04/2015";
		data[0][1] = "Event1";
		data[0][2] = "11:00";
		
		data[1][0] = "15/12/2015";
		data[1][1] = "Event2";
		data[1][2] = "11:00";
		
		data[2][0] = "15/12/2016";
		data[2][1] = "Event3";
		data[2][2] = "11:00";
		
		return data;
		
	}
	
	@AfterMethod
	public void close() throws InterruptedException{
		Thread.sleep(2000);
		if (driver != null){
			Thread.sleep(3000);
			driver.quit();
		}
			
	}

}
