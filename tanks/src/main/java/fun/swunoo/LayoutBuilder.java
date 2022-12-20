package fun.swunoo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static fun.swunoo.Sizes.*;

public class LayoutBuilder {

    static Pane getRoot(){

        BorderPane root = new BorderPane();
        root.setTop(buildHeader());
        root.setLeft(Sidenav.getSideNavBox());
        root.setCenter(GameArea.getGameAreaCanvas());

        return root;
    }

    static Label buildHeader(){

        String style = 
            "-fx-background-color: #000;";

        Label header = new Label("90 TANKS");
        header.setPrefHeight(HEADER_HEIGHT.getSize());
        header.setPrefWidth(WINDOW_WIDTH.getSize());
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(HEADER_PADDING.getSize()));
        header.setTextFill(Color.WHITE);
        header.setFont(Props._headerFont());
        header.setStyle(style);

        return header;
    }


    static class Sidenav {

        private static VBox box = null;

        private static String boxStyle = 
            "-fx-background-color: #FBFF4E;";

        private static String btnStyle = 
            "-fx-border-color: black;"
            + "-fx-border-width: 3px;"
            + "-fx-padding: 5px;"
            +"-fx-border-insets: 10px;"
            +"-fx-background-insets: 10px;";

        private static String startBtnStyle = "-fx-background-color: #fff; -fx-text-fill: #000;";
        private static String abtBtnStyle = "-fx-background-color: #473ADC; -fx-text-fill: #fff;";

        private static String statStyle =
            "-fx-background-color: #000;"; 

        private static String scoreStatStyle = "-fx-background-color: #fff; -fx-text-fill: #000;"; 

        private static String liveStatStyle = "-fx-background-color: #473ADC; -fx-text-fill: #fff;"; 

        private static Label score;
        private static Label live; 

        public static VBox getSideNavBox(){
            if(box == null) new Sidenav();
            return box;
        }
        
        private Sidenav(){
            
            score = new Label("0");
            live = new Label("3");

            VBox statBox = new VBox(
                buildStats("SCORE", score, scoreStatStyle),
                buildStats("LIVE", live, liveStatStyle));

            statBox.setPrefHeight(1000);
            statBox.setAlignment(Pos.CENTER);

            VBox btnBox = new VBox(
                buildButtons("START", startBtnStyle),
                buildButtons("ABOUT", abtBtnStyle)
            );

            btnBox.setPadding(new Insets(PADDING.getSize()));

            box = 
                new VBox(
                    btnBox,
                    statBox
                );

            box.setPrefWidth(SIDE_WIDTH.getSize());
            box.setAlignment(Pos.TOP_CENTER);
            box.setSpacing(GAP.getSize());
            
            box.setStyle(boxStyle);
            
        }

        private Button buildButtons(String text, String extraStyle){
            Button btn = new Button(text);
            btn.setStyle(btnStyle + extraStyle);
            btn.setPrefWidth(1000);
            btn.setFont(Props._font());
            return btn;
        }

        private Pane buildStats(String textString, Label valueLabel, String extraStyle){

            Label textLabel = new Label(textString);
            VBox stats = new VBox(textLabel, valueLabel);

            stats.setAlignment(Pos.CENTER);
            stats.setSpacing(GAP.getSize());
            stats.setPrefHeight(1000);
            stats.setStyle(statStyle + extraStyle);

            textLabel.setStyle(extraStyle);
            valueLabel.setStyle(extraStyle);

            textLabel.setFont(Props._statTextFont());
            valueLabel.setFont(Props._statValueFont());


            textLabel.setTextAlignment(TextAlignment.CENTER);

            return stats;
        }

        private static void addToStats(String statName, int value){
            if(statName == "score"){
                String newText = Integer.toString(
                    Integer.parseInt(score.getText()) + value
                );
                score.setText(newText);
            } else if (statName == "live"){
                String newText = Integer.toString(
                    Integer.parseInt(live.getText()) + value
                );
                live.setText(newText);
            }

        }

    }

    
}
