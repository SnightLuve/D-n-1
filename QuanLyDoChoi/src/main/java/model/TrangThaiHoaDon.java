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
public class TrangThaiHoaDon {
    private Integer idTTHD;
    private String maTTHD;
    private String tenTTHD;
    private Date createdAt;
    private Integer createdBy;
    private LocalDate updatedAt;
    private Integer updatedBy;
    private boolean deleted;

    public TrangThaiHoaDon() {
    }

    public TrangThaiHoaDon(String maTTHD, String tenTTHD, Date createdAt, Integer createdBy, LocalDate updatedAt, Integer updatedBy, boolean deleted) {
        this.maTTHD = maTTHD;
        this.tenTTHD = tenTTHD;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public Integer getIdTTHD() {
        return idTTHD;
    }

    public void setIdTTHD(Integer idTTHD) {
        this.idTTHD = idTTHD;
    }

    public String getMaTTHD() {
        return maTTHD;
    }

    public void setMaTTHD(String maTTHD) {
        this.maTTHD = maTTHD;
    }

    public String getTenTTHD() {
        return tenTTHD;
    }

    public void setTenTTHD(String tenTTHD) {
        this.tenTTHD = tenTTHD;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
}
