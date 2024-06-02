/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.XuatXu;

/**
 *
 * @author Dell
 */
public interface XuatXuService {
    public List<XuatXu> getAll();
    public int addXuatXu(XuatXu xx);
    public int deleteXuatXu(String ma);
    public int updateXuatXu(XuatXu xx,String ma);
    public XuatXu getByName(String name);
    public XuatXu selectTop1DESC();
}
