/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import service.ChiTietSanPhamService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;
import model.ChiTietSanPham;
import modelview.modelChiTietSanPham;

/**
 *
 * @author ACER
 */
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    Connection con;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
    public boolean updateHoanHang(Integer soLuong, Integer idCTSP) {
        String sql = """
                   update chiTietSanPham set soLuongTon=? where idCTSP=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public ChiTietSanPham getCTSPByID(Integer idCTSP) {
        String sql = """
                    select * from chiTietSanPham where idCTSP=?
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idCTSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setIdCTSP(rs.getInt(1));
                ctsp.setIdSanPham(rs.getInt(2));
                ctsp.setIdThuongHieu(rs.getInt(3));
                ctsp.setIdKhoiLuong(rs.getInt(4));
                ctsp.setIdDonViTinh(rs.getInt(5));
                ctsp.setIdXuatXu(rs.getInt(6));
                ctsp.setIdChatLieu(rs.getInt(7));
                ctsp.setIdLoaiSanPham(rs.getInt(8));
                ctsp.setSoLuongTon(rs.getInt(9));
                ctsp.setDonGia(rs.getDouble(10));
                ctsp.setBarcode(rs.getString(11));
                ctsp.setGhiChu(rs.getString(12));
                ctsp.setCreatedAt(rs.getDate(13));
                ctsp.setCreatedBy(rs.getInt(14));
                ctsp.setUpdatedAt(rs.getObject(15, LocalDate.class));
                ctsp.setUpdatedBy(rs.getInt(16));
                ctsp.setDeleted(rs.getBoolean(17));
                return ctsp;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean updateSoLuongCTSP(int soLuong, int idCTSP) {
        String sql = """
                   update chiTietSanPham set soLuongTon=? where idCTSP=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public ChiTietSanPham getCTSPByBarcode(String barcode) {
        String sql = """
                    select * from chiTietSanPham where barcode=?
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, barcode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setIdCTSP(rs.getInt(1));
                ctsp.setIdSanPham(rs.getInt(2));
                ctsp.setIdThuongHieu(rs.getInt(3));
                ctsp.setIdKhoiLuong(rs.getInt(4));
                ctsp.setIdDonViTinh(rs.getInt(5));
                ctsp.setIdXuatXu(rs.getInt(6));
                ctsp.setIdChatLieu(rs.getInt(7));
                ctsp.setIdLoaiSanPham(rs.getInt(8));
                ctsp.setSoLuongTon(rs.getInt(9));
                ctsp.setDonGia(rs.getDouble(10));
                ctsp.setBarcode(rs.getString(11));
                ctsp.setGhiChu(rs.getString(12));
                ctsp.setCreatedAt(rs.getDate(13));
                ctsp.setCreatedBy(rs.getInt(14));
                ctsp.setUpdatedAt(rs.getObject(15, LocalDate.class));
                ctsp.setUpdatedBy(rs.getInt(16));
                ctsp.setDeleted(rs.getBoolean(17));
                return ctsp;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<modelChiTietSanPham> findCTSP(String input) {
        String sql = """
                   SELECT chiTietSanPham.idCTSP,
                                             SanPham.maSanPham,
                                             SanPham.tenSanPham,
                                             LoaiSanPham.tenLoaiSanPham,
                                             ThuongHieu.tenThuongHieu,
                                             XuatXu.tenXuatXu,
                                             ChatLieu.tenChatLieu,
                                             DonViTinh.tenDonViTinh,
                                             KhoiLuong.tenKhoiLuong,
                                             chiTietSanPham.donGia,
                                             chiTietSanPham.barcode,
                                             chiTietSanPham.ghiChu,
                                             chiTietSanPham.soLuongTon,
                                             GETDATE() AS NGAYXUATKHO,chiTietSanPham.deleted FROM chiTietSanPham JOIN SanPham ON chiTietSanPham.idSanPham=SanPham.idSanPham
                                             JOIN LoaiSanPham ON chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham JOIN XuatXu ON chiTietSanPham.idXuatXu=XuatXu.idXuatXu JOIN KhoiLuong ON chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
                                             JOIN  DonViTinh ON chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh JOIN ThuongHieu ON chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
                                             JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu
                   						  where maSanPham like ? or tenSanPham like ? or tenLoaiSanPham like ? or tenThuongHieu like ? or tenXuatXu like ? or tenChatLieu like ? or tenDonViTinh like ? or
                   						  tenKhoiLuong like ?
                                             order by maSanPham asc
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
           ps.setObject(1, "%"+input+"%");
           ps.setObject(2, "%"+input+"%");
           ps.setObject(3, "%"+input+"%");
           ps.setObject(4, "%"+input+"%");
           ps.setObject(5, "%"+input+"%");
           ps.setObject(6, "%"+input+"%");
           ps.setObject(7, "%"+input+"%");
           ps.setObject(8, "%"+input+"%");
           ResultSet rs=ps.executeQuery();
           List<modelChiTietSanPham> list=new ArrayList<>();
           while(rs.next()){
               modelChiTietSanPham model = new modelChiTietSanPham();
                model.setIdCTSP(rs.getInt(1));
                model.setMaSp(rs.getString(2));
                model.setTenSanPham(rs.getString(3));
                model.setTenLoaiSanPham(rs.getString(4));
                model.setTenThuongHieu(rs.getString(5));
                model.setTenXuatXu(rs.getString(6));
                model.setTenChatLieu(rs.getString(7));
                model.setTenDonVitinh(rs.getString(8));
                model.setTenKhoiLuong(rs.getString(9));
                model.setDonGia(rs.getDouble(10));
                model.setBarcode(rs.getString(11));
                model.setGhiChu(rs.getString(12));
                model.setSoLuongTon(rs.getInt(13));
                model.setNgayXuatKho(rs.getDate(14));
                model.setDeleted(rs.getBoolean(15));
                list.add(model);
           }
           return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<modelChiTietSanPham> getSanPhamDangBan(String ma) {
        String sql = """
                   SELECT SanPham.maSanPham,
                                                                                                                                                             SanPham.tenSanPham,
                                                                                                                                                             LoaiSanPham.tenLoaiSanPham,
                                                                                                                                                             ThuongHieu.tenThuongHieu,
                                                                                                                                                             XuatXu.tenXuatXu,
                                                                                                                                                             ChatLieu.tenChatLieu,
                                                                                                                                                             DonViTinh.tenDonViTinh,
                                                                                                                                                             KhoiLuong.tenKhoiLuong,
                                                                                                                                                             chiTietSanPham.donGia,
                                                                                                                                                             chiTietSanPham.barcode,
                                                                                                                                                             chiTietSanPham.ghiChu,
                                                                                                                                                             chiTietSanPham.soLuongTon,
                                                                                                                                                             GETDATE() AS NGAYXUATKHO,chiTietSanPham.deleted FROM chiTietSanPham INNER JOIN SanPham ON chiTietSanPham.idSanPham=SanPham.idSanPham
                                                                                                                                                                                 INNER JOIN LoaiSanPham ON chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham JOIN XuatXu ON chiTietSanPham.idXuatXu=XuatXu.idXuatXu INNER JOIN KhoiLuong ON chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
                                                                                                                                                                                INNER JOIN  DonViTinh ON chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh INNER JOIN ThuongHieu ON chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
                                                                                                                                                                                INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where SanPham.maSanPham= ? and chiTietSanPham.deleted=0  
                   """;
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelChiTietSanPham model = new modelChiTietSanPham();
                model.setMaSp(rs.getString(1));
                model.setTenSanPham(rs.getString(2));
                model.setTenLoaiSanPham(rs.getString(3));
                model.setTenThuongHieu(rs.getString(4));
                model.setTenXuatXu(rs.getString(5));
                model.setTenChatLieu(rs.getString(6));
                model.setTenDonVitinh(rs.getString(7));
                model.setTenKhoiLuong(rs.getString(8));
                model.setDonGia(rs.getDouble(9));
                model.setBarcode(rs.getString(10));
                model.setGhiChu(rs.getString(11));
                model.setSoLuongTon(rs.getInt(12));
                model.setNgayXuatKho(rs.getDate(13));
                model.setDeleted(rs.getBoolean(14));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<modelChiTietSanPham> getAll(String ma) {
        String sql = """
                  SELECT SanPham.maSanPham,
                                                                                                                                                             SanPham.tenSanPham,
                                                                                                                                                             LoaiSanPham.tenLoaiSanPham,
                                                                                                                                                             ThuongHieu.tenThuongHieu,
                                                                                                                                                             XuatXu.tenXuatXu,
                                                                                                                                                             ChatLieu.tenChatLieu,
                                                                                                                                                             DonViTinh.tenDonViTinh,
                                                                                                                                                             KhoiLuong.tenKhoiLuong,
                                                                                                                                                             chiTietSanPham.donGia,
                                                                                                                                                             chiTietSanPham.barcode,
                                                                                                                                                             chiTietSanPham.ghiChu,
                                                                                                                                                             chiTietSanPham.soLuongTon,
                                                                                                                                                             GETDATE() AS NGAYXUATKHO,chiTietSanPham.deleted FROM chiTietSanPham INNER JOIN SanPham ON chiTietSanPham.idSanPham=SanPham.idSanPham
                                                                                                                                                                                 INNER JOIN LoaiSanPham ON chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham JOIN XuatXu ON chiTietSanPham.idXuatXu=XuatXu.idXuatXu INNER JOIN KhoiLuong ON chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
                                                                                                                                                                                INNER JOIN  DonViTinh ON chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh INNER JOIN ThuongHieu ON chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
                                                                                                                                                                                INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where maSanPham= ?
                   """;
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelChiTietSanPham model = new modelChiTietSanPham();
                model.setMaSp(rs.getString(1));
                model.setTenSanPham(rs.getString(2));
                model.setTenLoaiSanPham(rs.getString(3));
                model.setTenThuongHieu(rs.getString(4));
                model.setTenXuatXu(rs.getString(5));
                model.setTenChatLieu(rs.getString(6));
                model.setTenDonVitinh(rs.getString(7));
                model.setTenKhoiLuong(rs.getString(8));
                model.setDonGia(rs.getDouble(9));
                model.setBarcode(rs.getString(10));
                model.setGhiChu(rs.getString(11));
                model.setSoLuongTon(rs.getInt(12));
                model.setNgayXuatKho(rs.getDate(13));
                model.setDeleted(rs.getBoolean(14));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
