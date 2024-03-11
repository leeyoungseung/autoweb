package autoweb.singledocker;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import autoweb.AutoBase;
import autoweb.utils.PropertyUtil;

public class MyTest2 {


	private static String projectPath = System.getProperty("user.dir");
	private static String os = System.getProperty("os.name").toLowerCase();
	private static String dirSpliter = getDirSpliter(os);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String screenShotPath = projectPath + dirSpliter + "photo" + dirSpliter;
	private static DevTools devtool;

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
		// options.addArguments("--headless=new");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

	    LoggingPreferences logPrefs = new LoggingPreferences();
	    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
	    options.setCapability(EdgeOptions.LOGGING_PREFS, logPrefs);


		File logLocation = new File(projectPath + dirSpliter + "logs" + dirSpliter, sdf.format(new Date()) +"_"+"edge.log");
	    EdgeDriverService service = new EdgeDriverService.Builder()
	    		.withLogFile(logLocation)
	    		.withLogOutput(System.out)
	    		.build();

        // Set up WebDriver
//        WebDriver driver = new EdgeDriver(service, options);


        URL address = new URL("http://localhost:4444/wd/hub");
        RemoteWebDriver driver = new RemoteWebDriver(address, options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        // Navigate to a webpage
		driver.get(prop.getProperty("target.url"));

		Thread.sleep(7000);
		screenShot("001-executeApp", driver);

		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(System.out::println);

		WebElement el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"));
		getElementInfo(el, "規約に同意ボタン");

		// 規約に同意ボタン을 클릭해보기
		el.click(); Thread.sleep(3000);
		screenShot("002-Click_kiyaku_to_move_screen_of_login", driver);


		System.out.println("########## START 로그인 선택 ##########");
		Actions actions = new Actions(driver);
        for (int i=0; i<2; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.UP).perform();
        	Thread.sleep(300);
        }
        System.out.println("########## ログインせずに利用を選ぶ ##########");
        //
    	actions.sendKeys(Keys.DOWN).perform();
    	Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Thread.sleep(2000);
        screenShot("003-move_to_personal-info-input", driver);
        System.out.println("########## END 로그인 선택 ##########");
        System.out.println("");

		System.out.println("########## START 개인정보 입력 ##########");
		el = driver.findElement(
				By.xpath("/html/body/div/main/div/div[1]/div[1]/div[3]/div[4]/div/div[2]/div"));
		getElementInfo(el, "郵便番号入力");

        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        el.click();

        Thread.sleep(3000);
        screenShot("003-click-yuubin", driver);
        for (int i=0; i<7; i++) {
        	actions.sendKeys(Keys.ENTER).perform();
        	Thread.sleep(300);
        }
        el = driver.findElement(
				By.xpath("/html/body/div/main/div/div[2]/div[2]/div/div[60]/div/div"));
		getElementInfo(el, "決定キー");
		el.click();
		Thread.sleep(3000);

		// 次へ
        el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[3]/div"));
		getElementInfo(el, "次へボタン");

		Robot bot = new Robot();
		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);

        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(5000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(5000);
		screenShot("003-move_to_questionnaire", driver);
        System.out.println("########## END 개인정보 입력 ##########");

        System.out.println("########## START questionnaire:step=1 ##########");
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN).perform();  Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
//        bot.keyPress(KeyEvent.VK_ENTER); Thread.sleep(1000);
//        bot.keyPress(KeyEvent.VK_DOWN); Thread.sleep(1000);
//        bot.keyPress(KeyEvent.VK_ENTER); Thread.sleep(1000);

		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);
        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(2000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(5000);
        screenShot("003-move_to_questionnaire-2", driver);
        System.out.println("########## END questionnaire:step=1 ##########");

        System.out.println("########## START questionnaire:step=2 ##########");
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN).perform();  Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);
        for (int i=0; i<5; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(2000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(5000);
        screenShot("003-move_to_Home", driver);
        System.out.println("########## END questionnaire:step=2 ##########");

        // Close the browser
        driver.quit();

	}

	/**
	 * 브라우저의 각종 값을 출력
	 */
	public void getBrowserInfo(WebDriver driver) {
		System.out.println("######################## getBrowserInfo  ############################");
		System.out.println("######################## Cookie Info START  ############################");
		driver.manage().getCookies().forEach(System.out::println);
		driver.getCurrentUrl();
		driver.getTitle();
		System.out.println("######################## Cookie Info END  ############################");
	}

	/**
	 * WebElement의 각종 값을출력
	 * @param el
	 * @param name
	 */
	public void getElementInfo(WebElement el, String name) {
		System.out.printf("######################## getElementInfo [%s] ############################ \n", name);
		System.out.printf("Text : %s, Tagname : %s \n", el.getText(), el.getTagName());
		System.out.printf("isDisplayed : %s, isEnabled : %s, isSelected : %s \n", el.isDisplayed(), el.isEnabled(), el.isSelected());
		System.out.printf("Location : [%s, %s] , Size : %s \n", el.getLocation().getX(), el.getLocation().getY(), el.getSize());
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
