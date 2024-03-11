package autoweb.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyUtil {

	private final String separator = new File("").separator;
	protected final String projectPath = System.getProperty("user.dir");
	protected Properties prop;

	public PropertyUtil(String testCaseName) {
		this.prop = new Properties();
		String propertiesPath = projectPath + separator + "src" + separator + "test" + separator +
				"resources" + separator + (testCaseName.toLowerCase())+".properties";
		System.out.printf("propertiesPath : %s \n", propertiesPath);
		try {
			prop.load(Files.newBufferedReader(Paths.get(
					propertiesPath
			)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(final String key) {
		return getProperty(key, "");
	}

	public String getProperty(final String key, final String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
}
