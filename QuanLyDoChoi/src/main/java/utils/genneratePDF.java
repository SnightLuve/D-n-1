/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import com.itextpdf.io.image.BmpImageData;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.pdfa.PdfADocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.ChiTietSanPham;
import model.GioHang;
import model.HoaDon;
import model.KhachHang;
import model.SanPham;
import modelview.modelChiTietSanPham;
import service.ChiTietSanPhamService;
import service.SanPhamService;
import serviceImpl.ChiTietSanPhamServiceImpl;
import serviceImpl.SanPhamServiceImpl;

/**
 *
 * @author Dell
 */
public class genneratePDF {
    
    ChiTietSanPhamService ctspsv=new ChiTietSanPhamServiceImpl();
    SanPhamService spsv=new SanPhamServiceImpl();
    NumberFormat format=NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {

    }

    public void genInvoice(List<GioHang> list,HoaDon hd,KhachHang kh,String maHD) throws FileNotFoundException, MalformedURLException {
        String path = "src\\main\\resources\\InvoicePDF\\"+maHD+".pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        String imagePath = "src\\main\\resources\\icon\\rsz_logo-do-choi-babyking.png";
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);
        float x = pdfDocument.getDefaultPageSize().getWidth() / 2;
        float y = pdfDocument.getDefaultPageSize().getHeight() / 2;
        image.setFixedPosition(x - 150, y - 170);
        image.setOpacity(0.1f);
        document.add(image);

        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol + 150f;
        float threeColumnWidth[] = {threecol, threecol, threecol};
        float twocolumnWidth[] = {twocol150, twocol};
        float fullWidth[] = {threecol * 3};

        Table table = new Table(twocolumnWidth);

        table.addCell(new com.itextpdf.layout.element.Cell().add("King Kids").setBorder(Border.NO_BORDER).setBold());
        Table nestedtable = new Table(new float[]{twocol / 2, twocol / 2});
        nestedtable.addCell((com.itextpdf.layout.element.Cell) getHeaderTextCell("Invoice No."));
        nestedtable.addCell((com.itextpdf.layout.element.Cell) getHeaderTextCellValue(maHD));
        nestedtable.addCell((com.itextpdf.layout.element.Cell) getHeaderTextCell("Invoice Date."));
        nestedtable.addCell((com.itextpdf.layout.element.Cell) getHeaderTextCellValue("31/12/2022"));

        table.addCell(new com.itextpdf.layout.element.Cell().add(nestedtable).setBorder(Border.NO_BORDER));
        Border border = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(fullWidth);
        divider.setBorder(border);

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(divider);
        document.add(new Paragraph("\n"));

        Table twoColTable = new Table(threeColumnWidth);
        twoColTable.addCell(getBillingandShippingCell("Billing Information"));
        twoColTable.addCell(getBillingandShippingCell("Customer Information"));
        twoColTable.addCell(getBillingandShippingCell("Shipping Information"));
//        document.add(twoColTable.setMargin(12f));
        document.add(twoColTable);

        Table twoColTable2 = new Table(threeColumnWidth);
        twoColTable2.addCell(getCell10fLeft("Company", true));
        twoColTable2.addCell(getCell10fLeft("Name", true));
        twoColTable2.addCell(getCell10fLeft("Name", true));
        document.add(twoColTable2);

        //data
        Table twoColTable3 = new Table(threeColumnWidth);
        twoColTable3.addCell(getCell10fLeft("King Kids", false));
        twoColTable3.addCell(getCell10fLeft(kh.getTenKhachHang(), false));
        twoColTable3.addCell(getCell10fLeft(hd.getTenNguoiNhan(), false));
        document.add(twoColTable3);

