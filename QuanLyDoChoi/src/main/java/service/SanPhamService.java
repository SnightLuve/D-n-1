/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.SanPham;

/**
 *
 * @author Dell
 */
public interface SanPhamService {
    public List<SanPham> getAll();
    public int addSanPham(SanPham sp);
    public int deleteSanPham(String ma);
    public int updateSanPham(SanPham sp,String ma);
    public List<SanPham> getByName(String name);
    public SanPham getByNameSP(String name);
    public SanPham getByIdSP(Integer idSP);
    public SanPham getByMa(String ma);
    public int countSoLuong(String ma);
    public Integer countSP();
    public SanPham selectTop1DESC();
    
    
}
