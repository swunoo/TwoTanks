package fun.swunoo.Data;

import fun.swunoo.UI.LayoutBuilder.Sidenav.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Miscellaneous data that cannot be expressed in numbers.
 */

public class Props {

    static Label aboutLabel = null;
    static Label footer = null;
    static Label winningLabel = null;

    // Title of the Application
    public static String _appTitle(){
        return "Tale of Two Tanks";
    }

    // Label for About
    public static Label _aboutLabel(){

        if(aboutLabel == null){
            StringBuilder aboutBuilder = new StringBuilder();

            aboutBuilder
                .append("How To Play \n --------------- \n")
                .append("1. P1 moves with arrows and shoots with Enter.\n")
                .append("2. P2 moves with WASD and shoots with Space.\n")
                .append("3. First player to reach a score of 3 wins the game.\n")
                .append("---------------\n")
                .append("CLICK START\n")
                .append("---------------\n\n")
                .append("About\n")
                .append("- Mini game written while learning JavaFX.\n - Thanks for giving it a try.");
            
            aboutLabel = new Label(aboutBuilder.toString()); 
            aboutLabel.setAlignment(Pos.CENTER);
            aboutLabel.setFont(_font());
            aboutLabel.setWrapText(true);
            aboutLabel.setPadding(new Insets(Sizes.HEADER_PADDING.getSize()));
            aboutLabel.setLineSpacing(10);
        }

        return aboutLabel;
    }

    // Footer
    public static Label _footer(Stage stage){
        if(footer == null){

            // Styles
            String footerStyle = "-fx-background-color: #000; -fx-text-fill: #fff";
            String tooltipStyle = "-fx-background-color: #fff; -fx-text-fill: #000";

            // Link
            String link = "github.com/swunoo/TwoTanks";
            ClipboardContent linkContent = new ClipboardContent();
            linkContent.putString(link);

            // Tooltip
            Tooltip footerTooltip = new Tooltip("Click to Copy");
            footerTooltip.setShowDelay(new Duration(0));
            footerTooltip.setStyle(tooltipStyle);


            // Initializing footer
            footer = new Label("© 2022 by SwunOo | " + link);

            footer.setAlignment(Pos.CENTER);
            footer.setPrefWidth(Sizes.WINDOW_WIDTH.getSize());
            footer.setPadding(new Insets(Sizes.PADDING.getSize()));
            footer.setStyle(footerStyle);

            // Adding and configuring tooltip
            footer.setTooltip(footerTooltip);

            // Show tooltip on hover
            footer.setOnMouseEntered(e -> footerTooltip.show(stage));
            footer.setOnMouseExited(e -> footerTooltip.hide());

            // Copy link and change text on click
            footer.setOnMouseClicked(e -> {
                Clipboard.getSystemClipboard().setContent(linkContent);
                footerTooltip.setText("Copied.");
            });
        }

        return footer;
    }

    // Label for Winning
    public static Label _winningLabel(Player p){
        if(winningLabel == null){
            winningLabel = new Label(p + " WINS.\n (pause and start to restart)");
            winningLabel.setFont(_font());
            winningLabel.setTextAlignment(TextAlignment.CENTER);
        }
        return winningLabel;
    }

    // Scores
    public static int _initialScore(){
        return 0;
    }
    public static int _winningScore(){
        return 3;
    }

    // Fonts
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

    // Text for Buttons
    public static String _startBtnText(){ return "START"; }
    public static String _pauseBtnText(){ return "PAUSE"; }
    public static String _aboutBtnText(){ return "ABOUT"; }
}
