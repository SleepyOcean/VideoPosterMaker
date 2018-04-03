package main;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import config.Configuration;
import filter.LomoFilter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PosterGenerator;
import model.PosterProduction;
import tools.FileOperator;
import tools.StringOperator;
import tools.VideoKeyFrameGetter;
import view.Param;
import view.SizeManager;
import view.TextFieldEventHandler;
import view.TextFieldEventHandler.IEventHandler;
import view.ViewFactory;

public class MainApplication extends Application {
	private Stage primaryStage;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private Pane editPane;
	private ScrollPane keyframesPane;
	private Scene scene;
	private ImageView poster;

	private PosterProduction production;

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
		this.primaryStage = primaryStage;
		openFileChooser();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void openFileChooser() throws IOException, InterruptedException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("select video source");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Videos", "*.*"),
				new FileChooser.ExtensionFilter("MP4", "*.mp4"), new FileChooser.ExtensionFilter("rmvb", "*.rmvb"),
				new FileChooser.ExtensionFilter("mkv", "*.mkv"), new FileChooser.ExtensionFilter("avi", "*.avi"));

		String videoPath = fileChooser.showOpenDialog(primaryStage).getAbsolutePath();

		// 清空之前的关键帧
		clearCache();
		// 生成该视频的关键帧
		VideoKeyFrameGetter.generateKeyFrame(videoPath);
		// 创建海报对象
		production = new PosterProduction(videoPath, StringOperator.getUpperDir(videoPath));

		// TODO: 添加等待界面

		setMainView();
	}

	private void clearCache() {
		FileOperator.delAllFile(Configuration.getKeyFramePath());
	}

	private void setMainView() throws MalformedURLException {
		initStage();
		initViewFrame();

		setMenu();
		String defaultPoster = PosterGenerator.automaticGenerate(production);
		setMainPosterImageView(defaultPoster);
		setKeyFramesPane();
		setEditPane();

		scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void initStage() {
		primaryStage.setTitle(Configuration.getAppName());
		primaryStage.setWidth(SizeManager.appSize.getWidth());
		primaryStage.setHeight(SizeManager.appSize.getHeight());
		primaryStage.setResizable(false);
	}

	private void initViewFrame() {
		borderPane = new BorderPane();
		borderPane.setPrefSize(SizeManager.appSize.getWidth(), SizeManager.appSize.getHeight());
	}

	private void setMenu() {
		MenuItem itemVideo = ViewFactory.createMenuItem("Open Video", new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					openFileChooser();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Menu menuFile = ViewFactory.createMenu("File", itemVideo);
		menuBar = ViewFactory.createMenuBar(menuFile);
		borderPane.setTop(menuBar);
	}

	private void setMainPosterImageView(String defaultPoster) throws MalformedURLException {
		poster = ViewFactory.createImageView(defaultPoster, production, null);
		borderPane.setCenter(poster);
	}

	private void setKeyFramesPane() throws MalformedURLException {
		List<String> keyframes = VideoKeyFrameGetter.getKeyFrameList();
		keyframesPane = ViewFactory.createScrollPane(Param.Orientation.HORIZONTAL, SizeManager.keyframesSize,
				production, keyframes, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						try {
							Method method = arg0.getSource().getClass().getMethod("getIndex");
							int index = (int) method.invoke(arg0.getSource());
							String path = Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName()
									+ index + ".jpeg";
							production.setSourcePath(path);
							updatePosterImg(PosterGenerator.applyNewChange(production));
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
				});
		borderPane.setBottom(keyframesPane);
	}

	private void setEditPane() {
		editPane = ViewFactory.createPane(SizeManager.editorSize);

		List<Node> elements = new ArrayList<Node>();

		Font titleFont = new Font("Segoe UI Semibold", 18);
		Font normalFont = new Font("Segoe UI Semibold", 14);

		elements.add(ViewFactory.createLabel("Filter", titleFont, SizeManager.filterLabelCoordinate));
		elements.add(ViewFactory.createLabel("Font Style", titleFont, SizeManager.fontLabelCoordinate));
		elements.add(ViewFactory.createLabel("Color", normalFont, SizeManager.fontColorLabelCoordinate));
		elements.add(ViewFactory.createLabel("Style", normalFont, SizeManager.fontStyleLabelCoordinate));
		elements.add(ViewFactory.createLabel("Position", normalFont, SizeManager.fontPosLabelCoordinate));
		elements.add(ViewFactory.createLabel("Poster Title", titleFont, SizeManager.titleLabelCoordinate));
		elements.add(ViewFactory.createLabel("Description Label", titleFont, SizeManager.descriptionLabelCoordinate));
		elements.add(ViewFactory.createComboBox(FXCollections.observableArrayList("Lomo", "Guassa", "Blur"),
				SizeManager.filterComboCoordinate, new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						try {
							setFilter(arg2);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}));
		elements.add(ViewFactory.createComboBox(
				FXCollections.observableArrayList("black & white", "white & black", "blue & black", "black & blue",
						"blue & white", "white & blue", "white", "black", "blue"),
				SizeManager.fontColorComboCoordinate, new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						setFontColor(arg2);
						try {
							updatePosterImg(PosterGenerator.applyNewChange(production));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}));
		elements.add(ViewFactory.createComboBox(
				FXCollections.observableArrayList("conlas s", "Palatino Linotype m", "system l"),
				SizeManager.fontStyleComboCoordinate, new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						setFontStyle(arg2);
						try {
							updatePosterImg(PosterGenerator.applyNewChange(production));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}));
		elements.add(ViewFactory.createComboBox(
				FXCollections.observableArrayList("Center", "Left_top", "Left_bottom", "Right_top", "Right_bottom"),
				SizeManager.fontPosComboCoordinate, new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
						setFontPos(arg2);
						try {
							updatePosterImg(PosterGenerator.applyNewChange(production));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}));
		TextFieldEventHandler tfTitle = new TextFieldEventHandler();
		TextFieldEventHandler tfDescription = new TextFieldEventHandler();
		tfTitle.setHandler(new IEventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event, TextField tf) {
				if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
					if (tf != null) {
						production.getCharacter().setTitle(tf.getText().toString());
						try {
							updatePosterImg(PosterGenerator.applyNewChange(production));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Textfield is null!");
					}
				}
			}
		});
		tfDescription.setHandler(new IEventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event, TextField tf) {
				if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
					if (tf != null) {
						production.getCharacter().setDescription(tf.getText().toString());
						try {
							updatePosterImg(PosterGenerator.applyNewChange(production));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Textfield is null!");
					}
				}
			}
		});
		elements.add(ViewFactory.createTextField(normalFont, SizeManager.titleTFieldCoordinate, tfTitle));
		elements.add(ViewFactory.createTextField(normalFont, SizeManager.descriptionTFieldCoordinate, tfDescription));
		elements.add(ViewFactory.createButton("apply", titleFont, SizeManager.applyBtCoordinate));
		editPane.getChildren().addAll(elements);

		borderPane.setRight(editPane);
	}

	protected void setFontPos(String arg2) {
		switch (arg2) {
		case "Center":
			production.getCharacter().setPos(Param.CharacterPos.CENTER);
			break;
		case "Left_top":
			production.getCharacter().setPos(Param.CharacterPos.LEFT_TOP);
			break;
		case "Left_bottom":
			production.getCharacter().setPos(Param.CharacterPos.LEFT_BOTTOM);
			break;
		case "Right_top":
			production.getCharacter().setPos(Param.CharacterPos.RIGHT_TOP);
			break;
		case "Right_bottom":
			production.getCharacter().setPos(Param.CharacterPos.RIGHT_BOTTOM);
			break;
		}

	}

	// 设置滤镜，响应滤镜选择框的事件
	protected void setFilter(String filterName) throws MalformedURLException {
		// String from = production.getSourcePath();
		// String to = Configuration.getImgOutputPath() + filterName +
		// from.substring(from.lastIndexOf(".") - 1);
		// TODO: switch 语句
		production.setFilter(new LomoFilter());
		// FilterExecutor.exectue(new LomoFilter(), from, to);
		updatePosterImg(PosterGenerator.applyNewChange(production));
	}

	protected void setFontColor(String arg2) {
		switch (arg2) {
		case "black & white":
			production.getCharacter().setTitleColor(Color.BLACK);
			production.getCharacter().setDescriptionColor(Color.WHITE);
			break;
		case "white & black":
			production.getCharacter().setTitleColor(Color.WHITE);
			production.getCharacter().setDescriptionColor(Color.BLACK);
			break;
		case "blue & black":
			production.getCharacter().setTitleColor(Color.BLUE);
			production.getCharacter().setDescriptionColor(Color.BLACK);
			break;
		case "black & blue":
			production.getCharacter().setTitleColor(Color.BLACK);
			production.getCharacter().setDescriptionColor(Color.BLUE);
			break;
		case "blue & white":
			production.getCharacter().setTitleColor(Color.BLUE);
			production.getCharacter().setDescriptionColor(Color.WHITE);
			break;
		case "white & blue":
			production.getCharacter().setTitleColor(Color.WHITE);
			production.getCharacter().setDescriptionColor(Color.BLUE);
			break;
		case "white":
			production.getCharacter().setTitleColor(Color.WHITE);
			production.getCharacter().setDescriptionColor(Color.WHITE);
			break;
		case "black":
			production.getCharacter().setTitleColor(Color.BLACK);
			production.getCharacter().setDescriptionColor(Color.BLACK);
			break;
		case "blue":
			production.getCharacter().setTitleColor(Color.BLUE);
			production.getCharacter().setDescriptionColor(Color.BLUE);
			break;
		}
	}

	protected void setFontStyle(String arg2) {
		switch (arg2) {
		case "conlas s":
			production.getCharacter().setTitleFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 80));
			;
			production.getCharacter().setDescriptionFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 10));
			break;
		case "Palatino Linotype m":
			production.getCharacter().setTitleFont(new java.awt.Font("Palatino Linotype", java.awt.Font.BOLD, 100));
			;
			production.getCharacter()
					.setDescriptionFont(new java.awt.Font("Palatino Linotype", java.awt.Font.BOLD, 20));
			break;
		case "system l":
			production.getCharacter().setTitleFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 160));
			;
			production.getCharacter().setDescriptionFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 40));
			break;
		}
	}

	// 改变海报显示的图片
	protected void updatePosterImg(String path) throws MalformedURLException {
		Image image = new Image((new File(path)).toURI().toURL().toString());
		int vh = production.getHeight();
		int vw = production.getWidth();

		Rectangle clip = new Rectangle(1366, (vh * 1366) / vw);
		clip.setArcWidth(100);
		clip.setArcHeight(100);
		// poster.setClip(clip);
		poster.setFitWidth(1366);
		poster.setFitHeight((vh * 1366) / vw);
		poster.setImage(image);
	}
}
