module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.view to javafx.fxml;
    opens com.example.demo.utilities to javafx.fxml;

    exports com.example.demo.controller;
    exports com.example.demo.levels;
    exports com.example.demo.actors;
    exports com.example.demo.view;
    exports com.example.demo.utilities;
    exports com.example.demo.manager;
    opens com.example.demo.manager to javafx.fxml;
    exports com.example.demo.models;
    opens com.example.demo.models to javafx.fxml;
}
