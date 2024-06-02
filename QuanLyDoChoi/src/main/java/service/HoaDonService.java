/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.HoaDon;
import model.SanPhamHoan;

/**
 *
 * @author ACER
 */
public interface HoaDonService {
    public List<HoaDon> getAll();
    public List<Object[]> getSPDonHang(String maHoaDon);
    public List<Object[]> hoanHang(String maHoaDon);
    public boolean updateTrangThaiHoanHang(String maHoaDon);
    public List<Object[]> getHoaDon(Integer index);
    public Integer countHD();
    public List<Object[]> getHoaDonChiTiet(String maHoaDon);
    public List<Object[]> getLichSuHoaDon();
    public Object[] getChiTietLSHoaDon(String maHoaDon);
    public List<Object[]> getSanPhamLichSuHoaDon(String maHoaDon);
    public List<Object[]> findHoaDon(Object input);
    public List<Object[]> findHoaDonTheoGia(Double from,Double to);
    public boolean deleteHoaDon(Integer idHoaDon);
    public boolean deleteHoaDonChiTiet(Integer idHoaDon);
    public boolean deleteHoaDonChiTietByID(Integer idCTSP,Integer idHoaDon);
    public List<Object[]> DanhSachHoaDon();
    public HoaDon getHoaDonByMa(String maHoaDon);
    
    public boolean createBill(HoaDon hd);
    
    public HoaDon selectTop1DESC();
    
    public boolean ThanhToanHoaDon(HoaDon hd,String maHD);
    
    public boolean hoanTatBill(String maHD);
    
    public boolean ThanhToanHoaDonShip(HoaDon hd,String maHD);
    
    public boolean deleteHoaDonByMa(String maHoaDon);
    
    public boolean  deleteHoaDonCho();
    
    public boolean huyBill(String maHD);
    
    public List<Object[]> getSPHoan(String maHoaDon);
    
    public boolean addLichSuHoan(SanPhamHoan sph);
    
    public boolean updateSoLuongHDCT(Integer soLuong,Integer idCTSP,Integer idHoaDon);
    
    public int soLuongDonHangCho();
    
    public boolean updateTongTienHang(Double tongTienHang,String maHoaDon);
    
    
}
