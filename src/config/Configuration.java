package config;

/**
 * @name: Configuration.java
 * @author: sleepyocean
 * @date: created in 2018年3月16日 下午1:15:28
 * @version: 1.0
 * @description: 全局配置类
 */

public class Configuration {
	//软件名
	private static String APP_NAME = "VideoPosterMaker";
	// ffmpeg抽取关键帧的缓存路径
	private static String KEYFRAMES_PATH = "D:\\Cache\\frames\\";
	// 滤镜效果输出路径
	private static String IMG_OUTPUT_PATH = "D:\\Cache\\outputs\\";
	// 关键帧图片名前缀
	public static String IMG_NAME_PREFIX = "keyFrame-";

	
	public static String getImgOutputPath() {
		return IMG_OUTPUT_PATH;
	}

	public static String getKeyFramePrefixName() {
		return IMG_NAME_PREFIX;
	}

	public static String getKeyFramePath() {
		return KEYFRAMES_PATH;
	}
	
	public static String getAppName() {
		return APP_NAME;
	}
}
