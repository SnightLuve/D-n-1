/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.LoaiSanPham;

/**
 *
 * @author Dell
 */
public interface LoaiSanPhamService {

    public List<LoaiSanPham> getAll();

    public int addLoaiSanPham(LoaiSanPham lsp);

    public int deleteLoaiSanPham(String ma);

    public int updateLoaiSanPham(LoaiSanPham lsp, String ma);

    public LoaiSanPham getByName(String name);
    
    public LoaiSanPham selectTop1DESC();
}
