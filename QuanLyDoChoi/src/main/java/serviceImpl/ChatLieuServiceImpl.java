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
import model.ChatLieu;
import service.ChatLieuService;

/**
 *
 * @author Dell
 */
public class ChatLieuServiceImpl implements ChatLieuService {

    String sql = "";
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;

    @Override
    public List<ChatLieu> getAll() {
        sql = "SELECT *FROM ChatLieu WHERE deleted=0 order by idChatLieu desc";
        List<ChatLieu> list = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu chatlieu = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                list.add(chatlieu);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addChatLieu(ChatLieu cl) {
        sql = "insert into ChatLieu(maChatLieu,tenChatLieu,createdBy,deleted) values(?,?,?,?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, cl.getMaChatLieu());
            ps.setObject(2, cl.getTenChatLieu());
            ps.setObject(3, cl.getCreatedBy());
            ps.setObject(4, cl.isDelete());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteChatLieu(String ma) {
        sql = "	UPDATE ChatLieu SET deleted=1 WHERE maChatLieu=?";
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
    public int updateChatLieu(ChatLieu cl, String ma) {
        sql = "UPDATE ChatLieu SET tenChatLieu=?,updatedAt=?,updatedBy=? WHERE maChatLieu=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, cl.getTenChatLieu());
            ps.setObject(2, cl.getUpdatedAt());
            ps.setObject(3, cl.getUpdatedBy());
            ps.setObject(4, ma);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ChatLieu getChatLieu(int id) {
        sql = "SELECT *FROM ChatLieu WHERE  idChatLieu=?";
        ChatLieu chatLieu = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                chatLieu = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
            }
            return chatLieu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChatLieu getByName(String ten) {
        sql = "SELECT *from ChatLieu where tenChatLieu=?";
        ChatLieu cl = null;
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ten);
            rs = ps.executeQuery();
            while (rs.next()) {
                cl = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
            }
            return cl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChatLieu selectTop1DESC() {
        String sql="""
                   select top 1 * from ChatLieu order by idChatLieu desc
                   """;
        try{
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu chatlieu = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getInt(5), rs.getObject(6, LocalDate.class), rs.getInt(7), rs.getBoolean(8));
                return chatlieu;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

}
