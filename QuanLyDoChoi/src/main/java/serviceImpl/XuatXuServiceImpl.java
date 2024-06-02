/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.util.List;
import model.XuatXu;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import jdbc.DBConnect;
import service.XuatXuService;

/**
 *
 * @author Dell
 */
public class XuatXuServiceImpl implements XuatXuService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<XuatXu> getAll() {
        sql = "SELECT *FROM XUATXU WHERE deleted=0 order by idXuatXu desc";
        List<XuatXu> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                XuatXu xx = new XuatXu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );
                list.add(xx);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addXuatXu(XuatXu xx) {
        sql = "INSERT INTO XUATXU(maXuatXu,tenXuatXu,createdBy,deleted) VALUES(?,?,?,?)";
        try {

            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, xx.getMaXuatXu());
            ps.setObject(2, xx.getTenXuatXu());

            ps.setObject(3, xx.getCreatedBy());

            ps.setObject(4, xx.isDeleted());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteXuatXu(String ma) {
        sql = "UPDATE XuatXu SET deleted=1 WHERE maXuatXu=?";
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
    public int updateXuatXu(XuatXu xx, String ma) {
        sql = "UPDATE XuatXu SET tenXuatXu=?,updatedAt=?,updatedBy=? WHERE maXuatXu=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, xx.getTenXuatXu());

            ps.setObject(2, xx.getUpdatedAt());
            ps.setObject(3, xx.getUpdatedBy());

            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public XuatXu getByName(String name) {
        sql = "SELECT *FROM XuatXu where tenXuatXu=?";
        XuatXu xx = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                xx = new XuatXu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );

            }
            return xx;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public XuatXu selectTop1DESC() {
        String sql="""
                   select top 1 * from XuatXu order by idXuatXu desc
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                XuatXu xx = new XuatXu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8)
                );
                return xx;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
