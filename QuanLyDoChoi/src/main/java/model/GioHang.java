/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class GioHang {
    private Integer idCTSP;
    private String maSP;
    private String tenSP;
    private Integer soLuong;
    private Double thanhTien;
    private String maHoaDon;

    public GioHang() {
    }

    public GioHang(Integer idCTSP, String maSP, String tenSP, Integer soLuong, Double thanhTien,String maHoaDon) {
        this.idCTSP = idCTSP;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.maHoaDon=maHoaDon;
    }

    public Integer getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(Integer idCTSP) {
        this.idCTSP = idCTSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    
}
