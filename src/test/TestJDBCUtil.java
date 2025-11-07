package test;

import db.JDBCUtil;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCUtil{
    public static void main(String[] args) throws SQLException {
//        B1: Tao ket noi
        Connection connection = JDBCUtil.getConnection();
//        B2: Tao ra doi tuong statement
        Statement st = connection.createStatement();
//        B3: Tao mot cau lenh sql
        String sql = "INSERT INTO benhnhan (ma_benh_nhan, ho_ten, ngay_sinh, gioi_tinh, cccd, so_dien_thoai, so_dien_thoai_ngh)\n" +
                "VALUES \n" +
                "('BN08', 'Đặng Thị F', '1988-09-18', 'Nữ', '456789012345', '0909888777', '0933999888');";
//        System.out.println(connection);
        int check = st.executeUpdate(sql);
        System.out.println("So dong thay doi" + check);
//        B5: Ngat ket noi
        JDBCUtil.closeConnection(connection);
//        System.out.println(connection);
    }

}
