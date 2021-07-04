package controllers.management;



import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mngDashboardCtrl extends mngStage implements Initializable{


    @FXML
    private Label numKolAmanatLBL;

    @FXML
    private Label numAmanatFeliLBL;

    @FXML
    private Label numHameKetabhaLBL;

    @FXML
    private Label numKarbaranLBL;

    @FXML
    private Label odat1;

    @FXML
    private Label odat2;

    @FXML
    private Label odat3;

    @FXML
    private Label odat4;

    @FXML
    private Label amanat1;

    @FXML
    private Label amanat2;

    @FXML
    private Label amanat3;

    @FXML
    private Label amanat4;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        booksBTN.setOnAction(e ->{
            Stage stage = (Stage) booksBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("book");
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
