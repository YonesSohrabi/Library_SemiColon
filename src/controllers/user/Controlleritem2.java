package controllers.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Book;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlleritem2 implements Initializable {

    @FXML
    private Label item2_namebook;

    @FXML
    private Label item2_vaziyat;

    @FXML
    private Label item2_writername;

    @FXML
    private Label item2_ehdakonande;

    @FXML
    private Label item2_bookID;

    @FXML
    private HBox itemC;

    public void set_items(Book book){
        this.item2_namebook.setText(book.getKtbName());
        this.item2_writername.setText(book.getKtbNevisande());
        this.item2_ehdakonande.setText(book.getKtbEhdaKonandeh());
        this.item2_bookID.setText(String.valueOf(book.getKtbID()));
        /** add vaziyad later
         *
         */
        this.item2_vaziyat.setText("موجود");
    }


    public void set_bookname(String a){
        item2_namebook.setText(a);
    }

    public void set_bookwriter(String a){
        item2_writername.setText(a);
    }

    public void set_amanatdahande(String a){
        item2_ehdakonande.setText(a);
    }

    public void set_vaziyat(String a){
        item2_vaziyat.setText(a);
    }

    public void set_bookID(String a) {
        item2_bookID.setText(a);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
