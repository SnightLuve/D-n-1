/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Calendar;
import jdbc.DBConnect;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        int i = 0;
        while (true) {
//            cleanDB();
            this.setTrangThaiVoucher(this.getToDay());
            System.out.println("hehehe");
//            this.setTrangThaiVoucher1(this.getToDay());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean cleanDB() {
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

    public void setTrangThaiVoucher(LocalDate date) {
        String sql = """
                   update Voucher set trangThai=case when ngayBatDau<=? and ngayKetThuc>=? then 1
                   				     when ?>ngayKetThuc then 2
                   				     when ?< ngayBatDau then 3
                   end
                   where trangThai =1 or trangThai =2 or trangThai=3
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
    
    public void setTrangThaiVoucher1(LocalDate date) {
        String sql = """
                   update Voucher set trangThai=case 
                     when trangThai=4 and ?>ngayKetThuc then 2
                   end
                   """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, date);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    //GetToday
    private LocalDate getToDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        String ngay = "";
        if (month < 9 && date < 10) {
            ngay = (year + "-" + "0" + (month + 1) + "-" + "0" + date);
        } else if (month < 9 && date >= 10) {
            ngay = (year + "-" + "0" + (month + 1) + "-" + date);
        } else if (month >= 9 && date < 10) {
            ngay = (year + "-" + (month + 1) + "-" + "0" + date);
        } else if (month >= 9 && date >= 10) {
            ngay = (year + "-" + (month + 1) + "-" + date);
        }
        LocalDate ngay1 = LocalDate.parse(ngay);
        return ngay1;
    }
}
