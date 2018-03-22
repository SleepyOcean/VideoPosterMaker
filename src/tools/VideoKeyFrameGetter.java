package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import config.Configuration;

/**
 * @name: VideoKeyFrameGetter.java
 * @author: sleepyocean
 * @date: created in 2018年2月27日 下午3:31:16
 * @version: 1.0
 * @description: 获取视频关键帧
 */

public class VideoKeyFrameGetter {
	private static int videoWidth;
	private static int videoHeight;

	public static void generateKeyFrame(String videoPath) throws IOException, InterruptedException {
		String command = "ffmpeg -i " + videoPath + " -vf select='eq(pict_type\\,I)' -vsync 2 -f image2 "
				+ Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "%d.jpeg";
		CommandExcutor.excute(command);
	}

	public static void setVideoWidthAndHeight(String videoPath) {
		try {
			File picture = new File(videoPath);
			BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));

			videoWidth = sourceImg.getWidth();
			videoHeight = sourceImg.getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> getKeyFrameList() {
		List<String> keyFramePathList = new ArrayList<String>();

		int[] count = FileOperator.getFileCount(getKeyFramePath());

		for (int i = 1; i < count[0] + 1; i++) {
			keyFramePathList.add(getKeyFramePath() + getKeyFramePrefixName() + i + ".jpeg");
		}

		return keyFramePathList;
	}

	private static String getKeyFramePrefixName() {
		return Configuration.getKeyFramePrefixName();
	}

	private static String getKeyFramePath() {
		return Configuration.getKeyFramePath();
	}
	
	public static int getVideoWidth() {
		return videoWidth;
	}

	public static int getVideoHeight() {
		return videoHeight;
	}
}
