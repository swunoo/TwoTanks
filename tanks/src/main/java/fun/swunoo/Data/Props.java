package fun.swunoo.Data;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Miscellaneous data that cannot be expressed in numbers.
 */

public class Props {
    
    public static Font _font(){
        return Font.font("Verdana", FontWeight.EXTRA_BOLD, 14);
    }
    public static Font _headerFont(){
        return Font.font("Verdana", FontWeight.EXTRA_BOLD, 30);
    }
    public static Font _statTextFont(){
        return Font.font("Verdana", FontWeight.THIN, 14);
    }
    public static Font _statValueFont(){
        return Font.font("Verdana", FontWeight.EXTRA_BOLD, 30);
    }
    public static String _initialLives(){
        return "3";
    }
    public static String _initialScore(){
        return "0";
    }
}
