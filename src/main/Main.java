package main;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import filter.FastBlurUtil;
import filter.FilterExec;
import filter.LomoFilter;
import tools.VideoKeyFrameGetter;
import view.MainFrame;

/**
 * @name: Main.java
 * @author: sleepyocean
 * @date: created in 2018年2月4日 下午8:36:46
 * @version: 1.0
 * @description: 主程序入口
 */

public class Main {
	public static void main(String[] args) {
		System.out.println("Application has been started.");
//		generateKeyFrame();
		try {
			test1();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test1() throws FileNotFoundException, IOException {
//		MainFrame test = new MainFrame();
////		String imgPath = new LomoFilter().apply();
//		File picture = new File(imgPath);
//		BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
//
//		test.setSize(sourceImg.getWidth(), sourceImg.getHeight());
//		test.setLocation(0, 0);
//
//		test.setMainFrameOutlook();
////		test.displayImg(imgPath);

	}

	public static void generateKeyFrame() {
		try {
			VideoKeyFrameGetter.generateKeyFrame("D:\\Cache\\Avengers.mp4");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
