package fun.swunoo.Logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/*
 * Bomb shell that is fired from the canon of a tank.
 */
public class Shell {
    
    // position
    private double x;
    private double y;

    // boundary after which the shell stops.
    private double boundary;

    // half-size
    private double HALF_SIZE;

    // speed
    private double speed;

    // fill
    private Paint fill;

    // direction the shell was fired.
    private Direction direction;

    // graphics context to draw with
    private GraphicsContext g;

    // if active (moving, damaging) or not.
    private boolean isActive = true;

    /*
     * All variables must be initialized.
     */
    public Shell(
        double x, double y,
        double boundary, double size, double speed,
        Paint fill, Direction direction, GraphicsContext g
    ){
        // initializes variables
        this.x = x;
        this.y = y;
        this.boundary = boundary;
        this.HALF_SIZE = size/2;
        this.speed = speed;
        this.fill = fill;
        this.direction = direction;
        this.g = g;
    }

    /*
     * Draws the shell with GraphicsContext g.
     */
    public void show(){
        g.setFill(fill);
        g.fillOval(x-HALF_SIZE, y-HALF_SIZE, HALF_SIZE * 2, HALF_SIZE * 2);
    }

    /*
     * Continuously move the shell until passing the boundary.
     */

    public void update(){
        // moves the shell.
        switch(direction){
            case LEFT: x -= speed; break;
            case RIGHT: x += speed; break;
            case UP: y -= speed; break;
            case DOWN: y += speed;
        }

        // stops timer if shell passes boundaries.
        if(
            (direction == Direction.LEFT && x + HALF_SIZE < boundary)
            || (direction == Direction.RIGHT && x - HALF_SIZE > boundary)
            || (direction == Direction.UP && y + HALF_SIZE < boundary)
            || (direction == Direction.DOWN && y - HALF_SIZE > boundary)
            ){
                this.isActive = false;
        } else {
            // redraws the shell.
            show();
        }
    }    

    /*
     * Accessor for isActive.
     */
    public boolean isActive(){
        return isActive;
    }
    

    // class ShellMovingTask extends TimerTask {

    //     private Shell shell;

    //     ShellMovingTask(Shell shell){
    //         this.shell = shell;
    //     }

    //     public void run () {
            
            
    //     }
    // }
}
