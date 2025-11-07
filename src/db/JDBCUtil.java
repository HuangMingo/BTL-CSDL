package db;

import java.sql.*;

public class JDBCUtil {
    public static Connection getConnection() throws SQLException {
        Connection c = null;
        String url = "jdbc:mySQL://localhost:3306/EMR";
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        c = DriverManager.getConnection(url, "root", "minh1200");
        return c;
    }
    public static void closeConnection(Connection c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void printInfo(Connection c) throws SQLException {
        if(c != null){
            {
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.toString());
            }


        }
    }


}
