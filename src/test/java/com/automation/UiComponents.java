package com.automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UiComponents {

	public static WebDriver webdriver;

	@BeforeMethod
	public void setUp() {
		webdriver = new ChromeDriver();
		webdriver.get("https://letcode.in/test");
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	@Test(enabled = false)
	public void testAlerts() {

		WebElement dialogLink = webdriver.findElement(By.partialLinkText("Dialog"));
		// clicking on Dialog link
		dialogLink.click();

		// Accepting confirm alerts
		WebElement confirmAlertButton = webdriver.findElement(By.xpath("//button[@id='confirm']"));
		confirmAlertButton.click();
		Alert alert = webdriver.switchTo().alert();
		alert.accept();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

		// Cancelling alert
		confirmAlertButton.click();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		alert.dismiss();

		// Promt alert
		WebElement promptAlertButton = webdriver.findElement(By.cssSelector("button#prompt"));
		promptAlertButton.click();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		alert.sendKeys("Hello Prompt alert");
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		alert.accept();
		String promptAlerttext = webdriver.findElement(By.cssSelector("p#myName")).getText();
		System.out.println("Prompt alert text:" + promptAlerttext);
		Reporter.log("Prompt alert text:" + promptAlerttext);

	}

	@Test(enabled = false)
	public void testSelectDropdown() {
		WebElement selectDropdownLink = webdriver.findElement(By.partialLinkText("Drop-Down"));
		// clicking on select dropdown link
		selectDropdownLink.click();

		Select selectDropdown = new Select(webdriver.findElement(By.xpath("//select[@id='fruits']")));
		 selectDropdown.selectByValue("3");
		 selectDropdown.selectByIndex(4);
		 selectDropdown.selectByVisibleText("Orange");

		List<WebElement> fruitsList = selectDropdown.getOptions();
		for (WebElement webelement : fruitsList) {
			System.out.println("Fruit list:" + webelement.getText());
		}

	}

	@Test(enabled = false)
	public void testFindElementsMethod() {
		WebElement selectDropdownLink = webdriver.findElement(By.partialLinkText("Drop-Down"));
		// clicking on select dropdown link
		selectDropdownLink.click();

		// findElementsMethod
		List<WebElement> superHerosList = webdriver.findElements(By.id("superheros"));
		for (WebElement webelement : superHerosList) {
			System.out.println("Superheros list:" + webelement.getText());
		}

	}

	@AfterMethod
	public void tearDOwn() {
		webdriver.quit();
	}

}
