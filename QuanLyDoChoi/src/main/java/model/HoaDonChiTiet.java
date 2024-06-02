/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ACER
 */
public class HoaDonChiTiet {
    private Integer idHDCT;
    private Integer idHoaDon;
    private Integer idSPCT;
    private Integer soLuong;

    public HoaDonChiTiet() {
    }
    
    public HoaDonChiTiet(Integer idHoaDon, Integer idSPCT, Integer soLuong) {
        this.idHoaDon = idHoaDon;
        this.idSPCT = idSPCT;
        this.soLuong = soLuong;
    }

    public Integer getIdHDCT() {
        return idHDCT;
    }

    public void setIdHDCT(Integer idHDCT) {
        this.idHDCT = idHDCT;
    }

    public Integer getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(Integer idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Integer getIdSPCT() {
        return idSPCT;
    }

    public void setIdSPCT(Integer idSPCT) {
        this.idSPCT = idSPCT;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    
}
