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
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class will add everything to the GUI and display it.
 */
public class GUI extends Application {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Application.launch(args);
	}

	/**
	 * This method is overwriting the Java.Application method.
	 * @param primaryStage - the primary stage of the GUI in which all nodes are linked.
	 * @throws Exception
	 */
	@Override
	public void start(final Stage primaryStage) throws Exception {
		// Fix for OS X El Capitan (fix for font error using Java FX on El Capitan)
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
		root.getChildren().add(menu.getBorder());


		KeyFrame frame = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Will get the user entered dimensions of the map and adjust the stage accordingly
				primaryStage.setWidth(menu.getWorld().getXdimension() * 24);
				primaryStage.setHeight(menu.getWorld().getYdimension() * 26);

				//If pause is not true the application will run
				if (!menu.Pause()) {
					menu.getWorld().run(menu.Toggle());
				}
				//If it is paused the application will only display the fixed positions of the world
				else{
					menu.getWorld().display();
				}
			}

		});

		TimelineBuilder.create().cycleCount(javafx.animation.Animation.INDEFINITE).keyFrames(frame).build().play();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
