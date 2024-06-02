/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.Contains;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import itf.ChooseKH;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import modelview.modelChiTietSanPham;
import model.ChiTietSanPham;
import model.GioHang;
import model.HinhThucThanhToan;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.LoaiSanPham;
import model.Voucher;
import repositoryImpl.ChiTietSanPhamRepoImpl;
import serviceImpl.ChiTietSanPhamServiceImpl;
import serviceImpl.HTTTServiceImpl;
import serviceImpl.HoaDonChiTietServiceImpl;
import serviceImpl.HoaDonServiceImpl;
import serviceImpl.KhachHangServiceImpl;
import serviceImpl.LoaiSanPhamServiceImpl;
import serviceImpl.SanPhamServiceImpl;
import serviceImpl.VoucherServiceImpl;
import thread.MyThread;
import utils.Auth;
import utils.genneratePDF;
import view.Contain.EntitySanPham.KhachHangJDialog;

public class BanHangJPanel extends javax.swing.JPanel implements ChooseKH, ThreadFactory, Runnable {

    //Static
    private static List<GioHang> listGH = new ArrayList<>();
//    private List<GioHang> listGH = new ArrayList<>();
    //Call Model
    DefaultTableModel DSSPModel = new DefaultTableModel();
    DefaultTableModel HoaDonModel = new DefaultTableModel();
    DefaultTableModel GioHangModel = new DefaultTableModel();
    //Call Service
    SanPhamServiceImpl spsv = new SanPhamServiceImpl();
    HoaDonServiceImpl hdsv = new HoaDonServiceImpl();
    ChiTietSanPhamServiceImpl ctspsv = new ChiTietSanPhamServiceImpl();
    HTTTServiceImpl htttsv = new HTTTServiceImpl();
    KhachHangServiceImpl khsv = new KhachHangServiceImpl();
    HoaDonChiTietServiceImpl hdctsv = new HoaDonChiTietServiceImpl();
    VoucherServiceImpl vcsv = new VoucherServiceImpl();
    LoaiSanPhamServiceImpl lspsv = new LoaiSanPhamServiceImpl();
    //Call Repo
    ChiTietSanPhamRepoImpl ctspr = new ChiTietSanPhamRepoImpl();

    //Convert Tiền Tệ Việt Nam
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    //Call List
    List<modelChiTietSanPham> listMCTSP = new ArrayList<>();

    //Call WebCam
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    //Simple Format Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //My Thread
    MyThread mt = new MyThread();

    //Result Webcam
    Integer idVoucher = null;
    Double giaTriVoucher = null;

    public BanHangJPanel() {
        initComponents();
        this.initWebcam();
        mt.start();
        DSSPModel = (DefaultTableModel) DanhSachSanPhamTable.getModel();
        HoaDonModel = (DefaultTableModel) DanhSachHoaDonTable.getModel();
        GioHangModel = (DefaultTableModel) GioHangTable.getModel();
        listMCTSP = ctspr.getAll();
        this.setConfig();
        this.showDataDSSP(listMCTSP);
        this.showDataHoaDon();
        jdcNgayDiDuTinh.setDateFormatString("yyyy-MM-dd");
        jdcNgayNhanDuTinh.setDateFormatString("yyyy-MM-dd");
    }

