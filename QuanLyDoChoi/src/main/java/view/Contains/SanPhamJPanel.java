/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Contains;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.layout.element.Cell;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.ChiTietSanPham;
import model.DonViTinh;
import model.KhoiLuong;
import model.LoaiSanPham;
import model.SanPham;
import model.ThuongHieu;
import model.XuatXu;
import modelview.modelChiTietSanPham;
import repositoryImpl.ChiTietSanPhamRepoImpl;
import serviceImpl.ChatLieuServiceImpl;
import serviceImpl.DonViTinhServiceImpl;
import serviceImpl.KhoiLuongServiceImpl;
import serviceImpl.LoaiSanPhamServiceImpl;
import serviceImpl.SanPhamServiceImpl;
import serviceImpl.ThuongHieuServiceImpl;
import serviceImpl.XuatXuServiceImpl;
import utils.Auth;
import view.Contain.EntitySanPham.ChatLieuJDialog;
import view.Contain.EntitySanPham.DonViTinhJDialog;
import view.Contain.EntitySanPham.KhoiLuongJDialog;
import view.Contain.EntitySanPham.LoaiSanPhamJDialog;
import view.Contain.EntitySanPham.ThuongHieuJDialog;
import view.Contain.EntitySanPham.XuatXuJDialog;
import itf.fillTextItf;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repository.ChiTietSanPhamRepo;
import service.ChiTietSanPhamService;
import service.SanPhamService;
import serviceImpl.ChiTietSanPhamServiceImpl;
import utils.MsgBox;
import view.Contain.EntitySanPham.SanPhamJDialog;
import view.Contain.EntitySanPham.ScanQRCTSP;
import view.Contain.EntitySanPham.ThongTinSanPham;

/**
 *
 * @author Dell
 */
public class SanPhamJPanel extends javax.swing.JPanel implements fillTextItf {

    public static int g = 0;
    SanPhamServiceImpl serviceSP = new SanPhamServiceImpl();
    int index = -1;
    DefaultTableModel model;
    DefaultTableModel modelCTSP;
    ChatLieuServiceImpl serviceChatLieu = new ChatLieuServiceImpl();
    XuatXuServiceImpl serviceXuatXu = new XuatXuServiceImpl();
    LoaiSanPhamServiceImpl serviceLoaiSanPham = new LoaiSanPhamServiceImpl();
    DonViTinhServiceImpl serviceDonViTinh = new DonViTinhServiceImpl();
    ThuongHieuServiceImpl serviceThuongHieu = new ThuongHieuServiceImpl();
    KhoiLuongServiceImpl serviceKhoiLuong = new KhoiLuongServiceImpl();
    ChiTietSanPhamRepoImpl serviceModelChitietSanPham = new ChiTietSanPhamRepoImpl();
    ChiTietSanPhamRepoImpl serImpl = new ChiTietSanPhamRepoImpl();
    SanPhamService spsv = new SanPhamServiceImpl();
    modelChiTietSanPham ctsp = new modelChiTietSanPham();
    ChiTietSanPhamRepo ctsprp = new ChiTietSanPhamRepoImpl();
    ChiTietSanPhamService ctspsv = new ChiTietSanPhamServiceImpl();
    DefaultTableModel model1;

    private static long currentCount = 100000000000L;

    /**
     * Creates new form Sp
     */
    public SanPhamJPanel() {
        initComponents();
        fillComboBoxSanPham();
        fillComboBoxChatLieu();
        fillComboBoxXuatXu();
        fillComnoBoxLoaiSanPham();
        fillComboBoxDonViTinh();
        fillComboBoxThuongHieu();
        fillComboBoxKhoiLuong();
        fillTableSanPham(serviceSP.getAll());
        //fillTableChiTietSanPham(serviceSP.getAll());
        fill();
        fillTableXuatXu(serviceXuatXu.getAll());
        readQR();
        txtBarcode.setEnabled(false);
    }

    public void setText(String text) {
        txtBarcode.setText(text);
    }

    public void readQR() {
        try {
            String Path = "D:\\QuanLyDoChoi\\barcode";
            BufferedImage bf = ImageIO.read(new FileInputStream(Path));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
            Result result = new MultiFormatReader().decode(bitmap);
            System.out.println(result.getText());
        } catch (Exception e) {
        }
    }

    private void updateThongTinSanPham(String ghiChu) {
        this.txtGhiChu.setText(ghiChu);
    }

    public void fillTableSanPham(List<SanPham> listSp) {
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int i = 1;
        for (SanPham sp : listSp) {

            Object[] row = {
                i++, sp.getMaSanPham(), sp.getTenSanPham(), serviceSP.countSoLuong(sp.getMaSanPham())
            };
            model.addRow(row);
        }

    }

    public void fill() {
        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        List<modelChiTietSanPham> list = serImpl.getAll();
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    public void fillCTSP(List<modelChiTietSanPham> listCTSP) {
        int index = tblSanPham.getSelectedRow();
        String ma = tblSanPham.getValueAt(index, 1).toString();
        model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        List<modelChiTietSanPham> list = serImpl.getAll(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }
    }

    public void CTSP(List<modelChiTietSanPham> listCTSP) {

        model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;

        for (modelChiTietSanPham model : listCTSP) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }
    }

