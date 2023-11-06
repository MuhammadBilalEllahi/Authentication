module MyApp.SplashScreen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;
    requires javafx.web;
    requires java.desktop;
    requires twilio;
    requires org.apache.logging.log4j;

    requires org.kordamp.ikonli.core;
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.fontawesome;
    requires java.mail;


    opens MyApp.sqlapp to javafx.fxml;
    exports MyApp.sqlapp;
    exports MyApp.SplashScreen;
    opens MyApp.SplashScreen to javafx.fxml;
    exports MyApp.WebEngine;
    opens MyApp.WebEngine to javafx.fxml;

}