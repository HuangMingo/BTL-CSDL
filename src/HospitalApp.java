import DataAccessObject.BacSiDAO;
import DataAccessObject.BenhNhanDAO;
import DataAccessObject.KhoaDAO;
import DataAccessObject.LichSuKhamDAO;
import db.JDBCUtil;
import model.Khoa;
import model.LichSuKham;
import model.Patient;
import model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * HospitalApp.java
 * Một ứng dụng Swing đơn-file mô phỏng hệ thống quản lý bệnh viện theo sơ đồ ERD.
 * - Không dùng CSDL, dữ liệu lưu trong bộ nhớ (ArrayList).
 * - TabbedPane cho từng thực thể: Bệnh nhân, Bác sĩ, Lịch sử khám, Hồ sơ bệnh án, Dịch vụ, Thuốc, Đơn thuốc.
 *
 * Dán file này vào NetBeans (class public tên HospitalApp) và chạy.
 */
public class HospitalApp extends JFrame {

    // Models (in-memory)
//    private java.util.List<Person> people = new ArrayList<>();
    private java.util.List<Patient> patients = new ArrayList<>();
    private java.util.List<Doctor> doctors = new ArrayList<>();
    private java.util.List<ServiceItem> services = new ArrayList<>();
    private java.util.List<MedicineType> medicineTypes = new ArrayList<>();
    private java.util.List<Prescription> prescriptions = new ArrayList<>();
    private java.util.List<ExamRecord> examRecords = new ArrayList<>();
    private java.util.List<MedicalRecord> medicalRecords = new ArrayList<>();

    // UI components
    private JTabbedPane tabs;

    // Table models
    private DefaultTableModel patientTableModel;
    private DefaultTableModel doctorTableModel;
    private DefaultTableModel examTableModel;
    private DefaultTableModel medRecordTableModel;
    private DefaultTableModel serviceTableModel;
    private DefaultTableModel medicineTypeTableModel;
    private DefaultTableModel prescriptionTableModel;
    private DefaultTableModel recordTableModel;

