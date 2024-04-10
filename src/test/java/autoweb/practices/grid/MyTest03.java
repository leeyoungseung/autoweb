package autoweb.practices.grid;

import java.awt.Robot;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;
import java.util.logging.Level;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
// import org.junit.Test;
import org.testng.annotations.Test;

import autoweb.AutoBase;
import autoweb.utils.PropertyUtil;
import autoweb.utils.TesseractUtil;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTest03 extends AutoBase {

	private PropertyUtil prop = new PropertyUtil(MyTest03.class.getSimpleName());
	private RemoteWebDriver driver;
	// private EdgeDriver driver;
	private TesseractUtil tu = TesseractUtil.getInstance();


	// @BeforeTest
	@BeforeClass
	public void setup() throws Exception {
		EdgeDriverManager.getInstance(DriverManagerType.EDGE).setup();
		String driverPath = System.getProperty("webdriver.edge.driver");
		log.info("driverPath : {} ", driverPath);


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
		//options.addArguments("--user-agent=" + prop.getProperty("browser.edge.ua"));
//		options.addArguments("--user-data-dir=" + prop.getProperty("browser.edge.userdata.dir"));
//		options.addArguments("--profile-directory=" + prop.getProperty("browser.edge.userdata.profile.dir"));
        driver = initDriver("edge", options);
//	    driver = new EdgeDriver(options);

        driver.manage().window().setSize(new Dimension(1920, 1080));


        // Navigate to a webpage
        log.info("########## START Page Move ##########");
        // WebDriverWait 인스턴스 생성 (타임아웃: 10초)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 시간계측 시작
        StopWatch sw = StopWatch.createStarted();

        // app start
		driver.get(prop.getProperty("target.url"));
		sw.split();
		log.info("Result of page-load {} , START-TIME : {}, LOADED-TIME : {} ", prop.getProperty("target.url"), sw.getStartTime(), sw.getTime());

        // 특정 요소가 표시될 때까지 기다림
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"))
        		);

        log.info("Result of element-load, START-TIME : {}, LOADED-TIME : {} ", sw.getStartTime(), sw.getTime());
        sw.stop();

		Thread.sleep(10000);
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
		screenShot("001-executeApp", driver);

		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(System.out::println);

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

	}

	@Test
	public void test002() throws Exception {
		log.info("########## START View グロナビ ##########");
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.LEFT).perform(); Thread.sleep(1000);
		String ocrImagePath = screenShot("002-view-guronabi", driver);
		log.info(tu.ocrImage(ocrImagePath));
		log.info("########## END View グロナビ ##########");
	}

	@Test
	public void test003() throws Exception {
		log.info("########## START Close グロナビ ##########");
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.RIGHT).perform(); Thread.sleep(1000);
		screenShot("003-close-guronabi", driver);
		log.info("########## END Close グロナビ ##########");
	}

	@Test
	public void test004() throws Exception {
		log.info("########## START Move in Home ##########");
		screenShot("004-move-in-home-01", driver);
		Actions ac = new Actions(driver);
		for (int i=0; i<25; i++) {
			ac.sendKeys(Keys.DOWN).perform(); Thread.sleep(300);
		}
		ac.sendKeys(Keys.UP).perform(); Thread.sleep(300);
		Thread.sleep(3000);
		screenShot("004-move-in-home-02", driver);

		for (int i=0; i<12; i++) {
			ac.sendKeys(Keys.RIGHT).perform(); Thread.sleep(300);
		}
		Thread.sleep(3000);
		screenShot("004-move-in-home-03", driver);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (int i=0; i<3; i++) {
			ac.sendKeys(Keys.ENTER).perform(); Thread.sleep(5000);
			try {
		        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        		By.xpath("/html/body/div/main/div/div[1]/div[2]/div/div[1]/div[4]/div[1]/div/div"))
		        		);
		        break;
			} catch (TimeoutException e) {
				log.info("test004 TimeoutException COUNT {}", i);
			}
		}

		Thread.sleep(5000);
		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(a -> log.info(a));
		screenShot("004-move-in-home-04", driver);

		Thread.sleep(5000);
		ac.sendKeys(Keys.UP).perform();;  Thread.sleep(300);
		ac.sendKeys(Keys.UP).perform();;  Thread.sleep(300);
		ac.sendKeys(Keys.RIGHT).perform();;  Thread.sleep(300);
		ac.sendKeys(Keys.ENTER).perform();;  Thread.sleep(300);
		for (int i=0; i<3; i++) {
			ac.sendKeys(Keys.DOWN).perform(); Thread.sleep(300);
		}
		Thread.sleep(3000);
		screenShot("004-move-in-home-05", driver);

		for (int i=0; i<5; i++) {
			ac.sendKeys(Keys.BACK_SPACE).perform();  Thread.sleep(5000);
			if (driver.getCurrentUrl().equals(prop.getProperty("target.url")+"/tv#home")) {
				break;
			}
		}

		ac.sendKeys(Keys.BACK_SPACE).perform();  Thread.sleep(5000);
		screenShot("004-move-in-home-06", driver);

		log.info("########## END Move in Home ##########");
	}

	@Test
	public void test005() throws Exception {
		log.info("########## START Move to Search ##########");
		Actions ac = new Actions(driver);
		ac.sendKeys(Keys.LEFT).perform(); Thread.sleep(300);
		for (int i=0; i<12; i++) {
			ac.sendKeys(Keys.UP).perform(); Thread.sleep(300);
		}
		for (int i=0; i<2; i++) {
			ac.sendKeys(Keys.DOWN).perform(); Thread.sleep(300);
		}
		screenShot("005-move-to-search-01", driver);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		StopWatch sw = StopWatch.createStarted();
		ac.sendKeys(Keys.ENTER).perform();
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div[1]/div/span"))
        		);

		log.info("move to search START-TIME {} , END-TIME : {}", sw.getStartTime(), sw.getTime());
		sw.stop();
		Thread.sleep(3000);
		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(a -> log.info(a));
		screenShot("005-move-to-search-02", driver);

		ac.sendKeys(Keys.ENTER).perform();  Thread.sleep(300);
		ac.sendKeys(Keys.RIGHT).perform();  Thread.sleep(300);
		ac.sendKeys(Keys.ENTER).perform();  Thread.sleep(300);
		for (int i=0; i<4; i++) {
			ac.sendKeys(Keys.RIGHT).perform(); Thread.sleep(300);
		}
		Thread.sleep(1000);
		screenShot("005-move-to-search-03", driver);

		sw = StopWatch.createStarted();
		ac.sendKeys(Keys.ENTER).perform();
        el = wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div/div[4]/div[1]/div/div"))
        		);

		log.info("move to search START-TIME {} , END-TIME : {}", sw.getStartTime(), sw.getTime());
		sw.stop();
		Thread.sleep(5000);
		screenShot("005-move-to-search-04", driver);

		log.info("########## END Move to Search ##########");
	}



}
