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
import java.util.Random;
import java.util.ResourceBundle;

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

    User librarian1 = new User();

    //انجام ثبت نام
    public void press_Registration_btn(ActionEvent actionEvent) {
        //چک کردن خالی نبودن فیلد های مورد نیاز برای ثبت نام
        if (txt_Field_FirstName.getText().compareTo("") == 0 || txt_Field_LastName.getText().compareTo("") == 0 ||
                txt_Field_Password_R.getText().compareTo("") == 0 ||
                txt_Field_ConfirmPassword.getText().compareTo("") == 0) {
            alert.regis_fillall();
        }
        //چک کردن برابر بودن پسوورد و تاییدیه ی پسوورد
        else if (!(txt_Field_Password_R.getText().equals(txt_Field_ConfirmPassword.getText()))) {
            alert.regis_wrongconfirmpass();
        } else {
            //ست کردن یک نمونه از کلاس person با اطلاعات ثبت شده توسط کاربر
            librarian1.setFirstName(txt_Field_FirstName.getText());
            librarian1.setLastName(txt_Field_LastName.getText());
            librarian1.setPassword(txt_Field_Password_R.getText());
            librarian1.setUserName(txt_Field_UserName_R.getText());
            System.out.println("Password =" + librarian1.getPassword());
            Random rnd = new Random();
            String id = String.valueOf(rnd.nextInt(1000));
            System.out.println("id = " + id);
            librarian1.setID(id);
        }

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

//

            // مشکل در ثبت نام
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e);
            alert.regis_Faild();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_Back.setOnAction(e -> {
            Stage stage = (Stage) btn_Back.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exit_btn_regis.setOnAction(e -> {
            Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
            stage2.close();
        });
    }
}

