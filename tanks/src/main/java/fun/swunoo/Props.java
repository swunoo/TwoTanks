package fun.swunoo;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
}
