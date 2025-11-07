package DataAccessObject;
import db.JDBCUtil;
import model.Patient;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class BenhNhanDAO implements DAOInterface<Patient> {
    public static BenhNhanDAO getInstance(){
        return new BenhNhanDAO();
    }

    @Override
    public int insert(Patient b) throws SQLException {
//        B1: Tao ket noi toi CSDL
        Connection con = JDBCUtil.getConnection();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "INSERT INTO benhnhan (MaBenhNhan, HoVaTen, NgaySinh, GioiTinh, CCCD, SoDienThoai, SoDienThoaiNGH) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, b.getMa_benh_nhan());
        ps.setString(2, b.getHo_ten());
        ps.setDate(3, new java.sql.Date(b.getNgaySinh().getTime()));
        ps.setString(4, b.getGioiTinh());
        ps.setString(5, b.getCccd());
        ps.setString(6, b.getSo_dien_thoai());
        ps.setString(7, b.getSo_dien_thoai_ngh());
        int ans = ps.executeUpdate();
        JDBCUtil.closeConnection(con);
        return ans;
    }

    @Override
    public int update(Patient benhNhan) throws SQLException {
        int result = 0;
        Connection con = JDBCUtil.getConnection();
        String sql = "UPDATE BenhNhan "
                + "SET NgaySinh = ?, GioiTinh = ?, HoVaTen = ?,"
                + "CCCD = ?, SoDienThoai = ?, SoDienThoaiNGH = ?, DiaChi = ? "
                + "WHERE MaBenhNhan = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(benhNhan.getNgaySinh().getTime()));
        ps.setString(2, benhNhan.getGioiTinh());
        ps.setString(3, benhNhan.getHo_ten());
        ps.setString(4, benhNhan.getCccd());
        ps.setString(5, benhNhan.getSo_dien_thoai());
        ps.setString(6, benhNhan.getSo_dien_thoai_ngh());
        ps.setString(7, benhNhan.getDiaChi());
        ps.setString(8, benhNhan.getMa_benh_nhan());
        result = ps.executeUpdate();
        JDBCUtil.closeConnection(con);
        return result;
    }

    @Override
    public int delete(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "DELETE FROM BenhNhan\n" +
                "WHERE MaBenhNhan = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        int result = ps.executeUpdate();
        System.out.println("Rows deleted: " + result);
        JDBCUtil.closeConnection(con);
        return result;
    }

    @Override
    public ArrayList<Patient> selectAll() throws SQLException {
        ArrayList<Patient> ans = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM BenhNhan";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("MaBenhNhan");
            String hoten = rs.getString("HoVaTen");
            Date ngaySinh = rs.getDate("NgaySinh");
            String gioiTinh = rs.getString("GioiTinh");
            String cccd = rs.getString("CCCD");
            String sdt = rs.getString("SoDienThoai");
            String sdtngh = rs.getString("SoDienThoaiNGH");
            String diachi = rs.getString("DiaChi");
            Patient b = new Patient(id, hoten, ngaySinh, gioiTinh, cccd, sdt, sdtngh, diachi);
            ans.add(b);
        }
        return ans;
    }

    @Override
    public Patient selectById(Patient benhNhan) throws SQLException {
        Patient b = new Patient();
        Connection con = JDBCUtil.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM BenhNhan where ma_benh_nhan = '"+benhNhan.getMa_benh_nhan()+"'";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("ma_benh_nhan");
            String hoten = rs.getString("ho_ten");
            Date ngaySinh = rs.getDate("ngay_sinh");
            String gioiTinh = rs.getString("gioi_tinh");
            String cccd = rs.getString("cccd");
            String sdt = rs.getString("so_dien_thoai");
            String sdtngh = rs.getString("so_dien_thoai_ngh");
            String diachi = rs.getString("diachi");
            b = new Patient(id, hoten, ngaySinh, gioiTinh, cccd, sdt, sdtngh, diachi);
        }
        return b;
    }

    @Override
    public ArrayList<Patient> selectByCondition(String condition) {return null;}
    public ArrayList<Patient> selectByName(Patient benhNhan) throws SQLException {
        ArrayList<Patient> b = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM BenhNhan where ho_ten = '"+benhNhan.getHo_ten()+"'";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("ma_benh_nhan");
            String hoten = rs.getString("ho_ten");
            Date ngaySinh = rs.getDate("ngay_sinh");
            String gioiTinh = rs.getString("gioi_tinh");
            String cccd = rs.getString("cccd");
            String sdt = rs.getString("so_dien_thoai");
            String sdtngh = rs.getString("so_dien_thoai_ngh");
            String diachi = rs.getString("diachi");
            b.add(new Patient(id, hoten, ngaySinh, gioiTinh, cccd, sdt, sdtngh, diachi));
        }
        return b;
    }
    public Patient findWithId(String id) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM BenhNhan WHERE MaBenhNhan = '"+id+"'";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String ma_benh_nhan = rs.getString("MaBenhNhan");
            String hoten = rs.getString("HoVaTen");
            Date ngaySinh = rs.getDate("NgaySinh");
            String gioiTinh = rs.getString("GioiTinh");
            String cccd = rs.getString("cccd");
            String sdt = rs.getString("SoDienThoai");
            String sdtngh = rs.getString("SoDienThoaiNGH");
            String diachi = rs.getString("DiaChi");
            if(ma_benh_nhan.compareTo(id) == 0) {
                Patient b = new Patient(ma_benh_nhan, hoten, ngaySinh, gioiTinh, cccd, sdt, sdtngh, diachi);
                return b;
            }
        }
        return null;

    }

    @Override
    public boolean existDuplicate(Patient patient) throws SQLException {
        return false;
    }
}
