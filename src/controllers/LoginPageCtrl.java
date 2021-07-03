package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class  LoginPageCtrl {

    @FXML
    private Label lbl_fullname;

    @FXML
    private JFXButton exit_btn;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private JFXPasswordField txt_Field_Password_R;

    @FXML
    private JFXPasswordField txt_Field_ConfirmPassword;

    @FXML
    private JFXButton btn_Register;

    @FXML
    private Label btn_SignUp;

    @FXML
    private Label btn_ForgetPassword;

    @FXML
    private JFXTextField txt_Field_ID;

    @FXML
    private JFXTextField txt_Field_LastName;

    @FXML
    private JFXTextField txtfield_UserName;

    @FXML
    private JFXTextField txt_Field_FirstName;

    @FXML
    private JFXPasswordField txtfield_Password;

    @FXML
    private JFXButton btn_Back;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;
    @FXML
    private JFXTextField txt_Field_UserName_R;

    @FXML
    private JFXButton exit_btn_regis;

    @FXML
    void press_Exit_btn(ActionEvent event) {
        Stage stage = (Stage) exit_btn.getScene().getWindow();
        stage.close();
    }

    public void press_ExitRegis_btn(ActionEvent actionEvent) {
        Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
        stage2.close();
    }

    @FXML
    void exiteRegis_clicked(ActionEvent event) {
        Stage stage2 = (Stage) exit_btn_regis.getScene().getWindow();
        stage2.close();
    }

    public void exit_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_Login.getScene().getWindow();
        stage.close();
    }

    public void press_Back_btn(ActionEvent actionEvent) {
        pane2.setVisible(false);
        pane1.setVisible(true);
    }

    User librarian1 = new User();
    //انجام ثبت نام

    public void press_ForgetPassword(MouseEvent mouseEvent) {

    }

    //باز کردن صفحه ی ثبت نام
    public void open_registration(MouseEvent mouseEvent) {

    }

    static String id;
    User librarian2 = new User();
    private double x, y;
    public void press_Login_btn(ActionEvent actionEvent)  {
        //چک کردن پر بودن فیلد های مورد نیاز
        boolean login = false;
        if (txtfield_UserName.getText().equals("") || txtfield_Password.getText().equals("")) {
            alert.login_fiilall();
        } else {
            // کانکشن به دیتابیس
            try {
                Database.makeConnection();
                login = Database.login_user(txtfield_UserName.getText(), txtfield_Password.getText());

                if (login == true) {
                    alert.login_successful();
                    // بستن لاگین پیج و باز شدن صفحه ی اصلی برنامه
                    Stage stage = (Stage) btn_Login.getScene().getWindow();
                    stage.close();
                    Database.closeConnection();
                    Stage primaryStage = new Stage();
                    AnchorPane root = null;
                    try {
                        root = (AnchorPane) FXMLLoader.load(getClass().getResource("../fxml/Home.fxml"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    Scene scene = new Scene(root, 1050, 576);
                    primaryStage.setScene(scene);
                    primaryStage.initStyle(StageStyle.UNDECORATED);
                    primaryStage.show();
                }

                //آلارم غلط بودن یوزر یا پسوورد
                else if (login == false) {
                    alert.login_wrong();
                    txtfield_Password.setText("");
                }
                //مشکل و ارور در لاگین
            }catch(Exception e) {
                alert.login_error();

            }
        }
    }
    //گرفتن آی دی برای استفاده در کلاسهای دیگر
    public static String get_id(){
        System.out.println("returned id ="+ id);
        return id;
    }
    public static void set_id(String ID){
        id = ID;
    }

}
