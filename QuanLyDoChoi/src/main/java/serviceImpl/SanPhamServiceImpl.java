/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.util.List;
import model.SanPham;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import jdbc.DBConnect;
import service.SanPhamService;

/**
 *
 * @author Dell
 */
public class SanPhamServiceImpl implements SanPhamService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<SanPham> getAll() {
        String SQL = "SELECT *FROM SANPHAM WHERE deleted=0 order by idSanPham desc";
        List<SanPham> listSp = new ArrayList<>();
        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                listSp.add(sp);
            }
            return listSp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addSanPham(SanPham sp) {
        sql = "INSERT INTO SanPham(maSanPham,tenSanPham,createdBy,deleted) values(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getMaSanPham());
            ps.setObject(2, sp.getTenSanPham());
            ps.setObject(3, sp.getCreatedBy());
            ps.setObject(4, sp.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public int deleteSanPham(String ma) {
        sql = "UPDATE SanPham SET deleted=1 WHERE maSanPham=?";
        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateSanPham(SanPham sp, String ma) {
        sql = "UPDATE SanPham SET tenSanPham=?,updatedAt=?,updatedBy=? WHERE maSanPham=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, sp.getTenSanPham());

            ps.setObject(2, sp.getUpdatedAt());
            ps.setObject(3, sp.getUpdatedBy());

            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<SanPham> getByName(String name) {
        sql = "Select *from SanPham where maSanPham like ?";
        List<SanPham> listSp = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                listSp.add(sp);
            }
            return listSp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SanPham getByNameSP(String name) {
        sql = "Select *from SanPham where tenSanPham =?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SanPham getByIdSP(Integer idSP) {
        sql = "Select *from SanPham where idSanPham =?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, idSP);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SanPham getByMa(String ma) {
        sql = "Select *from SanPham where maSanPham =?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countSoLuong(String ma) {
        String sql = "SELECT SUM(soLuongTon) AS soluong FROM chiTietSanPham JOIN SanPham ON chiTietSanPham.idSanPham = SanPham.idSanPham WHERE SanPham.maSanPham=?";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ma);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("soluong");
                }
            }
        } catch (Exception e) {
            // Log the exception or print the stack trace for debugging
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public Integer countSP() {
        String sql = "Select count(*) from sanpham";
        int count = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return count;
    }

    @Override
    public SanPham selectTop1DESC() {
        String sql="""
                   select top 1 * from SanPham order by idSanPham desc
                   """;
        try {
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return sp;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
