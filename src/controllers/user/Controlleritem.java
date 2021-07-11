package controllers.user;


import controllers.*;
import controllers.login.LoginPageCtrl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Amanat;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controlleritem implements Initializable {

    @FXML
    private Label item_dateAmntgiri;

    @FXML
    private Label item_mohlat;

    @FXML
    private Label item_writername;

    @FXML
    private Label item_namebook;

    @FXML
    private Button btn_tamdid;

    @FXML
    private Button btn_odatBook;

    @FXML
    private HBox itemC;

    @FXML
    private Label item_bookID;

    //ست کردن اطلاعات کتاب درون item(یک Hbox برای نمایش اطلاعات کتاب های امانت گرفته شده توسط کاربر در صفحه ی home)
    public void setitemBook(Book book) throws SQLException, ParseException {
        item_bookID.setText(String.valueOf(book.getKtbID()));
        item_mohlat.setText(String.valueOf("10"));
        item_namebook.setText(book.getKtbName());
        item_writername.setText(book.getKtbNevisande());
        //گرفتن تاریخ امانتگیری و مهلت از طریق متد مربوطه در دیتابیس
        item_dateAmntgiri.setText(Database.getAmanatgiriDate(book.getAmtTarakoneshID()));
        item_mohlat.setText(Database.getMohlatTahvil(book.getAmtTarakoneshID()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //تمدید کتاب مورد نظر
        btn_tamdid.setOnAction(e -> {
            try {
                Database.updateAmanat(Database.getAmntTarakoneshID(item_bookID.getText()) , 1);
                alert.informationAlert("درخواست تمدید ثبت شد");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        //برگرداندن کتاب موردنظر به کتابخانه
        btn_odatBook.setOnAction(e -> {
            try {
                Database.updateBookAmntStatus("" , "" , "موجود" , item_bookID.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Stage stage = (Stage) btn_odatBook.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchUserPage("Home");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
