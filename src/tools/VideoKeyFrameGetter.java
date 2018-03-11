package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @name: VideoKeyFrameGetter.java
 * @author: sleepyocean
 * @date: created in 2018年2月27日 下午3:31:16
 * @version: 1.0
 * @description: 获取视频关键帧
 */

public class VideoKeyFrameGetter {
	// ffmpeg抽取关键帧的缓存路径
	public static String IMG_PATH = "D:\\Cache\\ffmpeg";
	// 关键帧图片名前缀
	public static String IMG_NAME_PREFIX = "keyFrame-";

	public static void generateKeyFrame(String videoPath) throws IOException {
		String command = "ffmpeg -i " + videoPath + " -vf select='eq(pict_type\\,I)' -vsync 2 -f image2 "
				+ IMG_NAME_PREFIX + "%d.jpeg";
		String line = null;
		StringBuilder result = new StringBuilder();

		System.out.println("starting to extract the key frame...");
		System.out.println(command);
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("cmd cd cache");
		
		process = runtime.exec("cmd dir");
//		Process process = runtime.exec(command);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		while ((line = bufferedReader.readLine()) != null) {
			result.append(line + "\n");
		}
		System.out.println(result);
		System.out.println("done.");
	}

	public static List<String> getKeyFrameList() {
		List<String> keyFramePathList = new ArrayList();

		// TODO get all key frame's path , and save to the list .

		return keyFramePathList;
	}

}
