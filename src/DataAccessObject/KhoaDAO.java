package DataAccessObject;

import java.sql.SQLException;
import java.util.ArrayList;
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
        return null;
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
    public ArrayList<Khoa> selectByName(Khoa khoa) throws SQLException {
        return null;
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
