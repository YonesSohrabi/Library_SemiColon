package controllers.management;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controllers.login.admLoginPageCtrl;
import model.User;

public class mngSettingCtrl extends mngStage implements Initializable {

    @FXML
    private JFXButton editBTN;

    @FXML
    private JFXTextField admUserNameTXT;

    @FXML
    private JFXTextField admLNameTXT;

    @FXML
    private JFXTextField admCodeMeliTXT;

    @FXML
    private JFXTextField admFNameTXT;

    @FXML
    private JFXTextField admPassTXT;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            fillInfoAdmin(admLoginPageCtrl.ID);
        } catch (SQLException e) {
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

        booksBTN.setOnAction(e ->{
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

        faliatBTN.setOnAction(e ->{
            Stage stage = (Stage) faliatBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("amanat");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gozareshBTN.setOnAction(e ->{
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        editBTN.setOnAction(e -> {
            try {
                updateInfoAdmin(admLoginPageCtrl.ID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        exitBTN.setOnAction(e ->{
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            stage.close();
        });

    }


    private void fillInfoAdmin(String adminID) throws SQLException {
        Admin admin = infoAdmin(adminID);
        admFNameTXT.setText(admin.getFirstName());
        admLNameTXT.setText(admin.getLastName());
        admCodeMeliTXT.setText(admin.getCodeMeli());
        admUserNameTXT.setText(admin.getUserName());
        admPassTXT.setText(admin.getPassword());
    }

    private Admin infoAdmin(String adminID) throws SQLException {
        List<Admin> admins = new ArrayList<>(Database.getInfoAdmin());
        Admin admin = new Admin();
        for (Admin adm:admins){
            if (adm.getID().equals(adminID)){
                admin = adm;
                break;
            }
        }
        return admin;
    }

    private void updateInfoAdmin(String adminID) throws SQLException {
        Admin admin = infoAdmin(adminID);
        admin.setFirstName(admFNameTXT.getText());
        admin.setLastName(admLNameTXT.getText());
        admin.setCodeMeli(admCodeMeliTXT.getText());
        admin.setUserName(admUserNameTXT.getText());
        admin.setPassword(admPassTXT.getText());
        Database.updateAdmin(admin,adminID);
    }
}

