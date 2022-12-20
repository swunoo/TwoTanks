package fun.swunoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage){

        Pane root = LayoutBuilder.getRoot();

        root.setFocusTraversable(false);

        Scene scene = new Scene(root, Sizes.WINDOW_WIDTH.getSize(), Sizes.WINDOW_HEIGHT.getSize());

        scene.setOnKeyPressed(e ->{
            GameArea.keyPressed(e.getCode());
        });



        stage.setScene(scene);
        stage.setTitle("90 TANKS by SwunOo");
        stage.setResizable(false);
        stage.show();

    }
}
