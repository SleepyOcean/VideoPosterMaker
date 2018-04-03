package view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import config.Configuration;
import filter.FilterExecutor;
import filter.LomoFilter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PosterProduction;
import tools.FileOperator;
import tools.VideoKeyFrameGetter;

public class MainAppView extends Application {

	Stage primaryStage;
	private PosterProduction production;

	private BorderPane borderPane;
	private MenuBar menuBar;
	private Pane mainKeyframePane;
	private Pane editPane;
	private ScrollPane keyframesPane;
	private Scene scene;
	ImageView imageView;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Configuration.getAppName());
		try {
			setDefaultMainView();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	// 改变海报显示的图片
	protected void changePosterImage(String path) throws MalformedURLException {
		Image image = new Image((new File(path)).toURI().toURL().toString());
		int vh = production.getHeight();
		int vw = production.getWidth();

		Rectangle clip = new Rectangle(1366, (vh * 1366) / vw);
		clip.setArcWidth(100);
		clip.setArcHeight(100);
		// imageView.setClip(clip);
		imageView.setFitWidth(1366);
		imageView.setFitHeight((vh * 1366) / vw);

		imageView.setImage(image);
	}

	// 设置默认的主界面
	private void setDefaultMainView() throws MalformedURLException {

		String sourcePath = Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "1.jpeg";
		production = new PosterProduction(sourcePath, Configuration.getImgOutputPath());

		primaryStage.setWidth(getMainWidth());
		primaryStage.setHeight(getMainHeight());
		primaryStage.setResizable(false);

		borderPane = new BorderPane();
		borderPane.setPrefSize(getMainWidth(), getMainHeight());

		// set imageView
		imageView = VPMViewFactory.createImageView(sourcePath, production.getWidth(), production.getHeight());

		// set menu
		menuBar = VPMViewFactory.createMenu(primaryStage);
		borderPane.setTop(menuBar);

		// set selected keyframe
		mainKeyframePane = VPMViewFactory.createPosterPane(imageView, sourcePath, production.getWidth(),
				production.getHeight());
		borderPane.setCenter(mainKeyframePane);

		// set edit pane
		editPane = VPMViewFactory.createEditPane(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				try {
					setFilter(arg2);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}, new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				setFontSize(arg2);
			}
		});
		borderPane.setRight(editPane);

		// set key frames
		keyframesPane = VPMViewFactory.createKFsScrollPane(production.getWidth(), production.getHeight(),
				keyframesMouseClickedEvent);
		borderPane.setBottom(keyframesPane);

		scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	protected void setFontSize(String arg2) {
		setFontSize(arg2);
	}

	// 设置滤镜，响应滤镜选择框的事件
	protected void setFilter(String filterName) throws MalformedURLException {
		String from = imageView.getImage().getUrl().toString().substring(6);
		String to = Configuration.getImgOutputPath() + filterName + from.substring(from.lastIndexOf(".") - 1);
		// System.out.println("from: " + from);
		// System.out.println("to: " + to);
		FilterExecutor.exectue(new LomoFilter(from, to));
		changePosterImage(to);
	}

	// 底栏关键帧组的点击事件
	private EventHandler<MouseEvent> keyframesMouseClickedEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {
			try {
				Method method = arg0.getSource().getClass().getMethod("getIndex");
				int index = (int) method.invoke(arg0.getSource());
				String path = Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + index + ".jpeg";
				changePosterImage(path);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	};

	private double getMainWidth() {
		return MainViewConf.getAppWidth();
	}

	private double getMainHeight() {
		return MainViewConf.getAppHeight();
	}

}
