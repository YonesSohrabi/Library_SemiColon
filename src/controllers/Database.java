package controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import model.Book;
import model.User;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Database {
    private static Connection connection = null;

    public static Statement getStatement() {
        return statement;
    }

    private static Statement statement = null;

    private Database() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Unable to load MySQL Driver");
        }
    }

    public static void makeConnection() throws ClassNotFoundException, SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library" , "root" , "1234");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public static boolean login_user(String txtusername, String txtpassword) {
        boolean login = false;
        try {
            String mysql = "SELECT usrID ,usrFName, usrLName ,usrName ,usrPass FROM user";

            ResultSet result = Database.getStatement().executeQuery(mysql);
            while (result.next()) {
                //   String ID = result.getString("id");
                String username = result.getString("usrName");
                String password = result.getString("usrPass");
                String name = result.getString("usrFName");
                String family = result.getString("usrLName");
                if (txtusername.compareTo(username) == 0 && txtpassword.compareTo(password) == 0) {
                    // ست کردن اطلاعات در کلاس person مطابق با اطلاعات کاربر
                    login = true;
                    String id = result.getString("usrID");
                    LoginPageCtrl.set_id(id);
                    System.out.println("id geted from databace =" + id);
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return login;
    }

    public static void registerUser(User p) {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS user ( `usrID` VARCHAR(11) NOT NULL , `usrFName` varchar(80) NOT NULL , `usrLName` varchar(80) NOT NULL , `usrName` varchar(40) NOT NULL , `usrPass` varchar(45) NOT NULL ,`usrCodeMeli` varchar(11) ,`usrBookList` TEXT , PRIMARY KEY (`usrID`) ,UNIQUE (`usrName`))";
            getStatement().execute(crtbl);
            //مشکل(ارور) در ثبت نام
        } catch (Exception ex) {
            System.out.println(ex);
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("ERROR");
            alert2.setHeaderText(null);
            alert2.setContentText("Registration Failed pleaes TryAgain");
            alert2.showAndWait();
        }
        //ارسال اطلاعات ثبت نام به دیتابیس
        String setinfo = "INSERT INTO user (usrID ,usrFName, usrLName , usrName , usrPass)  values ('%s','%s','%s','%s','%s')";
        setinfo = String.format(setinfo, p.getID(), p.getFirstName(), p.getLastName(), p.getUserName(), p.getPassword());
        System.out.println(setinfo);

        try {
            getStatement().execute(setinfo);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Registration");
            alert2.setHeaderText(null);
            alert2.setContentText("Successfully Registration!\nyour id is : " + p.getID());
            alert2.showAndWait();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnection();
    }

    public static User set_home_items() {
        String id = LoginPageCtrl.get_id();
        System.out.println("id in dabase class = " + id);
        User user = new User();
        try {
            String mysql = "SELECT usrName, usrLName , usrName , usrPass FROM user WHERE usrID =" + id;
            System.out.println("mysql=" + mysql);
            ResultSet result = Database.statement.executeQuery(mysql);
            result.next();
            //   String ID = result.getString("id");
            String username = result.getString("usrName");
            String password = result.getString("usrPass");
            String name = result.getString("usrName");
            String family = result.getString("usrLName");
            String fullname = (name + " " + family);
            System.out.println("fullname =" + fullname);
            user.setFirstName(name);
            user.setLastName(family );
            user.setID( id);
            user.setUserName(username);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    ///////////////////////////////////<<BOOK>>/////////////////////////////////////

    public static void create_book_table() {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS `book` ( `ktbID` INT NOT NULL , `ktbEhdaKonande` varchar(80) NOT NULL , `ktbName` varchar(80) NOT NULL ,  `ktbNevisandeh` varchar(80) NOT NULL ,  `ktbTedad` int NOT NULL ,  `ktbVazeiat` varchar(10) NOT NULL , `amtTarakoneshID` varchar(11) , `ktbEhdaDate` TEXT NOT NULL , PRIMARY KEY (`ktbID`))";
            Database.statement.execute(crtbl);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void add_book(Book book) throws SQLException {
        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
        String dateformat = fr.format(date);

        //delete date_ms later if dont use
        String addbook= "INSERT INTO book (ktbName, ktbNevisandeh , ktbEhdaKonande , ktbEhdaDate, ktbVazeiat ,ktbTedad , ktbID)  values ('%s','%s','%s','%s','%s','%s','%d')";

        Random rnd = new Random();
        int book_id = rnd.nextInt(9000)+1000;
        System.out.println("bookid = "+book_id);
        System.out.println("namebook = " + book.getKtbName() );
        //int book_id = Integer.parseInt(String.valueOf(state.executeQuery(getid)));
        addbook = String.format(addbook, book.getKtbName() , book.getKtbNevisande() , book.getKtbEhdaKonandeh() , dateformat , "موجود" , 1 , book_id );
        System.out.println(addbook);
        Database.getStatement().execute(addbook);
        Database.closeConnection();
    }
}


