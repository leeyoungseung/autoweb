package autoweb.practices.grid;

import java.awt.Robot;
import java.time.Duration;

// import org.junit.Test;
import org.testng.annotations.Test;

import autoweb.AutoBase;
import autoweb.utils.PropertyUtil;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTest01 extends AutoBase {

	private PropertyUtil prop = new PropertyUtil(MyTest01.class.getSimpleName());

	@Test
	public void test001() throws Exception {

        RemoteWebDriver driver = initDriver("edge");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1920, 1080));

        // Navigate to a webpage
		driver.get(prop.getProperty("target.url"));
		Thread.sleep(7000);
		screenShot("001-executeApp", driver);

		driver.manage().logs().get(LogType.PERFORMANCE).getAll().forEach(System.out::println);

		WebElement el = driver.findElement(
				By.xpath("/html/body/div/main/div/div/div[1]/div[1]/div/div"));
		viewElementInfo(el, "規約に同意ボタン");

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
        System.out.println("########## END 개인정보 입력 ##########");

        System.out.println("########## START questionnaire:step=1 ##########");
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
        System.out.println("########## END questionnaire:step=1 ##########");

        System.out.println("########## START questionnaire:step=2 ##########");
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
        System.out.println("########## END questionnaire:step=2 ##########");

        // Close the browser
        driver.quit();

	}


}
