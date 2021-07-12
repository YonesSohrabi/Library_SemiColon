package controllers;


import com.mysql.cj.protocol.Resultset;
import controllers.login.LoginPageCtrl;
import controllers.login.RegisterPageCtrl;
import javafx.scene.control.Alert;
import model.Admin;
import model.Amanat;
import model.Book;
import model.User;

import java.sql.*;
import java.text.ParseException;
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

    //اتصال به دیتابیس
    public static void makeConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    //قطع کردن اتصال به دیتابیس
    public static void closeConnection() {
        if (connection != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //اتصال به دیتابیس یوزر و چک کردن نام کاربری و رمز ورود وارد شده برای ورود کاربر با کاربر های ثبت نام شده
    public static boolean login_user(String txtusername, String txtpassword) {
        boolean login = false;
        try {
            makeConnection();
            String mysql = "SELECT * FROM user";

            ResultSet result = Database.getStatement().executeQuery(mysql);
            while (result.next()) {
                String username = result.getString("userName");
                String password = result.getString("usrPass");
                String name = result.getString("usrFName");
                String family = result.getString("usrLName");
                if (txtusername.compareTo(username) == 0 && txtpassword.compareTo(password) == 0) {
                    login = true;
                    String id = result.getString("usrID");
                    LoginPageCtrl.set_id(id);
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //برگرداندن true درصورت موجود بودن نام کاربری و رمز و برگرداندن false درصورت اشتباه بودن نام کاربری یا رمز ورود
        return login;
    }

    //ثبت نام یوزر جدید
    public static void registerUser(User p) {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS user ( `usrID` VARCHAR(11) NOT NULL , `usrFName` varchar(80) NOT NULL , `usrLName` varchar(80) NOT NULL , `userName` varchar(40) NOT NULL , `usrPass` varchar(45) NOT NULL ,`usrCodeMeli` varchar(11) ,`usrBookList` TEXT ,`status` varchar(10) default '1' , PRIMARY KEY (`usrID`) ,UNIQUE (`userName`))";
            getStatement().execute(crtbl);
            //مشکل(ارور) در ثبت نام
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //ارسال اطلاعات ثبت نام کننده به دیتابیس یوزر
        Random rnd = new Random();
        String id = String.valueOf(rnd.nextInt(9000) + 1000);
        String setinfo = "INSERT INTO user (usrID ,usrFName, usrLName , userName , usrPass)  values ('%s','%s','%s','%s','%s')";
        setinfo = String.format(setinfo, p.getID(), p.getFirstName(), p.getLastName(), p.getUserName(), p.getPassword());

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
    // گرفتن اطلاعات کاربر از دیتابیس یوزر
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
    //آپدیت کردن اطلاعات یک یوزر در دیتابیس یوزر
    public static void updateUser(User user, String usrIDTXT) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE user SET usrFName = '%s', usrLName = '%s' , usrPass = '%s' , userName = '%s' , usrCodeMeli = '%s'" +
                " WHERE usrID = '%s'", user.getFirstName(), user.getLastName(), user.getPassword(), user.getUserName(), user.getCodeMeli(), usrIDTXT);
        getStatement().executeUpdate(sql);
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
    //گرفتن اطلاعات کاربر مشخص در دیتابیس با استفاده از آیدی آن(به منظور ست کردن فیلدهای موجود در برنامه)
    public static User set_home_items() {
        String id = LoginPageCtrl.get_id();
        User user = new User();
        try {
            String mysql = "SELECT usrFName, usrLName , userName , usrPass FROM user WHERE usrID =" + id +" and status ='1' ";
            System.out.println("mysql=" + mysql);
            ResultSet result = Database.statement.executeQuery(mysql);
            result.next();
            String username = result.getString("userName");
            String password = result.getString("usrPass");
            String name = result.getString("usrFName");
            String family = result.getString("usrLName");
            String fullname = (name + " " + family);
            user.setFirstName(name);
            user.setLastName(family);
            user.setID(id);
            user.setUserName(username);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    ///////////////////////////////////<<BOOK>>/////////////////////////////////////
    //ساختن تیبل book در دیتابیس
    public static void create_book_table() {
        try {
            //ساختن تیبل مورد نیاز در دیتابیس
            String crtbl = "CREATE TABLE  IF NOT EXISTS `book` ( `ktbID` INT NOT NULL , `ktbEhdaKonande` varchar(80) NOT NULL , `ktbName` varchar(80) NOT NULL ,  `ktbNevisandeh` varchar(80) NOT NULL ,  `ktbTedad` int NOT NULL ,  `ktbVazeiat` varchar(10) NOT NULL , `amtTarakoneshID` varchar(11) , `ktbEhdaDate` TEXT NOT NULL , `ktbAmntGirande` TEXT , `status` varchar(10) default '1'  , PRIMARY KEY (`ktbID`))";
            Database.statement.execute(crtbl);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    //اضافه کردن کتاب جدید به دیتابیس book
    public static void add_book(Book book) throws SQLException {
        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
        String[] dates = fr.format(date).split("/");
        Roozh roozh = new Roozh();
        roozh.gregorianToPersian(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));

        String addbook = "INSERT INTO book (ktbName, ktbNevisandeh , ktbEhdaKonande , ktbEhdaDate , ktbVazeiat ,ktbTedad , ktbAmntGirande , ktbID)  values ('%s','%s','%s','%s','%s','%s','%s','%d')";
        Random rnd = new Random();
        int book_id = rnd.nextInt(9000) + 1000;
        addbook = String.format(addbook, book.getKtbName(), book.getKtbNevisande(), book.getKtbEhdaKonandeh(), roozh, "موجود", 1, "", book_id);
        Database.getStatement().execute(addbook);
        Database.closeConnection();
    }
    //ساختن یک لیست از کتابهای موجود در دیتابیس book برای جهت نمایش در برنامه
    public static List<Book> createBookList(String sql) {
        List<Book> booklist1 = null;
        try {
            System.out.println(sql);
            ResultSet result = Database.statement.executeQuery(sql);
            int i = 0;
            booklist1 = new ArrayList<>();
            while (result.next()) {
                //گرفتن اطلاعات از دیتابیس
                int bookid = result.getInt("ktbID");
                String bookname = result.getString("ktbName");
                String bookwriter = result.getString("ktbNevisandeh");
                String ehdakonande = result.getString("ktbEhdaKonande");
                String vaziyat = result.getString("ktbVazeiat");
                String amnttarakoneshid = result.getString("amtTarakoneshID");
                //ست کردن اطلاعات در کلاس Book
                Book book = new Book();
                book.setKtbName(bookname);
                book.setKtbNevisande(bookwriter);
                book.setKtbID(String.valueOf(bookid));
                book.setKtbEhdaKonandeh(ehdakonande);
                book.setKtbTedad("1");
                book.setKtbVazeit(vaziyat);
                book.setAmtTarakoneshID(amnttarakoneshid);
                //اضافه کردن اطلاعات کتاب به لیست کل کتابها
                booklist1.add(book);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(booklist1);
        return booklist1;
    }
    //اضافه کردن کتاب جدید به دیتابیس book در صفحه ی ادمین
    public static void createBook(Book book) throws SQLException {
        makeConnection();
        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
        String[] dates = fr.format(date).split("/");
        Roozh roozh = new Roozh();
        roozh.gregorianToPersian(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
        String sql = String.format("INSERT INTO book VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                book.getKtbID(), book.getKtbName(), book.getKtbNevisande(), book.getKtbEhdaKonandeh(),
                Integer.valueOf(book.getKtbTedad()), book.getKtbVazeit(), book.getAmtTarakoneshID(),roozh,"","1",book.getStatus());
        try {
            getStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    //خواندن اطلاعات کتاب ها از دیتابیس book (ادمین)
    public static List<Book> readBooksDB() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        ResultSet resultSet = Database.getStatement().executeQuery("SELECT * FROM book WHERE status = '1'");
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

    //خواندن اطلاعات کتابی خاص از دیتابیس book (ادمین)
    public static List<Book> readBooksDB(String txet, String lable) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        String sql;
        if (lable.equals("name")) {
            sql = String.format("SELECT * FROM book WHERE ktbName = '%s' AND status = '1' ", txet);
        } else {
            sql = String.format("SELECT * FROM book WHERE ktbID = '%s' AND status = '1' ", txet);
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
            book.setAmtTarakoneshID(resultSet.getString("amtTarakoneshID"));
            bookList.add(book);
        }
        return bookList;
    }
    //خواندن اطلاعات کتابها ازجدول book (ادمین)
    public static List<Book> readBooksDB(int vazeiat) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        Database.makeConnection();
        String vaz;
        if (vazeiat == 1) {
            vaz = "موجود";
        } else {
            vaz = "ناموجود";
        }
        String sql = String.format("SELECT * FROM book WHERE ktbVazeiat = '%s' AND status = '1' ", vaz);
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
    //د حذف یک کتاب از جدول book دیتابیس
    public static void deleteBook(String id) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE book SET status = '0' WHERE ktbID = '%s'",id);
        getStatement().executeUpdate(sql);
        closeConnection();
        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
        String[] dates = fr.format(date).split("/");
        Roozh roozh = new Roozh();
        roozh.gregorianToPersian(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
        makeConnection();
        sql = String.format("UPDATE amanat SET amtDateRtrn = '%s', amtDarkhastUsr = '%s' WHERE ktbID = '%s'",roozh,"عودت",id);
        getStatement().executeUpdate(sql);
        closeConnection();
    }
    //گرفتن اطلاعات کتابی خاص از جدول book دیتابیس با استفاده از آی دی کتاب
    public static Book getItemBookDB(String bookID) throws SQLException {
        makeConnection();
        String sql = String.format("Select * FROM book WHERE ktbID = '%s' ", bookID);
        ResultSet resultSet = getStatement().executeQuery(sql);
        Book book = new Book();
        resultSet.next();
        book.setKtbID(resultSet.getString("ktbID"));
        book.setKtbName(resultSet.getString("ktbName"));
        book.setKtbNevisande(resultSet.getString("ktbNevisandeh"));
        book.setKtbEhdaKonandeh(resultSet.getString("KtbEhdaKonande"));
        book.setKtbTedad(resultSet.getString("ktbTedad"));
        book.setKtbVazeit(resultSet.getString("ktbVazeiat"));
        book.setAmtTarakoneshID(resultSet.getString("amtTarakoneshID"));
        closeConnection();
        return book;
    }
    //آپدیت کردن اطلاعات کتابی خاص در جدول book دیتابیس
    public static void updateBook(Book book, String ktbIDTXT) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE book SET ktbName = '%s', ktbTedad = '%s', ktbNevisandeh = '%s'," +
                        " ktbEhdaKonande = '%s' WHERE ktbID = '%s'", book.getKtbName(), book.getKtbTedad(), book.getKtbNevisande(),
                book.getKtbEhdaKonandeh(), ktbIDTXT);
        getStatement().executeUpdate(sql);
        closeConnection();
    }
    //ساخت کاربر جدید توسط ادمین
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
    //خواندن اطلاعات کاربر از جول user دیتابیس
    public static List<User> readUsersDB() throws SQLException {
        List<User> userList = new ArrayList<>();
        Database.makeConnection();
        ResultSet resultSet = getStatement().executeQuery("SELECT * FROM user WHERE status = '1'");
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

    //خواندن اطلاعات کاربری خاص از جول user دیتابیس با استفاده از آی دی یا کدملی
    public static List<User> readUsersDB(String text, String lable) throws SQLException {
        List<User> userList = new ArrayList<>();
        Database.makeConnection();
        String sql;
        if (lable == "numOzv") {
            sql = String.format("SELECT * FROM user WHERE usrID = '%s' AND status = '1'", text);
        } else {
            sql = String.format("SELECT * FROM user WHERE usrCodeMeli = '%s' AND status = '1'", text);
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
    //حذف کردن کاربر از جول user دیتابیس
    public static void deleteUser(String id) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE user SET status = '0' WHERE usrID = '%s'",id);
        getStatement().executeUpdate(sql);
        closeConnection();

        Date date = new Date();
        SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd");
        String[] dates = fr.format(date).split("/");
        Roozh roozh = new Roozh();
        roozh.gregorianToPersian(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
        makeConnection();
        sql = String.format("UPDATE amanat SET amtDateRtrn = '%s', amtDarkhastUsr = '%s' WHERE usrID = '%s'",roozh,"عودت",id);
        getStatement().executeUpdate(sql);
        closeConnection();

        makeConnection();
        sql = String.format("SELECT ktbID FROM amanat WHERE usrID = '%s'",id);
        ResultSet resultSet = getStatement().executeQuery(sql);
        ArrayList ktbId = new ArrayList();
        while (resultSet.next()){
            ktbId.add(resultSet.getString(1));
        }
        closeConnection();

        makeConnection();
        for (Object kid:ktbId) {
            sql = String.format("UPDATE book SET ktbVazeiat = '%s' WHERE ktbID = '%s'","موجود",kid);
            getStatement().executeUpdate(sql);
        }
        closeConnection();

    }
    //گرفتن اطلاعات مربوط به امانت گیری کتابها از جدول amant دیتابیس
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

    // گرفتن اطلاعات مربوط به امانت گیری کتابی خاص از جدول amant دیتابیس با استفاده از آی دی کتاب یا آی دی یوزر
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
    //گرفتن نام کتاب مورد نظر از جدول book دیتابیس با استفاده از آی دی کتاب
    public static String getKtbName(String ktbID) throws SQLException {
        makeConnection();
        String sqlKtb = String.format("SELECT ktbName FROM book WHERE ktbID = '%s' ", ktbID);
        System.out.println(sqlKtb);
        ResultSet resultSetKtb = Database.getStatement().executeQuery(sqlKtb);
        resultSetKtb.next();
        String ktbName = resultSetKtb.getString("ktbName");
        resultSetKtb.close();
        return ktbName;
    }
    //گرفتن وضعیت(موجود بودن یا نبودن) کتابی خاص در جدول book دیتابیس با استفاده از آی دی کتاب
    public static boolean getBookVaziyat(String ktbid) throws SQLException {
        makeConnection();
        boolean vaziyat ;
        String testvaziyat = "select ktbVazeiat from book where ktbID = '%s' ";
        testvaziyat = String.format(testvaziyat , ktbid);
        ResultSet rs = Database.getStatement().executeQuery(testvaziyat);
        rs.next();
        String vazeiat = rs.getString("ktbVazeiat") ;
        rs.close();
        if (vazeiat.equals("موجود") ) {
            vaziyat = true;
        }
        else{
            vaziyat = false;
        }
        closeConnection();
        return vaziyat;
    }

    //امانت گرفتن کتاب از کتابخانه
    public static void amanatgiri(Amanat amanat) throws SQLException {
        //گرفتن وضعیت کتاب از طریق متد مربوطه
        boolean vaziyat = getBookVaziyat(amanat.getKtbID());
        if(vaziyat==true) {
            //در صورت موجود بودن کتاب-->اتصال به دیتابیس و ثبت اطلاعات مربوط به امانت گیری در جدول amanat دیتابیس
            makeConnection();
            String amanatgiri = "INSERT INTO amanat (ktbID, usrID , amtDateGet , amtDateRtrn ,amtDarkhastUsr , amtEmkanTamdid)  values ('%s','%s','%s','%s','%s','%s')";
            amanatgiri = String.format(amanatgiri, amanat.getKtbID(), amanat.getUsrID(), amanat.getAmtDateGet(),
                    amanat.getAmtDateRtrn(), amanat.getAmtDarkhastUsr(), amanat.getAmtEmkanTamdid());
            System.out.println(amanatgiri);
            Database.getStatement().execute(amanatgiri, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int amtID = resultSet.getInt(1);
            closeConnection();
            updateBookAmntStatus(LoginPageCtrl.get_id(), String.valueOf(amtID), "ناموجود", amanat.getKtbID());
        }
        //در ضورت موجود نبودن کتاب نمایش الرت کتاب موجود نیست
        else if(vaziyat==false){
            alert.errorAlert("کتاب مورد نظر شما درحال حاضر موجود نمیباشد");
        }
    }

    //آپدیت کردن فیلدهای مربوط به امانت در جدول book دیتابیس در صورت ایجاد تغییر
    public static void updateBookAmntStatus(String ktbamntgirande , String amnttarakoneshid , String ktbvaziyat , String ktbid) throws SQLException {
        makeConnection();
        String updateBook = String.format("UPDATE book SET ktbAmntGirande = '%s' ,amtTarakoneshID = '%s',  ktbVazeiat = '%s' WHERE ktbID = '%s' ",
                ktbamntgirande, amnttarakoneshid, ktbvaziyat , ktbid);
        getStatement().execute(updateBook);
        closeConnection();

        makeConnection();
        String updateAmanat = String.format("UPDATE amanat SET amtDarkhastUsr = \"عودت\" WHERE amtID = '%s' ", getAmntTarakoneshID(ktbid));
        getStatement().execute(updateAmanat);
        closeConnection();
    }

    //گرفتن تاریخ امانت گیری کتابی خاص از جدول amanat دیتابیس با استفاده از آی دی امانت(آی دی امانت بین جدول book و amanat یکسان هستند)
    public static String getAmanatgiriDate(String amnttarakoneshid) throws SQLException {
        makeConnection();
        String sql = String.format("Select amtDateGet FROM amanat WHERE amtID = '%d' ", Integer.parseInt(amnttarakoneshid));
        System.out.println(sql);
        ResultSet rs = Database.getStatement().executeQuery(sql);
        rs.next();
        String amanatdateget = rs.getString("amtDateGet") ;
        rs.close();
        return amanatdateget;
    }

    //گرفتن مهلت تحویل کتابی خاص از جدول amanat دیتابیس با استفاده از آی دی امانت(آی دی امانت بین جدول book و amanat یکسان هستند)
    public static String getMohlatTahvil(String amnttarakoneshid) throws SQLException, ParseException {
        makeConnection();
        String sql = String.format("Select amtDateRtrn FROM amanat WHERE amtID = '%d' ", Integer.parseInt(amnttarakoneshid));
        System.out.println(sql);
        ResultSet rs = Database.getStatement().executeQuery(sql);
        rs.next();
        String amanatdatereturn = rs.getString("amtDateRtrn") ;
        rs.close();
        String mohlat = DateSC.mohlatTahvil(amanatdatereturn);
        return mohlat;
    }

    //گرفتن نام کامل کاربری خاص از جدول user با استفاده از آی دی آن کاربر
    public static String getUsrName(String usrID) throws SQLException {
        makeConnection();
        String sqlUsr = String.format("SELECT usrFName,usrLName FROM user WHERE usrID = '%s'", usrID);
        ResultSet resultSetUsr = Database.getStatement().executeQuery(sqlUsr);
        resultSetUsr.next();
        String usrFullName = resultSetUsr.getString("usrFName") + " " + resultSetUsr.getString("usrLName");
        resultSetUsr.close();
        return usrFullName;
    }

    //گرفتن آی دی تراکنشی خاص از جدول book دیتابیس(برای دسترسی به اطلاعات مربوط به امانت این کتاب در جدول amanat دیتابیس از طریق آی دی تراکنش)
    public static String getAmntTarakoneshID(String ktbID) throws SQLException {
        makeConnection();
        String sqlUsr = String.format("SELECT amtTarakoneshID FROM book WHERE ktbID = '%s' ", ktbID);

        System.out.println(sqlUsr);

        ResultSet resultSetUsr = Database.getStatement().executeQuery(sqlUsr);
        resultSetUsr.next();
        String amntID = resultSetUsr.getString("amtTarakoneshID");
        resultSetUsr.close();
        return amntID;
    }

    //آپدیت کردن اطلاعات مربوط به امانتی خاص در صورت ایجاد تغییر
    public static void updateAmanat(String amtID, int lable) throws SQLException {
        makeConnection();
        String sql = null;
        if (lable == 0) {
            sql = String.format("UPDATE amanat SET amtEmkanTamdid = '0', amtDarkhastUsr = '3' WHERE amtID = '%s'", amtID);
        } else if (lable == 1) {
            sql = String.format("UPDATE amanat SET amtEmkanTamdid = '0', amtDarkhastUsr = '1' WHERE amtID = '%s'", amtID);
        }
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    //گرفتن وضعیت درخواست تمدید کاربر(جهت عدم نمایش دکمه ی تمدید درصورتی که کاربر قبلا درخواست نمدید داده باشد)
    public static String getDarkhastTamdidStatus(String amtID) throws SQLException {
        makeConnection();
        String sql = String.format("select amtDarkhastUsr from amanat WHERE amtID = '%s'", amtID);
        System.out.println(sql);
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        resultSet.next();
        String amntdarkhast = resultSet.getString("amtDarkhastUsr");
        resultSet.close();
        closeConnection();
        return amntdarkhast;
    }

    //گرفتن وضعیت امکان تمدید کاربر(جهت عدم نمایش دکمه ی تمدید درصورتی که کاربر قبلا درخواست نمدید داده باشد)
    public static String getEmkanTamdidStatus(String amtID) throws SQLException {
        makeConnection();
        String sql = String.format("select amtEmkanTamdid from amanat WHERE amtID = '%s'", amtID);
        System.out.println(sql);
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        resultSet.next();
        String amntemkantamdid = resultSet.getString("amtEmkanTamdid");
        resultSet.close();
        closeConnection();
        return amntemkantamdid;
    }

    //آپدیت کردن اطلاعات مربوط به امانتی خاص در صورت ایجاد تغییر
    public static void updateAmanat(String amtID, String amtDateRtrn) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE amanat SET amtDateRtrn = '%s', amtEmkanTamdid = '0', amtDarkhastUsr = '2'" +
                " WHERE amtID = '%s'", amtDateRtrn, amtID);
        getStatement().executeUpdate(sql);
        closeConnection();
    }

    //گرفتن اطلاعات مربوط به امانت گیری کتابی خاص از طریق آی دی امانت
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

    public static void updateAdmin(Admin admin, String adminID) throws SQLException {
        makeConnection();
        String sql = String.format("UPDATE admin SET admFName = '%s', admLName = '%s' , admCodeMeli = '%s',admUserName ='%s'," +
                        " admPass = '%s' WHERE admID = '%s'", admin.getFirstName(), admin.getLastName(), admin.getCodeMeli(),
                admin.getUserName(), admin.getPassword(), adminID);
        getStatement().executeUpdate(sql);
        closeConnection();
    }
    //گرفتن تراکنش های اخیر کاربران از جدول amanat دیتابیس
    public static List getAmanatRecent(String typeAmanat) throws SQLException {
        makeConnection();
        ArrayList<Amanat> amanatRecent = new ArrayList<>();
        String sql;
        if (typeAmanat.equals("عودت")) {
            sql = String.format("SELECT * FROM amanat WHERE amtDarkhastUsr = '%s' ORDER BY amtID DESC LIMIT 4 ", typeAmanat);
        } else {
            sql = String.format("SELECT * FROM amanat WHERE NOT amtDarkhastUsr = '%s' ORDER BY amtID DESC LIMIT 4 ", "عودت");
        }
        ResultSet resultSet = Database.getStatement().executeQuery(sql);
        Amanat amanat;
        while (resultSet.next()) {
            amanat = new Amanat();
            amanat.setAmtID(resultSet.getString("amtID"));
            amanat.setUsrName(getUsrName(resultSet.getString("usrID")));
            amanat.setKtbName(getKtbName(resultSet.getString("ktbID")));
            amanatRecent.add(amanat);
        }
        ArrayList str = new ArrayList();
        for (Amanat amt : amanatRecent) {
            String strAmt;
            if (typeAmanat.equals("عودت")) {
                strAmt = amt.getUsrName() + " کتاب " + amt.getKtbName() + " را با شماره " + amt.getAmtID() + " عودت داد.";
            } else {
                strAmt = amt.getUsrName() + " کتاب " + amt.getKtbName() + " را با شماره " + amt.getAmtID() + " به امانت برد.";
            }
            str.add(strAmt);
        }
        return str;
    }

    public static String counter(String tableName) throws SQLException {
        makeConnection();
        String sql = String.format("SELECT COUNT(*) FROM %s ", tableName);
        ResultSet resultSet = getStatement().executeQuery(sql);
        resultSet.next();
        String num = resultSet.getString(1);
        closeConnection();
        return num;
    }

    public static String counter(String tableName, String feildName, String feildValue,String status,String valueStatus) throws SQLException {
        makeConnection();
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = '%s' AND %s = '%s' ",
                tableName, feildName, feildValue,status,valueStatus);
        ResultSet resultSet = getStatement().executeQuery(sql);
        resultSet.next();
        String num = resultSet.getString(1);
        closeConnection();
        return num;
    }
}


