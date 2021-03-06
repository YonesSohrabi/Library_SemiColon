package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class editUserPageCtrl implements Initializable {
    @FXML
    private JFXButton exitBTN;

    @FXML
    private JFXButton editBTN;

    @FXML
    private JFXTextField userNameTXT;

    @FXML
    private JFXTextField usrIDTXT;

    @FXML
    private JFXTextField usrLNameTXT;

    @FXML
    private JFXTextField usrCodeMeliTXT;

    @FXML
    private JFXTextField usrFNameTXT;

    @FXML
    private JFXPasswordField passwordTXT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        usrIDTXT.setText(itemUserCtrl.userID);

        // گرفتن اطلاعاتی که در صفحه ویرایش کاربر وجود دارد
        try {
            getDataUser(usrIDTXT.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ایونت بستن صفحه ویرایش کاربر
        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        // ایونت ادیت صفحه ویرایش کاربر
        editBTN.setOnAction(e -> {
            try {
                updateUserinDB(usrIDTXT.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            closeBTN();
        });
    }

    // متد گرفتن اطلاعات کاربر و پرکردن تکست فیلدا
    private void getDataUser(String usrIDTXT) throws SQLException {
        User user = Database.getItemUserDB(usrIDTXT);
        usrFNameTXT.setText(user.getFirstName());
        usrLNameTXT.setText(user.getLastName());
        userNameTXT.setText(user.getUserName());
        passwordTXT.setText(user.getPassword());
        usrCodeMeliTXT.setText(user.getCodeMeli());
    }

    // متد آپدیت و ویرایش اطلاعات کاربر در دیتابیس
    private void updateUserinDB(String usrIDTXT) throws SQLException {
        User user = Database.getItemUserDB(usrIDTXT);
        user.setFirstName(usrFNameTXT.getText());
        user.setLastName(usrLNameTXT.getText());
        user.setCodeMeli(usrCodeMeliTXT.getText());
        user.setPassword(passwordTXT.getText());
        Database.updateUser(user,usrIDTXT);
    }

    // متد بستن صفحه
    public void closeBTN(){
        ((Stage)exitBTN.getScene().getWindow()).close();
        itemUserCtrl.editUserPage = null;
    }

}
