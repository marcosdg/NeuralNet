package core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {

	public static Logger logger;

	public Log(String className)
	{
		String pathToConfigFile = new StringBuilder(pathToWorkSpace())
							        .append("/src/core/utils/log.properties")
							        .toString();
		logger = Logger.getLogger(className);
		try {
			LogManager.getLogManager().readConfiguration(
			        new FileInputStream(pathToConfigFile));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// To support loading files with relative paths.

	private String pathToWorkSpace() {
		return System.getProperty("user.dir");
	}

	public static void log(String msg)
	{
		if (logger != null) {
			logger.log(logger.getLevel(), msg);
		}
	}

}
