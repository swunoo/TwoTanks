package fun.swunoo.Logic;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static fun.swunoo.Data.Sizes.*;

import fun.swunoo.Data.NormalTank;

import fun.swunoo.UI.LayoutBuilder.Sidenav.Player;

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

    /*
     * Initializes gameArea and canvas objects.
     * Both objects are then stored as static variables of the class.
     * So, this method is invoked only once in the program.
     */

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
            Player.P1,
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
            Player.P2,
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
        
        // INITIALIZING: Setting up AnimationTimer (animationTimer)
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

        // DRAQWING
        draw();
    }

    /*
     * Cmd from btn event.
     */
    public static void setInGame(boolean state){
        inGame = state;
        if(state) gameArea.animationTimer.start();
        else gameArea.animationTimer.stop();
    }

    /*
     * Event handler for key strokes.
     * Makes tanks move: LEFT, RIGHT, UP or DOWN.
     * Makes tanks shoot, rendering shell movement with animationTimer.
     */
    public static void keyPressed(KeyCode code){

        // no effect if the game is paused or haven't started.
        if(!inGame) return;

        // Moving the tank with arrow keys and shooting with spacebar.
        switch(code){

            // Player 1.
            case LEFT:
                gameArea.p1Tank.move(Direction.LEFT); break;
            case RIGHT:
                gameArea.p1Tank.move(Direction.RIGHT); break;
            case UP:
                gameArea.p1Tank.move(Direction.UP); break;
            case DOWN:
                gameArea.p1Tank.move(Direction.DOWN); break;
            case ENTER:
                gameArea.p1Tank.shoot(); break;

            // Player 2.
            case A:
                gameArea.p2Tank.move(Direction.LEFT); break;
            case D:
                gameArea.p2Tank.move(Direction.RIGHT); break;
            case W:
                gameArea.p2Tank.move(Direction.UP); break;
            case S:
                gameArea.p2Tank.move(Direction.DOWN); break;
            case SPACE:
                gameArea.p2Tank.shoot(); break;

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
