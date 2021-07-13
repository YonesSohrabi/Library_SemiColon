package controllers.management;

import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class itemBookCtrl implements Initializable {

    @FXML
    private Label bookNumLBL;

    @FXML
    private Label bookNameLBL;

    @FXML
    private Label bookAuthorLBL;

    @FXML
    private Label bookStatusLBL;

    @FXML
    private Button editBTN;

    @FXML
    private Button deleteBTN;

    static Stage editBookPage = null;
    static String bookID = null;

    // متد پرکردن مقادیر آیتم کتاب
    public void setItemBook(Book book) {
        bookNumLBL.setText(book.getKtbID());
        bookNameLBL.setText(book.getKtbName());
        bookAuthorLBL.setText(book.getKtbNevisande());
        bookStatusLBL.setText(book.getKtbVazeit());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ایونت مربوط به آیکون باز کردن صفحه ادیت کتاب
        editBTN.setOnAction(e -> {
            try {
                bookID = bookNumLBL.getText();
                openEditBookPage();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت مربوط به آیکون حذف کتاب
        deleteBTN.setOnAction(e -> {
            System.out.println(bookNameLBL.getText());
            try {
                Database.deleteBook(bookNumLBL.getText());// حذف کتاب از دیتابیس
                // رفرش صفحه پس از حذف کتاب
                Stage stage = (Stage) deleteBTN.getScene().getWindow();
                switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
                switchSenceCtrl.sceneSwitchManagement("book");
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });

    }


    // متد مربوط به ایونت باز کردن صفحه ادیت کتاب
    private void openEditBookPage() throws IOException {
        if (editBookPage == null) {
            Pane root = FXMLLoader.load(getClass().getResource("../../view/fxmls/management/editBookPage.fxml"));
            editBookPage = new Stage();
            editBookPage.initStyle(StageStyle.UNDECORATED);
            editBookPage.setScene(new Scene(root));
            editBookPage.show();

        }
    }
}

