/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.ChiTietSanPham;
import modelview.modelChiTietSanPham;

/**
 *
 * @author ACER
 */
public interface ChiTietSanPhamService {
    public boolean updateHoanHang(Integer soLuong,Integer idCTSP);
    
    public ChiTietSanPham getCTSPByID(Integer idCTSP);
    
    public boolean updateSoLuongCTSP(int soLuong,int idCTSP);
    
    public ChiTietSanPham getCTSPByBarcode(String barcode);
    
    public List<modelChiTietSanPham> findCTSP(String input);
    
}
