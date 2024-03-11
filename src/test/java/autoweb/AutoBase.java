package autoweb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AutoBase {

	private static String projectPath = System.getProperty("user.dir");
	protected static final Properties prop;
	private static String os = System.getProperty("os.name").toLowerCase();
	private static String separator = new File("").separator;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	// Read Properties
	static {
		prop = new Properties();
		String propertiesPath = projectPath + separator + "src" + separator + "test" + separator +
				"resources" + separator + (AutoBase.class.getSimpleName().toLowerCase())+".properties";
		System.out.printf("propertiesPath : %s \n", propertiesPath);
		try {
			prop.load(Files.newBufferedReader(Paths.get(
					propertiesPath
			)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String screenShotPath = projectPath + separator + "photo" + separator;
	private static String logPath = projectPath + separator + "logs" + separator;


	public static RemoteWebDriver initDriver(String browser) throws MalformedURLException {

		if ("edge".equals(browser)) {
			EdgeOptions options = new EdgeOptions();
			// options.addArguments("--headless=new");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

		    LoggingPreferences logPrefs = new LoggingPreferences();
		    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		    options.setCapability(EdgeOptions.LOGGING_PREFS, logPrefs);

			File logLocation = new File(logPath, sdf.format(new Date()) +"_"+"edge.log");
		    EdgeDriverService service = new EdgeDriverService.Builder()
		    		.withLogFile(logLocation)
		    		.withLogOutput(System.out)
		    		.build();

	        URL address = new URL(prop.getProperty("grid.hub.url")); // "http://localhost:4444/wd/hub
	        return new RemoteWebDriver(address, options);

		} else if ("chrome".equals(browser)) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");

	        URL address = new URL(prop.getProperty("grid.hub.url"));
	        return new RemoteWebDriver(address, options);
		}

		return null;
	}


	/**
	 * Output LogFile while using selenium.
	 * @param logEntries
	 * @param filePath
	 */
    public static void outputLogFile(List<LogEntry> logEntries, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (LogEntry entry : logEntries) {
                writer.write(entry.toString()); // Assuming LogEntry class has overridden toString() method
                writer.newLine(); // Add newline character after each entry
            }
            System.out.println("Log entries have been written to the file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing log entries to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

	/**
	 * Capture ScreenShot
	 * @param fileName
	 * @return
	 */
	public String screenShot(String fileName, WebDriver driver){
		String res = null;
		Date date = new Date();

		try{
			File capturedScreen = ((TakesScreenshot)driver)
					.getScreenshotAs(OutputType.FILE);

			String absoluteScreenShotPath = this.screenShotPath + sdf.format(date) +"_"+fileName+".png";
			Files.copy(capturedScreen.toPath(), new File(absoluteScreenShotPath).toPath());
			res = absoluteScreenShotPath;

		}catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * View info of Broswer
	 */
	public void viewBrowserInfo(WebDriver driver) {
		System.out.println("######################## getBrowserInfo  ############################");
		System.out.println("######################## Cookie Info START  ############################");
		driver.manage().getCookies().forEach(System.out::println);
		driver.getCurrentUrl();
		driver.getTitle();
		System.out.println("######################## Cookie Info END  ############################");
	}

	/**
	 * View info of WebElement
	 * @param el
	 * @param name
	 */
	public void viewElementInfo(WebElement el, String name) {
		System.out.printf("######################## getElementInfo [%s] ############################ \n", name);
		System.out.printf("Text : %s, Tagname : %s \n", el.getText(), el.getTagName());
		System.out.printf("isDisplayed : %s, isEnabled : %s, isSelected : %s \n", el.isDisplayed(), el.isEnabled(), el.isSelected());
		System.out.printf("Location : [%s, %s] , Size : %s \n", el.getLocation().getX(), el.getLocation().getY(), el.getSize());
	}

}
