/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.List;
import model.ChiTietSanPham;
import modelview.modelChiTietSanPham;

/**
 *
 * @author Dell
 */
public interface ChiTietSanPhamRepo {

    public List<modelChiTietSanPham> getAll(String ma);

    public List<modelChiTietSanPham> getAll();

    public int addChiTietSanPham(ChiTietSanPham ctsp);

    public int deleteChiTietSanPham(int trangThai, String barcode);
    
    public ChiTietSanPham getBy_Barcode(String barcode);

    public int countSanPham(String ma);
    
    public int update(ChiTietSanPham ctsp,String barcode);
    
    public List<modelChiTietSanPham> getAllByInput(String input);
    
    public modelChiTietSanPham getMCTSPByID(Integer idCTSP);
    
    // 2/12-7h24
    public List<modelChiTietSanPham> getCTSPBy_TenSP(String maSP,String tenSP);
    public List<modelChiTietSanPham> SanPhamDangBan();
    public List<modelChiTietSanPham> SanPhamDungBan();
    
    public modelChiTietSanPham getMCTSPByBarcode(String barcode);
    

}
