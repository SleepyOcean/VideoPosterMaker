package view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import config.Configuration;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.PosterProduction;
import tools.FileOperator;

/**
 * @name: ViewFactory.java
 * @author: sleepyocean
 * @date: created in 2018年3月30日 下午9:55:24
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class ViewFactory {

	public static MenuBar createMenuBar(Menu... items) {
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(items);
		return menuBar;
	}

	public static Menu createMenu(String name, MenuItem... items) {
		Menu menu = new Menu(name);
		menu.getItems().addAll(items);
		return menu;
	}

	public static MenuItem createMenuItem(String name, EventHandler<ActionEvent> handler) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(handler);
		return item;
	}

	public static ImageView createImageView(String path, PosterProduction production, Object handle)
			throws MalformedURLException {
		ImageView imageView = new ImageView(new Image((new File(path)).toURI().toURL().toString(), true));
		imageView.setFitWidth(SizeManager.posterSize.getWidth());
		imageView.setFitHeight((production.getHeight() * SizeManager.posterSize.getWidth()) / production.getWidth());
		return imageView;
	}

	public static ComboBox<String> createComboBox(ObservableList<String> list, ViewCoordinate coordinate,
			ChangeListener<? super String> listener) {
		ComboBox<String> comboBox = new ComboBox<String>(list);
		comboBox.setValue(list.get(0));
		comboBox.setLayoutX(coordinate.getX());
		comboBox.setLayoutY(coordinate.getY());
		comboBox.getSelectionModel().selectedItemProperty().addListener(listener);
		return comboBox;
	}

	public static Label createLabel(String name, Font font, ViewCoordinate coordinate) {
		Label label = new Label(name);
		label.setFont(font);
		label.setLayoutX(coordinate.getX());
		label.setLayoutY(coordinate.getY());
		return label;
	}

	public static TextField createTextField(Font font, ViewCoordinate coordinate,TextFieldEventHandler handler) {
		TextField textField = new TextField();
		textField.setFont(font);
		textField.setLayoutX(coordinate.getX());
		textField.setLayoutY(coordinate.getY());
		textField.setOnKeyPressed(handler);
		handler.setTf(textField);
		return textField;
	}

	public static Button createButton(String name, Font font, ViewCoordinate coordinate) {
		Button button = new Button(name);
		button.setFont(font);
		button.setLayoutX(coordinate.getX());
		button.setLayoutY(coordinate.getY());
		return button;
	}

	public static Pane createPane(ViewSize size) {
		Pane pane = new Pane();
		pane.setPrefSize(size.getWidth(), size.getHeight());
		return pane;
	}

	public static ScrollPane createScrollPane(int orientation, ViewSize size, PosterProduction production,
			List<String> list, EventHandler<? super MouseEvent> event) throws MalformedURLException {
		ScrollPane scrollPane = new ScrollPane();

		Pane box = (orientation == Param.Orientation.HORIZONTAL) ? new HBox() : new VBox();

		int[] framesNum = FileOperator.getFileCount(Configuration.getKeyFramePath());
		int height = SizeManager.keyframesSize.getHeight();

		int width = (production.getWidth() * height) / production.getHeight();

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

			box.getChildren().add(imageView);
		}
		scrollPane.setContent(box);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		return scrollPane;
	}

}
