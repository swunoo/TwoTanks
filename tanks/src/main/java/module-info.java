module fun.swunoo {
    requires javafx.controls;
    requires javafx.fxml;

    opens fun.swunoo to javafx.fxml;
    exports fun.swunoo;
}
