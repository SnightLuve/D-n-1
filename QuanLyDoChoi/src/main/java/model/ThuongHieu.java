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
public class ThuongHieu {

    private int idThuongHieu;
    private String maThuongHieu;
    private String tenThuongHieu;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public ThuongHieu() {
    }

    public ThuongHieu(int idThuongHieu, String maThuongHieu, String tenThuongHieu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idThuongHieu = idThuongHieu;
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public int getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(int idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public String getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(String maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }

    public String getTenThuongHieu() {
        return tenThuongHieu;
    }

    public void setTenThuongHieu(String tenThuongHieu) {
        this.tenThuongHieu = tenThuongHieu;
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

    public ThuongHieu(String maThuongHieu, String tenThuongHieu, int createdBy, boolean delete) {
        this.maThuongHieu = maThuongHieu;
        this.tenThuongHieu = tenThuongHieu;
        this.createdBy = createdBy;
        this.delete = delete;
    }

    public ThuongHieu(String tenThuongHieu, LocalDate updatedAt, int updatedBy) {
        this.tenThuongHieu = tenThuongHieu;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
    
    public Object[] toDataRow(){
        return new Object[]{
            maThuongHieu,tenThuongHieu,createdBy,updatedBy
        };
    }
}
