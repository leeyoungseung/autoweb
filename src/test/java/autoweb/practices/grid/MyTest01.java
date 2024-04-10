package autoweb.practices.grid;

import java.awt.Robot;
import java.time.Duration;
import java.util.Collections;
import java.util.logging.Level;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
// import org.junit.Test;
import org.testng.annotations.Test;

import autoweb.AutoBase;
import autoweb.utils.PropertyUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTest01 extends AutoBase {

	private PropertyUtil prop = new PropertyUtil(MyTest01.class.getSimpleName());
	private RemoteWebDriver driver;

	@BeforeClass
	public void setup() throws Exception {
		log.info("Setup : "+MyTest03.class.getSimpleName());
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
        driver = initDriver("edge", options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        // Navigate to a webpage
        log.info("########## START Page Move ##########");
		driver.get(prop.getProperty("target.url")); Thread.sleep(10000);
		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(a -> log.info(a));

		log.info("########## END Page Move ##########");
	}

	@AfterClass
	public void finish() {
        // Close the browser
		if (driver != null) driver.quit(); driver = null;
		log.info("Finish : "+MyTest03.class.getSimpleName());
	}

	@Test
	public void test001() throws Exception {
        // Navigate to a webpage

		screenShot("001-executeApp", driver);
		WebElement el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"));
		viewElementInfo(el, "規約に同意ボタン");

		// 規約に同意ボタン을 클릭해보기
		el.click(); Thread.sleep(3000);
		screenShot("002-Click_kiyaku_to_move_screen_of_login", driver);


		log.info("########## START 로그인 선택 ##########");
		Actions actions = new Actions(driver);
        for (int i=0; i<2; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.UP).perform();
        	Thread.sleep(300);
        }
        log.info("########## ログインせずに利用を選ぶ ##########");
        //
    	actions.sendKeys(Keys.DOWN).perform();
    	Thread.sleep(500);
        actions.sendKeys(Keys.ENTER).perform();

        Thread.sleep(2000);
        screenShot("003-move_to_personal-info-input", driver);
        log.info("########## END 로그인 선택 ##########");
        log.info("");

		log.info("########## START 개인정보 입력 ##########");
		el = driver.findElement(
				By.xpath("/html/body/div/main/div/div[1]/div[1]/div[3]/div[4]/div/div[2]/div"));
		viewElementInfo(el, "郵便番号入力");

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
		viewElementInfo(el, "決定キー");
		el.click();
		Thread.sleep(3000);

		// 次へ
        el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[3]/div"));
		viewElementInfo(el, "次へボタン");

		Robot bot = new Robot();
		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);

        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(5000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(15000);
		screenShot("003-move_to_questionnaire", driver);
        log.info("########## END 개인정보 입력 ##########");

        log.info("########## START questionnaire:step=1 ##########");
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN).perform();  Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);
        for (int i=0; i<3; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(10000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(15000);
        screenShot("003-move_to_questionnaire-2", driver);
        log.info("########## END questionnaire:step=1 ##########");

        log.info("########## START questionnaire:step=2 ##########");
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
        actions.sendKeys(Keys.DOWN).perform();  Thread.sleep(1000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(1000);
		bot.mouseMove(726, 206);
		bot.mouseWheel(500); Thread.sleep(1500);
        for (int i=0; i<10; i++) {
        	actions.sendKeys(Keys.DOWN).perform();
        	Thread.sleep(300);
        }
        Thread.sleep(5000);
        actions.sendKeys(Keys.ENTER).perform(); Thread.sleep(15000);
        screenShot("003-move_to_Home", driver);
        log.info("########## END questionnaire:step=2 ##########");

        // Close the browser
        driver.quit();

	}


}
