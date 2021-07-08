package controllers.management;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controllers.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class addUserPageCtrl implements Initializable {
    @FXML
    private JFXButton exitBTN;

    @FXML
    private JFXTextField userNameTXT;

    @FXML
    private JFXButton addBTN;

    @FXML
    private JFXTextField usrIDTXT;

    @FXML
    private JFXTextField usrLNameTXT;

    @FXML
    private JFXTextField usrCodeMeliTXT;

    @FXML
    private JFXPasswordField passwordTXT;

    @FXML
    private Label addUserErrLBL;

    @FXML
    private JFXTextField usrFNameTXT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usrIDTXT.setText(String.valueOf(userIdRandom()));
        exitBTN.setOnAction(e -> {
            closeBTN();
        });

        addBTN.setOnAction(e -> {
            try {
                addUserToDB();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private int userIdRandom() {
        Random rand = new Random();
        int userID = rand.nextInt((9999 - 1000) + 1) + 1000;
        return userID;
    }

    private void addUserToDB() throws SQLException {
        User user = new User();
        user.setID(usrIDTXT.getText());
        user.setUserName(userNameTXT.getText());
        user.setFirstName(usrFNameTXT.getText());
        user.setLastName(usrLNameTXT.getText());
        user.setCodeMeli(usrCodeMeliTXT.getText());
        user.setPassword(passwordTXT.getText());
        if (
                !(usrFNameTXT.getText().equals("") ||
                        usrLNameTXT.getText().equals("") ||
                        usrCodeMeliTXT.getText().equals("") ||
                        passwordTXT.getText().equals(""))
        ) {
            Database.createUser(user);
            closeBTN();
        } else {
            addUserErrLBL.setVisible(true);
        }
    }

    public void closeBTN() {
        ((Stage) exitBTN.getScene().getWindow()).close();
        mngUserCtrl.addUserPage = null;
    }


}
