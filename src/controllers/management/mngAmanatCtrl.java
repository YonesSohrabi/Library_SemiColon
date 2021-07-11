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

        try {
            searchAmanat();
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
        }

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
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        ktbIDRB.setOnAction(e -> {
            if (ktbIDRB.isSelected()){
                hidePaneJostejo();
                ktbIDPane.setVisible(true);
            }
        });

        ktbIDSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(ktbIDTXT.getText(),"ktbID");
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        usrIDRB.setOnAction(e -> {
            if (usrIDRB.isSelected()){
                hidePaneJostejo();
                usrIDPane.setVisible(true);
            }
        });

        usrIDSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(usrIDTXT.getText(),"usrID");
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        allAmanatRB.setOnAction(e -> {
            try {
                hidePaneJostejo();
                searchAmanat();
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void hidePaneJostejo() {
        ktbIDPane.setVisible(false);
        usrIDPane.setVisible(false);
        amtVazeiatPane.setVisible(false);
    }


    public void searchAmanat() throws SQLException, IOException, ParseException {
        List<Amanat> amanats = new ArrayList<>(Database.readAmanatsDB());
        showAmanat(amanats);
    }

    public void searchAmanat(String text,String lable) throws SQLException, IOException, ParseException {
        List<Amanat> amanats = new ArrayList<>(Database.readAmanatsDB(text,lable));
        showAmanat(amanats);
    }


    public void showAmanat(List<Amanat> amanats) throws IOException {
        itemAmanatList.getChildren().clear();
        int rows = 0;
        for (Amanat amanat : amanats) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/fxmls/management/itemAmanat.fxml"));
            HBox amanatBox = fxmlLoader.load();
            itemAmanatCtrl itemAmanatCtrl = fxmlLoader.getController();
            itemAmanatCtrl.setItemAmanat(amanat);
            itemAmanatList.addRow(rows++, amanatBox);
            GridPane.setMargin(amanatBox, new Insets(5, 0, 5, 10));

        }
    }

}

