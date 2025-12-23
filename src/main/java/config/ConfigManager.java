package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static final Properties prop = new Properties();
	
	static {
		
		System.out.println("Classpath root = " +
			    ConfigManager.class.getClassLoader().getResource(""));
		
		System.out.println(">>> ENTERING ConfigManager static block");
		
		
		
		String env = System.getProperty("env", "local"); //hardcoding system property of env to Local if not provided, system property is 
		                                                 //provided via Maven command or in Jenkins, this is not read from config files
		
		
		System.out.println(">>> env = " + env);
		
		String fileName = String.format("config-%s.properties", env);
		
		
		try(InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)){
			
			
			if(input == null) {
				
				throw new RuntimeException("Configuration file not found: " +fileName);
			}
			
			System.out.println(">>> InputStream = " + input);
			
			prop.load(input);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed to load configuration file: " + fileName, e);
		}
			
	}
	
	public static String get(String key) {
		
		String value = prop.getProperty(key);
		System.out.println(">>> ConfigManager.get('" + key + "') = " + value);
		return value;
	}
	
	public static int getInt(String key) {
		
		return Integer.parseInt(prop.getProperty(key));
	}
	
	public static long getLong(String key) {
		
		return Long.parseLong(prop.getProperty(key));
	}
	
	public static boolean getBool(String key) {
		
	    String sysValue = System.getProperty(key);
	    if (sysValue != null) {
	        return Boolean.parseBoolean(sysValue);
	    }

	    return Boolean.parseBoolean(prop.getProperty(key));
	}

}
