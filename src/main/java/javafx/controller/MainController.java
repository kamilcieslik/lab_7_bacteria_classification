package javafx.controller;

import javafx.CustomMessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private Label labelDbConnectionStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

    }

    @FXML
    void menuItemDbConnection_onAction() {

    }
}
