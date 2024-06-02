/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import model.KhachHang;
import service.KhachHangService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;

/**
 *
 * @author ACER
 */
public class KhachHangServiceImpl implements KhachHangService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
    public KhachHang findKHByID(Integer idKhachHang) {
        String sql = """
                   select * from KhachHang where idKhachHang=?
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idKhachHang);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setEmail(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setNgayHetHan(rs.getObject(7, LocalDate.class));
                kh.setCreatedAt(rs.getDate(8));
                kh.setCreatedBy(rs.getInt(9));
                kh.setUpdatedAt(rs.getObject(10, LocalDate.class));
                kh.setUpdatedBy(rs.getInt(11));
                kh.setDeleted(rs.getBoolean(12));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        sql = "SELECT *FROM KHACHHANG WHERE DELETED=0 ";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6),
                        rs.getObject(7, LocalDate.class), rs.getDate(8), rs.getInt(9), rs.getObject(10, LocalDate.class), rs.getInt(11), rs.getBoolean(12));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addKhachHang(KhachHang kh) {
        sql = "INSERT INTO KhachHang(maKhachHang,tenKhachHang,email,diaChi,sdt,ngayHetHan,createdBy,deleted)  Values(?,?,?,?,?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getMaKhachHang());
            ps.setObject(2, kh.getTenKhachHang());
            ps.setObject(3, kh.getEmail());
            ps.setObject(4, kh.getDiaChi());
            ps.setObject(5, kh.getSdt());
            ps.setObject(6, kh.getNgayHetHan());
            ps.setObject(7, kh.getCreatedBy());
            ps.setObject(8, kh.getDeleted());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteKhachHang(String ma) {
        sql = "UPDATE KHACHHANG SET DELETED=1 WHERE MAKHACHHANG=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateKhachHang(KhachHang kh, String ma) {
        sql = "update KhachHang set tenKhachHang=?,email=?,diaChi=?,sdt=?,updatedAt=?,updatedBy=? where maKhachHang=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getTenKhachHang());
            ps.setObject(2, kh.getEmail());
            ps.setObject(3, kh.getDiaChi());
            ps.setObject(4, kh.getSdt());
            ps.setObject(5, kh.getUpdatedAt());
            ps.setObject(6, kh.getUpdatedBy());
            ps.setObject(7, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<KhachHang> findKhachHang(Object input) {
        sql = "select * from KhachHang where maKhachHang like ? or tenKhachHang like ? or email like ? or diaChi like ? or sdt like ?";
        List<KhachHang> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, "%" + input + "%");
            ps.setObject(2, "%" + input + "%");
            ps.setObject(3, "%" + input + "%");
            ps.setObject(4, "%" + input + "%");
            ps.setObject(5, "%" + input + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getInt(1), rs.
                        getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5),
                        rs.getString(6),
                        rs.getObject(7, LocalDate.class), rs.getDate(8), rs.getInt(9), rs.getObject(10, LocalDate.class), rs.getInt(11), rs.getBoolean(12));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KhachHang findKHByMa(String maKhachHang) {
        String sql = """
                   select * from KhachHang where maKhachHang=?
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maKhachHang);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setEmail(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setNgayHetHan(rs.getObject(7, LocalDate.class));
                kh.setCreatedAt(rs.getDate(8));
                kh.setCreatedBy(rs.getInt(9));
                kh.setUpdatedAt(rs.getObject(10, LocalDate.class));
                kh.setUpdatedBy(rs.getInt(11));
                kh.setDeleted(rs.getBoolean(12));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> KhachHangBought(String maKH) {
        String sql = """
                   {CALL pr_KhachHangBought(?)}
                   """;
        String cols[] = {"maSanPham", "tenSanPham", "donGia", "soLuong", "tongTien"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maKH);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public KhachHang selectTop1DESC() {
        String sql = """
                   select top 1 * from KhachHang order by idKhachHang desc
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setEmail(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setNgayHetHan(rs.getObject(7, LocalDate.class));
                kh.setCreatedAt(rs.getDate(8));
                kh.setCreatedBy(rs.getInt(9));
                kh.setUpdatedAt(rs.getObject(10, LocalDate.class));
                kh.setUpdatedBy(rs.getInt(11));
                kh.setDeleted(rs.getBoolean(12));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public KhachHang findKHByTen(String tenKhachHang) {
        String sql = """
                   select * from KhachHang where tenKhachHang=?
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tenKhachHang);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setIdKhachHang(rs.getInt(1));
                kh.setMaKhachHang(rs.getString(2));
                kh.setTenKhachHang(rs.getString(3));
                kh.setEmail(rs.getString(4));
                kh.setDiaChi(rs.getString(5));
                kh.setSdt(rs.getString(6));
                kh.setNgayHetHan(rs.getObject(7, LocalDate.class));
                kh.setCreatedAt(rs.getDate(8));
                kh.setCreatedBy(rs.getInt(9));
                kh.setUpdatedAt(rs.getObject(10, LocalDate.class));
                kh.setUpdatedBy(rs.getInt(11));
                kh.setDeleted(rs.getBoolean(12));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
