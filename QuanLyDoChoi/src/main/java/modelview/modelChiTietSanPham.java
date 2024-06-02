/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelview;

import java.sql.Date;

/**
 *
 * @author Dell
 */
public class modelChiTietSanPham {

    private Integer idCTSP;
    private String maSp;
    private String tenSanPham;
    private String tenLoaiSanPham;
    private String tenThuongHieu;

    private String tenXuatXu;
    private String tenChatLieu;

    private String tenDonVitinh;
    private String tenKhoiLuong;
    private double donGia;

    private String barcode;

    private String ghiChu;
    private int soLuongTon;

    private Date ngayXuatKho;

    private boolean deleted;

    public modelChiTietSanPham() {
    }

    public modelChiTietSanPham(String maSp, String tenSanPham, String tenLoaiSanPham, String tenThuongHieu, String tenXuatXu, String tenChatLieu, String tenDonVitinh, String tenKhoiLuong, double donGia, String barcode, String ghiChu, int soLuongTon, Date ngayXuatKho, boolean deleted) {
        this.maSp = maSp;
        this.tenSanPham = tenSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.tenThuongHieu = tenThuongHieu;
        this.tenXuatXu = tenXuatXu;
        this.tenChatLieu = tenChatLieu;
        this.tenDonVitinh = tenDonVitinh;
        this.tenKhoiLuong = tenKhoiLuong;
        this.donGia = donGia;
        this.barcode = barcode;
        this.ghiChu = ghiChu;
        this.soLuongTon = soLuongTon;
        this.ngayXuatKho = ngayXuatKho;
        this.deleted = deleted;
    }

    public Integer getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(Integer idCTSP) {
        this.idCTSP = idCTSP;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getTenDonVitinh() {
        return tenDonVitinh;
    }

    public void setTenDonVitinh(String tenDonVitinh) {
        this.tenDonVitinh = tenDonVitinh;
    }

    public String getTenKhoiLuong() {
        return tenKhoiLuong;
    }

    public void setTenKhoiLuong(String tenKhoiLuong) {
        this.tenKhoiLuong = tenKhoiLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Date getNgayXuatKho() {
        return ngayXuatKho;
    }

    public void setNgayXuatKho(Date ngayXuatKho) {
        this.ngayXuatKho = ngayXuatKho;
    }

    @Override
    public String toString() {
        return "modelChiTietSanPham{" + "idCTSP=" + idCTSP + ", maSp=" + maSp + ", tenSanPham=" + tenSanPham + ", tenLoaiSanPham=" + tenLoaiSanPham + ", tenThuongHieu=" + tenThuongHieu + ", tenXuatXu=" + tenXuatXu + ", tenChatLieu=" + tenChatLieu + ", tenDonVitinh=" + tenDonVitinh + ", tenKhoiLuong=" + tenKhoiLuong + ", donGia=" + donGia + ", barcode=" + barcode + ", ghiChu=" + ghiChu + ", soLuongTon=" + soLuongTon + ", ngayXuatKho=" + ngayXuatKho + ", deleted=" + deleted + '}';
    }

}
