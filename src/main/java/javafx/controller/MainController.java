package javafx.controller;

import app.BacteriaClassifier;
import database.DbConnection;
import javafx.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private Label labelDbConnectionStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");
        BacteriaClassifier.dbConnectionStatus.addListener((o, oldVal, newVal) -> {
            if (newVal) {
                labelDbConnectionStatus.setStyle("-fx-text-fill: #008000;");
                labelDbConnectionStatus.setText("online");
            } else {
                labelDbConnectionStatus.setStyle("-fx-text-fill: #ff0000;");
                labelDbConnectionStatus.setText("offline");
            }
        });

        attemptToConnectToDatabaseUsingTheCurrentProperties();
    }

    @FXML
    void menuItemDbConnection_onAction() {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getClassLoader().getResource("fxml/db_connection_properties.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setTitle("Bacteria Classifier: Database Connection");
            stage.getIcons().add(new Image("image/app_icon.png"));
            stage.setScene(new Scene(root, 583, 571));
            stage.showAndWait();
        } catch (IOException ioEcx) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    private void attemptToConnectToDatabaseUsingTheCurrentProperties() {
        String host = BacteriaClassifier.preferences.get("bacteria_classifier_host", "");
        String port = BacteriaClassifier.preferences.get("bacteria_classifier_port", "");
        String database = BacteriaClassifier.preferences.get("bacteria_classifier_database", "");
        String login = BacteriaClassifier.preferences.get("bacteria_classifier_login", "");
        String password = BacteriaClassifier.preferences.get("bacteria_classifier_password", "");

        if (!(host.equals("") || port.equals("") || database.equals("") || login.equals("") || password.equals(""))) {
            try {
                BacteriaClassifier.dbConnection = new DbConnection(host + ":" + port + "/" + database, login, password);
                BacteriaClassifier.dbConnectionStatus.setValue(true);
            } catch (SQLException e) {
                if (!(e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof NoRouteToHostException)) {
                    BacteriaClassifier.preferences.put("bacteria_classifier_host", "");
                    BacteriaClassifier.preferences.put("bacteria_classifier_port", "");
                    BacteriaClassifier.preferences.put("bacteria_classifier_database", "");
                    BacteriaClassifier.preferences.put("bacteria_classifier_login", "");
                    BacteriaClassifier.preferences.put("bacteria_classifier_password", "");
                }
            }
        }
    }
}