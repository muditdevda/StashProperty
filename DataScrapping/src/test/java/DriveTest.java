

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DriveTest {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mudit.devda\\Documents\\DriverFile\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resource\\chromedriver.exe");
//        System.getProperty("user.dir");
		
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://stashproperty.com.au/app/search/off-market");
        
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("muditdevda@gmail.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("testing123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        driver.findElement(By.xpath("//span[text()='Search Off Market']")).click();
        WebElement suburbinput = driver.findElement(By.xpath("//input[@id='suburb']"));
        suburbinput.sendKeys("ADARE");
        Actions actions = new Actions(driver);
        Thread.sleep(5000);
        // Perform the sequence of actions - press down key and then press enter key
        actions.sendKeys(suburbinput, Keys.END)
        		.sendKeys(Keys.ENTER)	
        		.sendKeys(Keys.DOWN)
               .sendKeys(Keys.ENTER)
               .perform();
        
        driver.findElement(By.xpath("//input[@id='minBlocks']")).sendKeys("2");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
      
        List<WebElement> listofLinks = driver.findElements(By.xpath("//div[@class='h-24']//a"));
        List<String> addresslist = new ArrayList<String>();
        for (WebElement link : listofLinks) {
        	System.out.println(link.getText());
        	addresslist.add(link.getText());
        }
        
      
        List<WebElement> listofZone = driver.findElements(By.xpath("//span[text()='Zone: ']/../div/span"));
        List<String> Zonelist = new ArrayList<String>();
        for (WebElement zone : listofZone) {
        	System.out.println(zone.getText());
        	Zonelist.add(zone.getText());
        }
        
        List<WebElement> RPDList = driver.findElements(By.xpath("//span[text()='RPD: ']/.."));
        List<String> RPDStringlist = new ArrayList<String>();
        for (WebElement rpd : RPDList) {
        	System.out.println(rpd.getText());
        	RPDStringlist.add(rpd.getText());
        }
      
      
        
        List<WebElement> AreaElementlist = driver.findElements(By.xpath("//div[@class='flex items-center text-gray-600 text-xs truncate justify-end']"));
        List<String> AreaStringList = new ArrayList<String>();
        for (WebElement area : AreaElementlist) {
        	System.out.println(area.getText());
        	AreaStringList.add(area.getText());
        }
        
        //code to write in excel
        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create data to be written
        Object[][] data = {
                {"Address", "Zone", "RPD", "Area (in meter square), Street Frontage"},
                {addresslist.get(0), Zonelist.get(0),RPDStringlist.get(0), AreaStringList.get(0)},
                {addresslist.get(1), Zonelist.get(1),RPDStringlist.get(1), AreaStringList.get(1)},
                {addresslist.get(2), Zonelist.get(2),RPDStringlist.get(2), AreaStringList.get(2)},
                {addresslist.get(3), Zonelist.get(3),RPDStringlist.get(3), AreaStringList.get(3)},
                {addresslist.get(4), Zonelist.get(4),RPDStringlist.get(4), AreaStringList.get(4)},
                {addresslist.get(5), Zonelist.get(5),RPDStringlist.get(5), AreaStringList.get(5)}
        };

        // Write data to the sheet
        int rowCount = 0;
        for (Object[] row : data) {
            Row sheetRow = sheet.createRow(rowCount++);
            int columnCount = 0;
            for (Object field : row) {
                Cell cell = sheetRow.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        // Save the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir")+"\\Output\\ScappingData.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}
}
