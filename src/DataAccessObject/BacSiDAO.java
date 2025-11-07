//package DataAccessObject;
//
//import db.JDBCUtil;
//import model.BacSi;
//import model.BacSi;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//
//public class BacSiDAO implements DAOInterface<BacSi>{
//    public int insert(BacSi b) throws SQLException {
////        B1: Tao ket noi toi CSDL
//        Connection con = JDBCUtil.getCollection();
//        Statement st = con.createStatement();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String sql = "INSERT INTO BacSi (ma_benh_nhan, ho_ten, ngay_sinh, gioi_tinh, cccd, so_dien_thoai, so_dien_thoai_ngh) VALUES (" +
//                "'" + b.getMaBacSi() + "', " +
//                "'" + b.getHo_ten() + "', " +
//                "'" + b.getEmail() + "'" +
//                ");";
//        int ans = st.executeUpdate(sql);
//        JDBCUtil.closeCollection(con);
//        return 0;
//    }@Override
//    public int update(BacSi BacSi) {
//        return 0;
//    }
//
//    @Override
//    public int delete(BacSi BacSi) {
//        return 0;
//    }
//
//    @Override
//    public ArrayList<BacSi> selectAll() {
//        return null;
//    }
//
//    @Override
//    public BacSi selectById(BacSi bacSi) {
//        return null;
//    }
//
//    @Override
//    public ArrayList<BacSi> selectByCondition(String condition) {
//        return null;
//    }
//
//
//}
