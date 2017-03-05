package com.kslodowicz.tools.mtg.utils;

import org.apache.log4j.Logger;

public class ThreadUtils {
	private static final Logger LOGGER = Logger.getLogger(ThreadUtils.class);

	private ThreadUtils() {
	}

	public static void sleep(int seconds) {
		try {

			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			LOGGER.error(e);
			Thread.currentThread().interrupt();
		}
	}
}
