/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class HoaDon {
    private Integer idHoaDon;
    private String maHoaDon;
    private Integer idKhachHang;
    private Integer idNhanVien;
    private Integer idHTTT;
    private Integer idTTHD;
    private Integer idVoucher;
    private Double giamGiaDiemKH;
    private Integer thueVAT;
    private Double tongTienHang;
    private LocalDate ngayTao;
    private Double khachTraTM;
    private Double khachTraCK;
    private Double tienThua;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String diaChiNguoiNhan;
    private Date ngayShipDuTinh;
    private Date ngayDenDuTinh;
    private Double phiShip;
    private String ghiChu;
    private String maGiaoDich;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon,Integer idKhachHang, Integer idNhanVien, Integer idHTTT, Integer idTTHD, Integer idVoucher, Double giamGiaDiemKH, Integer thueVAT, Double tongTienHang, LocalDate ngayTao, Double khachTraTM, Double khachTraCK, Double tienThua, String tenNguoiNhan, String sdtNguoiNhan, String diaChiNguoiNhan, Date ngayShipDuTinh, Date ngayDenDuTinh, Double phiShip, String ghiChu,String maGiaoDich) {
        this.maHoaDon=maHoaDon;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.idHTTT = idHTTT;
        this.idTTHD = idTTHD;
        this.idVoucher = idVoucher;
        this.giamGiaDiemKH = giamGiaDiemKH;
        this.thueVAT = thueVAT;
        this.tongTienHang = tongTienHang;
        this.ngayTao = ngayTao;
        this.khachTraTM = khachTraTM;
        this.khachTraCK = khachTraCK;
        this.tienThua = tienThua;
        this.tenNguoiNhan = tenNguoiNhan;
        this.sdtNguoiNhan = sdtNguoiNhan;
        this.diaChiNguoiNhan = diaChiNguoiNhan;
        this.ngayShipDuTinh = ngayShipDuTinh;
        this.ngayDenDuTinh = ngayDenDuTinh;
        this.phiShip = phiShip;
        this.ghiChu = ghiChu;
        this.maGiaoDich=maGiaoDich;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    } 
    
    public Integer getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(Integer idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public Integer getIdHTTT() {
        return idHTTT;
    }

    public void setIdHTTT(Integer idHTTT) {
        this.idHTTT = idHTTT;
    }

    public Integer getIdTTHD() {
        return idTTHD;
    }

    public void setIdTTHD(Integer idTTHD) {
        this.idTTHD = idTTHD;
    }

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
    }

    public Double getGiamGiaDiemKH() {
        return giamGiaDiemKH;
    }

    public void setGiamGiaDiemKH(Double giamGiaDiemKH) {
        this.giamGiaDiemKH = giamGiaDiemKH;
    }

    public Integer getThueVAT() {
        return thueVAT;
    }

    public void setThueVAT(Integer thueVAT) {
        this.thueVAT = thueVAT;
    }

    public Double getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(Double tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Double getKhachTraTM() {
        return khachTraTM;
    }

    public void setKhachTraTM(Double khachTraTM) {
        this.khachTraTM = khachTraTM;
    }

    public Double getKhachTraCK() {
        return khachTraCK;
    }

    public void setKhachTraCK(Double khachTraCK) {
        this.khachTraCK = khachTraCK;
    }

    public Double getTienThua() {
        return tienThua;
    }

    public void setTienThua(Double tienThua) {
        this.tienThua = tienThua;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSdtNguoiNhan() {
        return sdtNguoiNhan;
    }

    public void setSdtNguoiNhan(String sdtNguoiNhan) {
        this.sdtNguoiNhan = sdtNguoiNhan;
    }

    public String getDiaChiNguoiNhan() {
        return diaChiNguoiNhan;
    }

    public void setDiaChiNguoiNhan(String diaChiNguoiNhan) {
        this.diaChiNguoiNhan = diaChiNguoiNhan;
    }

    public Date getNgayShipDuTinh() {
        return ngayShipDuTinh;
    }

    public void setNgayShipDuTinh(Date ngayShipDuTinh) {
        this.ngayShipDuTinh = ngayShipDuTinh;
    }

    public Date getNgayDenDuTinh() {
        return ngayDenDuTinh;
    }

    public void setNgayDenDuTinh(Date ngayDenDuTinh) {
        this.ngayDenDuTinh = ngayDenDuTinh;
    }

    public Double getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(Double phiShip) {
        this.phiShip = phiShip;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    
}
