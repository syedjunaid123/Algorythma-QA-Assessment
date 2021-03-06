package pages_com.saal.challenge;


import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccounts_Page {

	WebDriver driver = null;
	WebDriverWait wait = null;

	static String projectPath;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	@FindBy(css="h1")
	WebElement lbl_myaccount;

	@FindBy(linkText="Women")
	WebElement btn_women;

	@FindBy(css="h1")
	WebElement heading;

	@FindBy(className="logout")
	WebElement logout;

	@FindBy(className="info-account")	
	WebElement txt_info_account;
	
	@FindBy(className="account")
	WebElement lbl_fullname;
	
	public MyAccounts_Page(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(this.driver, 50);

		try {
			projectPath = System.getProperty("user.dir");
			workbook = new XSSFWorkbook(projectPath+"/DataSheet.xlsx");
			sheet = workbook.getSheetAt(0);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	public String myaccounts() {
		String a = this.wait.until(ExpectedConditions.visibilityOf(lbl_myaccount)).getText();
		return a;
	}
	public void click_btn_women() {
		this.wait.until(ExpectedConditions.visibilityOf(btn_women)).click();

	}

	public String get_heading_label() {
		String lbl_heading = this.wait.until(ExpectedConditions.visibilityOf(heading)).getText();
		return lbl_heading;
	}

	public boolean my_account_in_url() {
		String url_contains = sheet.getRow(21).getCell(1).getStringCellValue();
		return driver.getCurrentUrl().contains(url_contains);		
	}	

	public boolean is_disp_logout() {
		return logout.isDisplayed();			
	}

	public String get_txt_account_info() {
		String txt_order_confirmation = this.wait.until(ExpectedConditions.visibilityOf(txt_info_account)).getText();
		return txt_order_confirmation;
	}

	public String lbl_txt_account_info() {
		return sheet.getRow(20).getCell(1).getStringCellValue();		
	}
	public String lbl_my_account_heading() {
		return sheet.getRow(22).getCell(1).getStringCellValue();
	}
	public String get_fullname() {
		return sheet.getRow(23).getCell(1).getStringCellValue();
	}
	
	public String get_fullname_label() {
		String full_name = this.wait.until(ExpectedConditions.visibilityOf(lbl_fullname)).getText();
		return full_name;
	}
}
