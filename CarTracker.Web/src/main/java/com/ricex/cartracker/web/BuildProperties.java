package com.ricex.cartracker.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Retrieves the properties of the current build
 * 
 * @author Mitchell
 *
 */
public class BuildProperties {

	private static Logger log = LoggerFactory.getLogger(BuildProperties.class);
	
	private static final String PROPERTIES_FILE = "/build.properties";
	
	/** The key for the version property */
	private static final String KEY_VERSION = "version";
	
	/** The key for the build date property */
	private static final String KEY_BUILD_DATE = "buildDate";
	
	/** The properties to use */
	private static Properties properties;
	
	/** Load in the properties from the properties file
	 * 
	 */
	static {
		properties = new Properties();
		try {
			InputStream propertiesIn = BuildProperties.class.getResourceAsStream(PROPERTIES_FILE);
			if (null !=  propertiesIn) {
				properties.load(propertiesIn);
				propertiesIn.close();
			}
			else {
				log.error("Unable to open build properties!");
			}
		}
		catch (IOException e) {
			log.error("Error loading properties file: " + PROPERTIES_FILE + "!", e);
		}
	}
	
	/** Returns the version of the application
	 * 
	 * @return The application version
	 */
	public static String getVersion() {
		return getProperty(KEY_VERSION);
	}
	
	/** Returns the build date of the application
	 * 
	 * @return The build date
	 */
	public static String getBuildDate() {
		return getProperty(KEY_BUILD_DATE);
	}
	
	private static String getProperty(String key) {
		return getProperty(key, "");
	}
	
	private static String getProperty(String key, String def) {
		return properties.getProperty(key, def);
	}
	
}
