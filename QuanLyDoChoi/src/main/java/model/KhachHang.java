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
public class KhachHang {
    private Integer idKhachHang;
    private String maKhachHang;
    private String tenKhachHang;
    private String email;
    private String diaChi;
    private String sdt;
    private LocalDate ngayHetHan;
    private Date createdAt;
    private Integer createdBy;
    private LocalDate updatedAt;
    private Integer updatedBy;
    private Boolean deleted;

    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String tenKhachHang, String email, String diaChi, String sdt, LocalDate ngayHetHan,int createdBy, Boolean deleted) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.ngayHetHan=ngayHetHan;
        this.createdBy=createdBy;
        this.deleted = deleted;
    }

    
    
    public KhachHang(Integer idKhachHang, String maKhachHang, String tenKhachHang, String email, String diaChi, String sdt, LocalDate ngayHetHan, Date createdAt, Integer createdBy, LocalDate updatedAt, Integer updatedBy, Boolean deleted) {
        this.idKhachHang = idKhachHang;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.ngayHetHan = ngayHetHan;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }
    

    public KhachHang(String maKhachHang, String tenKhachHang, String email, String diaChi, String sdt, LocalDate ngayHetHan, Date createdAt, Integer createdBy, LocalDate updatedAt, Integer updatedBy, Boolean deleted) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.ngayHetHan = ngayHetHan;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public KhachHang(String maKhachHang, String tenKhachHang, String email, String diaChi, String sdt, LocalDate updatedAt, Integer updatedBy) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
    
    public KhachHang(String tenKhachHang, String email, String diaChi, String sdt, LocalDate updatedAt, Integer updatedBy) {
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
    
    

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    
}