    //Set config
    private void setConfig() {
        GioHangModel.setRowCount(0);
        DanhSachHoaDonTable.setRowHeight(20);
        GioHangTable.setRowHeight(20);
        DanhSachSanPhamTable.setRowHeight(20);
        lblMaNV.setText(Auth.maNhanVien());
        lblMaNV1.setText(Auth.maNhanVien());
        cbbHinhThucThanhToan.removeAllItems();
        cbbHinhThucThanhToanShip.removeAllItems();
        lblGiamGia.setText(format.format(0));
        lblGiamGiaShip.setText(format.format(0));
        txtMaKH.setText("KH1");
        txtMaKH.setEnabled(false);
        txtTenKH.setText("Khách Lẻ");
        txtTenKH.setEnabled(false);
        txtSDT.setText("not");
        txtSDT.setEnabled(false);
        txtMaKHShip.setText("KH1");
        txtMaKHShip.setEnabled(false);
        txtTenTVShip.setText("Khách Lẻ");
        txtTenTVShip.setEnabled(false);
        for (HinhThucThanhToan httt : htttsv.getAll()) {

            cbbHinhThucThanhToan.addItem(httt.getTenHTTT());
            cbbHinhThucThanhToanShip.addItem(httt.getTenHTTT());
        }
        cbbTimLoaiSP.removeAllItems();
        cbbTimLoaiSP.addItem("Tất Cả");
        for (LoaiSanPham lsp : lspsv.getAll()) {

            cbbTimLoaiSP.addItem(lsp.getTenLoaiSanPham());
        }
        new Timer(1000, new ActionListener() {
            SimpleDateFormat spdfm = new SimpleDateFormat("dd:MM:yyyy hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabChiTiet.getSelectedIndex() == 1) {
                    lblNgayTao1.setText(spdfm.format(new Date()));
                } else {
                    lblNgayTao.setText(spdfm.format(new Date()));
                }
            }
        }).start();
    }

    //FillData Hóa Đơn
    private void showDataHoaDon() {
        HoaDonModel.setRowCount(0);
        int i = 0;
        for (Object[] row : hdsv.DanhSachHoaDon()) {
            i++;
            String tenNV = String.valueOf(row[1]);
            String tenNVSub = tenNV.substring(0, tenNV.indexOf(" "));
            HoaDonModel.addRow(new Object[]{i, row[0], tenNVSub, row[2], row[3]});
        }
    }

    //chọn Khách Hàng
    public void chooseKH(KhachHang kh) {
        this.chonKH(kh);
    }

    //chooseHTTT
    private void chooseHTTT() {
        String httt = (String) cbbHinhThucThanhToan.getSelectedItem();
        if (httt != null) {
            if (httt.equalsIgnoreCase("Tiền Mặt")) {
                txtKhachTra.setEnabled(true);
                txtKhachTraCK.setEnabled(false);
            } else if (httt.equalsIgnoreCase("Chuyển Khoản")) {
                txtKhachTra.setEnabled(false);
                txtKhachTraCK.setEnabled(true);
            } else {
                txtKhachTra.setEnabled(true);
                txtKhachTraCK.setEnabled(true);
            }
        }

    }

    //FillData Sản Phẩm
    private void showDataDSSP(List<modelChiTietSanPham> list) {
        DSSPModel.setRowCount(0);
        for (modelChiTietSanPham mctsp : list) {
            Object[] row = {false, mctsp.getMaSp(), mctsp.getTenSanPham(), mctsp.getTenLoaiSanPham(), mctsp.getTenThuongHieu(),
                mctsp.getTenXuatXu(), mctsp.getTenChatLieu(), mctsp.getTenDonVitinh(), mctsp.getTenKhoiLuong(), format.format(mctsp.getDonGia()),
                mctsp.getSoLuongTon()};
            DSSPModel.addRow(row);
        }
    }

    //FillData Giỏ Hàng
    private void showDataGioHang(List<GioHang> list) {
        GioHangModel.setRowCount(0);
        int i = 0;
        for (GioHang gh1 : list) {
            i++;
            GioHangModel.addRow(new Object[]{i, gh1.getMaSP(), gh1.getTenSP(), gh1.getSoLuong(), gh1.getThanhTien()});
        }
    }

    //FillData Giỏ Hàng
    private void showDataGioHangByHD(List<GioHang> list, String hoaDon) {
        GioHangModel.setRowCount(0);
        int i = 0;
        for (GioHang gh1 : list) {
            if (hoaDon.equalsIgnoreCase(gh1.getMaHoaDon())) {
                i++;
                GioHangModel.addRow(new Object[]{i, gh1.getMaSP(), gh1.getTenSP(), gh1.getSoLuong(), format.format(gh1.getThanhTien())});
            }
        }
    }

    //init WebCam
    private void initWebcam() {
        try {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getWebcams().get(0);
            webcam.setViewSize(size);
            panel = new WebcamPanel(webcam);
            panel.setPreferredSize(size);
            panel.setFPSDisplayed(true);
            WebCamPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 252, 183));
            executor.execute(this);
        } catch (Exception e) {
            this.closeWebcam();
        }
    }

    //closeWebcam
    public void closeWebcam() {
        if (webcam.isOpen()) {
            webcam.close();
        }
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Result result = null;
            BufferedImage image = null;
            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result != null) {
                //Kết Quả Là Voucher thì xử lý
                if (result.getText().startsWith("VC")) {
                    Voucher vc = vcsv.findVCByMa(result.getText());
                    if (vc.getTrangThai() == 2) {
                        JOptionPane.showMessageDialog(this, "Mã Voucher Này Đã Hết Hạn!");
                    } else if (vc.getTrangThai() == 3) {
                        JOptionPane.showMessageDialog(this, "Mã Voucher Này Chưa Đến Ngày Áp Dụng!");
                    } else if (vc.getTrangThai() == 4) {
                        JOptionPane.showMessageDialog(this, "Mã Voucher Này Đã Bị Ngưng Áp Dụng!");
                    } else {
                        if (vc.getSoLuong() == 0) {
                            JOptionPane.showMessageDialog(this, "Số Lượng Sử Dụng Của Voucher Này Đã Hết!");
                        } else {
                            String tongTienHangBefore = lblTongTienHang.getText();
                            Number tongTienHangCV;
                            Double tongTienHangAfter = 0.0;
                            String tongTienHangShipBefore = lblTongTienHangShip.getText();
                            Number tongTienHangShipCV;
                            Double tongTienHangShipAfter = 0.0;

                            if (tabChiTiet.getSelectedIndex() == 0) {
                                if (tongTienHangBefore.trim().isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm!");
                                } else {
                                    try {
                                        tongTienHangCV = format.parse(tongTienHangBefore);
                                        tongTienHangAfter = tongTienHangCV.doubleValue();
                                        if (vc.getGiaTriToiThieu() <= tongTienHangAfter && tongTienHangAfter <= vc.getGiaTriToiDa()) {
                                            idVoucher = vc.getIdVoucher();
                                            if (vc.isLoaiVoucher() == true) {
                                                JOptionPane.showMessageDialog(this, "Bạn Được Giám Giá:" + vc.getGiaTri() + "% Cho Hóa Đơn Này!");
                                                lblGiamGia.setText(vc.getGiaTri() + "%");
                                                lblTongTienHang.setText(format.format(tongTienHangAfter - (tongTienHangAfter / 100) * vc.getGiaTri()));
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Bạn Được Giám Giá:" + format.format(vc.getGiaTri()) + " Cho Hóa Đơn Này!");
                                                lblGiamGia.setText(format.format(vc.getGiaTri()));
                                                lblTongTienHang.setText(format.format(tongTienHangAfter - vc.getGiaTri()));
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Tổng Tiền Đơn Hàng Này Phải Nằm Trong Khoảng" + vc.getGiaTriToiThieu() + "->" + vc.getGiaTriToiDa());

                                        }
                                    } catch (ParseException ex) {
                                        Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } else {
                                if (tongTienHangShipBefore.trim().isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm!");
                                } else {
                                    try {
                                        tongTienHangShipCV = format.parse(tongTienHangShipBefore);
                                        tongTienHangShipAfter = tongTienHangShipCV.doubleValue();
                                        if (vc.getGiaTriToiThieu() <= tongTienHangShipAfter && tongTienHangShipAfter <= vc.getGiaTriToiDa()) {
                                            idVoucher = vc.getIdVoucher();
                                            if (vc.isLoaiVoucher() == true) {
                                                JOptionPane.showMessageDialog(this, "Bạn Được Giám Giá:" + vc.getGiaTri() + "% Cho Hóa Đơn Này!");
                                                lblGiamGiaShip.setText(vc.getGiaTri() + "%");
                                                lblTongTienHangShip.setText(format.format(tongTienHangShipAfter - (tongTienHangShipAfter / 100) * vc.getGiaTri()));
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Bạn Được Giám Giá:" + format.format(vc.getGiaTri()) + " Cho Hóa Đơn Này!");
                                                lblGiamGiaShip.setText(format.format(vc.getGiaTri()));
                                                lblTongTienHangShip.setText(format.format(tongTienHangShipAfter - vc.getGiaTri()));
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Tổng Tiền Đơn Hàng Này Phải Nằm Trong Khoảng" + vc.getGiaTriToiThieu() + "->" + vc.getGiaTriToiDa());

                                        }
                                    } catch (ParseException ex) {
                                        Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

//                            giaTriVoucher = vc.getGiaTri();
//                            JOptionPane.showMessageDialog(this, "IdVoucher:" + idVoucher + "Giá Trị:" + giaTriVoucher);
                        }
                    }
                } else {
                    ChiTietSanPham ctsp = ctspsv.getCTSPByBarcode(result.getText());
                    int selectedHD = DanhSachHoaDonTable.getSelectedRow();
                    if (selectedHD >= 0) {
                        String trangThai = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
                        String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
                        if (trangThai.equalsIgnoreCase("Chờ Thanh Toán")) {
                            //Lấy id CTSP
                            modelChiTietSanPham mctsp = ctspr.getMCTSPByID(ctsp.getIdCTSP());
                            //Nếu Sản Phẩm Đó Có Trong LIst r thì thực hiện update
                            GioHang gh = this.getGH(ctsp.getIdCTSP(), maHD);
                            if (gh != null) {
                                //Trùng
                                String soLuong = JOptionPane.showInputDialog("Mời Bạn Nhập Số Lượng:");
                                if (soLuong != null) {
                                    try {
                                        int soLuongMua = Integer.parseInt(soLuong);
                                        if (soLuongMua <= 0) {
                                            JOptionPane.showMessageDialog(this, "Số Lượng Mua Phải Lớn Hơn 0!");
                                        } else {
                                            if (soLuongMua > ctsp.getSoLuongTon()) {
                                                JOptionPane.showMessageDialog(this, "Số Lượng Kho Không Đủ!");
                                            } else {
                                                gh.setSoLuong(soLuongMua + gh.getSoLuong());
                                                double tongTien = gh.getSoLuong() * ctsp.getDonGia();
                                                gh.setThanhTien(tongTien);
                                                int soLuongCon = (ctsp.getSoLuongTon() - soLuongMua);
                                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(soLuongCon, ctsp.getIdCTSP());
                                                if (updateSoLuong == true) {
                                                    double tongTienHang = 0.0;
                                                    for (GioHang gh2 : listGH) {
                                                        if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 0) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        } else if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 1) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        }
                                                    }
                                                    showDataGioHangByHD(listGH, maHD);
                                                    showDataDSSP(ctspr.getAll());
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
                                    }
                                }
                            } else {
                                //Không Trùng
                                String soLuong = JOptionPane.showInputDialog("Mời Bạn Nhập Số Lượng:");
                                if (soLuong != null) {
                                    try {
                                        int soLuongMua = Integer.parseInt(soLuong);
                                        if (soLuongMua <= 0) {
                                            JOptionPane.showMessageDialog(this, "Số Lượng Mua Phải Lớn Hơn 0!");
                                        } else {
                                            if (soLuongMua > ctsp.getSoLuongTon()) {
                                                JOptionPane.showMessageDialog(this, "Số Lượng Kho Không Đủ!");
                                            } else {
                                                GioHang gh1 = new GioHang();
                                                gh1.setIdCTSP(ctsp.getIdCTSP());
                                                gh1.setMaSP(mctsp.getMaSp());
                                                gh1.setTenSP(mctsp.getTenSanPham());
                                                gh1.setSoLuong(soLuongMua);
                                                gh1.setMaHoaDon(maHD);
                                                Double thanhTien = (ctsp.getDonGia() * soLuongMua);
                                                gh1.setThanhTien(thanhTien);
                                                listGH.add(gh1);
                                                int soLuongCon = (ctsp.getSoLuongTon() - soLuongMua);
                                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(soLuongCon, ctsp.getIdCTSP());
                                                if (updateSoLuong == true) {
                                                    double tongTienHang = 0.0;
                                                    for (GioHang gh2 : listGH) {
                                                        if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 0) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        } else if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 1) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        }
                                                    }
                                                    this.showDataGioHangByHD(listGH, maHD);
                                                    this.showDataDSSP(ctspr.getAll());
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Hóa Đơn Này Không Thể Thêm SP");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Chưa Chọn Hóa Đơn!");
                    }
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    //Chọn Sản Phẩm Đẩy Lên Giỏ Hàng
    private void pushSPToCart() {
        //code new
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String trangThai = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
            if (trangThai.equalsIgnoreCase("Chờ Thanh Toán")) {
                int selected = DanhSachSanPhamTable.getSelectedRow();
                if (selected >= 0) {
                    modelChiTietSanPham mctsp = listMCTSP.get(selected);
                    //
                    System.out.println(mctsp.getMaSp());
                    //
                    boolean choose = (boolean) DanhSachSanPhamTable.getValueAt(selected, 0);
                    if (choose == true) {
                        int confirm = JOptionPane.showConfirmDialog(this, "Bạn Muốn Chọn Sản Phẩm Này ?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            //Lấy id CTSP
                            ChiTietSanPham ctsp = ctspsv.getCTSPByID(mctsp.getIdCTSP());
                            //Nếu Sản Phẩm Đó Có Trong LIst r thì thực hiện update
                            GioHang gh = this.getGH(ctsp.getIdCTSP(), maHD);
                            if (gh != null) {
                                //Trùng
                                String soLuong = JOptionPane.showInputDialog("Mời Bạn Nhập Số Lượng:");
                                if (soLuong != null) {
                                    try {
                                        int soLuongMua = Integer.parseInt(soLuong);
                                        if (soLuongMua <= 0) {
                                            JOptionPane.showMessageDialog(this, "Số Lượng Mua Phải Lớn Hơn 0!");
                                        } else {
                                            if (soLuongMua > ctsp.getSoLuongTon()) {
                                                JOptionPane.showMessageDialog(this, "Số Lượng Kho Không Đủ!");
                                            } else {
                                                gh.setSoLuong(soLuongMua + gh.getSoLuong());
                                                double tongTien = gh.getSoLuong() * ctsp.getDonGia();
                                                gh.setThanhTien(tongTien);
                                                int soLuongCon = (ctsp.getSoLuongTon() - soLuongMua);
                                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(soLuongCon, ctsp.getIdCTSP());
                                                if (updateSoLuong == true) {
                                                    for (GioHang gh2 : listGH) {
                                                        double tongTienHang = 0.0;
                                                        if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 0) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        } else if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 1) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        }
                                                    }
                                                    this.showDataGioHangByHD(listGH, maHD);
                                                    this.showDataDSSP(ctspr.getAll());
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
                                    }
                                }
                            } else {
                                //Không Trùng
                                String soLuong = JOptionPane.showInputDialog("Mời Bạn Nhập Số Lượng:");
                                if (soLuong != null) {
                                    try {
                                        int soLuongMua = Integer.parseInt(soLuong);
                                        if (soLuongMua <= 0) {
                                            JOptionPane.showMessageDialog(this, "Số Lượng Mua Phải Lớn Hơn 0!");
                                        } else {
                                            if (soLuongMua > ctsp.getSoLuongTon()) {
                                                JOptionPane.showMessageDialog(this, "Số Lượng Kho Không Đủ!");
                                            } else {
                                                GioHang gh1 = new GioHang();
                                                gh1.setIdCTSP(ctsp.getIdCTSP());
                                                gh1.setMaSP(mctsp.getMaSp());
                                                gh1.setTenSP(mctsp.getTenSanPham());
                                                gh1.setSoLuong(soLuongMua);
                                                gh1.setMaHoaDon(maHD);
                                                Double thanhTien = (ctsp.getDonGia() * soLuongMua);
                                                gh1.setThanhTien(thanhTien);
                                                listGH.add(gh1);
                                                int soLuongCon = (ctsp.getSoLuongTon() - soLuongMua);
                                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(soLuongCon, ctsp.getIdCTSP());
                                                if (updateSoLuong == true) {
                                                    double tongTienHang = 0.0;
                                                    for (GioHang gh2 : listGH) {
                                                        if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 0) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        } else if (gh2.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 1) {
                                                            tongTienHang += gh2.getThanhTien();
                                                            lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                                        }
                                                    }
                                                    this.showDataGioHangByHD(listGH, maHD);
                                                    this.showDataDSSP(ctspr.getAll());
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, "Sai Dữ Liệu");
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Không Thể Thêm SP");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn");
        }
    }

    private GioHang getGH(Integer idCTSP, String maHD) {
        for (int i = 0; i < listGH.size(); i++) {
            GioHang gh1 = listGH.get(i);
            if (gh1.getIdCTSP() == idCTSP && gh1.getMaHoaDon() == maHD) {
                return gh1;
            }
        }
        return null;
    }

    //Tìm SPCT
    private void findSPCT() {
        String input = txtFind.getText();
        listMCTSP = ctspsv.findCTSP(input);
        showDataDSSP(listMCTSP);
//        for (modelChiTietSanPham row : list) {
//            DSSPModel.addRow(new Object[]{false, row.getMaSp(), row.getTenSanPham(), row.getTenLoaiSanPham(), row.getTenThuongHieu(), row.getTenXuatXu(),
//                row.getTenChatLieu(), row.getTenDonVitinh(), row.getTenKhoiLuong(), row.getDonGia(), row.getSoLuongTon()});
//        }

    }

    private void findSPCTByLoai() {
        String cbblsp = (String) cbbTimLoaiSP.getSelectedItem();
        if (cbblsp != null) {
            if (cbblsp.equalsIgnoreCase("Tất Cả")) {
                this.showDataDSSP(listMCTSP);
            } else {
                String input = (String) cbbTimLoaiSP.getSelectedItem();
                if (input != null) {
                    listMCTSP = ctspsv.findCTSP(input);
                    showDataDSSP(listMCTSP);
                }
            }
        }
    }

    //Xóa Sản Phẩm Khỏi Giỏ Hàng
    private void XoaSanPhamKhoiGioHang() {
        //new code
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            if (trangThaiHD.equalsIgnoreCase("Chờ Thanh Toán")) {
                int selected = GioHangTable.getSelectedRow();
                if (selected >= 0) {
                    GioHang gh = listGH.get(selected);
                    ChiTietSanPham ctsp = ctspsv.getCTSPByID(gh.getIdCTSP());
                    boolean updateSoLuong = ctspsv.updateSoLuongCTSP(ctsp.getSoLuongTon() + gh.getSoLuong(), ctsp.getIdCTSP());
                    if (updateSoLuong == true) {
                        listGH.remove(gh);
                        double tongTienHang = 0.0;
                        for (GioHang gh1 : listGH) {
                            if (gh1.getMaHoaDon().equalsIgnoreCase(maHD)) {
                                tongTienHang += gh1.getThanhTien();
                            }
                        }
                        if (tabChiTiet.getSelectedIndex() == 0) {
                            lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                        } else {
                            lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                        }
                        this.showDataGioHangByHD(listGH, maHD);
                        this.showDataDSSP(ctspr.getAll());
                        JOptionPane.showMessageDialog(this, "Xóa Sản Phẩm Thành Công!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Được Vận Chuyển Không Thể Xóa!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }

    }

    //Xóa ALl Sản Phẩm Giỏ Hàng
    private void XoaAllSanPhamGioHang() {
        //new code
        int selected = DanhSachHoaDonTable.getSelectedRow();
        if (selected >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selected, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selected, 3);
            if (trangThaiHD.equalsIgnoreCase("Chờ Thanh Toán")) {
                Iterator<GioHang> itr = listGH.iterator();
                while (itr.hasNext()) {
                    GioHang gh = itr.next();
                    if (gh.getMaHoaDon().equals(maHD)) {
                        ChiTietSanPham ctsp = ctspsv.getCTSPByID(gh.getIdCTSP());
                        boolean updateSoLuong = ctspsv.updateSoLuongCTSP(ctsp.getSoLuongTon() + gh.getSoLuong(), ctsp.getIdCTSP());
                        if (updateSoLuong == true) {
                            itr.remove();
                        }
                    }
                }
                if (tabChiTiet.getSelectedIndex() == 0) {
                    lblTongTienHang.setText("");
                } else {
                    lblTongTienHangShip.setText("");
                }
                this.showDataGioHangByHD(listGH, maHD);
                this.showDataDSSP(ctspr.getAll());
                JOptionPane.showMessageDialog(this, "Xóa Tất Cả Sản Phẩm Thành Công!");
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Được Vận Chuyển Không Thể Xóa!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }

    }

    //Open View Khách Hàng
    private void openViewKhachHang() {
        KhachHangJDialog khj = new KhachHangJDialog(this, true);
        khj.setVisible(true);
    }

    //Create Hoa Don
    private void createHoaDon() {
        Integer soLuongHDCho = hdsv.soLuongDonHangCho();
        if (soLuongHDCho == 5) {
            JOptionPane.showMessageDialog(this, "Đã Tạo Quá Hóa Đơn Chờ Cho Phép!");
        } else {
            HoaDon hd = new HoaDon();
            String maHD = this.gennerateMa();
            hd.setMaHoaDon(maHD);
            hd.setIdNhanVien(Auth.idNhanVien());
            hd.setIdTTHD(5);
            hd.setNgayTao(this.getToDay());
            boolean createHD = hdsv.createBill(hd);
            if (createHD == true) {
                this.showDataHoaDon();
                GioHangModel.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Tạo Hóa Đơn Thành Công!");
            }
        }
    }

    //genQR
    private void gennerateQRcode(String barcode) {
        try {
            String data = barcode;
            String path1 = "src\\main\\resources\\QRHoaDon\\" + barcode + ".png";
            QRCodeWriter qc = new QRCodeWriter();
            BitMatrix bm = qc.encode(data, BarcodeFormat.QR_CODE, 211, 157);
            Path path = FileSystems.getDefault().getPath(path1);
            MatrixToImageWriter.writeToPath(bm, "PNG", path);
        } catch (Exception e) {
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

    //Thanh Toán Bill
    private void thanhToanBill() throws ParseException, FileNotFoundException, MalformedURLException {
        int selected = DanhSachHoaDonTable.getSelectedRow();

        if (selected >= 0) {
            String tienThuaBefore = lblTienThua.getText();
            if (txtMaKH.getText().trim().isEmpty() || txtTenKH.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Khách Hàng!");
            } else {
                if (lblTongTienHang.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm!");
                } else {
                    if (tienThuaBefore.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Chưa Trả Đủ Tiền!");
                    } else {
                        Number tienThuaCV = format.parse(tienThuaBefore);
                        Double tienThuaAfter = tienThuaCV.doubleValue();
                        if (tienThuaAfter < 0) {
                            JOptionPane.showMessageDialog(this, "Chưa Trả Đủ Tiền!");
                        } else {
                            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Thanh Toán Hóa Đơn Này!", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                //Mã HĐ
                                String maHD = (String) DanhSachHoaDonTable.getValueAt(selected, 1);
                                //Tìm Kiếm THeo
                                KhachHang kh = khsv.findKHByMa(txtMaKH.getText());
                                String cbbHttt = (String) cbbHinhThucThanhToan.getSelectedItem();
                                HinhThucThanhToan httt = htttsv.findHTTTByName(cbbHttt);

                                //COnvert
//                                String diemKHBefore = lblGiamGia.getText();
//                                Number diemKHCV = format.parse(diemKHBefore);
//                                Double diemKHAfter = diemKHCV.doubleValue();
                                String tongTienHangBefore = lblTongTienHang.getText();
                                Number tongTienHangCV = format.parse(tongTienHangBefore);
                                Double tongTienHangAfter = tongTienHangCV.doubleValue();

                                String khachTraTMBefore = txtKhachTra.getText();
                                String khachTraCKBefore = txtKhachTraCK.getText();
                                Double khachTraTMAfter = 0.0;
                                Double khachTraCKAfter = 0.0;

                                if (cbbHttt.equalsIgnoreCase("Tiền Mặt")) {
                                    khachTraTMAfter = Double.parseDouble(khachTraTMBefore);
                                } else if (cbbHttt.equalsIgnoreCase("Chuyển Khoản")) {
                                    khachTraCKAfter = Double.parseDouble(khachTraCKBefore);
                                } else {
                                    khachTraTMAfter = Double.parseDouble(khachTraTMBefore);
                                    khachTraCKAfter = Double.parseDouble(khachTraCKBefore);
                                }

                                String tienThuaBefore1 = lblTienThua.getText();
                                Number tienThuaCV1 = format.parse(tienThuaBefore1);
                                Double tienThuaAfter1 = tienThuaCV1.doubleValue();

                                //Hỏi Mã Giao Dịch
                                String maGiaoDich = "not";
                                if (cbbHttt.equalsIgnoreCase("Tiền Mặt")) {
                                    maGiaoDich = "not";
                                } else if (cbbHttt.equalsIgnoreCase("Chuyển Khoản")) {
                                    maGiaoDich = JOptionPane.showInputDialog("Xin Mời Nhập Mã Giao Dịch");
                                } else {
                                    maGiaoDich = JOptionPane.showInputDialog("Xin Mời Nhập Mã Giao Dịch");
                                }
                                if (maGiaoDich.trim().isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "Mã Giao Dịch Không Được Trống");
                                } else {
                                    //Tạo 1 Hóa Đơn Để update
                                    HoaDon hd = new HoaDon();
                                    hd.setIdKhachHang(kh.getIdKhachHang());
                                    hd.setIdHTTT(httt.getIdHTTT());
                                    hd.setIdTTHD(2);
                                    hd.setGiamGiaDiemKH(0.0);
                                    hd.setThueVAT(5);
                                    hd.setTongTienHang(tongTienHangAfter);
                                    hd.setKhachTraTM(khachTraTMAfter);
                                    hd.setKhachTraCK(khachTraCKAfter);
                                    hd.setTienThua(tienThuaAfter1);
                                    hd.setTenNguoiNhan("not");
                                    hd.setSdtNguoiNhan("not");
                                    hd.setDiaChiNguoiNhan("not");
                                    hd.setPhiShip(0.0);
                                    hd.setGhiChu(txtGhiChu.getText());
                                    hd.setMaGiaoDich(maGiaoDich);
                                    hd.setIdVoucher(idVoucher);
                                    boolean thanhToanHD = hdsv.ThanhToanHoaDon(hd, maHD);
                                    if (thanhToanHD == true) {
                                        HoaDonChiTiet hdct = new HoaDonChiTiet();
                                        HoaDon hd1 = hdsv.getHoaDonByMa(maHD);
                                        this.gennerateQRcode(maHD);
                                        Iterator<GioHang> itr = listGH.iterator();
                                        if (idVoucher != null) {
                                            Voucher vc = vcsv.findVoucherByID(idVoucher);
                                            vcsv.updateSoLuongVoucher((vc.getSoLuong() - 1), idVoucher);
                                        }
                                        int confirmInvoice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn In Hóa Đơn Không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                                        if (confirmInvoice == JOptionPane.YES_OPTION) {
                                            genneratePDF gen = new genneratePDF();
                                            gen.genInvoice(listGH, hd, kh, maHD);
                                        }
                                        while (itr.hasNext()) {
                                            GioHang gh = itr.next();
                                            if (gh.getMaHoaDon().equals(maHD)) {
                                                hdct.setIdHoaDon(hd1.getIdHoaDon());
                                                hdct.setIdSPCT(gh.getIdCTSP());
                                                hdct.setSoLuong(gh.getSoLuong());
                                                boolean addHDCT = hdctsv.addHDCT(hdct);
                                                if (addHDCT == true) {
                                                    itr.remove();
                                                }
                                            }
                                        }
                                        JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công!");
                                        this.showDataHoaDon();
                                        this.clearText();
                                        GioHangModel.setRowCount(0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }
    }

    //Giao Hàng
    private void giaoHangBill() throws ParseException {
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            if (trangThaiHD.equalsIgnoreCase("Đang Giao Hàng")) {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Được Giao Hàng!");
            } else {
                Double phiShip = 0.0;
                Date ngayDi = jdcNgayDiDuTinh.getDate();
                Date ngayToi = jdcNgayNhanDuTinh.getDate();
                if (txtMaKHShip.getText().trim().isEmpty() || txtTenTVShip.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Khách Hàng!");
                } else {
                    //Validate Người Nhận
                    if (txtTenNguoiNhanShip.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Chưa Có Tên Người Nhận!");
                    } else {
                        if (txtSDTNguoiNhanShip.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Chưa Có SĐT Người Nhận!");
                        } else {
                            if (!txtSDTNguoiNhanShip.getText().matches("^(0|84)?\\d{9}$")) {
                                JOptionPane.showMessageDialog(this, "SĐT Người Nhận Không Đúng Định Dạng!");
                            } else {
                                if (txtDiaChiNguoiNhanShip.getText().trim().isEmpty()) {
                                    JOptionPane.showMessageDialog(this, "Chưa Có Địa Chỉ Người Nhận!");
                                } else {
                                    if (txtPhiShip.getText().trim().isEmpty()) {
                                        JOptionPane.showMessageDialog(this, "Phí Ship Không Được Chống!");
                                    } else {
                                        try {
                                            phiShip = Double.parseDouble(txtPhiShip.getText());
                                            if (lblTongTienHangShip.getText().trim().isEmpty()) {
                                                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm!");
                                            } else {
                                                if (ngayDi == null) {
                                                    JOptionPane.showMessageDialog(this, "Chưa Chọn Ngày Đi Dự Tính!");
                                                } else {
                                                    if (ngayToi == null) {
                                                        JOptionPane.showMessageDialog(this, "Chưa Chọn Ngày Nhận Dự Tính!");
                                                    } else {
                                                        int compareDate = ngayDi.compareTo(ngayToi);
                                                        if (compareDate > 0) {
                                                            JOptionPane.showMessageDialog(this, "Ngày Nhận Không Được Nhỏ Hơn Ngày Chuyển Hàng!");
                                                        } else {
                                                            int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Giao Hàng Không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                                                            if (confirm == JOptionPane.YES_OPTION) {
                                                                //Phần Logic Giao Hàng
                                                                //Mã HĐ
                                                                String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
                                                                //Tìm Kiếm THeo
                                                                KhachHang kh = khsv.findKHByMa(txtMaKHShip.getText());
                                                                String cbbHttt = (String) cbbHinhThucThanhToanShip.getSelectedItem();
                                                                HinhThucThanhToan httt = htttsv.findHTTTByName(cbbHttt);

                                                                //COnvert
//                                                        String voucherBefore = lblGiamGia.getText();
//                                                        Number voucherCV = format.parse(voucherBefore);
//                                                        Double voucherAfter = voucherCV.doubleValue();
                                                                //đang dở
                                                                String tongTienHangBefore = lblTongTienHangShip.getText();
                                                                Number tongTienHangCV = format.parse(tongTienHangBefore);
                                                                Double tongTienHangAfter = tongTienHangCV.doubleValue();

                                                                //Tạo 1 Hóa Đơn Để update
                                                                HoaDon hd = new HoaDon();
                                                                hd.setIdKhachHang(kh.getIdKhachHang());
                                                                hd.setIdHTTT(httt.getIdHTTT());
                                                                hd.setIdTTHD(1);
                                                                hd.setGiamGiaDiemKH(0.0);
                                                                hd.setThueVAT(5);
                                                                hd.setTongTienHang(tongTienHangAfter);
                                                                if (cbbHttt.equalsIgnoreCase("Tiền Mặt")) {
                                                                    hd.setKhachTraTM(tongTienHangAfter);
                                                                    hd.setKhachTraCK(0.0);
                                                                } else if (cbbHttt.equalsIgnoreCase("Chuyển Khoản")) {
                                                                    hd.setKhachTraTM(0.0);
                                                                    hd.setKhachTraCK(tongTienHangAfter);
                                                                } else {
                                                                    hd.setKhachTraTM(tongTienHangAfter / 2);
                                                                    hd.setKhachTraCK(tongTienHangAfter / 2);
                                                                }

                                                                hd.setTienThua(0.0);
                                                                hd.setTenNguoiNhan(txtTenNguoiNhanShip.getText());
                                                                hd.setSdtNguoiNhan(txtSDTNguoiNhanShip.getText());
                                                                hd.setDiaChiNguoiNhan(txtDiaChiNguoiNhanShip.getText());
                                                                hd.setPhiShip(phiShip);
                                                                hd.setGhiChu(txtGhiChu1.getText());
                                                                hd.setNgayShipDuTinh(ngayDi);
                                                                hd.setNgayDenDuTinh(ngayToi);
                                                                hd.setIdVoucher(idVoucher);
                                                                String maGiaoDich = "not";
                                                                if (cbbHttt.equalsIgnoreCase("Tiền Mặt")) {
                                                                    maGiaoDich = "not";
                                                                } else if (cbbHttt.equalsIgnoreCase("Chuyển Khoản")) {
                                                                    maGiaoDich = JOptionPane.showInputDialog("Xin Mời Nhập Mã Giao Dịch");
                                                                } else {
                                                                    maGiaoDich = JOptionPane.showInputDialog("Xin Mời Nhập Mã Giao Dịch");
                                                                }
                                                                if (maGiaoDich.trim().isEmpty()) {
                                                                    JOptionPane.showMessageDialog(this, "Mã Giao Dịch Không Được Trống");
                                                                } else {
                                                                    hd.setMaGiaoDich(maGiaoDich);
                                                                    boolean thanhToanHD = hdsv.ThanhToanHoaDonShip(hd, maHD);
                                                                    if (thanhToanHD == true) {
                                                                        HoaDonChiTiet hdct = new HoaDonChiTiet();
                                                                        HoaDon hd1 = hdsv.getHoaDonByMa(maHD);
                                                                        this.gennerateQRcode(maHD);
                                                                        Iterator<GioHang> itr = listGH.iterator();
                                                                        if (idVoucher != null) {
                                                                            Voucher vc = vcsv.findVoucherByID(idVoucher);
                                                                            vcsv.updateSoLuongVoucher((vc.getSoLuong() - 1), idVoucher);
                                                                        }
                                                                        int confirmInvoice = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn In Hóa Đơn Không?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                                                                        if (confirmInvoice == JOptionPane.YES_OPTION) {
                                                                            genneratePDF gen = new genneratePDF();
                                                                            gen.genInvoice(listGH, hd, kh, maHD);
                                                                        }
                                                                        while (itr.hasNext()) {
                                                                            GioHang gh = itr.next();
                                                                            if (gh.getMaHoaDon().equals(maHD)) {
                                                                                hdct.setIdHoaDon(hd1.getIdHoaDon());
                                                                                hdct.setIdSPCT(gh.getIdCTSP());
                                                                                hdct.setSoLuong(gh.getSoLuong());
                                                                                boolean addHDCT = hdctsv.addHDCT(hdct);
                                                                                if (addHDCT == true) {
                                                                                    itr.remove();
                                                                                }
                                                                            }
                                                                        }
                                                                        JOptionPane.showMessageDialog(this, "Thanh Toán Thành Công!");
                                                                        this.showDataHoaDon();
                                                                        this.clearText();
                                                                        GioHangModel.setRowCount(0);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            JOptionPane.showMessageDialog(this, "Phí Ship Phải Là Số!");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }
    }
    //Hoàn Tất Bill

    private void hoanTatBill() {
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            if (trangThaiHD.equalsIgnoreCase("Đang Giao Hàng")) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Muốn Hoàn Tất Hóa Đơn Này?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean hoanTat = hdsv.hoanTatBill(maHD);
                    if (hoanTat == true) {
                        this.showDataHoaDon();
                        this.clearTextGiaoHang();
                        GioHangModel.setRowCount(0);
                        JOptionPane.showMessageDialog(this, "Hoàn Tất Đơn Hàng Thành Công!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Chưa Thể Hoàn Tất!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }
    }

    //Xóa Hóa Đon
    private void removeHoaDon() {
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            if (trangThaiHD.equalsIgnoreCase("Chờ Thanh Toán")) {
                //thực hiện xóa
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Xóa Hóa Đơn Này", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean removeHD = hdsv.deleteHoaDonByMa(maHD);
                    if (removeHD == true) {
                        this.showDataHoaDon();
                        JOptionPane.showMessageDialog(this, "Xóa Hóa Đơn Thành Công!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Đang Được Vận Chuyển Không Thể Xóa!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }
    }

    //Hủy Hóa Đơn
    private void huyDonHang() {
        int selectedHD = DanhSachHoaDonTable.getSelectedRow();
        if (selectedHD >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 3);
            if (trangThaiHD.equalsIgnoreCase("Chờ Thanh Toán")) {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Này Chưa Thể Hủy!");
            } else {
                List<Object[]> listSPHoan = hdsv.hoanHang(maHD);
                boolean huyDon = hdsv.huyBill(maHD);
                if (huyDon == true) {
                    for (Object[] row : listSPHoan) {
                        ChiTietSanPham ctspCurrent = ctspsv.getCTSPByID(Integer.parseInt(String.valueOf(row[0])));
                        int soLuongHoan = Integer.parseInt(String.valueOf(row[1])) + ctspCurrent.getSoLuongTon();
                        ctspsv.updateHoanHang(soLuongHoan, ctspCurrent.getIdCTSP());
                    }
                    this.showDataHoaDon();
                    this.showDataDSSP(ctspr.getAll());
                    this.clearTextGiaoHang();
                    GioHangModel.setRowCount(0);
                    JOptionPane.showMessageDialog(this, "Hoàn Hủy Đơn Hàng Thành Công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
        }
    }

    //Clear Text
    private void clearText() {
        if (tabChiTiet.getSelectedIndex() == 0) {
            txtMaKH.setText("KH1");
            txtTenKH.setText("Khách Lẻ");
            txtSDT.setText("not");
            lblGiamGia.setText(format.format(0));
            cbbHinhThucThanhToan.setSelectedIndex(0);
            lblTongTienHang.setText("");
            txtKhachTra.setText(0 + "");
            txtKhachTraCK.setText(0 + "");
            lblTienThua.setText("");
            txtGhiChu.setText("");
        } else {
            clearTextGiaoHang();
        }

    }

    //clear Text Giao Hàng
    private void clearTextGiaoHang() {
        txtMaKHShip.setText("KH1");
        txtTenTVShip.setText("Khách Lẻ");
        txtTenNguoiNhanShip.setText("");
        txtSDTNguoiNhanShip.setText("");
        txtDiaChiNguoiNhanShip.setText("");
        txtPhiShip.setText("");
        lblTongTienHangShip.setText("");
        cbbHinhThucThanhToanShip.setSelectedIndex(0);
        jdcNgayDiDuTinh.setDate(null);
        jdcNgayNhanDuTinh.setDate(null);
        txtGhiChu1.setText("");
    }

    //Tiền Khách Trả
    private void tienKhachTra() throws ParseException {
        //convert tổng tiền hàng sang double
        String tongTienHangBefore = lblTongTienHang.getText();
        Number tongTienHangCV = format.parse(tongTienHangBefore);
        Double tongTienHangAfter = tongTienHangCV.doubleValue();

        //
        String khachTM = txtKhachTra.getText();
        String khachCK = txtKhachTraCK.getText();
        //tiền khách trả

        if (khachCK.trim().isEmpty()) {
            Double khachTraTM = Double.parseDouble(khachTM);
            Double tienThua = (khachTraTM + 0) - tongTienHangAfter;
            lblTienThua.setText(format.format(tienThua));
        } else {
            try {
                Double khachTraTM = Double.parseDouble(khachTM);
                Double khachTraCK = Double.parseDouble(khachCK);
                Double tienThua = (khachTraTM + khachTraCK) - tongTienHangAfter;
                lblTienThua.setText(format.format(tienThua));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi Dữ Liệu");
            }
        }
    }

    private void tienKhachTraCK() throws ParseException {
        //convert tổng tiền hàng sang double
        String tongTienHangBefore = lblTongTienHang.getText();
        Number tongTienHangCV = format.parse(tongTienHangBefore);
        Double tongTienHangAfter = tongTienHangCV.doubleValue();

        //
        String khachTM = txtKhachTra.getText();
        String khachCK = txtKhachTraCK.getText();

        if (khachTM.trim().isEmpty()) {
            Double khachTraCK = Double.parseDouble(khachCK);
            Double tienThua = (0 + khachTraCK) - tongTienHangAfter;
            lblTienThua.setText(format.format(tienThua));
        } else {
            try {
                Double khachTraTM = Double.parseDouble(khachTM);
                Double khachTraCK = Double.parseDouble(khachCK);
                Double tienThua = (khachTraTM + khachTraCK) - tongTienHangAfter;
                lblTienThua.setText(format.format(tienThua));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi Dữ Liệu!");
            }

        }

    }

    //Bấm Vào Bảng Hóa Đon Thì Xuất Ra Sản Phẩm Của Hóa Đơn Đấy
    private void detailHoaDonSP(List<Object[]> listSP) {
        GioHangModel.setRowCount(0);
        int i = 0;
        for (Object[] row : listSP) {
            i++;
            GioHangModel.addRow(new Object[]{i, row[0], row[1], row[3], format.format(row[4])});
        }
    }

    //Bám Vào Bảng Hóa Đơn Thì Xuất Ra detail Infor của Hóa Đơn Đấy
    private void detailHoaDonInfor(Object[] list) throws ParseException {
        KhachHang kh = khsv.findKHByTen(list[2] + "");
        txtMaKHShip.setText(kh.getMaKhachHang());
        txtTenTVShip.setText(list[2] + "");
        txtTenNguoiNhanShip.setText(list[5] + "");
        txtSDTNguoiNhanShip.setText(list[6] + "");
        txtDiaChiNguoiNhanShip.setText(list[7] + "");
        txtPhiShip.setText(list[18] + "");
        lblTongTienHangShip.setText(format.format(list[12]));
        if (String.valueOf(list[10]).equalsIgnoreCase("Tiền Mặt")) {
            cbbHinhThucThanhToanShip.setSelectedIndex(0);
        } else if (String.valueOf(list[10]).equalsIgnoreCase("Chuyển Khoản")) {
            cbbHinhThucThanhToanShip.setSelectedIndex(1);
        } else {
            cbbHinhThucThanhToanShip.setSelectedIndex(2);
        }
        txtGhiChu1.setText(list[9] + "");
        Date dateDi = sdf.parse(list[16] + "");
        jdcNgayDiDuTinh.setDate(dateDi);
        Date dateToi = sdf.parse(list[17] + "");
        jdcNgayNhanDuTinh.setDate(dateToi);

    }

    //Tự Động GenerateMa
    private String gennerateMa() {
        HoaDon hd = hdsv.selectTop1DESC();
        String maHDSub = hd.getMaHoaDon().substring(2);
        Integer soMaHD = Integer.parseInt(maHDSub);
        String maHDFinal = "HĐ" + (soMaHD + 1);
        return maHDFinal;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DanhSachHoaDonTable = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnXoaHoaDon = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        GioHangTable = new javax.swing.JTable();
        btnClearAllSanPham = new javax.swing.JButton();
        btnXoaSanPham = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbbTimLoaiSP = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        DanhSachSanPhamTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        WebCamPanel = new javax.swing.JPanel();
        tabChiTiet = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnTimThanhVien4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        vnd = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblThue = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cbbHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtKhachTra = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtKhachTraCK = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblTongTienHang = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        lblMaNV1 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblNgayTao1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtMaKHShip = new javax.swing.JTextField();
        btnTimThanhVien3 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        txtTenTVShip = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtTenNguoiNhanShip = new javax.swing.JTextField();
        txtSDTNguoiNhanShip = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txtDiaChiNguoiNhanShip = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        vnd1 = new javax.swing.JLabel();
        lblGiamGiaShip = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        lblThue1 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        cbbHinhThucThanhToanShip = new javax.swing.JComboBox<>();
        btnThanhToan1 = new javax.swing.JButton();
        btnHuyDonHang = new javax.swing.JButton();
        btnHoanTatBill = new javax.swing.JButton();
        lblTongTienHangShip = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtPhiShip = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jdcNgayDiDuTinh = new com.toedter.calendar.JDateChooser();
        jdcNgayNhanDuTinh = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtGhiChu1 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(100, 180, 244));
        jLabel1.setText("Danh Sách Hóa Đơn");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        DanhSachHoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Tên NV", "Trạng Thái", "Ngày Tạo"
            }
        ));
        DanhSachHoaDonTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DanhSachHoaDonTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(DanhSachHoaDonTable);
        if (DanhSachHoaDonTable.getColumnModel().getColumnCount() > 0) {
            DanhSachHoaDonTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        btnTaoHoaDon.setBackground(new java.awt.Color(100, 180, 244));
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus.png"))); // NOI18N
        btnTaoHoaDon.setText("Tạo Hóa Đơn");
        btnTaoHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaoHoaDonMouseClicked(evt);
            }
        });

        btnXoaHoaDon.setBackground(new java.awt.Color(100, 180, 244));
        btnXoaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-cart-24.png"))); // NOI18N
        btnXoaHoaDon.setText("Xóa Hóa Đơn");
        btnXoaHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaHoaDonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaHoaDon)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 33, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(100, 180, 244));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/qr-code.png"))); // NOI18N
        jLabel5.setText("Quét BarCode");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(100, 180, 244));
        jLabel6.setText("Giỏ Hàng");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 228, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        GioHangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số Lượng", "Thành Tiền"
            }
        ));
        GioHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GioHangTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(GioHangTable);

        btnClearAllSanPham.setBackground(new java.awt.Color(100, 180, 244));
        btnClearAllSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClearAllSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnClearAllSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-cart-24.png"))); // NOI18N
        btnClearAllSanPham.setText("Làm Mới Giỏ Hàng");
        btnClearAllSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearAllSanPhamMouseClicked(evt);
            }
        });

        btnXoaSanPham.setBackground(new java.awt.Color(100, 180, 244));
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-remove-from-cart-24.png"))); // NOI18N
        btnXoaSanPham.setText("Xóa Khỏi Giỏ Hàng");
        btnXoaSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaSanPhamMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnXoaSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClearAllSanPham))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearAllSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 254, 786, 230));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(100, 180, 244));
        jLabel8.setText("Tìm Sản Phẩm:");

        txtFind.setForeground(new java.awt.Color(153, 153, 153));
        txtFind.setText("Tìm Kiếm Theo Tất Cả");
        txtFind.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFindFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFindFocusLost(evt);
            }
        });
        txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindKeyReleased(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(100, 180, 244));
        jLabel9.setText("Lọc Theo Loại:");

        cbbTimLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTimLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTimLoaiSPActionPerformed(evt);
            }
        });

        DanhSachSanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Chọn", "Mã SP", "Tên SP", "Loại SP", "Thương Hiệu", "Xuất Xứ", "Chất Liệu", "Đơn Vị Tính", "Khối Lượng", "Đơn Giá", "Số Lượng Tồn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        DanhSachSanPhamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DanhSachSanPhamTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(DanhSachSanPhamTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(cbbTimLoaiSP, 0, 250, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cbbTimLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 522, 780, 240));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Danh Sách Sản Phẩm");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 496, -1, -1));

        WebCamPanel.setBackground(new java.awt.Color(255, 255, 255));
        WebCamPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        WebCamPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(WebCamPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(541, 33, 252, 183));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Mã Nhân Viên");

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblMaNV.setForeground(new java.awt.Color(100, 180, 244));

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Ngày Tạo");

        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblNgayTao.setForeground(new java.awt.Color(100, 180, 244));

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Mã Thành Viên");

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Tên Thành Viên");

        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("SĐT");

        btnTimThanhVien4.setBackground(new java.awt.Color(100, 180, 244));
        btnTimThanhVien4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimThanhVien4.setForeground(new java.awt.Color(255, 255, 255));
        btnTimThanhVien4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-magnifying-glass-24.png"))); // NOI18N
        btnTimThanhVien4.setText("Tìm");
        btnTimThanhVien4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimThanhVien4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(txtTenKH)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimThanhVien4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimThanhVien4))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(100, 180, 244));
        jLabel2.setText("Thông Tin Chung");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(100, 180, 244));
        jLabel22.setText("Chi Tiết Hóa Đơn");

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Giảm Voucher");

        vnd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        vnd.setForeground(new java.awt.Color(5, 56, 202));
        vnd.setText("VNĐ");

        lblGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblGiamGia.setForeground(new java.awt.Color(5, 56, 202));

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("Thuế VAT");

        lblThue.setForeground(new java.awt.Color(255, 0, 0));
        lblThue.setText("5%");

        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Hình Thức Thanh Toán");

        cbbHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHinhThucThanhToanActionPerformed(evt);
            }
        });

        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Tổng Tiền Hàng");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 0, 0));
        jLabel34.setText("VNĐ");

        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Khách Trả Tiền Mặt");

        txtKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachTraKeyReleased(evt);
            }
        });

        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Khách Trả CK");

        txtKhachTraCK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachTraCKKeyReleased(evt);
            }
        });

        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Tiền Trả Lại");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(193, 86, 196));
        jLabel38.setText("VNĐ");

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTienThua.setForeground(new java.awt.Color(193, 86, 196));

        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Ghi Chú Hóa Đơn");

        btnThanhToan.setBackground(new java.awt.Color(100, 180, 244));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-money-bag-30.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));

        lblTongTienHang.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTongTienHang.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblThue)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(vnd)
                            .addGap(18, 18, 18)
                            .addComponent(lblGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel34)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(lblTongTienHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(12, 12, 12)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtKhachTraCK, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(14, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(vnd))
                    .addComponent(lblGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblThue))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cbbHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongTienHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(jLabel33)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtKhachTraCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(lblTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel22))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabChiTiet.addTab("Hóa Đơn", jPanel9);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Mã Nhân Viên");

        lblMaNV1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaNV1.setForeground(new java.awt.Color(100, 180, 244));

        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Ngày Tạo");

        lblNgayTao1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNgayTao1.setForeground(new java.awt.Color(100, 180, 244));

        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Mã Thành Viên");

        btnTimThanhVien3.setBackground(new java.awt.Color(100, 180, 244));
        btnTimThanhVien3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimThanhVien3.setForeground(new java.awt.Color(255, 255, 255));
        btnTimThanhVien3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-magnifying-glass-24.png"))); // NOI18N
        btnTimThanhVien3.setText("Tìm");
        btnTimThanhVien3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimThanhVien3MouseClicked(evt);
            }
        });

        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Tên Thành Viên");

        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Tên Người Nhận");

        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("SĐT Người Nhận");

        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Địa Chỉ");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(21, 21, 21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(txtDiaChiNguoiNhanShip, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNgayTao1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenNguoiNhanShip, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                            .addComponent(txtTenTVShip, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaNV1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaKHShip)
                            .addComponent(txtSDTNguoiNhanShip))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimThanhVien3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblMaNV1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNgayTao1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(12, 12, 12)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtMaKHShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimThanhVien3))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtTenTVShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtTenNguoiNhanShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtSDTNguoiNhanShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtDiaChiNguoiNhanShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(100, 180, 244));
        jLabel47.setText("Thông Tin Chung");

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Giảm Giá Voucher");

        vnd1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        vnd1.setForeground(new java.awt.Color(5, 56, 202));
        vnd1.setText("VNĐ");

        lblGiamGiaShip.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblGiamGiaShip.setForeground(new java.awt.Color(5, 56, 202));

        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Thuế VAT");

        lblThue1.setForeground(new java.awt.Color(255, 0, 0));
        lblThue1.setText("5%");

        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Tổng Tiền Hàng");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 0, 0));
        jLabel57.setText("VNĐ");

        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Hình Thức Thanh Toán");

        cbbHinhThucThanhToanShip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThanhToan1.setBackground(new java.awt.Color(100, 180, 244));
        btnThanhToan1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan1.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delivery-truck.png"))); // NOI18N
        btnThanhToan1.setText("Giao Hàng");
        btnThanhToan1.setPreferredSize(new java.awt.Dimension(90, 25));
        btnThanhToan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToan1MouseClicked(evt);
            }
        });

        btnHuyDonHang.setBackground(new java.awt.Color(100, 180, 244));
        btnHuyDonHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyDonHang.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyDonHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cancel.png"))); // NOI18N
        btnHuyDonHang.setText("Hủy Đơn");
        btnHuyDonHang.setPreferredSize(new java.awt.Dimension(90, 25));
        btnHuyDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyDonHangMouseClicked(evt);
            }
        });

        btnHoanTatBill.setBackground(new java.awt.Color(100, 180, 244));
        btnHoanTatBill.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHoanTatBill.setForeground(new java.awt.Color(255, 255, 255));
        btnHoanTatBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/completed-task.png"))); // NOI18N
        btnHoanTatBill.setText("Hoàn Tất");
        btnHoanTatBill.setPreferredSize(new java.awt.Dimension(90, 25));
        btnHoanTatBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHoanTatBillMouseClicked(evt);
            }
        });

        lblTongTienHangShip.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTongTienHangShip.setForeground(new java.awt.Color(255, 0, 0));

        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Phí Ship");

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ngày vận chuyển dự tính");

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Ngày nhận dự tính");

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Ghi Chú");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vnd1)
                                    .addComponent(jLabel57))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGiamGiaShip, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTongTienHangShip, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblThue1)
                            .addComponent(txtPhiShip)
                            .addComponent(cbbHinhThucThanhToanShip, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdcNgayDiDuTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(jdcNgayNhanDuTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGhiChu1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThanhToan1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHoanTatBill, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(vnd1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel54))
                    .addComponent(lblGiamGiaShip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThue1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTongTienHangShip, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(cbbHinhThucThanhToanShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayDiDuTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayNhanDuTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtGhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoanTatBill, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102))
        );

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(100, 180, 244));
        jLabel51.setText("Chi Tiết Hóa Đơn");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabChiTiet.addTab("Đặt Hàng", jPanel8);

        jPanel2.add(tabChiTiet, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 460, 780));

        tab.addTab("Bán Hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, 1299, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHinhThucThanhToanActionPerformed
        // TODO add your handling code here:
        this.chooseHTTT();
    }//GEN-LAST:event_cbbHinhThucThanhToanActionPerformed

    private void cbbTimLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTimLoaiSPActionPerformed
        // TODO add your handling code here:
        this.findSPCTByLoai();
    }//GEN-LAST:event_cbbTimLoaiSPActionPerformed

    private void DanhSachSanPhamTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DanhSachSanPhamTableMouseClicked
        // TODO add your handling code here:
        this.pushSPToCart();
    }//GEN-LAST:event_DanhSachSanPhamTableMouseClicked

    private void txtFindFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFindFocusGained
        // TODO add your handling code here:
        if (txtFind.getText().equalsIgnoreCase("Tìm Kiếm Theo Tất Cả")) {
            txtFind.setText("");
        }
    }//GEN-LAST:event_txtFindFocusGained

    private void txtFindFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFindFocusLost
        // TODO add your handling code here:
        if (txtFind.getText().equalsIgnoreCase("")) {
            txtFind.setText("Tìm Kiếm Theo Tất Cả");
        }
    }//GEN-LAST:event_txtFindFocusLost

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
        // TODO add your handling code here:
        this.findSPCT();
    }//GEN-LAST:event_txtFindKeyReleased

    private void btnXoaSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaSanPhamMouseClicked
        // TODO add your handling code here:
        this.XoaSanPhamKhoiGioHang();
    }//GEN-LAST:event_btnXoaSanPhamMouseClicked

    private void btnClearAllSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearAllSanPhamMouseClicked
        // TODO add your handling code here:
        this.XoaAllSanPhamGioHang();
    }//GEN-LAST:event_btnClearAllSanPhamMouseClicked

    private void btnTaoHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoHoaDonMouseClicked
        // TODO add your handling code here:
        this.createHoaDon();
    }//GEN-LAST:event_btnTaoHoaDonMouseClicked

    private void btnTimThanhVien4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimThanhVien4MouseClicked
        // TODO add your handling code here:
        this.openViewKhachHang();
    }//GEN-LAST:event_btnTimThanhVien4MouseClicked

    private void DanhSachHoaDonTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DanhSachHoaDonTableMouseClicked
        // TODO add your handling code here:
        int selected = DanhSachHoaDonTable.getSelectedRow();
        if (selected >= 0) {
            String maHD = (String) DanhSachHoaDonTable.getValueAt(selected, 1);
            String trangThaiHD = (String) DanhSachHoaDonTable.getValueAt(selected, 3);
            if (trangThaiHD.equalsIgnoreCase("Đang Giao Hàng")) {
                Object[] list = hdsv.getChiTietLSHoaDon(maHD);
                List<Object[]> listSP = hdsv.getSanPhamLichSuHoaDon(maHD);
                this.detailHoaDonSP(listSP);
                try {
                    this.detailHoaDonInfor(list);
                } catch (ParseException ex) {
                    Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                showDataGioHangByHD(listGH, maHD);
                double tongTienHang = 0.0;
                for (GioHang gh : listGH) {
                    if (gh.getMaHoaDon().equalsIgnoreCase(maHD) && tabChiTiet.getSelectedIndex() == 0) {
                        tongTienHang += gh.getThanhTien();
                        lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                    } else {
                        tongTienHang += gh.getThanhTien();
                        lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                    }
                }
                txtTenNguoiNhanShip.setText("");
                txtSDTNguoiNhanShip.setText("");
                txtDiaChiNguoiNhanShip.setText("");
                txtPhiShip.setText("");
                cbbHinhThucThanhToanShip.setSelectedIndex(0);
                jdcNgayDiDuTinh.setDate(null);
                jdcNgayNhanDuTinh.setDate(null);
                txtGhiChu1.setText("");
            }
        }
    }//GEN-LAST:event_DanhSachHoaDonTableMouseClicked

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        try {
            try {
                // TODO add your handling code here:
                this.thanhToanBill();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParseException ex) {
            Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThanhToanMouseClicked

    private void txtKhachTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachTraKeyReleased
        try {
            // TODO add your handling code here:
            this.tienKhachTra();
        } catch (ParseException ex) {
            Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtKhachTraKeyReleased

    private void txtKhachTraCKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachTraCKKeyReleased
        try {
            // TODO add your handling code here:
            this.tienKhachTraCK();
        } catch (ParseException ex) {
            Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtKhachTraCKKeyReleased

    private void btnTimThanhVien3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimThanhVien3MouseClicked
        // TODO add your handling code here:
        this.openViewKhachHang();
    }//GEN-LAST:event_btnTimThanhVien3MouseClicked

    private void btnThanhToan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToan1MouseClicked
        try {
            // TODO add your handling code here:
            this.giaoHangBill();
        } catch (ParseException ex) {
            Logger.getLogger(BanHangJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThanhToan1MouseClicked

    private void btnHoanTatBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHoanTatBillMouseClicked
        // TODO add your handling code here:
        this.hoanTatBill();
    }//GEN-LAST:event_btnHoanTatBillMouseClicked

    private void btnXoaHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaHoaDonMouseClicked
        // TODO add your handling code here:
        this.removeHoaDon();
    }//GEN-LAST:event_btnXoaHoaDonMouseClicked

    private void GioHangTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GioHangTableMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int selectedHD = DanhSachHoaDonTable.getSelectedRow();
            if (selectedHD >= 0) {
                String maHD = (String) DanhSachHoaDonTable.getValueAt(selectedHD, 1);
                int selected = GioHangTable.getSelectedRow();
                if (selected >= 0) {
                    String nhapSoLuong = JOptionPane.showInputDialog("Mời Nhập Số Lượng Cần Xóa");
                    if (nhapSoLuong != null) {
                        try {
                            Integer soLuong = Integer.parseInt(nhapSoLuong);
                            GioHang gh = listGH.get(selected);
                            if (soLuong <= 0) {
                                JOptionPane.showMessageDialog(this, "Số Lượng Phải Lớn Hơn 0!");
                            } else if (soLuong > gh.getSoLuong()) {
                                JOptionPane.showMessageDialog(this, "Bạn Nhập Quá Số Lượng Trong Giỏ Hàng!");
                            } else if ((soLuong - gh.getSoLuong() == 0)) {
                                ChiTietSanPham ctsp = ctspsv.getCTSPByID(gh.getIdCTSP());
                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(ctsp.getSoLuongTon() + soLuong, ctsp.getIdCTSP());
                                if (updateSoLuong == true) {
                                    listGH.remove(gh);
                                    //lấy lại tổng tiền giỏ hàng
                                    double tongTienHang = 0.0;
                                    for (GioHang gh1 : listGH) {
                                        if (gh1.getMaHoaDon().equalsIgnoreCase(maHD)) {
                                            tongTienHang += gh1.getThanhTien();
                                        }
                                    }
                                    if (tabChiTiet.getSelectedIndex() == 0) {
                                        lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                    } else {
                                        lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                    }
                                    this.showDataGioHangByHD(listGH, maHD);
                                    this.showDataDSSP(ctspr.getAll());
                                    JOptionPane.showMessageDialog(this, "Xóa Sản Phẩm Thành Công!");
                                }
                            } else {
                                ChiTietSanPham ctsp = ctspsv.getCTSPByID(gh.getIdCTSP());
                                //lấy lại tổng tiền giỏ hàng
                                gh.setSoLuong(gh.getSoLuong() - soLuong);
                                double tongTien = ctsp.getDonGia() * gh.getSoLuong();
                                gh.setThanhTien(tongTien);
                                double tongTienHang = 0.0;
                                for (GioHang gh1 : listGH) {
                                    if (gh1.getMaHoaDon().equalsIgnoreCase(maHD)) {
                                        tongTienHang += gh1.getThanhTien();
                                    }
                                }
                                if (tabChiTiet.getSelectedIndex() == 0) {
                                    lblTongTienHang.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                } else {
                                    lblTongTienHangShip.setText(format.format(tongTienHang + ((tongTienHang / 100) * 5)));
                                }
                                boolean updateSoLuong = ctspsv.updateSoLuongCTSP(ctsp.getSoLuongTon() + soLuong, ctsp.getIdCTSP());
                                if (updateSoLuong == true) {
                                    this.showDataGioHangByHD(listGH, maHD);
                                    this.showDataDSSP(ctspr.getAll());
                                    JOptionPane.showMessageDialog(this, "Cập Nhật Số Lượng Thành Công!");
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Sai Dữ Liệu!");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn!");
            }
        }
    }//GEN-LAST:event_GioHangTableMousePressed

    private void btnHuyDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyDonHangMouseClicked
        // TODO add your handling code here:
        this.huyDonHang();
    }//GEN-LAST:event_btnHuyDonHangMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DanhSachHoaDonTable;
    private javax.swing.JTable DanhSachSanPhamTable;
    private javax.swing.JTable GioHangTable;
    private javax.swing.JPanel WebCamPanel;
    private javax.swing.JButton btnClearAllSanPham;
    private javax.swing.JButton btnHoanTatBill;
    private javax.swing.JButton btnHuyDonHang;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThanhToan1;
    private javax.swing.JButton btnTimThanhVien3;
    private javax.swing.JButton btnTimThanhVien4;
    private javax.swing.JButton btnXoaHoaDon;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JComboBox<String> cbbHinhThucThanhToan;
    private javax.swing.JComboBox<String> cbbHinhThucThanhToanShip;
    private javax.swing.JComboBox<String> cbbTimLoaiSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser jdcNgayDiDuTinh;
    private com.toedter.calendar.JDateChooser jdcNgayNhanDuTinh;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblGiamGiaShip;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaNV1;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNgayTao1;
    private javax.swing.JLabel lblThue;
    private javax.swing.JLabel lblThue1;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTienHang;
    private javax.swing.JLabel lblTongTienHangShip;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTabbedPane tabChiTiet;
    private javax.swing.JTextField txtDiaChiNguoiNhanShip;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGhiChu1;
    private javax.swing.JTextField txtKhachTra;
    private javax.swing.JTextField txtKhachTraCK;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKHShip;
    private javax.swing.JTextField txtPhiShip;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSDTNguoiNhanShip;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNguoiNhanShip;
    private javax.swing.JTextField txtTenTVShip;
    private javax.swing.JLabel vnd;
    private javax.swing.JLabel vnd1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void chonKH(KhachHang kh) {
        String maKH = kh.getMaKhachHang();
        String maKHSub = maKH.substring(0, kh.getMaKhachHang().indexOf(" "));
        if (tabChiTiet.getSelectedIndex() == 1) {
            txtMaKHShip.setText(maKHSub);
            txtTenTVShip.setText(kh.getTenKhachHang());
        } else {
            txtMaKH.setText(maKHSub);
            txtTenKH.setText(kh.getTenKhachHang());
            String sdt = kh.getSdt();
            String sdtSub = sdt.substring(0, sdt.indexOf(" "));
            txtSDT.setText(sdtSub);
        }
    }
}
