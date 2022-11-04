package Database;

public class DataDB {

    /*
    Server: sql10.freemysqlhosting.net
    Name: sql10529511
    Username: sql10529511
    Password: gZxWHg3eTC
    Port number: 3306
    */

    //Información de la BD
    public static String host="sql10.freemysqlhosting.net";
    public static String port="3306";
    public static String nameBD="sql10536614";
    public static String user="sql10536614";
    public static String pass="eIDjDSkSZw";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

}
