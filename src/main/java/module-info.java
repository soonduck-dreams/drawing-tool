module cose457.drawingtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens cose457.drawingtool to javafx.fxml;
    opens cose457.drawingtool.view to javafx.fxml;
    exports cose457.drawingtool;
    exports cose457.drawingtool.view;
}