package controllers.management;

import controllers.Database;
import controllers.switchSenceCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class itemUserCtrl implements Initializable {

    @FXML
    private Label userNumIdLBL;

    @FXML
    private Label userNameLBL;

    @FXML
    private Label userFamilyLBL;

    @FXML
    private Label userCodeMeliLBL;

    @FXML
    private Button editBTN;


    @FXML
    private Button deleteBTN;

    static Stage editUserPage = null;
    static String userID = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editBTN.setOnAction(e -> {
            try {
                userID = userNumIdLBL.getText();
                openEditUserPage();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        deleteBTN.setOnAction(e -> {
            System.out.println(userNameLBL.getText());
            try {
                Database.deleteUser(userNumIdLBL.getText());
                Stage stage = (Stage) deleteBTN.getScene().getWindow();
                switchSenceCtrl switchSenceCtrl = new switchSenceCtrl(stage);
                switchSenceCtrl.sceneSwitchManagement("ketabdar");
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });

    }

    private void openEditUserPage() throws IOException {
        if (editUserPage == null) {
            Pane root = FXMLLoader.load(getClass().getResource("../../view/fxmls/management/editUserPage.fxml"));
            editUserPage = new Stage();
            editUserPage.initStyle(StageStyle.UNDECORATED);
            editUserPage.setScene(new Scene(root));
            editUserPage.show();

        }
    }

    public void setItemUser(User user) {
        userNumIdLBL.setText(user.getID());
        userNameLBL.setText(user.getFirstName());
        userFamilyLBL.setText(user.getLastName());
        userCodeMeliLBL.setText((user.getCodeMeli()));
    }
}
