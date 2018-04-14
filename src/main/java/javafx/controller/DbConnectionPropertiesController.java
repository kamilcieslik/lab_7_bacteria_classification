package javafx.controller;

import app.BacteriaClassifier;
import database.DbConnection;
import javafx.CustomMessageBox;
import javafx.ListenerMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.NoRouteToHostException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnectionPropertiesController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private TextField textFieldHost, textFieldPort, textFieldDatabase, textFieldLogin, textFieldPassword;
    @FXML
    private Label labelHost, labelPort, labelDatabase, labelLogin, labelPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        ListenerMethods listenerMethods = new ListenerMethods();
        textFieldHost.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("(^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9]" +
                                "[0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$)|(^localhost$)", textFieldHost, labelHost,
                        "Podaj IP hosta.", "Niepoprawny format."));

        textFieldPort.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("^[1-9][0-9]{0,3}$", textFieldPort, labelPort,
                        "Podaj nr portu.", "Niepoprawny format."));

        textFieldDatabase.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("^\\S*$", textFieldDatabase, labelDatabase,
                        "Podaj nazwę bazy danych.", "Niepoprawny format."));

        textFieldLogin.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("^\\S*$", textFieldLogin, labelLogin,
                        "Podaj login uprawnionego konta.", "Niepoprawny format."));

        textFieldPassword.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("^\\S*$", textFieldPassword, labelPassword,
                        "Podaj hasło uprawnionego konta.", "Niepoprawny format."));

        textFieldHost.setText(BacteriaClassifier.preferences.get("bacteria_classifier_host", ""));
        textFieldPort.setText(BacteriaClassifier.preferences.get("bacteria_classifier_port", ""));
        textFieldDatabase.setText(BacteriaClassifier.preferences.get("bacteria_classifier_database", ""));
        textFieldLogin.setText(BacteriaClassifier.preferences.get("bacteria_classifier_login", ""));
        textFieldPassword.setText(BacteriaClassifier.preferences.get("bacteria_classifier_password", ""));
    }

    @FXML
    void buttonCancel_onAction() {
        Stage stage = (Stage) textFieldHost.getScene().getWindow();
        stage.close();
    }

    @FXML
    void buttonConnect_onAction() {
        if (labelHost.getText().equals("") && labelPort.getText().equals("") && labelDatabase.getText().equals("")
                && labelLogin.getText().equals("") && labelPassword.getText().equals("")) {
            String db_url = "";
            db_url += textFieldHost.getText() + ":";
            db_url += textFieldPort.getText() + "/";
            db_url += textFieldDatabase.getText();

            try {
                BacteriaClassifier.dbConnection = new DbConnection(db_url, textFieldLogin.getText(), textFieldPassword.getText());
                BacteriaClassifier.dbConnectionStatus.setValue(true);
                BacteriaClassifier.preferences.put("bacteria_classifier_host", textFieldHost.getText());
                BacteriaClassifier.preferences.put("bacteria_classifier_port", textFieldPort.getText());
                BacteriaClassifier.preferences.put("bacteria_classifier_database", textFieldDatabase.getText());
                BacteriaClassifier.preferences.put("bacteria_classifier_login", textFieldLogin.getText());
                BacteriaClassifier.preferences.put("bacteria_classifier_password", textFieldPassword.getText());
                Stage stage = (Stage) textFieldHost.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof NoRouteToHostException) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Próba połączenia nie powiodła się.",
                            "Powód: wystąpił brak połączenia z Internetem lub blokada Firewall.").showAndWait();
                } else
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Próba połączenia nie powiodła się.",
                            "Powód: " + e.getMessage()).showAndWait();
            }
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja próby połączenia nie powiedzie się.",
                    "Powód: nie wszystkie pola zostały uzupełnione lub mają niepoprawny format.").showAndWait();
    }
}