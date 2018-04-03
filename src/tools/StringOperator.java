package tools;

/**
 * @name: StringOperator.java
 * @author: sleepyocean
 * @date: created in 2018年3月25日 下午1:10:38
 * @version: 1.0
 * @description: 字符串处理类
 */

public class StringOperator {
	public static String getUpperDir(String path) {
		return path.substring(0, path.lastIndexOf("\\") + 1);
	}
}