        Table twoColumnTable1 = new Table(threeColumnWidth);
        twoColumnTable1.addCell(getCell10fLeft("Address", true));
        twoColumnTable1.addCell(getCell10fLeft("PhoneNumber", true));
        twoColumnTable1.addCell(getCell10fLeft("PhoneNumber", true));
        document.add(twoColumnTable1);
        //data
        Table twoColumnTable4 = new Table(threeColumnWidth);
        twoColumnTable4.addCell(getCell10fLeft("P.KieuMai,Ha Noi", false));
        twoColumnTable4.addCell(getCell10fLeft(kh.getSdt(), false));
        twoColumnTable4.addCell(getCell10fLeft(hd.getSdtNguoiNhan(), false));
        document.add(twoColumnTable4);

        Table twoColumnTable5 = new Table(threeColumnWidth);
        twoColumnTable5.addCell(getCell10fLeft("Email", true));
        twoColumnTable5.addCell(getCell10fLeft("Address", true));
        twoColumnTable5.addCell(getCell10fLeft("Address", true));
        document.add(twoColumnTable5);

        //data
        Table twoColumnTable6 = new Table(threeColumnWidth);
        twoColumnTable6.addCell(getCell10fLeft("kingkids@gmail.com", false));
        twoColumnTable6.addCell(getCell10fLeft(kh.getDiaChi(), false));
        twoColumnTable6.addCell(getCell10fLeft(hd.getDiaChiNguoiNhan(), false));

        document.add(twoColumnTable6.setMarginBottom(30f));

        Table tableDivider2 = new Table(fullWidth);
        Border dgb = new DashedBorder(Color.GRAY, 0.5f);
        document.add(tableDivider2.setBorder(dgb));

        Paragraph productPara = new Paragraph("Products");
        document.add(productPara.setBold());
        
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);
        threeColTable1.addCell(new Cell().add("Product name").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(10f));
        document.add(threeColTable1);

//        List<HoaDon> list = new ArrayList<>();
//        list.add(new HoaDon("Apple", 2, 200));
//        list.add(new HoaDon("Mango", 4, 200));
//        list.add(new HoaDon("Coconut", 1, 200));
        Table threeColTable2 = new Table(threeColumnWidth);
        for (GioHang gh : list) {
            ChiTietSanPham ctsp=ctspsv.getCTSPByID(gh.getIdCTSP());
            SanPham sp=spsv.getByIdSP(ctsp.getIdSanPham());
            double total = gh.getSoLuong()*ctsp.getDonGia();
            
            
            threeColTable2.addCell(new Cell().add(sp.getTenSanPham()).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            threeColTable2.addCell(new Cell().add(String.valueOf(gh.getSoLuong())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(format.format(total))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        }
        document.add(threeColTable2);
        
        Table threeColTable5 = new Table(threeColumnWidth);
        threeColTable5.setBackgroundColor(Color.BLACK, 0.7f);
        threeColTable5.addCell(new Cell().add("").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable5.addCell(new Cell().add("").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable5.addCell(new Cell().add("Tax: 5%").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(10f));
        document.add(threeColTable5.setMarginBottom(35f));
        
        float onetwo[] = {threecol + 125f, threecol * 2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2)).setBorder(Border.NO_BORDER);
        document.add(threeColTable4);

        Table threeColTable3 = new Table(threeColumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setMarginLeft(10f));
        threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(String.valueOf(format.format(hd.getTongTienHang()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(threeColTable3);
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMarginBottom(30f));

        Table tb = new Table(fullWidth);
        tb.addCell(new Cell().add("Terms add condition\n").setBold().setBorder(Border.NO_BORDER));

        List<String> TncList = new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment.");

        for (String tcn : TncList) {
            tb.addCell(new Cell().add(tcn).setBorder(Border.NO_BORDER));
        }
        document.add(tb);

        document.close();
    }

    static Cell getHeaderTextCell(String textValue) {
        return (Cell) new com.itextpdf.layout.element.Cell().add(textValue).setBold().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue(String textValue) {
        return (Cell) new com.itextpdf.layout.element.Cell().add(textValue).setBold().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingandShippingCell(String textValue) {
        return (Cell) new com.itextpdf.layout.element.Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10fLeft(String textValue, Boolean isBold) {
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT
        );
        return isBold ? myCell.setBold() : myCell;
    }

}

class Product {

    private String name;
    private int quantity;
    private float price;

    public Product(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
