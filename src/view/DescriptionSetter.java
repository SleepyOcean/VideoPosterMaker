package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** 
 * @name: DescriptionSetter.java
 * @author:  sleepyocean
 * @date: created in 2018年3月17日 下午5:15:13 
 * @version: 1.0 
 * @description: 海报主题描述设置
 */

public class DescriptionSetter {
	public void centerString(Graphics g, Rectangle r, String s, Font font) {
		FontRenderContext frc = new FontRenderContext(null, true, true);

		Rectangle2D r2D = font.getStringBounds(s, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rHeight = (int) Math.round(r2D.getHeight());
		int rX = (int) Math.round(r2D.getX());
		int rY = (int) Math.round(r2D.getY());

		int a = (r.width / 2) - (rWidth / 2) - rX;
		int b = (r.height / 2) - (rHeight / 2) - rY;

		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString(s, r.x + a, r.y + b);
	}
	
	public void outputEditedPic(String imageFolder, String imageName) throws IOException {
		File imageFile = new File(imageFolder + imageName);

		BufferedImage origin = ImageIO.read(imageFile);
		Graphics graphics = origin.getGraphics();
		Font font = new Font("Serif", Font.BOLD, 100);
		Rectangle r = new Rectangle(0, 0, origin.getWidth(), origin.getHeight());
		centerString(graphics, r, "Marvel", font);

		File editedImage = new File(imageFolder + "editedImg.jpg");
		ImageIO.write(origin, "JPEG", editedImage);
	}
}
