package fun.swunoo.Logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import static fun.swunoo.Logic.Direction.*;

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

    // possible targets
    private List<Tank> enemyTanks;

    // shooter of this shell
    private Tank shooterTank;

    // if active (moving, damaging) or not.
    private boolean isActive = true;

    /*
     * All variables must be initialized.
     */
    public Shell(
        double x, double y,
        double boundary, double size, double speed,
        Paint fill, Direction direction, GraphicsContext g,
        List<Tank> enemyTanks, Tank shooterTank
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
        this.enemyTanks = enemyTanks;
        this.shooterTank = shooterTank;
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

        // moves the shell and deactivates if out-of-bound.
        switch(direction){
            case LEFT:
                x -= speed;
                if(x + HALF_SIZE < 0) isActive = false;
                break;
            case RIGHT:
                x += speed;
                if(x - HALF_SIZE > boundary) isActive = false;
                break;
            case UP:
                y -= speed;
                if(y + HALF_SIZE < 0) isActive = false;
                break;
            case DOWN:
                if(y - HALF_SIZE > boundary) isActive = false;
                y += speed;
        }

        // draws if shell is still active.
        if (isActive){ 
            show();

            // The list is cloned to prevent ConcurrentModificationExpections, if enemy tanks need to get removed from enemyTanks after getting hit.
            List<Tank> enemyTanksClone = new ArrayList<>(enemyTanks);

            for(Tank tank : enemyTanksClone){
                if(
                    x > tank.accessBounds(LEFT) && x < tank.accessBounds(RIGHT)
                    &&
                    y > tank.accessBounds(UP) && y < tank.accessBounds(DOWN)){
                        System.out.println("-------TANK HIT-------");
                        tank.getHit(this);
                        this.isActive = false;
                }
            }
        }

        
    }    

    /*
     * Accessor for isActive.
     */
    public boolean isActive(){
        return isActive;
    }

    /*
     * Accessor for Shooter tank.
     */
    public Tank getShooter(){
        return shooterTank;
    }
    
}
