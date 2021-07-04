package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mngBookCtrl extends mngStage implements Initializable {



    @FXML
    private ToggleGroup jostejoRadio;

    @FXML
    private JFXRadioButton nameBookRB;

    @FXML
    private JFXRadioButton numBookRB;

    @FXML
    private JFXRadioButton vazeiatBookRB;

    @FXML
    private JFXRadioButton allBookRB;

    @FXML
    private Pane ktbNamePane;

    @FXML
    private JFXTextField ktbNametxt;

    @FXML
    private JFXButton ktbNameSearchbtn;

    @FXML
    private Pane ktbNumPane;

    @FXML
    private JFXTextField ktbNumtxt;

    @FXML
    private JFXButton ktbNumSearchbtn;

    @FXML
    private Pane ktbVazeiatPane;

    @FXML
    private ToggleGroup vazeiatJostejo;

    @FXML
    private JFXButton addBookbtn;

    @FXML
    private JFXRadioButton mojodBTN;

    @FXML
    private JFXRadioButton naMojodBTN;

    static Stage addBookPage = null;


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



        exitBTN.setOnAction(e -> {
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            stage.close();
        });


    }


}

