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
public class Voucher {
    private int idVoucher;
    private String maVoucher;
    private String tenVoucher;
    private boolean loaiVoucher;
    private double giaTri;
    private int soLuong;
    private int trangThai;
    private double giaTriToiThieu;
    private double giaTriToiDa;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;
    

    public Voucher() {
    }

    public Voucher(String maVoucher, String tenVoucher, boolean loaiVoucher, double giaTri, int soLuong, int trangThai, double giaTriToiThieu, double giaTriToiDa, Date ngayBatDau, Date ngayKetThuc, int createdBy, boolean delete) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.loaiVoucher = loaiVoucher;
        this.giaTri = giaTri;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.giaTriToiThieu = giaTriToiThieu;
        this.giaTriToiDa = giaTriToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.createdBy = createdBy;
        this.delete = delete;
    }

    public Voucher(String tenVoucher, boolean loaiVoucher, double giaTri, int soLuong, int trangThai, double giaTriToiThieu, double giaTriToiDa, Date ngayBatDau, Date ngayKetThuc, LocalDate updatedAt, int updatedBy) {
        this.tenVoucher = tenVoucher;
        this.loaiVoucher = loaiVoucher;
        this.giaTri = giaTri;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.giaTriToiThieu = giaTriToiThieu;
        this.giaTriToiDa = giaTriToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public boolean isLoaiVoucher() {
        return loaiVoucher;
    }

    public void setLoaiVoucher(boolean loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaTriToiThieu() {
        return giaTriToiThieu;
    }

    public void setGiaTriToiThieu(double giaTriToiThieu) {
        this.giaTriToiThieu = giaTriToiThieu;
    }

    public double getGiaTriToiDa() {
        return giaTriToiDa;
    }

    public void setGiaTriToiDa(double giaTriToiDa) {
        this.giaTriToiDa = giaTriToiDa;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    
   
    
    
}
