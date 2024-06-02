/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import model.TrangThaiHoaDon;
import service.TTHDService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;
/**
 *
 * @author ACER
 */
public class TTHDServiceImpl implements TTHDService{

    @Override
    public TrangThaiHoaDon findTTHDById(Integer idTTHD) {
        String sql="""
                   select * from TrangThaiHoaDon where idTTHD=?
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, idTTHD);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                TrangThaiHoaDon tthd=new TrangThaiHoaDon();
                tthd.setIdTTHD(rs.getInt(1));
                tthd.setMaTTHD(rs.getString(2));
                tthd.setTenTTHD(rs.getString(3));
                tthd.setCreatedAt(rs.getDate(4));
                tthd.setCreatedBy(rs.getInt(5));
                tthd.setUpdatedAt(rs.getObject(6, LocalDate.class));
                tthd.setUpdatedBy(rs.getInt(7));
                tthd.setDeleted(rs.getBoolean(8));
                return tthd;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<TrangThaiHoaDon> getAll() {
        String sql="""
                   select * from TrangThaiHoaDon
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            List<TrangThaiHoaDon> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                TrangThaiHoaDon tthd=new TrangThaiHoaDon();
                tthd.setIdTTHD(rs.getInt(1));
                tthd.setMaTTHD(rs.getString(2));
                tthd.setTenTTHD(rs.getString(3));
                tthd.setCreatedAt(rs.getDate(4));
                tthd.setCreatedBy(rs.getInt(5));
                tthd.setUpdatedAt(rs.getObject(6, LocalDate.class));
                tthd.setUpdatedBy(rs.getInt(7));
                tthd.setDeleted(rs.getBoolean(8));
                list.add(tthd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
}
