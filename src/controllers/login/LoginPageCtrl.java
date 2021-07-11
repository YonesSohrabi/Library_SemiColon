package controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.alert;
import controllers.switchSenceCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class  LoginPageCtrl {

    @FXML
    private JFXButton exit_btn;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private Label btn_SignUp;

    @FXML
    private Label openAdmLoginPage;

    @FXML
    private JFXTextField txtfield_UserName;

    @FXML
    private JFXPasswordField txtfield_Password;

    @FXML
    private Label labelalrtLogin;

    //خروج
    @FXML
    void press_Exit_btn(ActionEvent event) {
        Stage stage = (Stage) exit_btn.getScene().getWindow();
        stage.close();
    }
    //باز کردن صفحه ی ورود ادمین
    @FXML
    void openAdmLoginPage(MouseEvent event) {
        Stage stage = (Stage) openAdmLoginPage.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchLogin("admLogin");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    //باز کردن صفحه ی ثبت نام
    public void open_registration(MouseEvent mouseEvent) {
        Stage stage = (Stage) btn_SignUp.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchLogin("register");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    static String id;

    public void press_Login_btn(ActionEvent actionEvent) {
        //چک کردن پر بودن فیلد های مورد نیاز
        boolean login = false;
        if (txtfield_UserName.getText().equals("") || txtfield_Password.getText().equals("")) {
            labelalrtLogin.setText("complate all the fields");
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
                        root = (AnchorPane) FXMLLoader.load(getClass().getResource("../../view/fxmls/user/Home.fxml"));
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
                    labelalrtLogin.setText("username or password is wrong");
                    txtfield_Password.setText("");
                }
                //مشکل و ارور در لاگین
            } catch (Exception e) {
                labelalrtLogin.setText("Login Failed pleaes TryAgain");
            }
        }
    }

    //گرفتن آی دی برای استفاده در کلاسهای دیگر
    public static String get_id() {
        System.out.println("returned id =" + id);
        return id;
    }
    public static void set_id(String ID){
        id = ID;
    }

}
