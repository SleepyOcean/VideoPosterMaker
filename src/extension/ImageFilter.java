package extension;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @name: ImageFilter.java
 * @author: sleepyocean
 * @date: created in 2018年2月11日 下午4:11:17
 * @version: 1.0
 * @description: 图片滤镜处理类
 */

public class ImageFilter {
	public static void Lomo(String fromPath, String toPath) throws IOException {

		BufferedImage fromImage = ImageIO.read(new File(fromPath));

		int width = fromImage.getWidth();
		int height = fromImage.getHeight();
		BufferedImage toImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int a, r, g, b, grayValue = 0;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = fromImage.getRGB(i, j);
				// 过滤
				a = rgb & 0xff000000;
				r = (rgb & 0xff0000) >> 16;
				g = (rgb & 0xff00) >> 8;
				b = (rgb & 0xff);

				b = ModeSmoothLight(b, b);
				g = ModeSmoothLight(g, g);
				r = ModeSmoothLight(r, r);
				b = ModeExclude(b, 80);
				g = ModeExclude(g, 15);
				r = ModeExclude(r, 5);

				grayValue = a | (r << 16) | (g << 8) | b;
				toImage.setRGB(i, j, grayValue);
			}
		}
		ImageIO.write(toImage, "jpg", new File(toPath));
	}

	private static int ModeSmoothLight(int basePixel, int mixPixel) {
		int res = 0;
		res = mixPixel > 128
				? ((int) ((float) basePixel + ((float) mixPixel + (float) mixPixel - 255.0f)
						* ((Math.sqrt((float) basePixel / 255.0f)) * 255.0f - (float) basePixel) / 255.0f))
				: ((int) ((float) basePixel + ((float) mixPixel + (float) mixPixel - 255.0f)
						* ((float) basePixel - (float) basePixel * (float) basePixel / 255.0f) / 255.0f));
		return Math.min(255, Math.max(0, res));
	}

	private static int ModeExclude(int basePixel, int mixPixel) {
		int res = 0;
		res = (mixPixel + basePixel) - mixPixel * basePixel / 128;
		return Math.min(255, Math.max(0, res));
	}

	public static void main(String[] args) throws Exception {
		Lomo(".\\res\\p1.jpg", ".\\res\\output.jpg");

	}
}
