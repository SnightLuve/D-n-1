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
public class HinhThucThanhToan {
    private Integer idHTTT;
    private String maHTTT;
    private String tenHTTT;
    private Date createdAt;
    private Integer createdBy;
    private LocalDate updatedAt;
    private Integer updatedBy;
    private boolean deleted;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(String maHTTT, String tenHTTT, Date createdAt, Integer createdBy, LocalDate updatedAt, Integer updatedBy, boolean deleted) {
        this.maHTTT = maHTTT;
        this.tenHTTT = tenHTTT;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public Integer getIdHTTT() {
        return idHTTT;
    }

    public void setIdHTTT(Integer idHTTT) {
        this.idHTTT = idHTTT;
    }

    public String getMaHTTT() {
        return maHTTT;
    }

    public void setMaHTTT(String maHTTT) {
        this.maHTTT = maHTTT;
    }

    public String getTenHTTT() {
        return tenHTTT;
    }

    public void setTenHTTT(String tenHTTT) {
        this.tenHTTT = tenHTTT;
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
