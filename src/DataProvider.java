import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataProvider {
    //Khai báo báo driver để làm việc với mysql
    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //Khai báo tên db cần làm việc
    private final static String DATABASE_LINK = "jdbc:mysql://localhost:3306/EMR_Database?useSSL=false";

    /**
     * Hàm kết nối đến db trong MySQL cần làm việc
     * @return
     */
    public static Connection ketNoi()
    {
        //Khai báo đối tượng kết nối
        Connection conn = null;

        try {

            //Nạp driver của mysql vào để sử dụng
            Class.forName(JDBC_DRIVER);

            //Thực hiện kết nối đến db
            conn = DriverManager.getConnection(DATABASE_LINK, "root", "minh1200");

        } catch (ClassNotFoundException ex) {
            System.err.println("Không tìm thấy driver. Chi tiết: " + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Không kết nối được đến MySQL. Chi tiết: " + ex.getMessage());
        }

        //Trả về kết nối
        return conn;
    }

}