create proc pr_HoaDonChiTiet(@maHoaDon nvarchar(500))
as begin
select	HoaDon.maHoaDon maHoaDon,
		SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		chiTietSanPham.donGia donGia,
		hoaDonChiTiet.soLuong soLuong,
		(chiTietSanPham.donGia*hoaDonChiTiet.soLuong) tongTien
from HoaDon 
		join hoaDonChiTiet on HoaDon.idHoaDon=hoaDonChiTiet.idHoaDon
		join chiTietSanPham on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
		join SanPham on SanPham.idSanPham=chiTietSanPham.idSanPham where HoaDon.maHoaDon=@maHoaDon
end

create proc pr_hoanHang(@maHoaDon nvarchar(500))
as begin
	select chiTietSanPham.idCTSP idCTSP,
		   hoaDonChiTiet.soLuong soLuong 
    from HoaDon 
		   join hoaDonChiTiet on HoaDon.idHoaDon=hoaDonChiTiet.idHoaDon
		   join chiTietSanPham on chiTietSanPham.idCTSP=hoaDonChiTiet.idSPCT where hoaDon.maHoaDon=@maHoaDon
end

/*proc Hóa Đơn*/
create proc pr_HoaDon(@index int)
as begin
select	HoaDon.maHoaDon maHoaDon,
		NhanVien.maNhanVien maNhanVien,
		NhanVien.tenNhanVien tenNhanVien,
		KhachHang.tenKhachHang tenKhachHang,
		KhachHang.sdt sdt,
		HoaDon.tongTienHang tongTienHang,
		HoaDon.ngayTao ngayTao,
		TrangThaiHoaDon.tenTTHD tenTTHD
from HoaDon
		join NhanVien on hoaDon.idNhanVien=NhanVien.idNhanVien
		join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
		join TrangThaiHoaDon on HoaDon.idTTHD=TrangThaiHoaDon.idTTHD
		order by HoaDon.idHoaDon offset @index rows fetch next 4 rows only
end

/*proc Sản Phẩm Hóa Đơn*/
create proc pr_SanPhamHoaDon(@maHoaDon nvarchar(500))
as begin
select	HoaDon.maHoaDon maHoaDon,
		SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		ThuongHieu.tenThuongHieu tenThuongHieu,
		XuatXu.tenXuatXu tenXuatXu,
		KhoiLuong.tenKhoiLuong tenKhoiLuong,
		DonViTinh.tenDonViTinh tenDonViTinh,
		ChatLieu.tenChatLieu tenChatLieu,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		hoaDonChiTiet.soLuong soLuong,
		chiTietSanPham.donGia donGia,
		(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) tongTien
from HoaDon
	join HoaDonChiTiet on HoaDon.idHoaDon=hoaDonChiTiet.idHoaDon
	join chiTietSanPham on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
	join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
	join ThuongHieu on chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
	join XuatXu on chiTietSanPham.idXuatXu=XuatXu.idXuatXu
	join KhoiLuong on chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
	join DonViTinh on chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh
	join ChatLieu on chiTietSanPham.idChatLieu=ChatLieu.idChatLieu
	join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham 
	where HoaDon.maHoaDon=@maHoaDon
end

/*proc Lịch Sử Hóa Đơn*/
exec pr_LichSuHoaDon
create proc pr_LichSuHoaDon
as begin
select	HoaDon.maHoaDon maHoaDon,
		NhanVien.maNhanVien maNhanVien,
		NhanVien.tenNhanVien tenNhanVien,
		KhachHang.tenKhachHang tenKhachHang,
		KhachHang.sdt sdt,
		HoaDon.tongTienHang tongTienHang,
		HoaDon.ngayTao ngayTao,
		TrangThaiHoaDon.tenTTHD tenTTHD
from HoaDon
		join NhanVien on hoaDon.idNhanVien=NhanVien.idNhanVien
		join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
		join TrangThaiHoaDon on HoaDon.idTTHD=TrangThaiHoaDon.idTTHD
		order by hoaDon.idHoaDon desc
end

