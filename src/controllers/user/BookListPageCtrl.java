package controllers.user;

import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
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
    private Pane searchwith_bookname_pane;

    @FXML
    private JFXTextField ktbNametxt_search;

    @FXML
    private Pane searchwith_bookid_pane;

    @FXML
    private JFXTextField ktbIDtxt_search;

    @FXML
    private Pane searchwith_bookvaziyat_pane;

    @FXML
    private JFXTextField ktbwritertxt_search;

    @FXML
    private Pane searchwith_bookwriter_pane;

    // ست کردن اطلاعات صفحه ی booklist مطابق با اطلاعات کاربر وارد شده
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
    //اکشن های هنگام باز شدن صفحه
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controlleritem2.setUserName(lbl_fullname.getText());
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
    //متد جهت نمایش کتابهای کتابخانه در pnItem_bookList از طریق item2
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
                //اتصال به کنترلر آیتم2 جهت ست کردن مقادیر اف ایکس ام ال item با مقادیر موجود در book
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
                //اضافه کردن item ایجاد و ست شده به pnItem_bookList در صفحه ی booklist
                pnItems_booklist.getChildren().add(nodes[i]);
                i++;
                System.out.println(i);
            }
        }
    }
    //ساختن لیست کتابهای موجود در کتابخانه
    public void createBookList() throws ClassNotFoundException {
        try {
            //اتصال به دیتابیس
            Database.makeConnection();
            //ساختن تیبل مورد نیاز در دیتابیس
            Database.create_book_table();
            String mysql = "SELECT * FROM book";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //add book
    public void btn_addBoock_clicked(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        addBookPage addBookPage = new addBookPage();
        addBookPage.showAddBookPage(lbl_fullname.getText());
        createBookList();
    }
//متد های مربوط به سرچ کتاب در صفحه ی لیست کتابا
    //نمایش همه ی کتابها
    public void searchwith_showallbooks(ActionEvent actionEvent) throws ClassNotFoundException {
        searchwith_bookwriter_pane.setVisible(false);
        searchwith_bookvaziyat_pane.setVisible(false);
        searchwith_bookid_pane.setVisible(false);
        searchwith_bookname_pane.setVisible(false);
        createBookList();
    }
    //نمایش فیلدهای مربوط به جستجو براساس وضعیت کتابها
    public void searchwithbookvaziyat(ActionEvent actionEvent) {
        searchwith_bookwriter_pane.setVisible(false);
        searchwith_bookid_pane.setVisible(false);
        searchwith_bookname_pane.setVisible(false);
        searchwith_bookvaziyat_pane.setVisible(true);
    }
    //نمایش فیلدهای مربوط به جستجو براساس آی دی کتاب
    public void searchwithbookid(ActionEvent actionEvent) {
        searchwith_bookwriter_pane.setVisible(false);
        searchwith_bookname_pane.setVisible(false);
        searchwith_bookvaziyat_pane.setVisible(false);
        searchwith_bookid_pane.setVisible(true);
    }
    //نمایش فیلدهای مربوط به جستجو براساس نام کتاب
    public void searchwithbookname(ActionEvent actionEvent) {
        searchwith_bookwriter_pane.setVisible(false);
        searchwith_bookvaziyat_pane.setVisible(false);
        searchwith_bookid_pane.setVisible(false);
        searchwith_bookname_pane.setVisible(true);
   }
    //نمایش فیلدهای مربوط به جستجو براساس نویسنده ی کتاب
    public void searchwithbookwriter(ActionEvent actionEvent) {
        searchwith_bookvaziyat_pane.setVisible(false);
        searchwith_bookid_pane.setVisible(false);
        searchwith_bookname_pane.setVisible(false);
        searchwith_bookwriter_pane.setVisible(true);
    }
    //جستجو براساس نام کتاب
    public void search_bookname_btn_action(ActionEvent actionEvent) {
        String bookname = ktbNametxt_search.getText();
        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];
            Database.makeConnection();
            String mysql = "SELECT * FROM book where ktbName = "+ "\""+ bookname +"\"";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //جستجو براساس آی دی کتاب
    public void search_bookID_btn_actoin(ActionEvent actionEvent) {
        String bookid = ktbIDtxt_search.getText();
        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];
            Database.makeConnection();
            String mysql = "SELECT * FROM book where ktbID = "+ "\""+ bookid +"\"";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //نمایش کتابهای امانت داده شده
    public void show_amanatdadeshodebooks(ActionEvent actionEvent) {
        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];
            Database.makeConnection();
            String mysql = "SELECT * FROM book where ktbVazeiat = \"نا موجود\" ";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //نمایش کتابهای موجود در کتابخانه(کسی امانت نبرده)
    public void show_mojod_books(ActionEvent actionEvent) {
        try {
            pnItems_booklist.getChildren().clear();
            Node[] nodes = new Node[1000];
            Database.makeConnection();
            String mysql = "SELECT * FROM book where ktbVazeiat = \"موجود\" ";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //جستجو براساس نویسنده ی کتاب
    public void search_writer_btn_action(ActionEvent actionEvent) {
        String bookwriter = ktbwritertxt_search.getText();
        try {
            pnItems_booklist.getChildren().clear();

            Node[] nodes = new Node[1000];
            Database.makeConnection();
            String mysql = "SELECT * FROM book where ktbNevisandeh = "+ "\""+ bookwriter +"\"";
            showbooks(Database.createBookList(mysql));
            Database.getStatement().close();
            Database.closeConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}


