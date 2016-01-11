package Project1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// Fix for OS X El Capitan
		try {
			Class<?> macFontFinderClass = Class.forName("com.sun.t2k.MacFontFinder");
			Field psNameToPathMap = macFontFinderClass.getDeclaredField("psNameToPathMap");
			psNameToPathMap.setAccessible(true);
			psNameToPathMap.set(null, new HashMap<String, String>());
		} catch (Exception e) {
			// ignore
		}

		Group root = new Group();
		final Scene scene = new Scene(root);

		final Menu menu = new Menu(primaryStage, root);
		root.getChildren().add(menu.getMenuBar());


		KeyFrame frame = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				primaryStage.setWidth(menu.getWorld().getXdimension() * 24);
				primaryStage.setHeight(menu.getWorld().getYdimension() * 26);
				if (!menu.Pause()) {
					menu.getWorld().run(menu.Toggle());
				} else{
					menu.getWorld().display();
				}
			}

		});

		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
