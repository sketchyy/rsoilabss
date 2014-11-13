package com.sketchyy.rsoi1.oauth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsHolder {
	private static String propsFilename = "oauth.properties";
	private static Properties properties;

	public static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				InputStream inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(propsFilename);
				properties.load(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return properties;
	}
}
