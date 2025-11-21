package DataAccessObject;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAOInterface<T> {
    public static BenhNhanDAO getInstance(){
        return new BenhNhanDAO();
    }
    public  int insert(T t) throws SQLException;
    public  int update(T t) throws SQLException;
    public  int delete(String id) throws SQLException;
    public  ArrayList<T> selectAll () throws SQLException;
    public  T selectById(T t) throws SQLException;
    public  ArrayList<T> selectByCondition(String condition);
    public ArrayList<T> selectByName(String name) throws SQLException;
    public T findWithId(String id) throws  SQLException;
    public boolean existDuplicate(T t) throws SQLException;
}
