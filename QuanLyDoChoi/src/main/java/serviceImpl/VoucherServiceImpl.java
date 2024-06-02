/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.util.List;
import model.Voucher;
import service.VoucherService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import jdbc.DBConnect;

/**
 *
 * @author ACER
 */
public class VoucherServiceImpl implements VoucherService{

    @Override
    public List<Voucher> getAll() {
         String sql="""
                    select * from Voucher where deleted=0 order by idVoucher desc;
                    """;
         try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            List<Voucher> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                list.add(vc);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean addVoucher(Voucher vc) {
        String sql="""
                   insert into Voucher(maVoucher,tenVoucher,loaiVoucher,giaTri,giaTriToiThieu,giaTriToiDa,soLuong,ngayBatDau,ngayKetThuc,trangThai,createdBy,deleted)
                   values(?,?,?,?,?,?,?,?,?,?,?,?)
                   """;
        int check=0;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, vc.getMaVoucher());
            ps.setObject(2, vc.getTenVoucher());
            ps.setObject(3, vc.isLoaiVoucher());
            ps.setObject(4, vc.getGiaTri());
            ps.setObject(5, vc.getGiaTriToiThieu());
            ps.setObject(6, vc.getGiaTriToiDa());
            ps.setObject(7, vc.getSoLuong());
            ps.setObject(8, vc.getNgayBatDau());
            ps.setObject(9, vc.getNgayKetThuc());
            ps.setObject(10, vc.getTrangThai());
            ps.setObject(11, vc.getCreatedBy());
            ps.setObject(12, vc.isDelete());
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public Voucher findVCByMa(String maVC) {
        String sql="""
                    select * from Voucher where maVoucher=?;
                    """;
         try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
             ps.setObject(1, maVC);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                return vc;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public void updateTrangThaiVoucherDayByDay(LocalDate date) {
        String sql="""
                   update Voucher set trangThai=case when ngayBatDau<=? and ngayKetThuc>=? then 1
                   				     when ?>ngayKetThuc then 2
                   				     when ?< ngayBatDau then 3  
                   end
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, date);
            ps.setObject(2, date);
            ps.setObject(3, date);
            ps.setObject(4, date);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public boolean updateVoucher(String maVoucher, Voucher vc) {
        String sql="""
                   update Voucher set tenVoucher=?,giaTriToiThieu=?,giaTriToiDa=?,trangThai=?,loaiVoucher=?,giaTri=?,ngayBatDau=?,ngayKetThuc=?,updatedAt=?,updatedBy=?,soLuong=? where maVoucher=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, vc.getTenVoucher());
            ps.setObject(2, vc.getGiaTriToiThieu());
            ps.setObject(3, vc.getGiaTriToiDa());
            ps.setObject(4, vc.getTrangThai());
            ps.setObject(5,vc.isLoaiVoucher());
            ps.setObject(6,vc.getGiaTri());
            ps.setObject(7,vc.getNgayBatDau());
            ps.setObject(8,vc.getNgayKetThuc());
            ps.setObject(9,vc.getUpdatedAt());
            ps.setObject(10,vc.getUpdatedBy());
            ps.setObject(11,vc.getSoLuong());
            ps.setObject(12,maVoucher);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public boolean trangThaiVoucher(int trangThai,String maVoucher) {
        String sql="""
                   update Voucher set trangThai=? where maVoucher=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, trangThai);
            ps.setObject(2, maVoucher);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public List<Voucher> findVCByMa1(Object input) {
         String sql="""
                    select * from Voucher where maVoucher like ? or giaTri like ? and deleted=0;
                    """;
         try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
             ps.setObject(1, "%"+input+"%");
             ps.setObject(2, "%"+input+"%");
             List<Voucher> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                list.add(vc);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Voucher> findVCByLoai(boolean loaiVC) {
        String sql="""
                    select * from Voucher where loaiVoucher=? and deleted=0;
                    """;
         try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
             ps.setObject(1, loaiVC);
             List<Voucher> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                list.add(vc);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }


    @Override
    public List<Voucher> findVCByTrangThai(int trangThai) {
        String sql="""
                   select * from Voucher where deleted=?
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, trangThai);
            List<Voucher> list=new ArrayList<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                list.add(vc);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public Voucher selectTop1DESC() {
        String sql="""
                   select top 1 * from Voucher order by idVoucher desc
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                return vc;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean deleteVoucher(String maVoucher) {
        String sql="""
                   update Voucher set trangThai=2,deleted=1 where maVoucher=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setObject(1, maVoucher);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }

    @Override
    public Voucher findVoucherByID(Integer idVoucher) {
         String sql="""
                    select * from Voucher where idVoucher=?;
                    """;
         try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
             ps.setObject(1, idVoucher);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Voucher vc=new Voucher();
                vc.setIdVoucher(rs.getInt(1));
                vc.setMaVoucher(rs.getString(2));
                vc.setLoaiVoucher(rs.getBoolean(3));
                vc.setGiaTri(rs.getDouble(4));
                vc.setNgayBatDau(rs.getDate(5));
                vc.setNgayKetThuc(rs.getDate(6));
                vc.setCreatedAt(rs.getDate(7));
                vc.setCreatedBy(rs.getInt(8));
                vc.setUpdatedAt(rs.getObject(9, LocalDate.class));
                vc.setUpdatedBy(rs.getInt(10));
                vc.setDelete(rs.getBoolean(11));
                vc.setSoLuong(rs.getInt(12));
                vc.setTenVoucher(rs.getString(13));
                vc.setGiaTriToiThieu(rs.getDouble(14));
                vc.setGiaTriToiDa(rs.getDouble(15));
                vc.setTrangThai(rs.getInt(16));
                return vc;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean updateSoLuongVoucher(Integer soLuong,Integer idVoucher) {
        String sql="""
                   update Voucher set soLuong=? where idVoucher=?
                   """;
        int check=0;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, soLuong);
            ps.setObject(2, idVoucher);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }
    
}
