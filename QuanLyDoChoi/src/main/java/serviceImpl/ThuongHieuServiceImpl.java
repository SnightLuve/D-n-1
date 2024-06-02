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
import model.ThuongHieu;
import service.ThuongHieuService;

/**
 *
 * @author Dell
 */
public class ThuongHieuServiceImpl implements ThuongHieuService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<ThuongHieu> getAll() {
        sql = "SELECT *FROM ThuongHieu WHERE deleted=0 order by idThuongHieu desc";
        List<ThuongHieu> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThuongHieu thuongHieu = new ThuongHieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                list.add(thuongHieu);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addThuongHieu(ThuongHieu th) {
        sql = "INSERT INTO ThuongHieu(maThuongHieu,tenThuongHieu,createdBy,deleted) values(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, th.getMaThuongHieu());
            ps.setObject(2, th.getTenThuongHieu());

            ps.setObject(3, th.getCreatedBy());

            ps.setObject(4, th.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteThuongHieu(String ma) {
        sql = "	UPDATE ThuongHieu SET deleted=1 WHERE maThuongHieu=?";
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
    public int updateThuongHieu(ThuongHieu th, String ma) {
        sql = "UPDATE ThuongHieu SET tenThuongHieu=?,updatedAt=?,updatedBy=? WHERE maThuongHieu=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, th.getTenThuongHieu());

            ps.setObject(2, th.getUpdatedAt());
            ps.setObject(3, th.getUpdatedBy());

            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ThuongHieu getThuongHieu(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ThuongHieu getByName(String name) {
        sql = "select *from ThuongHieu where tenThuongHieu=?";
        ThuongHieu th = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                th = new ThuongHieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));

            }
            return th;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ThuongHieu selectTop1DESC() {
        String sql="""
                   select top 1 * from ThuongHieu order by idThuongHieu desc
                   """;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThuongHieu thuongHieu = new ThuongHieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return thuongHieu;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
}
