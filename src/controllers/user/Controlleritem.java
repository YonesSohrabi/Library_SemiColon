package controllers.user;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Book;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlleritem implements Initializable {
    ///////////////////////////////////////////////
    //item
    @FXML
    private Button btn_odat;

    @FXML
    private Label item_date;

    @FXML
    private Label item_mohlat;

    @FXML
    private Label item_writername;

    @FXML
    private Label item_namebook;

    @FXML
    private HBox itemC;

    @FXML
    private Label item_bookID;

    public void setitems(Book book){
        item_bookID.setText(String.valueOf(book.getKtbID()));
        item_date.setText("15/4/1400");
        item_mohlat.setText(String.valueOf("10"));
        item_namebook.setText(book.getKtbName());
        item_writername.setText(book.getKtbNevisande());
    }


    public void setname(String a){
        item_namebook.setText(a);
    }
    public void setwriter(String a){
        item_writername.setText(a);
    }
    public void setdate(String a){
        item_date.setText(a);
    }
    public void setmohlat(String a){
        item_mohlat.setText(a);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void set_bookID(String a) {
        item_bookID.setText(a);
    }
}
