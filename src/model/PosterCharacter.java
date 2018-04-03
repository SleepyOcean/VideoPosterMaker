package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import config.Configuration;
import view.Param;

/**
 * @name: KeyFrameInfo.java
 * @author: sleepyocean
 * @date: created in 2018年2月12日 下午6:04:08
 * @version: 1.0
 * @description: 海报文字属性类
 */

public class PosterCharacter {
	/** 海报描述 */
	private String description;
	/** 海报标题 */
	private String title;
	/** 海报文字位置 */
	private int pos;
	/** 标题样式 */
	private Font titleFont;
	private Color titleColor;
	/** 描述样式 */
	private Font descriptionFont;
	private Color descriptionColor;

	public PosterCharacter(String title, String description, int pos, Font titleFont, Color titleColor,
			Font descriptionFont, Color descriptionColor) {
		this.description = description;
		this.title = title;
		this.pos = pos;
		this.titleFont = titleFont;
		this.titleColor = titleColor;
		this.descriptionFont = descriptionFont;
		this.descriptionColor = descriptionColor;
	}

	public PosterCharacter(String title, String description, int pos) {
		this.description = description;
		this.title = title;
		this.pos = pos;
		this.titleFont = new Font("Serif", Font.BOLD, 100);
		this.titleColor = Color.BLACK;
		this.descriptionFont = new Font("Serif", Font.BOLD, 20);
		this.descriptionColor = Color.WHITE;
	}

	public PosterCharacter(String title, String description) {
		this.description = description;
		this.title = title;
		this.pos = Param.CharacterPos.CENTER;
		this.titleFont = new Font("Serif", Font.BOLD, 100);
		this.titleColor = Color.BLACK;
		this.descriptionFont = new Font("Serif", Font.BOLD, 20);
		this.descriptionColor = Color.WHITE;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public Color getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor;
	}

	public Font getDescriptionFont() {
		return descriptionFont;
	}

	public void setDescriptionFont(Font descriptionFont) {
		this.descriptionFont = descriptionFont;
	}

	public Color getDescriptionColor() {
		return descriptionColor;
	}

	public void setDescriptionColor(Color descriptionColor) {
		this.descriptionColor = descriptionColor;
	}

	public String apply(String imagePath) {
		String outputPath = Configuration.getImgOutputPath() + "fontStyle.jpeg";
		try {
			File srcFile = new File(imagePath);
			Image srcImage = ImageIO.read(srcFile);
			int w = srcImage.getWidth(null);
			int h = srcImage.getHeight(null);

			BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(srcImage, 0, 0, w, h, null);

			int[] titleWH = getFontWidthAndHeight(title, g, titleFont);
			int[] descriptionWH = getFontWidthAndHeight(description, g, descriptionFont);
			int[] posCoord = new int[4];
			switch (pos) {
			case Param.CharacterPos.CENTER:
				posCoord = getCenterPos(w, h, titleWH[0], titleWH[1], titleWH[2], descriptionWH[0], descriptionWH[1],
						descriptionWH[2]);
				break;
			case Param.CharacterPos.LEFT_TOP:
				posCoord = getLTPos(w, h, titleWH[0], titleWH[1], titleWH[2], descriptionWH[0], descriptionWH[1],
						descriptionWH[2]);
				break;
			case Param.CharacterPos.LEFT_BOTTOM:
				posCoord = getLBPos(w, h, titleWH[0], titleWH[1], titleWH[2], descriptionWH[0], descriptionWH[1],
						descriptionWH[2]);
				break;
			case Param.CharacterPos.RIGHT_TOP:
				posCoord = getRTPos(w, h, titleWH[0], titleWH[1], titleWH[2], descriptionWH[0], descriptionWH[1],
						descriptionWH[2]);
				break;
			case Param.CharacterPos.RIGHT_BOTTOM:
				posCoord = getRBPos(w, h, titleWH[0], titleWH[1], titleWH[2], descriptionWH[0], descriptionWH[1],
						descriptionWH[2]);
				break;
			}

			int x1 = posCoord[0];
			int y1 = posCoord[1];
			int x2 = posCoord[2];
			int y2 = posCoord[3];

			g.setColor(titleColor);
			g.setFont(titleFont);
			g.drawString(title, x1, y1);

			g.setColor(descriptionColor);
			g.setFont(descriptionFont);
			g.drawString(description, x2, y2);

			g.dispose();

			FileOutputStream fos = new FileOutputStream(outputPath);
			ImageIO.write(bufferedImage, "jpg", fos);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputPath;
	}

	private int[] getFontWidthAndHeight(String content, Graphics2D g, Font font) {
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		return new int[] { fm.stringWidth(content), fm.getAscent(), fm.getDescent() };
	}

//	public static void main(String[] args) {
//		new PosterCharacter("沉睡的海洋", "制作于2018-04-02").apply("E:\\Wallpaper\\20\\wallhaven-247289.jpg");
//	}

	/**
	 * 
	 * @Title: getCenterPos @Description: TODO(这里用一句话描述这个方法的作用) @param @param W
	 *         Image Width @param @param H Image Height @param @param w Title
	 *         width @param @param h Title height @param @param m Description
	 *         width @param @param n Description height @param @return @return
	 *         int[] @throws
	 */
	private int[] getCenterPos(int W, int H, int w, int hu, int hd, int m, int nu, int nd) {
		return new int[] { W / 2 - w / 2, H / 2, W / 2 - m / 2, H / 2 + hd + nu + nd };
	}

	private int[] getLTPos(int W, int H, int w, int hu, int hd, int m, int nu, int nd) {
		return new int[] { W / 4 - w / 2, H / 4, W / 4 - m / 2, H / 4 + hd + nu + nd };
	}

	private int[] getLBPos(int W, int H, int w, int hu, int hd, int m, int nu, int nd) {
		return new int[] { W / 4 - w / 2, (3 * H) / 4, W / 4 - m / 2, (3* H) / 4 + hd + nu + nd };
	}

	private int[] getRTPos(int W, int H, int w, int hu, int hd, int m, int nu, int nd) {
		return new int[] { (3 * W) / 4 - w / 2, H / 4, (3 * W) / 4 - m / 2, H / 4 + hd + nu + nd };
	}

	private int[] getRBPos(int W, int H, int w, int hu, int hd, int m, int nu, int nd) {
		return new int[] { (3 * W) / 4 - w / 2, (3 * H) / 4, (3 * W) / 4 - m / 2, (3 * H) / 4 + hd + nu + nd };
	}
}
