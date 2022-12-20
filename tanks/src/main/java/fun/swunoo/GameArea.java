package fun.swunoo;

import javafx.scene.canvas.Canvas;

public class GameArea {

    private static Canvas canvas = null;

    private static String canvasStyle = "-fx-background-color: #58F08C; -fx-border-width: 5px; -fx-border-color: #000;";

    public static Canvas getGameAreaCanvas(){
        if(canvas == null) new GameArea();
        return canvas;
    }

    private GameArea(){

        canvas = new Canvas();

        canvas.setStyle(canvasStyle);

    }
}
