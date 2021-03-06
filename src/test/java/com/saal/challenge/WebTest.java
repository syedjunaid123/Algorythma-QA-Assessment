package com.saal.challenge;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages_com.saal.challenge.AccountCreation_Page;
import pages_com.saal.challenge.Cart_Page;
import pages_com.saal.challenge.LogIn_Page;
import pages_com.saal.challenge.MyAccounts_Page;
import pages_com.saal.challenge.SignIn_Page;
import pages_com.saal.challenge.WomenClothing_Page;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;

@Listeners(com.saal.challenge.TestNGListeners.class)
public class WebTest {
	WebDriver driver;
	WebDriverWait wait;

	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	static String projectPath;

	Logger logger = LogManager.getLogger(WebTest.class);
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("myBrowser")
	public void setUp(String myBrowser) {
		if(myBrowser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(myBrowser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();			
		}
		else if(myBrowser.equalsIgnoreCase("Opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();		
		}
		
		wait = new WebDriverWait(driver, 10, 50);
		
		SignIn_Page signinPage = new SignIn_Page(driver);
		signinPage.appln_url();

		projectPath = System.getProperty("user.dir");
		String timestamp = String.valueOf(new Date().getTime());
		htmlReporter = new ExtentHtmlReporter(projectPath+"/ExtentReports/Extent"+timestamp+".html");
		extent = new ExtentReports();	
	}

	@Test
	public void signInTest() {	
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("SignIn Test", "This test is to check the login");
		test.log(Status.INFO, "This TEST has been started(status, details)");
		
		SignIn_Page signinPage = new SignIn_Page(driver);
		LogIn_Page create_account = new LogIn_Page(driver);
		AccountCreation_Page account_creation = new AccountCreation_Page(driver);
		MyAccounts_Page myaccount = new MyAccounts_Page(driver);

		signinPage.signin();
		logger.info("Sign In Button is clicked");
		test.pass("Sign In Button is clicked");

		create_account.enter_email();
		logger.info("Entering Email Id to Create New Account");
		test.pass("Entering Email Id to Create New Account");
		
		create_account.click_btn_create_account();
		logger.info("Clicking on Create Account Button");
		test.pass("Clicking on Create Account Button");
		
		account_creation.click_radio_btn_gender();
		logger.info("Selecting Gender Option");
		test.pass("Selecting Gender Option");

		account_creation.enter_name_and_pwd();
		logger.info("Entering Name and password details");
		test.pass("Entering Name and password details");

		account_creation.select_drd_day();
		logger.info("Selecting date from the dropdown");
		test.pass("Selecting date from the dropdown");

		account_creation.select_drd_month();
		logger.info("Selecting month from the dropdown");
		test.pass("Selecting month from the dropdown");

		account_creation.select_drd_year();
		logger.info("Selecting year from the dropdown");
		test.pass("Selecting year from the dropdown");

		account_creation.enter_all_address_details();
		logger.info("Entering all the address details such as such as addr, city, state, ph.no, comapny etc");
		test.pass("Entering all the address details such as such as addr, city, state, ph.no, comapny etc");

		account_creation.click_btn_submit();
		logger.info("Click on Submit button");
		test.pass("Click on Submit button");

		String get_txt_heading = myaccount.get_heading_label();
		logger.info("Fetching the heading of the page");
		test.pass("Fetching the heading of the page");

		String get_fullname = myaccount.get_fullname_label();
		logger.info("Fetching the fullname of the user");
		test.pass("Fetching the fullname of the user");

		Assert.assertEquals(myaccount.lbl_my_account_heading(), get_txt_heading, "Account Heading is not same");
		logger.info("Validating if the Heading of the page is right");
		test.pass("Validating if the Heading of the page is right");

		Assert.assertEquals(get_fullname, account_creation.name()+" "+account_creation.surname(), "Name and Surname is not same");
		logger.info("Validating if the Full Name of the user is correct");
		test.pass("Validating if the Full Name of the user is correct");

		Assert.assertEquals(myaccount.get_txt_account_info(), myaccount.lbl_txt_account_info(), "Account Info, text is not displayed");
		logger.info("Validating if the Account Information is displayed correctly");
		test.pass("Validating if the Account Information is displayed correctly");

		Assert.assertTrue(myaccount.is_disp_logout(), "Logout button is not displayed");
		logger.info("Validating if the logout button is displayed");
		test.pass("Validating if the logout button is displayed");

		Assert.assertTrue(myaccount.my_account_in_url(), "controller=my-account is not displayed in URL");
		logger.info("Validating if the 'controller=my-account' is appended in page URL");
		test.pass("Validating if the 'controller=my-account' is appended in page URL");
	}

	@Test
	public void logInTest() {
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("Login Test", "This test is to check the login");
		test.log(Status.INFO, "This TEST has been started(status, details)");

		SignIn_Page signinPage = new SignIn_Page(driver);		
		LogIn_Page loginPage = new LogIn_Page(driver);
		MyAccounts_Page myaccount = new MyAccounts_Page(driver);		

		signinPage.signin();
		logger.info("Sign In Button is clicked");
		test.pass("Sign In Button is clicked");

		loginPage.login();
		logger.info("LogIn is completed by entering username and password");
		test.pass("LogIn is completed by entering username and password");

		String get_txt_heading = myaccount.get_heading_label();
		logger.info("Fetching the Heading after successfull login");
		test.pass("Fetching the Heading after successfull login");

		String get_fullname = myaccount.get_fullname_label();
		logger.info("Fetching the Fullname of the sign in user");
		test.pass("Fetching the Fullname of the sign in user");

		Assert.assertEquals(myaccount.lbl_my_account_heading(), get_txt_heading, "Account Heading is not same");
		logger.info("Validating if the Heading displayed is correct");
		test.pass("Validating if the Heading displayed is correct");

		Assert.assertEquals(myaccount.get_fullname(), get_fullname, "User Name is not same");
		logger.info("Validating if the Fullname displayed is correct");	
		test.pass("Validating if the Fullname displayed is correct");

		Assert.assertEquals(myaccount.get_txt_account_info(), myaccount.lbl_txt_account_info(), "Account Info, text is not displayed");
		logger.info("Fetching & Validating if the Account Information is displayed correctly");	
		test.pass("Fetching & Validating if the Account Information is displayed correctly");

		Assert.assertTrue(myaccount.is_disp_logout(), "Logout button is not displayed");
		logger.info("Validating the logout button is displayed or not");	
		test.pass("Validating the logout button is displayed or not");

		Assert.assertTrue(myaccount.my_account_in_url(), "controller=my-account is not displayed in URL");
		logger.info("Validating if the 'controller=my-account' is appended in page URL");
		test.pass("Validating if the 'controller=my-account' is appended in page URL");

	}

	@Test
	public void checkoutTest() {		
		extent.attachReporter(htmlReporter);
		ExtentTest test = extent.createTest("Checkout Test", "This test is to check the login");
		test.log(Status.INFO, "This TEST has been started");

		SignIn_Page signinPage = new SignIn_Page(driver);
		LogIn_Page loginPage = new LogIn_Page(driver);
		MyAccounts_Page myaccount = new MyAccounts_Page(driver);		
		WomenClothing_Page dress_checkout = new WomenClothing_Page(driver);		
		Cart_Page order_checkout = new Cart_Page(driver);

		signinPage.signin();
		logger.info("Sign In Button is clicked");
		test.pass("Sign In Button is clicked");

		loginPage.login();
		logger.info("LogIn is completed by entering username and password");
		test.pass("LogIn is completed by entering username and password");
		
		myaccount.click_btn_women();		
		logger.info("Click on Women button");
		test.pass("Click on Women button");
		
		dress_checkout.select_dress();
		logger.info("Selecting the dress");
		test.pass("Selecting the dress");
		
		dress_checkout.select_view_dress();
		logger.info("Viewing the dress in maximized view in next page");
		test.pass("Viewing the dress in maximized view in next page");

		dress_checkout.click_btn_submit();
		logger.info("Clicking on Submit button for checkout");
		test.pass("Clicking on Submit button for checkout");

		dress_checkout.click_btn_proceed_checkout();	
		logger.info("Clicking on Checkout button to proceed");
		test.pass("Clicking on Checkout button to proceed");

		order_checkout.click_proceed_checkout();
		logger.info("Clicking on Checkout button to proceed in the next page again");
		test.pass("Clicking on Checkout button to proceed in the next page again");

		order_checkout.click_process_address();
		logger.info("Clicking on Next step button to process address");
		test.pass("Clicking on Next step button to process address");

		order_checkout.chk_terms_conditions();
		logger.info("Selecting the Terms and conditions checkbox");
		test.pass("Selecting the Terms and conditions checkbox");

		order_checkout.click_process_shipping();
		logger.info("Clicking on next to complete the shipping step");
		test.pass("Clicking on next to complete the shipping step");

		order_checkout.click_btn_pay_by_bank_wire();
		logger.info("Select button - Pay by Bank Wire");
		test.pass("Select button - Pay by Bank Wire");

		order_checkout.click_btn_confirm_order();	
		logger.info("Click on Confirm Order Button");
		test.pass("Click on Confirm Order Button");

		String get_txt_heading = order_checkout.get_heading_label();
		logger.info("Fetching the text Order Confirmation");
		test.pass("Fetching the text Order Confirmation");

		Assert.assertEquals(order_checkout.lbl_order_confirmation(), get_txt_heading,  "Order Confirmation text is not displayed");	
		logger.info("Verifying text in application if its ORDER Confirmation");
		test.pass("Verifying text in application if its ORDER Confirmation");

		Assert.assertTrue(order_checkout.is_disp_shipping_step4(), "Shipping Step4 is not displayed");
		logger.info("Validating if the shipping section is displayed");
		test.pass("Validating if the shipping section is displayed");

		Assert.assertTrue(order_checkout.is_disp_conformation_step_end(), "Confirmation Step End section is not displayed");
		logger.info("Validating if the Confirmation Step End section is displayed");
		test.pass("Validating if the Confirmation Step End section is displayed");

		Assert.assertEquals(order_checkout.get_txt_order_confirmation(), order_checkout.lbl_txt_order_confirmation(), "Order Completion Text is not displayed");
		logger.info("Verifying text ORDER Confirmation is displayed");
		test.pass("Verifying text ORDER Confirmation is displayed");

		Assert.assertTrue(order_checkout.order_confirmation_in_url(), "The URL doesnt contains 'order-confirmation'");
		logger.info("Validating if the 'order-confirmation' is appended in page URL");
		test.pass("Validating if the 'order-confirmation' is appended in page URL");
	}
	
	@AfterMethod(alwaysRun = true)	
	public void afterTestExecution(ITestResult result) {
		
		extent.attachReporter(htmlReporter);
		
		if(ITestResult.FAILURE==result.getStatus())
		{
			test.log(Status.FAIL, result.getThrowable());
			try 
			{
				TakesScreenshot ts=(TakesScreenshot)driver;
				File source=ts.getScreenshotAs(OutputType.FILE);
				String timestamp = String.valueOf(new Date().getTime());
				FileUtils.copyFile(source, new File("./ScreenShots/"+result.getName()+ timestamp +".png"));			 
				System.out.println(" ****** Screenshot taken ******");
			} 
			catch (Exception e)
			{
				System.out.println("Exception while taking screenshot "+e.getMessage());
			} 	 			
		}
		
		driver.quit();
		extent.flush();
	}
}
