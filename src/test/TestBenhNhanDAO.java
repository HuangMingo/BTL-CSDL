package test;
import DataAccessObject.BenhNhanDAO;
import model.Patient;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestBenhNhanDAO {
    public static void main(String[] args) throws SQLException {
        ArrayList<Patient> a = BenhNhanDAO.getInstance().selectAll();
        for(Patient x : a)
            System.out.println(x.toString());
        System.out.println("-----------------");
        Patient find = new Patient();
        find.setMa_benh_nhan("BN003");
        Patient b2 = BenhNhanDAO.getInstance().selectById(find);
        System.out.println(b2.toString());
    }
}
