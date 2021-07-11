package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.Book;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class editBookPageCtrl implements Initializable {
    @FXML
    private JFXButton exitBTN;

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXButton editBTN;

    @FXML
    private JFXTextField ktbNevisandehTXT;

    @FXML
    private JFXTextField ktbNameTXT;

    @FXML
    private JFXTextField ktbEhdakonandehTXT;

    @FXML
    private JFXTextField ktbTedadTXT;

    @FXML
    private JFXTextField amtIDTXT;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ktbIDTXT.setText(itemBookCtrl.bookID);
        try {
            getDataBook(ktbIDTXT.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        editBTN.setOnAction(e -> {
            try {
                updateBookinDB(ktbIDTXT.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            closeBTN();
        });
    }

    private void getDataBook(String ktbIDTXT) throws SQLException {
        Book book = Database.getItemBookDB(ktbIDTXT);
        ktbNameTXT.setText(book.getKtbName());
        ktbEhdakonandehTXT.setText(book.getKtbEhdaKonandeh());
        ktbNevisandehTXT.setText(book.getKtbNevisande());
        ktbTedadTXT.setText(book.getKtbTedad());
        amtIDTXT.setText(book.getAmtTarakoneshID());
    }

    private void updateBookinDB(String ktbIDTXT) throws SQLException {
        Book book = Database.getItemBookDB(ktbIDTXT);
        book.setKtbName(ktbNameTXT.getText());
        book.setKtbTedad(ktbTedadTXT.getText());
        book.setKtbNevisande(ktbNevisandehTXT.getText());
        book.setKtbEhdaKonandeh(ktbEhdakonandehTXT.getText());
        Database.updateBook(book,ktbIDTXT);
    }
    public void closeBTN(){
        ((Stage)exitBTN.getScene().getWindow()).close();
        itemBookCtrl.editBookPage = null;
    }
}
