package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class switchSenceCtrl {
    Stage stage;
    public switchSenceCtrl(Stage stage) {
        this.stage = stage;
    }
    // کنترل صفحات لاگین و ارتباط دادن آن ها به هم
    public void sceneSwitchLogin(String sceneName) throws IOException {
        Parent root = null;
        switch (sceneName){
            case "register":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/login/RegisterPage.fxml"));
                break;
            case "loginPage":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/login/LoginPage.fxml"));
                break;
            case "admLogin":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/login/admLoginPage.fxml"));
                break;
            case "home":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/user/Home.fxml"));
                break;
        }
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    public void sceneSwitchUserPage(String sceneName) throws IOException {
        Parent root = null;
        switch (sceneName){
            case "Home":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/user/Home.fxml"));
                break;
            case "UserInfo":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/user/UserInfo.fxml"));
                break;
            case "BookList":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/user/BookList.fxml"));
                break;
            case "loginPage":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/LoginPage.fxml"));
                break;
        }
        Scene scene = new Scene(root,1050,576);
        stage.setScene(scene);
        stage.show();
    }

    // کنترل صفحات ادمین و ارتباط دادن آن ها به هم
    public void sceneSwitchManagement(String sceneName) throws IOException {
        Parent root = null;
        switch (sceneName){
            case "dashboard":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngDashboard.fxml"));
                break;
            case "book":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngBook.fxml"));
                break;
            case "ketabdar":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngUser.fxml"));
                break;
            case "amanat":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngAmanat.fxml"));
                break;
            case "gozaresh":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngGozarsh.fxml"));
                break;
            case "setting":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/management/mngSetting.fxml"));
                break;

        }

        Scene scene = new Scene(root,1050,576);
        stage.setScene(scene);
        stage.show();

    }

}
