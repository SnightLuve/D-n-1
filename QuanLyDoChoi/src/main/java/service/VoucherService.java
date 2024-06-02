/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.List;
import model.Voucher;

/**
 *
 * @author ACER
 */
public interface VoucherService {
    public Voucher selectTop1DESC();
    public List<Voucher> getAll();
    public boolean addVoucher(Voucher vc);
    public Voucher findVCByMa(String maVC);
    public void updateTrangThaiVoucherDayByDay(LocalDate date);
    public boolean updateVoucher(String maVoucher,Voucher vc);
    public boolean trangThaiVoucher(int deleted,String maVoucher);
    public boolean deleteVoucher(String maVoucher);
    public List<Voucher> findVCByMa1(Object input);
    public List<Voucher> findVCByLoai(boolean loaiVC);
    public List<Voucher> findVCByTrangThai(int trangThai);
    public Voucher findVoucherByID(Integer idVoucher);
    public boolean updateSoLuongVoucher(Integer soLuong,Integer idVoucher);
}
