/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Contains;

import controller.ThongKeController;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.LoaiSanPham;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.ThongKeService;
import serviceImpl.LoaiSanPhamServiceImpl;
import serviceImpl.ThongKeServiceImpl;

/**
 *
 * @author ACER
 */
public class ThongKeJPanel extends javax.swing.JPanel {

    //Call Model
    DefaultTableModel doanhThuNgayModel = new DefaultTableModel();
    DefaultTableModel ThongKeSanPhamModel = new DefaultTableModel();
    //Call Service
    ThongKeServiceImpl tksv = new ThongKeServiceImpl();
    LoaiSanPhamServiceImpl lspsv = new LoaiSanPhamServiceImpl();
    //Call Convert Tiền VN
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ThongKeJPanel() {
        initComponents();
        doanhThuNgayModel = (DefaultTableModel) DoanhThuTheoNgayTable.getModel();
        ThongKeSanPhamModel = (DefaultTableModel) ThongKeSPTable.getModel();
        this.setTitleCTSP();
        this.configTable();
        this.fillCbbLoaiSP();
        this.fillCbbHTTK();
        this.showDoanhThuCaNam();
        this.showDoanhThuToDay();
        this.showDoanhThu7NgayGanNhat();
        this.showDoanhThuThangNay();
        this.DoanhThuSanPham();
        this.soSPDangKinhDoanh();
        this.soSPHetHang();
        this.soSPSapHetHang();
        this.soSPNgungKD();
        this.showDataChart();
        this.fillCbbThongKe();

    }
    
    
    
    private void setTitleCTSP() {
        String header[] = {"Mã SP", "Tên Sản Phẩm", "Tên Loại Sản Phẩm", "Số Lượng Tồn", "Đơn Giá", "Số Lượng Bán Được", "Doanh Thu Từ Sản Phẩm"};
        ThongKeSanPhamModel.setColumnIdentifiers(header);
    }
    
    //chart
    private void showDataChart(){
        ThongKeController tkc=new ThongKeController();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        tkc.setDateToChart(chartJPanel,year);
    }
    
    //fill cbbThongKe
    private void fillCbbThongKe(){
        cbbYear.removeAllItems();
        for(Object[] row:tksv.listYear()){
            cbbYear.addItem(row[0]+"");
        }
    }
    
    //config table
    private void configTable() {
        doanhThuNgayModel.setRowCount(0);
        DoanhThuTheoNgayTable.setRowHeight(50);
        ThongKeSanPhamModel.setRowCount(0);
        ThongKeSPTable.setRowHeight(50);
    }

