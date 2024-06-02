/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.Contain.EntitySanPham;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.sun.tools.javac.Main;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
import utils.MsgBox;
import utils.XImage;
import view.Contains.SanPhamJPanel;

/**
 *
 * @author ACER
 */
public class ThongTinSanPham extends javax.swing.JDialog {

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
    modelChiTietSanPham ctsp = new modelChiTietSanPham();
    DefaultTableModel model1;
    public String barcode;

    private String ghiChu;
    SanPhamJPanel spj;
    public ThongTinSanPham(JPanel parent,String tenSp, String loaiSp, String thuongHieu,
            String xuatXu, String chatLieu, String donViTinh, String khoiLuong, double donGia, String barcode, int soLuongTon, String ghiChu, String trangThai, boolean par,int whatKind) {
        initComponents();
        spj=(SanPhamJPanel) parent;
        this.ghiChu = ghiChu;
        setLocationRelativeTo(null);
        fillComboBoxSanPham();
        fillComboBoxChatLieu();
        fillComboBoxXuatXu();
        fillComnoBoxLoaiSanPham();
        fillComboBoxDonViTinh();
        fillComboBoxThuongHieu();
        fillComboBoxKhoiLuong();
        txtMoTa.setText(ghiChu);
        cbbTensp.setSelectedItem(tenSp);
        cbblsp.setSelectedItem(loaiSp);
        cbbth.setSelectedItem(thuongHieu);
        cbbxx.setSelectedItem(xuatXu);
        cbbcl.setSelectedItem(chatLieu);
        cbbdvt.setSelectedItem(donViTinh);
        cbbkl.setSelectedItem(khoiLuong);
        txtDonGia.setText(String.valueOf(donGia));
        txtSoLuongTon.setText(String.valueOf(soLuongTon));
        if (trangThai.equalsIgnoreCase("Đang bán")) {
            rbDangBan.setSelected(true);
        } else {
            rbNgungBan.setSelected(true);
        }
        String barcodeSub="";
        if(whatKind==1){
            barcodeSub = barcode.substring(0, barcode.indexOf(" "));
            File file = new File("src\\main\\resources\\barcode\\" + barcodeSub + ".png");
            jlbBarcode.setIcon(new ImageIcon(file.getAbsolutePath()));
        }else{
            barcodeSub=barcode;
            File file = new File("src\\main\\resources\\barcode\\" + barcodeSub + ".png");
            jlbBarcode.setIcon(new ImageIcon(file.getAbsolutePath()));
        }
        this.barcode = barcodeSub;
    }

