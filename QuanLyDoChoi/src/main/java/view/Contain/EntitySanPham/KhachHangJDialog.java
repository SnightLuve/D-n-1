/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.Contain.EntitySanPham;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.KhachHang;
import serviceImpl.KhachHangServiceImpl;
import utils.Auth;
import view.Contains.BanHangJPanel;

/**
 *
 * @author ACER
 */
public class KhachHangJDialog extends javax.swing.JDialog {

    int index = -1;
    KhachHangServiceImpl service = new KhachHangServiceImpl();
    DefaultTableModel model;
    DefaultTableModel modelKHBought = new DefaultTableModel();
    List<KhachHang> listKH=new ArrayList<>();
    BanHangJPanel bhj;
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    public KhachHangJDialog(JPanel parent, boolean modal) {
        initComponents();
        bhj=(BanHangJPanel) parent;
        setLocationRelativeTo(null);
        listKH=service.getAll();
        fillTable(listKH);
        DanhSachKhachHangTable.setRowHeight(50);
        findKH.setText("Tìm Kiếm Theo MaKH,TenKH,Email,DiaChi,SDT");
    }
    
    void fillTable(List<KhachHang> list) {
        model = (DefaultTableModel) DanhSachKhachHangTable.getModel();
        model.setRowCount(0);
        int i=1;
        for (KhachHang kh : list) {
            model.addRow(new Object[]{i++,kh.getMaKhachHang(),kh.getTenKhachHang(),kh.getEmail(),kh.getDiaChi(),kh.getSdt()});
        }
    }
    
    void showData(int index) {
        KhachHang kh = service.getAll().get(index);
        txtHoTen.setText(kh.getTenKhachHang());
        txtEmail.setText(kh.getEmail());
        txtDiaChi.setText(kh.getDiaChi());
        txtSDT.setText(kh.getSdt());
    }
    
    private void clearText() {
        txtHoTen.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
    }
    
