package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

    public void set_user_info() {
        User user = new User();

        try {
            Database.makeConnection();
            user = Database.set_home_items();
            Database.closeConnection();
            lbl_fullname.setText(user.getFirstName() + " " + user.getLastName());
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        set_user_info();
        //
        //      homeList();
    }

    public void btn_Home_clicked(ActionEvent actionEvent) {
        
//            homeList();
    }

//        public void homeList() {
//            pnItems.getChildren().clear();
//            int i = 0;
//            try {
//                //اتصال به دیتابیس
//                Database.makeConnection();
//                //ساختن تیبل مورد نیاز در دیتابیس
//                Database.create_book_table();
//                String amanatgirande = lbl_fullname.getText();
//                String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where amantgirande = " + "\"" + amanatgirande + "\"";
//                showbooks_homepage(Database.create_bookList(mysql));
//
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//            Database.closeConnection();
//        }
//
//        public void showbooks_homepage(List<Books> books) {
//            pnItems.getChildren().clear();
//            Node[] nodes = new Node[1000];
//            int i = 0;
//            if(books != null) {
//                try {
//                    for (Books book : books) {
//                        final int j = i;
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/item.fxml"));
//                        Parent root = (Parent) loader.load();
//                        Controlleritem bookitem = loader.getController();
//                        bookitem.setitems(book);
//
//                        nodes[i] = root;
//                        //give the items some effect
//                        nodes[i].setOnMouseEntered(event -> {
//                            nodes[j].setStyle("-fx-background-color : #0A0E3F");
//                        });
//                        nodes[i].setOnMouseExited(event -> {
//                            nodes[j].setStyle("-fx-background-color : #02030A");
//                        });
//                        pnItems.getChildren().add(nodes[i]);
//                        i++;
//                        System.out.println(i);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    public void btn_BookList_clicked(ActionEvent actionEvent) throws ClassNotFoundException {
        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("BookList");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        //          booklists();
    }

    public void btn_amanatgiri_clicked(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

    }

    public void btn_info_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_info.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchLogin("UserInfo");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        set_user_info();
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

    public void setlbl_fullname(String a) {
        this.lbl_fullname.setText(a);
    }


    public void btn_search_clicked(ActionEvent actionEvent) {
    }

    public void btn_odatBoock_clicked(ActionEvent actionEvent) {
    }
}
