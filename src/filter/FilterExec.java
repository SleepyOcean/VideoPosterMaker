package filter;

/**
 * @name: FilterExec.java
 * @author: sleepyocean
 * @date: created in 2018年2月27日 下午7:37:45
 * @version: 1.0
 * @description: TODO (write your description)
 */

import java.awt.image.*;

import javax.imageio.ImageIO;
import java.io.*;

public class FilterExec {

	public static void filterExecSharpen(String fromPath, String toPath) throws IOException {
		BufferedImage bufImage = ImageIO.read(new File(fromPath));
		sharpen(bufImage, toPath);
	}

	public static void filterExecBlur(String fromPath, String toPath) throws IOException {
		BufferedImage bufImage = ImageIO.read(new File(fromPath));
		blur(bufImage, toPath);
	}

	// 过滤图像
	public static void applyFilter(float[] data, BufferedImage bufImage, String toPath) throws IOException {
		if (bufImage == null)
			return; // 如果bufImage为空则直接返回
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp imageOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); // 创建卷积变换操作对象
		BufferedImage filteredBufImage = new BufferedImage(bufImage.getWidth(), bufImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB); // 过滤后的缓冲区图像
		imageOp.filter(bufImage, filteredBufImage);// 过滤图像，目标图像在filteredBufImage
		ImageIO.write(filteredBufImage, "JPEG", new File(toPath));
		bufImage = filteredBufImage; // 让用于显示的缓冲区图像指向过滤后的图像
	}

	// 模糊图像
	public static void blur(BufferedImage bufImage, String toPath) throws IOException {
		if (bufImage == null)
			return;
		float[] data = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.025f, 0.125f, 0.0625f, 0.125f, 0.0625f };
		applyFilter(data, bufImage, toPath);
	}

	// 锐化图像
	public static void sharpen(BufferedImage bufImage, String toPath) throws IOException {
		if (bufImage == null)
			return;
		float[] data = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f, -1.0f };
		applyFilter(data, bufImage, toPath);
	}

}