/*proc Chi Tiết Lịch Sử Hóa Đơn*/
exec pr_ChiTietLSHoaDon N'HĐ103'
create proc pr_ChiTietLSHoaDon(@maHoaDon nvarchar(500))
as begin
select	HoaDon.maHoaDon maHoaDon,
		NhanVien.tenNhanVien tenNhanVien,
		KhachHang.tenKhachHang tenKhachHang,
		KhachHang.sdt sdtKhachHang,
		hoaDon.giamGiaDiemKH giamGiaDiem,
		HoaDon.tenNguoiNhan tenNguoiNhan,
		HoaDon.sdtNguoiNhan sdtNguoiNhan,
		HoaDon.diaChiNguoiNhan diaChiNguoiNhan,
		HoaDon.ngayTao ngayTao,
		HoaDon.ghiChu ghiChu,
		HinhThucThanhToan.tenHTTT hinhThucThanhToan,
		TrangThaiHoaDon.tenTTHD trangThaiHoaDon,
		HoaDon.tongTienHang tongTienHang,
		HoaDon.KhachTraTM khachTraTM,
		HoaDon.KhachTraCK khachTraCK,
		HoaDon.tienThua tienThua,
		HoaDon.ngayShipDuTinh shipDuTinh,
		HoaDon.ngayDenDuTinh denDuTinh,
		HoaDon.phiShip phiShip,
		HoaDon.maGiaoDich maGiaoDich
from HoaDon 
	join NhanVien on HoaDon.idNhanVien=NhanVien.idNhanVien
	join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
	join HinhThucThanhToan on HoaDon.idHTTT=HinhThucThanhToan.idHTTT
	join TrangThaiHoaDon on HoaDon.idTTHD=TrangThaiHoaDon.idTTHD where HoaDon.maHoaDon=@maHoaDon
end

/*proc Sản Phẩm Lịch Sử Hóa Đơn*/
exec pr_SanPhamLichSuHoaDon N'HĐ103'
create proc pr_SanPhamLichSuHoaDon(@maHoaDon nvarchar(500))
as begin
select	
		Sanpham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		chiTietSanPham.donGia donGia,
		hoaDonChiTiet.soLuong soLuong,
		(chiTietSanPham.donGia*hoaDonChiTiet.soLuong) tongTien
from HoaDon 
		join hoaDonChiTiet on HoaDon.idHoaDon=hoaDonChiTiet.idHoaDon
		join chiTietSanPham on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
		join SanPham on SanPham.idSanPham=chiTietSanPham.idSanPham
		where hoaDon.maHoaDon=@maHoaDon
end

/*Tìm Kiếm Hóa Đơn*/
create proc pr_TimKiemHoaDon(@input nvarchar(max))
as begin
select
		HoaDon.maHoaDon maHoaDon,
		NhanVien.maNhanVien maNhanVien,
		NhanVien.tenNhanVien tenNhanVien,
		KhachHang.tenKhachHang tenKhachHang,
		KhachHang.sdt sdt,
		HoaDon.tongTienHang tongTienHang,
		HoaDon.ngayTao ngayTao,
		TrangThaiHoaDon.tenTTHD tenTTHD
from HoaDon
		join NhanVien on hoaDon.idNhanVien=NhanVien.idNhanVien
		join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
		join TrangThaiHoaDon on HoaDon.idTTHD=TrangThaiHoaDon.idTTHD
		where HoaDon.maHoaDon like @input or NhanVien.maNhanVien like @input
		or NhanVien.tenNhanVien like @input or KhachHang.tenKhachHang like @input
		or KhachHang.sdt like @input or HoaDon.tongTienHang like @input
		or TrangThaiHoaDon.tenTTHD like @input
end

/*Tìm Kiếm Hóa Đơn Theo Giá*/
create proc pr_TimKiemHoaDonTheoGia(@from float,@to float)
as begin
select
		HoaDon.maHoaDon maHoaDon,
		NhanVien.maNhanVien maNhanVien,
		NhanVien.tenNhanVien tenNhanVien,
		KhachHang.tenKhachHang tenKhachHang,
		KhachHang.sdt sdt,
		HoaDon.tongTienHang tongTienHang,
		HoaDon.ngayTao ngayTao,
		TrangThaiHoaDon.tenTTHD tenTTHD
