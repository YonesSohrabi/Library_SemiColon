package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class UserInfoPageCtrl implements Initializable {

    @FXML
    private Button btn_Signout;

    @FXML
    private Button btn_BookList;

    @FXML
    private Label lbl_ID;

    @FXML
    private Label lbl_UserName;

    @FXML
    private Label lbl_AccessLevel;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_family;

    @FXML
    private Label lbl_fullname;

    public void set_user_info() {
        User user = new User();
        try {
            Database.makeConnection();
            user = Database.set_home_items();
            Database.closeConnection();
            lbl_fullname.setText(user.getFirstName() + " " + user.getLastName());
            lbl_name.setText(user.getFirstName() + " : نام");
            lbl_family.setText(user.getLastName() + " : نام خانوادگی");
            lbl_ID.setText("آی دی : " + user.getID());
            lbl_AccessLevel.setText("سطح دسترسی : کتابدار");
            lbl_UserName.setText(user.getUserName() + " : نام کاربری");
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        set_user_info();
    }

    public void btn_Home_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("Home");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public void btn_info_clicked(ActionEvent actionEvent) {
        set_user_info();
    }
    public void btn_BookList_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("BookList");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void btn_Signout_clicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btn_Signout.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchLogin("loginPage");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void editperson_btn_clicked(ActionEvent actionEvent) throws IOException {
   //     editUserPageCtrl edituser_page = new editUserPageCtrl();
   //     edituser_page.showpage();
    }


}
