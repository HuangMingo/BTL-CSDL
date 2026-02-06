import db.JDBCUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class InvoiceForm extends JFrame {

    private JTextField txtMaHoaDon, txtTenBenhNhan, txtNgayKham, txtTongChiPhi;
    private JTable tblThuoc, tblXetNghiem;
    private DefaultTableModel itemsModel; //thuoc
    private DefaultTableModel modelXN; //bang xet nghiem
    public InvoiceForm(String maKham) throws SQLException {
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
        txtMaHoaDon.setText("HD0001");
        txtTenBenhNhan = new JTextField(15);
        Connection con = JDBCUtil.getConnection();
        String sql = "SELECT distinct p.HoVaTen\n" +
                "FROM lichsukham l\n" +
                "JOIN benhnhan p\n" +
                "ON l.MaBenhNhan = p.MaBenhNhan\n" +
                "WHERE l.MaKham IN(\n" +
                "\tSELECT don.MaKham\n" +
                "\tFROM donthuoc don\n" +
                "\tWHERE don.MaHoaDon = 'HD0001'\n" +
                ")";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            txtTenBenhNhan.setText(rs.getString("HoVaTen"));
        }

        String sql2 = "SELECT NgayTaoHoaDon\n" +
                "FROM hoadon\n" +
                "WHERE MaHoaDon = 'HD0001'";
        PreparedStatement ps1 = con.prepareStatement(sql2);
        ResultSet rs1 = ps1.executeQuery();
        txtNgayKham = new JTextField(15);
        while(rs1.next()){
            txtNgayKham.setText(rs1.getString("NgayTaoHoaDon"));
        }


        gbc.gridx = 0; gbc.gridy = 0;
        pnlInfo.add(new JLabel("Mã hóa đơn:"), gbc);

        gbc.gridx = 1;
        pnlInfo.add(txtMaHoaDon, gbc);

        gbc.gridx = 2;
        pnlInfo.add(new JLabel("Ngày tạo hóa đơn:"), gbc);

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
        String[] cols = {"Mã thuốc", "Tên thuốc", "Số lượng", "Giá tiền(VNĐ)", "Thành tiền(VNĐ)"};
        itemsModel = new DefaultTableModel(cols, 0);
        tblThuoc = new JTable(itemsModel);
        JScrollPane spThuoc = new JScrollPane(tblThuoc);
        spThuoc.setBorder(BorderFactory.createTitledBorder("Đơn thuốc"));
        spThuoc.setPreferredSize(new Dimension(900, 200));

        main.add(spThuoc);
        main.add(Box.createRigidArea(new Dimension(0, 15)));

        // =================================================================
        // BẢNG XÉT NGHIỆM
        // =================================================================
        tblXetNghiem = new JTable();
        String[] cols1 = {"Mã xét nghiệm", "Tên xét nghiệm", "Thành tiền(VNĐ)"};
        DefaultTableModel itemsModel1 = new DefaultTableModel(cols1, 0);
        tblXetNghiem.setModel(itemsModel1);
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
        JLabel lblTotal = new JLabel("Tổng tiền:");
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
            // 2. Đơn thuốc
            // ----------------------------------------------------------------
            PreparedStatement stThuoc = con.prepareStatement(
                    "SELECT ct.MaThuoc, t.TenThuoc, ct.SoLuong, t.ChiPhi, t.chiphi * ct.SoLuong thanhtien\n" +
                            "FROM donthuoc don\n" +
                            "JOIN chitietdonthuoc ct\n" +
                            "ON don.MaDon = ct.MaDon\n" +
                            "JOIN thuoc t\n" +
                            "ON t.MaThuoc = ct.MaThuoc\n" +
                            "WHERE MaHoaDon = 'HD0001';"
            );
//            stThuoc.setString(1, maKham);
            ResultSet rsThuoc = stThuoc.executeQuery();

            double tongThuoc = 0;
            while (rsThuoc.next()) {

                itemsModel.addRow(new Object[]{
                        rsThuoc.getString("MaThuoc"),
                        rsThuoc.getString("TenThuoc"),
                        rsThuoc.getString("SoLuong"),
                        rsThuoc.getDouble("ChiPhi"),
                        rsThuoc.getDouble("thanhtien")
                });
                tongThuoc += rsThuoc.getDouble("thanhtien");
            }
            tblThuoc.setModel(itemsModel);

            // ----------------------------------------------------------------
            // 3. Xét nghiệm
            // ----------------------------------------------------------------


            PreparedStatement stXN = con.prepareStatement(
                    "SELECT pxn.MaXetNghiem, xn.TenXetNghiem, xn.ChiPhi\n" +
                            "FROM phieuxetnghiem pxn\n" +
                            "JOIN xetnghiem xn\n" +
                            "ON pxn.MaXetNghiem = xn.MaXetNghiem\n" +
                            "WHERE MaHoaDon = 'HD0001'"
            );
//            stXN.setString(1, maKham);
            ResultSet rsXN = stXN.executeQuery();

            DefaultTableModel modelXN = new DefaultTableModel(
                    new String[]{"Mã xét nghiệm", "Tên xét nghiệm", "Chi phí (VND)"}, 0
            ){
                public boolean isCellEditable(int row, int column){ return false; }
            };

            double tongXN = 0;
            while (rsXN.next()) {
                modelXN.addRow(new Object[]{
                        rsXN.getString("MaXetNghiem"),
                        rsXN.getString("TenXetNghiem"),
                        rsXN.getDouble("ChiPhi")
                });
                tongXN += rsXN.getDouble("ChiPhi");
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

    public static void main(String[] args) throws SQLException {
        new InvoiceForm("K0001");
    }
}
