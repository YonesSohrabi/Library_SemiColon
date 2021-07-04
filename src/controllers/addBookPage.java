package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addBookPage implements Initializable {

    @FXML
    private JFXButton exitBTN;

    @FXML
    private  JFXTextField ehdakonande_txtfield;

    @FXML
    private Label addBookErrLBL;

    @FXML
    private JFXTextField name_writer;

    @FXML
    private JFXTextField nameBook_field;

    @FXML
    private JFXButton addBTN;

public void showAddBookPage(String ehdakonande) throws IOException {
    System.out.println(ehdakonande);
    FXMLLoader loader = new FXMLLoader(addBookPage.class.getResource("../view/fxmls/addBookPage.fxml"));
    Parent root = (Parent) loader.load();
    addBookPage a = loader.getController();
    a.ehdakonande_txtfield.setText(ehdakonande);

    Stage stage = new Stage();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);
    //this.ehdakonande_txtfield.setText(ehdakonande);
    stage.show();
}

    public void initialize(URL location, ResourceBundle resources) {

        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        addBTN.setOnAction(e -> {
            try {
                addBookToDB();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
    }

    public void closeBTN() {
        ((Stage) exitBTN.getScene().getWindow()).close();
       // mngBookCtrl.addBookPage = null;
    }

    private void addBookToDB() throws SQLException, ClassNotFoundException {
        Book book = new Book();
            //اتصال به دیتابیس
            Database.makeConnection();
            Database.getStatement();
            //ساختن تیبل مورد نیاز در دیتابیس
            Database.create_book_table();
            //مشکل(ارور)
            book.setKtbName(nameBook_field.getText());
            book.setKtbNevisande(name_writer.getText());
            book.setKtbEhdaKonandeh(ehdakonande_txtfield.getText());

        if (
                !(nameBook_field.getText().equals("") ||
                        name_writer.getText().equals("") ||
                        ehdakonande_txtfield.getText().equals("")

        )) {
            Database.add_book(book);
            nameBook_field.setText("");
            name_writer.setText("");
            closeBTN();
        } else {
            addBookErrLBL.setVisible(true);
        }
    }
}
