package fun.swunoo.UI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static fun.swunoo.Data.Sizes.*;

import fun.swunoo.Data.Props;
import fun.swunoo.Logic.GameArea;

/*
 * Primary class for UI.
 * It sets layout for the whole application.
 * It formats and styles the header and side nav.
 * 
 * Component tree is as follows:
 * 
 *  - root:   BorderPane
 *            - Header (top):    Label
 *            - Sidenav (left):  VBox
 *                               - btnBox:  VBox
 *                                          - startBtn  Label
 *                                          - aboutBtn  Label
 *                               - statBox:  VBox
 *                                          - p1Score  VBox
 *                                                        - text    Label
 *                                                        - value   Label
 *                                          - p2Score  VBox
 *                                                        - text    Label
 *                                                        - value   Label
 *            - GameArea (center):  Canvas
 * 
 * Header is built with a static method,
 *      SideNav is built with an inner class
 *      and GameArea is built with GameArea class, which drives game logic.
 * 
 */
public class LayoutBuilder {

    // The root element.
    static BorderPane root;

    /**
     * Builds the root node to be used in the Main class.
     */
    public static Pane getRoot(){

        root = new BorderPane();
        root.setTop(buildHeader());
        root.setLeft(Sidenav.getSideNavBox());
        root.setCenter(Props._aboutLabel());

        return root;
    }

    /*
     * The header at the top of window.
     */
    static Label buildHeader(){

        String style = "-fx-background-color: #000;";

        Label header = new Label("90 TANKS");
        header.setPrefHeight(HEADER_HEIGHT.getSize());
        header.setMaxHeight(HEADER_HEIGHT.getSize());
        header.setPrefWidth(WINDOW_WIDTH.getSize());
        header.setAlignment(Pos.CENTER_RIGHT);
        header.setPadding(new Insets(HEADER_PADDING.getSize()));
        header.setTextFill(Color.WHITE);
        header.setFont(Props._headerFont());
        header.setStyle(style);

        return header;
    }

    /**
     * Inner class that builds the sidenav.
     * Singleton pattern is used.
     * Important: Sidenav is NOT a UI component, it a BUILDER that builds a VBox with necessary elements.
     * Important: Elements that are called "buttons" are actually LABEL elements. "button" is just a name like "stats".
     */

    public static class Sidenav {

        // The singleton VBox, which serves as sidenav bar in the UI.
        private static VBox box = null;

        // CSS of sidenav.
        private static String boxStyle = 
            "-fx-background-color: #FBFF4E; -fx-border-width: 0 3px 0 0; -fx-border-color: #000;";

