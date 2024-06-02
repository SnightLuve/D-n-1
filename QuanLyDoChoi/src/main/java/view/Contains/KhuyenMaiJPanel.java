/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Contains;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdbc.DBConnect;
import model.NhanVien;
import model.Voucher;
import serviceImpl.NhanVienServiceImpl;
import serviceImpl.VoucherServiceImpl;
import utils.Auth;

/**
 *
 * @author ACER
 */
public class KhuyenMaiJPanel extends javax.swing.JPanel {

    //Call Model
    DefaultTableModel model = new DefaultTableModel();
    //Call ServiceImpl
    VoucherServiceImpl vcsv = new VoucherServiceImpl();
    NhanVienServiceImpl nvsv = new NhanVienServiceImpl();
    //Call List
    List<Voucher> listVC = new ArrayList<>();

    public KhuyenMaiJPanel() {
        initComponents();
        this.fillCbb();
        this.fillCbbFind();
        model = (DefaultTableModel) VoucherTable.getModel();
        this.setTitle();
        this.fillData();
        this.fillCbbTrangThai();
        jdcNgayBatDau.setDateFormatString("yyyy-MM-dd");
        jdcNgayKetThuc.setDateFormatString("yyyy-MM-dd");
        VoucherTable.setRowHeight(50);
    }

