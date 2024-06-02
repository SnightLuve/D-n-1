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
public class KhoiLuong {

    private int idKhoiLuong;
    private String maKhoiLuong;
    private String tenKhoiLuong;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public KhoiLuong() {
    }

    public KhoiLuong(int idKhoiLuong, String maKhoiLuong, String tenKhoiLuong, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idKhoiLuong = idKhoiLuong;
        this.maKhoiLuong = maKhoiLuong;
        this.tenKhoiLuong = tenKhoiLuong;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public KhoiLuong(String maKhoiLuong, String tenKhoiLuong, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.maKhoiLuong = maKhoiLuong;
        this.tenKhoiLuong = tenKhoiLuong;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public int getIdKhoiLuong() {
        return idKhoiLuong;
    }

    public void setIdKhoiLuong(int idKhoiLuong) {
        this.idKhoiLuong = idKhoiLuong;
    }

    public String getMaKhoiLuong() {
        return maKhoiLuong;
    }

    public void setMaKhoiLuong(String maKhoiLuong) {
        this.maKhoiLuong = maKhoiLuong;
    }

    public String getTenKhoiLuong() {
        return tenKhoiLuong;
    }

    public void setTenKhoiLuong(String tenKhoiLuong) {
        this.tenKhoiLuong = tenKhoiLuong;
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

    public KhoiLuong(String maKhoiLuong, String tenKhoiLuong, int createdBy, boolean delete) {
        this.maKhoiLuong = maKhoiLuong;
        this.tenKhoiLuong = tenKhoiLuong;
        this.createdBy = createdBy;
        this.delete = delete;
    }
    

    public KhoiLuong(String tenKhoiLuong, LocalDate updatedAt, int updatedBy) {
        this.tenKhoiLuong = tenKhoiLuong;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    
    
    
    public Object[] toDataRow(){
        return new Object[]{
          maKhoiLuong,tenKhoiLuong,createdBy,updatedBy
        };
    }
}
