package controllers;

import javafx.scene.control.Alert;

public class alert {

    public static  void informationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static  void errorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static  void login_successful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hey You");
        alert.setContentText("Welcome to YAR ");
        alert.showAndWait();
    }

    public static  void edit_userinfo_successfully() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setContentText("اطلاعات شما با موفقیت ویرایش شد");
        alert.showAndWait();
    }
}
