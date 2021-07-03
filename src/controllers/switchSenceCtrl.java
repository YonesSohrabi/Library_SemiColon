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

    public void sceneSwitchLogin(String sceneName) throws IOException {
        Parent root = null;
        switch (sceneName){
            case "register":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/RegisterPage.fxml"));
                break;
            case "loginPage":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/LoginPage.fxml"));
                break;
            case "home":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/Home.fxml"));
                break;
        }
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }


    public void sceneSwitchUserPage(String sceneName) throws IOException {
        Parent root = null;
        switch (sceneName){
            case "home":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/Home.fxml"));
                break;
            case "userinfo":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/UserInfo.fxml"));
                break;
            case "BookList":
                root = FXMLLoader.load(getClass().getResource("../view/fxmls/BookList.fxml"));
                break;
        }
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

}
