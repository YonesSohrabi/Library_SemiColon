package controllers.user;


import controllers.Database;
import controllers.DateSC;
import controllers.Roozh;
import controllers.login.LoginPageCtrl;
import controllers.switchSenceCtrl;
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
    ///////////////////////////////////////////////
    //item

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

    static String bookID = null;

    public void setitemBook(Book book) throws SQLException, ParseException {
        item_bookID.setText(String.valueOf(book.getKtbID()));
        item_mohlat.setText(String.valueOf("10"));
        item_namebook.setText(book.getKtbName());
        item_writername.setText(book.getKtbNevisande());
        item_dateAmntgiri.setText(Database.getAmanatgiriDate(book.getAmtTarakoneshID()));
        item_mohlat.setText(Database.getMohlatTahvil(book.getAmtTarakoneshID()));
    }
    public void setItemsAmnt(Amanat amnt){
        item_dateAmntgiri.setText(amnt.getAmtDateGet());
        item_mohlat.setText(amnt.getAmtDateRtrn());
    }


    public void setname(String a){
        item_namebook.setText(a);
    }
    public void setwriter(String a){
        item_writername.setText(a);
    }
    public void setdate(String a){
        item_dateAmntgiri.setText(a);
    }
    public void setmohlat(String a){
        item_mohlat.setText(a);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_tamdid.setOnAction(e -> {
            try {
                Database.updateAmanat(Database.getAmntTarakoneshID(item_bookID.getText()) , 1);
                btn_tamdid.setVisible(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
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

    public void set_bookID(String a) {
        item_bookID.setText(a);
    }
}