        // CSS of buttons (general)
        private static String btnStyle = 
            "-fx-border-color: black;"
            + "-fx-border-width: 3px;"
            + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,1), 0, 0, 5, 5);"
            + "-fx-padding: 5px;"
            +"-fx-border-insets: 10px;"
            +"-fx-background-insets: 10px;";

        // CSS of buttons (specific)
        private static String startBtnStyle = "-fx-background-color: #fff; -fx-text-fill: #000;";
        private static String abtBtnStyle = "-fx-background-color: #473ADC; -fx-text-fill: #fff;";

        // CSS of buttons when hovered
        private static String hoverBtnStyle = "-fx-background-color: #58F08C; -fx-text-fill: #000;";

        // CSS of stats (2 VBox showing score and lives)
        private static String p1ScoreStyle = "-fx-background-color: #fff; -fx-text-fill: #000;"; 
        private static String p2ScoreStyle = "-fx-background-color: #473ADC; -fx-text-fill: #fff;"; 

        // values of score stats (not the text, only numbers).
        private static Label p1Score = new Label(Player.P1.getScore()+"");
        private static Label p2Score = new Label(Player.P2.getScore()+""); 

        // Returns VBox (sidenav) if already built, and otherwise builds a new one first.
        public static VBox getSideNavBox(){
            if(box == null) new Sidenav();
            return box;
        }
        
        // Singleton constructor that builds the sidenav.
        private Sidenav(){

            // building statBox
            VBox statBox = new VBox(
                buildStats("P1 SCORE", p1Score, p1ScoreStyle),
                buildStats("P2 SCORE", p2Score, p2ScoreStyle));
            statBox.setPrefHeight(FULL.getSize());
            statBox.setAlignment(Pos.CENTER);

            // building btnBox
            VBox btnBox = new VBox(
                buildButtons("START", startBtnStyle),
                buildButtons("ABOUT", abtBtnStyle)
            );
            btnBox.setPadding(new Insets(PADDING.getSize()));

            // Adding buttons and stats to sidenav VBox.
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

        /*
         * Builds buttons (Label) depending on
         *  - text:         to write on button
         *  - extraStyle:   to style the specific button (alongside common btnStyle).
         */

        private Label buildButtons(String text, String extraStyle){
            
            Label btn = new Label(text);
            btn.setStyle(btnStyle + extraStyle);
            btn.setPrefWidth(FULL.getSize());
            btn.setFont(Props._font());
            btn.setAlignment(Pos.CENTER);

            // For hover effect
            btn.addEventHandler(
                MouseEvent.MOUSE_ENTERED, 
                e -> btn.setStyle(btnStyle + hoverBtnStyle));

            btn.addEventHandler(
                MouseEvent.MOUSE_EXITED, 
                e -> btn.setStyle(btnStyle + extraStyle));

            // Event handler for btnClick events.
            btn.setOnMouseClicked(e -> {

                Label btnClicked = (Label) e.getSource();
        
                if(btnClicked.getText() == "START"){
                    root.setCenter(GameArea.getGameAreaCanvas());
                    btnClicked.setText("PAUSE");
                                    
                    // Resets scores
                    Player.P1.resetScore();
                    Player.P2.resetScore();
                    p1Score.setText(Props._initialScore()+"");
                    p2Score.setText(Props._initialScore()+"");

                    GameArea.setInGame(true);

                } else if (btnClicked.getText() == "PAUSE"){
                    btnClicked.setText("START");
                    GameArea.setInGame(false);

                } else if (btnClicked.getText() == "ABOUT"){
                    root.setCenter(Props._aboutLabel());
                }

            });

            return btn;
        }

        /*
         * Builds stats (VBox) depending on:
         *      - textString:   text to put in text label
         *      - valueLabel:   label element for value
         *      - style:        to style the VBox AND Labels.
         * 
         * The return value is a VBox with two children:
         *      -   textLabel   (e.g. "P1 Score", "P2 Score")
         *      -   valueLabel  (e.g. "3", "0")
         */

        private Pane buildStats(String textString, Label valueLabel, String style){

            // building
            Label textLabel = new Label(textString);
            VBox stats = new VBox(textLabel, valueLabel);

            // styling
            stats.setAlignment(Pos.CENTER);
            stats.setSpacing(GAP.getSize());
            stats.setPrefHeight(FULL.getSize());
            stats.setStyle(style);
            textLabel.setStyle(style);
            valueLabel.setStyle(style);
            textLabel.setFont(Props._statTextFont());
            valueLabel.setFont(Props._statValueFont());
            textLabel.setTextAlignment(TextAlignment.CENTER);

            // returning
            return stats;
        }

        /*
         * Modifies scores.
         */
        public static void addToStats(Player player, int value){

            player.updateScore(value);

            int currentScore = player.getScore();

            if(player.equals(Player.P1)){
                p1Score.setText(currentScore + "");
            } else {
                p2Score.setText(currentScore + "");
            }

            if(currentScore >= Props._winningScore()){
                // Stops the game and animation.
                GameArea.setInGame(false);

                // Shows winning label.
                root.setCenter(Props._winningLabel(player));
            }

        }

        public enum Player {
            P1(Props._initialScore()),
            P2(Props._initialScore());

            private int score;

            Player(int score){
                this.score = score;
            }

            int getScore(){
                return score;
            }

            void updateScore(int value){
                this.score += value;
            }

            void resetScore(){
                this.score = Props._initialScore();
            }
        }

    }

    
}