    // Date format
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public HospitalApp() throws SQLException {
        super("Hospital Management (Demo - In-memory) ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        initSampleData();
        initUI();
    }

    private void initUI() throws SQLException {
        tabs = new JTabbedPane();

        tabs.addTab("Bệnh nhân", buildPatientPanel());
        tabs.addTab("Bác sĩ", buildDoctorPanel());
//        tabs.addTab("Lịch sử khám", buildExamPanel());
//        tabs.addTab("Hồ sơ bệnh án", buildMedicalRecordPanel());
//        tabs.addTab("Dịch vụ y tế", buildServicePanel());
//        tabs.addTab("Loại thuốc", buildMedicineTypePanel());
//        tabs.addTab("Đơn thuốc", buildPrescriptionPanel());

        add(tabs, BorderLayout.CENTER);

        // top info
        JLabel info = new JLabel("Demo: in-memory CRUD UI — chạy trên NetBeans. Dữ liệu không được lưu khi đóng.");
        add(info, BorderLayout.SOUTH);
    }
            //Validate

    public static boolean validatePatientInfo(Patient pa){
        boolean check = true;
        if(pa.getSo_dien_thoai().length() != 10 && pa.getSo_dien_thoai().length() != 0) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
            check = false;
        }
        if(pa.getSo_dien_thoai_ngh().length() != 10 && pa.getSo_dien_thoai_ngh().length() != 0) {
            JOptionPane.showMessageDialog(null, "Số điện thoại NGH không hợp lệ");
            check = false;
        }
        if(pa.getCccd().length() != 12 && pa.getCccd().length() != 0) {
            JOptionPane.showMessageDialog(null, "Số CCCD không hợp lệ");
            check = false;
        }

//        if(pa.getMa_benh_nhan().length() != 5 && !pa.getMa_benh_nhan().substring(0, 2).equals("BS")) {
//            JOptionPane.showMessageDialog(null, "Mã bệnh nhân không hợp lệ");
//            check = false;
//        }
        return check;
    }
//    Kiểm tra hợp lệ bác sĩ
    public static boolean validateDoctorInfo(Doctor d){
        boolean check = true;
        if(d.getSo_dien_thoai().length() != 10 && d.getSo_dien_thoai().length() != 0) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
            check = false;
        }
//        for(int i = 2; i < d.getMaBacSi().length(); i++){
//            if(!Character.isDigit(d.getMaBacSi().charAt(i))){
//                JOptionPane.showMessageDialog(null, "Mã bác sĩ không hợp lệ");
//                check = false;
//                break;
//            }
//        }
//        if(d.getMaBacSi().length() != 5 || !d.getMaBacSi().substring(0, 2).equals("BS")) {
//            JOptionPane.showMessageDialog(null, "Mã bác sĩ không hợp lệ");
//            check = false;
//        }
        return check;
    }
    private JPanel buildPatientPanel() throws SQLException {
        JPanel p = new JPanel(new BorderLayout());
        String[] columns = {"Mã bệnh nhân", "Họ tên", "CCCD", "Ngày sinh", "Giới tính", "Địa chỉ", "SĐT", "SĐT NGH"};
        patientTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(patientTableModel);
        BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
        refreshPatientTable(benhNhanDAO.selectAll());

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JTextField searchField = new JTextField(15); // Chiều rộng 15 cột
        JLabel searchLabel = new JLabel("Họ tên:");
        JButton addBtn = new JButton("Thêm");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");
        JButton viewRecordsBtn = new JButton("Hồ sơ bệnh án");
        JButton findBtn = new JButton("Tìm kiếm");
        JButton resetBtn = new JButton("Reset");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(viewRecordsBtn);


        // Thêm trường tìm kiếm và nút tìm kiếm
        btnPanel.add(searchLabel);
        btnPanel.add(searchField);
        btnPanel.add(findBtn);
        btnPanel.add(resetBtn);

        p.add(btnPanel, BorderLayout.NORTH);
        resetBtn.addActionListener(e ->{
            try {
                refreshPatientTable(benhNhanDAO.selectAll());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        addBtn.addActionListener(e -> {
            PatientFormDialog d = new PatientFormDialog(this, null);
            d.setVisible(true);
            if (d.saved) {
                System.out.println("Đang thêm bệnh nhân");
                try {
                    benhNhanDAO.insert(d.patient);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    refreshPatientTable(benhNhanDAO.selectAll());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 bệnh nhân để sửa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            Patient ptn = null;
            try {
                ptn = benhNhanDAO.findWithId(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            PatientFormDialog d = new PatientFormDialog(this, ptn);
            d.setVisible(true);
            if (d.saved) {
                try {
                    benhNhanDAO.update(d.patient);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (ptn == null) return;
                try {
                    refreshPatientTable(benhNhanDAO.selectAll());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 bệnh nhân để xóa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            System.out.println("ID được chọn: " + id);
            Patient ptn = null;
            try {
                ptn = benhNhanDAO.findWithId(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (ptn != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xác nhận xóa bệnh nhân " + ptn.getHo_ten() + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    patients.remove(ptn);
                    try {
                        int x = benhNhanDAO.delete(id);
                        System.out.println(x);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    examRecords.removeIf(er -> er.patientId.equals(id));
                    medicalRecords.removeIf(mr -> mr.patientId.equals(id));
                    try {
                        refreshAllTables();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        viewRecordsBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 bệnh nhân để xem hồ sơ.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            String name = (String) table.getValueAt(r, 1);
            String cccd = (String) table.getValueAt(r, 2);
            java.sql.Date ngaySinh = (java.sql.Date) table.getValueAt(r, 3);
            String gender = (String)table.getValueAt(r, 4);
            String addr = (String)table.getValueAt(r, 5);
            String sdt = (String)table.getValueAt(r, 6);
            String sdtngh = (String)table.getValueAt(r, 7);
            Patient patient = new Patient(id, name, ngaySinh, gender, cccd, sdt, sdtngh, addr);
            RecordPatientForm rpf = null;
            try {
                rpf = new RecordPatientForm(this, patient);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            rpf.setVisible(true);
        });
        findBtn.addActionListener(e ->{
            String s = searchField.getText();
            if(s.equals(""))
                JOptionPane.showMessageDialog(this,"Nhập họ tên bệnh nhân");
            try {
                ArrayList<Patient> a = benhNhanDAO.selectByName(s);
                refreshPatientTable(a);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        return p;
    }

    private JPanel buildDoctorPanel() throws SQLException {
        JPanel p = new JPanel(new BorderLayout());
        BacSiDAO bacSiDAO = new BacSiDAO();
        String[] cols = {"Mã bác sĩ", "Họ tên", "Số năm kinh nghiệm", "SĐT", "Chuyên khoa"};
        doctorTableModel = new DefaultTableModel(cols, 100);
        JTable table = new JTable(doctorTableModel);
        refreshDoctorTable(bacSiDAO.selectAll());
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        JTextField searchField = new JTextField(15); // Chiều rộng 15 cột
        JLabel searchLabel = new JLabel("Họ tên:");
        JButton addBtn = new JButton("Thêm");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");
        JButton findBtn = new JButton("Tìm kiếm");
        JButton resetBtn = new JButton("Reset");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(findBtn);
        btnPanel.add(searchLabel);
        btnPanel.add(searchField);
        btnPanel.add(findBtn);
        btnPanel.add(resetBtn);

        p.add(btnPanel, BorderLayout.NORTH);
        resetBtn.addActionListener( e ->{
            try {
                refreshDoctorTable(bacSiDAO.selectAll());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addBtn.addActionListener(e -> {
            DoctorFormDialog d = null;
            try {
                d = new DoctorFormDialog(this, null);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            d.setVisible(true);
            if (d.saved) {

                    try {
                        bacSiDAO.insert(d.doctor);
                        refreshDoctorTable(bacSiDAO.selectAll());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });

        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 bác sĩ để sửa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            String name = (String) table.getValueAt(r, 1);
            int nam = (int) table.getValueAt(r, 2);
            String sdt = (String) table.getValueAt(r, 3);
            String chuyenKhoa = (String) table.getValueAt(r, 4);
            KhoaDAO khoaDAO = new KhoaDAO();
            Doctor doc = null;
            try {
                doc = new Doctor(id, name, sdt, nam, khoaDAO.selectByName(chuyenKhoa).get(0));
                DoctorFormDialog d = new DoctorFormDialog(this, doc);
                d.setVisible(true);
                if (d.saved) {
                    try {
                        int x = bacSiDAO.update(d.doctor);
                        refreshDoctorTable(bacSiDAO.selectAll());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (doc == null) return;


        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 bác sĩ để xóa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            if (id != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa bác sĩ " + (String) table.getValueAt(r, 1) + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    // remove related assignments
//                    examRecords.forEach(er -> {
//                        if (er.doctorId.equals(doc.getMaBacSi())) er.doctorId = "";
//                    });
//                    prescriptions.forEach(pr -> {
//                        if (pr.doctorId.equals(doc.getMaBacSi())) pr.doctorId = "";
//                    });
                    try {
                        int x = bacSiDAO.delete(id);
                        refreshDoctorTable(bacSiDAO.selectAll());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        findBtn.addActionListener(e ->{
            String s = searchField.getText();
            if(s.equals(""))
                JOptionPane.showMessageDialog(this,"Nhập họ tên bác sĩ");
            try {
                ArrayList<Doctor> a = bacSiDAO.selectByName(s);
                refreshDoctorTable(a);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        return p;
    }

    private JPanel buildExamPanel() {
        JPanel p = new JPanel(new BorderLayout());

        String[] cols = {"Mã LS", "Mã bệnh nhân", "Bệnh nhân", "Mã bác sĩ", "Bác sĩ", "Ngày giờ", "Chẩn đoán", "Hồ sơ liên kết"};
        examTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(examTableModel);
//        refreshExamTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm lịch khám");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");
        JButton toPrescriptionBtn = new JButton("Tạo đơn thuốc");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(toPrescriptionBtn);

        p.add(btnPanel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            ExamFormDialog d = new ExamFormDialog(this, null);
            d.setVisible(true);
            if (d.saved) {
                examRecords.add(d.record);
//                refreshExamTable();
            }
        });

        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 lịch khám để sửa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            ExamRecord er = findExamById(id);
            if (er == null) return;
            ExamFormDialog d = new ExamFormDialog(this, er);
            d.setVisible(true);
//            if (d.saved)
//                refreshExamTable();
        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Chọn 1 lịch khám để xóa.");
                return;
            }
            String id = (String) table.getValueAt(r, 0);
            ExamRecord er = findExamById(id);
            if (er != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa lịch khám " + id + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    examRecords.remove(er);
//                    refreshExamTable();
                }
            }
        });

        toPrescriptionBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 lịch khám để tạo đơn thuốc."); return; }
            String id = (String) table.getValueAt(r, 0);
            ExamRecord er = findExamById(id);
            if (er == null) return;
            PrescriptionFormDialog d = new PrescriptionFormDialog(this, null, er.patientId, er.doctorId);
            d.setVisible(true);
            if (d.saved) {
                prescriptions.add(d.prescription);
                refreshPrescriptionTable();
            }
        });

        return p;
    }

    private JPanel buildMedicalRecordPanel() {
        JPanel p = new JPanel(new BorderLayout());

        String[] cols = {"Mã hồ sơ", "Mã bệnh nhân", "Tên bệnh nhân", "Chi tiết"};
        medRecordTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(medRecordTableModel);
        refreshMedicalRecordTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm hồ sơ");
        JButton viewBtn = new JButton("Xem");
        JButton delBtn = new JButton("Xóa");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(delBtn);

        p.add(btnPanel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            MedicalRecordFormDialog d = new MedicalRecordFormDialog(this, null);
            d.setVisible(true);
            if (d.saved) {
                medicalRecords.add(d.record);
                refreshMedicalRecordTable();
            }
        });

        viewBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 hồ sơ để xem."); return; }
            String id = (String) table.getValueAt(r, 0);
            MedicalRecord mr = findMedicalRecordById(id);
            if (mr != null) {
                JOptionPane.showMessageDialog(this, "Hồ sơ: " + mr.recordId + "\nMã BN: " + mr.patientId + "\nChi tiết:\n" + mr.details);
            }
        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 hồ sơ để xóa."); return; }
            String id = (String) table.getValueAt(r, 0);
            MedicalRecord mr = findMedicalRecordById(id);
            if (mr != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa hồ sơ " + id + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    medicalRecords.remove(mr);
                    refreshMedicalRecordTable();
                }
            }
        });

        return p;
    }

    private JPanel buildServicePanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"Mã dịch vụ", "Tên dịch vụ", "Chi phí"};
        serviceTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(serviceTableModel);
        refreshServiceTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm dịch vụ");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);

        p.add(btnPanel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            ServiceFormDialog d = new ServiceFormDialog(this, null);
            d.setVisible(true);
            if (d.saved) {
                services.add(d.serviceItem);
                refreshServiceTable();
            }
        });
        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn dịch vụ để sửa."); return; }
            String id = (String) table.getValueAt(r, 0);
            ServiceItem si = findServiceById(id);
            if (si != null) {
                ServiceFormDialog d = new ServiceFormDialog(this, si);
                d.setVisible(true);
                if (d.saved) refreshServiceTable();
            }
        });
        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn dịch vụ để xóa."); return; }
            String id = (String) table.getValueAt(r, 0);
            ServiceItem si = findServiceById(id);
            if (si != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa dịch vụ " + si.name + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    services.remove(si);
                    refreshServiceTable();
                }
            }
        });

        return p;
    }

    private JPanel buildMedicineTypePanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"Mã thuốc", "Tên thuốc", "Nhà sản xuất"};
        medicineTypeTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(medicineTypeTableModel);
        refreshMedicineTypeTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Thêm thuốc");
        JButton editBtn = new JButton("Sửa");
        JButton delBtn = new JButton("Xóa");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);

        p.add(btnPanel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            MedicineFormDialog d = new MedicineFormDialog(this, null);
            d.setVisible(true);
            if (d.saved) {
                medicineTypes.add(d.type);
                refreshMedicineTypeTable();
            }
        });
        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn thuốc để sửa."); return; }
            String id = (String) table.getValueAt(r, 0);
            MedicineType mt = findMedicineById(id);
            if (mt != null) {
                MedicineFormDialog d = new MedicineFormDialog(this, mt);
                d.setVisible(true);
                if (d.saved) refreshMedicineTypeTable();
            }
        });
        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn thuốc để xóa."); return; }
            String id = (String) table.getValueAt(r, 0);
            MedicineType mt = findMedicineById(id);
            if (mt != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa thuốc " + mt.name + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    medicineTypes.remove(mt);
                    refreshMedicineTypeTable();
                }
            }
        });

        return p;
    }

    private JPanel buildPrescriptionPanel() {
        JPanel p = new JPanel(new BorderLayout());
        String[] cols = {"Mã đơn", "Mã BN", "Bệnh nhân", "Mã BS", "Bác sĩ", "Ngày", "Nội dung (thuốc)"};
        prescriptionTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(prescriptionTableModel);
        refreshPrescriptionTable();

        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Tạo đơn");
        JButton viewBtn = new JButton("Xem");
        JButton delBtn = new JButton("Xóa");

        btnPanel.add(addBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(delBtn);

        p.add(btnPanel, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            PrescriptionFormDialog d = new PrescriptionFormDialog(this, null, null, null);
            d.setVisible(true);
            if (d.saved) {
                prescriptions.add(d.prescription);
                refreshPrescriptionTable();
            }
        });

        viewBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 đơn để xem."); return; }
            String id = (String) table.getValueAt(r, 0);
            Prescription pr = findPrescriptionById(id);
            if (pr != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Mã đơn: ").append(pr.prescriptionId).append("\n");
                sb.append("Bệnh nhân: ").append(pr.patientId).append(" - ").append("\n");
                sb.append("Bác sĩ: ").append(pr.doctorId).append(" - ").append("\n");
                sb.append("Ngày: ").append(pr.dateTime).append("\n");
                sb.append("Thuốc:\n");
                for (Prescription.MedItem mi : pr.items) {
                    sb.append("- ").append(mi.medicineId).append(" | ").append(findMedicineName(mi.medicineId)).append(" | Số lượng: ").append(mi.qty).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        });

        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 đơn để xóa."); return; }
            String id = (String) table.getValueAt(r, 0);
            Prescription pr = findPrescriptionById(id);
            if (pr != null) {
                int ok = JOptionPane.showConfirmDialog(this, "Xóa đơn " + id + " ?");
                if (ok == JOptionPane.YES_OPTION) {
                    prescriptions.remove(pr);
                    refreshPrescriptionTable();
                }
            }
        });

        return p;
    }

    // ---------- Refresh helpers ----------
    private void refreshAllTables() throws SQLException {
        BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
        refreshPatientTable(benhNhanDAO.selectAll());

        BacSiDAO bacSiDAO = new BacSiDAO();
        refreshDoctorTable(bacSiDAO.selectAll());
//        refreshExamTable();
        refreshMedicalRecordTable();
        refreshServiceTable();
        refreshMedicineTypeTable();
        refreshPrescriptionTable();
    }

    private void refreshPatientTable(ArrayList<Patient> a) throws SQLException {
        patientTableModel.setRowCount(0);
        BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
        for (Patient p : a) {
            patientTableModel.addRow(new Object[]{
                    p.getMa_benh_nhan(), p.getHo_ten(), p.getCccd(), p.getNgaySinh(), p.getGioiTinh(), p.getDiaChi(), p.getSo_dien_thoai(), p.getSo_dien_thoai_ngh()
            });
        }
    }

    private void refreshDoctorTable(ArrayList<Doctor> a) throws SQLException {
        doctorTableModel.setRowCount(0);
        for(model.Doctor d : a)
            doctorTableModel.addRow(new Object[]{
                    d.getMaBacSi(), d.getHo_ten(), d.getSoNamKinhNghiem(), d.getSo_dien_thoai(), d.getChuyen_khoa().getTen()
            });
    }
    private void refreshRecordPatient(ArrayList<LichSuKham> a) throws SQLException {
        recordTableModel.setRowCount(0);
        System.out.println(a.size());
        for(model.LichSuKham lsk : a) {
            recordTableModel.addRow(new Object[]{
                    lsk.getNgayKham(), lsk.getBacSi().getMaBacSi(), lsk.getBacSi().getHo_ten(), lsk.getChanDoan()
            });
        }
    }


    private void refreshMedicalRecordTable() {
        medRecordTableModel.setRowCount(0);
        for (MedicalRecord mr : medicalRecords) {
            medRecordTableModel.addRow(new Object[]{mr.recordId, mr.patientId, mr.details});
        }
    }

    private void refreshServiceTable() {
        serviceTableModel.setRowCount(0);
        for (ServiceItem si : services) {
            serviceTableModel.addRow(new Object[]{si.serviceId, si.name, si.cost});
        }
    }

    private void refreshMedicineTypeTable() {
        medicineTypeTableModel.setRowCount(0);
        for (MedicineType mt : medicineTypes) {
            medicineTypeTableModel.addRow(new Object[]{mt.medicineId, mt.name, mt.manufacturer});
        }
    }

    private void refreshPrescriptionTable() {
        prescriptionTableModel.setRowCount(0);
        for (Prescription pr : prescriptions) {
            String content = "";
            for (Prescription.MedItem mi : pr.items) {
                content += mi.medicineId + " x" + mi.qty + "; ";
            }
            prescriptionTableModel.addRow(new Object[]{pr.prescriptionId, pr.patientId, pr.doctorId, findDoctorName(pr.doctorId), pr.dateTime, content});
        }
    }

    // ---------- Find helpers ----------

    private Doctor findDoctorById(String id) {
        for (Doctor d : doctors) if (d.getMaBacSi().equals(id)) return d;
        return null;
    }

    private ServiceItem findServiceById(String id) {
        for (ServiceItem s : services) if (s.serviceId.equals(id)) return s;
        return null;
    }

    private MedicineType findMedicineById(String id) {
        for (MedicineType m : medicineTypes) if (m.medicineId.equals(id)) return m;
        return null;
    }

    private ExamRecord findExamById(String id) {
        for (ExamRecord e : examRecords) if (e.recordId.equals(id)) return e;
        return null;
    }

    private MedicalRecord findMedicalRecordById(String id) {
        for (MedicalRecord m : medicalRecords) if (m.recordId.equals(id)) return m;
        return null;
    }

    private Prescription findPrescriptionById(String id) {
        for (Prescription p : prescriptions) if (p.prescriptionId.equals(id)) return p;
        return null;
    }

    private String findPatientName(String id) throws SQLException {
        BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
        Patient p = benhNhanDAO.findWithId(id);
        return p == null ? "" : p.getHo_ten();
    }

    private String findDoctorName(String id) {
        Doctor d = findDoctorById(id);
        return d == null ? "" : d.getHo_ten();
    }

    private String findMedicineName(String id) {
        MedicineType m = findMedicineById(id);
        return m == null ? "" : m.name;
    }

    private void showMedicalRecordsForPatient(String patientId) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hồ sơ cho bệnh nhân: ").append("\n\n");
//        for (MedicalRecord mr : medicalRecords) {
//            if (mr.patientId.equals(patientId)) {
//                sb.append("- ").append(mr.recordId).append(": ").append(mr.details).append("\n");
//            }
//        }
//        JOptionPane.showMessageDialog(this, sb.length() == 0 ? "Không có hồ sơ." : sb.toString());
    }

    // ---------- Sample data ----------
    private void initSampleData() throws SQLException {
        BenhNhanDAO patientDAO = new BenhNhanDAO();
        patients = patientDAO.selectAll();
    }

    // ---------- Data classes ----------


    static class ServiceItem {
        String serviceId;
        String name;
        double cost;
//        String maKham;
        ServiceItem(String sid, String name, double cost) {
            this.serviceId = sid;
            this.name = name;
            this.cost = cost;
//            this.maKham = maKham;
        }
    }

    static class MedicineType {
        String medicineId;
        String name;
        String manufacturer;
        MedicineType(String id, String name, String mfr) {
            this.medicineId = id;
            this.name = name;
            this.manufacturer = mfr;
        }
    }

    static class ExamRecord {
        String recordId;
        String patientId;
        String doctorId;
        String dateTime;
        String diagnose;
        String medicalRecordId; // liên kết tới hồ sơ nếu có
        ExamRecord(String rid, String pid, String did, String dateTime, String diag, String medRec) {
            this.recordId = rid; this.patientId = pid; this.doctorId = did; this.dateTime = dateTime; this.diagnose = diag; this.medicalRecordId = medRec;
        }
    }

    static class MedicalRecord {
        String recordId;
        String patientId;
        String details;
        String summary;
        MedicalRecord(String id, String pid, String details, String summary) { this.recordId = id; this.patientId = pid; this.details = details; this.summary = summary; }
    }

    static class Prescription {
        String prescriptionId;
        String patientId;
        String doctorId;
        String dateTime;
        java.util.List<MedItem> items = new ArrayList<>();
        Prescription(String id, String pid, String did, String dateTime) { this.prescriptionId = id; this.patientId = pid; this.doctorId = did; this.dateTime = dateTime; }
        static class MedItem {
            String medicineId;
            int qty;
            MedItem(String mid, int q) { this.medicineId = mid; this.qty = q; }
        }
    }

    // ---------- Dialogs (forms) ----------
    // Patient form
    class PatientFormDialog extends JDialog {
        boolean saved = false;
        Patient patient;
        private JTextField idField, nameField, dobField, genderField, addrField, phoneField, phoneParentField, cccdField;

        PatientFormDialog(Frame owner, Patient existing) {
            super(owner, true);
            setTitle(existing == null ? "Thêm bệnh nhân" : "Sửa bệnh nhân");
            setSize(420, 380);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout());

            JPanel form = new JPanel(new GridLayout(8,2,6,6));
            form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            idField = new JTextField();
//            int cnt = 801;
//            idField.setText(String.format("BN%03d", cnt++));
//            idField.setEnabled(false);
            nameField = new JTextField();
            cccdField = new JTextField();
            dobField = new JTextField();
            genderField = new JTextField();
            addrField = new JTextField();
            phoneField = new JTextField();
            phoneParentField = new JTextField();

            form.add(new JLabel("Mã bệnh nhân:")); form.add(idField);
            form.add(new JLabel("Họ tên:")); form.add(nameField);
            form.add(new JLabel("CCCD:")); form.add(cccdField);
            form.add(new JLabel("Ngày sinh (yyyy-mm-dd):")); form.add(dobField);
            form.add(new JLabel("Giới tính:")); form.add(genderField);
            form.add(new JLabel("Địa chỉ:")); form.add(addrField);
            form.add(new JLabel("SĐT:")); form.add(phoneField);
            form.add(new JLabel("SĐT NGH:")); form.add(phoneParentField);

            add(form, BorderLayout.CENTER);

            JPanel btns = new JPanel();
            JButton save = new JButton("Lưu");
            JButton cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel);
            add(btns, BorderLayout.SOUTH);

            if (existing != null) {
                idField.setText(existing.getMa_benh_nhan());
                idField.setEnabled(false);
                nameField.setText(existing.getHo_ten());
                cccdField.setText(existing.getCccd());
                dobField.setText(existing.getNgaySinh().toString());
                genderField.setText(existing.getGioiTinh());
                addrField.setText(existing.getDiaChi());
                phoneField.setText(existing.getSo_dien_thoai());
                phoneParentField.setText(existing.getSo_dien_thoai_ngh());
                patient = existing;
            }

            save.addActionListener(e -> {
                String id = idField.getText().trim();
                if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập mã bệnh nhân"); return; }
                String name = nameField.getText().trim();
                String cccd = cccdField.getText().trim();
                String dob = dobField.getText().trim();
                java.sql.Date ngaySinh;
                try{
                    ngaySinh = java.sql.Date.valueOf(dob);
                }
                catch(IllegalArgumentException iea){
                    JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Định dạng đúng: yyyy-MM-dd");
                    return;
                }
                String gender = genderField.getText().trim();
                String addr = addrField.getText().trim();
                String sdt = phoneField.getText().trim();
                String sdtNGH = phoneParentField.getText().trim();
                Patient tmp = new Patient(
                        id,
                        name,
                        ngaySinh,
                        gender,
                        cccd,
                        sdt,
                        sdtNGH,
                        addr
                );
                if(!validatePatientInfo(tmp)){
                    saved = false;
                    return;
                }
                if (existing == null) {
                    BenhNhanDAO benhNhanDAO = new BenhNhanDAO();
                    try {
                        if (benhNhanDAO.findWithId(id) != null) {
                            JOptionPane.showMessageDialog(null, "Mã đã tồn tại.");
                            return;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Patient pt = new Patient(id, name, ngaySinh, gender, cccd, sdt, sdtNGH, addr);
                    patient = pt;
                } else {
                    existing.setHo_ten(name);
                    existing.setCccd(cccd);
                    existing.setNgaySinh(java.sql.Date.valueOf(dob));
                    existing.setGioiTinh(gender);
                    existing.setDiaChi(addr);
                    existing.setSo_dien_thoai(sdt);
                    existing.setSo_dien_thoai_ngh(sdtNGH);
                }

                saved = true;
                dispose();
            });
            cancel.addActionListener(e -> dispose());
        }
    }
    class RecordPatientForm extends JDialog{
        private JTextField MaBNField, DiaChiField, HoTenField, SDTField, GioiTinhField, SDT_NGHField;
        private JTable recordTable;

        public RecordPatientForm(Frame owner, Patient p) throws SQLException {
            super(owner, "Hồ sơ Bệnh nhân", true);

            // --- 1. Cấu hình cơ bản ---
            setSize(1280, 1280);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout(10, 10)); // Layout chính: BorderLayout
            // --- 2. Panel Thông tin Bệnh nhân (NORTH) ---
            JPanel infoPanel = new JPanel(new GridLayout(3, 4, 5, 5)); // 3 hàng, 4 cột, khoảng cách 5px
            infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15)); // Đệm ngoài

            // Giả lập dữ liệu để hiển thị (Bạn có thể thay bằng JTextfield nếu muốn sửa)
            MaBNField = new JTextField(p.getMa_benh_nhan());
            HoTenField = new JTextField(p.getHo_ten());
            GioiTinhField = new JTextField(p.getGioiTinh());
            DiaChiField = new JTextField(p.getDiaChi());
            SDTField = new JTextField(p.getSo_dien_thoai());
            SDT_NGHField = new JTextField(p.getSo_dien_thoai_ngh());
            MaBNField.setEnabled(false);
            GioiTinhField.setEnabled(false);
            HoTenField.setEnabled(false);
            DiaChiField.setEnabled(false);
            SDTField.setEnabled(false);
            SDT_NGHField.setEnabled(false);
            // Hàng 1
            infoPanel.add(new JLabel("Mã bệnh nhân:"));
            infoPanel.add(MaBNField);
            infoPanel.add(new JLabel("Địa chỉ:"));
            infoPanel.add(DiaChiField);

            // Hàng 2
            infoPanel.add(new JLabel("Họ và tên:"));
            infoPanel.add(HoTenField);
            infoPanel.add(new JLabel("Số điện thoại:"));
            infoPanel.add(SDTField);

            // Hàng 3
            infoPanel.add(new JLabel("Giới tính:"));
            infoPanel.add(GioiTinhField);
            infoPanel.add(new JLabel("Số điện thoại NGH:"));
            infoPanel.add(SDT_NGHField);

            // --- 3. Bảng Lịch sử Khám bệnh (CENTER) ---

            // Khởi tạo Model và Bảng
            String[] columnNames = {"Ngày khám", "Mã bác sĩ", "Họ tên bác sĩ", "Chẩn đoán"};
            LichSuKhamDAO lichSuKhamDAO = new LichSuKhamDAO();
            ArrayList<LichSuKham> a = lichSuKhamDAO.selectRecordsByPatientId(p.getMa_benh_nhan());
            recordTableModel = new DefaultTableModel(null, columnNames);
            recordTable = new JTable(recordTableModel);
            refreshRecordPatient(a);

            // Thêm bảng vào JScrollPane để có thể cuộn
            JScrollPane tableScrollPane = new JScrollPane(recordTable);

            // --- 5. Thêm các Panel vào Dialog chính ---
            add(infoPanel, BorderLayout.NORTH);
            add(tableScrollPane, BorderLayout.CENTER);

            // --- Hiển thị ---
            setResizable(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
    public  ArrayList<String> loadSpecialties() throws SQLException { // Đặt logic thực thi vào đây
        ArrayList<String> specialties = new ArrayList<>();
        KhoaDAO khoaDAO = new KhoaDAO();
        ArrayList<Khoa> k = khoaDAO.selectAll();
        for(Khoa x : k){
            specialties.add(x.getMaKhoa() + " - " +  x.getTen());
        }
        return specialties;
    }
    // Doctor form
    class DoctorFormDialog extends JDialog {
        boolean saved = false;
        Doctor doctor;
        private JTextField idField, nameField, phoneField, experienceField;

        ArrayList<String> specialties = loadSpecialties();
        String[] tmp = specialties.toArray(new String[0]);
        JComboBox<String> specialtyField = new JComboBox<>(tmp);
        BacSiDAO bacSiDAO = new BacSiDAO();
        DoctorFormDialog(Frame owner, Doctor existing) throws SQLException {
            super(owner, true);
            setTitle(existing == null ? "Thêm bác sĩ" : "Sửa bác sĩ");
            setSize(450, 420);
            setLocationRelativeTo(owner);
            setLayout(new BorderLayout());
            JPanel form = new JPanel(new GridLayout(9,2,6,6));
            form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            idField = new JTextField();
            nameField = new JTextField();
            phoneField = new JTextField();
            experienceField = new JTextField();
            form.add(new JLabel("Mã bác sĩ:")); form.add(idField);
            form.add(new JLabel("Họ tên:")); form.add(nameField);
            form.add(new JLabel("SĐT:")); form.add(phoneField);
            form.add(new JLabel("Số năm kinh nghiệm:")); form.add(experienceField);
            form.add(new JLabel("Chuyên khoa:")); form.add(specialtyField);

            add(form, BorderLayout.CENTER);

            JPanel btns = new JPanel();
            JButton save = new JButton("Lưu");
            JButton cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel);
            add(btns, BorderLayout.SOUTH);

            if (existing != null) {
                idField.setText(existing.getMaBacSi()); idField.setEnabled(false);
                nameField.setText(existing.getHo_ten());
                phoneField.setText(existing.getSo_dien_thoai());
                experienceField.setText(String.valueOf(existing.getSoNamKinhNghiem()));
                specialtyField.setSelectedItem(existing.getChuyen_khoa().getMaKhoa() + " - " + existing.getChuyenKhoa().getTen());
                doctor = existing;
            }

            save.addActionListener(e -> {
                String id = idField.getText().trim();
                if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập mã bác sĩ"); return; }
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                int soNamKinhNghiem = Integer.parseInt(experienceField.getText().trim());
                String chuyenKhoa = ((String)specialtyField.getSelectedItem()).substring(8);
                KhoaDAO khoaDAO = new KhoaDAO();
                Khoa khoa = new Khoa();
                try {
                    ArrayList<Khoa> a = khoaDAO.selectByName(chuyenKhoa);
                    doctor = new Doctor(
                            id,
                            name,
                            phone,
                            soNamKinhNghiem,
                            a.get(0)
                );


                    if(!validateDoctorInfo(doctor)){
                        saved = false;
                        return;
                    }
                    if (existing == null) {
                        if(bacSiDAO.findWithId(id) != null){
                            JOptionPane.showMessageDialog(this, "Mã bác sĩ đã tồn tai");
                            saved = false;
                            return;
                        }
                        Doctor d = new Doctor(id, name, phone, soNamKinhNghiem, khoa);
//                        doctor = d;
                    } else {
                        existing.setMaBacSi(id);
                        existing.setHoTen(name);
                        existing.setSoDienThoai(phone);
                        existing.setSoNamKinhNghiem(soNamKinhNghiem);
                        existing.setChuyenKhoa(khoa);
                    }
                    saved = true;
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            });

            cancel.addActionListener(e -> dispose());
        }
    }

    // Service form
    class ServiceFormDialog extends JDialog {
        boolean saved = false;
        ServiceItem serviceItem;
        JTextField idF, nameF, costF;
        ServiceFormDialog(Frame owner, ServiceItem existing) {
            super(owner, true);
            setTitle(existing == null ? "Thêm dịch vụ" : "Sửa dịch vụ");
            setSize(350, 220); setLocationRelativeTo(owner);
            JPanel form = new JPanel(new GridLayout(3,2,6,6));
            form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            idF = new JTextField(); nameF = new JTextField(); costF = new JTextField();
            form.add(new JLabel("Mã dịch vụ:")); form.add(idF);
            form.add(new JLabel("Tên dịch vụ:")); form.add(nameF);
            form.add(new JLabel("Chi phí:")); form.add(costF);
            add(form, BorderLayout.CENTER);

            JPanel btns = new JPanel(); JButton save = new JButton("Lưu"), cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel); add(btns, BorderLayout.SOUTH);

            if (existing != null) { idF.setText(existing.serviceId); idF.setEnabled(false); nameF.setText(existing.name); costF.setText(String.valueOf(existing.cost)); }

            save.addActionListener(e -> {
                String id = idF.getText().trim();
                String name = nameF.getText().trim();
                String cost = costF.getText().trim();
                if (id.isEmpty() || name.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập đầy đủ"); return; }
                double c = 0;
                try { c = Double.parseDouble(cost); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Chi phí phải là số"); return; }
                if (existing == null) {
                    if (findServiceById(id) != null) { JOptionPane.showMessageDialog(this, "Mã tồn tại"); return; }
                    serviceItem = new ServiceItem(id, name, c);
                } else {
                    existing.name = name; existing.cost = c;
                }
                saved = true; dispose();
            });
            cancel.addActionListener(e -> dispose());
        }
    }

    // Medicine form
    class MedicineFormDialog extends JDialog {
        boolean saved = false;
        MedicineType type;
        JTextField idF, nameF, mfrF;
        MedicineFormDialog(Frame owner, MedicineType existing) {
            super(owner, true);
            setTitle(existing == null ? "Thêm thuốc" : "Sửa thuốc");
            setSize(360, 220); setLocationRelativeTo(owner);
            JPanel form = new JPanel(new GridLayout(3,2,6,6));
            form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            idF = new JTextField(); nameF = new JTextField(); mfrF = new JTextField();
            form.add(new JLabel("Mã thuốc:")); form.add(idF);
            form.add(new JLabel("Tên thuốc:")); form.add(nameF);
            form.add(new JLabel("Nhà sản xuất:")); form.add(mfrF);
            add(form, BorderLayout.CENTER);

            JPanel btns = new JPanel(); JButton save = new JButton("Lưu"), cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel); add(btns, BorderLayout.SOUTH);

            if (existing != null) { idF.setText(existing.medicineId); idF.setEnabled(false); nameF.setText(existing.name); mfrF.setText(existing.manufacturer); }

            save.addActionListener(e -> {
                String id = idF.getText().trim(); String name = nameF.getText().trim(); String mfr = mfrF.getText().trim();
                if (id.isEmpty() || name.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập đầy đủ"); return; }
                if (existing == null) {
                    if (findMedicineById(id) != null) { JOptionPane.showMessageDialog(this, "Mã tồn tại"); return; }
                    type = new MedicineType(id, name, mfr);
                } else {
                    existing.name = name; existing.manufacturer = mfr;
                }
                saved = true; dispose();
            });
            cancel.addActionListener(e -> dispose());
        }
    }

    // Medical record form
    class MedicalRecordFormDialog extends JDialog {
        boolean saved = false;
        MedicalRecord record;
        JTextField idF, patientIdF;
        JTextArea detailsA;
        MedicalRecordFormDialog(Frame owner, MedicalRecord existing) {
            super(owner, true);
            setTitle(existing == null ? "Thêm hồ sơ bệnh án" : "Sửa hồ sơ");
            setSize(480, 360); setLocationRelativeTo(owner);
            JPanel top = new JPanel(new GridLayout(2,2,6,6));
            idF = new JTextField(); patientIdF = new JTextField();
            top.add(new JLabel("Mã hồ sơ:")); top.add(idF);
            top.add(new JLabel("Mã bệnh nhân:")); top.add(patientIdF);
            add(top, BorderLayout.NORTH);
            detailsA = new JTextArea();
            add(new JScrollPane(detailsA), BorderLayout.CENTER);

            JPanel btns = new JPanel(); JButton save = new JButton("Lưu"), cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel); add(btns, BorderLayout.SOUTH);

            if (existing != null) {
                idF.setText(existing.recordId); idF.setEnabled(false);
                patientIdF.setText(existing.patientId);
                detailsA.setText(existing.details);
                record = existing;
            }

            save.addActionListener(e -> {
                String id = idF.getText().trim(); String pid = patientIdF.getText().trim(); String det = detailsA.getText().trim();
                if (id.isEmpty() || pid.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập mã và mã bệnh nhân"); return; }
                if (existing == null) {
                    if (findMedicalRecordById(id) != null) { JOptionPane.showMessageDialog(this, "Mã tồn tại"); return; }
                    record = new MedicalRecord(id, pid, det, "");
                } else {
                    existing.details = det;
                }
                saved = true; dispose();
            });
            cancel.addActionListener(e -> dispose());
        }
    }

    // Exam form
    class ExamFormDialog extends JDialog {
        boolean saved = false;
        ExamRecord record;
        JTextField idF, patientIdF, doctorIdF, datetimeF;
        JTextArea diagA;
        JTextField medRecIdF;

        ExamFormDialog(Frame owner, ExamRecord existing) {
            super(owner, true);
            setTitle(existing == null ? "Thêm lịch sử khám" : "Sửa lịch sử khám");
            setSize(500, 420); setLocationRelativeTo(owner);
            JPanel top = new JPanel(new GridLayout(5,2,6,6));
            idF = new JTextField(); patientIdF = new JTextField(); doctorIdF = new JTextField(); datetimeF = new JTextField(sdf.format(new Date())); medRecIdF = new JTextField();
            top.add(new JLabel("Mã lịch sử:")); top.add(idF);
            top.add(new JLabel("Mã bệnh nhân:")); top.add(patientIdF);
            top.add(new JLabel("Mã bác sĩ:")); top.add(doctorIdF);
            top.add(new JLabel("Ngày giờ (yyyy-MM-dd HH:mm):")); top.add(datetimeF);
            top.add(new JLabel("Mã hồ sơ liên kết (nếu có):")); top.add(medRecIdF);

            add(top, BorderLayout.NORTH);
            diagA = new JTextArea(); add(new JScrollPane(diagA), BorderLayout.CENTER);
            JPanel btns = new JPanel(); JButton save = new JButton("Lưu"), cancel = new JButton("Hủy");
            btns.add(save); btns.add(cancel); add(btns, BorderLayout.SOUTH);

            if (existing != null) {
                idF.setText(existing.recordId); idF.setEnabled(false);
                patientIdF.setText(existing.patientId); doctorIdF.setText(existing.doctorId);
                datetimeF.setText(existing.dateTime); diagA.setText(existing.diagnose);
                medRecIdF.setText(existing.medicalRecordId == null ? "" : existing.medicalRecordId);
                record = existing;
            }

            save.addActionListener(e -> {
                String id = idF.getText().trim(); String pid = patientIdF.getText().trim(); String did = doctorIdF.getText().trim();
                String dt = datetimeF.getText().trim(); String diag = diagA.getText().trim(); String mre = medRecIdF.getText().trim();
                if (id.isEmpty() || pid.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập mã lịch và mã bệnh nhân"); return; }
                if (existing == null) {
                    if (findExamById(id) != null) { JOptionPane.showMessageDialog(this, "Mã tồn tại"); return; }
                    record = new ExamRecord(id, pid, did == null ? "" : did, dt, diag, mre.isEmpty() ? null : mre);
                } else {
                    existing.patientId = pid; existing.doctorId = did; existing.dateTime = dt; existing.diagnose = diag; existing.medicalRecordId = mre.isEmpty() ? null : mre;
                }
                saved = true; dispose();
            });
            cancel.addActionListener(e -> dispose());
        }
    }


    // Prescription form
    class PrescriptionFormDialog extends JDialog {
        boolean saved = false;
        Prescription prescription;

        JTextField idF, patientIdF, doctorIdF, dateF;
        DefaultTableModel itemsModel;
        PrescriptionFormDialog(Frame owner, Prescription existing, String suggestedPatientId, String suggestedDoctorId) {
            super(owner, true);
            setTitle(existing == null ? "Tạo đơn thuốc" : "Sửa đơn thuốc");
            setSize(700, 480); setLocationRelativeTo(owner);
            setLayout(new BorderLayout());

            JPanel top = new JPanel(new GridLayout(3,4,6,6));
            idF = new JTextField(); patientIdF = new JTextField(); doctorIdF = new JTextField(); dateF = new JTextField(sdf.format(new Date()));
            top.add(new JLabel("Mã đơn:")); top.add(idF);
            top.add(new JLabel("Mã bệnh nhân:")); top.add(patientIdF);
            top.add(new JLabel("Mã bác sĩ:")); top.add(doctorIdF);
            top.add(new JLabel("Ngày giờ:")); top.add(dateF);
            add(top, BorderLayout.NORTH);

            // items table
            String[] cols = {"Mã thuốc", "Tên thuốc", "Số lượng"};
            itemsModel = new DefaultTableModel(cols, 0);
            JTable t = new JTable(itemsModel);
            add(new JScrollPane(t), BorderLayout.CENTER);

            JPanel itemBtns = new JPanel();
            JButton addItemBtn = new JButton("Thêm thuốc");
            JButton delItemBtn = new JButton("Xóa thuốc");
            itemBtns.add(addItemBtn); itemBtns.add(delItemBtn);
            add(itemBtns, BorderLayout.EAST);

            JPanel bottom = new JPanel();
            JButton save = new JButton("Lưu"); JButton cancel = new JButton("Hủy");
            bottom.add(save); bottom.add(cancel);
            add(bottom, BorderLayout.SOUTH);

            if (existing != null) {
                idF.setText(existing.prescriptionId); idF.setEnabled(false);
                patientIdF.setText(existing.patientId); doctorIdF.setText(existing.doctorId);
                dateF.setText(existing.dateTime);
                prescription = existing;
                for (Prescription.MedItem mi : existing.items) {
                    itemsModel.addRow(new Object[]{mi.medicineId, findMedicineName(mi.medicineId), mi.qty});
                }
            } else {
                if (suggestedPatientId != null) patientIdF.setText(suggestedPatientId);
                if (suggestedDoctorId != null) doctorIdF.setText(suggestedDoctorId);
            }

            addItemBtn.addActionListener(e -> {
                String mid = JOptionPane.showInputDialog(this, "Nhập mã thuốc (ví dụ MT001):");
                if (mid == null || mid.trim().isEmpty()) return;
                MedicineType mt = findMedicineById(mid.trim());
                if (mt == null) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã thuốc: " + mid);
                    return;
                }
                String qtyS = JOptionPane.showInputDialog(this, "Số lượng:");
                int q = 1;
                try { q = Integer.parseInt(qtyS); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ"); return; }
                itemsModel.addRow(new Object[]{mt.medicineId, mt.name, q});
            });

            delItemBtn.addActionListener(e -> {
                int r = t.getSelectedRow();
                if (r != -1) itemsModel.removeRow(r);
            });

            save.addActionListener(e -> {
                String id = idF.getText().trim(); String pid = patientIdF.getText().trim(); String did = doctorIdF.getText().trim(); String date = dateF.getText().trim();
                if (id.isEmpty() || pid.isEmpty()) { JOptionPane.showMessageDialog(this, "Nhập mã đơn và mã bệnh nhân"); return; }
                if (existing == null && findPrescriptionById(id) != null) { JOptionPane.showMessageDialog(this, "Mã đơn đã tồn tại"); return; }
                Prescription pr = existing == null ? new Prescription(id, pid, did, date) : existing;
                if (existing == null) pr.items.clear();
                // read items
                java.util.List<Prescription.MedItem> list = new ArrayList<>();
                for (int i=0;i<itemsModel.getRowCount();i++) {
                    String mid = (String) itemsModel.getValueAt(i,0);
                    int qty = Integer.parseInt(itemsModel.getValueAt(i,2).toString());
                    list.add(new Prescription.MedItem(mid, qty));
                }
                pr.items = list;
                if (existing == null) prescription = pr;
                saved = true; dispose();
            });

            cancel.addActionListener(e -> dispose());
        }
    }

    // ---------- main ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // look and feel system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            try {
                new HospitalApp().setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
