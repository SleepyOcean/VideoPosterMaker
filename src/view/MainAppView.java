package view;

import java.io.File;
import java.net.MalformedURLException;

import config.Configuration;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import tools.FileOperator;
import tools.VideoKeyFrameGetter;

public class MainAppView extends Application {

	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Configuration.getAppName());

		try {
			setMainView();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void setMainView() throws MalformedURLException {

		VideoKeyFrameGetter.setVideoWidthAndHeight(
				Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "1.jpeg");

		primaryStage.setWidth(getMainWidth());
		primaryStage.setHeight(getMainHeight());
		primaryStage.setResizable(false);

		// AnchorPane rootLayout = new AnchorPane();
		// rootLayout.setPrefSize(getMainWidth(), getMainHeight());
		BorderPane borderPane = new BorderPane();
		borderPane.setPrefSize(getMainWidth(), getMainHeight());

		// set menu
		MenuBar menuBar = setMenu();
		borderPane.setTop(menuBar);

		// set selected keyframe
		Pane mainKeyframePane = setPosterView();
		borderPane.setCenter(mainKeyframePane);

		// set edit pane
		Pane editPane = setEditPane();
		borderPane.setRight(editPane);

		// set key frames
		ScrollPane keyframesPane = setKeyframesPane();
		borderPane.setBottom(keyframesPane);

		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ScrollPane setKeyframesPane() throws MalformedURLException {
		ScrollPane keyFrameScrollPane = new ScrollPane();

		HBox hBox = new HBox();
		int[] framesNum = FileOperator.getFileCount(Configuration.getKeyFramePath());
		int height = 200;

		int width = (VideoKeyFrameGetter.getVideoWidth() / VideoKeyFrameGetter.getVideoHeight()) * height;
		System.out.println("width:" + width + "height:" + height);
		for (int i = 0; i < framesNum[0]; i++) {
			Image image;
			// image = new Image(
			// "../frames/" + Configuration.getKeyFramePrefixName() + (i + 1) + ".jpeg");
			File file = new File(
					Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + (i + 1) + ".jpeg");
			image = new Image(file.toURI().toURL().toString(), true);
			ImageView imageView = new ImageView(image);

			imageView.setFitWidth(width);
			imageView.setFitHeight(height);

			hBox.getChildren().add(imageView);
		}

		// hBox.setPadding(new Insets(10, 0, 10, 0));
		keyFrameScrollPane.setContent(hBox);

		return keyFrameScrollPane;
	}

	private Pane setEditPane() {
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

		Label descriptionLabel = new Label("Poster Title");
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

	private Pane setPosterView() throws MalformedURLException {
		Pane mainKeyframePane = new Pane();
		mainKeyframePane.setPrefSize(1366, (VideoKeyFrameGetter.getVideoHeight() / VideoKeyFrameGetter.getVideoWidth())*1366);

		ImageView imageView = new ImageView(
				new Image((new File(Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "1.jpeg")).toURI().toURL().toString(), true));
		imageView.setFitWidth(1366);
		imageView.setFitHeight((VideoKeyFrameGetter.getVideoHeight() / VideoKeyFrameGetter.getVideoWidth())*1366);
		mainKeyframePane.getChildren().add(imageView);
		return mainKeyframePane;
	}

	private MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem fileItemVideo = new MenuItem("Open Video");
		MenuItem fileItemImage = new MenuItem("Open Image");
		menuFile.getItems().addAll(fileItemVideo, fileItemImage);

		Menu menuAbout = new Menu("About");
		MenuItem aboutItemAbout = new MenuItem("About");
		menuAbout.getItems().add(aboutItemAbout);

		menuBar.getMenus().addAll(menuFile, menuAbout);
		return menuBar;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private double getMainWidth() {
		return MainViewConf.getAppWidth();
	}

	private double getMainHeight() {
		return MainViewConf.getAppHeight();
	}

}
