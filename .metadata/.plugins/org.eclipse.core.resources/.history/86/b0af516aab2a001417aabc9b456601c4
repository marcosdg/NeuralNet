package core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

	public static Logger logger;

	public Log(String className)
	{
		String pathToConfigFile = new StringBuilder(pathToWorkSpace())
							        .append("/src/core/utils/log.properties")
							        .toString();
		String pathToLog = new StringBuilder(pathToUserHome())
									.append("/")
									.append(className)
									.append(".log")
									.toString();
		logger = Logger.getLogger(className);
		try {
			LogManager.getLogManager().readConfiguration(
			        new FileInputStream(pathToConfigFile));
			//FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler(pathToLog);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
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

	private String pathToUserHome() {
		return System.getProperty("user.home");
	}

	public static void log(String msg)
	{
		if (logger != null) {
			logger.log(Level.INFO, msg);
		}
	}

}
