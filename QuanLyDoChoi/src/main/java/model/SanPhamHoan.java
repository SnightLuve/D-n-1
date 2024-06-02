/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class SanPhamHoan {
    private Integer idSanPhamHoan;
    private Integer idCTSP;
    private Integer soLuong;
    private Date ngayTra;
    private String maHoaDon;

    public SanPhamHoan() {
    }

    public SanPhamHoan(Integer idCTSP, Integer soLuong, String maHoaDon) {
        this.idCTSP = idCTSP;
        this.soLuong = soLuong;
        this.maHoaDon = maHoaDon;
    }

    public Integer getIdSanPhamHoan() {
        return idSanPhamHoan;
    }

    public void setIdSanPhamHoan(Integer idSanPhamHoan) {
        this.idSanPhamHoan = idSanPhamHoan;
    }

    public Integer getIdCTSP() {
        return idCTSP;
    }

    public void setIdCTSP(Integer idCTSP) {
        this.idCTSP = idCTSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }
    
    
}
