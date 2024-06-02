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
public class ChatLieu {

    private int idChatLieu;
    private String maChatLieu;
    private String tenChatLieu;
    private Date createdAt;
    private int createdBy;
    private LocalDate updatedAt;
    private int updatedBy;
    private boolean delete;

    public ChatLieu() {
    }

    public ChatLieu(String maChatLieu, String tenChatLieu, int createdBy, boolean delete) {
        this.maChatLieu = maChatLieu;
        this.tenChatLieu = tenChatLieu;
        this.createdBy = createdBy;
        this.delete = delete;
    }

    public ChatLieu(String tenChatLieu, LocalDate updatedAt, int updatedBy) {
        this.tenChatLieu = tenChatLieu;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
    
    

    public ChatLieu(int idChatLieu, String maChatLieu, String tenChatLieu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.idChatLieu = idChatLieu;
        this.maChatLieu = maChatLieu;
        this.tenChatLieu = tenChatLieu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public ChatLieu(String maChatLieu, String tenChatLieu, Date createdAt, int createdBy, LocalDate updatedAt, int updatedBy, boolean delete) {
        this.maChatLieu = maChatLieu;
        this.tenChatLieu = tenChatLieu;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.delete = delete;
    }

    public int getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(int idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public String getMaChatLieu() {
        return maChatLieu;
    }

    public void setMaChatLieu(String maChatLieu) {
        this.maChatLieu = maChatLieu;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
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
    public Object[] toDataRow(){
        return new Object[]{
            this.maChatLieu,this.tenChatLieu,this.createdBy,this.updatedBy
        };
    }

    
    
    
    

}
