/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author 24dom
 */
public class MsgBox {

    public static void alert(Component parent, String ms) {
        JOptionPane.showMessageDialog(parent, ms, "Hệ Thống Quản Lý Đào Tạo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(Component parent, String ms) {
        int result = JOptionPane.showConfirmDialog(parent, ms, "Hệ Thống Quản Lý Đào Tạo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String prompt(Component parent, String ms) {
        return JOptionPane.showInputDialog(parent, ms, "Hệ Thống Quản Lý Đào Tạo", JOptionPane.INFORMATION_MESSAGE);
    }

}
