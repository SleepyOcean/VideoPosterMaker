package view;

import java.io.File;
import java.net.MalformedURLException;

import config.Configuration;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tools.FileOperator;

/**
 * @name: VPMViewFactory.java
 * @author: sleepyocean
 * @date: created in 2018年3月27日 上午9:04:47
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class VPMViewFactory {

	/**
	 * 
	 * @Title: createKFsScrollPane
	 * @Description: 创建一个关键帧组的scrollpane
	 * @param w
	 * @param h
	 * @param event
	 * @return
	 * @throws MalformedURLException
	 * @return ScrollPane
	 */
	public static ScrollPane createKFsScrollPane(int w, int h, EventHandler<? super MouseEvent> event)
			throws MalformedURLException {
		ScrollPane keyFrameScrollPane = new ScrollPane();

		HBox hBox = new HBox();
		int[] framesNum = FileOperator.getFileCount(Configuration.getKeyFramePath());
		int height = 200;

		int width = (w * height) / h;
		for (int i = 0; i < framesNum[0]; i++) {
			Image image;
			// image = new Image(
			// "../frames/" + Configuration.getKeyFramePrefixName() + (i + 1) + ".jpeg");
			File file = new File(
					Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + (i + 1) + ".jpeg");
			image = new Image(file.toURI().toURL().toString(), true);
			KeyframeImageView imageView = new KeyframeImageView(image);

			imageView.setFitWidth(width);
			imageView.setFitHeight(height);
			imageView.setIndex(i + 1);
			imageView.setOnMouseClicked((EventHandler<? super MouseEvent>) event);

			hBox.getChildren().add(imageView);
		}
		keyFrameScrollPane.setContent(hBox);
		return keyFrameScrollPane;
	}

	/**
	 * 
	 * @Title: createEditPane
	 * @Description: 创建编辑菜单栏
	 * @param filterListener
	 * @param fontSizeListener
	 * @param
	 * @return Pane
	 */
	public static Pane createEditPane(ChangeListener<String> filterListener, ChangeListener<String> fontSizeListener) {
		Pane editPane = new Pane();

		editPane.setPrefSize(334, 768);

		Label filterLabel = new Label("Filter");
		filterLabel.setFont(new Font("Segoe UI Semibold", 18));
		filterLabel.setLayoutX(40);
		filterLabel.setLayoutY(20);

		ObservableList<String> filterOptions = FXCollections.observableArrayList("Lomo", "Guassa", "Blur");
		ComboBox<String> filterComboBox = new ComboBox<String>(filterOptions);
		filterComboBox.setPrefWidth(200);
		filterComboBox.setValue("Lomo");
		filterComboBox.setLayoutX(120);
		filterComboBox.setLayoutY(20);
		filterComboBox.getSelectionModel().selectedItemProperty().addListener(filterListener);

		Label fontLabel = new Label("Font Style");
		fontLabel.setFont(new Font("Segoe UI Semibold", 18));
		fontLabel.setLayoutX(40);
		fontLabel.setLayoutY(80);

		Label fontSizeLabel = new Label("Size");
		fontSizeLabel.setFont(new Font("Segoe UI Semibold", 14));
		fontSizeLabel.setLayoutX(50);
		fontSizeLabel.setLayoutY(130);

		ObservableList<String> fontSizeOptions = FXCollections.observableArrayList("8", "10", "16");
		ComboBox<String> fontSizeComboBox = new ComboBox<String>(fontSizeOptions);
		fontSizeComboBox.setPrefWidth(200);
		fontSizeComboBox.setValue("16");
		fontSizeComboBox.setLayoutX(120);
		fontSizeComboBox.setLayoutY(130);
		fontSizeComboBox.getSelectionModel().selectedItemProperty().addListener(fontSizeListener);

		Label fontStyleLabel = new Label("Style");
		fontStyleLabel.setFont(new Font("Segoe UI Semibold", 14));
		fontStyleLabel.setLayoutX(50);
		fontStyleLabel.setLayoutY(180);

		ObservableList<String> fontStyleOptions = FXCollections.observableArrayList("conlas", "yahei", "system");
		ComboBox<String> fontStyleComboBox = new ComboBox<String>(fontStyleOptions);
		fontStyleComboBox.setPrefWidth(200);
		fontStyleComboBox.setValue("system");
		fontStyleComboBox.setLayoutX(120);
		fontStyleComboBox.setLayoutY(180);

		Label titleLabel = new Label("Poster Title");
		titleLabel.setFont(new Font("Segoe UI Semibold", 18));
		titleLabel.setLayoutX(40);
		titleLabel.setLayoutY(240);

		TextField titleTField = new TextField();
		titleTField.setFont(new Font("Segoe UI Semibold", 14));
		titleTField.setLayoutX(50);
		titleTField.setLayoutY(280);

		Label descriptionLabel = new Label("Description Label");
		descriptionLabel.setFont(new Font("Segoe UI Semibold", 18));
		descriptionLabel.setLayoutX(40);
		descriptionLabel.setLayoutY(340);

		TextField descriptionTField = new TextField();
		descriptionTField.setFont(new Font("Segoe UI Semibold", 14));
		descriptionTField.setLayoutX(50);
		descriptionTField.setLayoutY(380);

		Button applyBt = new Button("apply");
		applyBt.setFont(new Font("Segoe UI Semibold", 18));
		applyBt.setLayoutX(40);
		applyBt.setLayoutY(440);

		editPane.getChildren().addAll(filterLabel, filterComboBox, fontLabel, fontSizeLabel, fontSizeComboBox,
				fontStyleLabel, fontStyleComboBox, titleLabel, titleTField, descriptionLabel, descriptionTField,
				applyBt);

		return editPane;
	}

	/**
	 * @Title: createMenu @Description: 创建菜单 @param @param
	 *         stage @param @return @return MenuBar @throws
	 */
	public static MenuBar createMenu(Stage stage) {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem fileItemVideo = new MenuItem("Open Video");
		MenuItem fileItemImage = new MenuItem("Open Image");
		menuFile.getItems().addAll(fileItemVideo, fileItemImage);

		Menu menuAbout = new Menu("About");
		MenuItem aboutItemAbout = new MenuItem("About");
		menuAbout.getItems().add(aboutItemAbout);

		fileItemVideo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("select video source");

				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Videos", "*.*"),
						new FileChooser.ExtensionFilter("MP4", "*.mp4"),
						new FileChooser.ExtensionFilter("rmvb", "*.rmvb"),
						new FileChooser.ExtensionFilter("mkv", "*.mkv"),
						new FileChooser.ExtensionFilter("avi", "*.avi"));

				String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
			}
		});

		fileItemImage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("select image source");

				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
						new FileChooser.ExtensionFilter("jpeg", "*.jpeg"),
						new FileChooser.ExtensionFilter("jpg", "*.jpg"),
						new FileChooser.ExtensionFilter("png", "*.png"));

				String path = fileChooser.showOpenDialog(stage).getAbsolutePath();
				// try {
				// changePosterImage(path);
				// } catch (MalformedURLException e) {
				// e.printStackTrace();
				// }
			}
		});

		menuBar.getMenus().addAll(menuFile, menuAbout);
		return menuBar;
	}

	/**
	 * @Title: createPosterPane @Description: 创建海报显示pane @param @param
	 *         imageView @param @param path @param @param vw @param @param
	 *         vh @param @return @param @throws MalformedURLException @return
	 *         Pane @throws
	 */
	public static Pane createPosterPane(ImageView imageView, String path, int vw, int vh) throws MalformedURLException {
		Pane mainKeyframePane = new Pane();
		double w = 1366;
		double h = 768;

		mainKeyframePane.setPrefSize(w, h);
		mainKeyframePane.getChildren().add(imageView);
		return mainKeyframePane;
	}

	/**
	 * @Title: createImageView 
	 * @Description: 创建海报imageView
	 * @param @param path
	 * @param @param vw
	 * @param @param vh
	 * @param @return
	 * @param @throws MalformedURLException    
	 * @return ImageView    
	 * @throws
	 */
	public static ImageView createImageView(String path, int vw, int vh) throws MalformedURLException {
		ImageView imageView = new ImageView(new Image((new File(path)).toURI().toURL().toString(), true));
		imageView.setFitWidth(1366);
		imageView.setFitHeight((vh * 1366) / vw);

		return imageView;
	}
}
