package autoweb.practices.grid;


import java.time.Duration;
import java.util.Collections;
import java.util.logging.Level;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import autoweb.AutoBase;
import autoweb.utils.PropertyUtil;
import autoweb.utils.TesseractUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTest04 extends AutoBase {

	private PropertyUtil prop = new PropertyUtil(MyTest04.class.getSimpleName());
	private RemoteWebDriver driver;
	private TesseractUtil tu = TesseractUtil.getInstance();
	private String testCaseName = MyTest04.class.getSimpleName();


	@BeforeClass
	public void setup() throws Exception {
		log.info("Setup : {} ", testCaseName);
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
        driver.manage().window().setSize(new Dimension(1920, 1080));

        // Navigate to a webpage
        log.info("########## START Page Move ##########");
        // WebDriverWait 인스턴스 생성 (타임아웃: 10초)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // 시간계측 시작
        StopWatch sw = StopWatch.createStarted();

        // app start
		driver.get(prop.getProperty("target.url"));
		sw.split();
		log.info("Result of page-load {} , START-TIME : {}, LOADED-TIME : {} ", prop.getProperty("target.url"), sw.getStartTime(), sw.getTime());

        // 특정 요소가 표시될 때까지 기다림
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"))
        		//
        		);

        log.info("Result of element-load, START-TIME : {}, LOADED-TIME : {} ", sw.getStartTime(), sw.getTime());
        sw.stop();

		Thread.sleep(5000);
		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(a -> log.info(a));

		log.info("########## END Page Move ##########");
	}

	@AfterClass
	@AfterMethod
	public void finish() {
        // Close the browser
		if (driver != null) driver.quit(); driver = null;
		log.info("Finish : "+MyTest04.class.getSimpleName());
	}

	@Test
	public void test001() throws Exception {
        // Navigate to a webpage

		screenShot(testCaseName+"-001-executeApp", driver);
		WebElement el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"));
		viewElementInfo(el, "規約に同意ボタン");

		// 規約に同意ボタン을 클릭해보기
		el.click(); Thread.sleep(3000);
		screenShot(testCaseName+"-001-Click_kiyaku_to_move_screen_of_login", driver);


		log.info("########## START 로그인 선택 ##########");
		Actions ac = new Actions(driver);

		sendkeyLoop(ac, Keys.UP, 300, 3);
		sendkey(ac, Keys.ENTER, 3000);

		// Get PassCode
		String [] passcode = new String[8];
		passcode[0] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[4]/div/div"))
				.getText();
		passcode[1] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[5]/div/div"))
				.getText();
		passcode[2] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[6]/div/div"))
				.getText();
		passcode[3] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[7]/div/div"))
				.getText();
		passcode[4] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[8]/div/div"))
				.getText();
		passcode[5] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[9]/div/div"))
				.getText();
		passcode[6] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[10]/div/div"))
				.getText();
		passcode[7] = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div/div[3]/div[1]/div/div[1]/div/div[11]/div/div"))
				.getText();

		log.info("Passcode : {}", String.join("", passcode));

        screenShot(testCaseName+"-001-move_to_login", driver);

        log.info("########## END 로그인 선택 ##########");

        String originalWindow = driver.getWindowHandle();

        assert driver.getWindowHandles().size() == 1;
        log.info("originalWindow : {}", originalWindow);

        driver.switchTo().newWindow(WindowType.TAB);  Thread.sleep(5000);
        driver.get(prop.getProperty("sub.url.1"));    Thread.sleep(5000);
        screenShot(testCaseName+"-001-move_to_login-newtab_1", driver);

        driver.get(prop.getProperty("sub.url.2"));    Thread.sleep(5000);
        String secondWindow = driver.getWindowHandle();
        log.info("secondWindow : {}", secondWindow);

        el = driver.findElement(By.xpath("/html/body/div/div/main/div/button"));
        log.info("web-welcome-page-1 botton : {}", el.getText());
        el.click();  Thread.sleep(5000);
        screenShot(testCaseName+"-001-move_to_login-newtab_2", driver);

        el = driver.findElement(By.xpath("/html/body/div/div/main/div/button"));
        log.info("web-welcome-page-2 botton : {}", el.getText());
        el.click();  Thread.sleep(5000);
        screenShot(testCaseName+"-001-move_to_login-newtab_3", driver);

        el = driver.findElement(By.xpath("/html/body/div/div/div/main/div[3]/div/button[1]"));
        log.info("web-welcome-page-3 botton : {}", el.getText());
        el.click();  Thread.sleep(7000);
        screenShot(testCaseName+"-001-move_to_login-newtab_4", driver);

        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div[1]/div[3]/dl/dd[1]/input"));
        el.sendKeys(prop.getProperty("sub.login.id"));  Thread.sleep(3000);
        screenShot(testCaseName+"-001-move_to_login-newtab_5", driver);

        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div[1]/div[6]/div/button"));
        log.info("web-login-page-4 botton : {}", el.getText());
        el.click();  Thread.sleep(7000);
        screenShot(testCaseName+"-001-move_to_login-newtab_6", driver);


        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div/div[4]/dl/dd/div/input"));
        el.sendKeys(prop.getProperty("sub.login.pw"));  Thread.sleep(3000);
        screenShot(testCaseName+"-001-move_to_login-newtab_7", driver);

        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div/div[6]/div/button"));
        log.info("web-login-page-5 botton : {}", el.getText());
        el.click();  Thread.sleep(7000);
        screenShot(testCaseName+"-001-move_to_login-newtab_8", driver);


        el = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/a"));
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)", el);
        log.info("web-login-page-6 botton : {}", el.getText());
        el.click();  Thread.sleep(10000);

        String thirdWindow = "";
        for (String tabid : driver.getWindowHandles()) {
        	if (!originalWindow.equals(tabid) && !secondWindow.equals(tabid)) {
        		thirdWindow = tabid;
        	}
        }

        driver.switchTo().window(thirdWindow);

        int windowCnt = driver.getWindowHandles().size();
        log.info("thirdWindow : {}", thirdWindow);
        log.info("windowCnt : {}", windowCnt);
        driver.getWindowHandles().forEach(tabId -> log.info("WindowTab-ID : {}", tabId));
        screenShot(testCaseName+"-001-move_to_login-newtab_9", driver);

        el = driver.findElement(By.xpath("/html/body"));
        log.info("web-login-page-7 botton : {}", el.getText());
        char [] passcodeOnWeb = el.getText().toCharArray();

        driver.switchTo().window(secondWindow);  Thread.sleep(10000);

        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[1]")
        		).sendKeys(passcodeOnWeb[0]+"");
        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[2]")
        		).sendKeys(passcodeOnWeb[1]+"");
        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[3]")
        		).sendKeys(passcodeOnWeb[2]+"");
        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[4]")
        		).sendKeys(passcodeOnWeb[3]+"");
        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[5]")
        		).sendKeys(passcodeOnWeb[4]+"");
        driver.findElement(
        		By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[5]/dl/dd/div/div/div/input[6]")
        		).sendKeys(passcodeOnWeb[5]+"");

        Thread.sleep(2000);
        screenShot(testCaseName+"-001-move_to_login-newtab_10", driver);


        el = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/div/div/div[8]/div/button"));
        log.info("web-login-page-8 botton : {}", el.getText());
        el.click();  Thread.sleep(7000);
        screenShot(testCaseName+"-001-move_to_login-newtab_11", driver);

        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div/div[4]/div/a"));
        log.info("web-login-page-9 botton : {}", el.getText());
        el.click();  Thread.sleep(5000);
        screenShot(testCaseName+"-001-move_to_login-newtab_12", driver);

        el = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div/div/div/div[4]/div/a"));
        log.info("web-login-page-10 botton : {}", el.getText());
        el.click();  Thread.sleep(5000);
        screenShot(testCaseName+"-001-move_to_login-newtab_13", driver);


	}



}
