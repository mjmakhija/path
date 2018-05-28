package com.emsays.path;

import static com.emsays.path.GV.CHROME_DRIVER_PATH;
import java.util.HashMap;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumHelper
{

	private WebDriver driver = null;

	public WebDriver getDriver()
	{
		if (driver != null)
		{
			driver.get("http://www.google.com");
			driver.quit();
			if (!driver.toString().contains("null"))
			{

				return driver;
			}

		}

		if (SystemUtils.IS_OS_WINDOWS)
		{
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH + "/chromedriver.exe");
		}
		else if (SystemUtils.IS_OS_LINUX)
		{
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH + "/chromedriver");
		}

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		//chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");
		options.addArguments("--disable-extensions"); //to disable browser extension popup

		org.openqa.selenium.remote.DesiredCapabilities cap = org.openqa.selenium.remote.DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		cap.setCapability(org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);

		return driver;
	}

	public WebElement findElement(By abc)
	{
		return findElement(abc, 3);
	}

	public WebElement findElement(By abc, int timeout)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(abc));
			return driver.findElement(abc);
		}
		catch (Exception e)
		{
			//LOGGER.log(Level.SEVERE, "Cant find element", e);
			return null;
		}

	}

	public void wait(int seconds)
	{
		try
		{
			Thread.sleep(seconds * 1000);

		}
		catch (InterruptedException e)
		{
			//LOGGER.log(Level.SEVERE, "Wait function failed " + e);
		}
	}
	
	public void close()
	{
		try
		{
			driver.close();
			driver = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
