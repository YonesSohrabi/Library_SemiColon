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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngBookCtrl extends mngStage implements Initializable {



    @FXML
    private ToggleGroup jostejoRadio;

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
    private ToggleGroup vazeiatJostejo;

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

        dashboardBTN.setOnAction(e -> {
            Stage stage = (Stage) dashboardBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("dashboard");
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
            stage.close();
        });

        addBookbtn.setOnAction(e -> {
            try {
                openAddBookPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

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

    private void openAddBookPage() throws IOException {
        if (addBookPage == null) {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../../view/fxmls/management/addBookPage.fxml"));
            addBookPage = new Stage();
            addBookPage.setScene(new Scene(root));
            addBookPage.show();
        }
    }

    private void hidePaneJostejo() {
        ktbNamePane.setVisible(false);
        ktbNumPane.setVisible(false);
        ktbVazeiatPane.setVisible(false);
    }

    public void searchBook() throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB());
        showBook(books);
    }

    public void searchBook(String bookName, String lable) throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB(bookName, lable));
        showBook(books);
    }

    public void searchBook(int vazeiat) throws SQLException, IOException {
        List<Book> books = new ArrayList<>(Database.readBooksDB(vazeiat));
        showBook(books);
    }

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

