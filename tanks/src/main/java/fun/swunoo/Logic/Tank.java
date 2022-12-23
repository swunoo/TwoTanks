package fun.swunoo.Logic;

import java.util.ArrayList;
import java.util.List;

import fun.swunoo.Data.TankMeasurements;
import fun.swunoo.UI.LayoutBuilder.Sidenav;
import fun.swunoo.UI.LayoutBuilder.Sidenav.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static fun.swunoo.Logic.Direction.*;

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

    // initial direction
    private Direction direction;

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

    // enemies that are targets for shells
    private List<Tank> enemyTanks;

    // shells fired from the tank
    private List<Shell> shells;
    // shells to remove
    private List<Shell> shellsToRemove;

    // playername
    private String playerName;
    // playername
    private Player player;

    /*
     * All variables must be initialized.
     */
    Tank (
        String playerName,
        Player player,
        double x, double y,
        double boundary_x, double boundary_y,
        Paint fill, Paint stroke,
        TankMeasurements T,
        Direction direction,
        GraphicsContext g
        ) {
        this.playerName = playerName;
        this.player = player;
        this.x = x;
        this.y = y;
        this.boundary_x = boundary_x;
        this.boundary_y = boundary_y;
        this.fill = fill;
        this.stroke = stroke;
        this.T = T;
        this.direction = direction;
        this.g = g;
        this.HALF_OF_WHOLE_BREADTH = T.TANK_SIDE/2 + T.WHEEL_BREADTH/2;
        this.HALF_OF_WHOLE_LENGTH = Math.max(T.CANON_LENGTH, T.WHEEL_LENGTH/2);
        this.shells = new ArrayList<>();
        this.enemyTanks = new ArrayList<>();
    }

    public void addEnemyTank(Tank enemy){
        enemyTanks.add(enemy);
    }

    public void enemyHit(Tank enemy){

        Sidenav.addToStats(player, 1);

        // If tanks should be exploded and disappear after getting hit:
        // synchronized(enemyTanks){
        //     enemyTanks.remove(enemy);
        // }
    }

    public List<Tank> getEnemyTanks(){
        return enemyTanks;
    }

    /*
     * Accessors for positions of tanks' bounds.
     */
    public double accessBounds(Direction direction){
        switch(direction){
            case LEFT: return x - T.TANK_SIDE/2;
            case RIGHT: return x + T.TANK_SIDE/2;
            case UP: return y - T.TANK_SIDE/2;
            case DOWN: return y + T.TANK_SIDE/2;
            default: return 0.0;
        }
    }

    /*
     * Accessor for playerName.
     */
    public String getName(){
        return playerName;
    }

    /*
     * Updates tank direction.
     */
    public void move(Direction keyDirection){

        if (! keyDirection.equals(direction))
            direction = keyDirection;
        
        moveForward();

    }

    /*
     * Moves tank towards `direction` and makes sure it is within boundaries.
     */
    public void moveForward(){

        // moves the tank and resets out-of-bound movements.
        switch(direction){
            case LEFT:
                x -= T.SPEED;
                if(x - HALF_OF_WHOLE_LENGTH < 0)
                    x = HALF_OF_WHOLE_LENGTH;
                break;
            case RIGHT:
                x += T.SPEED;
                if(x + HALF_OF_WHOLE_LENGTH > boundary_x)
                    x = boundary_x - HALF_OF_WHOLE_LENGTH;
                break;
            case UP:
                y -= T.SPEED;
                if(y - HALF_OF_WHOLE_LENGTH < 0)
                    y = HALF_OF_WHOLE_LENGTH;
                break;
            case DOWN:
                y += T.SPEED;
                if(y + HALF_OF_WHOLE_LENGTH > boundary_y)
                    y = boundary_y - HALF_OF_WHOLE_LENGTH;
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
            y, 
            direction.equals(RIGHT)    // if RIGHT or DOWN, boundary is boundary_x or boundary_y. 0 otherwise.
                ? boundary_x
                : (direction.equals(DOWN) ? boundary_y : 0),
            T.SHELL_SIDE,
            T.SHELL_SPEED,
            Color.BLACK,
            direction,
            g,
            enemyTanks, this)
        );
    }

    /*
     * Receiving damage.
     */
    public void getHit(Shell shell){
        shell.getShooter().enemyHit(this);
        Sidenav.addToStats(player, -1);
    }

    /*
     * This method updates `shells`, to prevent memory leaks.
     * IMPORTANT: make sure inactive shells are still being removed if this method is modified.
     * 
     * How inactive shells can be removed:
     *  -   `shells` is a list, so it shouldn't be modified while being iterated.
     *  -   So, another list, `shellsToRemove` is constructed to hold unactive shells.
     *  -   These unactive shells are removed only after the iteration of `shells`.
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
        drawWheels();

        // Draws body.
        drawBody();

        g.setFill(stroke); // Turret and Canon use stroke color as fill.

        // Draws turret.
        drawTurret();

        // Draws canon.
        drawCanon();
        
    }

    /*
     * Helper functions to draw the tank.
     */
    private void drawWheels(){

        // POSITION: first wheel.
        double wheel_x1 = 0;
        double wheel_y1 = 0;

        // POSITION: second wheel.
        double wheel_x2 = 0;
        double wheel_y2 = 0;

        // X-DISTANCE: length in x position for both wheels.
        double length_x = 0;

        // Y-DISTANCE: length in y position for both wheels.
        double length_y = 0;

        // RADIUS: border radia of both wheels.
        double border_radius = T.WHEEL_BREADTH;

        // For vertical position:
        if(direction.equals(UP) || direction.equals(DOWN)){

            wheel_x1 = x - T.TANK_SIDE/2 - T.WHEEL_BREADTH/2;
            wheel_y1 = y - T.WHEEL_LENGTH/2;
            wheel_x2 = x + T.TANK_SIDE/2 - T.WHEEL_BREADTH/2;
            wheel_y2 = wheel_y1;
            length_x = T.WHEEL_BREADTH;
            length_y = T.WHEEL_LENGTH;

        // For horizontal position:
        } else {
            wheel_x1 = x - T.WHEEL_LENGTH/2;
            wheel_y1 = y - T.TANK_SIDE/2 - T.WHEEL_BREADTH/2;
            wheel_x2 = wheel_x1;
            wheel_y2 = y + T.TANK_SIDE/2 - T.WHEEL_BREADTH/2;
            length_x = T.WHEEL_LENGTH;
            length_y = T.WHEEL_BREADTH;
        }

        // DRAWING: wheel 1.
        g.strokeRoundRect(wheel_x1, wheel_y1, length_x, length_y, border_radius, border_radius);
        g.fillRoundRect(wheel_x1, wheel_y1, length_x, length_y, border_radius, border_radius);

        // DRAWING: wheel 2.
        g.strokeRoundRect(wheel_x2, wheel_y2, length_x, length_y, border_radius, border_radius);
        g.fillRoundRect(wheel_x2, wheel_y2, length_x, length_y, border_radius, border_radius);
    }

    private void drawBody(){
        g.strokeRect(
            x - T.TANK_SIDE/2,
            y - T.TANK_SIDE/2,
            T.TANK_SIDE,
            T.TANK_SIDE
        );
        g.fillRect(
            x - T.TANK_SIDE/2,
            y - T.TANK_SIDE/2,
            T.TANK_SIDE,
            T.TANK_SIDE
        );
        
    }
    private void drawTurret(){
        g.fillOval(
            x - T.TURRET_RADIUS,
            y - T.TURRET_RADIUS,
            T.TURRET_RADIUS*2,
            T.TURRET_RADIUS*2);
    }
    private void drawCanon(){

        // POSITION and DISTANCE in X and Y directions.
        double x_pos, y_pos, x_len, y_len;

        if(direction.equals(UP) || direction.equals(DOWN)){
            x_pos = x - T.CANON_BREADTH/2;
            y_pos = direction.equals(UP) ? y - T.CANON_LENGTH : y;
            x_len = T.CANON_BREADTH;
            y_len = T.CANON_LENGTH;
        } else {
            x_pos = direction.equals(LEFT) ? x - T.CANON_LENGTH : x;
            y_pos = y - T.CANON_BREADTH/2;
            x_len = T.CANON_LENGTH;
            y_len = T.CANON_BREADTH;
        }

        g.fillRect(x_pos, y_pos, x_len, y_len);
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