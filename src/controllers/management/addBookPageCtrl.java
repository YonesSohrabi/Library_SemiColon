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
    private JFXButton exitBTN;

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXButton addBTN;

    @FXML
    private JFXTextField ktbNevisandehTXT;

    @FXML
    private JFXTextField ktbNameTXT;

    @FXML
    private JFXTextField ktbEhdakonandehTXT;

    @FXML
    private JFXTextField ktbTedadTXT;

    @FXML
    private Label addBookErrLBL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ktbIDTXT.setText(String.valueOf(bookIdRandom()));

        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        addBTN.setOnAction(e -> {
            try {
                addBookToDB();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void closeBTN() {
        ((Stage) exitBTN.getScene().getWindow()).close();
        mngBookCtrl.addBookPage = null;
    }

    private int bookIdRandom() {
        Random rand = new Random();
        int bookID = rand.nextInt((9999 - 1000) + 1) + 1000;
        return bookID;
    }

    private void addBookToDB() throws SQLException {
        Book book = new Book();
        book.setKtbID(ktbIDTXT.getText());
        book.setKtbName(ktbNameTXT.getText());
        book.setKtbNevisande(ktbNevisandehTXT.getText());
        book.setKtbEhdaKonandeh(ktbEhdakonandehTXT.getText());
        book.setKtbTedad(ktbTedadTXT.getText());
        book.setKtbVazeit("موجود");
        if (
                !(ktbNameTXT.getText().equals("") ||
                        ktbNevisandehTXT.getText().equals("") ||
                        ktbEhdakonandehTXT.getText().equals("") ||
                        ktbTedadTXT.getText().equals(""))
        ) {
            Database.createBook(book);
            closeBTN();
        } else {
            addBookErrLBL.setVisible(true);
        }
    }
}
