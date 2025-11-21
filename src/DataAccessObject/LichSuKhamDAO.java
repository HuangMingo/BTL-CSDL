package DataAccessObject;

import db.JDBCUtil;
import model.Doctor;
import model.LichSuKham;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class LichSuKhamDAO implements DAOInterface<LichSuKham> {

    public static LichSuKhamDAO getInstance() {
        return new LichSuKhamDAO();
    }

    @Override
    public int insert(LichSuKham lichSuKham) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO lichsukham (MaKham, MaBenhNhan, MaBacSi, NgayKham, ChanDoan) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, lichSuKham.getMaKham());
        ps.setString(2, lichSuKham.getBenhNhan().getMa_benh_nhan());
        ps.setString(3, lichSuKham.getBacSi().getMaBacSi());
        ps.setDate(4, new java.sql.Date(lichSuKham.getNgayKham().getTime()));
        ps.setString(5, lichSuKham.getChanDoan());
        int ans = ps.executeUpdate();
        JDBCUtil.closeConnection(con);
        return ans;
    }

    @Override
    public int update(LichSuKham lichSuKham) throws SQLException {
        int result = 0;
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE lichsukham SET MaBenhNhan = ?, MaBacSi = ?, NgayKham = ?, ChanDoan = ? WHERE MaKham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, lichSuKham.getBenhNhan().getMa_benh_nhan());
        ps.setString(2, lichSuKham.getBacSi().getMaBacSi());
        ps.setDate(3, new java.sql.Date(lichSuKham.getNgayKham().getTime()));
        ps.setString(4, lichSuKham.getChanDoan());
        ps.setString(5, lichSuKham.getMaKham());
        result = ps.executeUpdate();
        JDBCUtil.closeConnection(con);
        return result;
    }

    @Override
    public int delete(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "DELETE FROM lichsukham WHERE MaKham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        int result = ps.executeUpdate();
        System.out.println("Rows deleted: " + result);
        JDBCUtil.closeConnection(con);
        return result;
    }

    @Override
    public ArrayList<LichSuKham> selectAll() throws SQLException {
        ArrayList<LichSuKham> ans = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM lichsukham";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String maKham = rs.getString("MaKham");
            String maBenhNhan = rs.getString("MaBenhNhan");
            String maBacSi = rs.getString("MaBacSi");
            Date ngayKham = rs.getDate("NgayKham");
            String chanDoan = rs.getString("ChanDoan");
            BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
            BacSiDAO bacSiDAO = new BacSiDAO();
            LichSuKham lsk = new LichSuKham(maKham, benhNhanDAO.findWithId(maBenhNhan), bacSiDAO.findWithId(maBacSi), ngayKham, chanDoan);
            ans.add(lsk);
        }
        JDBCUtil.closeConnection(con);
        return ans;
    }

    @Override
    public LichSuKham selectById(LichSuKham lichSuKham) throws SQLException {
        LichSuKham result = null;
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM lichsukham WHERE MaKham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, lichSuKham.getMaKham());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String maKham = rs.getString("MaKham");
            String maBenhNhan = rs.getString("MaBenhNhan");
            String maBacSi = rs.getString("MaBacSi");
            Date ngayKham = rs.getDate("NgayKham");
            String chanDoan = rs.getString("ChanDoan");
            BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
            BacSiDAO bacSiDAO = new BacSiDAO();
            result = new LichSuKham(maKham, benhNhanDAO.findWithId(maBenhNhan), bacSiDAO.findWithId(maBacSi), ngayKham, chanDoan);
        }
        JDBCUtil.closeConnection(con);
        return result;
    }

    @Override
    public ArrayList<LichSuKham> selectByCondition(String condition) {
        ArrayList<LichSuKham> result = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM lichsukham WHERE " + condition;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String maKham = rs.getString("MaKham");
                String maBenhNhan = rs.getString("MaBenhNhan");
                String maBacSi = rs.getString("MaBacSi");
                Date ngayKham = rs.getDate("NgayKham");
                String chanDoan = rs.getString("ChanDoan");
                BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
                BacSiDAO bacSiDAO = new BacSiDAO();
                LichSuKham lsk = new LichSuKham(maKham, benhNhanDAO.findWithId(maBenhNhan), bacSiDAO.findWithId(maBacSi), ngayKham, chanDoan);
                result.add(lsk);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<LichSuKham> selectByName(String name) throws SQLException {
        return null;
    }

    @Override
    public LichSuKham findWithId(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM lichsukham WHERE MaKham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String maKham = rs.getString("MaKham");
            String maBenhNhan = rs.getString("MaBenhNhan");
            String maBacSi = rs.getString("MaBacSi");
            Date ngayKham = rs.getDate("NgayKham");
            String chanDoan = rs.getString("ChanDoan");
            if (maKham.equals(id)) {
                BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
                BacSiDAO bacSiDAO = new BacSiDAO();
                LichSuKham lsk = new LichSuKham(maKham, benhNhanDAO.findWithId(maBenhNhan), bacSiDAO.findWithId(maBacSi), ngayKham, chanDoan);
                JDBCUtil.closeConnection(con);
                return lsk;
            }
        }
        JDBCUtil.closeConnection(con);
        return null;
    }

    @Override
    public boolean existDuplicate(LichSuKham lichSuKham) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT COUNT(*) FROM lichsukham WHERE MaKham = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, lichSuKham.getMaKham());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            JDBCUtil.closeConnection(con);
            return count > 0;
        }
        JDBCUtil.closeConnection(con);
        return false;
    }
    public ArrayList<LichSuKham> selectRecordsByPatientId(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        ArrayList<LichSuKham> a = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM lichsukham l join bacsi d\n" +
                "ON l.MaBacSi = d.MaBacSi\n" +
                "JOIN benhnhan p\n" +
                "ON l.MaBenhNhan = p.MaBenhNhan\n" +
                "WHERE p.MaBenhNhan = '"+id+"'";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            String maKham = rs.getString("MaKham");
            Date ngayKham = rs.getDate("NgayKham");
            String maBacSi = rs.getString("MaBacSi");
            String doctorName = rs.getString("HoVaTen");
            String chanDoan = rs.getString("ChanDoan");
            String maBenhNhan = rs.getString("MaBenhNhan");
            BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
            Patient x = benhNhanDAO.findWithId(maBenhNhan);
            BacSiDAO bacSiDAO = new BacSiDAO();
            Doctor d = bacSiDAO.findWithId(maBacSi);
            a.add(new LichSuKham(maKham, x, d, ngayKham, chanDoan));
//            System.out.println(a.get(0).getMaKham());
        }
        return a;

    }

}