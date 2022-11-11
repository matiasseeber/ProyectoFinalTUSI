package Database;

public class DataDB {

    /*
   From: bounces+2831340-016d-jybvphtnlvtoiruvcw=tmmbt.com@email.freemysqlhosting.net

    Subject: Your Database is setup

    Hi

    Your account number is: 702699

    Your new database is now ready to use.

    To connect to your database use these details

    Server: sql10.freemysqlhosting.net
    Name: sql10562334
    Username: sql10562334
    Password: IfK61l2ynj
    Port number: 3306

    phpMyAdmin
    phpMyAdmin is now available to administer your database. phpMyAdmin will allow you to create, edit and remove tables and to back up and import your data. Follow this link http://www.phpmyadmin.co and use the database details above to get started with your new database.

    If you have any questions or problems, please reply to this email detailing your message.

    Do you want to host your own website? Fast and affordable. Includes PHP, MySQL, Email and cPanel. Click here to find out more

    Many thanks
    FreeMySQLhosting.net

    */

    //Información de la BD
    public static String host="sql10.freemysqlhosting.net";
    public static String port="3306";
    public static String nameBD="sql10562334";
    public static String user="sql10562334";
    public static String pass="IfK61l2ynj";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}
