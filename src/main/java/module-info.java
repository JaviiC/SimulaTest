module org.example.simulacro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.slf4j;

    exports org.example.controllers;
    exports org.example.simulacro;
    opens org.example.controllers to javafx.fxml;
    opens org.example.simulacro to javafx.fxml;
    exports org.example.manager;
    opens org.example.manager to javafx.fxml;
}