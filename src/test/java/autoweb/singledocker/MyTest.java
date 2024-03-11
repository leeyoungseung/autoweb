package autoweb.singledocker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import autoweb.AutoBase;
import autoweb.practices.grid.MyTest01;
import autoweb.utils.PropertyUtil;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;

public class MyTest {

	private static String projectPath = System.getProperty("user.dir");
	private static String os = System.getProperty("os.name").toLowerCase();
	private static String dirSpliter = getDirSpliter(os);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String screenShotPath = projectPath + dirSpliter + "photo" + dirSpliter;

	private PropertyUtil prop = new PropertyUtil(AutoBase.class.getSimpleName());

	@Test
	public void test001() throws Exception {
		System.out.printf("projectPath : %s, screenShotPath : %s \n", projectPath, screenShotPath);

		System.setProperty("webdriver.edge.driver", projectPath + dirSpliter +
				"driver" + dirSpliter + "linux" + dirSpliter +
				"edge" + dirSpliter + "122.0.2365.52" + dirSpliter + "msedgedriver");
		String driverPath = System.getProperty("webdriver.edge.driver");
		System.out.printf("driverPath : %s \n", driverPath);

		EdgeOptions options = new EdgeOptions();
		options.addArguments("--headless=new");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

	    LoggingPreferences logPrefs = new LoggingPreferences();
	    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
	    options.setCapability(EdgeOptions.LOGGING_PREFS, logPrefs);

	    String logPathDir = projectPath + dirSpliter + "logs" + dirSpliter; // /path/to/destination/

		File logLocation = new File(logPathDir, sdf.format(new Date()) +"_"+"00_edge.log");
	    EdgeDriverService service = new EdgeDriverService.Builder()
	    		.withLogFile(logLocation)
	    		.withLogOutput(System.out)
	    		.build();


        // Set up WebDriver
        WebDriver driver = new EdgeDriver(service, options);
        driver.manage().window().setSize(new Dimension(1920, 1080));


        // Navigate to a webpage
        driver.get(prop.getProperty("target.url"));
        Thread.sleep(3000);

        Thread.sleep(30000);
        screenShot("test-img", driver);

        writeToFile(
        		driver.manage().logs().get(LogType.PERFORMANCE).getAll(),
        		logPathDir + sdf.format(new Date()) +"_"+"edge_performance.log"
        );

        // Close the browser
        logLocation = null;
        driver.quit();

	}

    public static void writeToFile(List<LogEntry> logEntries, String filePath) {
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

	private static String getDirSpliter(String os) {
		System.out.printf("OS Info : %s \n", os);

		if (os.contains("win")) {
			return "\\";
		} else if (os.contains("nux") || os.contains("nix")) {
			return "/";
		} else {
			return "/"; // default는 linux
		}
	}

	/**
	 * ScreenShot
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

}
