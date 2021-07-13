package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngBookCtrl extends mngStage implements Initializable {


    @FXML
    private GridPane itemBookList;


    @FXML
    private JFXRadioButton nameBookRB;

    @FXML
    private JFXRadioButton numBookRB;

    @FXML
    private JFXRadioButton vazeiatBookRB;

    @FXML
    private JFXRadioButton allBookRB;

    @FXML
    private Pane ktbNamePane;

    @FXML
    private JFXTextField ktbNametxt;

    @FXML
    private JFXButton ktbNameSearchbtn;

    @FXML
    private Pane ktbNumPane;

    @FXML
    private JFXTextField ktbNumtxt;

    @FXML
    private JFXButton ktbNumSearchbtn;

    @FXML
    private Pane ktbVazeiatPane;


    @FXML
    private JFXButton addBookbtn;

    @FXML
    private JFXRadioButton mojodBTN;

    @FXML
    private JFXRadioButton naMojodBTN;

    static Stage addBookPage = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            searchBook();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        /* شروع ایونت های مربوط به قسمت دکمه های منو برنامه
        *
        *
        به ترتیب
        داشبورد - کتابدار - امانت - گزارش - تنطیمات - خروج
        *
         */
        dashboardBTN.setOnAction(e -> {
            Stage stage = (Stage) dashboardBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("dashboard");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        ketabdarBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("ketabdar");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        faliatBTN.setOnAction(e ->{
            Stage stage = (Stage) faliatBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("amanat");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gozareshBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        settingBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exitBTN.setOnAction(e -> {
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        /* پایان ایونت های منو
        *
        */

        // ایونت اضافه کردن کتاب به کتابخانه
        addBookbtn.setOnAction(e -> {
            try {
                openAddBookPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // دو تا ایونت مربوط به سرچ بر اساس نام کتاب
        nameBookRB.setOnAction(e -> {
            if (nameBookRB.isSelected()) {
                hidePaneJostejo();
                ktbNamePane.setVisible(true);
            }
        });
        ktbNameSearchbtn.setOnAction(e -> {
            try {
                searchBook(ktbNametxt.getText(), "name");
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // دو تا ایونت مربوط به سرچ کتاب بر اساس شماره کتاب
        numBookRB.setOnAction(e -> {
            if (numBookRB.isSelected()) {
                hidePaneJostejo();
                ktbNumPane.setVisible(true);
            }
        });


        ktbNumSearchbtn.setOnAction(e -> {
            try {
                searchBook(ktbNumtxt.getText(), "number");
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // سه تا ایونت مربوط به سرچ بر اساس وضعیت کتاب ، به عنوان موجود و ناموجود
        vazeiatBookRB.setOnAction(e -> {
            if (vazeiatBookRB.isSelected()) {
                hidePaneJostejo();
                ktbVazeiatPane.setVisible(true);
            }
        });

        mojodBTN.setOnAction(e -> {
            if (mojodBTN.isSelected()) {
                try {
                    searchBook(1);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        naMojodBTN.setOnAction(e -> {
            if (naMojodBTN.isSelected()) {
                try {
                    searchBook(0);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // ایونت مربوط به دکمه نمایش همه کتاب ها
        allBookRB.setOnAction(e -> {
            if (allBookRB.isSelected()) {
                hidePaneJostejo();
                try {
                    searchBook();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }


    // متد مربوط به باز کردن صفحه اضافه کردن کتاب
    private void openAddBookPage() throws IOException {
        if (addBookPage == null) {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../../view/fxmls/management/addBookPage.fxml"));
            addBookPage = new Stage();
            addBookPage.initStyle(StageStyle.UNDECORATED);
            addBookPage.setScene(new Scene(root));
            addBookPage.show();
        }
    }

    // متد مخفی کردن پین های جستجو
    private void hidePaneJostejo() {
        ktbNamePane.setVisible(false);
        ktbNumPane.setVisible(false);
        ktbVazeiatPane.setVisible(false);
    }

    // متد سرچ همه کتاب ها از دیتابیس
    public void searchBook() throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB());
        showBook(books);
    }

    // متد سرچ براساس شماره کتاب یا نام کتاب
    public void searchBook(String bookName, String lable) throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB(bookName, lable));
        showBook(books);
    }

    // متد سرچ براساس وضعیت موجود بودن یا ناموجود بودن کتاب
    public void searchBook(int vazeiat) throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB(vazeiat));
        showBook(books);
    }

    // متدی که کتاب های جستجو شده رو نمایش میده
    public void showBook(List<Book> books) throws IOException {
        itemBookList.getChildren().clear();
        int rows = 0;
        for (Book book : books) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/fxmls/management/itemBook.fxml"));
            HBox bookBox = fxmlLoader.load();
            itemBookCtrl itemBookCtrl = fxmlLoader.getController();
            itemBookCtrl.setItemBook(book);
            itemBookList.addRow(rows++, bookBox);
            GridPane.setMargin(bookBox, new Insets(5, 0, 5, 10));

        }
    }




}

