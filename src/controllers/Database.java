package controllers;


import controllers.login.LoginPageCtrl;
import javafx.scene.control.Alert;
import model.Admin;
import model.Amanat;
import model.Book;
import model.User;

import java.sql.*;
import java.text.ParseException;
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

    public static void deleteBook(String id) throws SQLException {
        makeConnection();
        String sql = String.format("DELETE FROM book WHERE ktbID = %s", id);
        try {
            getStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static Book getItemBookDB(String bookID) throws SQLException {
        makeConnection();
        String sql = String.format("Select * FROM book WHERE ktbID = '%s' ", bookID);
        ResultSet resultSet = getStatement().executeQuery(sql);
        Book book = new Book();
        resultSet.next();
        book.setKtbID(resultSet.getString("ktbID"));
        book.setKtbName(resultSet.getString("ktbName"));
        book.setKtbNevisande(resultSet.getString("ktbNevisandeh"));
        book.setKtbEhdaKonandeh(resultSet.getString("KtbEhdaKonandeh"));
        book.setKtbTedad(resultSet.getString("ktbTedad"));
        book.setKtbVazeit(resultSet.getString("ktbVazeiat"));
        book.setAmtTarakoneshID(resultSet.getString("amtTarakoneshID"));
        closeConnection();

        return book;
    }


    public static void updateBook(Book book, String ktbIDTXT) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE book SET ktbName = '%s', ktbTedad = '%s', ktbNevisandeh = '%s'," +
                        " ktbEhdaKonandeh = '%s' WHERE ktbID = '%s'", book.getKtbName(), book.getKtbTedad(), book.getKtbNevisande(),
                book.getKtbEhdaKonandeh(), ktbIDTXT);
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    public static void createUser(User user) throws SQLException {
        makeConnection();
        String sql = String.format("INSERT INTO user (usrID, userName, usrFName, usrLName, usrCodeMeli, usrPass)" +
                        " VALUES ('%s','%s','%s','%s','%s','%s')", user.getID(), user.getUserName(), user.getFirstName(),
                user.getLastName(), user.getCodeMeli(), user.getPassword());
        try {
            getStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static List<User> readUsersDB() throws SQLException {
        List<User> userList = new ArrayList<>();
        Database.makeConnection();
        ResultSet resultSet = Database.getStatement().executeQuery("SELECT * FROM user");
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setID(resultSet.getString("usrID"));
            user.setUserName(resultSet.getString("userName"));
            user.setPassword(resultSet.getString("usrPass"));
            user.setFirstName(resultSet.getString("usrFName"));
            user.setLastName(resultSet.getString("usrLName"));
            user.setCodeMeli(resultSet.getString("usrCodeMeli"));
            userList.add(user);
        }

        Database.closeConnection();
        return userList;
    }

    public static List<User> readUsersDB(String text, String lable) throws SQLException {
        List<User> userList = new ArrayList<>();
        Database.makeConnection();
        String sql;
        if (lable == "numOzv") {
            sql = String.format("SELECT * FROM user WHERE usrID = '%s'", text);
        } else {
            sql = String.format("SELECT * FROM user WHERE usrCodeMeli = '%s'", text);
        }
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setID(resultSet.getString("usrID"));
            user.setUserName(resultSet.getString("userName"));
            user.setPassword(resultSet.getString("usrPass"));
            user.setFirstName(resultSet.getString("usrFName"));
            user.setLastName(resultSet.getString("usrLName"));
            user.setCodeMeli(resultSet.getString("usrCodeMeli"));
            userList.add(user);
        }

        Database.closeConnection();
        return userList;
    }

    public static void deleteUser(String id) throws SQLException {
        makeConnection();
        String sql = String.format("DELETE FROM user WHERE usrID = %s", id);
        try {
            getStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public static User getItemUserDB(String userID) throws SQLException {
        makeConnection();
        String sql = String.format("Select * FROM user WHERE usrID = '%s' ", userID);
        ResultSet resultSet = getStatement().executeQuery(sql);
        User user = new User();
        resultSet.next();
        user.setID(String.valueOf(resultSet.getInt("usrID")));
        user.setUserName(resultSet.getString("userName"));
        user.setPassword(resultSet.getString("usrPass"));
        user.setFirstName(resultSet.getString("usrFName"));
        user.setLastName(resultSet.getString("usrLName"));
        user.setCodeMeli(resultSet.getString("usrCodeMeli"));
        closeConnection();

        return user;
    }

    public static void updateUser(User user, String usrIDTXT) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE user SET usrFName = '%s', usrLName = '%s' , usrCodeMeli = '%s', usrPass = '%s'" +
                " WHERE usrID = '%s'", user.getFirstName(), user.getLastName(), user.getCodeMeli(), user.getPassword(), usrIDTXT);
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    public static List<Amanat> readAmanatsDB() throws SQLException, ParseException {
        List<Amanat> amanatList = new ArrayList<>();
        Database.makeConnection();
        ResultSet resultSet = Database.getStatement().executeQuery("SELECT * FROM amanat");
        Amanat amanat;
        while (resultSet.next()) {
            amanat = new Amanat();
            amanat.setAmtID(resultSet.getString("amtID"));
            amanat.setKtbID(resultSet.getString("ktbID"));
            amanat.setUsrID(resultSet.getString("usrID"));
            amanat.setAmtDateGet(resultSet.getString("amtDateGet"));
            amanat.setAmtDateRtrn(resultSet.getString("amtDateRtrn"));
            amanat.setAmtDarkhastUsr(resultSet.getString("amtDarkhastUsr"));
            amanat.setAmtEmkanTamdid(resultSet.getString("amtEmkanTamdid"));
            amanat.setKtbName(getKtbName(resultSet.getString("ktbID")));
            amanat.setUsrName(getUsrName(resultSet.getString("usrID")));
            amanatList.add(amanat);
        }

        Database.closeConnection();
        return amanatList;
    }


    public static List<Amanat> readAmanatsDB(String text, String lable) throws SQLException, ParseException {
        List<Amanat> amanatList = new ArrayList<>();
        Database.makeConnection();
        String sql;
        if (lable.equals("ktbID")) {
            sql = String.format("SELECT * FROM amanat WHERE ktbID = '%s'", text);
        } else {
            sql = String.format("SELECT * FROM amanat WHERE usrID = '%s'", text);
        }
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        Amanat amanat;
        while (resultSet.next()) {
            amanat = new Amanat();
            amanat.setAmtID(resultSet.getString("amtID"));
            amanat.setKtbID(resultSet.getString("ktbID"));
            amanat.setUsrID(resultSet.getString("usrID"));
            amanat.setAmtDateGet(resultSet.getString("amtDateGet"));
            amanat.setAmtDateRtrn(resultSet.getString("amtDateRtrn"));
            amanat.setAmtDarkhastUsr(resultSet.getString("amtDarkhastUsr"));
            amanat.setAmtEmkanTamdid(resultSet.getString("amtEmkanTamdid"));
            amanat.setKtbName(getKtbName(resultSet.getString("ktbID")));
            amanat.setUsrName(getUsrName(resultSet.getString("usrID")));
            amanatList.add(amanat);
        }

        Database.closeConnection();
        return amanatList;
    }

    public static String getKtbName(String ktbID) throws SQLException {
        makeConnection();
        String sqlKtb = String.format("SELECT ktbName FROM book WHERE ktbID = '%s'", ktbID);
        ResultSet resultSetKtb = Database.getStatement().executeQuery(sqlKtb);
        resultSetKtb.next();
        String ktbName = resultSetKtb.getString("ktbName");
        resultSetKtb.close();
        return ktbName;
    }

    public static String getUsrName(String usrID) throws SQLException {
        makeConnection();
        String sqlUsr = String.format("SELECT usrFName,usrLName FROM user WHERE usrID = '%s'", usrID);
        ResultSet resultSetUsr = Database.getStatement().executeQuery(sqlUsr);
        resultSetUsr.next();
        String usrName = resultSetUsr.getString("usrFName") + " " + resultSetUsr.getString("usrLName");
        resultSetUsr.close();
        return usrName;
    }

    public static void updateAmanat(String amtID) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE amanat SET amtEmkanTamdid = '0', amtDarkhastUsr = '3' WHERE amtID = '%s'", amtID);
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    public static void updateAmanat(String amtID, String amtDateRtrn) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE amanat SET amtDateRtrn = '%s', amtEmkanTamdid = '0', amtDarkhastUsr = '2'" +
                " WHERE amtID = '%s'", amtDateRtrn, amtID);
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    public static Amanat getItemAmanatDB(String amtID) throws SQLException, ParseException {
        makeConnection();
        String sql = String.format("Select * FROM amanat WHERE amtID = '%d' ", Integer.parseInt(amtID));
        ResultSet resultSet = getStatement().executeQuery(sql);
        Amanat amanat = new Amanat();
        resultSet.next();
        amanat.setAmtID(String.valueOf(resultSet.getInt("amtID")));
        amanat.setKtbID(resultSet.getString("ktbID"));
        amanat.setUsrID(resultSet.getString("usrID"));
        amanat.setAmtDateGet(resultSet.getString("amtDateGet"));
        amanat.setAmtDateRtrn(resultSet.getString("amtDateRtrn"));
        amanat.setAmtDarkhastUsr(resultSet.getString("amtDarkhastUsr"));
        amanat.setAmtEmkanTamdid(resultSet.getString("amtEmkanTamdid"));
        closeConnection();

        return amanat;
    }


}


