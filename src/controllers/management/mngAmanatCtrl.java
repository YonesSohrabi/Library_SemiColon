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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Amanat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngAmanatCtrl extends mngStage implements Initializable {

    @FXML
    private GridPane itemAmanatList;

    @FXML
    private JFXRadioButton ktbIDRB;

    @FXML
    private JFXRadioButton usrIDRB;

    @FXML
    private JFXRadioButton dateRB;

    @FXML
    private JFXRadioButton allAmanatRB;

    @FXML
    private Pane ktbIDPane;

    @FXML
    private JFXTextField ktbIDTXT;

    @FXML
    private JFXButton ktbIDSearchBTN;

    @FXML
    private Pane usrIDPane;

    @FXML
    private JFXTextField usrIDTXT;

    @FXML
    private JFXButton usrIDSearchBTN;

    @FXML
    private Pane amtDatePane;

    @FXML
    private JFXRadioButton searchMohlatRB;

    @FXML
    private JFXRadioButton searchTwoDateRB;

    @FXML
    private Pane searchMohlatPane;

    @FXML
    private JFXTextField amtMohlatTXT;

    @FXML
    private JFXButton amtMohlatSearchBTN;

    @FXML
    private Pane searchDatePane;

    @FXML
    private JFXTextField amtAzDateTXT;

    @FXML
    private JFXButton amtDateSearchBTN;

    @FXML
    private JFXTextField amtTaDateTXT;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // نمایش امانت ها در صفحه مدیریت امانت
        try {
            searchAmanat();
        } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
        }

        // ایونت مربوط به دکمه داشبورد در منو
        dashboardBTN.setOnAction(e -> {
            Stage stage = (Stage) dashboardBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("dashboard");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت مربوط به دکمه کتاب ها در منو
        booksBTN.setOnAction(e -> {
            Stage stage = (Stage) booksBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("book");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت مربوط به قسمت کتابدار در منوی برنامه
        ketabdarBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("ketabdar");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت مربوط به دکمه گزارش در منوی برنامه
        gozareshBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // دکمه مربوط به صفحه تنظیمات در منوی برنامه
        settingBTN.setOnAction(e -> {
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        // ایونت مربوط به دکمه خروج از برنامه
        exitBTN.setOnAction(e -> {
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchLogin("loginPage");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        // ایونت باتن جستجو (شماره کتاب)
        ktbIDRB.setOnAction(e -> {
            if (ktbIDRB.isSelected()){
                hidePaneJostejo();
                ktbIDPane.setVisible(true);
            }
        });

        // ایونت باتن جستجو (تاریخ)
        dateRB.setOnAction(e -> {
            if (dateRB.isSelected()){
                hidePaneJostejo();
                amtDatePane.setVisible(true);
            }
        });

        // ایونت بخش جستحو
        ktbIDSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(ktbIDTXT.getText(),"ktbID");// سرچ بر اساس شماره کتاب
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // ایونت باتن جستجو (شماره یوزر)
        usrIDRB.setOnAction(e -> {
            if (usrIDRB.isSelected()){
                hidePaneJostejo();
                usrIDPane.setVisible(true);
            }
        });

        // باتن سرچ بر اساس آیدی یوزر
        usrIDSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(usrIDTXT.getText(),"usrID");
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // سرچ همه امانت ها
        allAmanatRB.setOnAction(e -> {
            try {
                hidePaneJostejo();
                searchAmanat();
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // سرچ بر اساس تاریخ مهلت برگشت
        searchMohlatRB.setOnAction(e -> {
            if (searchMohlatRB.isSelected()){
                searchMohlatPane.setVisible(true);
                searchDatePane.setVisible(false);
            }
        });

        amtMohlatSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(amtMohlatTXT.getText(),"mohlat");
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        // سرچ بر اساس امانت های بین دو تاریخ
        searchTwoDateRB.setOnAction(e -> {
            if (searchTwoDateRB.isSelected()){
                searchDatePane.setVisible(true);
                searchMohlatPane.setVisible(false);
            }
        });

        amtDateSearchBTN.setOnAction(e -> {
            try {
                searchAmanat(amtAzDateTXT.getText(),amtTaDateTXT.getText(),"twoDate");
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void hidePaneJostejo() {
        ktbIDPane.setVisible(false);
        usrIDPane.setVisible(false);
        amtDatePane.setVisible(false);
    }


    public void searchAmanat() throws SQLException, IOException, ParseException {
        List<Amanat> amanats = new ArrayList<>(Database.readAmanatsDB());
        showAmanat(amanats);
    }

    public void searchAmanat(String text,String lable) throws SQLException, IOException, ParseException {
        List<Amanat> amanats = new ArrayList<>(Database.readAmanatsDB(text,lable));
        showAmanat(amanats);
    }
    public void searchAmanat(String dateGet,String dateRtrn,String lable) throws SQLException, IOException, ParseException {
        List<Amanat> amanats = new ArrayList<>(Database.readAmanatsDB(dateGet,dateRtrn,lable));
        showAmanat(amanats);
    }


    public void showAmanat(List<Amanat> amanats) throws IOException {
        itemAmanatList.getChildren().clear();
        int rows = 0;
        for (Amanat amanat : amanats) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/fxmls/management/itemAmanat.fxml"));
            HBox amanatBox = fxmlLoader.load();
            itemAmanatCtrl itemAmanatCtrl = fxmlLoader.getController();
            itemAmanatCtrl.setItemAmanat(amanat);
            itemAmanatList.addRow(rows++, amanatBox);
            GridPane.setMargin(amanatBox, new Insets(5, 0, 5, 10));

        }
    }

}

