package cn.com.netty.demo.utils;

import org.apache.commons.lang3.StringUtils;

public class Log {
	private static final String LINESEPRATOR = System.lineSeparator();

	public static void log(Object... objects) {
		System.err.println(StringUtils.join(objects, LINESEPRATOR));
	}

	public static void logSingleLine(Object... objects) {
		System.err.println(StringUtils.join(objects, " "));
	}
}
