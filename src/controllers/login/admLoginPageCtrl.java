package controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class admLoginPageCtrl implements Initializable {

    @FXML
    private JFXTextField usrNameAdmTXT;

    @FXML
    private JFXPasswordField passAdmTXT;

    @FXML
    private JFXButton loginAdmBTN;

    @FXML
    private JFXButton exitBTN;

    @FXML
    private JFXButton backAdmBTN;

    @FXML
    private Label admLoginErrLBL;


    public static String ID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ایونت لاگین برای صفحه لاگین مدیریت
        loginAdmBTN.setOnAction(e -> {
            try {
                if (!(usrNameAdmTXT.getText().equals("") || passAdmTXT.getText().equals(""))){// چک کردن خالی نبودن فیلد ها
                    if(checkInfoAdmin(usrNameAdmTXT.getText(),passAdmTXT.getText())){// چک کردن یوزر نیم و پسورد مدیریت
                        enterToAdminPanel();// ورود به صفحه مدیریت
                    }else {
                        admLoginErrLBL.setText("Wrong username or password");
                        admLoginErrLBL.setVisible(true);
                    }
                }else {
                    admLoginErrLBL.setText("Please fill in the fields");
                    admLoginErrLBL.setVisible(true);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        // ایونت برگشت به صفحه لاگین کاربر
        backAdmBTN.setOnAction(e -> {
            Stage stage = (Stage) backAdmBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت بستن نرم افزار
        exitBTN.setOnAction(e ->{
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            stage.close();
        });


    }

    // متدی برای ورود به صفحه مدیریت کتابخانه
    private void enterToAdminPanel() {
        Stage stage = (Stage) loginAdmBTN.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchManagement("dashboard");// ورود به صفحه مدیریت - داشبورد
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // متد چک کردن اطلاعات وارد شده با اطلاعات موجود در جدول ادمین در دیتابیس
    private boolean checkInfoAdmin(String userName,String password) throws SQLException {
        List<Admin> admins = Database.getInfoAdmin();// خواندن اطلاعات مدیران از دیتابیس
        boolean isCorrect = false;
        for (Admin adm:admins){
            if (adm.getUserName().equals(userName)){
                if (adm.getPassword().equals(password)){
                    isCorrect = true;
                    ID = adm.getID();
                    break;
                }
            }
        }
        return isCorrect;
    }
}