from HoaDon
		join NhanVien on hoaDon.idNhanVien=NhanVien.idNhanVien
		join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
		join TrangThaiHoaDon on HoaDon.idTTHD=TrangThaiHoaDon.idTTHD
		where HoaDon.tongTienHang>=@from and hoaDon.tongTienHang<=@to
end

create proc pr_KhachHangBought(@maKhachHang nchar(20))
as begin
select	SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		chiTietSanPham.donGia donGia,
		hoaDonChiTiet.soLuong soLuong,
		(chiTietSanPham.donGia*hoaDonChiTiet.soLuong) tongTien
from hoaDon 
	join hoaDonChiTiet on hoaDon.idHoaDon=hoaDonChiTiet.idHoaDon
	join chiTietSanPham on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
	join KhachHang on HoaDon.idKhachHang=KhachHang.idKhachHang
	join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
	where KhachHang.maKhachHang=@maKhachHang
end


create proc [dbo].[pr_DanhSachHoaDon]
as begin
select	HoaDon.maHoaDon maHoaDon,
		NhanVien.tenNhanVien tenNhanVien,
		TrangThaiHoaDon.tenTTHD trangThaiHoaDon,
		HoaDon.ngayTao ngayTao
from HoaDon 
join NhanVien on HoaDon.idNhanVien=NhanVien.idNhanVien
join TrangThaiHoaDon on hoaDon.idTTHD=TrangThaiHoaDon.idTTHD
end

/*Sản Phẩm Hoàn*/
create proc pr_LichSuSanPhamHoan(@maHoaDon nvarchar(500))
as begin
select	SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		chiTietSanPham.donGia donGia,
		SanPhamHoan.soLuong soLuong,
		SanPhamHoan.ngayTra ngayTra
from SanPhamHoan
		join chiTietSanPham on SanPhamHoan.idCTSP=chiTietSanPham.idCTSP
		join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
		where SanPhamHoan.maHoaDon=@maHoaDon
end


update HoaDon set ngayTao='2023-11-24' where idHoaDon=11
select * from HoaDon
/*Doanh Thu Cả Năm*/
select SUM(tongTienHang) from HoaDon where YEAR(ngayTao)=2023
/*Doanh Thu Hôm Nay*/
select SUM(tongTienHang) from HoaDon where ngayTao='2023-11-16'
/*Doanh Thu 7 ngày gần nhất*/
select SUM(tongTienHang) from hoaDon where ngayTao>=DATEADD(DAY,-7,GETDATE())
/*Doanh Thu Tháng Này*/
select SUM(tongTienHang) from hoaDon where ngayTao between '2023-11-01' and '2023-11-24'

/*Lọc Doanh Thu Theo Ngày*/
create proc pr_LocDoanhThuTheoNgay(@from date,@to date)
as begin
	select hoaDon.ngayTao ngayTao,
	SUM(tongTienHang) doanhThu
	from HoaDon where HoaDon.ngayTao between @from and @to and idTTHD=2 or idTTHD=3 group by HoaDon.ngayTao
end
/*Doanh Thu Sản Phẩm*/
create proc pr_DoanhThuSanPham
as begin
select distinct	SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
end

/*Doanh Thu Sản Phẩm Theo LoaiSanPham*/
create proc pr_DoanhThuSanPhamTheoLoaiSanPham(@loaiSanPham nvarchar(50))
as begin
select distinct	SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where loaiSanPham.tenLoaiSanPham=@loaiSanPham and HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
end

/*Sản Phẩm Doanh Thu Nhiều Nhất*/
create proc pr_DoanhThuSanPhamNhieuNhat
as begin
select distinct top 1 SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
					order by doanhThuTuSanPham desc
end
/*Sản Phẩm Doanh Thu Ít Nhất*/
create proc pr_DoanhThuSanPhamItNhat
as begin
select distinct top 1 SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
					order by doanhThuTuSanPham asc
