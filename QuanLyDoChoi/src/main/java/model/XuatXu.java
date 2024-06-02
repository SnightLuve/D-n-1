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
public class XuatXu {

    private int idXuatXu;
    private String maXuatXu;
    private String tenXuatXu;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean deleted;

    public XuatXu() {
    }

    public XuatXu(int idXuatXu, String maXuatXu, String tenXuatXu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean deleted) {
        this.idXuatXu = idXuatXu;
        this.maXuatXu = maXuatXu;
        this.tenXuatXu = tenXuatXu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public XuatXu(int idXuatXu, String maXuatXu, String tenXuatXu) {
        this.idXuatXu = idXuatXu;
        this.maXuatXu = maXuatXu;
        this.tenXuatXu = tenXuatXu;
    }

    public XuatXu(String maXuatXu, String tenXuatXu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean deleted) {
        this.maXuatXu = maXuatXu;
        this.tenXuatXu = tenXuatXu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }

    public int getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(int idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public String getMaXuatXu() {
        return maXuatXu;
    }

    public void setMaXuatXu(String maXuatXu) {
        this.maXuatXu = maXuatXu;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
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

    public XuatXu(String maXuatXu, String tenXuatXu, int createdBy, boolean deleted) {
        this.maXuatXu = maXuatXu;
        this.tenXuatXu = tenXuatXu;
        this.createdBy = createdBy;
        this.deleted = deleted;
    }

    public XuatXu(String tenXuatXu, LocalDate updatedAt, int updatedBy) {
        this.tenXuatXu = tenXuatXu;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
    

    public Object[] toDataRow() {
        return new Object[]{
            maXuatXu, tenXuatXu, createdBy, updatedBy
        };
    }

}
