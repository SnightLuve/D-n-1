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
import model.LoaiSanPham;
import service.LoaiSanPhamService;

/**
 *
 * @author Dell
 */
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<LoaiSanPham> getAll() {
        sql = "SELECT *FROM LoaiSanPham WHERE deleted=0 order by idLoaiSanPham desc";
        List<LoaiSanPham> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSanPham lsp = new LoaiSanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );
                list.add(lsp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addLoaiSanPham(LoaiSanPham lsp) {
        sql = "INSERT INTO LoaiSanPham(maLoaiSanPham,tenLoaiSanPham,createdBy,deleted) VALUES(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, lsp.getMaLoaiSanPham());
            ps.setObject(2, lsp.getTenLoaiSanPham());
            ps.setObject(3, lsp.getCreatedBy());
            ps.setObject(4, lsp.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteLoaiSanPham(String ma) {
        sql = "UPDATE LOAISANPHAM SET deleted=1 WHERE maLoaiSanPham=?";
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
    public int updateLoaiSanPham(LoaiSanPham lsp, String ma) {
        sql = "UPDATE LoaiSanPham set tenLoaiSanPham=?,updatedAt=?,updatedBy=? WHERE maLoaiSanPham=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, lsp.getTenLoaiSanPham());

            ps.setObject(2, lsp.getUpdatedAt());
            ps.setObject(3, lsp.getUpdatedBy());

            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public LoaiSanPham getByName(String name) {
        sql = "select *from LoaiSanPham where tenLoaiSanPham=? ";
        LoaiSanPham lsp = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                lsp = new LoaiSanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );

            }
            return lsp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoaiSanPham selectTop1DESC() {
        String sql="""
                   select top 1 * from LoaiSanPham order by idLoaiSanPham desc
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSanPham lsp = new LoaiSanPham(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );
                return lsp;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
