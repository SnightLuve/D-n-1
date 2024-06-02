/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;
import model.KhoiLuong;
import service.KhoiLuongService;

/**
 *
 * @author Dell
 */
public class KhoiLuongServiceImpl implements KhoiLuongService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<KhoiLuong> getAll() {
        sql = "SELECT *FROM KhoiLuong where deleted=0 order by idKhoiLuong desc";
        List<KhoiLuong> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhoiLuong dvt = new KhoiLuong(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                list.add(dvt);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addKhoiLuong(KhoiLuong kl) {
        sql = "INSERT INTO KhoiLuong(maKhoiLuong,tenKhoiLuong,createdBy,deleted) values(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kl.getMaKhoiLuong());
            ps.setObject(2, kl.getTenKhoiLuong());

            ps.setObject(3, kl.getCreatedBy());

            ps.setObject(4, kl.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteKhoiLuong(String ma) {
        sql = "	UPDATE KhoiLuong SET deleted=1 WHERE maKhoiLuong=?";
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
    public int updateKhoiLuong(KhoiLuong kl, String ma) {
        sql = "UPDATE KhoiLuong SET tenKhoiLuong=?,createdAt=?,updatedBy=? WHERE maKhoiLuong=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, kl.getTenKhoiLuong());

            ps.setObject(2, kl.getUpdatedAt());
            ps.setObject(3, kl.getUpdatedBy());

            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public KhoiLuong getKhoiLuong(String ma) {
        sql = "SELECT *FROM KhoiLuong";
        KhoiLuong kl = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);
            rs = ps.executeQuery();
            while (rs.next()) {
                kl = new KhoiLuong(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));

            }
            return kl;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KhoiLuong getByName(String name) {
        sql = "SELECT *FROM KhoiLuong WHERE TENKHOILUONG=?";
        KhoiLuong kl = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                kl = new KhoiLuong(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));

            }
            return kl;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KhoiLuong selectTop1DESC() {
        String sql="""
                   select top 1 * from KhoiLuong order by idKhoiLuong desc
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhoiLuong kl = new KhoiLuong(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return kl;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
