package autoweb.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;

import autoweb.AutoBase;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractUtil {
	private TesseractUtil() {}
	private static class TesseractUtilHelper {
		private static final TesseractUtil TESSERACT = new TesseractUtil();
	}
	public static TesseractUtil getInstance() {
		return TesseractUtilHelper.TESSERACT;
	}

	protected static final Properties prop;
	private static String projectPath = System.getProperty("user.dir");
	private static String separator = new File("").separator;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static String tesseractDataPath = projectPath + separator + "tesseract" + separator;

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

	private static Tesseract getTesseract() {
		Tesseract instance = new Tesseract();
		instance.setDatapath(tesseractDataPath);
		instance.setLanguage("jpn");//"jpn+eng"
		return instance;
	}

	public static String ocrImage(String fileName) {
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
