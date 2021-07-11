package controllers.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.alert;
import controllers.login.LoginPageCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import java.io.IOException;
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

    /**                    صفحه ی ویرایش اطلاعات کاربر
     *
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ست کردن فیلد یوزر آی دی در صفحه ی ویرایش اطلاعات با آی دی کاربر
        usrIDTXT.setText(LoginPageCtrl.get_id());
        try {
            getDataUser(LoginPageCtrl.get_id());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //خروج
        exitBTN.setOnAction(e -> {
            closeBTN();
        });
        //انجام ویرایش اطلاعات کاربر
        editBTN.setOnAction(e -> {
            try {
                updateUserinDB(usrIDTXT.getText());
                alert.edit_userinfo_successfully();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            closeBTN();
        });
    }
    //گرفتن اطلاعات کاربر به منظور ست کردن فیلدهای پیش فرض صفحه
    private void getDataUser(String usrIDTXT) throws SQLException, ClassNotFoundException {
        User user = Database.getItemUserDB(usrIDTXT);
        usrFNameTXT.setText(user.getFirstName());
        usrLNameTXT.setText(user.getLastName());
        userNameTXT.setText(user.getUserName());
        passwordTXT.setText(user.getPassword());
        usrCodeMeliTXT.setText(user.getCodeMeli());
    }
    //متد مربوط به ذخیره کردن اطلاعات جدید(ویرایش شده ی)کاربر به دیتابیس
    private void updateUserinDB(String usrIDTXT) throws SQLException, ClassNotFoundException {
        User user = Database.getItemUserDB(usrIDTXT);
        user.setFirstName(usrFNameTXT.getText());
        user.setLastName(usrLNameTXT.getText());
        user.setCodeMeli(usrCodeMeliTXT.getText());
        user.setPassword(passwordTXT.getText());
        user.setUserName(userNameTXT.getText());
        user.setCodeMeli(usrCodeMeliTXT.getText());
        //آپدیت کردن اطلاعات کاربر در دیتابیس
        Database.updateUser(user,usrIDTXT);
    }
    //خالی کردن فیلدها و بستن پنجره
    public void closeBTN(){
        ((Stage)exitBTN.getScene().getWindow()).close();
        usrFNameTXT.setText("");
        usrLNameTXT.setText("");
        userNameTXT.setText("");
        passwordTXT.setText("");
        usrCodeMeliTXT.setText("");
    }
    //نمایش پنجره ی ویرایش اطلاعات کاربر
    public void showpage() throws IOException {
        FXMLLoader loader = new FXMLLoader(editUserPageCtrl.class.getResource("../../view/fxmls/user/editUserPage.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
