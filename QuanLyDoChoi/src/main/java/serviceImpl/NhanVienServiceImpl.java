/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import model.NhanVien;
import service.NhanVienService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;

/**
 *
 * @author ACER
 */
public class NhanVienServiceImpl implements NhanVienService{

    @Override
    public NhanVien findNVByMa(String maNhanVien,String matKhau) {
        String sql="""
                   select * from NhanVien where maNhanVien=? and matKhau=?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, maNhanVien);
            ps.setObject(2, matKhau);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean addNV(NhanVien nv) {
        String sql="""
                   INSERT INTO [dbo].[NhanVien]
                              ([maNhanVien]
                              ,[tenNhanVien]
                              ,[gioiTinh]
                              ,[ngaySinh]
                              ,[diaChi]
                              ,[sdt]
                              ,[CCCD]
                              ,[email]
                              ,[trangThai]
                              ,[chucVu]
                              ,[matKhau])
                        VALUES
                              (?,?,?,?,?,?,?,?,?,?,?)
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, nv.getMaNhanVien());
            ps.setObject(2, nv.getTenNhanVien());
            ps.setObject(3, nv.isGioiTinh());
            ps.setObject(4, nv.getNgaySinh());
            ps.setObject(5, nv.getDiaChi());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getCccd());
            ps.setObject(8, nv.getEmail());
            ps.setObject(9, nv.isTrangThai());
            ps.setObject(10, nv.isChucVu());
            ps.setObject(11, nv.getMatKhau());
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public boolean updateNV(NhanVien nv, String maNV) {
        String sql="""
                   UPDATE [dbo].[NhanVien]
                      SET [maNhanVien] = ?
                         ,[tenNhanVien] = ?
                         ,[gioiTinh] = ?
                         ,[ngaySinh] = ?
                         ,[diaChi] = ?
                         ,[sdt] = ?
                         ,[CCCD] = ?
                         ,[email] = ?
                         ,[trangThai] = ?
                         ,[chucVu] = ?
                    WHERE [maNhanVien]=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, nv.getMaNhanVien());
            ps.setObject(2, nv.getTenNhanVien());
            ps.setObject(3, nv.isGioiTinh());
            ps.setObject(4, nv.getNgaySinh());
            ps.setObject(5, nv.getDiaChi());
            ps.setObject(6, nv.getSdt());
            ps.setObject(7, nv.getCccd());
            ps.setObject(8, nv.getEmail());
            ps.setObject(9, nv.isTrangThai());
            ps.setObject(10, nv.isChucVu());
            ps.setObject(11, maNV);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public boolean xoaNV(String maNV) {
         String sql="""
                    Delete NhanVien where maNhanVien=?
                    """;
         int check=0;
         try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, maNV);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
         return check>0;
    }

    @Override
    public List<NhanVien> getAll() {
        String sql="""
                   select * from NhanVien
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            List<NhanVien> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public NhanVien findNVByMaNV(String maNhanVien) {
        String sql="""
                   select * from NhanVien where maNhanVien=?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, maNhanVien);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean updateTrangThai(boolean trangThai, String maNhanVien) {
        String sql="""
                   update NhanVien set trangThai=? where maNhanVien=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, trangThai);
            ps.setObject(2, maNhanVien);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public List<NhanVien> findNV(Object input) {
        String sql="""
                   select * from NhanVien where maNhanVien like ? or tenNhanVien like ? or diaChi like ? or sdt like ? or CCCD like ? or email like ?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1,"%"+input+"%");
            ps.setObject(2, "%"+input+"%");
            ps.setObject(3,"%"+input+"%");
            ps.setObject(4, "%"+input+"%");
            ps.setObject(5,"%"+input+"%");
            ps.setObject(6, "%"+input+"%");
            List<NhanVien> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public NhanVien findNVByCCCD(String cccd) {
         String sql="""
                   select * from NhanVien where CCCD = ?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, cccd);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<NhanVien> findByCCCD(String cccd) {
         String sql="""
                   select * from NhanVien where CCCD = ?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, cccd);
            List<NhanVien> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public NhanVien findNVByID(Integer id) {
        String sql="""
                   select * from NhanVien where idNhanVien = ?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, id);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                NhanVien nv=new NhanVien();
                nv.setIdNhanVien(rs.getInt(1));
                nv.setMaNhanVien(rs.getString(2));
                nv.setTenNhanVien(rs.getString(3));
                nv.setGioiTinh(rs.getBoolean(4));
                nv.setNgaySinh(rs.getDate(5));
                nv.setDiaChi(rs.getString(6));
                nv.setSdt(rs.getString(7));
                nv.setCccd(rs.getString(8));
                nv.setEmail(rs.getString(9));
                nv.setTrangThai(rs.getBoolean(10));
                nv.setChucVu(rs.getBoolean(11));
                nv.setMatKhau(rs.getString(12));
                return nv;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
}
