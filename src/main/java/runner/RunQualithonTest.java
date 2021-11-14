package runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v95.log.Log;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigFileReader;
import utilities.FileHandling;

@CucumberOptions(
        features = "src/test/java/features/",
        glue = {"steps"},
        tags = "@TREASURE",
        plugin = "json:target/cucumber-reports/CucumberTestReport.json")

public class RunQualithonTest extends AbstractTestNGCucumberTests {

	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static String configFile;
	private static ExtentReports extentRep = null;
    private static String screenshotPath;
    protected static ThreadLocal<ExtentTest> testScenario = new ThreadLocal<>();
    public static String cucumbertag = "";
    //public static ThreadLocal<DevTools> devTool = new ThreadLocal<>();
    public static DevTools devTool;
	
	ConfigFileReader config = new ConfigFileReader();
	FileHandling fileObj = new FileHandling();
    int scenario = 1;
	
	static {
        try {
            configFile = getConfigFilePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@BeforeClass
	public void beforeRun() {
		String repFoldName = fileObj.currDate();
        String reportPath = fileObj.createFolder(System.getProperty("user.dir") + config.getConfig("reportPath") + repFoldName );
        screenshotPath = fileObj.createFolder(reportPath + "/screenshots");
        extentRep = new ExtentReports(reportPath + "/report.html",true);
        extentRep.loadConfig(new File(System.getProperty("user.dir") + config.getConfig("extentReportConfigPath")));
        cucumbertag = readCucumberTag(this.getClass());
	}
	
	@BeforeMethod
	public void beforeHook() {
		/*DesiredCapabilities caps = DesiredCapabilities.chrome();
	    LoggingPreferences logPrefs = new LoggingPreferences();
	    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
	    logPrefs.enable(LogType.PROFILER, Level.ALL);
	    logPrefs.enable(LogType.BROWSER, Level.ALL);
	    logPrefs.enable(LogType.CLIENT, Level.ALL);
	    logPrefs.enable(LogType.DRIVER, Level.ALL);
	    logPrefs.enable(LogType.SERVER, Level.ALL);
	    caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		
		ChromeOptions chromeOption = new ChromeOptions();
		chromeOption.setCapability(ChromeOptions.CAPABILITY, caps);*/
		WebDriverManager.chromedriver().setup();
		WebDriverManager.chromedriver().browserVersion("95.0").arch64().setup();
		
		//System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "\\chromedriver.log");
		//System.setProperty("webdriver.chrome.verboseLogging", "false");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + config.getConfig("chromeDriverPath"));
		ChromeDriver chromeDriver = new ChromeDriver();		
		driver.set(chromeDriver);
		devTool = ((HasDevTools) getDriver()).getDevTools();
		devTool.createSession();
		devTool.send(Log.enable());
		testScenario.set(extentRep.startTest("Test", "desc"));
        scenario = scenario + 1;
	}
	
	public WebDriver getDriver() {
		return driver.get();
	}

	@AfterMethod
	public void afterHook() {
		extentRep.endTest(testScenario.get());
		getDriver().quit();
	}
	
	@AfterClass
    public void afterRun(){
        extentRep.flush();
        extentRep.close();
    }
	
	public static String readCucumberTag(Class<?> clazz) {
        String tags = "NULL";
        CucumberOptions co = clazz.getAnnotation(CucumberOptions.class);
        if (co != null) {
            tags = Arrays.asList(co.tags()).get(0);
        }
        return tags;
    }
	
	public static String getConfigFilePath() throws IOException {
        final String[] configPath = {""};
        try (Stream<Path> walkStream = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
                if (f.toString().endsWith("configuration.properties")) {
                    configPath[0] = f.toString();
                }
            });
        }
        return configPath[0];
    }
	
	public ExtentTest getScenario(){
        return testScenario.get();
    }
	
	public String getScreenshotPath() { return screenshotPath;}
	

}
