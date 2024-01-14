module com.example.tunicamp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.gson;
    requires stripe.java;

    opens esprit.tn.tunicamp to javafx.fxml;
    exports esprit.tn.tunicamp;
    exports esprit.tn.tunicamp.controllers;
    opens esprit.tn.tunicamp.controllers to javafx.fxml;
}