package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import config.Configuration;
import filter.LomoFilter;

/**
 * @name: FunctionsTest.java
 * @author: sleepyocean
 * @date: created in 2018年2月4日 下午8:39:29
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class MainFrame extends JFrame {

	public void setMainFrameOutlook() {
		this.setTitle(Configuration.getAppName());
		setFrameSize();
		setMenuBar();
		setFrameLayout();
	}

	private void setFrameLayout() {
		this.setLayout(new BorderLayout());
	}

	private void setMenuBar() {
		MenuBar menuBar = new MenuBar();

		Menu menuFile = new Menu("File");
		Menu menuAbout = new Menu("About");

		MenuItem itemVideo = new MenuItem("Open Video Source");
		itemVideo.setShortcut(new MenuShortcut(KeyEvent.VK_V,false));
		MenuItem itemImage = new MenuItem("Opne Image");
		MenuItem itemExport = new MenuItem("Export");

		menuFile.add(itemVideo);
		menuFile.add(itemImage);
		menuFile.add(itemExport);

		MenuItem itemAbout = new MenuItem("Abot software");
		MenuItem itemUpdate = new MenuItem("Update");
		MenuItem itemContact = new MenuItem("Contact me");

		menuAbout.add(itemAbout);
		menuAbout.add(itemUpdate);
		menuAbout.add(itemContact);

		menuBar.add(menuFile);
		menuBar.add(menuAbout);

		this.setMenuBar(menuBar);
	}

	private void setJMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu("文件（F）");
		menuFile.setMnemonic('f');
		JMenu menuAbout = new JMenu("关于（A）");
		menuFile.setMnemonic('a');

		JMenuItem itemVideo = new JMenuItem("打开视频");
		JMenuItem itemImage = new JMenuItem("打开图片");
		JMenuItem itemExport = new JMenuItem("导出");

		menuFile.add(itemVideo);
		menuFile.add(itemImage);
		menuFile.add(itemExport);

		JMenuItem itemAbout = new JMenuItem("关于软件");
		JMenuItem itemUpdate = new JMenuItem("检查更新");
		JMenuItem itemContact = new JMenuItem("联系我");

		menuAbout.add(itemAbout);
		menuAbout.add(itemUpdate);
		menuAbout.add(itemContact);

		menuBar.add(menuFile);
		menuBar.add(menuAbout);

		this.setJMenuBar(menuBar);

	}

	private void setFrameSize() {
		// TODO 设置窗口大小
	}

	
	public void displayImg(String imagePath) {
		JLabel label = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				ImageIcon imageIcon = new ImageIcon(imagePath);
				g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), imageIcon.getImageObserver());

				Font font = new Font("Impact", Font.PLAIN, 100);
				Rectangle r = g.getClipBounds();
				new DescriptionSetter().centerString(g, r, "Marvel", font);
			}
		};

		this.add(label);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
