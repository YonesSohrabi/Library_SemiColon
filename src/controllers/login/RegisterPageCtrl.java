package controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.alert;
import controllers.switchSenceCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class  RegisterPageCtrl implements Initializable {

    @FXML
    private JFXPasswordField txt_Field_Password_R;

    @FXML
    private JFXPasswordField txt_Field_ConfirmPassword;

    @FXML
    private JFXTextField txt_Field_LastName;

    @FXML
    private JFXTextField txt_Field_FirstName;

    @FXML
    private JFXTextField txt_Field_UserName_R;

    @FXML
    private JFXButton exit_btn_regis;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private Label labelalrtRgis;

    User librarian1 = new User();

    //انجام ثبت نام
    public void press_Registration_btn(ActionEvent actionEvent) throws SQLException {

        //چک کردن خالی نبودن فیلد های مورد نیاز برای ثبت نام
        if (txt_Field_FirstName.getText().compareTo("") == 0 || txt_Field_LastName.getText().compareTo("") == 0 ||
                txt_Field_Password_R.getText().compareTo("") == 0 ||
                txt_Field_ConfirmPassword.getText().compareTo("") == 0) {
            labelalrtRgis.setText("complate all the fields");
        }
        //چک کردن برابر بودن پسوورد و تاییدیه ی پسوورد
        else if (!(txt_Field_Password_R.getText().equals(txt_Field_ConfirmPassword.getText()))) {
            labelalrtRgis.setText("Enter confirmPassword correctly");
        }else if(Database.testUsreNames(txt_Field_UserName_R.getText()) == false){
            labelalrtRgis.setText("This UserName is not available");
        } else {
            //ست کردن یک نمونه از کلاس user با اطلاعات ثبت شده توسط کاربر
            librarian1.setFirstName(txt_Field_FirstName.getText());
            librarian1.setLastName(txt_Field_LastName.getText());
            librarian1.setPassword(txt_Field_Password_R.getText());
            librarian1.setUserName(txt_Field_UserName_R.getText());
            Random rnd = new Random();
            String id = String.valueOf(rnd.nextInt(9000) + 1000);
            librarian1.setID(id);

            try {
                //اتصال به دیتابیس
                Database.makeConnection();
                Database.registerUser(librarian1);
                //خالی کردن تکست فیلدها
                txt_Field_FirstName.setText("");
                txt_Field_LastName.setText("");
                txt_Field_UserName_R.setText("");
                txt_Field_Password_R.setText("");
                txt_Field_ConfirmPassword.setText("");
                //برگشت به صفحه ی لاگین با اتمام مراحل ثبت نام
                Stage stage = (Stage) btn_Back.getScene().getWindow();
                switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
                try {
                    switchSenceCtrl.sceneSwitchLogin("loginPage");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                // مشکل در ثبت نام
            } catch (Exception e) {
                System.out.println(e);
                labelalrtRgis.setText("Registration Failed pleaes TryAgain");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //بازگشت به صفحه ی لاگین پیج با زدن دکمه ی back
        btn_Back.setOnAction(e -> {
            Stage stage = (Stage) btn_Back.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //خروج
        exit_btn_regis.setOnAction(e -> {
            Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
            stage2.close();
        });
    }
}

