package app;

import database.BacteriaClassifierService;
import database.DbConnection;
import database.entity.Examined;
import database.entity.Flagella;
import database.entity.Toughness;
import database.procedures_results.HistoryViewProcedureResultModel;
import javafx.UnclassifiedBacteria;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.controller.WelcomeBannerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class BacteriaClassifier extends Application {
    public static Preferences preferences;
    public static DbConnection dbConnection;
    public static BacteriaClassifierService bacteriaClassifierService;
    public static BooleanProperty dbConnectionStatus = new SimpleBooleanProperty(false);
    public static ObservableList<Examined> examinedObservableList = FXCollections.observableArrayList();
    public static ObservableList<HistoryViewProcedureResultModel> historyObservableList = FXCollections.observableArrayList();
    public static ObservableList<UnclassifiedBacteria> unclassifiedBacteriaObservableList = FXCollections.observableArrayList();
    public static List<Flagella> flagellaList = new ArrayList<>();
    public static List<Toughness> toughnessList = new ArrayList<>();

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
        preferences = Preferences.userRoot();
        launch(args);
    }

    public static void startNewSessionWithDatabase(){
        BacteriaClassifier.examinedObservableList.clear();
        BacteriaClassifier.historyObservableList.clear();
        BacteriaClassifier.unclassifiedBacteriaObservableList.clear();
        BacteriaClassifier.flagellaList.clear();
        BacteriaClassifier.toughnessList.clear();
        if (BacteriaClassifier.bacteriaClassifierService != null)
            try {
                BacteriaClassifier.examinedObservableList.addAll(BacteriaClassifier.bacteriaClassifierService.getExaminedList());
                BacteriaClassifier.flagellaList.addAll(BacteriaClassifier.bacteriaClassifierService.getFlagellaList());
                BacteriaClassifier.toughnessList.addAll(BacteriaClassifier.bacteriaClassifierService.getToughnessList());
            } catch (SQLException e) {
            e.printStackTrace();
                databaseConnectionHasBeenLost(e);
            }
    }

    public static void databaseConnectionHasBeenLost(SQLException e) {
        if (!(e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof NoRouteToHostException)) {
            BacteriaClassifier.preferences.put("bacteria_classifier_host", "");
            BacteriaClassifier.preferences.put("bacteria_classifier_port", "");
            BacteriaClassifier.preferences.put("bacteria_classifier_database", "");
            BacteriaClassifier.preferences.put("bacteria_classifier_login", "");
            BacteriaClassifier.preferences.put("bacteria_classifier_password", "");
        }
        
        BacteriaClassifier.bacteriaClassifierService = null;
        BacteriaClassifier.dbConnectionStatus.setValue(false);
    }
}