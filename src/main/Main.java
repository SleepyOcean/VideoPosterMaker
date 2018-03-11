package main;

import java.io.IOException;

import javax.swing.JFrame;

import extension.FastBlurUtil;
import extension.FilterExec;
import tools.VideoKeyFrameGetter;

/**
 * @name: Main.java
 * @author: sleepyocean
 * @date: created in 2018年2月4日 下午8:36:46
 * @version: 1.0
 * @description: 主程序入口
 */

public class Main {
	public static void main(String[] args) {
		// System.out.println("Starting......");
		test2();
	}
	
	public static void test1() {
		JFrame frame = new JFrame("VideoPosterMaker");
		frame.setSize(1920, 1080);
		frame.setLocation(0, 0);

		FunctionsTest test = new FunctionsTest(frame);

		test.displayImg(".\\res\\p1.jpg");
		try {
			test.outputEditedPic(".\\res\\", "p1.jpg");
			FastBlurUtil.gaussianBlur(".\\res\\p1.jpg",".\\res\\gaussianBlur.jpg");
			FilterExec.filterExecBlur(".\\res\\girl.jpg",".\\res\\blur1-girl.jpg");
			FilterExec.filterExecSharpen(".\\res\\girl.jpg",".\\res\\sharpen1-girl.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test2() {
		try {
			VideoKeyFrameGetter.generateKeyFrame("D:\\Cache\\dt.mp4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
