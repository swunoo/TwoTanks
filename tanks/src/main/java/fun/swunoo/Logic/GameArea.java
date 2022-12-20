package fun.swunoo.Logic;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static fun.swunoo.Data.Sizes.*;
import static fun.swunoo.Data.GameMeasurements.*;

import fun.swunoo.Data.Props;
import fun.swunoo.Data.Sizes;

/**
 * Manages game logic.
 * ALso builds the canvas.
 * Uses singleton patten:
 *      -   Canvas object 'canvas' for presentation and GameArea object 'gameArea' for gameplay are both signleton instances.
 *      -   Both objects can be requested with their own singleton methods.
 *      -   The singleton methods return the requested object if already built, and builds a new one using a single constructor.
 * */
public class GameArea {

    private static Canvas canvas = null;
    private static GameArea gameArea = null;

    private GraphicsContext g;

    private XYPosition p1TankPos;   // XYPosition: a wrapper class for x and y coordinate points.

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

        // INITIALIZING: Canvas (canvas)
        int canvasWidth = WINDOW_WIDTH.getSize() - SIDE_WIDTH.getSize();
        int canvasHeight = WINDOW_HEIGHT.getSize() - HEADER_HEIGHT.getSize() - HEADER_PADDING.getSize()/2;
        canvas = new Canvas(canvasWidth, canvasHeight);

        // INITIALIZING: XYPosition (p1TankPos)
        p1TankPos = new XYPosition(canvasWidth/2, canvasHeight- STARTING_BOTTOM.getSize());

        // INITIALIZING: GraphicsContext (g)
        g = canvas.getGraphicsContext2D();
        g.setLineWidth(STROKE_WIDTH);

        // Drawing the canvas.
        draw();
    }

    /*
     * Event handler for START and ABOUT buttons.
     */
    public static void btnClicked(MouseEvent evt){
        Label btn = (Label) evt.getSource();
        
        if(btn.getText() == "START"){
            btn.setText("PAUSE");
            inGame = true;

        } else if (btn.getText() == "ABOUT"){
            System.out.println("TODO: Add About Modal or something.");
        }
    }

    /*
     * Event handler for key strokes.
     * Moves the tank LEFT, RIGHT, UP, DOWN.
     */
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

        // redrawing canvas.
        gameArea.draw();

    }

    /*
     * Refreshes the canvas by:
     *      -   drawing the background.
     *      -   drawing tanks.
     */
    public void draw(){
        g.setFill(Color.rgb(88, 240, 140));
        g.fillRect(0, 0, 1000, 1000);

        drawTank(p1TankPos, Color.WHITE, Color.BLACK);
    }

    /**
     * Draws a tank based on:
     *  -   pos:          X and Y positions of the CENTER POINT of the tank.
     *  -   fillColor:    Paint object to fill the body and wheels.
     *  -   strokeColor:  Paint object to outline the body and wheels, and to fill the turret and canon.
     */
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
        
    }

    /*
     * Helper method to combine strokeRect and fillRect.
     */
    private void strokeAndFillRect(double x, double y, double l, double b){
        g.strokeRect(x, y, l, b);
        g.fillRect(x, y, l, b);
    }

     /*
     * Helper method to combine strokeRoundRect and fillRoundRect.
     */
    private void strokeAndFillRoundRect(double x, double y, double l, double b, double r1, double r2){
        g.strokeRoundRect(x, y, l, b, r1, r2);
        g.fillRoundRect(x, y, l, b, r1, r2);
    }

    /*
     * Wrapper for x and y positions.
     */
    class XYPosition{
        double x;
        double y;
        XYPosition(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

}