    //GetToday
    private LocalDate getToDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String ngay = "";
        if (month < 9 && date < 10) {
            ngay = (year + "-" + "0" + (month + 1) + "-" + "0" + date);
        } else if (month < 9 && date >= 10) {
            ngay = (year + "-" + "0" + (month + 1) + "-" + date);
        } else if (month >= 9 && date < 10) {
            ngay = (year + "-" + (month + 1) + "-" + "0" + date);
        } else if (month >= 9 && date >= 10) {
            ngay = (year + "-" + (month + 1) + "-" + date);
        }
        LocalDate ngay1 = LocalDate.parse(ngay);
        return ngay1;
    }

    //fillCbbLoaiSP
    private void fillCbbLoaiSP() {
        cbbLoaiSanPham.removeAllItems();
        for (LoaiSanPham lsp : lspsv.getAll()) {
            cbbLoaiSanPham.addItem(lsp.getTenLoaiSanPham());
        }
    }

    //fillCbbHinhThucThongKe
    private void fillCbbHTTK() {
        cbbHinhThucThongKe.removeAllItems();
        cbbHinhThucThongKe.addItem("Tất Cả");
        cbbHinhThucThongKe.addItem("Top Sản Phẩm Doanh Thu Nhiều Nhất");
        cbbHinhThucThongKe.addItem("Top Sản Phẩm Doanh Thu Thấp Nhất");
        cbbHinhThucThongKe.addItem("Top Sản Phẩm Được Mua Nhiều Nhất");
        cbbHinhThucThongKe.addItem("Top Sản Phẩm Được Mua Ít Nhất");
    }

    //Doanh Thu Cả Năm
    private void showDoanhThuCaNam() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Double doanhThuCaNam = tksv.getDoanhThuCaNam(year);
        lblDoanhThuCaNamNay.setText(format.format(doanhThuCaNam));
    }

    //Doanh Thu Hôm Nay
    private void showDoanhThuToDay() {
        LocalDate toDay = this.getToDay();
        Double doanhThuToDay = tksv.getDoanhThuToDay(toDay);
        lblTongDoanhThuHomNay.setText(format.format(doanhThuToDay));
    }

    //Doanh Thu 7 Ngày Gần Nhất
    private void showDoanhThu7NgayGanNhat() {
        Double doanhThu7NgayGanNhat = tksv.getDoanhThu7NgayGanNhat();
        lblTongDoanhThu7NgayGanNhat.setText(format.format(doanhThu7NgayGanNhat));
    }

    //Doanh Thu Tháng Này
    private void showDoanhThuThangNay() {
        Calendar cld = Calendar.getInstance();
        int month = cld.get(Calendar.MONTH);
        String thang = "";
        if (month < 9) {
            thang = "0" + month + 1;
        } else {
            thang = month + 1 + "";
        }
        int year = cld.get(Calendar.YEAR);
        String firstDayInMonth = year + "-" + thang + "-" + "01";
        LocalDate toDay = this.getToDay();
        Double doanhThuThangNay = tksv.getDoanhThuThangNay(firstDayInMonth, toDay);
        lblTongDoanhThuThangNay.setText(format.format(doanhThuThangNay));
    }

    //Lọc Doanh Thu Theo Ngày
    private void locDoanhThuTheoNgay() {
        doanhThuNgayModel.setRowCount(0);
        Date from = jdcFrom.getDate();
        Date to = jdcTo.getDate();
        if (from == null || to == null) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Đủ Dữ Liệu!");
        } else if (to.before(from)) {
            JOptionPane.showMessageDialog(this, "Sai Dữ Liệu");
        } else {
            SimpleDateFormat spdfm = new SimpleDateFormat("yyyy-MM-dd");
            String tu = spdfm.format(from);
            String den = spdfm.format(to);
            List<Object[]> list = tksv.LocDoanhThuTheoNgay(tu, den);
            for (Object[] row : list) {
                doanhThuNgayModel.addRow(new Object[]{row[0], format.format(row[1])});
            }
        }
    }

    //Doanh Thu Sản Phẩm
    private void DoanhThuSanPham() {
        ThongKeSanPhamModel.setRowCount(0);
        List<Object[]> list = tksv.DoanhThuSanPham();
        for (Object[] row : list) {
            ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
        }
    }

    //Doanh Thu Sản Phẩm Theo Loại Sản Phẩm
    private void DoanhThuSanPhamTheoLoaiSP(String tenLoaiSP) {
        ThongKeSanPhamModel.setRowCount(0);
        List<Object[]> list = tksv.DoanhThuSanPhamTheoLoaiSP(tenLoaiSP);
        for (Object[] row : list) {
            ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
        }
    }

    //Doanh Thu Theo HTTK
    private void DoanhThuHTTK() {
        String httk = (String) cbbHinhThucThongKe.getSelectedItem();
        if (httk != null) {
            if (httk.equalsIgnoreCase("Top Sản Phẩm Doanh Thu Nhiều Nhất")) {
                ThongKeSanPhamModel.setRowCount(0);
                Object[] row = tksv.DoanhThuSanPhamNhieuNhat();
                ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
            }else if(httk.equalsIgnoreCase("Top Sản Phẩm Doanh Thu Thấp Nhất")){
                ThongKeSanPhamModel.setRowCount(0);
                Object[] row = tksv.DoanhThuSanPhamItNhat();
                ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
            }else if(httk.equalsIgnoreCase("Top Sản Phẩm Được Mua Nhiều Nhất")){
                ThongKeSanPhamModel.setRowCount(0);
                Object[] row = tksv.SanPhamBanNhieuNhat();
                ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
            }else if(httk.equalsIgnoreCase("Top Sản Phẩm Được Mua Ít Nhất")){
                ThongKeSanPhamModel.setRowCount(0);
                Object[] row = tksv.SanPhamBanItNhat();
                ThongKeSanPhamModel.addRow(new Object[]{row[0], row[1], row[2], row[3], format.format(row[4]), row[5], format.format(row[6])});
            }else{
                this.DoanhThuSanPham();
            }
        }
    }
    
    //Số Sản Phẩm Đang Kinh Doanh
    private void soSPDangKinhDoanh(){
        int soSP=tksv.soSanPhamDangKinhDoanh();
        lblDangKinhDoanh.setText(soSP+"");
    }
    //Số Sản Phẩm Hết Hàng
    private void soSPHetHang(){
        int soSP=tksv.soSanPhamHetHang();
        lblDangHetHang.setText(soSP+"");
    }
    //Số Sản Phẩm Sắp Hết Hàng
    private void soSPSapHetHang(){
        int soSP=tksv.soSanPhamSapHetHang();
        lblSPSapHetHang.setText(soSP+"");
    }
    //Số Sản Phẩm Hết Hàng
    private void soSPNgungKD(){
        int soSP=tksv.soSanPhamNgungKinhDoanh();
        lblSPNgungKinhDoanh.setText(soSP+"");
    }
    
    // import excel doanh thu theo sản phẩm
    private void ImportExcelDoanhThuTheoSanPham(){
        
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("src\\main\\resources\\excel\\");
        excelFileChooser.setDialogTitle("Save AS");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");

                // Tạo một CellStyle cho các ô in đậm
//                CellStyle boldStyle = excelJTableExporter.createCellStyle();
//                XSSFFont font = excelJTableExporter.createFont();
//                font.setBold(true);
//                boldStyle.setFont(font);
                String[] columnNames = {"Mã SP", "Tên Sản Phẩm", "Tên Loại Sản Phẩm", "Số Lượng Tồn", "Đơn Giá", "Số Lượng Bán Được", "Doanh Thu Từ Sản Phẩm"};
                XSSFRow headerRow = excelSheet.createRow(0);
                for (int j = 0; j < columnNames.length; j++) {
                    XSSFCell cell = headerRow.createCell(j);
                    cell.setCellValue(columnNames[j]);
//                    cell.setCellStyle(boldStyle); // Áp dụng CellStyle cho ô in đậm
                }
                for (int i = 0; i < ThongKeSanPhamModel.getRowCount(); i++) {
                    XSSFRow excelrow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < columnNames.length; j++) {
                        XSSFCell excelCell = excelrow.createCell(j);
                        excelCell.setCellValue(ThongKeSanPhamModel.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Export Successfully");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SanPhamJPanel.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(SanPhamJPanel.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }

                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(SanPhamJPanel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void ImportExcelDoanhThuTheoMocThoiGian(){
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("src\\main\\resources\\excel\\");
        excelFileChooser.setDialogTitle("Save AS");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");

                // Tạo một CellStyle cho các ô in đậm
//                CellStyle boldStyle = excelJTableExporter.createCellStyle();
//                XSSFFont font = excelJTableExporter.createFont();
//                font.setBold(true);
//                boldStyle.setFont(font);
                String[] columnNames = {"Ngày","Doanh Thu"};
                XSSFRow headerRow = excelSheet.createRow(0);
                for (int j = 0; j < columnNames.length; j++) {
                    XSSFCell cell = headerRow.createCell(j);
                    cell.setCellValue(columnNames[j]);
//                    cell.setCellStyle(boldStyle); // Áp dụng CellStyle cho ô in đậm
                }
                for (int i = 0; i < doanhThuNgayModel.getRowCount(); i++) {
                    XSSFRow excelrow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < columnNames.length; j++) {
                        XSSFCell excelCell = excelrow.createCell(j);
                        excelCell.setCellValue(doanhThuNgayModel.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Export Successfully");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SanPhamJPanel.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(SanPhamJPanel.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }

                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(SanPhamJPanel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cbbLoaiSanPham = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbbHinhThucThongKe = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ThongKeSPTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblDangKinhDoanh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblDangHetHang = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblSPSapHetHang = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblSPNgungKinhDoanh = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblDoanhThuCaNamNay = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblTongDoanhThuHomNay = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblTongDoanhThu7NgayGanNhat = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblTongDoanhThuThangNay = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DoanhThuTheoNgayTable = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnFilter = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jdcTo = new com.toedter.calendar.JDateChooser();
        jdcFrom = new com.toedter.calendar.JDateChooser();
        jPanel17 = new javax.swing.JPanel();
        chartJPanel = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbbYear = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cbbLoaiSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiSanPhamActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(100, 180, 244));
        jLabel1.setText("Chọn Hình Thức Thống Kê");

        cbbHinhThucThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbHinhThucThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHinhThucThongKeActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(100, 180, 244));
        jLabel20.setText("Loại Sản Phẩm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(cbbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbbHinhThucThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cbbHinhThucThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnExcel.setBackground(new java.awt.Color(100, 180, 244));
        btnExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_excel.png"))); // NOI18N
        btnExcel.setText("Export To Excel");
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExcelMouseClicked(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        ThongKeSPTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(ThongKeSPTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel11.setBackground(new java.awt.Color(0, 15, 255));

        lblDangKinhDoanh.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDangKinhDoanh.setForeground(new java.awt.Color(255, 255, 255));
        lblDangKinhDoanh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDangKinhDoanh.setText("1");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Số Sản Phẩm Đang Kinh Doanh");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(lblDangKinhDoanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDangKinhDoanh)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(25, 25, 25))
        );

        jPanel12.setBackground(new java.awt.Color(255, 117, 99));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Số Sản Phẩm Hết Hàng");

        lblDangHetHang.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDangHetHang.setForeground(new java.awt.Color(255, 255, 255));
        lblDangHetHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDangHetHang.setText("2");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(lblDangHetHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDangHetHang)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(25, 25, 25))
        );

        jPanel13.setBackground(new java.awt.Color(255, 154, 0));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Số Sản Phẩm Sắp Hết Hàng");

        lblSPSapHetHang.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblSPSapHetHang.setForeground(new java.awt.Color(255, 255, 255));
        lblSPSapHetHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPSapHetHang.setText("3");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(lblSPSapHetHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSPSapHetHang)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(25, 25, 25))
        );

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));

        lblSPNgungKinhDoanh.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblSPNgungKinhDoanh.setForeground(new java.awt.Color(255, 255, 255));
        lblSPNgungKinhDoanh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSPNgungKinhDoanh.setText("4");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Số Sản Phẩm Ngừng Kinh Doanh");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSPNgungKinhDoanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(lblSPNgungKinhDoanh)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh Thu Theo Sản Phẩm", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBackground(new java.awt.Color(0, 15, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Danh Thu Cả Năm Nay");

        lblDoanhThuCaNamNay.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblDoanhThuCaNamNay.setForeground(new java.awt.Color(255, 255, 255));
        lblDoanhThuCaNamNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoanhThuCaNamNay.setText("1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDoanhThuCaNamNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDoanhThuCaNamNay)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(31, 31, 31))
        );

        jPanel6.setBackground(new java.awt.Color(0, 172, 0));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Danh Thu Hôm Nay");

        lblTongDoanhThuHomNay.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTongDoanhThuHomNay.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuHomNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThuHomNay.setText("2");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(lblTongDoanhThuHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTongDoanhThuHomNay)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(33, 33, 33))
        );

        jPanel15.setBackground(new java.awt.Color(255, 24, 0));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Doanh Thu 7 Ngày Gần Nhất");

        lblTongDoanhThu7NgayGanNhat.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTongDoanhThu7NgayGanNhat.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThu7NgayGanNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThu7NgayGanNhat.setText("3");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(lblTongDoanhThu7NgayGanNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTongDoanhThu7NgayGanNhat)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(29, 29, 29))
        );

        jPanel16.setBackground(new java.awt.Color(255, 154, 0));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Doanh Thu Tháng Này");

        lblTongDoanhThuThangNay.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTongDoanhThuThangNay.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhThuThangNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongDoanhThuThangNay.setText("4");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(lblTongDoanhThuThangNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblTongDoanhThuThangNay)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        DoanhThuTheoNgayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ngày", "Doanh Thu"
            }
        ));
        jScrollPane2.setViewportView(DoanhThuTheoNgayTable);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(100, 180, 244));
        jLabel18.setText("Từ");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(100, 180, 244));
        jLabel19.setText("Đến");

        btnFilter.setBackground(new java.awt.Color(100, 180, 244));
        btnFilter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFilter.setForeground(new java.awt.Color(255, 255, 255));
        btnFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/filter.png"))); // NOI18N
        btnFilter.setText("Lọc");
        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFilterMouseClicked(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(100, 180, 244));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rsz_excel.png"))); // NOI18N
        jButton2.setText("Export To Excel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jdcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jdcTo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jdcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(17, 17, 17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Doanh Thu Theo Mốc Thời Gian", jPanel2);

        chartJPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout chartJPanelLayout = new javax.swing.GroupLayout(chartJPanel);
        chartJPanel.setLayout(chartJPanelLayout);
        chartJPanelLayout.setHorizontalGroup(
            chartJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartJPanelLayout.setVerticalGroup(
            chartJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 661, Short.MAX_VALUE)
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setForeground(new java.awt.Color(100, 180, 244));
        jLabel2.setText("Lọc Theo");

        cbbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoc.setBackground(new java.awt.Color(100, 180, 244));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setText("Lọc");
        btnLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLocMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cbbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addContainerGap(970, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLoc))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Biểu Đồ", jPanel17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFilterMouseClicked
        // TODO add your handling code here:
        this.locDoanhThuTheoNgay();
    }//GEN-LAST:event_btnFilterMouseClicked

    private void cbbLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiSanPhamActionPerformed
        // TODO add your handling code here:
        String tenLoaiSP = (String) cbbLoaiSanPham.getSelectedItem();
        if (tenLoaiSP != null) {
            this.DoanhThuSanPhamTheoLoaiSP(tenLoaiSP);
        }
    }//GEN-LAST:event_cbbLoaiSanPhamActionPerformed

    private void cbbHinhThucThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThongKeActionPerformed
        // TODO add your handling code here:
        this.DoanhThuHTTK();
    }//GEN-LAST:event_cbbHinhThucThongKeActionPerformed

    private void btnLocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocMouseClicked
        // TODO add your handling code here:
        String year=(String) cbbYear.getSelectedItem();
        Integer nam=Integer.parseInt(year);
        ThongKeController tkc=new ThongKeController();
        tkc.setDateToChart(chartJPanel,nam);
    }//GEN-LAST:event_btnLocMouseClicked

    private void btnExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseClicked
        // TODO add your handling code here:
        this.ImportExcelDoanhThuTheoSanPham();
    }//GEN-LAST:event_btnExcelMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        this.ImportExcelDoanhThuTheoMocThoiGian();
    }//GEN-LAST:event_jButton2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DoanhThuTheoNgayTable;
    private javax.swing.JTable ThongKeSPTable;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnLoc;
    private javax.swing.JComboBox<String> cbbHinhThucThongKe;
    private javax.swing.JComboBox<String> cbbLoaiSanPham;
    private javax.swing.JComboBox<String> cbbYear;
    private javax.swing.JPanel chartJPanel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcFrom;
    private com.toedter.calendar.JDateChooser jdcTo;
    private javax.swing.JLabel lblDangHetHang;
    private javax.swing.JLabel lblDangKinhDoanh;
    private javax.swing.JLabel lblDoanhThuCaNamNay;
    private javax.swing.JLabel lblSPNgungKinhDoanh;
    private javax.swing.JLabel lblSPSapHetHang;
    private javax.swing.JLabel lblTongDoanhThu7NgayGanNhat;
    private javax.swing.JLabel lblTongDoanhThuHomNay;
    private javax.swing.JLabel lblTongDoanhThuThangNay;
    // End of variables declaration//GEN-END:variables
}
