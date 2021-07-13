package controllers.management;



import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngDashboardCtrl extends mngStage implements Initializable{

    @FXML
    private Label numKolAmanatLBL;

    @FXML
    private Label numAmanatFeliLBL;

    @FXML
    private Label numHameKetabhaLBL;

    @FXML
    private Label numKarbaranLBL;

    @FXML
    private Label odat1;

    @FXML
    private Label odat2;

    @FXML
    private Label odat3;

    @FXML
    private Label odat4;

    @FXML
    private Label amanat1;

    @FXML
    private Label amanat2;

    @FXML
    private Label amanat3;

    @FXML
    private Label amanat4;

    @FXML
    private HBox amtHBox1;

    @FXML
    private HBox amtHBox2;

    @FXML
    private HBox amtHBox3;

    @FXML
    private HBox amtHBox4;

    @FXML
    private HBox odatHBox1;

    @FXML
    private HBox odatHBox2;

    @FXML
    private HBox odatHBox3;

    @FXML
    private HBox odatHBox4;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // لیست 4 کتابی اخیر هم عودت داده شده هم امانت برده شده
        List amtRecentList = null ,odatRecentList = null;
        try {
            amtRecentList = new ArrayList(Database.getAmanatRecent("امانت"));
            odatRecentList = new ArrayList(Database.getAmanatRecent("عودت"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // نمایش اطلاعاتی از 4 کتاب اخیری که عودت داده شده برا اساس تعدادشون
        switch (odatRecentList.size()){
            case 4:
                odatHBox1.setVisible(true);
                odatHBox2.setVisible(true);
                odatHBox3.setVisible(true);
                odatHBox4.setVisible(true);
                odat1.setText(String.valueOf(odatRecentList.get(0)));
                odat2.setText(String.valueOf(odatRecentList.get(1)));
                odat3.setText(String.valueOf(odatRecentList.get(2)));
                odat4.setText(String.valueOf(odatRecentList.get(3)));
                break;
            case 3 :
                odatHBox1.setVisible(true);
                odatHBox2.setVisible(true);
                odatHBox3.setVisible(true);
                odat1.setText(String.valueOf(odatRecentList.get(0)));
                odat2.setText(String.valueOf(odatRecentList.get(1)));
                odat3.setText(String.valueOf(odatRecentList.get(2)));
                break;
            case 2 :
                odatHBox1.setVisible(true);
                odatHBox2.setVisible(true);
                odat1.setText(String.valueOf(odatRecentList.get(0)));
                odat2.setText(String.valueOf(odatRecentList.get(1)));
                break;
            case 1 :
                odatHBox1.setVisible(true);
                odat1.setText(String.valueOf(odatRecentList.get(0)));
                break;
            case 0 :
                odatHBox1.setVisible(true);
                odat1.setText("موردی برای نمایش وجود ندارد.");
                break;

        }

        // نمایش اطلاعاتی از 4 کتاب اخیری که امانت داده شده برا اساس تعدادشون
        switch (amtRecentList.size()){
            case 4:
                amtHBox1.setVisible(true);
                amtHBox2.setVisible(true);
                amtHBox3.setVisible(true);
                amtHBox4.setVisible(true);
                amanat1.setText(String.valueOf(amtRecentList.get(0)));
                amanat2.setText(String.valueOf(amtRecentList.get(1)));
                amanat3.setText(String.valueOf(amtRecentList.get(2)));
                amanat4.setText(String.valueOf(amtRecentList.get(3)));
                break;
            case 3 :
                amtHBox1.setVisible(true);
                amtHBox2.setVisible(true);
                amtHBox3.setVisible(true);
                amanat1.setText(String.valueOf(amtRecentList.get(0)));
                amanat2.setText(String.valueOf(amtRecentList.get(1)));
                amanat3.setText(String.valueOf(amtRecentList.get(2)));
                break;
            case 2 :
                amtHBox1.setVisible(true);
                amtHBox2.setVisible(true);
                amanat1.setText(String.valueOf(amtRecentList.get(0)));
                amanat2.setText(String.valueOf(amtRecentList.get(1)));
                break;
            case 1 :
                amtHBox1.setVisible(true);
                amanat1.setText(String.valueOf(amtRecentList.get(0)));
                break;
            case 0 :
                amtHBox1.setVisible(true);
                amanat1.setText("موردی برای نمایش وجود ندارد .");
                break;

        }

        //خواندن بعضی اطلاعات از و نمایش آمار آن در فیلد مربوطه در صفحه داشبورد
        try {
            numKarbaranLBL.setText(counterNum("user","user"));// تعداد کاربران
            numHameKetabhaLBL.setText(counterNum("book","book"));// تعداد کتاب ها
            numAmanatFeliLBL.setText(counterNum("book","ktbVazeiat","ناموجود",
                    "status","1"));// تعداد کتاب های امانت برده شده ای ک الان برگشت داده نشده
            numKolAmanatLBL.setText(counterNum("amanat","amanat"));// تعداد همه امانت ها
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* شروع ایونت های مربوط به قسمت دکمه های منو برنامه
        *
        *
        به ترتیب
        کتاب ها - کتابدار - امانت - گزارش - تنطیمات - خروج
        *
         */
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

        gozareshBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        settingBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
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

    private String counterNum(String tableName,String lable) throws SQLException {
        return Database.counter(tableName,lable);
    }
    private String counterNum(String tableName,String feildName,String feildValue,String status,String valueStatus) throws SQLException {
        return Database.counter(tableName,feildName,feildValue,status,valueStatus);
    }

}
