package functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TasksTest {
	
	public WebDriver acessarAplicacao() {
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--no-sandbox");
		op.addArguments("--disable-dev-shm-usage");
		WebDriver drive = new 
				ChromeDriver(op);
		drive.navigate().to("http://192.168.48.138:8001/tasks");
		drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return drive;
	}
	
	
	@Test
	public void testRegTaskAmbiente() {
		
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
	public void naoDeveRegTaskAmbienteSemDesc() {
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
	public void NaoDevRegTaskAmbienteSemData() {
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
	public void NaoDevRegTaskComDataPassada() {
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
