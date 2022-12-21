package fun.swunoo.Logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fun.swunoo.Data.TankMeasurements;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Class for the tank objects.
 */
public class Tank {

    // position
    private double x;
    private double y;

    // tanks cannot go pass boundaries
    private double boundary_x;
    private double boundary_y;

    // fill and stroke
    private Paint fill;
    private Paint stroke;

    // measurements
    private TankMeasurements T;

    // half the distance between center of the tank to its sides.
    private double HALF_OF_WHOLE_BREADTH;

    // half the distance between center of the tank to the tip of its canon.
    private double HALF_OF_WHOLE_LENGTH;

    // graphics context to draw with
    private GraphicsContext g;

    // shells fired from the tank
    private List<Shell> shells;
    // shells to remove
    private List<Shell> shellsToRemove;

    /*
     * All variables must be initialized.
     */
    Tank (
        double x, double y,
        double boundary_x, double boundary_y,
        Paint fill, Paint stroke,
        TankMeasurements T,
        GraphicsContext g
    ) {
        this.x = x;
        this.y = y;
        this.boundary_x = boundary_x;
        this.boundary_y = boundary_y;
        this.fill = fill;
        this.stroke = stroke;
        this.T = T;
        this.g = g;
        this.HALF_OF_WHOLE_BREADTH = T.TANK_SIDE/2 + T.WHEEL_BREADTH/2;
        this.HALF_OF_WHOLE_LENGTH = Math.max(T.CANON_LENGTH, T.WHEEL_LENGTH/2);
        this.shells = new ArrayList<>();
    }

    /*
     * Updates tank position and make sure it is withing boundaries.
     */
    public void move(Direction direction){

        // moves the position of the tank.
        switch(direction){
            case LEFT: x -= T.SPEED; break;
            case RIGHT: x += T.SPEED; break;
            case UP: y -= T.SPEED; break;
            case DOWN: y += T.SPEED;
        }

        // prevents out of bound movements.
        if(x + HALF_OF_WHOLE_BREADTH > boundary_x){
            x = boundary_x - HALF_OF_WHOLE_BREADTH; // sets x of rightmost side to boundary_x.
        } else if (x - HALF_OF_WHOLE_BREADTH < 0){
            x = HALF_OF_WHOLE_BREADTH; // sets x of leftmost side to 0.
        }

        if(y + HALF_OF_WHOLE_LENGTH > boundary_y){
            y = boundary_y - HALF_OF_WHOLE_LENGTH; // sets y of topmost side to boundary_y.
        } else if (y - HALF_OF_WHOLE_LENGTH < 0){
            y = HALF_OF_WHOLE_LENGTH; // sets y of bottommost side to 0.
        }

    }

    /*
     * Shoots shells out of cannon.
     */
    public void shoot(){
        shells.add(
            new Shell(
            x,
            y - HALF_OF_WHOLE_LENGTH, 
            0,
            T.SHELL_SIDE,
            T.SHELL_SPEED,
            Color.BLACK,
            Direction.UP,
            g)
        );
    }

    /*
     * Updates shells
     */
    public void updateShells(){

        synchronized (shells) {
            shellsToRemove = new ArrayList<>();
            shells.forEach(
                currentShell -> {
                    if(currentShell.isActive())
                        currentShell.update();
                    else
                        shellsToRemove.add(currentShell); 
                }
            );
            shells.removeAll(shellsToRemove);

        }

        // Be sure to re-check this if this method is modified.
        // System.out.println("Shell number: " + shells.size());
    }

    /**
     * Draws a tank based on internal states.
     */
    public void show(){

        // Sets colors for outline and fill.
        g.setLineWidth(T.STROKE_WIDTH);
        g.setStroke(stroke);
        g.setFill(fill);

        // Draws wheels.
        strokeAndFillRoundRect(
            x - T.TANK_SIDE/2 - T.WHEEL_BREADTH/2,
            y - T.WHEEL_LENGTH/2,
            T.WHEEL_BREADTH,
            T.WHEEL_LENGTH,
            T.WHEEL_BREADTH,
            T.WHEEL_BREADTH
        );
        strokeAndFillRoundRect(
            x + T.TANK_SIDE/2 - T.WHEEL_BREADTH/2,
            y - T.WHEEL_LENGTH/2,
            T.WHEEL_BREADTH,
            T.WHEEL_LENGTH,
            T.WHEEL_BREADTH,
            T.WHEEL_BREADTH
        );

        // Draws body of the tank.
        strokeAndFillRect(
            x - T.TANK_SIDE/2,
            y - T.TANK_SIDE/2,
            T.TANK_SIDE,
            T.TANK_SIDE);

        // Draws turret and canon (they use stroke color).
        g.setFill(stroke);

        g.fillOval(
            x - T.TURRET_RADIUS,
            y - T.TURRET_RADIUS,
            T.TURRET_RADIUS*2,
            T.TURRET_RADIUS*2);
        
        g.fillRect(
            x - T.CANON_BREADTH/2,
            y - T.CANON_LENGTH,
            T.CANON_BREADTH,
            T.CANON_LENGTH);
        
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

}

/*
 * Directions the tank can move.
 */
enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;
}
