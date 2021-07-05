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
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class mngUserCtrl extends mngStage implements Initializable {

    @FXML
    private GridPane itemUserList;

    @FXML
    private JFXRadioButton numUserRB;

    @FXML
    private JFXRadioButton codeMeliUserRB;

    @FXML
    private JFXRadioButton allUserRB;

    @FXML
    private Pane usrNumOzvPane;

    @FXML
    private JFXTextField usrNumOzvtxt;

    @FXML
    private JFXButton usrNumOzvSearchbtn;

    @FXML
    private Pane usrCodeMeliPane;

    @FXML
    private JFXTextField usrCodeMelitxt;

    @FXML
    private JFXButton usrCodeMeliSearchbtn;

    @FXML
    private JFXButton addUserbtn;

    static Stage addUserPage = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            searchUser();
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

        booksBTN.setOnAction(e ->{
            Stage stage = (Stage) booksBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("book");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gozareshBTN.setOnAction(e ->{
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("gozaresh");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        settingBTN.setOnAction(e ->{
            Stage stage = (Stage) ketabdarBTN.getScene().getWindow();
            switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
            try {
                switchSenceCtrl.sceneSwitchManagement("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        exitBTN.setOnAction(e ->{
            Stage stage = (Stage) exitBTN.getScene().getWindow();
            stage.close();
        });

        addUserbtn.setOnAction(e -> {
            try {
                openAddUserPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        numUserRB.setOnAction(e -> {
            if(numUserRB.isSelected()){
                hidePaneJostejo();
                usrNumOzvPane.setVisible(true);

            }
        });

        usrNumOzvSearchbtn.setOnAction(e -> {
            try {
                searchUser(usrNumOzvtxt.getText(),"numOzv");
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        codeMeliUserRB.setOnAction(e -> {
            if (codeMeliUserRB.isSelected()){
                hidePaneJostejo();
                usrCodeMeliPane.setVisible(true);
            }
        });

        usrCodeMeliSearchbtn.setOnAction(e -> {
            try {
                searchUser(usrCodeMelitxt.getText(),"codeMeli");
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });


        allUserRB.setOnAction(e -> {
            hidePaneJostejo();
            try {
                searchUser();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void openAddUserPage() throws IOException {
        if (addUserPage == null) {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../views/addUserPage.fxml"));
            addUserPage = new Stage();
            addUserPage.setScene(new Scene(root));
            addUserPage.show();
        }
    }

    private void hidePaneJostejo() {
        usrNumOzvPane.setVisible(false);
        usrCodeMeliPane.setVisible(false);
    }

    public void searchUser() throws SQLException, IOException {
        List<User> users = new ArrayList<>(Database.readUsersDB());
        showUser(users);
    }
    public void searchUser(String text, String lable) throws SQLException, IOException {
        List<User> users = new ArrayList<>(Database.readUsersDB(text,lable));
        showUser(users);
    }

    public void showUser(List<User> users) throws IOException {
        itemUserList.getChildren().clear();
        int rows = 0;
        for (User user:users) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/fxmls/management/itemUser.fxml"));
            HBox userBox = fxmlLoader.load();
            itemUserCtrl itemUserCtrl = fxmlLoader.getController();
            itemUserCtrl.setItemUser(user);
            itemUserList.addRow(rows++, userBox);
            GridPane.setMargin(userBox, new Insets(5, 0, 5, 10));
        }
    }

}

