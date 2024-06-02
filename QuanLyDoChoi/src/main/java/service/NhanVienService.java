/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.NhanVien;

/**
 *
 * @author ACER
 */
public interface NhanVienService {
    public List<NhanVien> getAll();
    public boolean updateTrangThai(boolean trangThai,String maNhanVien);
    public NhanVien findNVByMa(String maNhanVien,String matKhau);
    public NhanVien findNVByMaNV(String maNhanVien);
    public boolean addNV(NhanVien nv);
    public boolean updateNV(NhanVien nv,String maNV);
    public boolean xoaNV(String maNV);
    public List<NhanVien> findNV(Object input);
    public NhanVien findNVByCCCD(String cccd);
    public List<NhanVien> findByCCCD(String cccd);
    public NhanVien findNVByID(Integer id);
}
