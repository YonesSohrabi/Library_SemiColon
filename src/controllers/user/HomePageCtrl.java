package controllers.user;

import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import model.Book;
import model.User;

public class HomePageCtrl implements Initializable {
    static String id;

    @FXML
    public VBox pnItems = null;

    @FXML
    private VBox pnItems_booklist = null;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_Signout;

    @FXML
    private Button btn_info;

    @FXML
    private Button btn_BookList;

    @FXML
    private Label lbl_fullname;


    final ObservableList<String> bookInfo = FXCollections.observableArrayList();

    public void setInfo() {
        User user = new User();

        try {
            Database.makeConnection();
            user = Database.set_home_items();
            Database.closeConnection();
            lbl_fullname.setText(user.getFirstName() + " " + user.getLastName());
        } catch (SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfo();
        createhomeList();
    }

    public void btn_Home_clicked(ActionEvent actionEvent) {
            createhomeList();
    }

        public void createhomeList() {
            pnItems.getChildren().clear();
            int i = 0;
            try {
                //اتصال به دیتابیس
                Database.makeConnection();
                //ساختن تیبل مورد نیاز در دیتابیس
                Database.create_book_table();
                String amanatgirande = lbl_fullname.getText();

                /***replace ehdakonande with ktbAmtID later
                 *
                 */

                String mysql = "SELECT * FROM book where ktbEhdaKonande = " + "\"" + amanatgirande + "\"";
                showbooks_homepage(Database.create_bookList(mysql));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Database.closeConnection();
        }

        public void showbooks_homepage(List<Book> books) {
            pnItems.getChildren().clear();
            Node[] nodes = new Node[1000];
            int i = 0;
            if(books != null) {
                try {
                    for (Book book : books) {
                        final int j = i;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/fxmls/user/Item.fxml"));
                        Parent root = (Parent) loader.load();
                        Controlleritem bookitem = loader.getController();
                        bookitem.setitems(book);

                        nodes[i] = root;
                        //give the items some effect
                        nodes[i].setOnMouseEntered(event -> {
                            nodes[j].setStyle("-fx-background-color : #0A0E3F");
                        });
                        nodes[i].setOnMouseExited(event -> {
                            nodes[j].setStyle("-fx-background-color : #02030A");
                        });
                        pnItems.getChildren().add(nodes[i]);
                        i++;
                        System.out.println(i);
                    }
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }

    public void btn_BookList_clicked(ActionEvent actionEvent) throws ClassNotFoundException {
        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("BookList");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void btn_amanatgiri_clicked(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

    }

    public void btn_info_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_info.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("UserInfo");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void btn_Signout_clicked(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) btn_Signout.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchLogin("loginPage");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void btn_search_clicked(ActionEvent actionEvent) {
    }

    public void btn_odatBoock_clicked(ActionEvent actionEvent) {
    }
}
