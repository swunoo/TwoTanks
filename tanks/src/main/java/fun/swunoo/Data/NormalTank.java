package fun.swunoo.Data;

import javafx.scene.paint.Color;

/*
 * Variables directly (and almost exclusively) related with gameplay.
 */
public class NormalTank extends TankMeasurements {

          public NormalTank () {

               // Tanks
               TANK_SIDE = 30;
               WHEEL_LENGTH = 50;
               WHEEL_BREADTH = 10;
               TURRET_RADIUS = 5;
               CANON_BREADTH = 3;
               CANON_LENGTH = 40;
               SHELL_SIDE = 5;
               SHELL_SPEED = 5;
               SHELL_FILL = Color.BLACK;
               
               // Others
               STROKE_WIDTH = 3;
               SPEED = 3;
          }
     
}
     

