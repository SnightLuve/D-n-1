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
public class SanPham {

    private int idSanPham;
    private String maSanPham;
    private String tenSanPham;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public SanPham() {
    }

    public SanPham(int idSanPham, String maSanPham, String tenSanPham, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idSanPham = idSanPham;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public SanPham(String maSanPham, String tenSanPham, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }
    
    

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
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

    public SanPham(String maSanPham, String tenSanPham, int createdBy, boolean delete) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.createdBy = createdBy;
        this.delete = delete;
    }

    public SanPham( String tenSanPham, LocalDate updatedAt, int updatedBy) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public SanPham(String maSanPham, String tenSanPham) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
    }

    public Object[] toDataRow() {
        return new Object[]{
            this.maSanPham, this.tenSanPham, this.createdBy, this.updatedBy
        };
    }

}
