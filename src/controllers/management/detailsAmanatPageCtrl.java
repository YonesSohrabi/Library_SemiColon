package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.DateSC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Amanat;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class detailsAmanatPageCtrl implements Initializable {

    @FXML
    private JFXTextField amtIDTXT;

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXTextField ktbNameTXT;

    @FXML
    private JFXTextField usrIDTXT;

    @FXML
    private JFXTextField usrNameTXT;

    @FXML
    private JFXTextField usrCodeMeliTXT;

    @FXML
    private JFXTextField amtGetDateTXT;

    @FXML
    private JFXTextField amtRtrnDateTXT;

    @FXML
    private Text vazeiatTamdidTXT;

    @FXML
    private Text vazeiatDarkhastTXT;

    @FXML
    private JFXButton radBTN;

    @FXML
    private JFXButton exitBTN;

    @FXML
    private JFXButton pazireshBTN;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        amtIDTXT.setText(itemAmanatCtrl.amtID);//پرکردن فیلد آیدی امانت
        ktbNameTXT.setText(itemAmanatCtrl.ktbName);// پرکردن فیلد نام کتاب

        try {
            getDataAmanat(amtIDTXT.getText());
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        // ایونت مربوط به پذیرش درخواست کاربر و تمدید مهلت کتاب
        pazireshBTN.setOnAction(e -> {

            try {
                amtRtrnDateTXT.setText(DateSC.tamdidMohalat(amtRtrnDateTXT.getText(), 10));// تمدید مهلت
                //  تغییر وضعیت درخواست کاربر - وضعیت 2 یعتی درخواست کاربر توسط مدیریت پذیرش شده
                Database.updateAmanat(amtIDTXT.getText(), amtRtrnDateTXT.getText(),"2");
                getDataAmanat(amtIDTXT.getText());// گرفتن اطلاعات امانت بعد از پذیرش درخواست
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
            pazireshBTN.setVisible(false);
            radBTN.setVisible(false);
        });

        // ایونت مربوط به رد درخواست کاربر برای تمدید مهلت کتاب
        radBTN.setOnAction(e -> {
            try {
                // آپدیت وضعیت درخواست کاربر و امکان تمدید پس از رد درخواست
                Database.updateAmanat(amtIDTXT.getText() , 0);
                getDataAmanat(amtIDTXT.getText());
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }

            // مخفی کردن دکمه پذیرش و رد درخواست پس از هندل شدن ایونت
            pazireshBTN.setVisible(false);
            radBTN.setVisible(false);
        });

        // ایونت خروج از صفحه جزئیات امانت
        exitBTN.setOnAction(e -> {
            closeBTN();
        });
    }

    // متد گرفتن و نمایش اطلاعاتی که در قسمت جزئیات امانت نمایش داده میشه
    private void getDataAmanat(String amtIDTXT) throws SQLException, ParseException {

        Amanat amanat = Database.getItemAmanatDB(amtIDTXT);
        ktbIDTXT.setText(amanat.getKtbID());
        usrIDTXT.setText(amanat.getUsrID());
        amtGetDateTXT.setText(amanat.getAmtDateGet());
        amtRtrnDateTXT.setText(amanat.getAmtDateRtrn());
        // نمایش متن مربوط به امکان تمدید کتاب در قسمت وضعیت تمدید
        if (amanat.getAmtEmkanTamdid().equals("1")) {
            vazeiatTamdidTXT.setText("امکان تمدید امانت وجود دارد.");
        } else {
            vazeiatTamdidTXT.setText("دیگر امکان تمدید مهلت این امانت وجود ندارد.");
            vazeiatTamdidTXT.setFill(Color.valueOf("#bf1a1a"));
        }

        // نمایش متن مربوط به وضعیت درخواست بر اساس وضعیت امانت
        if (amanat.getAmtDarkhastUsr().equals("0")) {
            vazeiatDarkhastTXT.setText("کاربر فعلا درخواستی مبنی بر تمدید نداشته است .");
        } else if (amanat.getAmtDarkhastUsr().equals("1")) {
            vazeiatDarkhastTXT.setText("کاربر در خواست تمدید مهلت را دارد.");
            pazireshBTN.setVisible(true);
            radBTN.setVisible(true);
        } else if (amanat.getAmtDarkhastUsr().equals("2")) {
            vazeiatDarkhastTXT.setText("مهلت کتاب به مدت 10 روز دیگر با موافقت مدیریت برای کاربر تمدید شد.");
        } else if (amanat.getAmtDarkhastUsr().equals("3")) {
            vazeiatDarkhastTXT.setText("درخواست تمدید مهلت توسط مدیریت برای این کتاب رد شد .");
        } else if (amanat.getAmtDarkhastUsr().equals("عودت")) {
            vazeiatDarkhastTXT.setText("كتاب به كتابخانه عودت داده شده است .");
        }

        User user = Database.getItemUserDB(usrIDTXT.getText());
        usrNameTXT.setText(user.getFirstName() + " " + user.getLastName());
        usrCodeMeliTXT.setText(user.getCodeMeli());

    }
    
    public void closeBTN() {
        ((Stage) exitBTN.getScene().getWindow()).close();
        itemAmanatCtrl.detailsAmanatPage = null;
    }
}
