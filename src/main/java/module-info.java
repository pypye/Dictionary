module client.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires org.apache.commons.text;
    requires org.controlsfx.controls;
    requires org.json;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.javafx;
    requires validatorfx;
    requires jaws.bin;

    opens client.dictionary to javafx.fxml;
    exports client.dictionary;
    exports client.dictionary.controllers;
    opens client.dictionary.controllers to javafx.fxml;
}