end
/*Sản Phẩm Mua Nhiều Nhất*/
create proc pr_SanPhamBanNhieuNhat
as begin
select distinct top 1 SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
					order by soLuongBanDuoc desc
end
/*Sản Phẩm Mua Ít Nhất*/
create proc pr_SanPhamBanItNhat
as begin
select distinct top 1 SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		chiTietSanPham.soLuongTon soLuongTon,
		chiTietSanPham.donGia donGia,
		SUM(hoaDonChiTiet.soLuong) soLuongBanDuoc,
		SUM(hoaDonChiTiet.soLuong*chiTietSanPham.donGia) doanhThuTuSanPham
from chiTietSanPham join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
					join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
					join hoaDonChiTiet on hoaDonChiTiet.idSPCT=chiTietSanPham.idCTSP
					join HoaDon on hoaDonChiTiet.idHoaDon=hoaDon.idHoaDon
					where HoaDon.idTTHD=2 or HoaDon.idTTHD=3
					group by SanPham.maSanPham,SanPham.tenSanPham,LoaiSanPham.tenLoaiSanPham,chiTietSanPham.soLuongTon,chiTietSanPham.donGia
					order by soLuongBanDuoc asc
end

select * from chiTietSanPham
select COUNT(*) from chiTietSanPham where soLuongTon=0
select COUNT(*) from chiTietSanPham where soLuongTon<=5
select COUNT(*) from chiTietSanPham where deleted=1

/*Bán Hàng: Danh Sách Hóa Đơn*/

create proc pr_DanhSachHoaDon
as begin
select	HoaDon.maHoaDon maHoaDon,
		NhanVien.maNhanVien maNhanVien,
		TrangThaiHoaDon.tenTTHD trangThaiHoaDon,
		HoaDon.ngayTao ngayTao
from HoaDon 
join NhanVien on HoaDon.idNhanVien=NhanVien.idNhanVien
join TrangThaiHoaDon on hoaDon.idTTHD=TrangThaiHoaDon.idTTHD
where TrangThaiHoaDon.tenTTHD=N'Chờ Thanh Toán' or TrangThaiHoaDon.tenTTHD=N'Đang Giao Hàng'
order by idHoaDon desc
end



/*Tìm Kiếm Sản Phẩm*/
create proc pr_findCTSP(@input nvarchar(500))
as begin
select
		SanPham.maSanPham maSanPham,
		SanPham.tenSanPham tenSanPham,
		LoaiSanPham.tenLoaiSanPham tenLoaiSanPham,
		ThuongHieu.tenThuongHieu tenThuongHieu,
		XuatXu.tenXuatXu tenXuatXu,
		ChatLieu.tenChatLieu tenChatLieu,
		DonViTinh.tenDonViTinh tenDonViTinh,
		KhoiLuong.tenKhoiLuong tenKhoiLuong,
		chiTietSanPham.donGia donGia,
		chiTietSanPham.soLuongTon soLuongTon
from chiTietSanPham
	join SanPham on chiTietSanPham.idSanPham=SanPham.idSanPham
	join ThuongHieu on chiTietSanPham.idThuongHieu=ThuongHieu.idThuongHieu
	join XuatXu on chiTietSanPham.idXuatXu=XuatXu.idXuatXu
	join KhoiLuong on chiTietSanPham.idKhoiLuong=KhoiLuong.idKhoiLuong
	join DonViTinh on chiTietSanPham.idDonViTinh=DonViTinh.idDonViTinh
	join ChatLieu on chiTietSanPham.idChatLieu=ChatLieu.idChatLieu
	join LoaiSanPham on chiTietSanPham.idLoaiSanPham=LoaiSanPham.idLoaiSanPham
	where maSanPham like @input or tenSanPham like @input or tenLoaiSanPham like @input
	or tenThuongHieu like @input or tenXuatXu like @input or tenChatLieu like @input
	or tenDonViTinh like @input or tenKhoiLuong like @input
end
