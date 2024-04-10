package autoweb;

import java.io.File;
import java.text.SimpleDateFormat;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractTest {

	private static String projectPath = System.getProperty("user.dir");
	private static String separator = new File("").separator;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String screenShotPath = projectPath + separator + "photo" + separator;
	private static String tesseractDataPath = projectPath + separator + "tesseract" + separator;

	public static void main(String[] args) {
		String result = ocrImage(screenShotPath + "20240313031636_001-executeApp.png");
		System.out.println(result);
	}


	private static Tesseract getTesseract() {

		Tesseract instance = new Tesseract();
		instance.setDatapath(tesseractDataPath);
		instance.setLanguage("jpn");//"jpn+eng"
		return instance;

	}

	private static String ocrImage(String fileName) {

		Tesseract tesseract = getTesseract();

		String result = null;

		File file = new File(fileName);

		if(file.exists() && file.canRead()) {
			try {
				result = tesseract.doOCR(file);
			} catch (TesseractException e) {
				result = e.getMessage();
			}
		} else {
			result = "not exist";
		}
		return result;

	}

}