    private String readBarcode(String barcode) {
        try {
            String Path = "src\\main\\resources\\barcode\\" + barcode + ".png";
            BufferedImage bf = ImageIO.read(new FileInputStream(Path));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    void fillComboBoxKhoiLuong() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbbkl.getModel();
        model1.removeAllElements();
        List<KhoiLuong> listKl = serviceKhoiLuong.getAll();
        for (KhoiLuong kl : listKl) {
            model1.addElement(kl.getTenKhoiLuong());
        }
    }

    void fillComboBoxSanPham() {
        DefaultComboBoxModel model1 = (DefaultComboBoxModel) cbbTensp.getModel();
        model1.removeAllElements();
        List<model.SanPham> listSp = serviceSP.getAll();
        for (model.SanPham sp : listSp) {
            model1.addElement(sp.getTenSanPham());
        }

    }

    void fillComboBoxChatLieu() {
        DefaultComboBoxModel modelChatLieu = (DefaultComboBoxModel) cbbcl.getModel();
        modelChatLieu.removeAllElements();
        List<ChatLieu> listChatLieu = serviceChatLieu.getAll();
        for (ChatLieu chatlieu : listChatLieu) {
            modelChatLieu.addElement(chatlieu.getTenChatLieu());
        }
    }

    void fillComboBoxXuatXu() {
        DefaultComboBoxModel modelXuaXu = (DefaultComboBoxModel) cbbxx.getModel();
        modelXuaXu.removeAllElements();
        List<XuatXu> listXuatXu = serviceXuatXu.getAll();
        for (XuatXu xuatxu : listXuatXu) {
            modelXuaXu.addElement(xuatxu.getTenXuatXu());
        }
    }

    void fillComnoBoxLoaiSanPham() {
        DefaultComboBoxModel modelLoaiSanPham = (DefaultComboBoxModel) cbblsp.getModel();
        modelLoaiSanPham.removeAllElements();
        List<LoaiSanPham> listLoaiSanPham = serviceLoaiSanPham.getAll();
        for (LoaiSanPham lsp : listLoaiSanPham) {
            modelLoaiSanPham.addElement(lsp.getTenLoaiSanPham());
        }
    }

    void fillComboBoxDonViTinh() {
        DefaultComboBoxModel modelDonViTinh = (DefaultComboBoxModel) cbbdvt.getModel();
        modelDonViTinh.removeAllElements();
        List<DonViTinh> listdvt = serviceDonViTinh.getAll();
        for (DonViTinh dvt : listdvt) {
            modelDonViTinh.addElement(dvt.getTenDonViTinh());
        }
    }

    void fillComboBoxThuongHieu() {
        DefaultComboBoxModel modelThuongHieu = (DefaultComboBoxModel) cbbth.getModel();
        modelThuongHieu.removeAllElements();
        List<ThuongHieu> listThuongHieu = serviceThuongHieu.getAll();
        for (ThuongHieu th : listThuongHieu) {
            modelThuongHieu.addElement(th.getTenThuongHieu());
        }
    }

    void fillbyma() {
        modelChiTietSanPham mo = new modelChiTietSanPham();
        String ma = mo.getMaSp();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbbTensp = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbblsp = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbbxx = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbbdvt = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbbkl = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbbth = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbbcl = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMoTa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtSoLuongTon = new javax.swing.JTextField();
        rbDangBan = new javax.swing.JRadioButton();
        rbNgungBan = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jlbBarcode = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Thông tin sản phẩm");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên sản phẩm");

        cbbTensp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên loại sản phẩm");

        cbblsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Xuất xứ");

        cbbxx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Đơn vị tính");

        cbbdvt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Khối lượng");

        cbbkl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Thương hiệu");

        cbbth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Chất liệu");

        cbbcl.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Số lượng tồn ");

        jLabel10.setText("Giá bán ");

        jLabel11.setText("Mô tả");

        jButton1.setText("Huỷ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Chỉnh sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbDangBan);
        rbDangBan.setText("Đang bán");

        buttonGroup1.add(rbNgungBan);
        rbNgungBan.setSelected(true);
        rbNgungBan.setText("Ngừng bán");

        jLabel12.setText("Barcode");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(232, 232, 232))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jButton2)))
                .addGap(21, 21, 21))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbblsp, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbxx, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbdvt, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbkl, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rbDangBan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbNgungBan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbbth, javax.swing.GroupLayout.Alignment.LEADING, 0, 193, Short.MAX_VALUE)
                        .addComponent(cbbcl, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSoLuongTon, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtMoTa, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbbTensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbbth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbblsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(cbbcl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbxx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9)
                    .addComponent(txtSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbdvt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbkl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel11)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbDangBan)
                            .addComponent(rbNgungBan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addContainerGap(61, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn huỷ không ");
        if (confirm == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this, "Ban muon sua khong ?");
        if (confirm == 0) {
            String maQR = readBarcode(barcode);
            ChiTietSanPham ctsp = this.checkSanPham();
            if (serviceModelChitietSanPham.update(ctsp, maQR) > 0) {
                MsgBox.alert(this, "Sửa thành công");
                spj.fill();
                this.dispose();
            } else {
                MsgBox.alert(this, "Sửa thất bại");
            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongTinSanPham.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTinSanPham.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTinSanPham.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTinSanPham.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String tenSp = "";
                String loaiSp = "";
                String thuongHieu = "";
                String xuatXu = "";
                String chatLieu = "";
                String donViTinh = "";
                String khoiLuong = "";
                double donGia = 0;
                int soLuongTon = 0;
                String ghiChu = "";
                String trangThai = "";
                String barcode = "";
                int whatkind=0;
                ThongTinSanPham dialog = new ThongTinSanPham(new javax.swing.JPanel(),tenSp, loaiSp, thuongHieu,
                        xuatXu, chatLieu, donViTinh, khoiLuong, donGia, barcode, soLuongTon, ghiChu, trangThai, true,whatkind);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbTensp;
    private javax.swing.JComboBox<String> cbbcl;
    private javax.swing.JComboBox<String> cbbdvt;
    private javax.swing.JComboBox<String> cbbkl;
    private javax.swing.JComboBox<String> cbblsp;
    private javax.swing.JComboBox<String> cbbth;
    private javax.swing.JComboBox<String> cbbxx;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel jlbBarcode;
    private javax.swing.JRadioButton rbDangBan;
    private javax.swing.JRadioButton rbNgungBan;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSoLuongTon;
    // End of variables declaration//GEN-END:variables

    private ChiTietSanPham checkSanPham() {
        SanPham sp = serviceSP.getByNameSP((String) cbbTensp.getSelectedItem());
        LoaiSanPham lsp = serviceLoaiSanPham.getByName((String) cbblsp.getSelectedItem());
        ThuongHieu th = serviceThuongHieu.getByName((String) cbbth.getSelectedItem());
        KhoiLuong kl = serviceKhoiLuong.getByName((String) cbbkl.getSelectedItem());
        DonViTinh dvt = serviceDonViTinh.getByName((String) cbbdvt.getSelectedItem());
        XuatXu xx = serviceXuatXu.getByName((String) cbbxx.getSelectedItem());
        ChatLieu cl = serviceChatLieu.getByName((String) cbbcl.getSelectedItem());

        int soLuongAfter = Integer.parseInt(txtSoLuongTon.getText());

        double giaBanAfter = Double.parseDouble(txtDonGia.getText());

        String barcode = jlbBarcode.getText();
        String ghiChu = txtMoTa.getText();

        ChiTietSanPham ctsp = new ChiTietSanPham();
        ctsp.setIdSanPham(sp.getIdSanPham());
        ctsp.setIdLoaiSanPham(lsp.getIdLoaiSanPham());
        ctsp.setIdThuongHieu(th.getIdThuongHieu());
        ctsp.setIdKhoiLuong(kl.getIdKhoiLuong());
        ctsp.setIdDonViTinh(dvt.getIdDonViTinh());
        ctsp.setIdXuatXu(xx.getIdXuatXu());
        ctsp.setIdChatLieu(cl.getIdChatLieu());
        ctsp.setSoLuongTon(soLuongAfter);
        ctsp.setDonGia(giaBanAfter);
        ctsp.setBarcode(barcode);
        ctsp.setGhiChu(ghiChu);
        ctsp.setCreatedBy(Auth.idNhanVien());
        if (rbNgungBan.isSelected() == true) {
            ctsp.setDeleted(true);
        } else {
            ctsp.setDeleted(false);
        }

        return ctsp;
    }

}
