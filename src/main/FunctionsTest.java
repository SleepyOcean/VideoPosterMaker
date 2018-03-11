package main;

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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @name: FunctionsTest.java
 * @author: sleepyocean
 * @date: created in 2018年2月4日 下午8:39:29
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class FunctionsTest {
	JFrame frame;

	public FunctionsTest(JFrame frame) {
		this.frame = frame;
	}

	public void displayImg(String imagePath) {
		JLabel label = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				ImageIcon imageIcon = new ImageIcon(imagePath);
				g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), imageIcon.getImageObserver());

				Font font = new Font("Serif", Font.BOLD, 100);
				Rectangle r = g.getClipBounds();
				centerString(g, r, "Dry Mountain", font);
			}
		};

		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void outputEditedPic(String imageFolder, String imageName) throws IOException {
		File imageFile = new File(imageFolder + imageName);

		BufferedImage origin = ImageIO.read(imageFile);
		Graphics graphics = origin.getGraphics();
		Font font = new Font("Serif", Font.BOLD, 100);
		Rectangle r = new Rectangle(0, 0, origin.getWidth(), origin.getHeight());
		centerString(graphics, r, "Dry Mountain", font);

		File editedImage = new File(imageFolder + "editedImg.jpg");
		ImageIO.write(origin, "JPEG", editedImage);
	}

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
}
