package controllers;


import controllers.login.LoginPageCtrl;
import javafx.scene.control.Alert;
import model.Admin;
import model.Book;
import model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;

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

    public static void makeConnection() throws  SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library" ,
                    "root" , "1380Ys1388?");
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
            makeConnection();
            String mysql = "SELECT * FROM user";

            ResultSet result = Database.getStatement().executeQuery(mysql);
            while (result.next()) {
                //   String ID = result.getString("id");
                String username = result.getString("userName");
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
            String crtbl = "CREATE TABLE  IF NOT EXISTS user ( `usrID` VARCHAR(11) NOT NULL , `usrFName` varchar(80) NOT NULL ," +
                    " `usrLName` varchar(80) NOT NULL , `userName` varchar(40) NOT NULL , `usrPass` varchar(45) NOT NULL ,`usrCodeMeli` varchar(11) ,`usrBookList` TEXT , PRIMARY KEY (`usrID`) ,UNIQUE (`userName`))";
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

        Random rnd = new Random();
        String id = String.valueOf(rnd.nextInt(9000)+1000);
        System.out.println("id = " + id);
        String setinfo = "INSERT INTO user (usrID ,usrFName, usrLName , userName , usrPass)  values ('%s','%s','%s','%s','%s')";

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


    // گرفتن اطلاعات مدیران کتابخانه از جدول ادمین موجود در دیتابیس
    public static List<Admin> getInfoAdmin() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        makeConnection();
        ResultSet resultSet = Database.getStatement().executeQuery("SELECT * FROM admin");
        Admin admin;
        while (resultSet.next()) {
            admin = new Admin();
            admin.setID(resultSet.getString("admID"));
            admin.setUserName(resultSet.getString("admUserName"));
            admin.setPassword(resultSet.getString("admPass"));
            admin.setFirstName(resultSet.getString("admFName"));
            admin.setLastName(resultSet.getString("admLName"));
            admin.setCodeMeli(resultSet.getString("admCodeMeli"));
            admins.add(admin);
        }
        Database.closeConnection();
        return admins;

    }


    public static User set_home_items() {
        String id = LoginPageCtrl.get_id();
        System.out.println("id in dabase class = " + id);
        User user = new User();
        try {
            String mysql = "SELECT usrFName, usrLName , userName , usrPass FROM user WHERE usrID =" + id;
            System.out.println("mysql=" + mysql);
            ResultSet result = Database.statement.executeQuery(mysql);
            result.next();
            //   String ID = result.getString("id");
            String username = result.getString("userName");
            String password = result.getString("usrPass");
            String name = result.getString("usrFName");
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
        String addbook= "INSERT INTO book (ktbName, ktbNevisandeh , ktbEhdaKonandeh , ktbVazeiat ,ktbTedad , ktbID)  values ('%s','%s','%s','%s','%s','%d')";

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

    public static void createBook(Book book) throws SQLException {
        makeConnection();
        String sql = String.format("INSERT INTO book VALUES ('%s','%s','%s','%s','%s','%s','%s')", book.getKtbID(), book.getKtbName(),
                book.getKtbNevisande(), book.getKtbEhdaKonandeh(), Integer.valueOf(book.getKtbTedad()), book.getKtbVazeit(),
                book.getAmtTarakoneshID());
        try {
            getStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static List<Book> readBooksDB() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        ResultSet resultSet = Database.getStatement().executeQuery("SELECT * FROM book");
        Book book;
        while (resultSet.next()) {
            book = new Book();
            book.setKtbID(String.valueOf(resultSet.getInt("ktbID")));
            book.setKtbName(resultSet.getString("ktbName"));
            book.setKtbNevisande(resultSet.getString("ktbNevisandeh"));
            book.setKtbTedad(resultSet.getString("ktbTedad"));
            book.setKtbVazeit(resultSet.getString("ktbVazeiat"));
            bookList.add(book);
        }

        Database.closeConnection();
        return bookList;
    }


    public static List<Book> readBooksDB(String txet, String lable) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        String sql;
        if (lable.equals("name")) {
            sql = String.format("SELECT * FROM book WHERE ktbName = '%s'", txet);
        } else {
            sql = String.format("SELECT * FROM book WHERE ktbID = '%s'", txet);
        }

        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        Book book;
        while (resultSet.next()) {
            book = new Book();
            book.setKtbID(String.valueOf(resultSet.getInt("ktbID")));
            book.setKtbName(resultSet.getString("ktbName"));
            book.setKtbNevisande(resultSet.getString("ktbNevisandeh"));
            book.setKtbTedad(resultSet.getString("ktbTedad"));
            book.setKtbVazeit(resultSet.getString("ktbVazeiat"));
            bookList.add(book);
        }
        return bookList;
    }

    public static List<Book> readBooksDB(int vazeiat) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        String vaz;
        if (vazeiat == 1) {
            vaz = "موجود";
        } else {
            vaz = "ناموجود";
        }
        String sql = String.format("SELECT * FROM book WHERE ktbVazeiat = '%s'", vaz);
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        Book book;
        while (resultSet.next()) {
            book = new Book();
            book.setKtbID(String.valueOf(resultSet.getInt("ktbID")));
            book.setKtbName(resultSet.getString("ktbName"));
            book.setKtbNevisande(resultSet.getString("ktbNevisandeh"));
            book.setKtbTedad(resultSet.getString("ktbTedad"));
            book.setKtbVazeit(resultSet.getString("ktbVazeiat"));
            bookList.add(book);
        }
        return bookList;
    }

}


