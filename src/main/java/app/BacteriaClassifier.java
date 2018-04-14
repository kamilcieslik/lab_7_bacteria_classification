package app;

import database.BacteriaClassifierService;
import database.DbConnection;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.controller.WelcomeBannerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BacteriaClassifier extends Application {
    public static DbConnection dbConnection;
    public static BacteriaClassifierService bacteriaClassifierService;
    public static BooleanProperty dbConnectionStatus = new SimpleBooleanProperty(false); 

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getClassLoader().getResource("fxml/welcome_banner.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            primaryStage.setTitle("Bacteria Classifier");
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.resizableProperty().setValue(Boolean.FALSE);
            primaryStage.setScene(new Scene(root, 819, 325));
            WelcomeBannerController loaderController = loader.getController();
            primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> {
                Thread windowShownListener = new Thread(loaderController::initMainScene);
                windowShownListener.start();
            });
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(BacteriaClassifier.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    public void stop()  {
        if (dbConnection!=null) {
            try {
                dbConnection.closeConnection();
            } catch (SQLException e) {
                dbConnection=null;
            }
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}