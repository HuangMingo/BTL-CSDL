import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class InvoiceForm extends JFrame {

    private JTextField txtMaHoaDon, txtTenBenhNhan, txtNgayKham, txtTongChiPhi;
    private JTable tblThuoc, tblXetNghiem;

    public InvoiceForm(String maKham) {
        setTitle("HÓA ĐƠN KHÁM BỆNH");
        setSize(950, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======= Font tổng ========
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(main);

        // =================================================================
        // TIÊU ĐỀ
        // =================================================================
        JLabel title = new JLabel("HÓA ĐƠN DỊCH VỤ Y TẾ", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        main.add(title);

        // =================================================================
        // THÔNG TIN BỆNH NHÂN (giống mẫu hóa đơn thực tế)
        // =================================================================
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBorder(BorderFactory.createTitledBorder("Thông tin bệnh nhân"));
        pnlInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        txtMaHoaDon = new JTextField(15);
        txtTenBenhNhan = new JTextField(15);
        txtNgayKham = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        pnlInfo.add(new JLabel("Mã hóa đơn:"), gbc);

        gbc.gridx = 1;
        pnlInfo.add(txtMaHoaDon, gbc);

        gbc.gridx = 2;
        pnlInfo.add(new JLabel("Ngày khám:"), gbc);

        gbc.gridx = 3;
        pnlInfo.add(txtNgayKham, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlInfo.add(new JLabel("Họ tên bệnh nhân:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        pnlInfo.add(txtTenBenhNhan, gbc);

        main.add(pnlInfo);

        main.add(Box.createRigidArea(new Dimension(0, 10)));

        // =================================================================
        // BẢNG ĐƠN THUỐC
        // =================================================================
        tblThuoc = new JTable();
        JScrollPane spThuoc = new JScrollPane(tblThuoc);
        spThuoc.setBorder(BorderFactory.createTitledBorder("Đơn thuốc"));
        spThuoc.setPreferredSize(new Dimension(900, 200));

        main.add(spThuoc);
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // =================================================================
        // BẢNG XÉT NGHIỆM
        // =================================================================
        tblXetNghiem = new JTable();
        JScrollPane spXN = new JScrollPane(tblXetNghiem);
        spXN.setBorder(BorderFactory.createTitledBorder("Xét nghiệm"));
        spXN.setPreferredSize(new Dimension(900, 200));

        main.add(spXN);

        main.add(Box.createRigidArea(new Dimension(0, 20)));

        // =================================================================
        // TỔNG KẾT
        // =================================================================
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottom.setBorder(BorderFactory.createTitledBorder("Tổng kết"));

        JLabel lblTotal = new JLabel("Tổng chi phí:");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));

        txtTongChiPhi = new JTextField(15);
        txtTongChiPhi.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTongChiPhi.setForeground(Color.RED);

        pnlBottom.add(lblTotal);
        pnlBottom.add(txtTongChiPhi);

        main.add(pnlBottom);

        // >>> Load dữ liệu
        loadData(maKham);

        setVisible(true);
    }

    // =================================================================
    // HÀM LẤY DỮ LIỆU TỪ MYSQL
    // =================================================================
    private void loadData(String maKham) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emr",
                    "root",
                    "minh1200"
            );

            // ----------------------------------------------------------------
            // 1. Thông tin bệnh nhân + ngày khám
            // ----------------------------------------------------------------
            PreparedStatement stInfo = con.prepareStatement(
                    "SELECT bn.HoVaTen, lk.NgayKham " +
                            "FROM lichsukham lk JOIN benhnhan bn ON lk.MaBenhNhan = bn.MaBenhNhan " +
                            "WHERE lk.MaKham = ?"
            );
            stInfo.setString(1, maKham);
            ResultSet rs = stInfo.executeQuery();

            if (rs.next()) {
                txtMaHoaDon.setText(maKham);
                txtTenBenhNhan.setText(rs.getString("HoVaTen"));
                txtNgayKham.setText(rs.getString("NgayKham"));
            }

            // ----------------------------------------------------------------
            // 2. Đơn thuốc
            // ----------------------------------------------------------------
            PreparedStatement stThuoc = con.prepareStatement(
                    "SELECT t.TenThuoc, ct.LieuLuong, ct.CachDung, t.ChiPhi " +
                            "FROM donthuoc dt " +
                            "JOIN chitietdonthuoc ct ON dt.MaDon = ct.MaDon " +
                            "JOIN thuoc t ON ct.MaThuoc = t.MaThuoc " +
                            "WHERE dt.MaKham = ?"
            );
            stThuoc.setString(1, maKham);
            ResultSet rsThuoc = stThuoc.executeQuery();

            DefaultTableModel modelThuoc = new DefaultTableModel(
                    new String[]{"Tên thuốc", "Liều lượng", "Cách dùng", "Đơn giá (VND)"}, 0
            ){
                public boolean isCellEditable(int row, int column){ return false; }
            };

            double tongThuoc = 0;
            while (rsThuoc.next()) {
                modelThuoc.addRow(new Object[]{
                        rsThuoc.getString(1),
                        rsThuoc.getString(2),
                        rsThuoc.getString(3),
                        rsThuoc.getDouble(4)
                });
                tongThuoc += rsThuoc.getDouble(4);
            }
            tblThuoc.setModel(modelThuoc);

            // ----------------------------------------------------------------
            // 3. Xét nghiệm
            // ----------------------------------------------------------------
            PreparedStatement stXN = con.prepareStatement(
                    "SELECT TenXetNghiem, KetQuaXetNghiem, ChiPhi " +
                            "FROM xetnghiem WHERE MaKham = ?"
            );
            stXN.setString(1, maKham);
            ResultSet rsXN = stXN.executeQuery();

            DefaultTableModel modelXN = new DefaultTableModel(
                    new String[]{"Tên xét nghiệm", "Kết quả", "Chi phí (VND)"}, 0
            ){
                public boolean isCellEditable(int row, int column){ return false; }
            };

            double tongXN = 0;
            while (rsXN.next()) {
                modelXN.addRow(new Object[]{
                        rsXN.getString(1),
                        rsXN.getString(2),
                        rsXN.getDouble(3)
                });
                tongXN += rsXN.getDouble(3);
            }
            tblXetNghiem.setModel(modelXN);

            txtTongChiPhi.setText(String.format("%,.0f VND", (tongThuoc + tongXN)));

            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu!");
        }
    }

    public static void main(String[] args) {
        new InvoiceForm("MK001");
    }
}
