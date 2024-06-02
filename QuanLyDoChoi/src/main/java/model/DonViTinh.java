/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class DonViTinh {

    private int idDonViTinh;
    private String maDonViTinh;
    private String tenDonViTinh;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public DonViTinh() {
    }

    public DonViTinh(int idDonViTinh, String maDonViTinh, String tenDonViTinh, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idDonViTinh = idDonViTinh;
        this.maDonViTinh = maDonViTinh;
        this.tenDonViTinh = tenDonViTinh;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public DonViTinh(int idDonViTinh, String maDonViTinh, String tenDonViTinh) {
        this.idDonViTinh = idDonViTinh;
        this.maDonViTinh = maDonViTinh;
        this.tenDonViTinh = tenDonViTinh;
    }

    public DonViTinh(String maDonViTinh, String tenDonViTinh, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.maDonViTinh = maDonViTinh;
        this.tenDonViTinh = tenDonViTinh;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public int getIdDonViTinh() {
        return idDonViTinh;
    }

    public void setIdDonViTinh(int idDonViTinh) {
        this.idDonViTinh = idDonViTinh;
    }

    public String getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(String maDonViTinh) {
        this.maDonViTinh = maDonViTinh;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
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

    public DonViTinh(String tenDonViTinh, LocalDate updatedAt, int updatedBy) {
        this.tenDonViTinh = tenDonViTinh;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public DonViTinh(String maDonViTinh, String tenDonViTinh, int createdBy, boolean delete) {
        this.maDonViTinh = maDonViTinh;
        this.tenDonViTinh = tenDonViTinh;
        this.createdBy = createdBy;
        this.delete = delete;
    }
    
    
    

    public Object[] toDaTaRow() {
        return new Object[]{
            maDonViTinh, tenDonViTinh, createdBy, updatedBy, delete
        };
    }

}
