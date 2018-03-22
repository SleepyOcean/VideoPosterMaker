package view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import config.Configuration;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tools.FileOperator;
import tools.VideoKeyFrameGetter;

public class MainApp extends Application {
	private Stage primaryStage;

	@FXML
	private TextField posterTitleInput;

	@FXML
	private ScrollPane keyFrameScrollPane;

	@FXML
	private void posterTitleInputKeyReleasedEvent(KeyEvent event) {
		System.out.println(posterTitleInput.getText());
	}

	@FXML
	private void keyFrameScrollPaneOnClick(MouseEvent event) {
		if (keyFrameScrollPane != null) {
			try {
				setKeyFrames();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("sp is null!");
		}
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Configuration.getAppName());

		setAllView();
	}

	private void setAllView() {
		try {
			FXMLLoader fxLoader = new FXMLLoader();
			fxLoader.setLocation(MainApp.class.getResource("MainFrame.fxml"));

			AnchorPane rootLayout = (AnchorPane) fxLoader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setWidth(1700);
			primaryStage.setHeight(1000);
			primaryStage.setResizable(false);
			// setKeyFrames();
			// VideoKeyFrameGetter.generateKeyFrame("D:\\Cache\\Avengers2.mp4");
			VideoKeyFrameGetter.setVideoWidthAndHeight(
					Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "1.jpeg");

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setKeyFrames() throws MalformedURLException {
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
		;
		if (keyFrameScrollPane != null) {
			// keyFrameScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);

			keyFrameScrollPane.setContent(hBox);
		} else {
			System.out.println("keyFrameScrollPane is null!");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
