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
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;
import serviceImpl.NhanVienServiceImpl;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ACER
 */
public class NhanVienJPanel extends javax.swing.JPanel implements ThreadFactory, Runnable {

    //Call Model
    DefaultTableModel NVModel = new DefaultTableModel();
    //Call Service
    NhanVienServiceImpl nvsv = new NhanVienServiceImpl();
    //Call List
    List<NhanVien> listNV = new ArrayList<>();
    //Call WebCam
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    public NhanVienJPanel() {
        initComponents();
        initWebcam();

        NVModel = (DefaultTableModel) NhanVienTable.getModel();
        this.setTitle();
        this.fillData();
        ngaySinhJDC.setDateFormatString("dd/MM/yyyy");
        rdNam.setSelected(true);
        rdQuanLy.setSelected(true);
    }

    //RamDom Mật Khẩu
    private String RandomMatKhau() {
        char[] chars = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };

        // Tạo một mật khẩu có độ dài length
        String password = "";
        for (int i = 0; i < 10; i++) {
            // Lấy ngẫu nhiên một kí tự từ mảng chars
            int index = (int) (Math.random() * chars.length);
            password += chars[index];
        }

        return password;
    }

    //send Email
    public void SendMail(String matKhau) {
        final String username = "nguyenvimanhnqt@gmail.com";
        final String password = "uozg gtlr covh ypwr";
        final String to = txtEmail.getText();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject("Tạo tài khoản thành công của CỬA HÀNG BÁN ĐỒ CHƠI KING-KIDS");
            mimeMessage.setText("Đây là mật khẩu của bạn: "
                    + "\n"
                    + "\n"
                    + "\n" + "Mật khẩu: " + matKhau
            );

            // Gửi email
            Transport.send(mimeMessage);
            JOptionPane.showMessageDialog(this, "Mật Khẩu Đã Được Gửi Đến Email Có Địa Chỉ Là:" + to);
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send email: " + ex.getMessage());
        }
    }

    //closeWebcam
    public void closeWebcam() {
        if (webcam.isOpen()) {
            webcam.close();
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
            WebcamPN.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 201));
            executor.execute(this);
        } catch (Exception e) {
            this.closeWebcam();
        }
    }

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
                String[] cccd = result.getText().split("\\|");
                txtHoTen.setText(cccd[2]);
                String gioiTinh = cccd[4];
                if (gioiTinh.equalsIgnoreCase("Nam")) {
                    rdNam.setSelected(true);
                } else {
                    rdNu.setSelected(false);
                }
                txtCCCD.setText(cccd[0]);
                txtDiaChi.setText(cccd[5]);
                String ngaySinh = cccd[3];
                String date = ngaySinh.substring(0, 2);
                String month = ngaySinh.substring(2, 4);
                String year = ngaySinh.substring(4, 8);
                Date birthDay = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(date));
                ngaySinhJDC.setDate(birthDay);
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    public void setTitle() {
        String header[] = {"Mã NV", "Họ Tên", "Ngày Sinh", "SĐT", "Email", "Địa Chỉ", "Giới Tính", "CCCD", "Trạng Thái", "Chức Vụ"};
        NVModel.setColumnIdentifiers(header);
    }

    public void showData(List<NhanVien> listNV) {
        NVModel.setRowCount(0);
        for (NhanVien nv : listNV) {
            String gioiTinh = "";
            String trangThai = "";
            String chucVu = "";
            if (nv.isGioiTinh() == true) {
                gioiTinh = "Nam";
            } else {
                gioiTinh = "Nữ";
            }
            if (nv.isTrangThai() == true) {
                trangThai = "Đang Làm Việc";
            } else {
                trangThai = "Nghỉ Việc";
            }
            if (nv.isChucVu() == true) {
                chucVu = "Quản Lý";
            } else {
                chucVu = "Nhân Viên";
            }
            Object row[] = {nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getNgaySinh(), nv.getSdt(), nv.getEmail(), nv.getDiaChi(), gioiTinh, nv.getCccd(), trangThai, chucVu};
            NVModel.addRow(row);
        }
    }

    public void fillData() {
        listNV = nvsv.getAll();
        showData(listNV);
        this.clearText();
    }

    public NhanVien checkNhanVien() throws WriterException, IOException {
        String ten = txtHoTen.getText();
        String diaChi = txtDiaChi.getText();
        Date ngaySinh = ngaySinhJDC.getDate();
        String sdt = txtSDT.getText();
        String cccd = txtCCCD.getText();
        String email = txtEmail.getText();
        String ma = txtMaNV.getText();
        String matKhau = "";
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+(.[a-zA-Z]+){2}$");
        int count = 0;
        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Nhân Viên Không Được Trống!");
        } else {
            if (ngaySinh == null) {
                count++;
                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Sinh!");
            } else {
                if (sdt.trim().isEmpty()) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Bạn Chưa Điền SĐT!");
                } else {
                    if (!sdt.matches("^(0|84)?\\d{9}$")) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Không Đúng Định Dạng SĐT!");
                    } else {
                        if (cccd.trim().isEmpty()) {
                            count++;
                            JOptionPane.showMessageDialog(this, "CCCD Không Được Trống!");
                        } else {
                            if (cccd.length() != 12) {
                                count++;
                                JOptionPane.showMessageDialog(this, "CCCD phải là 12 số");
                            } else {
                                NhanVien nv2 = nvsv.findNVByCCCD(cccd);
                                if (nv2 != null) {
                                    count++;
                                    JOptionPane.showMessageDialog(this, "CCCD Này Đã Tồn Tại!");
                                } else {
                                    if (diaChi.trim().isEmpty()) {
                                        count++;
                                        JOptionPane.showMessageDialog(this, "Địa Chỉ Không Được Trống!");
                                    } else {
                                        if (email.trim().isEmpty()) {
                                            count++;
                                            JOptionPane.showMessageDialog(this, "Email Không Được Trống!");
                                        } else {
                                            if (!pattern.matcher(email).find()) {
                                                count++;
                                                JOptionPane.showMessageDialog(this, "Email Không Đúng Định Dạng!");
                                            } else {
                                                if (ma.trim().isEmpty()) {
                                                    count++;
                                                    JOptionPane.showMessageDialog(this, "Mã Không Được Trống!");
                                                } else {
                                                    NhanVien nv1 = nvsv.findNVByMaNV(ma);
                                                    if (nv1 != null) {
                                                        count++;
                                                        JOptionPane.showMessageDialog(this, "Mã Nhân Viên Này Đã Tồn Tại!");
                                                    } else {
                                                        matKhau = RandomMatKhau();
                                                        this.SendMail(matKhau);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        boolean gioiTinh;
        if (rdNam.isSelected() == true) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        boolean chucVu;
        if (rdQuanLy.isSelected() == true) {
            chucVu = true;
        } else {
            chucVu = false;
        }
        if (count == 0) {
            NhanVien nv = new NhanVien(ma, ten, gioiTinh, ngaySinh, diaChi, cccd, sdt, email, true, chucVu, matKhau);
            return nv;
        } else {
            return null;
        }

    }

    public NhanVien checkSuaNhanVien() throws WriterException, IOException {
        String ten = txtHoTen.getText();
        String diaChi = txtDiaChi.getText();
        Date ngaySinh = ngaySinhJDC.getDate();
        String sdt = txtSDT.getText();
        String cccd = txtCCCD.getText();
        String email = txtEmail.getText();
        String ma = txtMaNV.getText();
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+@[a-zA-Z]+(.[a-zA-Z]+){2}$");
        int count = 0;
        if (ten.trim().isEmpty()) {
            count++;
            JOptionPane.showMessageDialog(this, "Tên Nhân Viên Không Được Trống!");
        } else {
            if (ngaySinh == null) {
                count++;
                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Ngày Sinh!");
            } else {
                if (sdt.trim().isEmpty()) {
                    count++;
                    JOptionPane.showMessageDialog(this, "Bạn Chưa Điền SĐT!");
                } else {
                    if (!sdt.matches("^(0|84)?\\d{9}$")) {
                        count++;
                        JOptionPane.showMessageDialog(this, "Không Đúng Định Dạng SĐT!");
                    } else {
                        if (cccd.trim().isEmpty()) {
                            count++;
                            JOptionPane.showMessageDialog(this, "CCCD Không Được Trống!");
                        } else {
                            if (cccd.length() != 12) {
                                count++;
                                JOptionPane.showMessageDialog(this, "CCCD phải là 12 số");
                            } else {
                                if (diaChi.trim().isEmpty()) {
                                    count++;
                                    JOptionPane.showMessageDialog(this, "Địa Chỉ Không Được Trống!");
                                } else {
                                    if (email.trim().isEmpty()) {
                                        count++;
                                        JOptionPane.showMessageDialog(this, "Email Không Được Trống!");
                                    } else {
                                        if (!pattern.matcher(email).find()) {
                                            count++;
                                            JOptionPane.showMessageDialog(this, "Email Không Đúng Định Dạng!");
                                        } else {
                                            if (ma.trim().isEmpty()) {
                                                count++;
                                                JOptionPane.showMessageDialog(this, "Mã Không Được Trống!");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        boolean gioiTinh;
        if (rdNam.isSelected() == true) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        boolean chucVu;
        if (rdQuanLy.isSelected() == true) {
            chucVu = true;
        } else {
            chucVu = false;
        }
        if (count == 0) {
            NhanVien nv = new NhanVien(ma, ten, gioiTinh, ngaySinh, diaChi, cccd, sdt, email, true, chucVu);
            return nv;
        } else {
            return null;
        }

    }

    public void clearText() {
        txtHoTen.setText("");
        buttonGroup1.clearSelection();
        ngaySinhJDC.setDate(null);
        txtSDT.setText("");
        txtCCCD.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        buttonGroup2.clearSelection();
        txtMaNV.setText("");
    }

    private void addNhanVien() throws WriterException, IOException {
        NhanVien nv = checkNhanVien();
        if (nv != null) {
            boolean add = nvsv.addNV(nv);
            if (add == true) {
                this.fillData();
                JOptionPane.showMessageDialog(this, "Thêm Nhân Viên Thành Công!");
            }
        }
    }

    public void updateNhanVien() throws WriterException, IOException {
        int selected = NhanVienTable.getSelectedRow();
        if (selected >= 0) {
            String maNV = (String) NhanVienTable.getValueAt(selected, 0);
            NhanVien nvCheck = checkSuaNhanVien();
            boolean suaNV = nvsv.updateNV(nvCheck, maNV);
            if (suaNV == true) {
                this.fillData();
                JOptionPane.showMessageDialog(this, "Sửa Thông Tin Nhân Viên Thành Công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Dòng Nào!");
        }
    }

    private void fillTextField(int selected) {
        NhanVien nv = listNV.get(selected);
        txtHoTen.setText(nv.getTenNhanVien());
        if (nv.isGioiTinh() == true) {
            rdNam.setSelected(true);
        } else {
            rdNu.setSelected(true);
        }
        ngaySinhJDC.setDate(nv.getNgaySinh());
        txtSDT.setText(nv.getSdt());
        txtCCCD.setText(nv.getCccd());
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());
        if (nv.isChucVu() == true) {
            rdQuanLy.setSelected(true);
        } else {
            rdNhanVien.setSelected(true);
        }
        txtMaNV.setText(nv.getMaNhanVien());
    }

    private void updateTrangThai() {
        int selected = NhanVienTable.getSelectedRow();
        if (selected >= 0) {
            String maNV = (String) NhanVienTable.getValueAt(selected, 0);
            NhanVien nv = nvsv.findNVByMaNV(maNV);
            if (nv.isTrangThai() == true) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Muốn Thay Đổi Trạng Thái Nhân Viên Này Sang Nghỉ Việc?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean update = nvsv.updateTrangThai(false, maNV);
                    if (update == true) {
                        this.fillData();
                        JOptionPane.showMessageDialog(this, "Thay Đổi Trạng Thái Nhân Viên Thành Công!");
                    }
                }
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn Muốn Thay Đổi Trạng Thái Nhân Viên Này Sang Đi Làm Lại?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean update = nvsv.updateTrangThai(true, maNV);
                    if (update == true) {
                        this.fillData();
                        JOptionPane.showMessageDialog(this, "Thay Đổi Trạng Thái Nhân Viên Thành Công!");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Dòng Nào!");
        }
    }

    private void findNV() {
        List<NhanVien> list = nvsv.findNV(txtFind.getText());
        showData(list);
    }

//    private void gennerateQR(String cccd) throws WriterException, IOException {
//        String path1 = "E:\\Group7-DuAn1\\QuanLyDoChoi\\QRCCCD\\" + cccd + ".png";
//        QRCodeWriter qc = new QRCodeWriter();
//        BitMatrix bm = qc.encode(cccd, BarcodeFormat.QR_CODE, 300, 300);
//        Path path = FileSystems.getDefault().getPath(path1);
//        MatrixToImageWriter.writeToPath(bm, "PNG", path);
//    }

    private void xoaNhanVien() {
        int selected = NhanVienTable.getSelectedRow();
        if (selected >= 0) {
            String maNV = (String) NhanVienTable.getValueAt(selected, 0);
            boolean xoa = nvsv.xoaNV(maNV);
            if (xoa == true) {
                this.fillData();
                JOptionPane.showMessageDialog(this, "Xóa Nhân Viên Thành Công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Nhân Viên Nào!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rdQuanLy = new javax.swing.JRadioButton();
        rdNhanVien = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        btnTrangThai = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        WebcamPN = new javax.swing.JPanel();
        ngaySinhJDC = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        NhanVienTable = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(100, 180, 244));
        jLabel1.setText("Quản Lý Nhân Viên");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Họ Tên");

        txtHoTen.setForeground(new java.awt.Color(100, 180, 244));
        txtHoTen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Giới Tính");

        buttonGroup1.add(rdNam);
        rdNam.setSelected(true);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Ngày Sinh");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Số Điện Thoại");

        txtSDT.setForeground(new java.awt.Color(100, 180, 244));
        txtSDT.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        btnThem.setBackground(new java.awt.Color(100, 180, 244));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-user.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(100, 180, 244));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/mechanic.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnMoi.setBackground(new java.awt.Color(100, 180, 244));
        btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoiMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Địa Chỉ");

        txtDiaChi.setForeground(new java.awt.Color(100, 180, 244));
        txtDiaChi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Email");

        txtEmail.setForeground(new java.awt.Color(100, 180, 244));
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Chức Vụ");

        buttonGroup2.add(rdQuanLy);
        rdQuanLy.setSelected(true);
        rdQuanLy.setText("Quản Lý");

        buttonGroup2.add(rdNhanVien);
        rdNhanVien.setText("Nhân Viên");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Mã Nhân Viên");

        txtMaNV.setForeground(new java.awt.Color(100, 180, 244));
        txtMaNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        btnTrangThai.setBackground(new java.awt.Color(100, 180, 244));
        btnTrangThai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTrangThai.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-condition-24.png"))); // NOI18N
        btnTrangThai.setText("Trạng Thái");
        btnTrangThai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrangThaiMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("CCCD");

        txtCCCD.setForeground(new java.awt.Color(100, 180, 244));
        txtCCCD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));

        WebcamPN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(100, 180, 244));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/qr-code.png"))); // NOI18N
        jLabel15.setText("Quét Mã QR");

        btnXoa.setBackground(new java.awt.Color(100, 180, 244));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTrangThai))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdNu))
                            .addComponent(txtSDT)
                            .addComponent(txtHoTen)
                            .addComponent(txtCCCD)
                            .addComponent(ngaySinhJDC, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdQuanLy)
                                .addGap(18, 18, 18)
                                .addComponent(rdNhanVien))
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel15))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(WebcamPN, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rdNam)
                            .addComponent(rdNu)
                            .addComponent(jLabel9)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ngaySinhJDC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10)
                                    .addComponent(rdNhanVien))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)))
                            .addComponent(rdQuanLy)))
                    .addComponent(WebcamPN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(100, 180, 244));
        jLabel2.setText("Thông Tin Nhân Viên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Danh Sách Nhân Viên");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search-profile.png"))); // NOI18N
        jLabel11.setText("Tìm Kiếm");

        txtFind.setForeground(new java.awt.Color(153, 153, 153));
        txtFind.setText("Tìm Kiếm Theo MaNV,TenNV,DiaChi,SDT,CCCD,Email");
        txtFind.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 180, 244)));
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

        NhanVienTable.setModel(new javax.swing.table.DefaultTableModel(
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
        NhanVienTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NhanVienTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(NhanVienTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 954, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(515, 515, 515)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            // TODO add your handling code here:
            this.addNhanVien();
        } catch (WriterException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            // TODO add your handling code here:
            this.updateNhanVien();
        } catch (WriterException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void NhanVienTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhanVienTableMouseClicked
        // TODO add your handling code here:
        this.fillTextField(NhanVienTable.getSelectedRow());
    }//GEN-LAST:event_NhanVienTableMouseClicked

    private void btnMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseClicked
        // TODO add your handling code here:
        this.clearText();
    }//GEN-LAST:event_btnMoiMouseClicked

    private void btnTrangThaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrangThaiMouseClicked
        // TODO add your handling code here:
        this.updateTrangThai();
    }//GEN-LAST:event_btnTrangThaiMouseClicked

    private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindKeyReleased
        // TODO add your handling code here:
        this.findNV();
    }//GEN-LAST:event_txtFindKeyReleased

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        this.xoaNhanVien();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtFindFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFindFocusGained
        // TODO add your handling code here:
        if (txtFind.getText().endsWith("Tìm Kiếm Theo MaNV,TenNV,DiaChi,SDT,CCCD,Email")) {
            txtFind.setText("");
        }
    }//GEN-LAST:event_txtFindFocusGained

    private void txtFindFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFindFocusLost
        // TODO add your handling code here:
        if (txtFind.getText().endsWith("")) {
            txtFind.setText("Tìm Kiếm Theo MaNV,TenNV,DiaChi,SDT,CCCD,Email");
        }
    }//GEN-LAST:event_txtFindFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable NhanVienTable;
    private javax.swing.JPanel WebcamPN;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTrangThai;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser ngaySinhJDC;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNhanVien;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JRadioButton rdQuanLy;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
