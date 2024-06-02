/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import service.ThongKeService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jdbc.DBConnect;
import model.ThongKe;

/**
 *
 * @author ACER
 */
public class ThongKeServiceImpl implements ThongKeService{

    @Override
    public double getDoanhThuCaNam(int year) {
        String sql="""
                   select SUM(tongTienHang) from HoaDon where YEAR(ngayTao)=? and idTTHD=2 or YEAR(ngayTao)=? and idTTHD=3
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, year);
            ps.setObject(2, year);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0.0;
    }

    @Override
    public double getDoanhThuToDay(LocalDate toDay) {
        String sql="""
                   select distinct SUM(tongTienHang) from HoaDon where ngayTao=? and idTTHD=2 or ngayTao=? and idTTHD=3
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, toDay);
            ps.setObject(2, toDay);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0.0;
    }

    @Override
    public double getDoanhThu7NgayGanNhat() {
        String sql="""
                   select SUM(tongTienHang) from hoaDon where ngayTao>=DATEADD(DAY,-7,GETDATE()) and idTTHD=2 or ngayTao>=DATEADD(DAY,-7,GETDATE()) and idTTHD=3
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0.0;
    }

    @Override
    public double getDoanhThuThangNay(String firstDayInMonth, LocalDate toDay) {
        String sql="""
                   select SUM(tongTienHang) from hoaDon where ngayTao between ? and ? and idTTHD=2 or ngayTao between ? and ? and idTTHD=3
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, firstDayInMonth);
            ps.setObject(2, toDay);
            ps.setObject(3, firstDayInMonth);
            ps.setObject(4, toDay);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0.0;
    }

    @Override
    public List<Object[]> LocDoanhThuTheoNgay(String from, String to) {
        String sql="""
                   {CALL pr_LocDoanhThuTheoNgay(?,?)}
                   """;
        String cols[]={"ngayTao","doanhThu"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ps.setObject(1, from);
            ps.setObject(2, to);
            ResultSet rs=ps.executeQuery();
            List<Object[]> list=new ArrayList<>();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> DoanhThuSanPham() {
        String sql="""
                   {CALL pr_DoanhThuSanPham}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            List<Object[]> list=new ArrayList<>();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> DoanhThuSanPhamTheoLoaiSP(String tenLoaiSP) {
        String sql="""
                   {CALL pr_DoanhThuSanPhamTheoLoaiSanPham(?)}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ps.setObject(1, tenLoaiSP);
            ResultSet rs=ps.executeQuery();
            List<Object[]> list=new ArrayList<>();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public Object[] DoanhThuSanPhamNhieuNhat() {
        String sql="""
                   {CALL pr_DoanhThuSanPhamNhieuNhat}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                return vals;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public Object[] DoanhThuSanPhamItNhat() {
        String sql="""
                   {CALL pr_DoanhThuSanPhamItNhat}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                return vals;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public Object[] SanPhamBanNhieuNhat() {
        String sql="""
                   {CALL pr_SanPhamBanNhieuNhat}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                return vals;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public Object[] SanPhamBanItNhat() {
        String sql="""
                   {CALL pr_SanPhamBanItNhat}
                   """;
        String cols[]={"maSanPham","tenSanPham","tenLoaiSanPham","soLuongTon","donGia","soLuongBanDuoc","doanhThuTuSanPham"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                return vals;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public int soSanPhamDangKinhDoanh() {
        String sql="""
                   select COUNT(*) from chiTietSanPham where deleted=0
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    @Override
    public int soSanPhamHetHang() {
        String sql="""
                   select COUNT(*) from chiTietSanPham where soLuongTon=0
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    @Override
    public int soSanPhamSapHetHang() {
        String sql="""
                   select COUNT(*) from chiTietSanPham where soLuongTon<=5 and soLuongTon>0
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    @Override
    public int soSanPhamNgungKinhDoanh() {
        String sql="""
                   select COUNT(*) from chiTietSanPham where deleted=1
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareCall(sql)){
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    @Override
    public ThongKe ThongKeChart(int month,int year) {
        String sql="""
                   select	SUM(HoaDon.tongTienHang) tongTienHang,
                   		MONTH(hoaDon.ngayTao) thang
                   from HoaDon
                   join NhanVien on HoaDon.idNhanVien=NhanVien.idNhanVien
                   join TrangThaiHoaDon on hoaDon.idTTHD=TrangThaiHoaDon.idTTHD
                   where MONTH(hoaDon.ngayTao)=? and YEAR(ngayTao)=?
                   group by MONTH(hoaDon.ngayTao)
                   """;
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setObject(1, month);
            ps.setObject(2, year);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ThongKe tk=new ThongKe();
                tk.setTongTien(rs.getDouble(1));
                tk.setThang(rs.getInt(2));
                return tk;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> listYear() {
        String sql="""
                   select distinct YEAR(HoaDon.ngayTao) ngayTao from hoaDon
                   """;
        String cols[]={"ngayTao"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            List<Object[]> list=new ArrayList<>();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> listMonth() {
        String sql="""
                   select distinct Month(HoaDon.ngayTao) ngayTao from hoaDon
                   """;
        String cols[]={"ngayTao"};
        try(Connection con=DBConnect.getConnection();PreparedStatement ps=con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            List<Object[]> list=new ArrayList<>();
            while(rs.next()){
                Object[] vals=new Object[cols.length];
                for(int i=0;i<cols.length;i++){
                    vals[i]=rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    
    
}
