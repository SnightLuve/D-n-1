/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.KhoiLuong;

/**
 *
 * @author Dell
 */
public interface KhoiLuongService {
     public List<KhoiLuong> getAll();
    public int addKhoiLuong(KhoiLuong kl);
    public int deleteKhoiLuong(String ma);
    public int updateKhoiLuong(KhoiLuong kl,String ma);
    public KhoiLuong getKhoiLuong(String ma);
    public KhoiLuong getByName(String name);
    public KhoiLuong selectTop1DESC();
}
