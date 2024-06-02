/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Contains;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import model.ChiTietSanPham;
import model.HinhThucThanhToan;
import model.HoaDon;
import model.KhachHang;
import model.NhanVien;
import model.SanPham;
import model.SanPhamHoan;
import model.TrangThaiHoaDon;
import model.Voucher;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.VoucherService;
import serviceImpl.ChiTietSanPhamServiceImpl;
import serviceImpl.HTTTServiceImpl;
import serviceImpl.HoaDonServiceImpl;
import serviceImpl.KhachHangServiceImpl;
import serviceImpl.NhanVienServiceImpl;
import serviceImpl.SanPhamServiceImpl;
import serviceImpl.TTHDServiceImpl;
import serviceImpl.VoucherServiceImpl;
import view.Contain.EntitySanPham.ScanQRHoaDon;

/**
 *
 * @author ACER
 */
public class HoaDonJPanel extends javax.swing.JPanel {

    //Call Default Model
    DefaultTableModel modelHD = new DefaultTableModel();
    DefaultTableModel modelHDCT = new DefaultTableModel();
    DefaultTableModel modelLSHD = new DefaultTableModel();
    DefaultTableModel modelSPDM = new DefaultTableModel();
    DefaultTableModel modelSPHoan = new DefaultTableModel();
    //Call Service
    NhanVienServiceImpl nvsv = new NhanVienServiceImpl();
    TTHDServiceImpl tthdsv = new TTHDServiceImpl();
    HTTTServiceImpl htttsv = new HTTTServiceImpl();
    HoaDonServiceImpl hdsv = new HoaDonServiceImpl();
    KhachHangServiceImpl khsv = new KhachHangServiceImpl();
    ChiTietSanPhamServiceImpl ctspsv = new ChiTietSanPhamServiceImpl();
    SanPhamServiceImpl spsv = new SanPhamServiceImpl();
    VoucherService vcsv = new VoucherServiceImpl();
    //Call List
    List<HoaDon> listHD = new ArrayList<>();
    //Convert tiền tệ
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public HoaDonJPanel() {
        initComponents();
        modelHD = (DefaultTableModel) HoaDonTable.getModel();
        modelHDCT = (DefaultTableModel) HoaDonChiTietTable.getModel();
        modelLSHD = (DefaultTableModel) LichSuHoaDonTable.getModel();
        modelSPDM = (DefaultTableModel) SanPhamDaMuaTable.getModel();
        this.setTitleHoaDon();
        this.setTitleHoaDonChiTiet();
        this.setTitleLichSuHoaDon();
        this.setTitleSanPhamDaMua();
        this.fillCbbTTHD();
        this.resetText();
        modelHDCT.setRowCount(0);
        modelSPDM.setRowCount(0);
        modelSPHoan.setRowCount(0);
//        this.fillDataHoaDon();
//        modelHDCT.setRowCount(0);
        this.showDataHoaDon(1);
        this.showDataLichSuHoaDon();
        this.totalSoTrang();
    }

    //Phân Trang
    private void totalSoTrang() {
        int total = 0;
        if (hdsv.countHD() % 4 == 0) {
            total = (hdsv.countHD() / 4);
        } else {
            total = (hdsv.countHD() / 4) + 1;
        }
        lblTotal.setText(total + "");
    }

