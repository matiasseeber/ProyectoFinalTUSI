package Database;

public class DataDB {

    /*
    Subject: Your Database is setup

    Hi

    Your account number is: 692968

    Your new database is now ready to use.

    To connect to your database use these details

    Server: sql10.freemysqlhosting.net
    Name: sql10553569
    Username: sql10553569
    Password: rQ4tK61kpY
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
    public static String nameBD="sql10553569";
    public static String user="sql10553569";
    public static String pass="rQ4tK61kpY";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}
