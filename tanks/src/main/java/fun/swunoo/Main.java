package fun.swunoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage){

        Pane root = LayoutBuilder.getRoot();

        Scene scene = new Scene(root, Sizes.WINDOW_WIDTH.getSize(), Sizes.WINDOW_HEIGHT.getSize());
        stage.setScene(scene);
        stage.setTitle("90 TANKS by SwunOo");
        stage.setResizable(false);
        stage.show();

    }
}
