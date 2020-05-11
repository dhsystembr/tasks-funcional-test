package functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
	//Utilizando ChromeDriver
		/*	ChromeOptions op = new ChromeOptions();
		op.addArguments("--ignore-certificate-errors");
		op.addArguments("--test-type");
		op.addArguments("test-type");
		op.addArguments("start-maximized");
		op.addArguments("--window-size=1920,1080");
		op.addArguments("--enable-precise-memory-info");
		op.addArguments("--disable-popup-blocking");
		op.addArguments("--disable-default-apps");
		op.addArguments("test-type=browser");
		op.addArguments("--incognito");
		op.addArguments("--no-sandbox");
		op.setExperimentalOption("useAutomationExtension", false);
		WebDriver drive = new 
				ChromeDriver(op);
		//drive.navigate().to("http://192.168.48.138:8001/tasks");
		drive.navigate().to("http://localhost:8001/tasks");
		drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return drive;*/
	//Utilizando Selenium GRID
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		
		cap.setPlatform(Platform.LINUX);
		WebDriver drive = new 
				RemoteWebDriver(new URL("http://192.168.48.138:4444/wd/hub"), cap);
		
		drive.navigate().to("http://192.168.48.138:8001/tasks");
		drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return drive;
	}
	
	
	@Test
	public void testRegTaskAmbiente() throws MalformedURLException {
		
		WebDriver drive4 = acessarAplicacao();
		try {
			drive4.findElement(By.id("addTodo")).click();
			
			drive4.findElement(By.id("task")).sendKeys("testRegTaskAmbiente");
			drive4.findElement(By.id("dueDate")).sendKeys("10/10/2031");
			
			drive4.findElement(By.id("saveButton")).click();
			
			String msg = drive4.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", msg);
		} finally {
			System.out.println("af 1");
			drive4.quit();
		}
	}
	
	@Test
	public void naoDeveRegTaskAmbienteSemDesc() throws MalformedURLException{
		WebDriver drive1 = acessarAplicacao();
		try {
			drive1.findElement(By.id("addTodo")).click();
			
			drive1.findElement(By.id("dueDate")).sendKeys("11/11/2044");
			
			drive1.findElement(By.id("saveButton")).click();
			
			String msg2 = drive1.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", msg2);
		} finally {
			System.out.println("af 2");
			drive1.quit();
		}
	}
	
	@Test
	public void NaoDevRegTaskAmbienteSemData() throws MalformedURLException{
		WebDriver drive2 = acessarAplicacao();
		try {
			drive2.findElement(By.id("addTodo")).click();
			
			drive2.findElement(By.id("task")).sendKeys("NaoDevRegTaskAmbienteSemData");
			
			drive2.findElement(By.id("saveButton")).click();
			
			String msg3 = drive2.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", msg3);
		} finally {
			System.out.println("af 3");
			drive2.quit();
		}
	}
	
	@Test
	public void NaoDevRegTaskComDataPassada() throws MalformedURLException{
		WebDriver drive3 = acessarAplicacao();
		try {
			drive3.findElement(By.id("addTodo")).click();
			
			drive3.findElement(By.id("task")).sendKeys("NEWBlaBla30");
			drive3.findElement(By.id("dueDate")).sendKeys("10/10/2000");
			
			drive3.findElement(By.id("saveButton")).click();
			
			String msg = drive3.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", msg);
		} finally {
			System.out.println("af 1");
			drive3.quit();
		}
	}
}
