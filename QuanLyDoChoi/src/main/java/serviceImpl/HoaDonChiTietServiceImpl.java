/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import model.HoaDonChiTiet;
import service.HoaDonChiTietService;
import java.sql.*;
import jdbc.DBConnect;

/**
 *
 * @author ACER
 */
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService{

    @Override
    public boolean addHDCT(HoaDonChiTiet hdct) {
        String sql="""
                   insert into hoaDonChiTiet(idHoaDon,idSPCT,soLuong) values(?,?,?)
                   """;
        int check=0;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, hdct.getIdHoaDon());
            ps.setObject(2, hdct.getIdSPCT());
            ps.setObject(3, hdct.getSoLuong());
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }
    
}
