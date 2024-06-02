/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.ThuongHieu;

/**
 *
 * @author Dell
 */
public interface ThuongHieuService {

    public List<ThuongHieu> getAll();

    public int addThuongHieu(ThuongHieu th);

    public int deleteThuongHieu(String ma);

    public int updateThuongHieu(ThuongHieu th,String ma);

    public ThuongHieu getThuongHieu(int id);
    public ThuongHieu getByName(String name);
    
    public ThuongHieu selectTop1DESC();
}
