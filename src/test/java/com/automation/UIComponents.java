package com.automation;

/*@Author Akshaykumar Jadhav
@since 05/04/2025
*/

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UIComponents {

	public static WebDriver webdriver;

	@BeforeMethod(groups = { "smoke" })
	@Parameters({ "browser" })
	public void setUp(String browserName) {
		System.out.println("Browser::::"+browserName);
		if (browserName.equalsIgnoreCase("Chrome")) {
			webdriver = new ChromeDriver();
		}
		webdriver.get("https://letcode.in/test");
		webdriver.manage().window().maximize();
		// implicit wait
		webdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// explicit wait
		WebElement heading = webdriver.findElement(By.xpath("//h1[text()='Ready to be a Pro Engineer?']"));
		Wait<WebDriver> explicitWait = new WebDriverWait(webdriver, Duration.ofSeconds(2));
		explicitWait.until(d -> heading.isDisplayed());

		// Fluent wait
		Wait<WebDriver> fluentWait = new FluentWait<>(webdriver).withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(300)).ignoring(ElementNotInteractableException.class);
		fluentWait.until(d -> heading.isEnabled());

	}

	@Test
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

	@Test(groups = { "smoke" })
	public void tesWindow() {
		WebElement windowsLink = webdriver.findElement(By.partialLinkText("Tabs"));
		// clicking on windows link
		windowsLink.click();

		// Clicking on home page button
		WebElement openHomeLink = webdriver.findElement(By.cssSelector("button#home"));
		openHomeLink.click();
		System.out.println("Parent Page Title:" + webdriver.getCurrentUrl());

		Object[] windowhandles = webdriver.getWindowHandles().toArray();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		// switching to next tab
		webdriver.switchTo().window((String) windowhandles[1]);
		System.out.println("Child page Title:" + webdriver.getCurrentUrl());
		// switching to previous tab
		webdriver.switchTo().window((String) windowhandles[0]);
		System.out.println("Parent page Title after switching:" + webdriver.getCurrentUrl());

	}

	@Test(invocationCount = 2)
	public void testActionClassMouseOver() {
		Actions action = new Actions(webdriver);
		action.moveToElement(webdriver.findElement(By.xpath("//a[text()='Products']"))).build().perform();
		webdriver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));

	}

	@Test(groups = { "smoke" })
	public void testActionKeys() {

		WebElement editLink = webdriver.findElement(By.partialLinkText("Edit"));
		// clicking on edit link
		editLink.click();
		WebElement fullNameText = webdriver.findElement(By.id("fullName"));
		fullNameText.sendKeys("Akshay");

		Actions action = new Actions(webdriver);
		fullNameText.sendKeys(Keys.TAB);

		WebElement appendTextBox = webdriver.findElement(By.cssSelector("input#join"));
		appendTextBox.clear();
		appendTextBox.sendKeys("Hello Akshay");
	}

	@AfterMethod(groups = { "smoke" })
	public void tearDOwn() {
		webdriver.quit();
	}

}
