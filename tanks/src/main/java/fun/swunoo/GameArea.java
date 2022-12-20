package fun.swunoo;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static fun.swunoo.Sizes.*;
import static fun.swunoo.GameArea.Measurements.*;

public class GameArea {

    private static Canvas canvas = null;
    private static GameArea gameArea = null;

    private GraphicsContext g;

    private XYPosition p1TankPos;

    private static boolean inGame = false;

    public static Canvas getGameAreaCanvas(){
        if(canvas == null) gameArea = new GameArea();
        return canvas;
    }

    public static GameArea getInstance(){
        if(gameArea == null) gameArea = new GameArea();
        return gameArea;
    }

    private GameArea(){

        int canvasWidth = WINDOW_WIDTH.getSize() - SIDE_WIDTH.getSize();
        int canvasHeight = WINDOW_HEIGHT.getSize() - HEADER_HEIGHT.getSize() - HEADER_PADDING.getSize()/2;

        canvas = new Canvas(canvasWidth, canvasHeight);

        p1TankPos = new XYPosition(canvasWidth/2, canvasHeight-100); //TODO: this can be a props.

        g = canvas.getGraphicsContext2D();
        g.setLineWidth(STROKE_WIDTH);

        draw();

    }

    public static void btnClicked(MouseEvent evt){
        Label btn = (Label) evt.getSource();
        
        if(btn.getText() == "START"){
            btn.setText("PAUSE");
            inGame = true;

        } else if (btn.getText() == "ABOUT"){
            System.out.println("TODO: Add About Modal or something.");
        }
    }

    public static void keyPressed(KeyCode code){

        System.out.println("pressed: " + code);

        switch(code){
            case LEFT: gameArea.p1TankPos.x -= SPEED; break;
            case RIGHT: gameArea.p1TankPos.x += SPEED; break;
            case UP: gameArea.p1TankPos.y -= SPEED; break;
            case DOWN: gameArea.p1TankPos.y += SPEED; break;
            default: return;
        }

        // if(code == KeyCode.LEFT){

        //     gameArea.p1TankPos.x -= SPEED;
            
        // } else if (code == KeyCode.RIGHT){
            
        // } else if (code == KeyCode.UP){

        // } else if (code == KeyCode.DOWN){

        // } else if (code == KeyCode.SPACE){

        // } else {
        //     return;
        // }

        gameArea.draw();

    }

    public void draw(){
        g.setFill(Color.rgb(88, 240, 140));
        g.fillRect(0, 0, 1000, 1000);

        drawTank(p1TankPos, Color.WHITE, Color.BLACK);
    }

    public void drawTank(XYPosition pos, Paint fillColor, Paint strokeColor){


        // Sets colors for outline and fill.
        g.setStroke(strokeColor);
        g.setFill(fillColor);

        // Draws wheels.
        strokeAndFillRoundRect(
            pos.x - TANK_SIDE/2 - WHEEL_BREADTH/2,
            pos.y - WHEEL_LENGTH/2,
            WHEEL_BREADTH,
            WHEEL_LENGTH,
            WHEEL_BREADTH,
            WHEEL_BREADTH
        );
        strokeAndFillRoundRect(
            pos.x + TANK_SIDE/2 - WHEEL_BREADTH/2,
            pos.y - WHEEL_LENGTH/2,
            WHEEL_BREADTH,
            WHEEL_LENGTH,
            WHEEL_BREADTH,
            WHEEL_BREADTH
        );

        // Draws body of the tank.
        strokeAndFillRect(
            pos.x - TANK_SIDE/2,
            pos.y - TANK_SIDE/2,
            TANK_SIDE,
            TANK_SIDE);

        // Draws turret and canon (they use stroke color).
        g.setFill(strokeColor);

        g.fillOval(
            pos.x - TURRET_RADIUS,
            pos.y - TURRET_RADIUS,
            TURRET_RADIUS*2,
            TURRET_RADIUS*2);
        
        g.fillRect(
            pos.x - CANON_BREADTH/2,
            pos.y - CANON_LENGTH,
            CANON_BREADTH,
            CANON_LENGTH);
        
        //
    }

    private void strokeAndFillRect(double x, double y, double l, double b){
        g.strokeRect(x, y, l, b);
        g.fillRect(x, y, l, b);
    }

    private void strokeAndFillRoundRect(double x, double y, double l, double b, double r1, double r2){
        g.strokeRoundRect(x, y, l, b, r1, r2);
        g.fillRoundRect(x, y, l, b, r1, r2);
    }

    class XYPosition{
        double x;
        double y;
        XYPosition(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

    static class Measurements{
    
        // Tanks
        static double TANK_SIDE = 30;
        static double WHEEL_LENGTH = 50;
        static double WHEEL_BREADTH = 10;
        static double TURRET_RADIUS = 5;
        static double CANON_BREADTH = 3;
        static double CANON_LENGTH = 40;
        static double SHELL_SIDE = 5;
        
        // Others
        static double STROKE_WIDTH = 3;
        static double SPEED = 3;
    
    }

}
