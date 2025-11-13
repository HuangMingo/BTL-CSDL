package DataAccessObject;

import db.JDBCUtil;
import model.Doctor;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Khoa;

public class BacSiDAO implements DAOInterface<Doctor>{
    public int insert(Doctor d) throws SQLException {
//        B1: Tao ket noi toi CSDL
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO BacSi (MaBacSi, HoVaTen, SoNamKinhNghiem, SoDienThoai, MaKhoa) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, d.getMaBacSi());
        ps.setString(2, d.getHo_ten());
        ps.setInt(3, d.getSoNamKinhNghiem());
//        ps.setString(4, d.);
        int ans = ps.executeUpdate(sql);
        JDBCUtil.closeConnection(con);
        return 0;
    }@Override
    public int update(Doctor BacSi) {
        return 0;
    }

    @Override
    public int delete(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "DELETE FROM BacSi WHERE MaBacSi = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        int cnt = ps.executeUpdate();
        JDBCUtil.closeConnection(con);
        return cnt;
    }

    @Override
    public ArrayList<Doctor> selectAll() throws SQLException {
        Connection con = JDBCUtil.getConnection();
        ArrayList<Doctor> a = new ArrayList<>();
        String sql = "SELECT d.MaBacSi, d.HoVaTen, d.SoNamKinhNghiem, d.SoDienThoai, k.MaKhoa, k.TenKhoa\n" +
                "FROM bacsi d " +
                "join khoa k\n" +
                "on k.MaKhoa = d.MaKhoa";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("MaBacSi");
            String name = rs.getString("HoVaTen");
            int soNamKinhnghiem = rs.getInt("SoNamKinhNghiem");
            String sdt = rs.getString("SoDienThoai");
            Khoa chuyenKhoa = new Khoa(rs.getString("MaKhoa"), rs.getString("TenKhoa"));
            a.add(new Doctor(id, name, sdt, soNamKinhnghiem, chuyenKhoa));
        }
        return a;
    }

    @Override
    public Doctor selectById(Doctor bacSi) {
        return null;
    }

    @Override
    public ArrayList<Doctor> selectByCondition(String condition) {
        return null;
    }

    @Override
    public ArrayList<Doctor> selectByName(Doctor doctor) throws SQLException {
        return null;
    }

    @Override
    public Doctor findWithId(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean existDuplicate(Doctor doctor) throws SQLException {
        return false;
    }
}