    private void setTitleHoaDon() {
        String header[] = {"STT", "Mã HĐ", "Mã Nhân Viên", "Tên Nhân Viên", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Tiền", "Ngày Tạo", "Trạng Thái"};
        HoaDonTable.setRowHeight(49);
        modelHD.setColumnIdentifiers(header);
    }

    private void setTitleHoaDonChiTiet() {
        String header[] = {"STT", "Mã HĐ", "Mã Sản Phẩm", "Tên Sản Phẩm", "Thương Hiệu", "Xuất Xứ", "Khối Lượng", "Đơn Vị Tính", "Chất Liệu", "Loại Sản Phẩm", "Số Lượng", "Đơn Giá", "Tổng Tiền"};
        HoaDonChiTietTable.setRowHeight(49);
        modelHDCT.setColumnIdentifiers(header);
    }

    private void setTitleLichSuHoaDon() {
        String header[] = {"STT", "Mã HĐ", "Mã Nhân Viên", "Tên Nhân Viên", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Tiền", "Ngày Tạo", "Trạng Thái"};
        LichSuHoaDonTable.setRowHeight(50);
        modelLSHD.setColumnIdentifiers(header);
    }

    private void setTitleSanPhamDaMua() {
        SanPhamDaMuaTable.setRowHeight(50);
    }

    //show Data Hoa Don
    private void showDataHoaDon(int index) {
        modelHD.setRowCount(0);
        int i = 0;
        List<Object[]> list = hdsv.getHoaDon(index);
        for (Object[] row : list) {
            i++;
            modelHD.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
        }
    }

    //show Data SanPhamHoaDon
    private void showDataHoaDonChiTiet(String maHoaDon) {
        modelHDCT.setRowCount(0);
        int i = 0;
        List<Object[]> list = hdsv.getHoaDonChiTiet(maHoaDon);
        for (Object[] row : list) {
            i++;
            modelHDCT.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], format.format(row[10]), format.format(row[11])});
        }
    }

    //show Data Lich Su Hoa Don
    private void showDataLichSuHoaDon() {
        modelLSHD.setRowCount(0);
        int i = 0;
        List<Object[]> list = hdsv.getLichSuHoaDon();
        for (Object[] row : list) {
            i++;
            modelLSHD.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
        }
    }

    //Sản Phẩm Hóa Đơn
    private void SanPhamHoaDon() {
        int selected = HoaDonTable.getSelectedRow();
        if (selected >= 0) {
            String maHoaDon = (String) HoaDonTable.getValueAt(selected, 1);
//            String idHoaDonSub = idHoaDon.substring(2);
            this.showDataHoaDonChiTiet(maHoaDon);
        }
    }

    //Chi Tiết Lịch Sử Hóa Đơn
    private void chiTietLSHoaDon() {
        int selected = LichSuHoaDonTable.getSelectedRow();
        if (selected >= 0) {
            String maHoaDon = (String) LichSuHoaDonTable.getValueAt(selected, 1);
//            String idHoaDonSub = idHoaDon.substring(2);
            Object[] list = hdsv.getChiTietLSHoaDon(maHoaDon);
            lblMaHD.setText(String.valueOf(list[0]));
            lblTenNV.setText(String.valueOf(list[1]));
            lblTenKH.setText(String.valueOf(list[2]));
            lblSDTKH.setText(String.valueOf(list[3]));
            lblGiamGiaDiem.setText(String.valueOf(format.format(list[4])));
            lblTenNguoiNhan.setText(String.valueOf(list[5]));
            lblSDTNN.setText(String.valueOf(list[6]));
            lblDCNguoiNhan.setText(String.valueOf(list[7]));
            lblTGTao.setText(String.valueOf(list[8]));
            lblGhiChu.setText(String.valueOf(list[9]));
            lblHTTT.setText(String.valueOf(list[10]));
            lblTTHD.setText(String.valueOf(list[11]));

            lblTongTien.setText(String.valueOf(format.format(list[12])));
            lblTienKhachDua.setText(String.valueOf(format.format(list[13])));
            lblKhachCK.setText(String.valueOf(format.format(list[14])));
            lblTienThua.setText(String.valueOf(format.format(list[15])));
            lblShipDuTinh.setText(String.valueOf(list[16]));
            lblDenDuTinh.setText(String.valueOf(list[17]));
            lblPhiShip.setText(String.valueOf(format.format(list[18])));
            lblMaGiaoDich.setText(String.valueOf(list[19]));
        }
    }

    public void ChiTietLSHoaDonQR(String maQR) {
        Object[] list = hdsv.getChiTietLSHoaDon(maQR);
        if (list == null) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        } else {
            lblMaHD.setText(String.valueOf(list[0]));
            lblTenNV.setText(String.valueOf(list[1]));
            lblTenKH.setText(String.valueOf(list[2]));
            lblSDTKH.setText(String.valueOf(list[3]));
            lblGiamGiaDiem.setText(String.valueOf(format.format(list[4])));
            lblTenNguoiNhan.setText(String.valueOf(list[5]));
            lblSDTNN.setText(String.valueOf(list[6]));
            lblDCNguoiNhan.setText(String.valueOf(list[7]));
            lblTGTao.setText(String.valueOf(list[8]));
            lblGhiChu.setText(String.valueOf(list[9]));
            lblHTTT.setText(String.valueOf(list[10]));
            lblTTHD.setText(String.valueOf(list[11]));

            lblTongTien.setText(String.valueOf(format.format(list[12])));
            lblTienKhachDua.setText(String.valueOf(format.format(list[13])));
            lblKhachCK.setText(String.valueOf(format.format(list[14])));
            lblTienThua.setText(String.valueOf(format.format(list[15])));
            lblShipDuTinh.setText(String.valueOf(list[16]));
            lblDenDuTinh.setText(String.valueOf(list[17]));
            lblPhiShip.setText(String.valueOf(format.format(list[18])));
            lblMaGiaoDich.setText(String.valueOf(list[19]));
        }
    }

    //reset Text
    private void resetText() {
        lblMaHD.setText("");
        lblTenNV.setText("");
        lblTenKH.setText("");
        lblSDTKH.setText("");
        lblGiamGiaDiem.setText("");
        lblTenNguoiNhan.setText("");
        lblSDTNN.setText("");
        lblDCNguoiNhan.setText("");
        lblTGTao.setText("");
        lblGhiChu.setText("");
        lblHTTT.setText("");
        lblTTHD.setText("");
        lblTongTien.setText("");
        lblTienKhachDua.setText("");
        lblKhachCK.setText("");
        lblTienThua.setText("");
        lblShipDuTinh.setText("");
        lblDenDuTinh.setText("");
        lblPhiShip.setText("");
        lblMaGiaoDich.setText("");
    }

    //Sản Phẩm Lịch Sử Hóa Đơn
    private void showDataSanPhamLichSuHoaDon() {
        modelSPDM.setRowCount(0);
        int selected = LichSuHoaDonTable.getSelectedRow();
        int i = 0;
        if (selected >= 0) {
            String maHoaDon = (String) LichSuHoaDonTable.getValueAt(selected, 1);
//            JOptionPane.showMessageDialog(this, "Mã HĐ:"+maHoaDon+"ĐỘ Dài"+maHoaDon.length());
//            String idHoaDonSub = idHoaDon.substring(2);
            List<Object[]> list = hdsv.getSanPhamLichSuHoaDon(maHoaDon);
            for (Object[] row : list) {
                i++;
                modelSPDM.addRow(new Object[]{i, row[0], row[1], format.format(row[2]), row[3], format.format(row[4])});
            }
        }
    }

    public void showDataSanPhamLichSuHoaDonQR(String maQR) {
        modelSPDM.setRowCount(0);
        int i = 0;
        List<Object[]> list = hdsv.getSanPhamLichSuHoaDon(maQR);
        for (Object[] row : list) {
            i++;
            modelSPDM.addRow(new Object[]{i, row[0], row[1], format.format(row[2]), row[3], format.format(row[4])});
        }
    }

    //Sản Phẩm Hoàn
    private void showDataSanPhamHoan() {
        modelSPHoan.setRowCount(0);
        int selected = LichSuHoaDonTable.getSelectedRow();
        int i = 0;
        if (selected >= 0) {
            String maHoaDon = (String) LichSuHoaDonTable.getValueAt(selected, 1);
            List<Object[]> list = hdsv.getSPHoan(maHoaDon);
            for (Object[] row : list) {
                i++;
                modelSPHoan.addRow(new Object[]{i, row[0], row[1], format.format(row[2]), row[3], row[4]});
            }
        }
    }

    //Tìm Kiếm Hóa Đơn 
    private void timKiemHoaDon() {
        Object input = findHoaDon.getText();
        List<Object[]> list = hdsv.findHoaDon(input);
        modelHD.setRowCount(0);
        int i = 0;
        for (Object[] row : list) {
            i++;
            modelHD.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
        }
    }

    private void timKiemLSHoaDon() {
        Object input = findLSHD.getText();
        List<Object[]> list = hdsv.findHoaDon(input);
        modelLSHD.setRowCount(0);
        int i = 0;
        for (Object[] row : list) {
            i++;
            modelLSHD.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
        }
    }

    private void timTheoTTHD() {
        String tthd = (String) cbbTTHD.getSelectedItem();
        if (tthd != null) {
            List<Object[]> list = hdsv.findHoaDon(tthd);
            modelHD.setRowCount(0);
            int i = 0;
            for (Object[] row : list) {
                i++;
                modelHD.addRow(new Object[]{i, row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
            }
        }
    }

    private void timTheoGia() {
        String from = txtFrom.getText();
        String to = txtTo.getText();
        int count = 0;
        Double fromSo = 0.0;
        Double toSo = 0.0;
        if (from.trim().isEmpty()) {
            count++;
            txtFrom.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Bạn Chưa Nhập Đủ Thông Tin!");
        } else {
            try {
                fromSo = Double.parseDouble(from);
                if (fromSo < 0) {
                    count++;
                    txtFrom.setBackground(Color.yellow);
                    JOptionPane.showMessageDialog(this, "Giá Từ Phải Lớn Hơn 0!");
                } else {
                    txtFrom.setBackground(Color.white);
                    if (to.trim().isEmpty()) {
                        count++;
                        txtTo.setBackground(Color.yellow);
                        JOptionPane.showMessageDialog(this, "Bạn Chưa Nhập Đủ Thông Tin!");
                    } else {
                        try {
                            toSo = Double.parseDouble(to);
                            txtTo.setBackground(Color.WHITE);
                            if (toSo < fromSo) {
                                count++;
                                txtTo.setBackground(Color.yellow);
                                JOptionPane.showMessageDialog(this, "Giá Tới Phải Lớn Hơn!");
                            } else {
                                txtTo.setBackground(Color.white);
                            }
                        } catch (Exception e) {
                            count++;
                            txtTo.setBackground(Color.yellow);
                            JOptionPane.showMessageDialog(this, "Dữ Liệu Này Phải Là Số");
                        }
                    }
                }
            } catch (Exception e) {
                count++;
                txtFrom.setBackground(Color.yellow);
                JOptionPane.showMessageDialog(this, "Dữ Liệu Này Phải Là Số");
            }
        }
        if (count == 0) {
            List<Object[]> list = hdsv.findHoaDonTheoGia(fromSo, toSo);
            modelHD.setRowCount(0);
            int i = 0;
            for (Object[] row : list) {
                i++;
                modelHD.addRow(new Object[]{i, "HĐ" + row[0], row[1], row[2], row[3], row[4], format.format(row[5]), row[6], row[7]});
            }
        }
    }

    //delete Hóa Đơn
    private void deleteHoaDon() {
        int selected = HoaDonTable.getSelectedRow();
        if (selected >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Xóa Hóa Đơn Này?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maHoaDon = (String) HoaDonTable.getValueAt(selected, 1);
                HoaDon hd = hdsv.getHoaDonByMa(maHoaDon);
                boolean removeHDCT = hdsv.deleteHoaDonChiTiet(hd.getIdHoaDon());
                if (removeHDCT == true) {
                    boolean removeHD = hdsv.deleteHoaDon(hd.getIdHoaDon());
                    if (removeHD == true) {
                        this.showDataHoaDon(Integer.parseInt(currentPage.getText()));
                        lblTotal.setText(hdsv.countHD() + "");
                        this.totalSoTrang();
                        JOptionPane.showMessageDialog(this, "Xóa Hóa Đơn Thành Công!");
                    }
                } else {
                    boolean removeHD = hdsv.deleteHoaDon(hd.getIdHoaDon());
                    if (removeHD == true) {
                        this.showDataHoaDon(Integer.parseInt(currentPage.getText()));
                        lblTotal.setText(hdsv.countHD() + "");
                        this.totalSoTrang();
                        JOptionPane.showMessageDialog(this, "Xóa Hóa Đơn Thành Công!");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn Nào!");
        }
    }

    //choose sản phẩm hoàn hàng
//    private void chooseHoanHang() {
//        int selectedLSHD = LichSuHoaDonTable.getSelectedRow();
//        Double tongTienHang = 0.0;
//        if (selectedLSHD >= 0) {
//            int selectedSPDM = SanPhamDaMuaTable.getSelectedRow();
//            if (selectedSPDM >= 0) {
//                String maHoaDon = (String) LichSuHoaDonTable.getValueAt(selectedLSHD, 1);
//                String trangThaiHoaDon = (String) LichSuHoaDonTable.getValueAt(selectedLSHD, 8);
//                if (trangThaiHoaDon.equalsIgnoreCase("Đã Thanh Toán")) {
//                    List<Object[]> ob = hdsv.hoanHang(maHoaDon);
//                    int[] selectedRows = SanPhamDaMuaTable.getSelectedRows();
//                    int i = 0;
//                    int k = 0;
//                    for (int row : selectedRows) {
//                        i++;
//                        //lấy ra những thằng hoàn
//                        Object[] ctspHoan = ob.get(row);
//                        String idctsp = String.valueOf(ctspHoan[0]);
//                        String soLuongCanHoan = String.valueOf(ctspHoan[1]);
//                        ChiTietSanPham ctspCurrent = ctspsv.getCTSPByID(Integer.parseInt(idctsp));
//                        int soLuongHoan = Integer.parseInt(soLuongCanHoan) + ctspCurrent.getSoLuongTon();
//                        if (i == 1) {
//                            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Chắn Muốn Hoàn Trả?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
//                            if (confirm == JOptionPane.YES_OPTION) {
//                                k = 10;
//                                JOptionPane.showMessageDialog(this, "Hoàn Thành Công!");
//                            }
//                        }
//                        if (k == 10 || k >= 1) {
//                            //update lại số lượng trong ctsp
//                            k++;
//                            ctspsv.updateHoanHang(soLuongHoan, Integer.parseInt(idctsp));
//                            //add vào sản phẩm hoàn
//                            SanPhamHoan sph = new SanPhamHoan();
//                            sph.setIdCTSP(ctspCurrent.getIdCTSP());
//                            sph.setSoLuong(Integer.parseInt(soLuongCanHoan));
//                            sph.setMaHoaDon(maHoaDon);
//                            hdsv.addLichSuHoan(sph);
//                            //Update lại Tổng Tiền Hàng Hóa Đơn
//                            HoaDon hd = hdsv.getHoaDonByMa(maHoaDon);
//                            tongTienHang = hd.getTongTienHang() - (Integer.parseInt(soLuongCanHoan) * ctspCurrent.getDonGia());
//                            hdsv.updateTongTienHang(tongTienHang, maHoaDon);
//                            //xóa cái thằng sản phẩm được hoàn khỏi hóa đơn chi Tiết
//                            hdsv.deleteHoaDonChiTietByID(ctspCurrent.getIdCTSP(), hd.getIdHoaDon());
//
//                        }
//                    }
//                    //update lại trạng thái hóa đơn thành Hoàn Hàng
//                    boolean up = hdsv.updateTrangThaiHoanHang(maHoaDon);
//                    if (up == true) {
//                        //show lại data
//                        this.showDataLichSuHoaDon();
//                        this.showDataSanPhamLichSuHoaDon();
//                        this.showDataSanPhamHoan();
//                    }
//                } else if (trangThaiHoaDon.equalsIgnoreCase("Đang Giao Hàng")) {
//                    JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Được Giao Chưa Thể Hoàn!");
//                } else if (trangThaiHoaDon.equalsIgnoreCase("Hoàn Hàng")) {
//                    JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Được Hoàn Trả!");
//                } else if (trangThaiHoaDon.equalsIgnoreCase("Hủy Đơn Hàng")) {
//                    JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Bị Hủy Không Thể Hoàn!");
//                }
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm Nào!");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn Nào!");
//        }
//    }
    //Hoàn Sản Phầm
//    private void HoanSanPham() {
//        int selectedHD = LichSuHoaDonTable.getSelectedRow();
//        Double tongTienHang = 0.0;
//        if (selectedHD >= 0) {
//            String header[] = {"STT", "Mã HĐ", "Mã Nhân Viên", "Tên Nhân Viên", "Tên Khách Hàng", "Số Điện Thoại", "Tổng Tiền", "Ngày Tạo", "Trạng Thái"};
//            String maHoaDon = (String) LichSuHoaDonTable.getValueAt(selectedHD, 1);
//            String trangThaiHD = (String) LichSuHoaDonTable.getValueAt(selectedHD, 8);
//            if (trangThaiHD.equalsIgnoreCase("Đã Thanh Toán")) {
//                int selected = SanPhamDaMuaTable.getSelectedRow();
//                if (selected >= 0) {
//                    boolean choose = (boolean) SanPhamDaMuaTable.getValueAt(selected, 0);
//                    if (choose == true) {
//                        List<Object[]> ob = hdsv.hoanHang(maHoaDon);
//                        Object[] row = ob.get(selected);
//                        Integer soLuong = Integer.parseInt(String.valueOf(row[1]));
//                        Integer idCTSP = Integer.parseInt(String.valueOf(row[0]));
//                        ChiTietSanPham ctspCurrent = ctspsv.getCTSPByID(idCTSP);
//                        String slh = JOptionPane.showInputDialog("Điền Số Lượng Hoàn!");
//                        if (slh != null) {
//                            if (slh.trim().isEmpty()) {
//                                JOptionPane.showMessageDialog(this, "Số Lượng Hoàn Không Được Trống!");
//                            } else {
//                                try {
//                                    Integer soLuongHoan = Integer.parseInt(slh);
//                                    if (soLuongHoan <= 0) {
//                                        JOptionPane.showMessageDialog(this, "Số Lượng Hoàn Phải Lớn Hơn 0!");
//                                    } else {
//                                        if (soLuongHoan > soLuong) {
//                                            JOptionPane.showMessageDialog(this, "Không Đủ Số Lượng Hoàn!");
//                                        } else if ((soLuongHoan - soLuong) == 0) {
//                                            //Khi Số Lượng Trừ Đi Bằng 0
//                                            //add vào db hoàn
//                                            SanPhamHoan sph = new SanPhamHoan();
//                                            sph.setIdCTSP(ctspCurrent.getIdCTSP());
//                                            sph.setSoLuong(soLuongHoan);
//                                            sph.setMaHoaDon(maHoaDon);
//                                            boolean addLSHoan = hdsv.addLichSuHoan(sph);
//                                            if (addLSHoan == true) {
//                                                //update lại số lượng ctsp
//                                                ctspsv.updateHoanHang(soLuongHoan + ctspCurrent.getSoLuongTon(), ctspCurrent.getIdCTSP());
//                                                HoaDon hd = hdsv.getHoaDonByMa(maHoaDon);
//                                                //update lại tổng tiền hàng
//                                                tongTienHang = hd.getTongTienHang() - (soLuongHoan * ctspCurrent.getDonGia());
//                                                hdsv.updateTongTienHang(tongTienHang, maHoaDon);
//                                                //xóa sản phẩm đó trong hóa đơn chi tiết
//                                                boolean deleteHDCT = hdsv.deleteHoaDonChiTietByID(ctspCurrent.getIdCTSP(), hd.getIdHoaDon());
//                                                if (deleteHDCT == true) {
//                                                    boolean updateTTHoanHang = hdsv.updateTrangThaiHoanHang(maHoaDon);
//                                                    if (updateTTHoanHang == true) {
//                                                        this.showDataLichSuHoaDon();
//                                                        this.showDataSanPhamLichSuHoaDon();
//                                                        this.showDataSanPhamHoan();
//                                                        JOptionPane.showMessageDialog(this, "Hoàn Hàng Thành Công!");
//                                                    }
//                                                }
//                                            }
//                                        } else {
//                                            //Khi Số Lượng Trừ Đi Khác 0
//                                            SanPhamHoan sph = new SanPhamHoan();
//                                            sph.setIdCTSP(ctspCurrent.getIdCTSP());
//                                            sph.setSoLuong(soLuongHoan);
//                                            sph.setMaHoaDon(maHoaDon);
//                                            boolean addLSHoan = hdsv.addLichSuHoan(sph);
//                                            if (addLSHoan == true) {
//                                                ctspsv.updateHoanHang(soLuongHoan + ctspCurrent.getSoLuongTon(), ctspCurrent.getIdCTSP());
//                                                HoaDon hd = hdsv.getHoaDonByMa(maHoaDon);
//                                                //update sô lượng hdct
//                                                boolean updateHDCT = hdsv.updateSoLuongHDCT(soLuong - soLuongHoan, ctspCurrent.getIdCTSP(), hd.getIdHoaDon());
//                                                if (updateHDCT == true) {
//                                                    //update lại tổng tiền hàng
//                                                    tongTienHang = hd.getTongTienHang() - (soLuongHoan * ctspCurrent.getDonGia());
//                                                    hdsv.updateTongTienHang(tongTienHang, maHoaDon);
//                                                    //update lại trạng thái hoàn
//                                                    boolean updateTTHoanHang = hdsv.updateTrangThaiHoanHang(maHoaDon);
//                                                    if (updateTTHoanHang == true) {
//                                                        this.showDataLichSuHoaDon();
//                                                        this.showDataSanPhamLichSuHoaDon();
//                                                        this.showDataSanPhamHoan();
//                                                        JOptionPane.showMessageDialog(this, "Hoàn Hàng Thành Công!");
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    JOptionPane.showMessageDialog(this, "Số Lượng Hoàn Phải Là Số!");
//                                }
//                            }
//                        }
//                    }
//                }
//            } else if (trangThaiHD.equalsIgnoreCase("Đang Giao Hàng")) {
//                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Giao Chưa Thể Hoàn!");
//            } else if (trangThaiHD.equalsIgnoreCase("Hoàn Hàng")) {
//                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Được Hoàn Hàng!");
//            } else if (trangThaiHD.equalsIgnoreCase("Hủy Đơn Hàng")) {
//                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đã Bị Hủy!");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
//        }
//
//    }
    //fill  cbb TTHD
    private void fillCbbTTHD() {
        cbbTTHD.removeAllItems();
        for (TrangThaiHoaDon tthd : tthdsv.getAll()) {
            cbbTTHD.addItem(tthd.getTenTTHD());
        }
    }

    //show Data HoaDon ChiTiet
//    private void showDataHoaDonChiTiet(Integer idHoaDon) {
//        modelHDCT.setRowCount(0);
//        List<Object[]> list = hdsv.getSPDonHang(idHoaDon);
//        int i = 0;
//        for (Object[] row : list) {
//            i++;
//            modelHDCT.addRow(new Object[]{i, "HĐ" + row[0], row[1], row[2], format.format(row[3]), row[4], format.format(row[5])});
//        }
//    }
    //fill infor
//    private void fillInfor() {
//        int selected = HoaDonTable.getSelectedRow();
//        HoaDon hd = listHD.get(selected);
//        //get Ten NV
//        NhanVien nv = nvsv.findNVByID(hd.getIdNhanVien());
//        //get Tên KH
//        KhachHang kh = khsv.findKHByID(hd.getIdKhachHang());
//        //get Tên HTTT
//        HinhThucThanhToan httt = htttsv.findHTTTById(hd.getIdHTTT());
//        //get Tên Tình Trạng HD
//        TrangThaiHoaDon tthd = tthdsv.findTTHDById(hd.getIdTTHD());
//        //Set Text infor
//        lblMaHD.setText("HĐ" + hd.getIdHoaDon());
//        lblTenNV.setText(nv.getTenNhanVien());
//        lblTenKH.setText(kh.getTenKhachHang());
//        lblThoiGianTao.setText(hd.getNgayTao() + "");
//        lblHinhThucThanhToan.setText(httt.getTenHTTT());
//        lblTongTien.setText(format.format(hd.getTongTienHang()));
//        lblTinhTrang.setText(tthd.getTenTTHD());
//        lblTienKhachDua.setText(format.format(hd.getKhachTraTM()));
//        lblTienKhachCK.setText(format.format(hd.getKhachTraCK()));
//        lblTenNguoiNhan.setText(hd.getTenNguoiNhan());
//        lblSDTNguoiNhan.setText(hd.getSdtNguoiNhan());
//        lblDiaChi.setText(hd.getDiaChiNguoiNhan());
//        lblShipDuTinh.setText(hd.getNgayShipDuTinh() + "");
//        lblDenDuTinh.setText(hd.getNgayDenDuTinh() + "");
//        lblPhiShip.setText(format.format(hd.getPhiShip()));
//        this.showDataHoaDonChiTiet(hd.getIdHoaDon());
//    }
    //Fill Data HoaDon
//    private void fillDataHoaDon() {
//        listHD = hdsv.getAll();
//        showDataHoaDon(listHD);
//    }
    private void nextPage() {
        Integer page = Integer.parseInt(currentPage.getText());
        Integer totalPage = Integer.parseInt(lblTotal.getText());
        currentPage.setText((page + 1) + "");
        if (page >= totalPage) {
            currentPage.setText(1 + "");
        }
        this.showDataHoaDon(Integer.parseInt(currentPage.getText()));
    }

    private void previousPage() {
        Integer page = Integer.parseInt(currentPage.getText());
        currentPage.setText((page - 1) + "");
        if (page <= 1) {
            currentPage.setText(lblTotal.getText());
        }
        this.showDataHoaDon(Integer.parseInt(currentPage.getText()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        findHoaDon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbbTTHD = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTo = new javax.swing.JTextField();
        filterBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        HoaDonTable = new javax.swing.JTable();
        previousBtn = new javax.swing.JButton();
        currentPage = new javax.swing.JLabel();
        nextBtn = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        HoaDonChiTietTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        findLSHD = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        LichSuHoaDonTable = new javax.swing.JTable();
        scanQRbtn = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblSDTKH = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblGiamGiaDiem = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lblTenNguoiNhan = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblSDTNN = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lblDCNguoiNhan = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblHTTT = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        lblTTHD = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        lblTienKhachDua = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lblKhachCK = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        lblShipDuTinh = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        lblDenDuTinh = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        lblTGTao = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblPhiShip = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        lblGhiChu = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblMaGiaoDich = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        SanPhamDaMuaTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tìm Kiếm Hóa Đơn");

        findHoaDon.setForeground(new java.awt.Color(153, 153, 153));
        findHoaDon.setText("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn");
        findHoaDon.setDisabledTextColor(new java.awt.Color(153, 153, 153));
        findHoaDon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                findHoaDonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                findHoaDonFocusLost(evt);
            }
        });
        findHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findHoaDonKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Trạng Thái Hóa Đơn:");

        cbbTTHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTTHDActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Giá Khoảng:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Đến");

        filterBtn.setBackground(new java.awt.Color(100, 180, 244));
        filterBtn.setForeground(new java.awt.Color(255, 255, 255));
        filterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/filter.png"))); // NOI18N
        filterBtn.setText("Lọc");
        filterBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterBtnMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        HoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
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
        HoaDonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HoaDonTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(HoaDonTable);

        previousBtn.setBackground(new java.awt.Color(100, 180, 244));
        previousBtn.setForeground(new java.awt.Color(255, 255, 255));
        previousBtn.setText("<<");
        previousBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                previousBtnMouseClicked(evt);
            }
        });
        previousBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousBtnActionPerformed(evt);
            }
        });

        currentPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentPage.setText("1");

        nextBtn.setBackground(new java.awt.Color(100, 180, 244));
        nextBtn.setForeground(new java.awt.Color(255, 255, 255));
        nextBtn.setText(">>");
        nextBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextBtnMouseClicked(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(100, 180, 244));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check-list.png"))); // NOI18N
        jButton7.setText("Xuất Danh Sách");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel64.setText("/");

        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("8");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(previousBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel64)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousBtn)
                    .addComponent(currentPage)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(lblTotal)
                    .addComponent(nextBtn))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbTTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(filterBtn))
                            .addComponent(findHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 402, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(findHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbTTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterBtn))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Hóa Đơn");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        HoaDonChiTietTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(HoaDonChiTietTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Hóa Đơn Chi Tiết");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hóa Đơn", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Tìm Kiếm Hóa Đơn:");

        findLSHD.setForeground(new java.awt.Color(153, 153, 153));
        findLSHD.setText("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn");
        findLSHD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                findLSHDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                findLSHDFocusLost(evt);
            }
        });
        findLSHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findLSHDKeyReleased(evt);
            }
        });

        LichSuHoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
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
        LichSuHoaDonTable.setShowGrid(false);
        LichSuHoaDonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LichSuHoaDonTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(LichSuHoaDonTable);

        scanQRbtn.setBackground(new java.awt.Color(100, 180, 244));
        scanQRbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        scanQRbtn.setForeground(new java.awt.Color(255, 255, 255));
        scanQRbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/qr-code.png"))); // NOI18N
        scanQRbtn.setText("Quét Mã QR");
        scanQRbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scanQRbtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(findLSHD, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(scanQRbtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(findLSHD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scanQRbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Mã Hóa Đơn:");

        lblMaHD.setText("jLabel11");

        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("Tên Nhân Viên:");

        lblTenNV.setText("jLabel13");

        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Tên Khách Hàng:");

        lblTenKH.setText("jLabel15");

        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("SĐT Khách Hàng:");

        lblSDTKH.setText("jLabel17");

        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Giảm Giá Điểm:");

        lblGiamGiaDiem.setText("jLabel19");

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Tên Người Nhận:");

        lblTenNguoiNhan.setText("jLabel21");

        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("SĐT Người Nhận:");

        lblSDTNN.setText("jLabel23");

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Địa Chỉ Người Nhận:");

        lblDCNguoiNhan.setText("jLabel25");

        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Hình Thức Thanh Toán:");

        lblHTTT.setText("jLabel43");

        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Trạng Thái HĐ:");

        lblTTHD.setText("jLabel45");

        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Tổng Tiền:");

        lblTongTien.setText("jLabel47");

        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Tiền Khách Đưa:");

        lblTienKhachDua.setText("jLabel49");

        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Tiền Khách CK:");

        lblKhachCK.setText("jLabel51");

        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Tiền Thừa:");

        lblTienThua.setText("jLabel53");

        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Ship Dự Tính:");

        lblShipDuTinh.setText("jLabel55");

        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Đến Dự Tính:");

        lblDenDuTinh.setText("jLabel57");

        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Thời Gian Tạo:");

        lblTGTao.setText("jLabel59");

        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Phí Ship:");

        lblPhiShip.setText("jLabel61");

        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Ghi Chú:");

        lblGhiChu.setText("jLabel63");
        lblGhiChu.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblGhiChu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Mã Giao Dịch:");

        lblMaGiaoDich.setText("jLabel8");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel32)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(lblTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTenNguoiNhan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGiamGiaDiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSDTKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel38)
                                        .addGap(35, 35, 35))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel58)
                                    .addGap(51, 51, 51)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel62)
                                .addGap(84, 84, 84)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSDTNN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDCNguoiNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTGTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel50)
                        .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(lblTTHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHTTT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKhachCK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblShipDuTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDenDuTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPhiShip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaGiaoDich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lblMaHD)
                    .addComponent(jLabel42)
                    .addComponent(lblHTTT))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lblTenNV)
                    .addComponent(jLabel44)
                    .addComponent(lblTTHD))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(lblTenKH)
                    .addComponent(jLabel46)
                    .addComponent(lblTongTien))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(lblSDTKH)
                    .addComponent(jLabel48)
                    .addComponent(lblTienKhachDua))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(lblGiamGiaDiem)
                    .addComponent(jLabel50)
                    .addComponent(lblKhachCK))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(lblTenNguoiNhan)
                    .addComponent(jLabel52)
                    .addComponent(lblTienThua))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(lblSDTNN)
                    .addComponent(jLabel54)
                    .addComponent(lblShipDuTinh))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(lblDCNguoiNhan)
                    .addComponent(jLabel56)
                    .addComponent(lblDenDuTinh))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(lblTGTao)
                    .addComponent(jLabel60)
                    .addComponent(lblPhiShip))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(lblMaGiaoDich))
                        .addGap(28, 28, 28))))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        SanPhamDaMuaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"
            }
        ));
        SanPhamDaMuaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SanPhamDaMuaTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(SanPhamDaMuaTable);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Sản Phẩm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 380, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tìm Kiếm Hóa Đơn", jPanel5);

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

    private void nextBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextBtnMouseClicked
        // TODO add your handling code here:
        this.nextPage();
    }//GEN-LAST:event_nextBtnMouseClicked

    private void previousBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousBtnMouseClicked
        // TODO add your handling code here:
        this.previousPage();
    }//GEN-LAST:event_previousBtnMouseClicked

    private void HoaDonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HoaDonTableMouseClicked
        // TODO add your handling code here:
        this.SanPhamHoaDon();
    }//GEN-LAST:event_HoaDonTableMouseClicked

    private void LichSuHoaDonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LichSuHoaDonTableMouseClicked
        // TODO add your handling code here:
        this.chiTietLSHoaDon();
        this.showDataSanPhamLichSuHoaDon();
        this.showDataSanPhamHoan();
    }//GEN-LAST:event_LichSuHoaDonTableMouseClicked

    private void findHoaDonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findHoaDonKeyReleased
        // TODO add your handling code here:
        this.timKiemHoaDon();
    }//GEN-LAST:event_findHoaDonKeyReleased

    private void cbbTTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTTHDActionPerformed
        // TODO add your handling code here:
        this.timTheoTTHD();
    }//GEN-LAST:event_cbbTTHDActionPerformed

    private void filterBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filterBtnMouseClicked
        // TODO add your handling code here:
        this.timTheoGia();
    }//GEN-LAST:event_filterBtnMouseClicked

    private void findLSHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findLSHDKeyReleased
        // TODO add your handling code here:
        this.timKiemLSHoaDon();
    }//GEN-LAST:event_findLSHDKeyReleased

    private void findHoaDonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findHoaDonFocusGained
        // TODO add your handling code here:
        if (findHoaDon.getText().endsWith("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn")) {
            findHoaDon.setText("");
        }
    }//GEN-LAST:event_findHoaDonFocusGained

    private void findHoaDonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findHoaDonFocusLost
        // TODO add your handling code here:
        if (findHoaDon.getText().endsWith("")) {
            findHoaDon.setText("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn");
        }
    }//GEN-LAST:event_findHoaDonFocusLost

    private void findLSHDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findLSHDFocusGained
        // TODO add your handling code here:
        if (findLSHD.getText().endsWith("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn")) {
            findLSHD.setText("");
        }
    }//GEN-LAST:event_findLSHDFocusGained

    private void findLSHDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findLSHDFocusLost
        // TODO add your handling code here:
        if (findLSHD.getText().endsWith("")) {
            findLSHD.setText("Tìm theo Mã Nhân Viên,Tên Nhân Viên,Tên Khách Hàng,SĐT và Trạng Thái Hóa Đơn");
        }
    }//GEN-LAST:event_findLSHDFocusLost

    private void SanPhamDaMuaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamDaMuaTableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_SanPhamDaMuaTableMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) HoaDonChiTietTable.getModel();
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
                String[] columnNames = {"STT", "Mã HĐ", "Mã Sản Phẩm", "Tên Sản Phẩm", "Thương Hiệu", "Xuất Xứ", "Khối Lượng", "Đơn Vị Tính", "Chất Liệu", "Loại Sản Phẩm", "Số Lượng", "Đơn Giá", "Tổng Tiền"};
                XSSFRow headerRow = excelSheet.createRow(0);
                for (int j = 0; j < columnNames.length; j++) {
                    XSSFCell cell = headerRow.createCell(j);
                    cell.setCellValue(columnNames[j]);
//                    cell.setCellStyle(boldStyle); // Áp dụng CellStyle cho ô in đậm
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    XSSFRow excelrow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < columnNames.length; j++) {
                        XSSFCell excelCell = excelrow.createCell(j);
                        excelCell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Export Successfully");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void previousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousBtnActionPerformed

    private void scanQRbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scanQRbtnMouseClicked
        // TODO add your handling code here:
        new ScanQRHoaDon(this, true).setVisible(true);
    }//GEN-LAST:event_scanQRbtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable HoaDonChiTietTable;
    private javax.swing.JTable HoaDonTable;
    private javax.swing.JTable LichSuHoaDonTable;
    private javax.swing.JTable SanPhamDaMuaTable;
    private javax.swing.JComboBox<String> cbbTTHD;
    private javax.swing.JLabel currentPage;
    private javax.swing.JButton filterBtn;
    private javax.swing.JTextField findHoaDon;
    private javax.swing.JTextField findLSHD;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDCNguoiNhan;
    private javax.swing.JLabel lblDenDuTinh;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblGiamGiaDiem;
    private javax.swing.JLabel lblHTTT;
    private javax.swing.JLabel lblKhachCK;
    private javax.swing.JLabel lblMaGiaoDich;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblPhiShip;
    private javax.swing.JLabel lblSDTKH;
    private javax.swing.JLabel lblSDTNN;
    private javax.swing.JLabel lblShipDuTinh;
    private javax.swing.JLabel lblTGTao;
    private javax.swing.JLabel lblTTHD;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTenNguoiNhan;
    private javax.swing.JLabel lblTienKhachDua;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton previousBtn;
    private javax.swing.JButton scanQRbtn;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
