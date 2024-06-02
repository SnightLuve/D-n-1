/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serviceImpl;

import java.util.List;
import model.HoaDon;
import service.HoaDonService;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import jdbc.DBConnect;
import model.SanPhamHoan;

/**
 *
 * @author ACER
 */
public class HoaDonServiceImpl implements HoaDonService {

    @Override
    public List<HoaDon> getAll() {
        String sql = """
                   select * from HoaDon;
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<HoaDon> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setIdKhachHang(rs.getInt(2));
                hd.setIdNhanVien(rs.getInt(3));
                hd.setIdHTTT(rs.getInt(4));
                hd.setIdTTHD(rs.getInt(5));
                hd.setIdVoucher(rs.getInt(6));
                hd.setGiamGiaDiemKH(rs.getDouble(7));
                hd.setThueVAT(rs.getInt(8));
                hd.setTongTienHang(rs.getDouble(9));
                hd.setNgayTao(rs.getObject(10, LocalDate.class));
                hd.setKhachTraTM(rs.getDouble(11));
                hd.setKhachTraCK(rs.getDouble(12));
                hd.setTienThua(rs.getDouble(13));
                hd.setTenNguoiNhan(rs.getString(14));
                hd.setSdtNguoiNhan(rs.getString(15));
                hd.setDiaChiNguoiNhan(rs.getString(16));
                hd.setNgayShipDuTinh(rs.getDate(17));
                hd.setNgayDenDuTinh(rs.getDate(18));
                hd.setPhiShip(rs.getDouble(19));
                hd.setGhiChu(rs.getString(20));
                hd.setMaHoaDon(rs.getString(21));
                hd.setMaGiaoDich(rs.getString(22));
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> getSPDonHang(String maHoaDon) {
        String sql = """
                   {CALL pr_HoaDonChiTiet(?)}
                   """;
        String cols[] = {"maHoaDon", "maSanPham", "tenSanPham", "donGia", "soLuong", "tongTien"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public List<Object[]> hoanHang(String maHoaDon) {
        String sql = """
                   {CALL pr_hoanHang(?)}
                   """;
        String cols[] = {"idCTSP", "soLuong"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public boolean updateTrangThaiHoanHang(String maHoaDon) {
        String sql = """
                    update hoaDon set idTTHD=3 where maHoaDon=?
                    """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public List<Object[]> getHoaDon(Integer index) {
        String sql = """
                   {CALL pr_HoaDon(?)}
                   """;
        String cols[] = {"maHoaDon", "maNhanVien", "tenNhanVien", "tenKhachHang", "sdt", "tongTienHang", "ngayTao", "tenTTHD"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, (index - 1) * 4);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public Integer countHD() {
        String sql = """
                   select count(*) from hoaDon
                   """;
        int count = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return count;
    }

    @Override
    public List<Object[]> getHoaDonChiTiet(String maHoaDon) {
        String sql = """
                   {CALL pr_SanPhamHoaDon(?)}
                   """;
        String cols[] = {"maHoaDon", "maSanPham", "tenSanPham", "tenThuongHieu", "tenXuatXu", "tenKhoiLuong", "tenDonViTinh", "tenChatLieu", "tenLoaiSanPham", "soLuong", "donGia", "tongTien"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            List<Object[]> list = new ArrayList<>();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public List<Object[]> getLichSuHoaDon() {
        String sql = """
                   {CALL pr_LichSuHoaDon}
                   """;
        String cols[] = {"maHoaDon", "maNhanVien", "tenNhanVien", "tenKhachHang", "sdt", "tongTienHang", "ngayTao", "tenTTHD"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public Object[] getChiTietLSHoaDon(String maHoaDon) {
        String sql = """
                   {CALL pr_ChiTietLSHoaDon(?)}
                   """;
        String cols[] = {"maHoaDon", "tenNhanVien", "tenKhachHang", "sdtKhachHang", "giamGiaDiem", "tenNguoiNhan", "sdtNguoiNhan",
            "diaChiNguoiNhan", "ngayTao", "ghiChu", "hinhThucThanhToan", "trangThaiHoaDon", "tongTienHang", "khachTraTM",
            "khachTraCK", "tienThua", "shipDuTinh", "denDuTinh", "phiShip", "maGiaoDich"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                return vals;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public List<Object[]> getSanPhamLichSuHoaDon(String maHoaDon) {
        String sql = """
                   {CALL pr_SanPhamLichSuHoaDon(?)}
                   """;
        String cols[] = {"maSanPham", "tenSanPham", "donGia", "soLuong", "tongTien"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public List<Object[]> findHoaDon(Object input) {
        String sql = """
                   {CALL pr_TimKiemHoaDon(?)}
                   """;
        String cols[] = {"maHoaDon", "maNhanVien", "tenNhanVien", "tenKhachHang", "sdt", "tongTienHang", "ngayTao", "tenTTHD"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, "%" + input + "%");
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public List<Object[]> findHoaDonTheoGia(Double from, Double to) {
        String sql = """
                   {CALL pr_TimKiemHoaDonTheoGia(?,?)}
                   """;
        String cols[] = {"maHoaDon", "maNhanVien", "tenNhanVien", "tenKhachHang", "sdt", "tongTienHang", "ngayTao", "tenTTHD"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, from);
            ps.setObject(2, to);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public boolean deleteHoaDon(Integer idHoaDon) {
        String sql = """
                   delete HoaDon where idHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean deleteHoaDonChiTiet(Integer idHoaDon) {
        String sql = """
                   delete hoaDonChiTiet where idHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean deleteHoaDonChiTietByID(Integer idCTSP, Integer idHoaDon) {
        String sql = """
                   delete hoaDonChiTiet where idSPCT=? and idHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idCTSP);
            ps.setObject(2, idHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public List<Object[]> DanhSachHoaDon() {
        String sql = """
                   {CALL pr_DanhSachHoaDon}
                   """;
        String cols[] = {"maHoaDon", "maNhanVien", "trangThaiHoaDon", "ngayTao"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public HoaDon getHoaDonByMa(String maHoaDon) {
        String sql = """
                    select * from hoaDon where maHoaDon=?
                    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setIdKhachHang(rs.getInt(2));
                hd.setIdNhanVien(rs.getInt(3));
                hd.setIdHTTT(rs.getInt(4));
                hd.setIdTTHD(rs.getInt(5));
                hd.setIdVoucher(rs.getInt(6));
                hd.setGiamGiaDiemKH(rs.getDouble(7));
                hd.setThueVAT(rs.getInt(8));
                hd.setTongTienHang(rs.getDouble(9));
                hd.setNgayTao(rs.getObject(10, LocalDate.class));
                hd.setKhachTraTM(rs.getDouble(11));
                hd.setKhachTraCK(rs.getDouble(12));
                hd.setTienThua(rs.getDouble(13));
                hd.setTenNguoiNhan(rs.getString(14));
                hd.setSdtNguoiNhan(rs.getString(15));
                hd.setDiaChiNguoiNhan(rs.getString(16));
                hd.setNgayShipDuTinh(rs.getDate(17));
                hd.setNgayDenDuTinh(rs.getDate(18));
                hd.setPhiShip(rs.getDouble(19));
                hd.setGhiChu(rs.getString(20));
                hd.setMaHoaDon(rs.getString(21));
                hd.setMaGiaoDich(rs.getString(22));
                return hd;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean createBill(HoaDon hd) {
        String sql = """
                   insert into HoaDon(maHoaDon,idNhanVien,idTTHD,ngayTao) values(?,?,?,?)
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, hd.getMaHoaDon());
            ps.setObject(2, hd.getIdNhanVien());
            ps.setObject(3, hd.getIdTTHD());
            ps.setObject(4, hd.getNgayTao());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public HoaDon selectTop1DESC() {
        String sql = """
                   select top 1 * from hoaDon order by HoaDon.idHoaDon desc
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setIdHoaDon(rs.getInt(1));
                hd.setIdKhachHang(rs.getInt(2));
                hd.setIdNhanVien(rs.getInt(3));
                hd.setIdHTTT(rs.getInt(4));
                hd.setIdTTHD(rs.getInt(5));
                hd.setIdVoucher(rs.getInt(6));
                hd.setGiamGiaDiemKH(rs.getDouble(7));
                hd.setThueVAT(rs.getInt(8));
                hd.setTongTienHang(rs.getDouble(9));
                hd.setNgayTao(rs.getObject(10, LocalDate.class));
                hd.setKhachTraTM(rs.getDouble(11));
                hd.setKhachTraCK(rs.getDouble(12));
                hd.setTienThua(rs.getDouble(13));
                hd.setTenNguoiNhan(rs.getString(14));
                hd.setSdtNguoiNhan(rs.getString(15));
                hd.setDiaChiNguoiNhan(rs.getString(16));
                hd.setNgayShipDuTinh(rs.getDate(17));
                hd.setNgayDenDuTinh(rs.getDate(18));
                hd.setPhiShip(rs.getDouble(19));
                hd.setGhiChu(rs.getString(20));
                hd.setMaHoaDon(rs.getString(21));
                return hd;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    public boolean ThanhToanHoaDon(HoaDon hd, String maHD) {
        String sql = """
                    update HoaDon set idKhachHang=?,idHTTT=?,idTTHD=?,giamGiaDiemKH=?,thueVat=?,tongTienHang=?,KhachTraTM=?,KhachTraCK=?,tienThua=?,ghiChu=?,tenNguoiNhan=?,sdtNguoiNhan=?,diaChiNguoiNhan=?,phiShip=?,maGiaoDich=?,idVoucher=? where maHoaDon=?
                    """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, hd.getIdKhachHang());
            ps.setObject(2, hd.getIdHTTT());
            ps.setObject(3, hd.getIdTTHD());
            ps.setObject(4, hd.getGiamGiaDiemKH());
            ps.setObject(5, hd.getThueVAT());
            ps.setObject(6, hd.getTongTienHang());
            ps.setObject(7, hd.getKhachTraTM());
            ps.setObject(8, hd.getKhachTraCK());
            ps.setObject(9, hd.getTienThua());
            ps.setObject(10, hd.getGhiChu());
            ps.setObject(11, hd.getTenNguoiNhan());
            ps.setObject(12, hd.getSdtNguoiNhan());
            ps.setObject(13, hd.getDiaChiNguoiNhan());
            ps.setObject(14, hd.getPhiShip());
            ps.setObject(15, hd.getMaGiaoDich());
            ps.setObject(16, hd.getIdVoucher());
            ps.setObject(17, maHD);
            
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean hoanTatBill(String maHD) {
        String sql = """
                   update HoaDon set idTTHD=2 where HoaDon.maHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean ThanhToanHoaDonShip(HoaDon hd, String maHD) {
        String sql = """
                    update HoaDon set idKhachHang=?,idHTTT=?,idTTHD=?,giamGiaDiemKH=?,thueVat=?,tongTienHang=?,KhachTraTM=?,KhachTraCK=?,tienThua=?,ghiChu=?,tenNguoiNhan=?,sdtNguoiNhan=?,diaChiNguoiNhan=?,phiShip=?,maGiaoDich=?,ngayShipDuTinh=?,ngayDenDuTinh=?,idVoucher=? where maHoaDon=?
                    """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, hd.getIdKhachHang());
            ps.setObject(2, hd.getIdHTTT());
            ps.setObject(3, hd.getIdTTHD());
            ps.setObject(4, hd.getGiamGiaDiemKH());
            ps.setObject(5, hd.getThueVAT());
            ps.setObject(6, hd.getTongTienHang());
            ps.setObject(7, hd.getKhachTraTM());
            ps.setObject(8, hd.getKhachTraCK());
            ps.setObject(9, hd.getTienThua());
            ps.setObject(10, hd.getGhiChu());
            ps.setObject(11, hd.getTenNguoiNhan());
            ps.setObject(12, hd.getSdtNguoiNhan());
            ps.setObject(13, hd.getDiaChiNguoiNhan());
            ps.setObject(14, hd.getPhiShip());
            ps.setObject(15, hd.getMaGiaoDich());
            ps.setObject(16, hd.getNgayShipDuTinh());
            ps.setObject(17, hd.getNgayDenDuTinh());
            ps.setObject(18, hd.getIdVoucher());
            ps.setObject(19, maHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean deleteHoaDonByMa(String maHoaDon) {
        String sql = """
                   delete HoaDon where maHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean deleteHoaDonCho() {
        String sql = """
                    delete HoaDon where idTTHD=5
                    """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean huyBill(String maHD) {
        String sql = """
                   update HoaDon set idTTHD=4 where HoaDon.maHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, maHD);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public List<Object[]> getSPHoan(String maHoaDon) {
        String sql = """
                   {CALL pr_LichSuSanPhamHoan(?)}
                   """;
        String cols[] = {"maSanPham", "tenSanPham", "donGia", "soLuong", "ngayTra"};
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, maHoaDon);
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
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
    public boolean addLichSuHoan(SanPhamHoan sph) {
        String sql = """
                   insert into SanPhamHoan(idCTSP,soLuong,maHoaDon) values (?,?,?)
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, sph.getIdCTSP());
            ps.setObject(2, sph.getSoLuong());
            ps.setObject(3, sph.getMaHoaDon());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public boolean updateSoLuongHDCT(Integer soLuong, Integer idCTSP, Integer idHoaDon) {
        String sql = """
                   update hoaDonChiTiet set soLuong=? where hoaDonChiTiet.idSPCT=? and hoaDonChiTiet.idHoaDon=?
                   """;
        int check = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idCTSP);
            ps.setObject(3, idHoaDon);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    @Override
    public int soLuongDonHangCho() {
        String sql="""
                   select count(*) from HoaDon where idTTHD=5
                   """;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)){
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
    public boolean updateTongTienHang(Double tongTienHang, String maHoaDon) {
        String sql="""
                   update HoaDon set tongTienHang=? where maHoaDon=?
                   """;
        int check=0;
        try(Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareCall(sql)){
            ps.setObject(1, tongTienHang);
            ps.setObject(2, maHoaDon);
            check=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check>0;
    }



}
