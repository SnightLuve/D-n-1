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
public class LoaiSanPham {

    private int idLoaiSanPham;
    private String maLoaiSanPham;
    private String tenLoaiSanPham;

    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int idLoaiSanPham, String maLoaiSanPham, String tenLoaiSanPham, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idLoaiSanPham = idLoaiSanPham;
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public LoaiSanPham(String maLoaiSanPham, String tenLoaiSanPham, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public LoaiSanPham(int idLoaiSanPham, String maLoaiSanPham, String tenLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public int getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public String getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(String maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
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
    public LoaiSanPham(String maLoaiSanPham, String tenLoaiSanPham, int createdBy, boolean delete) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.createdBy = createdBy;
        this.delete = delete;
    }
    

    public LoaiSanPham(String tenLoaiSanPham, LocalDate updatedAt, int updatedBy) {
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Object[] toDataRow() {
        return new Object[]{
            maLoaiSanPham, tenLoaiSanPham,createdBy,updatedBy
        };
    }

}