    public void fillComboBoxKhoiLuong() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbbkl.getModel();
        model1.removeAllElements();
        List<KhoiLuong> listKl = serviceKhoiLuong.getAll();
        for (KhoiLuong kl : listKl) {
            model1.addElement(kl.getTenKhoiLuong());
        }
    }

    public void fillComboBoxSanPham() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbbTensp.getModel();
        model1.removeAllElements();
        List<model.SanPham> listSp = serviceSP.getAll();
        for (model.SanPham sp : listSp) {
            model1.addElement(sp.getTenSanPham());
        }

    }

    public void fillComboBoxChatLieu() {
        DefaultComboBoxModel modelChatLieu = (DefaultComboBoxModel) cbbcl.getModel();
        modelChatLieu.removeAllElements();
        List<ChatLieu> listChatLieu = serviceChatLieu.getAll();
        for (ChatLieu chatlieu : listChatLieu) {
            modelChatLieu.addElement(chatlieu.getTenChatLieu());
        }
    }

    public void fillComboBoxXuatXu() {
        DefaultComboBoxModel modelXuaXu = (DefaultComboBoxModel) cbbxx.getModel();
        modelXuaXu.removeAllElements();
        List<XuatXu> listXuatXu = serviceXuatXu.getAll();
        for (XuatXu xuatxu : listXuatXu) {
            modelXuaXu.addElement(xuatxu.getTenXuatXu());
        }
    }

    public void fillComnoBoxLoaiSanPham() {
        DefaultComboBoxModel modelLoaiSanPham = (DefaultComboBoxModel) cbblsp.getModel();
        modelLoaiSanPham.removeAllElements();
        List<LoaiSanPham> listLoaiSanPham = serviceLoaiSanPham.getAll();
        for (LoaiSanPham lsp : listLoaiSanPham) {
            modelLoaiSanPham.addElement(lsp.getTenLoaiSanPham());
        }
    }

    public void fillComboBoxDonViTinh() {
        DefaultComboBoxModel modelDonViTinh = (DefaultComboBoxModel) cbbdvt.getModel();
        modelDonViTinh.removeAllElements();
        List<DonViTinh> listdvt = serviceDonViTinh.getAll();
        for (DonViTinh dvt : listdvt) {
            modelDonViTinh.addElement(dvt.getTenDonViTinh());
        }
    }

    public void fillComboBoxThuongHieu() {
        DefaultComboBoxModel modelThuongHieu = (DefaultComboBoxModel) cbbth.getModel();
        modelThuongHieu.removeAllElements();
        List<ThuongHieu> listThuongHieu = serviceThuongHieu.getAll();
        for (ThuongHieu th : listThuongHieu) {
            modelThuongHieu.addElement(th.getTenThuongHieu());
        }

    }

    public void showDataSanPham(int index) {
        SanPham sp = serviceSP.getAll().get(index);
        txtTenSanPham.setText(sp.getTenSanPham());
        txtMaSp.setText(sp.getMaSanPham());

    }

    private SanPham readForm() {
        String ma = this.gennerateMa();
        String ten = txtTenSanPham.getText();
        int count = 0;
        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Bạn Chưa Nhập Tên!");
        }
        if (count == 0) {
            return new SanPham(ma, ten, Auth.idNhanVien(), false);
        } else {
            return null;
        }
    }

    private SanPham updateForm() {
        LocalDate toDay = this.getToDay();
        return new SanPham(txtTenSanPham.getText(), toDay, Auth.idNhanVien());
    }

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

    public void fillTable(List<SanPham> listSp) {
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int i = 1;
        for (SanPham sp : listSp) {

            Object[] row = {i++,
                sp.getMaSanPham(), sp.getTenSanPham()};
            model.addRow(row);
        }
    }

    void clearForm() {
        txtMaSp.setText("");
        txtTenSanPham.setText("");
    }

    void clear() {
        txtSoLuongTon.setText("");
        txtDonGia.setText("");
        txtGhiChu.setText("");
        txtBarcode.setText("");
        cbbTensp.setSelectedIndex(0);
        cbbcl.setSelectedIndex(0);
        cbbdvt.setSelectedIndex(0);
        cbbkl.setSelectedIndex(0);
        cbblsp.setSelectedIndex(0);
        cbbxx.setSelectedIndex(0);
        cbbth.setSelectedIndex(0);
    }

    private void gennerateBarcode(String barcode) {
        try {
            String data = barcode;
            String path1 = "src\\main\\resources\\barcode\\" + barcode + ".png";
            QRCodeWriter qc = new QRCodeWriter();
            BitMatrix bm = qc.encode(data, BarcodeFormat.QR_CODE, 211, 157);
            Path path = FileSystems.getDefault().getPath(path1);
            MatrixToImageWriter.writeToPath(bm, "PNG", path);
        } catch (Exception e) {
        }
    }

    private ChiTietSanPham checkSanPham() {
        SanPham sp = serviceSP.getByNameSP((String) cbbTensp.getSelectedItem());
        LoaiSanPham lsp = serviceLoaiSanPham.getByName((String) cbblsp.getSelectedItem());
        ThuongHieu th = serviceThuongHieu.getByName((String) cbbth.getSelectedItem());
        KhoiLuong kl = serviceKhoiLuong.getByName((String) cbbkl.getSelectedItem());
        DonViTinh dvt = serviceDonViTinh.getByName((String) cbbdvt.getSelectedItem());
        XuatXu xx = serviceXuatXu.getByName((String) cbbxx.getSelectedItem());
        ChatLieu cl = serviceChatLieu.getByName((String) cbbcl.getSelectedItem());

        String soLuongAfter = txtSoLuongTon.getText();
        Integer soLuongBefore = 0;
        String giaBanAfter = txtDonGia.getText();
        Double giaBanBefore = 0.0;
        txtBarcode.setText(this.genQR());
        String barcode = txtBarcode.getText();
        String ghiChu = txtGhiChu.getText();
        int count = 0;
        if (soLuongAfter.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Số Lượng Không Được Trống!");
        } else {
            try {
                soLuongBefore = Integer.parseInt(soLuongAfter);
                if (soLuongBefore <= 0) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Số Lượng Phải Lớn Hơn 0!");
                } else {
                    if (giaBanAfter.trim().isEmpty()) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Giá Bán Không Được Trống!");
                    } else {
                        try {
                            giaBanBefore = Double.parseDouble(giaBanAfter);
                            if (giaBanBefore <= 0) {
                                count++;
                                JOptionPane.showMessageDialog(this, "Giá Bán Phải Lớn Hơn 0!");
                            } else {
                                if (barcode.trim().isEmpty()) {
                                    count++;
                                    JOptionPane.showMessageDialog(this, "Mã Barcode Không Được Trống!");
                                } else {
                                    if (barcode.trim().length() != 12) {
                                        count++;
                                        JOptionPane.showMessageDialog(this, "Mã Barcode Phải Là 12 Chữ Số!");
                                    } else {
                                        if (ghiChu.trim().isEmpty()) {
                                            count++;
                                            JOptionPane.showMessageDialog(this, "Ghi Chú Không Được Trống!");
                                        } else {
                                            this.gennerateBarcode(barcode);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            count++;
                            System.out.println(e);
                            JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
                        }
                    }
                }
            } catch (Exception e) {
                count++;
                JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
            }
        }
        if (count == 0) {
            ChiTietSanPham ctsp = new ChiTietSanPham();
            ctsp.setIdSanPham(sp.getIdSanPham());
            ctsp.setIdLoaiSanPham(lsp.getIdLoaiSanPham());
            ctsp.setIdThuongHieu(th.getIdThuongHieu());
            ctsp.setIdKhoiLuong(kl.getIdKhoiLuong());
            ctsp.setIdDonViTinh(dvt.getIdDonViTinh());
            ctsp.setIdXuatXu(xx.getIdXuatXu());
            ctsp.setIdChatLieu(cl.getIdChatLieu());
            ctsp.setSoLuongTon(soLuongBefore);
            ctsp.setDonGia(giaBanBefore);
            ctsp.setBarcode(barcode);
            ctsp.setGhiChu(ghiChu);
            ctsp.setCreatedBy(Auth.idNhanVien());
            ctsp.setDeleted(false);
            return ctsp;
        } else {
            return null;
        }
    }

    public void showChiTietSanPham(int index) {
        modelChiTietSanPham model = serviceModelChitietSanPham.getAll().get(index);
        cbbTensp.setSelectedItem(model.getTenSanPham());
//        txtMaSpTab2.setText(model.getMaSp());
        txtBarcode.setText(model.getBarcode());
        txtSoLuongTon.setText(String.valueOf(model.getSoLuongTon()));
        txtDonGia.setText(String.valueOf(model.getDonGia()));
        txtGhiChu.setText(model.getGhiChu());
        cbbcl.setSelectedItem(model.getTenChatLieu());
        cbbth.setSelectedItem(model.getTenThuongHieu());
        cbbkl.setSelectedItem(model.getTenKhoiLuong());
        cbbdvt.setSelectedItem(model.getTenDonVitinh());
        cbblsp.setSelectedItem(model.getTenLoaiSanPham());
        cbbxx.setSelectedItem(model.getTenXuatXu());
        cbbTensp.setSelectedItem(model.getTenSanPham());
    }

    public void showDataChiTietSanPhamByMa(int index) {
        int row = tblctsp.getSelectedRow();
        String ma = tblctsp.getValueAt(row, 1).toString();
        modelChiTietSanPham model = serviceModelChitietSanPham.getAll(ma).get(index);
        cbbTensp.setSelectedItem(model.getTenSanPham());
//        txtMaSpTab2.setText(model.getMaSp());
        String barcode = model.getBarcode();
        String barcodeSub = barcode.substring(0, barcode.indexOf(" "));
        txtBarcode.setText(barcodeSub);
        txtSoLuongTon.setText(String.valueOf(model.getSoLuongTon()));
        txtDonGia.setText(String.valueOf(model.getDonGia()));
        txtGhiChu.setText(model.getGhiChu());
        cbbcl.setSelectedItem(model.getTenChatLieu());
        cbbth.setSelectedItem(model.getTenThuongHieu());
        cbbkl.setSelectedItem(model.getTenKhoiLuong());
        cbbdvt.setSelectedItem(model.getTenDonVitinh());
        cbblsp.setSelectedItem(model.getTenLoaiSanPham());
        cbbxx.setSelectedItem(model.getTenXuatXu());
        cbbTensp.setSelectedItem(model.getTenSanPham());
    }

    void fillbyma() {
        int index = tblSanPham.getSelectedRow();
        String ma = tblSanPham.getValueAt(index, 1).toString();
        model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        List<modelChiTietSanPham> list = serImpl.getAll(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }
    }

    public void openTTSPByWC(String barcode) {
        modelChiTietSanPham mctsp = ctsprp.getMCTSPByBarcode(barcode);
//        ChiTietSanPham ctsp=ctspsv.getCTSPByBarcode(barcode);
        String trangThai = "";
        if (mctsp.isDeleted() == false) {
            trangThai = "Đang bán";
        } else {
            trangThai = "Tạm Ngưng Bán";
        }
        ThongTinSanPham ttsp = new ThongTinSanPham(this, mctsp.getTenSanPham(), mctsp.getTenLoaiSanPham(), mctsp.getTenThuongHieu(),
                mctsp.getTenXuatXu(), mctsp.getTenChatLieu(), mctsp.getTenDonVitinh(), mctsp.getTenKhoiLuong(),
                mctsp.getDonGia(), barcode, mctsp.getSoLuongTon(), mctsp.getGhiChu(), trangThai, true, 0);
        ttsp.setVisible(true);

    }

    public void openThongTinSanPham() {
        int row = tblctsp.getSelectedRow();
        String tenSp = tblctsp.getValueAt(row, 2).toString();
        String loaiSp = tblctsp.getValueAt(row, 3).toString();
        String thuongHieu = tblctsp.getValueAt(row, 4).toString();
        String xuatXu = tblctsp.getValueAt(row, 5).toString();
        String chatLieu = tblctsp.getValueAt(row, 6).toString();
        String donViTinh = tblctsp.getValueAt(row, 7).toString();
        String khoiLuong = tblctsp.getValueAt(row, 8).toString();
        double donGia = Double.parseDouble(tblctsp.getValueAt(row, 9).toString());
        int soLuongTon = Integer.parseInt(tblctsp.getValueAt(row, 12).toString());
        String ghiChu = tblctsp.getValueAt(row, 11).toString();
        String trangThai = tblctsp.getValueAt(row, 14).toString();
        String barcode = tblctsp.getValueAt(row, 10).toString();

        ThongTinSanPham ttsp = new ThongTinSanPham(this, tenSp, loaiSp, thuongHieu,
                xuatXu, chatLieu, donViTinh, khoiLuong, donGia, barcode, soLuongTon, ghiChu, trangThai, true, 1);
        ttsp.setVisible(true);
    }

    void fillBycode() {
        int index = tblctsp.getSelectedRow();
        String ma = tblctsp.getValueAt(index, 1).toString();
        model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        List<modelChiTietSanPham> list = serImpl.getAll(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }
    }

    //Thong tin san pham
    ChatLieu readFormChatLieu() {
        return new ChatLieu(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
    }

    void fillTableChatLieu(List<ChatLieu> listChatLieu) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (ChatLieu chatlieu : listChatLieu) {
            // model.addRow(chatlieu.toDataRow());
            Object[] row = {i++, chatlieu.getMaChatLieu(), chatlieu.getTenChatLieu(), chatlieu.getCreatedBy(), chatlieu.getUpdatedBy()};
            model.addRow(row);
        }
    }

    public void fillTableLoaiSanPham(List<LoaiSanPham> listLsp) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (LoaiSanPham lsp : listLsp) {
            Object[] row = {
                i++, lsp.getMaLoaiSanPham(), lsp.getTenLoaiSanPham(), lsp.getCreatedBy(), lsp.getUpdatedBy()
            };
            model.addRow(row);
        }
    }

    public void fillTableKhoiLuong(List<KhoiLuong> listKhoiLuong) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (KhoiLuong kl : listKhoiLuong) {
            Object[] row = {
                i++, kl.getMaKhoiLuong(), kl.getTenKhoiLuong(), kl.getCreatedBy(), kl.getUpdatedBy()
            };
            model.addRow(row);

        }

    }

    public void fillTableDonViTinh(List<DonViTinh> list) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (DonViTinh dvt : list) {
            Object[] row = {
                i++, dvt.getMaDonViTinh(), dvt.getTenDonViTinh(), dvt.getCreatedBy(), dvt.getUpdatedBy()
            };
            model.addRow(row);

        }
    }

    void fillTableThuongHieu(List<ThuongHieu> listThuongHieu) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (ThuongHieu th : listThuongHieu) {
            Object[] row = {
                i++, th.getMaThuongHieu(), th.getTenThuongHieu(), th.getCreatedBy(), th.getUpdatedBy()
            };
            model.addRow(row);

        }
    }

    public void fillTableXuatXu(List<XuatXu> listXX) {
        model = (DefaultTableModel) tblThuocTinh.getModel();
        model.setRowCount(0);
        int i = 1;
        for (XuatXu xx : listXX) {
            Object[] row = {
                i++, xx.getMaXuatXu(), xx.getTenXuatXu(), xx.getCreatedBy(), xx.getUpdatedBy()
            };
            model.addRow(row);

        }

    }

    private XuatXu readFormXuatXu() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Xuất Xứ");
            return null;
        } else {
            return new XuatXu(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    private KhoiLuong readFormKhoiLuong() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Khối Lượng!");
            return null;
        } else {
            return new KhoiLuong(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    private DonViTinh readFormDonViTinh() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Đơn Vị Tính!");
            return null;
        } else {
            return new DonViTinh(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    private ThuongHieu readFormThuongHieu() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Thương Hiệu!");
            return null;
        } else {
            return new ThuongHieu(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    private ChatLieu readFormChatLieuTab3() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Chất Liệu!");
            return null;
        } else {
            return new ChatLieu(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    private LoaiSanPham readFormLoaiSanPham() {
        if (txtTenTab3.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Điền Tên Loại Sản Phẩm!");
            return null;
        } else {
            return new LoaiSanPham(txtMaTab3.getText(), txtTenTab3.getText(), Auth.idNhanVien(), false);
        }
    }

    public String genMaChatLieu() {
        ChatLieu cl = serviceChatLieu.selectTop1DESC();
        String maSub = cl.getMaChatLieu().substring(2, cl.getMaChatLieu().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "CL" + (soVC + 1);
        return maFinal;
    }

    public String genLoaiSanPham() {
        LoaiSanPham sp = serviceLoaiSanPham.selectTop1DESC();
        String maSub = sp.getMaLoaiSanPham().substring(3, sp.getMaLoaiSanPham().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "LSP" + (soVC + 1);
        return maFinal;
    }

    public String genDonViTinh() {
        DonViTinh sp = serviceDonViTinh.selectTop1DESC();
        String maSub = sp.getMaDonViTinh().substring(3, sp.getMaDonViTinh().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "DVT" + (soVC + 1);
        return maFinal;
    }

    public String genThuongHieu() {
        ThuongHieu sp = serviceThuongHieu.selectTop1DESC();
        String maSub = sp.getMaThuongHieu().substring(2, sp.getMaThuongHieu().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "TH" + (soVC + 1);
        return maFinal;
    }

    public String genKhoiLuong() {
        KhoiLuong sp = serviceKhoiLuong.selectTop1DESC();
        String maSub = sp.getMaKhoiLuong().substring(2, sp.getMaKhoiLuong().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "KL" + (soVC + 1);
        return maFinal;
    }

    public String genXuatXu() {
        XuatXu sp = serviceXuatXu.selectTop1DESC();
        String maSub = sp.getMaXuatXu().substring(2, sp.getMaXuatXu().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "XX" + (soVC + 1);
        return maFinal;
    }

    public String gennerateMa() {
        SanPham sp = spsv.selectTop1DESC();
        String maSub = sp.getMaSanPham().substring(2, sp.getMaSanPham().indexOf(" "));
        Integer soVC = Integer.parseInt(maSub);
        String maFinal = "SP" + (soVC + 1);
        return maFinal;
    }

    void showDataXuatXu(int index) {
        XuatXu xx = serviceXuatXu.getAll().get(index);
        txtMaTab3.setText(xx.getMaXuatXu());
        txtTenTab3.setText(xx.getTenXuatXu());
    }

    void showDataChatLieu(int index) {
        ChatLieu chatLieu = serviceChatLieu.getAll().get(index);
        txtMaTab3.setText(chatLieu.getMaChatLieu());
        txtTenTab3.setText(chatLieu.getTenChatLieu());
    }

    void showDataThuongHieu(int index) {
        ThuongHieu th = serviceThuongHieu.getAll().get(index);
        txtMaTab3.setText(th.getMaThuongHieu());
        txtTenTab3.setText(th.getTenThuongHieu());
    }

    void showDataKhoiLuong(int index) {
        KhoiLuong kl = serviceKhoiLuong.getAll().get(index);
        txtMaTab3.setText(kl.getMaKhoiLuong());
        txtTenTab3.setText(kl.getTenKhoiLuong());
    }

    void showDataDonViTinh(int index) {
        DonViTinh dvt = serviceDonViTinh.getAll().get(index);
        txtMaTab3.setText(dvt.getMaDonViTinh());
        txtTenTab3.setText(dvt.getTenDonViTinh());
    }

    void showDataLoaiSanPham(int index) {
        LoaiSanPham lsp = serviceLoaiSanPham.getAll().get(index);
        txtMaTab3.setText(lsp.getMaLoaiSanPham());
        txtTenTab3.setText(lsp.getTenLoaiSanPham());
    }

    public void clearFormThuocTinh() {
        txtMaTab3.setText("");
        txtTenTab3.setText("");
        this.fillComboBoxChatLieu();
        this.fillComboBoxDonViTinh();
        this.fillComnoBoxLoaiSanPham();
        this.fillComboBoxKhoiLuong();
        this.fillComboBoxThuongHieu();
        this.fillComboBoxXuatXu();
    }

    public boolean checkThuocTinh() {
        if (txtTenTab3.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập tên");
            return false;
        }
        return true;
    }

    private void SanPhamChiTietAllTheoMa() {
        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        SanPham sp = new SanPham();
        int index = tblSanPham.getSelectedRow();
        String ma = tblSanPham.getValueAt(index, 1).toString();

        //sua 7h52
        List<modelChiTietSanPham> list = serImpl.getAll(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    private void SanPhamDangBanByMa() {

        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        int index = tblSanPham.getSelectedRow();
        String ma = tblSanPham.getValueAt(index, 1).toString();

        //sua 7h52
        List<modelChiTietSanPham> list = serImpl.getSanPhamDangBan(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    private void SanPhamDangBan() {
        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        int index = tblSanPham.getSelectedRow();
        //sua 7h52
        List<modelChiTietSanPham> list = serImpl.SanPhamDangBan();
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    private void SanPhamNgungBanByMa() {
        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        int index = tblSanPham.getSelectedRow();
        String ma = tblSanPham.getValueAt(index, 1).toString();
        List<modelChiTietSanPham> list = serImpl.getSanPhamDungBan(ma);
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    private void SanPhamNgungBan() {
        DefaultTableModel model1 = (DefaultTableModel) tblctsp.getModel();
        model1.setRowCount(0);
        int i = 1;
        List<modelChiTietSanPham> list = serImpl.SanPhamDungBan();
        for (modelChiTietSanPham model : list) {
            String trangThai = "";
            if (model.isDeleted() == false) {
                trangThai = "Đang Bán";
            } else {
                trangThai = "Tạm Ngưng Bán";
            }
            Object[] row = {i++, model.getMaSp(), model.getTenSanPham(), model.getTenLoaiSanPham(), model.getTenThuongHieu(),
                model.getTenXuatXu(), model.getTenChatLieu(), model.getTenDonVitinh(), model.getTenKhoiLuong(), model.getDonGia(),
                model.getBarcode(), model.getGhiChu(), model.getSoLuongTon(), model.getNgayXuatKho(), trangThai};
            model1.addRow(row);
        }

    }

    public String genQR() {
        int numberOfDigits = 12;
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < numberOfDigits; i++) {
            int digit = random.nextInt(10);
            codeBuilder.append(digit);
        }
        return codeBuilder.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaSp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fillMa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbbTensp = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuongTon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbblsp = new javax.swing.JComboBox<>();
        cbbxx = new javax.swing.JComboBox<>();
        cbbdvt = new javax.swing.JComboBox<>();
        cbbkl = new javax.swing.JComboBox<>();
        cbbth = new javax.swing.JComboBox<>();
        cbbcl = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        quetQRbtn = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtBarcode = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtFillCTSP = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblctsp = new javax.swing.JTable();
        rboTatCa = new javax.swing.JRadioButton();
        RboNgung = new javax.swing.JRadioButton();
        rboDangBan = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtMaTab3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTenTab3 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        rbXx = new javax.swing.JRadioButton();
        rbkl = new javax.swing.JRadioButton();
        rbdvt = new javax.swing.JRadioButton();
        rbth = new javax.swing.JRadioButton();
        rbChatLieu = new javax.swing.JRadioButton();
        rblsp = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();

        tab.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel1.setText("Mã Sản Phẩm ");

        txtMaSp.setEnabled(false);
        txtMaSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSpActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên Sản Phẩm");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSp, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(txtTenSanPham))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        btnAdd.setBackground(new java.awt.Color(100, 180, 244));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(100, 180, 244));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(100, 180, 244));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnAdd)
                .addGap(34, 34, 34)
                .addComponent(btnSua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnClear)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel3.setText("Tìm kiếm");

        fillMa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fillMaKeyReleased(evt);
            }
        });

        tblSanPham.setBackground(new java.awt.Color(255, 255, 255));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số lượng"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(90, 90, 90)
                        .addComponent(fillMa, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fillMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        tab.addTab("Sản Phẩm", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel4.setText("Tên sản phẩm");

        cbbTensp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Số lượng tồn");

        jLabel7.setText("Giá bán");

        jLabel8.setText("Mô tả");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        jLabel9.setText("Tên loại sản phẩm");

        jLabel10.setText("Xuất xứ");

        jLabel11.setText("Đơn vị tính");

        jLabel12.setText("Khối lượng");

        jLabel13.setText("Barcode");

        jLabel14.setText("Thương hiệu");

        jLabel15.setText("Chất liệu");

        cbblsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbxx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbdvt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbkl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbcl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        btnThemSP.setBackground(new java.awt.Color(100, 180, 244));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(100, 180, 244));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Làm mới");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(100, 180, 244));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Xuất file Excel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        quetQRbtn.setBackground(new java.awt.Color(100, 180, 244));
        quetQRbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        quetQRbtn.setForeground(new java.awt.Color(255, 255, 255));
        quetQRbtn.setText("Quét QR");
        quetQRbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quetQRbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quetQRbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnThemSP)
                .addGap(31, 31, 31)
                .addComponent(quetQRbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(37, 37, 37)
                .addComponent(jButton8)
                .addGap(30, 30, 30))
        );

        jButton13.setBackground(new java.awt.Color(100, 180, 244));
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("+");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(100, 180, 244));
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("+");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(100, 180, 244));
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("+");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(100, 180, 244));
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("+");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(100, 180, 244));
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("+");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(100, 180, 244));
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("+");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(100, 180, 244));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13))
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbTensp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSoLuongTon)
                    .addComponent(txtDonGia)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(txtBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbkl, 0, 345, Short.MAX_VALUE)
                    .addComponent(cbbth, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbcl, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbxx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbdvt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbblsp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbTensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(cbblsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbbxx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14)
                            .addComponent(jLabel6)
                            .addComponent(txtSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbbdvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15)
                            .addComponent(jLabel7)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cbbkl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16)
                            .addComponent(jLabel13)
                            .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(cbbth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton18))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(cbbcl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton19)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel16.setText("Tìm kiếm ");

        txtFillCTSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFillCTSPKeyReleased(evt);
            }
        });

        tblctsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Thương hiệu", "Xuất xứ", "Chất liệu", "Đơn vị tính", "Khối lượng", "Đơn giá", "Barcode", "Ghi chú", "Số lượng tồn", "Ngày xuất kho", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblctsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblctspMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblctspMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblctsp);

        buttonGroup2.add(rboTatCa);
        rboTatCa.setSelected(true);
        rboTatCa.setText("Tất cả");
        rboTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rboTatCaActionPerformed(evt);
            }
        });

        buttonGroup2.add(RboNgung);
        RboNgung.setText("Ngừng bán");
        RboNgung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RboNgungActionPerformed(evt);
            }
        });

        buttonGroup2.add(rboDangBan);
        rboDangBan.setText("Đang bán ");
        rboDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rboDangBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtFillCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(rboTatCa)
                        .addGap(34, 34, 34)
                        .addComponent(rboDangBan)
                        .addGap(47, 47, 47)
                        .addComponent(RboNgung)))
                .addContainerGap(904, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1344, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtFillCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rboTatCa)
                    .addComponent(RboNgung)
                    .addComponent(rboDangBan))
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(40, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        tab.addTab("Chi Tiết Sản Phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));

        jLabel17.setText("Mã thuộc tính");

        txtMaTab3.setEnabled(false);

        jLabel18.setText("Tên thuộc tính");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaTab3)
                    .addComponent(txtTenTab3, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtMaTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTenTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup1.add(rbXx);
        rbXx.setSelected(true);
        rbXx.setText("Xuất Xứ");
        rbXx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbXxMouseClicked(evt);
            }
        });
        rbXx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbXxActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbkl);
        rbkl.setText("Khối lượng");
        rbkl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbklMouseClicked(evt);
            }
        });

        buttonGroup1.add(rbdvt);
        rbdvt.setText("Đơn vị tính");
        rbdvt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbdvtMouseClicked(evt);
            }
        });

        buttonGroup1.add(rbth);
        rbth.setText("Thương hiệu");
        rbth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbthMouseClicked(evt);
            }
        });

        buttonGroup1.add(rbChatLieu);
        rbChatLieu.setText("Chất liệu");
        rbChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbChatLieuMouseClicked(evt);
            }
        });
        rbChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbChatLieuActionPerformed(evt);
            }
        });

        buttonGroup1.add(rblsp);
        rblsp.setText("Loại sản phẩm");
        rblsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rblspMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbXx)
                    .addComponent(rbdvt)
                    .addComponent(rbkl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbth)
                            .addComponent(rbChatLieu))
                        .addGap(18, 18, 18))
                    .addComponent(rblsp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbXx)
                    .addComponent(rbth))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbkl)
                    .addComponent(rbChatLieu))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbdvt)
                    .addComponent(rblsp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setBackground(new java.awt.Color(100, 180, 244));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(100, 180, 244));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xoá");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(100, 180, 244));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Làm mới");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1)
                .addGap(45, 45, 45)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(26, 26, 26))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setText("Danh sách thuộc tính");

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính"
            }
        ));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblThuocTinhMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        tab.addTab("Thuộc tính chi tiết", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        txtMaSp.setText(this.gennerateMa());
        SanPham sp = readForm();
        if (sp != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Thêm Không?");
            if (confirm == 0) {
                if (serviceSP.addSanPham(sp) > 0) {
                    fillTable(serviceSP.getAll());
                    JOptionPane.showMessageDialog(this, "Thêm Sản Phẩm Thành Công!");
                } else {
                    MsgBox.alert(this, "That bai");
                }
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        SanPham sp = updateForm();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa không ?");
        if (confirm == 0) {
            int index = tblSanPham.getSelectedRow();
            if (index >= 0) {
                String ma = tblSanPham.getValueAt(index, 1).toString();
                if (serviceSP.updateSanPham(sp, ma) > 0) {
                    fillTable(serviceSP.getAll());
                    MsgBox.alert(this, "Sửa thành công");
                    clearForm();
                } else {
                    MsgBox.alert(this, "Sửa thất bại");
                }
            } else {
                MsgBox.alert(this, "Vui lòng chọn dòng để sửa");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblSanPham.getSelectedRow();
        showDataSanPham(index);
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        LoaiSanPhamJDialog main = new LoaiSanPhamJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        XuatXuJDialog main = new XuatXuJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        DonViTinhJDialog main = new DonViTinhJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        KhoiLuongJDialog main = new KhoiLuongJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        ThuongHieuJDialog main = new ThuongHieuJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        ChatLieuJDialog main = new ChatLieuJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        fill();
        clear();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void fillMaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fillMaKeyReleased
        // TODO add your handling code here:
        String ma = fillMa.getText();
        ma = "%" + ma + "%";
        if (serviceSP.getByName(ma) != null) {
            fillTable(serviceSP.getByName(ma));
        }
    }//GEN-LAST:event_fillMaKeyReleased

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        if (g != 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm không ?");
            if (confirm == 0) {
                ChiTietSanPham ctsp = this.checkSanPham();
                if (ctsp != null) {
                    int add = serviceModelChitietSanPham.addChiTietSanPham(ctsp);
                    if (add > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                        //fill();
                        fillbyma();
                        clear();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại !");
                    }
                }
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm không ?");
            if (confirm == 0) {
                ChiTietSanPham ctsp = this.checkSanPham();
                if (ctsp != null) {
                    int add = serviceModelChitietSanPham.addChiTietSanPham(ctsp);
                    if (add > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm Thành Công!");
                        fill();
                        clear();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại !");
                    }
                }
            }
        }

    }//GEN-LAST:event_btnThemSPActionPerformed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
//        if (evt.getClickCount() == 2) {
//            int row = tblSanPham.getSelectedRow();
//            String ma = tblSanPham.getValueAt(row, 1).toString();
//            String tenSp = tblSanPham.getValueAt(row, 2).toString();
//            txtMaSpTab2.setText(ma);
//            cbbTensp.setSelectedItem(tenSp);
//
////            int row = tblSanPham.getSelectedRow();
////            String ma = tblSanPham.getValueAt(row, 1).toString();
//            fillbyma();
//            tab.setSelectedIndex(1);
//        }
        g = 10;
        if (evt.getClickCount() == 2) {
            int row = tblSanPham.getSelectedRow();
            String ma = tblSanPham.getValueAt(row, 1).toString();
            String tenSp = tblSanPham.getValueAt(row, 2).toString();

            fillbyma();

            cbbTensp.setSelectedItem(tenSp);
            tab.setSelectedIndex(1);

        }
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void tblThuocTinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tblThuocTinhMousePressed

    private void tblctspMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblctspMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            openThongTinSanPham();
        }

    }//GEN-LAST:event_tblctspMousePressed

    private void rbXxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbXxMouseClicked
        // TODO add your handling code here:
        if (rbXx.isSelected()) {
            fillTableXuatXu(serviceXuatXu.getAll());
        }
    }//GEN-LAST:event_rbXxMouseClicked

    private void rbChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbChatLieuMouseClicked
        // TODO add your handling code here:
        if (rbChatLieu.isSelected()) {
            fillTableChatLieu(serviceChatLieu.getAll());
        }
    }//GEN-LAST:event_rbChatLieuMouseClicked

    private void rblspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rblspMouseClicked
        // TODO add your handling code here:
        if (rblsp.isSelected()) {
            fillTableLoaiSanPham(serviceLoaiSanPham.getAll());
        }
    }//GEN-LAST:event_rblspMouseClicked

    private void rbklMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbklMouseClicked
        // TODO add your handling code here:
        if (rbkl.isSelected()) {
            fillTableKhoiLuong(serviceKhoiLuong.getAll());
        }
    }//GEN-LAST:event_rbklMouseClicked

    private void rbdvtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbdvtMouseClicked
        // TODO add your handling code here:
        if (rbdvt.isSelected()) {
            fillTableDonViTinh(serviceDonViTinh.getAll());
        }
    }//GEN-LAST:event_rbdvtMouseClicked

    private void rbthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbthMouseClicked
        // TODO add your handling code here:
        if (rbth.isSelected()) {
            fillTableThuongHieu(serviceThuongHieu.getAll());
        }
    }//GEN-LAST:event_rbthMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (rbXx.isSelected()) {
            String ma = genXuatXu();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn  thêm thuộc tính xuất xứ không  ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    XuatXu xx = readFormXuatXu();
                    if (serviceXuatXu.getByName(xx.getTenXuatXu()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceXuatXu.addXuatXu(xx) > 0) {
                            fillTableXuatXu(serviceXuatXu.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
        if (rbkl.isSelected()) {
            String ma = genKhoiLuong();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn  thêm thuộc tính khối lượng không ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    KhoiLuong kl = readFormKhoiLuong();
                    if (serviceKhoiLuong.getByName(kl.getTenKhoiLuong()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceKhoiLuong.addKhoiLuong(kl) > 0) {
                            fillTableKhoiLuong(serviceKhoiLuong.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
        if (rbdvt.isSelected()) {
            String ma = genDonViTinh();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn  thêm thuộc tính đơn vị tính không ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    DonViTinh dvt = readFormDonViTinh();
                    if (serviceDonViTinh.getByName(dvt.getTenDonViTinh()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceDonViTinh.addDonViTinh(dvt) > 0) {
                            fillTableDonViTinh(serviceDonViTinh.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
        if (rbth.isSelected()) {
            String ma = genThuongHieu();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm  thuộc tính thương hiệu không ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    ThuongHieu th = readFormThuongHieu();
                    if (serviceThuongHieu.getByName(th.getTenThuongHieu()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceThuongHieu.addThuongHieu(th) > 0) {
                            fillTableThuongHieu(serviceThuongHieu.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
        if (rbChatLieu.isSelected()) {
            String ma = genMaChatLieu();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn  thêm thuộc tính xuất xứ không ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    ChatLieu cl = readFormChatLieu();
                    if (serviceChatLieu.getByName(cl.getTenChatLieu()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceChatLieu.addChatLieu(cl) > 0) {
                            fillTableChatLieu(serviceChatLieu.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
        if (rblsp.isSelected()) {
            String ma = genLoaiSanPham();
            txtMaTab3.setText(ma);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm thuộc tính loại sản phẩm  không ?");
            if (confirm == 0) {
                if (checkThuocTinh()) {
                    LoaiSanPham lsp = readFormLoaiSanPham();
                    if (serviceLoaiSanPham.getByName(lsp.getTenLoaiSanPham()) != null) {
                        MsgBox.alert(this, "Trùng tên");
                    } else {
                        if (serviceLoaiSanPham.addLoaiSanPham(lsp) > 0) {
                            fillTableLoaiSanPham(serviceLoaiSanPham.getAll());
                            JOptionPane.showMessageDialog(this, "Thêm thành công");
                            clearFormThuocTinh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm thất bại");
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rbXxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbXxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbXxActionPerformed

    private void txtMaSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSpActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        SanPhamJDialog main = new SanPhamJDialog(this, true);
        main.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) tblctsp.getModel();
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
                String[] columnNames = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Thương hiệu", "Xuất xứ", "Chất liệu", "Đơn vị tính", "Khối lượng", "Đơn giá", "Barcode", "Ghi chú", "Số lượng tồn", "Ngày xuất kho", "Trạng thái"};
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
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        int row = tblThuocTinh.getSelectedRow();
        String ma = tblThuocTinh.getValueAt(row, 1).toString();

//        if (rbChatLieu.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceChatLieu.deleteChatLieu(ma) > 0) {
//                    fillTableChatLieu(serviceChatLieu.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
//        if (rbXx.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceXuatXu.deleteXuatXu(ma) > 0) {
//                    fillTableXuatXu(serviceXuatXu.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
//        if (rbth.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceThuongHieu.deleteThuongHieu(ma) > 0) {
//                    fillTableThuongHieu(serviceThuongHieu.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
//        if (rbkl.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceKhoiLuong.deleteKhoiLuong(ma) > 0) {
//                    fillTableKhoiLuong(serviceKhoiLuong.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
//        if (rbdvt.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceDonViTinh.deleteDonViTinh(ma) > 0) {
//                    fillTableDonViTinh(serviceDonViTinh.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
//        if (rblsp.isSelected()) {
//            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
//            if (confirm == 0) {
//                if (serviceLoaiSanPham.deleteLoaiSanPham(ma) > 0) {
//                    fillTableLoaiSanPham(serviceLoaiSanPham.getAll());
//                    MsgBox.alert(this, "Xoá thành công");
//                    clearFormThuocTinh();
//                } else {
//                    MsgBox.alert(this, "Xoá thất bại");
//                }
//            }
//        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá thuộc tính xuất xứ không  ?");
        if (confirm == 0) {
            if (rbChatLieu.isSelected()) {

                if (serviceChatLieu.deleteChatLieu(ma) > 0) {
                    fillTableChatLieu(serviceChatLieu.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");
                }

            }
            if (rbXx.isSelected()) {

                if (serviceXuatXu.deleteXuatXu(ma) > 0) {
                    fillTableXuatXu(serviceXuatXu.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");

                }
            }
            if (rbth.isSelected()) {

                if (serviceThuongHieu.deleteThuongHieu(ma) > 0) {
                    fillTableThuongHieu(serviceThuongHieu.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");
                }

            }
            if (rbkl.isSelected()) {

                if (serviceKhoiLuong.deleteKhoiLuong(ma) > 0) {
                    fillTableKhoiLuong(serviceKhoiLuong.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");
                }
            }

            if (rbdvt.isSelected()) {

                if (serviceDonViTinh.deleteDonViTinh(ma) > 0) {
                    fillTableDonViTinh(serviceDonViTinh.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");
                }

            }
            if (rblsp.isSelected()) {

                if (serviceLoaiSanPham.deleteLoaiSanPham(ma) > 0) {
                    fillTableLoaiSanPham(serviceLoaiSanPham.getAll());
                    MsgBox.alert(this, "Xoá thành công");
                    clearFormThuocTinh();
                } else {
                    MsgBox.alert(this, "Xoá thất bại");
                }

            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        if (rbXx.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataXuatXu(index);
        }
        if (rbChatLieu.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataChatLieu(index);
        }
        if (rbth.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataThuongHieu(index);
        }
        if (rbdvt.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataDonViTinh(index);
        }
        if (rbkl.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataKhoiLuong(index);
        }
        if (rblsp.isSelected()) {
            int index = tblThuocTinh.getSelectedRow();
            showDataLoaiSanPham(index);
        }

    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearFormThuocTinh();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rbChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbChatLieuActionPerformed

    private void tblctspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblctspMouseClicked
        // TODO add your handling code here:
//        int row = tblctsp.getSelectedRow();
//        showDataChiTietSanPhamByMa(row);
    }//GEN-LAST:event_tblctspMouseClicked

    private void rboDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rboDangBanActionPerformed
        // TODO add your handling code here:
        if (g != 0) {
            SanPhamDangBanByMa();
        } else {
            SanPhamDangBan();
        }
    }//GEN-LAST:event_rboDangBanActionPerformed

    private void rboTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rboTatCaActionPerformed
        // TODO add your handling code here:
        if (g != 0) {
            SanPhamChiTietAllTheoMa();
        } else {
            fill();
        }
    }//GEN-LAST:event_rboTatCaActionPerformed

    private void RboNgungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RboNgungActionPerformed
        // TODO add your handling code here:
        if (g != 0) {
            SanPhamNgungBanByMa();
        } else {
            SanPhamNgungBan();
        }
    }//GEN-LAST:event_RboNgungActionPerformed

    private void txtFillCTSPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFillCTSPKeyReleased
        // TODO add your handling code here:
        String input = txtFillCTSP.getText();
        input = "%" + input + "%";
        if (serviceModelChitietSanPham.getAllByInput(input) != null) {
            CTSP(serviceModelChitietSanPham.getAllByInput(input));
        }


    }//GEN-LAST:event_txtFillCTSPKeyReleased

    private void quetQRbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quetQRbtnActionPerformed
        // TODO add your handling code here:
        new ScanQRCTSP(this, true).setVisible(true);
    }//GEN-LAST:event_quetQRbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RboNgung;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThemSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbTensp;
    private javax.swing.JComboBox<String> cbbcl;
    private javax.swing.JComboBox<String> cbbdvt;
    private javax.swing.JComboBox<String> cbbkl;
    private javax.swing.JComboBox<String> cbblsp;
    private javax.swing.JComboBox<String> cbbth;
    private javax.swing.JComboBox<String> cbbxx;
    private javax.swing.JTextField fillMa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton quetQRbtn;
    private javax.swing.JRadioButton rbChatLieu;
    private javax.swing.JRadioButton rbXx;
    private javax.swing.JRadioButton rbdvt;
    private javax.swing.JRadioButton rbkl;
    private javax.swing.JRadioButton rblsp;
    private javax.swing.JRadioButton rboDangBan;
    private javax.swing.JRadioButton rboTatCa;
    private javax.swing.JRadioButton rbth;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTable tblctsp;
    private javax.swing.JTextField txtBarcode;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtFillCTSP;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaSp;
    private javax.swing.JTextField txtMaTab3;
    private javax.swing.JTextField txtSoLuongTon;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenTab3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void fillText(String ma) {
    }
}
