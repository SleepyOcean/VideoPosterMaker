package tools;

import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @name: FileOperator.java
 * @author: sleepyocean
 * @date: created in 2018年3月16日 下午1:28:11
 * @version: 1.0
 * @description: 文件处理类
 */

public class FileOperator {
	public static int[] getFileCount(String path) {
		File file = new File(path);
		File[] fileArr = file.listFiles();
		int fileCount = 0;
		int directoryCount = 0;
		int[] count = new int[2];
		for (File f : fileArr) {
			if (f.isDirectory()) {
				directoryCount++;
			} else if(f.isFile()) {
				fileCount++;
			}
		}
		count[0] = fileCount;
		count[1] = directoryCount;
		return count;
	}
	
	//功能：输出系统字体到systemFontSet.txt			可以删除
	public static void outputSystemFontToFile() {
		try {
			File file = new File(".\\systemFontSet.txt");
			StringBuilder fontSet = new StringBuilder();
			String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (String fontname : fontNames)
				fontSet.append(fontname + "\r\n");
			bw.write(fontSet.toString(), 0, fontSet.length() - 1);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
