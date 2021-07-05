package controllers.user;

import com.jfoenix.controls.JFXListView;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Book;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class BookListPageCtrl implements Initializable {
    static String id;

    @FXML
    private VBox pnItems_booklist = null;

    @FXML
    private Button btn_Signout;

    @FXML
    private Button btn_info;

    @FXML
    private Button btn_BookList;

    @FXML
    private Label lbl_fullname;

    @FXML
    private JFXListView<?> listView_Booklist;


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
        try {
            createBookList();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btn_Home_clicked(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("Home");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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
    public void btn_BookList_clicked(ActionEvent actionEvent) throws ClassNotFoundException {

        Stage stage = (Stage) btn_BookList.getScene().getWindow();
        switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
        try {
            switchSenceCtrl.sceneSwitchUserPage("BookList");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        createBookList();
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

    public void showbooks(List<Book> books) throws IOException {
        System.out.println("book    "+ books);
        pnItems_booklist.getChildren().clear();
        Node[] nodes = new Node[1000];
        int i = 0;
        if(books != null){
            for(Book book1 : books){
                int j = i;
                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../../view/fxmls/user/item2.fxml"));
                Parent root = loader1.load();
                Controlleritem2 bookitems = loader1.getController();
                bookitems.set_items(book1);
                nodes[i] = root;
               System.out.println("bookname = "+book1.getKtbName());

                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color: #0a0e3f ");
                });
                nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #02030A");
                    });

                pnItems_booklist.getChildren().add(nodes[i]);
                i++;
                System.out.println(i);
            }
        }
    }


//
    public void createBookList() throws ClassNotFoundException {

        try {
            //اتصال به دیتابیس
            Database.makeConnection();
            //ساختن تیبل مورد نیاز در دیتابیس
            Database.create_book_table();
            String mysql = "SELECT * FROM book";
            showbooks(Database.create_bookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void btn_search_clicked(ActionEvent actionEvent) {



    }
    //add book
    public void btn_addBoock_clicked(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        addBookPage addBookPage = new addBookPage();
        addBookPage.showAddBookPage(lbl_fullname.getText());
        createBookList();
    }

    public void btn_amanatgiri_clicked(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {

    }

    //search
    //جستجو در صفحه ی بوک لیست بر اساس نام کتاب
    public void btn_search_BkList_clicked(ActionEvent actionEvent) {
//        String bookname = search_Bklist_txtfield.getText();
//
//        try {
//            pnItems_booklist.getChildren().clear();
//            Node[] nodes = new Node[1000];
//
//            Database.makeConnection();
//
//            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where name = "+ "\""+ bookname +"\"";
//            showbooks(Database.create_bookList(mysql));
//
//            Database.getStatement().close();
//            Database.closeConnection();
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }
//
    public void searchwith_showallbooks(ActionEvent actionEvent) throws ClassNotFoundException {
//        searchwith_bookvaziyat_pane.setVisible(false);
//        searchwith_bookid_pane.setVisible(false);
//        searchwith_bookname_pane.setVisible(false);
//        booklists();
    }
//
    public void searchwithbookvaziyat(ActionEvent actionEvent) {
//        searchwith_bookid_pane.setVisible(false);
//        searchwith_bookname_pane.setVisible(false);
//        searchwith_bookvaziyat_pane.setVisible(true);
    }
//
    public void searchwithbookid(ActionEvent actionEvent) {
//        searchwith_bookname_pane.setVisible(false);
//        searchwith_bookvaziyat_pane.setVisible(false);
//        searchwith_bookid_pane.setVisible(true);
    }
//
    public void searchwithbookname(ActionEvent actionEvent) {
//        searchwith_bookvaziyat_pane.setVisible(false);
//        searchwith_bookid_pane.setVisible(false);
//        searchwith_bookname_pane.setVisible(true);
   }
//
    public void search_bookname_btn_action(ActionEvent actionEvent) {
//        String bookname = ktbNametxt_search.getText();
//
//        try {
//            pnItems_booklist.getChildren().clear();
//            Node[] nodes = new Node[1000];
//
//            Database.makeConnection();
//
//            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where name = "+ "\""+ bookname +"\"";
//            showbooks(Database.create_bookList(mysql));
//
//            Database.getStatement().close();
//            Database.closeConnection();
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }
//
    public void search_bookID_btn_actoin(ActionEvent actionEvent) {
//        String bookid = ktbIDtxt_search.getText();
//
//        try {
//            pnItems_booklist.getChildren().clear();
//            Node[] nodes = new Node[1000];
//
//            Database.makeConnection();
//
//            String mysql = "SELECT id ,amantgirande ,  name, writer , date, date_ms , amantdahande , mohlat FROM books where id = "+ "\""+ bookid +"\"";
//            showbooks(Database.create_bookList(mysql));
//
//            Database.getStatement().close();
//            Database.closeConnection();
//        }catch (Exception e){
//            System.out.println(e);
//        }
    }

    public void show_amanatdadeshodebooks(ActionEvent actionEvent) {
    }

    public void show_mojod_books(ActionEvent actionEvent) {

    }
}


