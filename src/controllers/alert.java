package controllers;

import javafx.scene.control.Alert;

public class alert {

    public static  void regis_Faild(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Registration Failed pleaes TryAgain");
        alert.showAndWait();
    }

    public static  void regis_fillall(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("complate all the fields");
        alert.showAndWait();
    }

    public static  void regis_wrongconfirmpass(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Enter confirmPassword correctly");
        alert.showAndWait();

    }


    public static  void login_wrong(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("username or password is wrong");
        alert.showAndWait();
    }

    public static  void login_successful() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hey You");
        alert.setContentText("Welcome to YAR ");
        alert.showAndWait();

    }
    public static  void login_error() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Login Failed pleaes TryAgain");
        alert.showAndWait();
    }
    public static  void login_fiilall() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("complate all the fields");
        alert.showAndWait();
    }

    public static  void edit_userinfo_successfully() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setContentText("اطلاعات شما با موفقیت ویرایش شد");
        alert.showAndWait();
    }





}
