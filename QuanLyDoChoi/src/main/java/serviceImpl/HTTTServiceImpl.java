/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import model.HinhThucThanhToan;
import service.HTTTService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;

/**
 *
 * @author ACER
 */
public class HTTTServiceImpl implements HTTTService {
    
    @Override
    public HinhThucThanhToan findHTTTById(Integer idHTTT) {
        String sql = """
                    select * from HinhThucThanhToan where idHTTT=?
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, idHTTT);
            List<HinhThucThanhToan> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                HinhThucThanhToan httt=new HinhThucThanhToan();
                httt.setIdHTTT(rs.getInt(1));
                httt.setMaHTTT(rs.getString(2));
                httt.setTenHTTT(rs.getString(3));
                httt.setCreatedAt(rs.getDate(4));
                httt.setCreatedBy(rs.getInt(5));
                httt.setUpdatedAt(rs.getObject(6, LocalDate.class));
                httt.setUpdatedBy(rs.getInt(7));
                httt.setDeleted(rs.getBoolean(8));
                return httt;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<HinhThucThanhToan> getAll() {
        String sql = """
                    select * from HinhThucThanhToan
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<HinhThucThanhToan> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                HinhThucThanhToan httt=new HinhThucThanhToan();
                httt.setIdHTTT(rs.getInt(1));
                httt.setMaHTTT(rs.getString(2));
                httt.setTenHTTT(rs.getString(3));
                httt.setCreatedAt(rs.getDate(4));
                httt.setCreatedBy(rs.getInt(5));
                httt.setUpdatedAt(rs.getObject(6, LocalDate.class));
                httt.setUpdatedBy(rs.getInt(7));
                httt.setDeleted(rs.getBoolean(8));
                list.add(httt);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public HinhThucThanhToan findHTTTByName(String name) {
        String sql="""
                   select * from HinhThucThanhToan where tenHTTT=?
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, name);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                HinhThucThanhToan httt=new HinhThucThanhToan();
                httt.setIdHTTT(rs.getInt(1));
                httt.setMaHTTT(rs.getString(2));
                httt.setTenHTTT(rs.getString(3));
                httt.setCreatedAt(rs.getDate(4));
                httt.setCreatedBy(rs.getInt(5));
                httt.setUpdatedAt(rs.getObject(6, LocalDate.class));
                httt.setUpdatedBy(rs.getInt(7));
                httt.setDeleted(rs.getBoolean(8));
                return httt;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
}
