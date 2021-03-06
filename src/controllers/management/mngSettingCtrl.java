package controllers.management;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controllers.login.admLoginPageCtrl;

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

    @FXML
    private VBox editSettingLBL;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // پرکردن اطلاعات مدیریت
        try {
            fillInfoAdmin(admLoginPageCtrl.ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* شروع ایونت های مربوط به قسمت دکمه های منو برنامه
        *
        *
        به ترتیب
        داشبورد -کتاب ها - کتابدار - امانت - تنطیمات
        *
         */
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

        faliatBTN.setOnAction(e -> {
            Stage stage = (Stage) faliatBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("amanat");
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

        editBTN.setOnAction(e -> {
            if (!(admUserNameTXT.getText().equals("") || admPassTXT.getText().equals("") ||
                    admFNameTXT.getText().equals("") || admLNameTXT.getText().equals("") ||
                    admCodeMeliTXT.getText().equals(""))) {
                try {
                    updateInfoAdmin(admLoginPageCtrl.ID);
                    editSettingLBL.setVisible(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {

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

        /* پایان ایونت های منو
         *
         */

    }


    // متد پرکردن اطلاعات در صفحه تنظیمات
    private void fillInfoAdmin(String adminID) throws SQLException {
        Admin admin = infoAdmin(adminID);
        admFNameTXT.setText(admin.getFirstName());
        admLNameTXT.setText(admin.getLastName());
        admCodeMeliTXT.setText(admin.getCodeMeli());
        admUserNameTXT.setText(admin.getUserName());
        admPassTXT.setText(admin.getPassword());
    }

    //مند خوندن اطلاعات مدیریت از دیتابیس
    private Admin infoAdmin(String adminID) throws SQLException {
        List<Admin> admins = new ArrayList<>(Database.getInfoAdmin());
        Admin admin = new Admin();
        for (Admin adm : admins) {
            if (adm.getID().equals(adminID)) {
                admin = adm;
                break;
            }
        }
        return admin;
    }

    // متد آپدیت و ویرایش اطلاعات مدیریت
    private void updateInfoAdmin(String adminID) throws SQLException {
        Admin admin = infoAdmin(adminID);
        admin.setFirstName(admFNameTXT.getText());
        admin.setLastName(admLNameTXT.getText());
        admin.setCodeMeli(admCodeMeliTXT.getText());
        admin.setUserName(admUserNameTXT.getText());
        admin.setPassword(admPassTXT.getText());
        Database.updateAdmin(admin, adminID);
    }
}

