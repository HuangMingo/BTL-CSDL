package DataAccessObject;

import java.sql.*;
import java.util.ArrayList;

import db.JDBCUtil;
import model.Khoa;
public class KhoaDAO implements DAOInterface<Khoa>{
    @Override
    public int insert(Khoa khoa) throws SQLException {
        return 0;
    }

    @Override
    public int update(Khoa khoa) throws SQLException {
        return 0;
    }

    @Override
    public int delete(String id) throws SQLException {
        return 0;
    }

    @Override
    public ArrayList<Khoa> selectAll() throws SQLException {
        ArrayList<Khoa> a = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT * FROM Khoa";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            String id = rs.getString("MaKhoa");
            String name = rs.getString("TenKhoa");
            a.add(new Khoa(id, name));
        }
        return a;

    }

    @Override
    public Khoa selectById(Khoa khoa) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Khoa> selectByCondition(String condition) {
        return null;
    }

    @Override
    public ArrayList<Khoa> selectByName(String name) throws SQLException {
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT *\n" +
                "FROM khoa\n" +
                "WHERE TenKhoa = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        Khoa khoa = null;
        ArrayList<Khoa> a = new ArrayList<>();
        while(rs.next()){
            String maKhoa = rs.getString("MaKhoa");
            String tenKhoa = rs.getString("TenKhoa");
            khoa = new Khoa(maKhoa, tenKhoa);
            a.add(khoa);
        }
        JDBCUtil.closeConnection(con);
        return a;

    }

    @Override
    public Khoa findWithId(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean existDuplicate(Khoa khoa) throws SQLException {
        return false;
    }
}
