/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositoryImpl;

import java.util.List;
import model.ChiTietSanPham;
import repository.ChiTietSanPhamRepo;
import java.sql.*;
import java.util.ArrayList;
import jdbc.DBConnect;
import modelview.modelChiTietSanPham;

/**
 *
 * @author Dell
 */
public class ChiTietSanPhamRepoImpl implements ChiTietSanPhamRepo {

    Connection con;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
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

    @Override
    public int addChiTietSanPham(ChiTietSanPham ctsp) {
//        int soLuongTon, double donGia, String hinhAnh, String barcode, String ghiChu, Date createdAt, int createdBy, Date updatedAt, int updatedBy, boolean delete
        sql = "INSERT INTO chiTietSanPham(idSanPham,idLoaiSanPham,idThuongHieu,idXuatXu,idChatLieu,idDonViTinh,idKhoiLuong,\n"
                + "donGia,barcode,ghiChu,soLuongTon,createdBy,deleted) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getIdSanPham());
            ps.setObject(2, ctsp.getIdLoaiSanPham());
            ps.setObject(3, ctsp.getIdThuongHieu());
            ps.setObject(4, ctsp.getIdXuatXu());
            ps.setObject(5, ctsp.getIdChatLieu());
            ps.setObject(6, ctsp.getIdDonViTinh());
            ps.setObject(7, ctsp.getIdKhoiLuong());
            ps.setObject(8, ctsp.getDonGia());
            ps.setObject(9, ctsp.getBarcode());
            ps.setObject(10, ctsp.getGhiChu());
            ps.setObject(11, ctsp.getSoLuongTon());
            ps.setObject(12, ctsp.getCreatedBy());
            ps.setObject(13, ctsp.isDeleted());
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteChiTietSanPham(int trangThai, String barcode) {
        String sql = """
                   update chiTietSanPham set deleted=? where barcode=?
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, trangThai);
            ps.setObject(2, barcode);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    @Override
    public List<modelChiTietSanPham> getAll() {
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
                          order by chiTietSanPham.idCTSP desc
                   """;
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

    @Override
    public int countSanPham(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietSanPham getBy_Barcode(String barcode) {
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
                                                                                        INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where chiTietSanPham.barcode= ?
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, barcode);
            ResultSet rs = ps.executeQuery();
            modelChiTietSanPham model = null;
            while (rs.next()) {
                model = new modelChiTietSanPham();
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
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public int update(ChiTietSanPham ctsp, String barcode) {
        sql = "UPDATE chiTietSanPham SET idSanPham=?,idLoaiSanPham=?,idThuongHieu=?,idXuatXu=?,idChatLieu=?,idDonViTinh=?,idKhoiLuong=?,donGia=?,\n"
                + "ghiChu=?,soLuongTon=?,createdBy=?,deleted=? where barcode=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ctsp.getIdSanPham());
            ps.setObject(2, ctsp.getIdLoaiSanPham());
            ps.setObject(3, ctsp.getIdThuongHieu());
            ps.setObject(4, ctsp.getIdXuatXu());
            ps.setObject(5, ctsp.getIdChatLieu());
            ps.setObject(6, ctsp.getIdDonViTinh());
            ps.setObject(7, ctsp.getIdKhoiLuong());
            ps.setObject(8, ctsp.getDonGia());
            ps.setObject(13, barcode);
            ps.setObject(9, ctsp.getGhiChu());
            ps.setObject(10, ctsp.getSoLuongTon());
            ps.setObject(11, ctsp.getCreatedBy());
            ps.setObject(12, ctsp.isDeleted());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<modelChiTietSanPham> getAllByInput(String input) {
        String sql = """
                   SELECT 
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
                                                                                                               GETDATE() AS NGAYXUATKHO,chiTietSanPham.deleted FROM chiTietSanPham INNER JOIN SanPham ON chiTietSanPham.idSanPham=SanPham.idSanPham
                                                                                                               INNER JOIN LoaiSanPham ON chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham JOIN XuatXu ON chiTietSanPham.idXuatXu=XuatXu.idXuatXu INNER JOIN KhoiLuong ON chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
                                                                                                               INNER JOIN  DonViTinh ON chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh INNER JOIN ThuongHieu ON chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
                                                                                                               INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu
                                                                                     						  where SanPham.tenSanPham like ? OR LoaiSanPham.tenLoaiSanPham like ? OR ThuongHieu.tenThuongHieu like ? OR XuatXu.tenXuatXu like ? OR
                                                                                                                                                                                 ChatLieu.tenChatLieu like ? OR
                                                                                                                                                                                 DonViTinh.tenDonViTinh like ? OR
                                                                                                                                                                                 KhoiLuong.tenKhoiLuong like ?
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1,input);
            ps.setObject(2,input);
            ps.setObject(3,input);
            ps.setObject(4,input);
            ps.setObject(5,input);
            ps.setObject(6,input);
            ps.setObject(7,input);
            rs = ps.executeQuery();
            modelChiTietSanPham model = null;
            List<modelChiTietSanPham> list = new ArrayList<>();
            while (rs.next()) {
                model = new modelChiTietSanPham();
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

    @Override
    public modelChiTietSanPham getMCTSPByID(Integer idCTSP) {
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
                                             GETDATE() AS NGAYXUATKHO,chiTietSanPham.deleted FROM chiTietSanPham INNER JOIN SanPham ON chiTietSanPham.idSanPham=SanPham.idSanPham
                                             INNER JOIN LoaiSanPham ON chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham JOIN XuatXu ON chiTietSanPham.idXuatXu=XuatXu.idXuatXu INNER JOIN KhoiLuong ON chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
                                             INNER JOIN  DonViTinh ON chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh INNER JOIN ThuongHieu ON chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
                                             INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where chiTietSanPham.idCTSP=?
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idCTSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                return model;
            }
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

    public List<modelChiTietSanPham> getSanPhamDungBan(String ma) {
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
                                                                                        INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where  SanPham.maSanPham=? and chiTietSanPham.deleted=1
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

    @Override
    public List<modelChiTietSanPham> getCTSPBy_TenSP(String maSP, String tenSP) {
        sql = """
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
                                                                                                                                                                                            INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where maSanPham= ? and SanPham.tenSanPham like ?""";
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, maSP);
            ps.setObject(2, tenSP);
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
            return null;
        }
    }

    @Override
    public List<modelChiTietSanPham> SanPhamDangBan() {
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
                                                                                                                                                                                INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where chiTietSanPham.deleted=0  
                   """;
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
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

    @Override
    public List<modelChiTietSanPham> SanPhamDungBan() {
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
                                                                                        INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where chiTietSanPham.deleted=1
                   """;
        List<modelChiTietSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
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

    @Override
    public modelChiTietSanPham getMCTSPByBarcode(String barcode) {
        String sql="""
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
                                           INNER JOIN ChatLieu ON chiTietSanPham.idChatLieu=ChatLieu.idChatLieu where barcode=?
                   """;
        try{
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, barcode);
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
                return model;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
