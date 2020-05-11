package prod;

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

public class HealthCheck {
	
	@Test
	public void healthCheckIT() throws MalformedURLException{
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");

		cap.setPlatform(Platform.LINUX);
		WebDriver drive = new RemoteWebDriver(new URL("http://192.168.48.138:4444/wd/hub"), cap);
		String version;
		try {
			drive.navigate().to("http://192.168.48.138:9999/tasks");
			drive.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			version = drive.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));
			System.out.println(version);
		} finally {
			drive.quit();
		}
	}
}
