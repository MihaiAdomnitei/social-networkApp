module socialnetwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens socialnetwork to javafx.fxml;
    opens socialnetwork.domain;
    exports socialnetwork;
    exports socialnetwork.controller;
    opens socialnetwork.controller to javafx.fxml;
}