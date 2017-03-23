package cn.com.netty.demo.utils;

import org.apache.commons.lang3.StringUtils;

public class Log {

	public static void log(Object... objects) {
		System.err.println(StringUtils.join(objects, " "));
	}

}
