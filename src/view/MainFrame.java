package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

/**
 * @name: MainFrame.java
 * @author: sleepyocean
 * @date: created in 2018年2月4日 下午9:03:43
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class MainFrame {
	JLabel label;
	JFileChooser chooser;

	class ImageViewerFrame extends JFrame {

		public ImageViewerFrame() {
			super("ImageViewer");
			setSize(WIDTH, HEIGHT);
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				//
			}

			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			JMenu menu = new JMenu("File");
			JMenuItem openItem = new JMenuItem("open");
			menu.add(openItem);
			openItem.addActionListener(new FileOpenListener());
			JMenuItem exitItem = new JMenuItem("exit");
			menu.add(exitItem);
			menuBar.add(menu);
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});

			// use a label to display a image
			label = new JLabel();
			add(label, BorderLayout.CENTER);

			chooser = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "gif");
			chooser.setFileFilter(filter);
			// 预览
			chooser.setAccessory(new ImagePreviewer(chooser));
			// accessory 通常用于显示已选中文件的预览图像

			// chooser.setFileView(new FileIconView(filter,new ImageIcon("palette.gif")));
			chooser.setFileView(new FileIconView(filter, new ImageIcon()));
			// 设置用于检索 UI 信息的文件视图，如表示文件的图标或文件的类型描述。

		}

		private class FileOpenListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chooser.setCurrentDirectory(new File("."));
				int result = chooser.showOpenDialog(ImageViewerFrame.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					String name = chooser.getSelectedFile().getPath();
					ImageIcon icon = new ImageIcon(name);
					// 等比缩放条件
					// int imgWidth=icon.getIconWidth();
					// int imgHeight=icon.getIconHeight();
					// int conWidth=getWidth();
					// int conHeight=getHeight();
					// int reImgWidth;
					// int reImgHeight;
					// if(imgWidth/imgHeight>=conWidth/conHeight){
					// if(imgWidth>conWidth){
					// reImgWidth=conWidth;
					// reImgHeight=imgHeight*reImgWidth/imgWidth;
					// }else{
					// reImgWidth=imgWidth;
					// reImgHeight=imgHeight;
					// }
					// }else{
					// if(imgWidth>conWidth){
					// reImgHeight=conHeight;
					// reImgWidth=imgWidth*reImgHeight/imgHeight;
					// }else{
					// reImgWidth=imgWidth;
					// reImgHeight=imgHeight;
					// }
					// }
					// 这个是强制缩放到与组件(Label)大小相同
					icon = new ImageIcon(
							icon.getImage().getScaledInstance(getWidth(), getHeight() - 25, Image.SCALE_DEFAULT));
					// 这个是按等比缩放
					// icon=new ImageIcon(icon.getImage().getScaledInstance(reImgWidth, reImgHeight,
					// Image.SCALE_DEFAULT));
					label.setIcon(icon);
					label.setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
		}

		public static final int WIDTH = 500;
		public static final int HEIGHT = 500;

		private JLabel label;
		private JFileChooser chooser;
	}

	class FileIconView extends FileView {
		public FileIconView(FileFilter aFilter, Icon anIcon) {
			filter = aFilter;
			icon = anIcon;
		}

		public Icon getIcon(File f) {
			if (!f.isDirectory() && filter.accept(f)) {
				return icon;
			} else
				return null;
		}

		private FileFilter filter;
		private Icon icon;
	}

	class ImagePreviewer extends JLabel {
		public ImagePreviewer(JFileChooser chooser) {
			setPreferredSize(new Dimension(100, 100));
			setBorder(BorderFactory.createEtchedBorder());
			chooser.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
						File f = (File) event.getNewValue();
						if (f == null) {
							setIcon(null);
							return;
						}
						ImageIcon icon = new ImageIcon(f.getPath());
						// if(icon.getIconWidth()>getWidth()){
						icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
						// }
						setIcon(icon);
					}
				}
			});
		}
	}

}
