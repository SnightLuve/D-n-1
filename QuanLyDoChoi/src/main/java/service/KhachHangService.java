/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.List;
import model.KhachHang;

/**
 *
 * @author ACER
 */
public interface KhachHangService {
    public KhachHang findKHByID(Integer idKhachHang);
    public KhachHang findKHByMa(String maKhachHang);
    public List<KhachHang> getAll();
    public int addKhachHang(KhachHang kh);
    public int deleteKhachHang(String ma);
    public int updateKhachHang(KhachHang kh,String ma);
    public List<KhachHang> findKhachHang(Object input); 
    public List<Object[]> KhachHangBought(String maKH);
    public KhachHang selectTop1DESC();
    public KhachHang findKHByTen(String tenKhachHang);
}
