package javafx.controller;

import app.BacteriaClassifier;
import database.BacteriaClassifierService;
import database.DbConnection;
import database.entity.Examined;
import database.procedures_results.HistoryViewProcedureResultModel;
import javafx.CustomMessageBox;
import javafx.ListenerMethods;
import javafx.UnclassifiedBacteria;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xml_parser.ExaminedBacteriaXmlWriteParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private Label labelDbConnectionStatus, labelGenotype;
    @FXML
    private TextField textFieldGenotype;
    @FXML
    private TableView<Examined> tableViewExaminedBacteria;
    @FXML
    private TableColumn<Examined, String> tableColumnExaminedBacteria_Genotype;
    @FXML
    private TableColumn<Examined, String> tableColumnExaminedBacteria_Class;
    @FXML
    private TableView<HistoryViewProcedureResultModel> tableViewHistory;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, Date> tableColumnHistory_Date;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, String> tableColumnHistory_Genotype;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, Integer> tableColumnHistory_Alpha;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, Integer> tableColumnHistory_Beta;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, Integer> tableColumnHistory_Gamma;
    @FXML
    private TableColumn<HistoryViewProcedureResultModel, String> tableColumnHistory_Class;
    @FXML
    private TableView<UnclassifiedBacteria> tableViewUnclassifiedBacteria;
    @FXML
    private TableColumn<UnclassifiedBacteria, String> tableColumnUnclassifiedBacteria_Genotype;
    @FXML
    private TableColumn<UnclassifiedBacteria, Integer> tableColumnUnclassifiedBacteria_Alpha;
    @FXML
    private TableColumn<UnclassifiedBacteria, Integer> tableColumnUnclassifiedBacteria_Beta;
    @FXML
    private TableColumn<UnclassifiedBacteria, Integer> tableColumnUnclassifiedBacteria_Gamma;

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

        ListenerMethods listenerMethods = new ListenerMethods();
        textFieldGenotype.textProperty().addListener((observable, oldValue, newValue) -> listenerMethods
                .changeLabelTextField("^[0-9]{6}$", textFieldGenotype, labelGenotype,
                        "Podaj genotyp bakterii.", "Niepoprawny format."));

        attemptToConnectToDatabaseUsingTheCurrentProperties();
        initTableViews();
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

    @FXML
    void buttonAddToList_onAction() {
        if (labelGenotype.getText().equals("")) {
            BacteriaClassifier.unclassifiedBacteriaObservableList.add(new UnclassifiedBacteria(textFieldGenotype.getText()));
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja dodania genotypu do listy oczekujących na klasyfikację nie powiedzie się.",
                    "Powód: nie uzupełniono genotypu lub wprowadzono dane o niepoprawnym formacie.");
    }

    @FXML
    void buttonClassifyAllBacteria_onAction() {
        if (BacteriaClassifier.unclassifiedBacteriaObservableList.size() == 0)
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja klasyfikacji oczekujących bakterii nie powiedzie się.",
                    "Powód: tablica jest pusta.");
        else {
            // TODO:

            BacteriaClassifier.unclassifiedBacteriaObservableList.clear();
        }
    }

    @FXML
    void buttonClassify_onAction() {
        if (labelGenotype.getText().equals("")) {
            // TODO:
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja klasyfikacji nie powiedzie się.",
                    "Powód: nie uzupełniono genotypu lub wprowadzono dane o niepoprawnym formacie.");
    }

    @FXML
    void buttonDeleteSelectedUnclassifiedBacteria_onAction() {
        if (tableViewUnclassifiedBacteria.getSelectionModel().getSelectedItem() != null) {
            BacteriaClassifier.unclassifiedBacteriaObservableList
                    .remove(tableViewUnclassifiedBacteria.getSelectionModel().getSelectedItem());
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja usunięcia nie powiedzie się.",
                    "Powód: nie zaznaczono niesklasyfikowanej bakterii.");
    }

    @FXML
    void buttonDisplayWholeHistory_onAction() {
        try {
            BacteriaClassifier.historyObservableList.clear();
            BacteriaClassifier.historyObservableList.addAll(BacteriaClassifier.bacteriaClassifierService
                    .getHistoryOfExaminedBacteria(null));
        } catch (SQLException e) {
            BacteriaClassifier.databaseConnectionHasBeenLost(e);
            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof NoRouteToHostException) {
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Historia nie zostanie wyświetlona.",
                        "Powód: wystąpił brak połączenia z Internetem lub blokada Firewall.").showAndWait();
            } else
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Historia nie zostanie wyświetlona.",
                        "Powód: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    void buttonWriteToXml_onAction() {
        if (BacteriaClassifier.examinedObservableList.size() == 0)
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja zapisu sklasyfikowanych bakterii do pliku .xml nie powiedzie się.",
                    "Powód: tablica sklasyfikowanych bakterii jest pusta.")
                    .showAndWait();
        else {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Bacteria Classifier: Write To Xml File");
            File directory = chooser.showDialog(textFieldGenotype.getScene().getWindow());
            if (directory != null) {
                try {
                    String directoryPath = directory.getAbsolutePath();
                    ExaminedBacteriaXmlWriteParser examinedBacteriaXmlWriteParser = new ExaminedBacteriaXmlWriteParser();
                    examinedBacteriaXmlWriteParser.writeExaminedBacteriaList(BacteriaClassifier.examinedObservableList, directoryPath);
                } catch (JAXBException e) {
                    String exceptionMessage;
                    if (e.getCause() != null && !e.getCause().getMessage().isEmpty())
                        exceptionMessage = e.getCause().getMessage();
                    else
                        exceptionMessage = e.getMessage();
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Operacja zapisu sklasyfikowanych bakterii do pliku .xml nie powiodła się.",
                            "Powód: " + exceptionMessage)
                            .showAndWait();
                }
            }
        }
    }

    @FXML
    void tableViewExaminedBacteria_onMouseClicked() {
        if (tableViewExaminedBacteria.getSelectionModel().getSelectedItem() != null) {
            try {
                BacteriaClassifier.historyObservableList.clear();
                BacteriaClassifier.historyObservableList.addAll(BacteriaClassifier.bacteriaClassifierService
                        .getHistoryOfExaminedBacteria(tableViewExaminedBacteria.getSelectionModel().getSelectedItem().getGenotype()));
            } catch (SQLException e) {
                BacteriaClassifier.databaseConnectionHasBeenLost(e);
                if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause() instanceof NoRouteToHostException) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Historia nie zostanie wyświetlona.",
                            "Powód: wystąpił brak połączenia z Internetem lub blokada Firewall.").showAndWait();
                } else
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Historia nie zostanie wyświetlona.",
                            "Powód: " + e.getMessage()).showAndWait();
            }
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Historia nie zostanie wyświetlona.",
                    "Powód: nie zaznaczono sklasyfikowanej bakterii.");
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
                BacteriaClassifier.bacteriaClassifierService = new BacteriaClassifierService(BacteriaClassifier.dbConnection);
            } catch (SQLException e) {
                e.printStackTrace();
                BacteriaClassifier.databaseConnectionHasBeenLost(e);
            }
        }
    }

    private void initTableViews() {
        tableColumnExaminedBacteria_Genotype.setCellValueFactory(new PropertyValueFactory<>("genotype"));
        tableColumnExaminedBacteria_Class.setCellValueFactory(new PropertyValueFactory<>("bacteriaClass"));

        tableColumnHistory_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnHistory_Date.setCellFactory(col -> customDateFormat());
        tableColumnHistory_Genotype.setCellValueFactory(new PropertyValueFactory<>("genotype"));
        tableColumnHistory_Alpha.setCellValueFactory(new PropertyValueFactory<>("alpha"));
        tableColumnHistory_Beta.setCellValueFactory(new PropertyValueFactory<>("beta"));
        tableColumnHistory_Gamma.setCellValueFactory(new PropertyValueFactory<>("gamma"));
        tableColumnHistory_Class.setCellValueFactory(new PropertyValueFactory<>("bacteriaClass"));

        tableColumnUnclassifiedBacteria_Genotype.setCellValueFactory(new PropertyValueFactory<>("genotype"));
        tableColumnUnclassifiedBacteria_Alpha.setCellValueFactory(new PropertyValueFactory<>("alpha"));
        tableColumnUnclassifiedBacteria_Beta.setCellValueFactory(new PropertyValueFactory<>("beta"));
        tableColumnUnclassifiedBacteria_Gamma.setCellValueFactory(new PropertyValueFactory<>("gamma"));

        tableViewExaminedBacteria.setItems(BacteriaClassifier.examinedObservableList);
        tableViewHistory.setItems(BacteriaClassifier.historyObservableList);
        tableViewUnclassifiedBacteria.setItems(BacteriaClassifier.unclassifiedBacteriaObservableList);

        BacteriaClassifier.startNewSessionWithDatabase();
    }

    private TableCell<HistoryViewProcedureResultModel, Date> customDateFormat() {
        return new TableCell<HistoryViewProcedureResultModel, Date>() {
            @Override
            public void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date));
                }
            }
        };
    }
}