    private void setTitle() {
        String header[] = {"STT", "Mã Voucher", "Tên Voucher", "Loại", "Giá Trị", "Số Lượng", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"};
        model.setColumnIdentifiers(header);
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

    private void fillData() {
        listVC = vcsv.getAll();
        showData(listVC);
    }

    //fill Cbb
    private void fillCbb() {
        cbbLoaiVC.removeAllItems();
        cbbLoaiVC.addItem("%");
        cbbLoaiVC.addItem("VNĐ");
    }

    //fill Cbb
    private void fillCbbFind() {
        findByLoai.removeAllItems();
        findByLoai.addItem("%");
        findByLoai.addItem("VNĐ");
    }

    //fill Cbb Trạng Thái
    private void fillCbbTrangThai() {
        cbbTrangThai.removeAllItems();
        cbbTrangThai.addItem("Hiện Có");
        cbbTrangThai.addItem("Đã Xóa");
    }

    //Tự Động GenerateMa
    private String gennerateMa() {
        Voucher vc = vcsv.selectTop1DESC();
        String maVCSub = vc.getMaVoucher().substring(2);
        Integer soMaVC = Integer.parseInt(maVCSub);
        String maKHFinal = "VC" + (soMaVC + 1);
        return maKHFinal;
    }

    private void showData(List<Voucher> listVC) {
        model.setRowCount(0);
        int i = 0;
        for (Voucher vc : listVC) {
            i++;
            String loaiVC = "";
            if (vc.isLoaiVoucher() == true) {
                loaiVC = "%";
            } else {
                loaiVC = "VNĐ";
            }
            String trangThai = "";
            if (vc.getTrangThai() == 1) {
                trangThai = "Đang Áp Dụng";
            } else if (vc.getTrangThai() == 2) {
                trangThai = "Hết Hạn";
            } else if (vc.getTrangThai() == 3) {
                trangThai = "Sắp Diễn Ra";
            } else if (vc.getTrangThai() == 4) {
                trangThai = "Tạm Ngưng";
            }
            Object row[] = {i, vc.getMaVoucher(), vc.getTenVoucher(), loaiVC, vc.getGiaTri(), vc.getSoLuong(), vc.getNgayBatDau(), vc.getNgayKetThuc(), trangThai};
            model.addRow(row);
        }
    }

    //check VOucher
    private Voucher checkVoucher() {
        String ma = gennerateMa();
        String ten = txtTenVC.getText();
        String giaTriBefore = txtGiaTri.getText();
        Double giaTriAfter = 0.0;
        String soLuongBefore = txtSoLuong.getText();
        Integer soLuongAfter = 0;

        String giaTriToiThieuBefore = txtGiaTriToiThieu.getText();
        Double giaTriToiThieuAfter = 0.0;
        String giaTriToiDaBefore = txtGiaTriToiDa.getText();
        Double giaTriToiDaAfter = 0.0;

        String loaiVC = (String) cbbLoaiVC.getSelectedItem();
        boolean loaiVCAfter;
        if (loaiVC.equalsIgnoreCase("%")) {
            loaiVCAfter = true;
        } else {
            loaiVCAfter = false;
        }
        Date ngayBatDau = jdcNgayBatDau.getDate();
        Date ngayKetThuc = jdcNgayKetThuc.getDate();

        int trangThai = 0;

        int count = 0;

        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Voucher Không Được Trống!");
        } else {
            if (giaTriBefore.trim().isEmpty()) {
                count++;
                JOptionPane.showMessageDialog(this, "Giá Trị Voucher Không Được Trống!");
            } else {
                try {
                    giaTriAfter = Double.parseDouble(giaTriBefore);
                    if (loaiVC.equalsIgnoreCase("%") && giaTriAfter > 80) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Giá Trị Giảm Không Được Lớn Hơn 80%");
                    } else {
                        if (giaTriAfter <= 0) {
                            count++;
                            JOptionPane.showMessageDialog(this, "Giá Trị Voucher Không Được Nhỏ Hơn 0!");
                        } else {
                            if (giaTriToiThieuBefore.trim().isEmpty()) {
                                count++;
                                JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Không Được Trống!");
                            } else {
                                try {
                                    giaTriToiThieuAfter = Double.parseDouble(giaTriToiThieuBefore);
                                    if (giaTriToiThieuAfter < 0) {
                                        count++;
                                        JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Không Được Nhỏ Hơn 0!");
                                    } else {
                                        if (giaTriToiDaBefore.trim().isEmpty()) {
                                            count++;
                                            JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Không Được Trống!");
                                        } else {
                                            try {
                                                giaTriToiDaAfter = Double.parseDouble(giaTriToiDaBefore);
                                                if (giaTriToiDaAfter < 0) {
                                                    count++;
                                                    JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Không Được Nhỏ Hơn 0!");
                                                } else {
                                                    if (soLuongBefore.trim().isEmpty()) {
                                                        count++;
                                                        JOptionPane.showMessageDialog(this, "Số Lượng Voucher Không Được Chống!");
                                                    } else {
                                                        try {
                                                            soLuongAfter = Integer.parseInt(soLuongBefore);
                                                            if (soLuongAfter <= 0) {
                                                                count++;
                                                                JOptionPane.showMessageDialog(this, "Số Lượng Voucher Phải Lớn Hơn 0");
                                                            } else {
                                                                if (ngayBatDau == null) {
                                                                    count++;
                                                                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Bắt Đầu!");
                                                                } else {
                                                                    if (ngayKetThuc == null) {
                                                                        count++;
                                                                        JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Kết Thúc!");
                                                                    } else {
                                                                        int compareDate = ngayBatDau.compareTo(ngayKetThuc);
                                                                        if (compareDate > 0) {
                                                                            count++;
                                                                            JOptionPane.showMessageDialog(this, "Ngày Kết Thúc Phải Lớn Hơn Ngày Bắt Đầu!");
                                                                        } else {
                                                                            this.generateBarcode(ma);
//                                                                            LocalDate toDay1 = this.getToDay();
//                                                                            java.sql.Date toDay = java.sql.Date.valueOf(toDay1);
//                                                                            int compare1 = ngayBatDau.compareTo(toDay);
//                                                                            int compare2 = ngayKetThuc.compareTo(toDay);
//
//                                                                            if (compare1 > 0) {
//                                                                                trangThai = 3;
//                                                                            } else if (compare1 < 0 && compare2 > 0) {
//                                                                                trangThai = 1;
//                                                                            } else if (compare2 < 0) {
//                                                                                trangThai = 2;
//                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            count++;
                                                            JOptionPane.showMessageDialog(this, "Số Lượng Voucher Phải Là Số!");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                count++;
                                                JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Phải Là Số");
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    count++;
                                    JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Phải Là Số!");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Giá Trị Voucher Phải Là Số!");
                }
            }
        }

        if (count == 0) {
            Voucher vc = new Voucher(ma, ten, loaiVCAfter, giaTriAfter, soLuongAfter, trangThai, giaTriToiThieuAfter, giaTriToiDaAfter, ngayBatDau, ngayKetThuc, Auth.idNhanVien(), false);
            return vc;
        } else {
            return null;
        }
    }

    //check Update Voucher
    private Voucher checkUpdateVoucher() {
        int selected = VoucherTable.getSelectedRow();
        String ma = (String) VoucherTable.getValueAt(selected, 1);
        String ten = txtTenVC.getText();
        String giaTriBefore = txtGiaTri.getText();
        Double giaTriAfter = 0.0;
        String soLuongBefore = txtSoLuong.getText();
        Integer soLuongAfter = 0;

        String giaTriToiThieuBefore = txtGiaTriToiThieu.getText();
        Double giaTriToiThieuAfter = 0.0;
        String giaTriToiDaBefore = txtGiaTriToiDa.getText();
        Double giaTriToiDaAfter = 0.0;

        String loaiVC = (String) cbbLoaiVC.getSelectedItem();
        boolean loaiVCAfter;
        if (loaiVC.equalsIgnoreCase("%")) {
            loaiVCAfter = true;
        } else {
            loaiVCAfter = false;
        }
        Date ngayBatDau = jdcNgayBatDau.getDate();
        Date ngayKetThuc = jdcNgayKetThuc.getDate();

        int trangThai = 0;

        int count = 0;

        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Voucher Không Được Trống!");
        } else {
            if (giaTriBefore.trim().isEmpty()) {
                count++;
                JOptionPane.showMessageDialog(this, "Giá Trị Voucher Không Được Trống!");
            } else {
                try {
                    giaTriAfter = Double.parseDouble(giaTriBefore);
                    if (loaiVC.equalsIgnoreCase("%") && giaTriAfter > 80) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Giá Trị Giảm Không Được Lớn Hơn 80%");
                    } else {
                        if (giaTriAfter <= 0) {
                            count++;
                            JOptionPane.showMessageDialog(this, "Giá Trị Voucher Không Được Nhỏ Hơn 0!");
                        } else {
                            if (giaTriToiThieuBefore.trim().isEmpty()) {
                                count++;
                                JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Không Được Trống!");
                            } else {
                                try {
                                    giaTriToiThieuAfter = Double.parseDouble(giaTriToiThieuBefore);
                                    if (giaTriToiThieuAfter < 0) {
                                        count++;
                                        JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Không Được Nhỏ Hơn 0!");
                                    } else {
                                        if (giaTriToiDaBefore.trim().isEmpty()) {
                                            count++;
                                            JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Không Được Trống!");
                                        } else {
                                            try {
                                                giaTriToiDaAfter = Double.parseDouble(giaTriToiDaBefore);
                                                if (giaTriToiDaAfter < 0) {
                                                    count++;
                                                    JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Không Được Nhỏ Hơn 0!");
                                                } else {
                                                    if (soLuongBefore.trim().isEmpty()) {
                                                        count++;
                                                        JOptionPane.showMessageDialog(this, "Số Lượng Voucher Không Được Chống!");
                                                    } else {
                                                        try {
                                                            soLuongAfter = Integer.parseInt(soLuongBefore);
                                                            if (soLuongAfter <= 0) {
                                                                count++;
                                                                JOptionPane.showMessageDialog(this, "Số Lượng Voucher Phải Lớn Hơn 0");
                                                            } else {
                                                                if (ngayBatDau == null) {
                                                                    count++;
                                                                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Bắt Đầu!");
                                                                } else {
                                                                    if (ngayKetThuc == null) {
                                                                        count++;
                                                                        JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Kết Thúc!");
                                                                    } else {
                                                                        int compareDate = ngayBatDau.compareTo(ngayKetThuc);
                                                                        if (compareDate > 0) {
                                                                            count++;
                                                                            JOptionPane.showMessageDialog(this, "Ngày Kết Thúc Phải Lớn Hơn Ngày Bắt Đầu!");
                                                                        } else {
                                                                            this.generateBarcode(ma);
//                                                                            LocalDate toDay1 = this.getToDay();
//                                                                            java.sql.Date toDay = java.sql.Date.valueOf(toDay1);
//                                                                            int compare1 = ngayBatDau.compareTo(toDay);
//                                                                            int compare2 = ngayKetThuc.compareTo(toDay);
//
//                                                                            if (compare1 > 0) {
//                                                                                trangThai = 3;
//                                                                            } else if (compare1 < 0 && compare2 > 0) {
//                                                                                trangThai = 1;
//                                                                            } else if (compare2 < 0) {
//                                                                                trangThai = 2;
//                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } catch (Exception e) {
                                                            count++;
                                                            JOptionPane.showMessageDialog(this, "Số Lượng Voucher Phải Là Số!");
                                                        }
                                                    }
                                                }
                                            } catch (Exception e) {
                                                count++;
                                                JOptionPane.showMessageDialog(this, "Giá Trị Tối Đa Voucher Phải Là Số");
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    count++;
                                    JOptionPane.showMessageDialog(this, "Giá Trị Tối Thiểu Voucher Phải Là Số!");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Giá Trị Voucher Phải Là Số!");
                }
            }
        }
        LocalDate updateAt = this.getToDay();

        if (count == 0) {
            Voucher vc = new Voucher(ten, loaiVCAfter, giaTriAfter, soLuongAfter, trangThai, giaTriToiThieuAfter, giaTriToiDaAfter, ngayBatDau, ngayKetThuc, updateAt, Auth.idNhanVien());
            return vc;
        } else {
            return null;
        }
    }

    private void addVoucher() {
        Voucher vc = checkVoucher();
        if (vc != null) {
            boolean add = vcsv.addVoucher(vc);
            if (add == true) {
                vcsv.updateTrangThaiVoucherDayByDay(this.getToDay());
                this.fillData();
                this.clearText();
                JOptionPane.showMessageDialog(this, "Thêm Thành Công Voucher!");
            }
        }
    }

    private void updateVoucher() {
        int selected = VoucherTable.getSelectedRow();
        if (selected >= 0) {
            String maVoucher = (String) VoucherTable.getValueAt(selected, 1);
            Voucher vc = checkUpdateVoucher();
            if (vc != null) {
                boolean update = vcsv.updateVoucher(maVoucher, vc);
                if (update == true) {
                    vcsv.updateTrangThaiVoucherDayByDay(this.getToDay());
                    this.fillData();
                    this.clearText();
                    JOptionPane.showMessageDialog(this, "Update Voucher Thành Công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Dòng Nào!");
        }
    }

    //fill text
    private void fillText() {
        int selected = VoucherTable.getSelectedRow();
        Voucher vc = listVC.get(selected);
        txtTenVC.setText(vc.getTenVoucher());
        if (vc.isLoaiVoucher() == true) {
            cbbLoaiVC.setSelectedIndex(0);
        } else {
            cbbLoaiVC.setSelectedIndex(1);
        }

        txtGiaTri.setText(vc.getGiaTri() + "");
        txtSoLuong.setText(vc.getSoLuong() + "");
        jdcNgayBatDau.setDate(vc.getNgayBatDau());
        jdcNgayKetThuc.setDate(vc.getNgayKetThuc());
        txtGiaTriToiThieu.setText(vc.getGiaTriToiThieu() + "");
        txtGiaTriToiDa.setText(vc.getGiaTriToiDa() + "");
    }

    //Trạng Thái Voucher
    private void trangThaiVoucher() {
        int selected = VoucherTable.getSelectedRow();
        if (selected >= 0) {
            String maVoucher = (String) VoucherTable.getValueAt(selected, 1);
            Voucher vc = vcsv.findVCByMa(maVoucher);
            if (vc.getTrangThai() == 4 || vc.getTrangThai() == 2) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Áp Dụng Lại Voucher Này Không ?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    LocalDate toDay1 = this.getToDay();
                    java.sql.Date toDay = java.sql.Date.valueOf(toDay1);
                    int compare1 = vc.getNgayKetThuc().compareTo(toDay);
                    if (compare1 < 0) {
                        JOptionPane.showMessageDialog(this, "Voucher Này Đã Hết Hạn Không Thể Tiếp Tục!");
                    } else {
                        boolean trangThai = vcsv.trangThaiVoucher(1, maVoucher);
                        if (trangThai == true) {
                            this.fillData();
                            JOptionPane.showMessageDialog(this, "Áp Dụng Lại Thành Công!");
                        }
                    }
                }
            } else if (vc.getTrangThai() == 1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Ngưng Voucher Này Không ?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean trangThai = vcsv.trangThaiVoucher(4, maVoucher);
                    if (trangThai == true) {
                        this.fillData();
                        JOptionPane.showMessageDialog(this, "Ngưng Voucher Thành Công!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "Voucher Này Sắp Diễn Ra Không Thể Thay Đổi Trạng Thái!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Dòng Nào!");
        }
    }

    //Delete Voucher
    private void deleteVoucher() {
        int selected = VoucherTable.getSelectedRow();
        if (selected >= 0) {
            Voucher vc = listVC.get(selected);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Xóa Voucher Này?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean delete = vcsv.deleteVoucher(vc.getMaVoucher());
                if (delete == true) {
                    this.fillData();
                    this.clearText();
                    JOptionPane.showMessageDialog(this, "Xóa Voucher Thành Công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Voucher Nào!");
        }
    }

    //Mã Barcode
    private void generateBarcode(String data) {
        try {
            String path = "src\\main\\resources\\QRVoucher\\" + data + ".png";
            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, 500, 200);
            MatrixToImageWriter.writeToPath(matrix, "png", Paths.get(path));
        } catch (Exception e) {

        }
    }

    //find VOucher By Ma
    private void findVCByMa() {
        String maVC = findVoucher.getText();
        listVC = vcsv.findVCByMa1(maVC);
        showData(listVC);
    }

    private void findVCByLoai() {
        String loaiVC = (String) findByLoai.getSelectedItem();
        if (loaiVC != null) {
            boolean loaiVC1;
            if (loaiVC.equalsIgnoreCase("%")) {
                loaiVC1 = true;
            } else {
                loaiVC1 = false;
            }
            listVC = vcsv.findVCByLoai(loaiVC1);
            showData(listVC);
        }
    }

    private void findByTrangThai() {
        String trangThai = (String) cbbTrangThai.getSelectedItem();
        if (trangThai != null) {
            if (trangThai.equalsIgnoreCase("Đã Xóa")) {
                listVC = vcsv.findVCByTrangThai(1);
                showData(listVC);
            } else {
                listVC = vcsv.findVCByTrangThai(0);
                showData(listVC);
            }
        }
    }

    private void clearText() {
        txtTenVC.setText("");
        cbbLoaiVC.setSelectedIndex(0);
        txtGiaTri.setText("");
        txtGiaTriToiThieu.setText("");
        txtGiaTriToiDa.setText("");
        txtSoLuong.setText("");
        jdcNgayBatDau.setDate(null);
        jdcNgayKetThuc.setDate(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTenVC = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbbLoaiVC = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtGiaTri = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jdcNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jdcNgayKetThuc = new com.toedter.calendar.JDateChooser();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiaTriToiThieu = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtGiaTriToiDa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VoucherTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        findVoucher = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        findByLoai = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbbTrangThai = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 113, 116));
        jLabel2.setText("Voucher");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Tên Voucher");

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Loại Voucher");

        cbbLoaiVC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Giá Trị");

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Ngày Bắt Đầu");

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ngày Kết Thúc");

        btnThem.setBackground(new java.awt.Color(100, 180, 244));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(100, 180, 244));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaMouseClicked(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(100, 180, 244));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Số Lượng");

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Giá Trị Tối Thiểu");

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Giá Trị Tối Đa");

        jButton1.setBackground(new java.awt.Color(100, 180, 244));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-installing-updates-24.png"))); // NOI18N
        jButton1.setText("Trạng Thái");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel12)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenVC)
                            .addComponent(cbbLoaiVC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGiaTri)
                            .addComponent(txtSoLuong)
                            .addComponent(txtGiaTriToiThieu)
                            .addComponent(txtGiaTriToiDa)
                            .addComponent(jdcNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdcNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenVC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbbLoaiVC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGiaTri, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtGiaTriToiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtGiaTriToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Danh Sách Khuyến Mãi");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        VoucherTable.setModel(new javax.swing.table.DefaultTableModel(
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
        VoucherTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VoucherTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(VoucherTable);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tìm Kiếm Voucher");

        findVoucher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findVoucherKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Loại");

        findByLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        findByLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findByLoaiActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Trạng Thái");

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(findVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(findByLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(findVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(findByLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Khuyến Mãi", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        // TODO add your handling code here:
        this.addVoucher();
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseClicked
        // TODO add your handling code here:
        this.updateVoucher();
    }//GEN-LAST:event_btnSuaMouseClicked

    private void VoucherTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VoucherTableMouseClicked
        // TODO add your handling code here:
        this.fillText();
    }//GEN-LAST:event_VoucherTableMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        // TODO add your handling code here:
        this.deleteVoucher();
    }//GEN-LAST:event_btnXoaMouseClicked

    private void findVoucherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findVoucherKeyReleased
        // TODO add your handling code here:
        this.findVCByMa();
    }//GEN-LAST:event_findVoucherKeyReleased

    private void findByLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findByLoaiActionPerformed
        // TODO add your handling code here:
        this.findVCByLoai();
    }//GEN-LAST:event_findByLoaiActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        this.findByTrangThai();
    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        this.trangThaiVoucher();
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable VoucherTable;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbLoaiVC;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JComboBox<String> findByLoai;
    private javax.swing.JTextField findVoucher;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcNgayBatDau;
    private com.toedter.calendar.JDateChooser jdcNgayKetThuc;
    private javax.swing.JTextField txtGiaTri;
    private javax.swing.JTextField txtGiaTriToiDa;
    private javax.swing.JTextField txtGiaTriToiThieu;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenVC;
    // End of variables declaration//GEN-END:variables
}
