/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import model.ThongKe;

/**
 *
 * @author ACER
 */
public interface ThongKeService {
    public double getDoanhThuCaNam(int year);
    
    public double getDoanhThuToDay(LocalDate toDay);
    
    public double getDoanhThu7NgayGanNhat();
    
    public double getDoanhThuThangNay(String firstDayInMonth,LocalDate toDay);
    
    public List<Object[]> LocDoanhThuTheoNgay(String from,String to);
    
    public List<Object[]> DoanhThuSanPham();
    
    public List<Object[]> DoanhThuSanPhamTheoLoaiSP(String tenLoaiSP);
    
    public Object[] DoanhThuSanPhamNhieuNhat();
    
    public Object[] DoanhThuSanPhamItNhat();
    
    public Object[] SanPhamBanNhieuNhat();
    
    public Object[] SanPhamBanItNhat();
    
    public int soSanPhamDangKinhDoanh();
    
    public int soSanPhamHetHang();
    
    public int soSanPhamSapHetHang();
    
    public int soSanPhamNgungKinhDoanh();
    
    public ThongKe ThongKeChart(int month,int year);
    
    public List<Object[]> listYear();
    
    public List<Object[]> listMonth();
}
