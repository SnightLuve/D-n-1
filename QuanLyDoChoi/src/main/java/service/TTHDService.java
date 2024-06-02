/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;
import model.TrangThaiHoaDon;

/**
 *
 * @author ACER
 */
public interface TTHDService {
    public TrangThaiHoaDon findTTHDById(Integer idTTHD);
    public List<TrangThaiHoaDon> getAll();
}
