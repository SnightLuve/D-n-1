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
public class ChiTietSanPham {
    private int idCTSP;
    private int idSanPham;
    private int idThuongHieu;
    private int idKhoiLuong;
    private int idDonViTinh;
    private int idXuatXu;
    private int idChatLieu;
    private int idLoaiSanPham;
    private int soLuongTon;
    private double donGia;
    private String barcode;
    private String ghiChu;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean deleted;

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(int idSanPham, int idThuongHieu, int idKhoiLuong, int idDonViTinh, int idXuatXu, int idChatLieu, int idLoaiSanPham, int soLuongTon, double donGia, String barcode, String ghiChu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean deleted) {
        this.idSanPham = idSanPham;
        this.idThuongHieu = idThuongHieu;
        this.idKhoiLuong = idKhoiLuong;
        this.idDonViTinh = idDonViTinh;
        this.idXuatXu = idXuatXu;
        this.idChatLieu = idChatLieu;
        this.idLoaiSanPham = idLoaiSanPham;
        this.soLuongTon = soLuongTon;
        this.donGia = donGia;
        this.barcode = barcode;
        this.ghiChu = ghiChu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public int getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(int idCTSP) {
        this.idCTSP = idCTSP;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(int idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public int getIdKhoiLuong() {
        return idKhoiLuong;
    }

    public void setIdKhoiLuong(int idKhoiLuong) {
        this.idKhoiLuong = idKhoiLuong;
    }

    public int getIdDonViTinh() {
        return idDonViTinh;
    }

    public void setIdDonViTinh(int idDonViTinh) {
        this.idDonViTinh = idDonViTinh;
    }

    public int getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(int idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public int getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(int idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public int getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
}
