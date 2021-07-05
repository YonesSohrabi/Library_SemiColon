package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Amanat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngAmanatCtrl extends mngStage implements Initializable {

    @FXML
    private GridPane itemAmanatList;

    @FXML
    private JFXRadioButton ktbIDRB;

    @FXML
    private ToggleGroup jostejoRadio;

    @FXML
    private JFXRadioButton usrIDRB;

    @FXML
    private JFXRadioButton amtVazeiatRB;

    @FXML
    private JFXRadioButton allAmanatRB;

    @FXML
    private Pane ktbIDPane;

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXButton ktbIDSearchBTN;

    @FXML
    private Pane usrIDPane;

    @FXML
    private JFXTextField usrIDTXT;

    @FXML
    private JFXButton usrIDSearchBTN;

    @FXML
    private Pane amtVazeiatPane;

    @FXML
    private JFXTextField amtMohlatTXT;

    @FXML
    private JFXButton amtMohlatSearchBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dashboardBTN.setOnAction(e -> {
            Stage stage = (Stage) dashboardBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("dashboard");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        booksBTN.setOnAction(e -> {
            Stage stage = (Stage) booksBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("book");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        ketabdarBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("ketabdar");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gozareshBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        settingBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exitBTN.setOnAction(e -> {
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            stage.close();
        });

    }

}

