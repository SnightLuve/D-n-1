/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.ChatLieu;
import model.SanPham;

/**
 *
 * @author Dell
 */
public interface ChatLieuService {
    public List<ChatLieu> getAll();
    public int addChatLieu(ChatLieu cl);
    public int deleteChatLieu(String ma);
    public int updateChatLieu(ChatLieu cl,String ma);
    public ChatLieu getChatLieu(int id);
    public ChatLieu getByName(String tenChatLieu);
    public ChatLieu selectTop1DESC();
    
}
