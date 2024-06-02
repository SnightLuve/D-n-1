/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import category.categoryBean;
import com.github.sarxos.webcam.Webcam;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.Contains.BanHangJPanel;
import view.Contains.KhuyenMaiJPanel;
import view.Contains.HoaDonJPanel;
import view.Contains.KhachHangJPanel;
import view.Contains.NhanVienJPanel;
import view.Contains.SanPhamJPanel;
import view.Contains.ThongKeJPanel;

/**
 *
 * @author ACER
 */
public class MoveScreenController {

    private JPanel root;
    private String kindSelected = "";
    private List<categoryBean> listItem = null;
    Webcam wc;

    public MoveScreenController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "BanHang";
        jpnItem.setBackground(new Color(37, 197, 219));
        jlbItem.setBackground(new Color(37, 197, 219));
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new BanHangJPanel());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<categoryBean> listItem) {
        this.listItem = listItem;
        for (categoryBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "BanHang":
                    node = new BanHangJPanel();
                    break;
                case "SanPham":
                    node = new SanPhamJPanel();
                    break;
                case "GiamGia":
                    node = new KhuyenMaiJPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangJPanel();
                    break;
                case "ThongKe":
                    node = new ThongKeJPanel();
                    break;
                case "NhanVien":
                    node = new NhanVienJPanel();
                    break;
                case "HoaDon":
                    node = new HoaDonJPanel();
                    break;
                default:
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(37, 197, 219));
            jlbItem.setBackground(new Color(37, 197, 219));
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(37, 197, 219));
            jlbItem.setBackground(new Color(37, 197, 219));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(100, 180, 244));
                jlbItem.setBackground(new Color(100, 180, 244));
            }
        }

        private void setChangeBackground(String kind) {
            for (categoryBean item : listItem) {
                if (item.getKind().equalsIgnoreCase(kind)) {
                    item.getJpn().setBackground(new Color(37, 197, 219));
                    item.getJlb().setBackground(new Color(37, 197, 219));
                } else {
                    item.getJpn().setBackground(new Color(100, 180, 244));
                    item.getJlb().setBackground(new Color(100, 180, 244));
                }
            }
        }
    }
}
