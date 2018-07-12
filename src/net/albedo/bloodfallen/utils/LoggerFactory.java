package net.albedo.bloodfallen.utils;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import net.albedo.bloodfallen.launcher.LogOutput;
import net.albedo.bloodfallen.launcher.rmi.ILogOutput;
import net.albedo.bloodfallen.launcher.rmi.impl.RmiManager;


public class LoggerFactory {
	private static final Map<Class, Logger> LOGGER_MAP = new HashMap<>();
	private static final RmiHandler RMI_HANDLER = new RmiHandler();

	// prevent construction :/
	private LoggerFactory() {
	}

	
	public static Logger getLogger(Class clazz) {
		if (LOGGER_MAP.containsKey(clazz))
			return LOGGER_MAP.get(clazz);
		final Logger logger = Logger.getLogger(clazz.getName());
		logger.setUseParentHandlers(false);
		logger.addHandler(RMI_HANDLER);
		logger.setLevel(Level.ALL);
		LOGGER_MAP.put(clazz, logger);
		return logger;
	}

	public static class RmiHandler extends StreamHandler {
		private static final ILogOutput LOG_OUTPUT = (ILogOutput) RmiManager.getRMI("LogOutput");

		@Override
		public synchronized void publish(LogRecord record) {
			try {
				record.getSourceMethodName(); // obtain caller class before
												// serialization.
				LOG_OUTPUT.log(record);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