    //Tự Động GenerateMa
    private String gennerateMa() {
        KhachHang kh = service.selectTop1DESC();
        String maKHSub = kh.getMaKhachHang().substring(2, kh.getMaKhachHang().indexOf(" "));
        Integer soMaKh = Integer.parseInt(maKHSub);
        String maKHFinal = "KH" + (soMaKh + 1);
        return maKHFinal;
    }
    
    
    private KhachHang checkKhachHang() {
        String ten = txtHoTen.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        String sdt = txtSDT.getText();
        String ma = this.gennerateMa();
        Pattern patternEmail = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+(.[a-zA-Z]+){2}$");
        Pattern patternSDT = Pattern.compile("^(0|\\+84)([0-9]{9})$");
        int count = 0;
        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Khách Hàng Không Được Trống!");
        } else {
            if (email.trim().isEmpty()) {
                count++;
                JOptionPane.showMessageDialog(this, "Email Khách Hàng Không Được Để Trống!");
            } else {
                if (!patternEmail.matcher(email).find()) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Không Đúng Định Dạng Email!");
                } else {
                    if (diaChi.trim().isEmpty()) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Địa Chỉ Khách Hàng Không Được Trống!");
                    } else {
                        if (sdt.trim().isEmpty()) {
                            count++;
                            JOptionPane.showMessageDialog(this, "SĐT Khách Hàng Không Được Chống!");
                        } else {
                            if (!patternSDT.matcher(sdt).find()) {
                                count++;
                                JOptionPane.showMessageDialog(this, "Không Đúng Định Dạng SĐT!");
                            }
                        }
                    }
                }
            }
        }
        if (count == 0) {
            return new KhachHang(ma, ten, email, diaChi, sdt, LocalDate.parse(this.toDayButNextYear()), Auth.idNhanVien(), false);
        } else {
            return null;
        }
    }
    
    
    //update Check Khach Hàng
    private KhachHang checkUpdateKhachHang() {
        String ten = txtHoTen.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        String sdt = txtSDT.getText();
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+(.[a-zA-Z]+){2}$");
        int count = 0;
        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Khách Hàng Không Được Trống!");
        } else {
            if (email.trim().isEmpty()) {
                count++;
                JOptionPane.showMessageDialog(this, "Email Khách Hàng Không Được Để Trống!");
            } else {
                if (!pattern.matcher(email).find()) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Không Đúng Định Dạng Email!");
                } else {
                    if (diaChi.trim().isEmpty()) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Địa Chỉ Khách Hàng Không Được Trống!");
                    } else {
                        if (sdt.trim().isEmpty()) {
                            count++;
                            JOptionPane.showMessageDialog(this, "SĐT Khách Hàng Không Được Chống!");
                        }
                    }
                }
            }
        }
        if (count == 0) {
            return new KhachHang( ten, email, diaChi, sdt, LocalDate.parse(this.toDay()), Auth.idNhanVien());
        } else {
            return null;
        }
    }

    private String toDayButNextYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String ngay = "";
        if (month < 9 && date < 10) {
            ngay = ((year + 1) + "-" + "0" + (month + 1) + "-" + "0" + date);
        } else if (month < 9 && date >= 10) {
            ngay = ((year + 1) + "-" + "0" + (month + 1) + "-" + date);
        } else if (month >= 9 && date < 10) {
            ngay = ((year + 1) + "-" + (month + 1) + "-" + "0" + date);
        } else if (month >= 9 && date >= 10) {
            ngay = ((year + 1) + "-" + (month + 1) + "-" + date);
        }
        return ngay;
    }
    
    private String toDay() {
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
        return ngay;
    }
    
    //chọn Khách Hàng
    private void chonKhachHang(){
        int selected=DanhSachKhachHangTable.getSelectedRow();
        if(selected>=0){
            KhachHang kh=listKH.get(selected);
            bhj.chonKH(kh);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Khách Hàng Nào!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DanhSachKhachHangTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        findKH = new javax.swing.JTextField();
        btnChonKH = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tab.setForeground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        DanhSachKhachHangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Khách Hàng", "Tên Khách Hàng", "Email", "Địa Chỉ", "SĐT"
            }
        ));
        DanhSachKhachHangTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DanhSachKhachHangTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DanhSachKhachHangTableMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(DanhSachKhachHangTable);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Tìm Số Điện Thoại");

        findKH.setForeground(new java.awt.Color(153, 153, 153));
        findKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                findKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                findKHFocusLost(evt);
            }
        });
        findKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findKHKeyReleased(evt);
            }
        });

        btnChonKH.setBackground(new java.awt.Color(255, 247, 110));
        btnChonKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChonKH.setForeground(new java.awt.Color(0, 0, 0));
        btnChonKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-check-24.png"))); // NOI18N
        btnChonKH.setText("Chọn");
        btnChonKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChonKHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(findKH, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 141, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(findKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        tab.addTab("Danh Sách Khách Hàng", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Họ Tên");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Email");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Địa Chỉ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Số Điện Thoại");

        btnThem.setBackground(new java.awt.Color(100, 180, 244));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-user.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(100, 180, 244));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-pencil-24.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(100, 180, 244));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtDiaChi)
                            .addComponent(txtSDT))
                        .addContainerGap(94, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tab.addTab("Thêm Khách Hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DanhSachKhachHangTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DanhSachKhachHangTableMouseClicked
        // TODO add your handling code here:
        int index = DanhSachKhachHangTable.getSelectedRow();
        showData(index);
    }//GEN-LAST:event_DanhSachKhachHangTableMouseClicked

    private void DanhSachKhachHangTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DanhSachKhachHangTableMousePressed
        // TODO add your handling code here:
        int index = DanhSachKhachHangTable.getSelectedRow();
        if (evt.getClickCount() == 2) {
            showData(index);
            tab.setSelectedIndex(1);
        }
    }//GEN-LAST:event_DanhSachKhachHangTableMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        KhachHang kh = checkKhachHang();
        if (kh != null) {
            if (service.addKhachHang(kh) > 0) {
                fillTable(service.getAll());
                this.clearText();
                JOptionPane.showMessageDialog(this, "Thêm Khách Hàng Thành Công!");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        KhachHang kh = checkUpdateKhachHang();
        int index = DanhSachKhachHangTable.getSelectedRow();
        if (index >= 0) {
            String ma = DanhSachKhachHangTable.getValueAt(index, 1).toString();
            if (kh != null) {
                if (service.updateKhachHang(kh, ma) > 0) {
                    fillTable(service.getAll());
                    this.clearText();
                    JOptionPane.showMessageDialog(this, "Sửa Khách Hàng Thành Công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Dòng Nào!");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int index = DanhSachKhachHangTable.getSelectedRow();
        if (index >= 0) {
            String ma = DanhSachKhachHangTable.getValueAt(index, 1).toString();
            if (service.deleteKhachHang(ma) > 0) {
                fillTable(service.getAll());
                this.clearText();
                JOptionPane.showMessageDialog(this, "Xóa Thành Công Khách Hàng!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Khách Hàng!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnChonKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChonKHMouseClicked
        // TODO add your handling code here:
        this.chonKhachHang();
    }//GEN-LAST:event_btnChonKHMouseClicked

    private void findKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findKHKeyReleased
        // TODO add your handling code here:
        String input = findKH.getText();
        if (service.findKhachHang(input) != null) {
            fillTable(service.findKhachHang(input));
        }
    }//GEN-LAST:event_findKHKeyReleased

    private void findKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findKHFocusGained
        // TODO add your handling code here:
        if (findKH.getText().equals("Tìm Kiếm Theo MaKH,TenKH,Email,DiaChi,SDT")) {
            findKH.setText("");
        }
    }//GEN-LAST:event_findKHFocusGained

    private void findKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_findKHFocusLost
        // TODO add your handling code here:
        if (findKH.getText().equals("")) {
            findKH.setText("Tìm Kiếm Theo MaKH,TenKH,Email,DiaChi,SDT");
        }
    }//GEN-LAST:event_findKHFocusLost

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
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhachHangJDialog dialog = new KhachHangJDialog(new javax.swing.JPanel(), true);
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
    private javax.swing.JTable DanhSachKhachHangTable;
    private javax.swing.JButton btnChonKH;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JTextField findKH;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
