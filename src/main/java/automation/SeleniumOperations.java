package automation;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import runner.RunQualithonTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v95.log.Log;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigFileReader;
import utilities.FileHandling;
import utilities.ReportGeneration;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.imageio.ImageIO;

public class SeleniumOperations {

    WebDriver driver = null;
    ConfigFileReader config = new ConfigFileReader();
    ReportGeneration report = new ReportGeneration();
    RunQualithonTest run = new RunQualithonTest();
    FileHandling fh = new FileHandling();

    public boolean elementExists(By browserObject){
        WebElement element = null;
        Boolean elemDisp = false;
        try{
            element = getElement(browserObject);
            if(element != null){
                elemDisp = true;
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return elemDisp;
    }

    public WebElement getElement(By browserObject) {
        WebElement element = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(browserObject)));
            if (driver.findElements(browserObject).size() != 0) {
                //wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                //wait.until(ExpectedConditions.visibilityOf(driver.findElement(browserObject)));
                element = driver.findElement(browserObject);
                //getAndSwitchFrame(browserObject);
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return element;
    }

    public WebElement getElement(WebElement element) {
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            if (element.isDisplayed()) {
                wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return element;
    }

    public List<WebElement> getElements(By browserObject) {
        List<WebElement> elements = null;
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            //wait.until(ExpectedConditions.visibilityOf(getElement(browserObject)));
            wait.until(ExpectedConditions.presenceOfElementLocated(browserObject));
            if (getElement(browserObject) != null) {
                elements = driver.findElements(browserObject);
            }
        } catch (Exception e) {
            //report.logError(e.getMessage());
        }
        return elements;
    }

    public String getAttribute(By browserObject, String attributeName){
        String attributeValue = "";
        try{
            attributeValue = getElement(browserObject).getAttribute(attributeName);
        }catch (Exception e){
            report.logError(e.getMessage());
        }
        return attributeValue;
    }
    
    public String getConsoleLog() {
    	String consoleLog = "";
    	DevTools devTool = null;
    	try {
    		    		
    		driver = run.getDriver();
    		devTool = run.devTool;
    		List<String> list = new ArrayList<String>();
    		devTool.addListener(Log.entryAdded(), entry -> list.add(entry.getText()));
    		System.out.println("Log Size : " + list.size());
    		for(Object log:list) {
    			System.out.println("Logs are : " + log);
    		}
    		
    		String data = "data:image/png;base64,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";
    		String base64Image = data.split(",")[1];
    		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
    		
    		//System.out.println(getElement(By.id("notABotCanvas")).);
    		
    		
    		/*LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
    		List<LogEntry> logs = logEntries.getAll();
    		System.out.println(logEntries);
    		System.out.println(logs.size());
    		for (LogEntry log : logs) {
    			System.out.println("Message1 :->" + log.getMessage());
    			consoleLog = log.getMessage();
    		}*/
    		/*Logs logs = driver.manage().logs();
    		System.out.println("*****START Now*********");
    		LogEntries logEntries = logs.get(LogType.BROWSER);
//    		LogEntries logEntries1 = logs.get(LogType.CLIENT);
//    		LogEntries logEntries2 = logs.get(LogType.DRIVER);
//    		LogEntries logEntries3 = logs.get(LogType.PERFORMANCE);
//    		LogEntries logEntries4 = logs.get(LogType.PROFILER);
    		LogEntries logEntries5 = logs.get(LogType.SERVER);
			List<LogEntry> errorLogs = logEntries.getAll();
//			List<LogEntry> errorLogs1 = logEntries1.getAll();
//			List<LogEntry> errorLogs2 = logEntries2.getAll();
//			List<LogEntry> errorLogs3 = logEntries3.getAll();
//			List<LogEntry> errorLogs4 = logEntries4.getAll();
			List<LogEntry> errorLogs5 = logEntries5.getAll();
			String msg="";
			System.out.println("*****START*********");
			System.out.println("logentries: "+logEntries);
			System.out.println("Errorlogs: "+errorLogs);
		//	System.out.println("Errorlogs1: "+errorLogs1);
		//	System.out.println("Errorlogs2: "+errorLogs2);
		//	System.out.println("Errorlogs3: "+errorLogs3);
		//	System.out.println("Errorlogs4: "+errorLogs4);
			System.out.println("Errorlogs5: "+errorLogs5);
			int i=0;
			for (LogEntry logEntry : errorLogs) {
				String logMessage=logEntry.getMessage();
				Level loglevel=logEntry.getLevel();
			
					System.out.println("Found logs: " + logMessage +" and "+loglevel );
					System.out.println("Found suma logs: " + logMessage +" and "+loglevel.toString() );
					
					i++;
				}*/
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}    	
    	return consoleLog;
    }

    public String getAttribute(WebElement element, String attributeName){
        String attributeValue = "";
        try{
            attributeValue = getElement(element).getAttribute(attributeName);
        }catch (Exception e){
            report.logError(e.getMessage());
        }
        return attributeValue;
    }

    public String getScreenshot() {
        String destination = null;
        try {
            driver = run.getDriver();
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            //after execution, you could see a folder "FailedTestsScreenshots" under src folder
            destination = run.getScreenshotPath() + "\\" +dateName + ".png";
            File finalDestination = new File(destination);
            Files.copy(source, finalDestination);
            report.logReportWithScreenshot(finalDestination.getAbsolutePath());
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return destination;
    }

    public void launchBrowser(String url) {
        try {
            //driver = BrowserFactory.createInstance("chrome");
            //DriverFactory.getInstance().setDriver(driver);
        	        	
            driver = run.getDriver();
            driver.get(url);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Long.parseLong(config.getConfig("waitSeconds")), TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Long.parseLong(config.getConfig("pageloadWait")), TimeUnit.SECONDS);
            System.out.println();
            
            report.logReport(LogStatus.PASS, "Browser launched successfully and navigated to url - " + url);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void quitBrowser() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            Set<String> s = driver.getWindowHandles();
            String subWindowHandler = "", mainWindowHandler = "";
            Iterator<String> iterator = s.iterator();
            if (iterator.hasNext()) {
                mainWindowHandler = iterator.next();
            }
            while (iterator.hasNext()){
                subWindowHandler = iterator.next();
                driver.switchTo().window(subWindowHandler);
                driver.close();
            }
            driver.switchTo().window(mainWindowHandler);
            //closeDriver();
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public String getText(By browserObject) {
        String txt = null;
        try {
            driver = run.getDriver();
            txt = getElement(browserObject).getText();
        } catch (Exception e) {
            txt = "";
        }
        return txt;
    }

    public String getText(WebElement element) {
        String txt = null;
        try {
            driver = run.getDriver();
            txt = getElement(element).getAttribute("textContent");
        } catch (Exception e) {
            txt = "";
        }
        return txt;
    }
    
    public void sendKeys(By browserObject, Keys key) {
    	try {
    		getElement(browserObject).sendKeys(key);
    	}catch(Exception e) {
    		report.logError(e.getMessage());
    	}
    }
    
    public void sendKeys(By browserObject, String key) {
    	try {
    		getElement(browserObject).sendKeys(key);
    	}catch(Exception e) {
    		report.logError(e.getMessage());
    	}
    }

    public void setText(By browserObject, String txt) {
        try {
            driver = run.getDriver();
            if(txt.equalsIgnoreCase("RANDOM-NUMBER")){
                txt = String.valueOf((int)(Math.random() * 100000001));
            }else if(txt.equalsIgnoreCase("RANDOM-MNEMONIC")){
                txt = "MB" + (int)(Math.random() * 1000001);
            }else if(txt.equalsIgnoreCase("RANDOM-STRING")){
                txt = Long.toHexString(Double.doubleToLongBits(Math.random()));
            }
            if(txt.length() == 0){
                getElement(browserObject).clear();
                getElement(browserObject).sendKeys(Keys.TAB);
            }else{
                getElement(browserObject).sendKeys(txt);
                getElement(browserObject).sendKeys(Keys.TAB);
            }
            report.logInfo("Text value : " + txt + " set on " + getElement(browserObject).getAttribute("id") + " field");
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void dropdownSelect(By browserObject, String txt) {
        try {
            driver = run.getDriver();
            Select dropdown = new Select(getElement(browserObject));
            dropdown.selectByValue(txt);
            report.logInfo("Dropdown value : " + txt + " set on " + getElement(browserObject).getAttribute("id") + " field");
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void updateText(By browserObject, String txt) {
        try {
            driver = run.getDriver();
            getElement(browserObject).clear();
            if(txt.equalsIgnoreCase("RANDOM-NUMBER")){
                txt = String.valueOf((int)(Math.random() * 100000001));
            }else if(txt.equalsIgnoreCase("RANDOM-MNEMONIC")){
                txt = "MB" + (int)(Math.random() * 1000001);
            }else if(txt.equalsIgnoreCase("RANDOM-STRING")){
                txt = Long.toHexString(Double.doubleToLongBits(Math.random()));
            }
            if(txt.length() == 0){
                getElement(browserObject).clear();
                getElement(browserObject).sendKeys(Keys.TAB);
            }else{
                getElement(browserObject).sendKeys(txt);
                getElement(browserObject).sendKeys(Keys.TAB);
            }
            report.logInfo("Text value : " + txt + " updated on " + getElement(browserObject).getAttribute("id") + " field");
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clearText(By browserObject) {
        try {
            driver = run.getDriver();
            getElement(browserObject).clear();
            report.logInfo("Text cleared on - " + getElement(browserObject).getAttribute("id"));
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clickAlert(String inpArg){
        try {
            if (inpArg.contains("Return to Application") || inpArg.contains("Delete Button")) {
                driver = run.getDriver();
                WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                if (wait.until(ExpectedConditions.alertIsPresent()) != null){
                    driver.switchTo().alert().accept();
                }
            }
        }catch (Exception e){
        //    report.logError(e.getMessage());
        }
    }

    public void click(By browserObject, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            windowSize = getWindowCount();
            getElement(browserObject).click();
            report.logInfo("Clicked on - " + logDesc);
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            clickAlert(logDesc);
            switchToLatestWindow(windowSize);
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }
    
    public void clickNoLog(By browserObject) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            driver.findElement(browserObject).click();
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void click(WebElement element, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            windowSize = getWindowCount();
            element.click();
            report.logInfo("Clicked on - " + logDesc);
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            switchToLatestWindow(windowSize);
        }
        catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clickifavailable(By browserObject, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            windowSize = getWindowCount();
            if (driver.findElements(browserObject).size() != 0) {
                getElement(browserObject).click();
                report.logInfo("Clicked on - " + logDesc);
            }
            switchToLatestWindow(windowSize);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void clickifavailable(WebElement browserObject, String logDesc) {
        int windowSize = 0;
        try {
            driver = run.getDriver();
            windowSize = getWindowCount();
            if (browserObject.isDisplayed()) {
                getElement(browserObject).click();
                report.logInfo("Clicked on - " + logDesc);
            }
            switchToLatestWindow(windowSize);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    /*public void switchFrame(int frameIndex) {
        WebDriverWait wait = null;
        try {
            driver = run.getDriver();
            driver.switchTo().defaultContent();
            wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
            //driver.switchTo().frame(frameIndex);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }*/
    
    public void mouseHover(By browserObject) {
    	try {
    		driver = run.getDriver();
    		Actions action = new Actions(driver);
    		action.moveToElement(driver.findElement(browserObject)).perform();
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    }
    
    public void scrollToElement(By browserObject) {
    	try {
    		driver = run.getDriver();
    		WebElement element = driver.findElement(browserObject);
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    }
    
    public int getObjectCounts(By browserObject) {
    	List<WebElement> elements = null;
    	int elementsSize = 0;
    	try {
    		driver = run.getDriver();
    		elements = driver.findElements(browserObject);   
    		elementsSize = elements.size();
    	}catch(Exception e) {
    		e.printStackTrace();
    		report.logError(e.getMessage());
    	}
    	return elementsSize;
    }

    public void getAndSwitchFrame(By browserObject,String frame){
        WebDriverWait wait = null;
        try{
            driver = run.getDriver();
            driver.switchTo().defaultContent();
            List<WebElement> iframes = getElements(By.xpath("//"+frame));
            if (iframes != null){
                if(iframes.size() > 0){
                    for (WebElement iframe : iframes) {
                        wait = new WebDriverWait(driver, Long.parseLong(config.getConfig("waitSeconds")));
                        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
                        if(driver.findElements(browserObject).size() > 0 ){
                            if(driver.findElement(browserObject).isEnabled()){
                                report.logInfo("Switched To New Frame");
                                break;
                            }
                        }
                        driver.switchTo().defaultContent();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            report.logError(e.getMessage());
        }
    }

    public void switchToLatestWindow(int currWindowSize){
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            if(currWindowSize < getWindowCount()){
                Set<String> allwindows = driver.getWindowHandles();
                String subWindowHandler = "";
                Iterator<String> iterator = allwindows.iterator();
                while (iterator.hasNext()){
                    subWindowHandler = iterator.next();
                }
                driver.switchTo().window(subWindowHandler);
                driver.manage().timeouts().pageLoadTimeout(Long.parseLong(config.getConfig("pageloadWait")), TimeUnit.SECONDS);
                driver.manage().window().maximize();
                report.logInfo("Switched to new window");
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public int getWindowCount(){
        int windowCount = 0;
        try {
            windowCount = driver.getWindowHandles().size();
            //report.logInfo("Window Count - " + windowCount);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
        return windowCount;
    }

    /*public void switchNewWindow() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            String parent = driver.getWindowHandle();
            Set<String> s = driver.getWindowHandles();
            for (String child_window : s) {
                if (!parent.equals(child_window)) {
                    driver.switchTo().window(child_window);
                    driver.manage().window().maximize();
                    report.logInfo("Switched to new window - " + driver.getTitle());
                }
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }*/

    /*public void switchParentWindow() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }*/

    public void closeWindow() {
        try {
            Thread.sleep(Long.valueOf(config.getConfig("threadsleep")));
            driver = run.getDriver();
            driver.close();
            Set<String> s = driver.getWindowHandles();
            String subWindowHandler = "";
            Iterator<String> iterator = s.iterator();
            //System.out.println("Windows Opened - " + s.size());
            while (iterator.hasNext()){
                subWindowHandler = iterator.next();
            }
            driver.switchTo().window(subWindowHandler);
            //driver.close();
            //int windowSize = getWindowCount();
            //System.out.println("Windows Opened - " + windowSize);
            //switchToLatestWindow(windowSize);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void validateText(By browserObject, String expectedText, String logDescription) {
        try {
            driver = run.getDriver();
            String txt = getElement(browserObject).getText();
            if (txt.equalsIgnoreCase(expectedText)) {
                report.logReport(LogStatus.PASS, logDescription + "is successful");
            } else {
                report.logReport(LogStatus.FAIL, logDescription + "is failed");
            }
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }

    public void sleepsometime(long slp){
        try {
            Thread.sleep(slp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*public void getIframe() {
        try {
            driver = run.getDriver();
            int framecount = 0;
            List<WebElement> iframes = driver.findElements(By.tagName("frame"));
            for (WebElement iframe : iframes) {
                report.logInfo(iframe.getAttribute("id"));
                framecount = framecount + 1;
            }
            report.logInfo("Total Frame Count - " + framecount);
        } catch (Exception e) {
            report.logError(e.getMessage());
        }
    }*/

}

