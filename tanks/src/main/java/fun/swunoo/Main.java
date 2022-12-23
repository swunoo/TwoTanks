package fun.swunoo;

import fun.swunoo.Data.Props;
import fun.swunoo.Data.Sizes;
import fun.swunoo.Logic.GameArea;
import fun.swunoo.UI.LayoutBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *  This class, Main, sets the scene and launches the application. 
 */

public class Main extends Application{

    public static void main(String[] args) {
        launch();
    }

    /*
     * Defining abstract method from Application.
     */
    public void start(Stage stage){

        // Takes root node from LayoutBuilder, a UI class.
        Pane root = LayoutBuilder.getRoot(stage);

        Scene scene = new Scene(
            root, Sizes.WINDOW_WIDTH.getSize(), Sizes.WINDOW_HEIGHT.getSize()
        );

        // Directing key events to game logic.
        scene.setOnKeyPressed(e ->{
            GameArea.keyPressed(e.getCode());
        });

        stage.setScene(scene);
        stage.setTitle(Props._appTitle());
        stage.setResizable(false);
        stage.show();

    }
}
