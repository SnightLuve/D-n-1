/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.DonViTinh;

/**
 *
 * @author Dell
 */
public interface DonViTinhService {

    public List<DonViTinh> getAll();

    public int addDonViTinh(DonViTinh dvt);

    public int deleteDonViTinh(String ma);

    public int updateDonViTinh(DonViTinh dvt, String ma);
    public DonViTinh getByName(String ten);
    public DonViTinh selectTop1DESC();
}
