package fun.swunoo.Logic;

import javafx.animation.AnimationTimer;
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

import fun.swunoo.Data.NormalTank;

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
    private AnimationTimer animationTimer;

    private Tank p1Tank;
    private Tank p2Tank;

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

        // INITIALIZING: GraphicsContext (g)
        g = canvas.getGraphicsContext2D();

        // INITIALIZING: Tank (p1Tank)
        p1Tank = new Tank(
            "Player 1",
            true,
            canvasWidth/2,
            canvasHeight - STARTING_BOTTOM.getSize(),
            canvasWidth,
            canvasHeight,
            Color.WHITE,
            Color.BLACK,
            new NormalTank(),
            Direction.UP,
            g);

        // INITIALIZING: Tank (p2Tank)
        p2Tank = new Tank(
            "Player 2",
            false,
            canvasWidth/2,
            STARTING_BOTTOM.getSize(),
            canvasWidth,
            canvasHeight,
            Color.BLUE,
            Color.BLACK,
            new NormalTank(),
            Direction.DOWN,
            g);

        // INITIALIZING: Setting up Tanks
        p1Tank.addEnemyTank(p2Tank);

        p2Tank.addEnemyTank(p1Tank);
        
        animationTimer = new AnimationTimer( ) {
            long previousFrameTime; // nanoseconds
            public void handle(long time) {
                if (time - previousFrameTime > 0.99e9/60) {
                    // Only renders 60 times per second.
                    draw();
                    previousFrameTime = time;
                }
            }
        };

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
            gameArea.animationTimer.start();

        } else if (btn.getText() == "PAUSE"){
            btn.setText("START");
            inGame = false;
            gameArea.animationTimer.stop();

        } else if (btn.getText() == "ABOUT"){
            System.out.println("TODO: Add About Modal or something.");
        }
    }

    /*
     * Event handler for key strokes.
     * Moves the tank LEFT, RIGHT, UP, DOWN.
     * Shoots the tanks, rendering shell movement with animationTimer.
     */
    public static void keyPressed(KeyCode code){

        System.out.println("pressed: " + code);

        if(!inGame) return;

        // Moving the tank with arrow keys and shooting with spacebar.
        switch(code){
            case LEFT: gameArea.p1Tank.move(Direction.LEFT); break;
            case RIGHT: gameArea.p1Tank.move(Direction.RIGHT); break;
            case UP: gameArea.p1Tank.move(Direction.UP); break;
            case DOWN: gameArea.p1Tank.move(Direction.DOWN); break;
            case SPACE: gameArea.p1Tank.shoot(); break;
            case A: gameArea.p2Tank.shoot(); break;
            default: return;
        }

        // redrawing canvas.
        gameArea.draw();

    }

    /*
     * Refreshes the canvas by:
     *      -   drawing the background.
     *      -   drawing tanks.
     *      -   drawing shells.
     */
    public void draw(){

        // background
        g.setFill(Color.rgb(88, 240, 140));
        g.fillRect(0, 0, 1000, 1000);

        // tanks
        p1Tank.show();
        p2Tank.show();

        // shells
        p1Tank.updateShells();
        p2Tank.updateShells();
    }
}
