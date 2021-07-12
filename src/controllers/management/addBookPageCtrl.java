package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Book;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class addBookPageCtrl implements Initializable {

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXTextField ktbNevisandehTXT;

    @FXML
    private JFXTextField ktbNameTXT;

    @FXML
    private JFXTextField ktbEhdakonandehTXT;

    @FXML
    private JFXTextField ktbTedadTXT;

    @FXML
    private JFXButton addBTN;

    @FXML
    private JFXButton exitBTN;

    @FXML
    private Label addBookErrLBL;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ktbIDTXT.setText(String.valueOf(bookIdRandom()));// انتخاب آیدی رندم برای کتابی که قراره به کتابخونه اضافه شه

        // بستن صفحه اضافه کردن کتاب
        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        // ایونت اضافه کردن کتاب
        addBTN.setOnAction(e -> {
            try {
                addBookToDB();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    // متد مربوط به ایونت بستن صفحه اضافه کردن کتاب
    public void closeBTN() {
        ((Stage) exitBTN.getScene().getWindow()).close();
        mngBookCtrl.addBookPage = null;
    }

    // متد انتخاب یک عدد رندم 4 رقمی برای آیدی کتاب
    private int bookIdRandom() {
        Random rand = new Random();
        int bookID = rand.nextInt((9999 - 1000) + 1) + 1000;
        return bookID;
    }

    // متد مربوط به ایونت اضافه کردن کتاب به کتاب خانه | ساختن یک شی از کتاب و پرکردن مقادیر آن با مقادیری تکست فیلدا
    private void addBookToDB() throws SQLException {
        Book book = new Book();
        book.setKtbID(ktbIDTXT.getText());
        book.setKtbName(ktbNameTXT.getText());
        book.setKtbNevisande(ktbNevisandehTXT.getText());
        book.setKtbEhdaKonandeh(ktbEhdakonandehTXT.getText());
        book.setKtbTedad(ktbTedadTXT.getText());
        book.setKtbVazeit("موجود");

        // چک کردن اینکه تکست فیلدا خالی نباشن
        if (
                !(ktbNameTXT.getText().equals("") ||
                        ktbNevisandehTXT.getText().equals("") ||
                        ktbTedadTXT.getText().equals(""))
        ) {
            Database.createBook(book);// ذخیره اطلاعات در دیتابیس
            closeBTN();
        } else {
            addBookErrLBL.setVisible(true); // نمایش پیغام خطا در صورتی ک اعتبار سنجی بالا فالس بشه
        }
    }
}
