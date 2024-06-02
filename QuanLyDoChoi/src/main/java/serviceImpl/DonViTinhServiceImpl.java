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
import model.DonViTinh;

/**
 *
 * @author Dell
 */
public class DonViTinhServiceImpl implements service.DonViTinhService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<DonViTinh> getAll() {
        sql = "SELECT *FROM DonViTinh where deleted=0 order by idDonViTinh desc";
        List<DonViTinh> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DonViTinh dvt = new DonViTinh(rs.getInt(1), rs.getString(2), rs.getString(3),
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
    public int addDonViTinh(DonViTinh dvt) {
        sql = "insert into DonViTinh(maDonViTinh,tenDonViTinh,createdBy,deleted) values(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, dvt.getMaDonViTinh());
            ps.setObject(2, dvt.getTenDonViTinh());

            ps.setObject(3, dvt.getCreatedBy());

            ps.setObject(4, dvt.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteDonViTinh(String ma) {
        sql = "UPDATE DonViTinh SET deleted=1 WHERE maDonViTinh=?";
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
    public int updateDonViTinh(DonViTinh dvt, String ma) {
        sql = "UPDATE DonViTinh SET tenDonViTinh=?,updatedAt=?,updatedBy=? WHERE maDonViTinh=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, dvt.getTenDonViTinh());
            ps.setObject(2, dvt.getUpdatedAt());
            ps.setObject(3, dvt.getUpdatedBy());
            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public DonViTinh getByName(String ten) {
        sql = "SELECT *FROM DonViTinh WHERE tenDonViTinh=?";
        DonViTinh dvt = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ten);
            rs = ps.executeQuery();
            while (rs.next()) {
                dvt = new DonViTinh(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));

            }
            return dvt;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonViTinh selectTop1DESC() {
        String sql="""
                   select top 1 * from DonViTinh order by idDonViTinh desc
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DonViTinh dvt = new DonViTinh(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return dvt;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
