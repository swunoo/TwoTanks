package fun.swunoo;

import fun.swunoo.Data.Sizes;
import fun.swunoo.Logic.GameArea;
import fun.swunoo.UI.LayoutBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The Game
 * The tank game we used to play as a child. Gameplay is super simple. You can drive the tank around and shoot at other tanks. Tanks break after receiveing certain number of shots. The goal is to set high score by scoring as many tanks as possible before breaking down.
 * 
 * The Project
 * This game is written on 2022 December while learning JavaFX in CS1102 course at UoPeople.
 * 
 * The Code
 * Game logic, UI and Data are separated into packages. This class, Main, sets the scene and launches the application.
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
        Pane root = LayoutBuilder.getRoot();

        Scene scene = new Scene(root, Sizes.WINDOW_WIDTH.getSize(), Sizes.WINDOW_HEIGHT.getSize());

        // Directing key events to game logic.
        scene.setOnKeyPressed(e ->{
            GameArea.keyPressed(e.getCode());
        });

        stage.setScene(scene);
        stage.setTitle("90 TANKS by SwunOo");
        stage.setResizable(false);
        stage.show();

    }
}
