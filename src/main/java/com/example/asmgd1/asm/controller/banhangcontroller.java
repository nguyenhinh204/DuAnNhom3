package com.example.asmgd1.asm.controller;

import com.example.asmgd1.asm.Repository.*;
import com.example.asmgd1.asm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/banhang")
public class banhangcontroller {
    @Autowired
    private SPCTRepository sanPhamChiTietRepository;

    @Autowired
    private HDCTRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @ModelAttribute("listnv")
    List<NhanVien> getall(){
        return nhanVienRepository.findAll();
    }
    @ModelAttribute("listkh")
    List<khachhang> getallkh(){
        return khachHangRepository.findAll();
    }
    private List<CartItem> list_CartItem = new ArrayList<>();

    public Integer getTongSoLuong() {
        return list_CartItem.stream().mapToInt(CartItem::getSoLuong).sum();
    }

//    private Integer idUpdate = 0;

    public Double getTongTien() {
        return list_CartItem.stream()
                .mapToDouble(item -> item.getSoLuong() * item.getDonGia())
                .sum();
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String ngayHienTai = LocalDate.now().format(formatter);

    @GetMapping("/hien-thi")
    public String showDs(Model model,
                         @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon) {
        if (idHoaDon != null) {
            // Tìm hóa đơn chi tiết theo ID và thêm vào model
            model.addAttribute("list_HoaDon", hoaDonChiTietRepository.findById(idHoaDon).orElse(null));
        } else {
            // Nếu không có ID, lấy tất cả hóa đơn chi tiết
            model.addAttribute("list_HoaDon", hoaDonChiTietRepository.findAll());
        }

        // Xóa các mục trong giỏ hàng (nếu cần thiết)
        // cartItems.clear();

        return "banhang/hien-thi"; // Trả về view
    }
    @PostMapping("/update-item")
    public String updateItemInCart(@RequestParam("sanPhamChiTietId") Integer id,
                                   @RequestParam("soLuongGio") Integer soLuong) {
        for (CartItem cartItem : list_CartItem) {
            if (cartItem.getSanPhamChiTietId().equals(id)) {
                spchitiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(id);
                sanPhamChiTiet.setSoluong(sanPhamChiTiet.getSoluong() + cartItem.getSoLuong() - soLuong);
                sanPhamChiTietRepository.save(sanPhamChiTiet);
                cartItem.setSoLuong(soLuong);
                break;
            }
        }
        return "redirect:/banhang/add";
    }
    @GetMapping("/detail")
    public String chiTiet(Model model, @RequestParam("id") Integer id) {

        hoadon hoaDon = hoaDonRepository.getReferenceById(id);

        List<hdchitiet> list_HoaDonChiTiet = hoaDonChiTietRepository.getHoaDonChiTietByHoaDonId(id);

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("list_HoaDonChiTiet", list_HoaDonChiTiet);

        return "banhang/detail";
    }
    @GetMapping("/search")
    public String searchHoaDonById(@RequestParam(value = "idHoaDon", required = false) Integer idHoaDon, Model model) {

        List<hdchitiet> list_HoaDonChiTiet;

        if (idHoaDon != null) {
            list_HoaDonChiTiet = hoaDonChiTietRepository.getHoaDonChiTietByHoaDonId(idHoaDon);
        } else {
            // Nếu không có ID, lấy tất cả hóa đơn chi tiết
            list_HoaDonChiTiet = hoaDonChiTietRepository.findAll();
        }

        model.addAttribute("list_HoaDon", list_HoaDonChiTiet);
        return "banhang/hien-thi";
    }

    @GetMapping("/add")
    public String searchSanPhamChiTiet(@RequestParam(value = "idSearch", required = false) Integer idSearch, Model model) {
        List<spchitiet> list_SanPhamChiTiet;

        if (idSearch != null) {
            Optional<spchitiet> optionalSanPhamChiTiet = sanPhamChiTietRepository.findById(idSearch);
            list_SanPhamChiTiet = optionalSanPhamChiTiet.map(List::of).orElseGet(Collections::emptyList);
        } else {
            list_SanPhamChiTiet = sanPhamChiTietRepository.findAll();
        }

        model.addAttribute("list_SanPhamChiTiet", list_SanPhamChiTiet);

        // Thêm các thuộc tính cần thiết khác vào model
        model.addAttribute("list_CartItem", list_CartItem);
        model.addAttribute("idSanPhamChiTiet", "Tổng mặt hàng: " + list_CartItem.size() + " mặt hàng");
        model.addAttribute("soLuongForm", "Tổng số lượng: " + getTongSoLuong() + " sản phẩm!");
        model.addAttribute("tongTien", "Tổng tiền: " + getTongTien() + " VND.");
        model.addAttribute("ngayHienTai", ngayHienTai);

        return "banhang/add";
    }
    @PostMapping("/add-item")
    public String addItemToCart(@RequestParam("sanPhamChiTietId") Integer sanPhamChiTietId,
                                @RequestParam("soluong") Integer soluong, Model model) {
        spchitiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(sanPhamChiTietId);

        if (soluong > sanPhamChiTiet.getSoluong()) {
            model.addAttribute("error", "Số lượng không đủ.");
            model.addAttribute("list_SanPhamChiTiet", sanPhamChiTietRepository.findAll());
            return "banhang/add";
        }

        sanPhamChiTiet.setSoluong(sanPhamChiTiet.getSoluong() - soluong);
        sanPhamChiTietRepository.save(sanPhamChiTiet);


        boolean productExistsInCart = false;
        for (CartItem cartItem : list_CartItem) {
            if (cartItem.getSanPhamChiTietId().equals(sanPhamChiTietId)) {
                cartItem.setSoLuong(cartItem.getSoLuong() + soluong);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            CartItem cartItem = new CartItem(sanPhamChiTietId, sanPhamChiTiet.getSanPham().getTen(),
                    sanPhamChiTiet.getMauSac().getTenms(), sanPhamChiTiet.getKichThuoc().getTenkt(),
                    soluong, sanPhamChiTiet.getDongia());
            list_CartItem.add(cartItem);
        }

        return "redirect:/banhang/add";
    }
//    @PostMapping("/add")
//    public String addHoaDonFromCart(@ModelAttribute hoadon hoaDon,
//                                    @RequestParam("thanh_toan") Boolean thanhToan,
//                                    Model model) {
//
//        boolean hasError = false;
//
//        if (list_CartItem.isEmpty()) {
//            model.addAttribute("error", "Hãy chọn sản phẩm!");
//            hasError = true;
//        }
//
//        if (hoaDon.getKhachHang() == null || hoaDon.getKhachHang().getId() == null) {
//            model.addAttribute("errorkhachHang", "Hãy chọn khách hàng!");
//            hasError = true;
//        }
//
//        if (hoaDon.getNhanVien() == null || hoaDon.getNhanVien().getId() == null) {
//            model.addAttribute("errornhanVien", "Hãy chọn nhân viên!");
//            hasError = true;
//        }
//
//        if (hasError) {
//            model.addAttribute("list_CartItem", list_CartItem);
//            model.addAttribute("idSanPhamChiTiet", "Tổng mặt hàng: " + list_CartItem.size() + " mặt hàng");
//            model.addAttribute("soLuongForm", "Tổng số lượng: " + getTongSoLuong() + " sản phẩm!");
//            model.addAttribute("list_SanPhamChiTiet", sanPhamChiTietRepository.findAll());
//            model.addAttribute("ngayHienTai", ngayHienTai);
//
//            return "banhang/add";
//        }
//
//        hoaDon.setNgaymh(LocalDate.now());
//        hoaDon.setTrangThai(thanhToan);
//        hoaDonRepository.save(hoaDon);
//
//        for (CartItem cartItem : list_CartItem) {
//            hdchitiet hoaDonChiTiet = new hdchitiet();
//            hoaDonChiTiet.setHoaDon(hoaDon);
//            hoaDonChiTiet.setSpChiTiet(sanPhamChiTietRepository.getReferenceById(cartItem.getSanPhamChiTietId()));
//            hoaDonChiTiet.setSoluong(cartItem.getSoLuong());
//            hoaDonChiTiet.setDongia(cartItem.getDonGia());
//            hoaDonChiTiet.setTrangThai(thanhToan);
//
//            hoaDonChiTietRepository.save(hoaDonChiTiet);
//        }
//
//        list_CartItem.clear();
//        return "redirect:/banhang/hien-thi";
//    }
@PostMapping("/add")
public String addHoaDonFromCart(@ModelAttribute hoadon hoaDon,
                                @RequestParam("thanh_toan") Boolean thanhToan) {
    hoaDon.setNgaymh(LocalDate.now());
    hoaDon.setTrangThai(thanhToan);
    hoaDonRepository.save(hoaDon);

    for (CartItem cartItem : list_CartItem) {
        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(sanPhamChiTietRepository.getReferenceById(cartItem.getSanPhamChiTietId()));
        hoaDonChiTiet.setSoluong(cartItem.getSoLuong());
        hoaDonChiTiet.setDongia(cartItem.getDonGia());
        hoaDonChiTiet.setTrangThai(thanhToan);

        hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    // Xóa giỏ hàng sau khi thanh toán
    list_CartItem.clear();
//    return "banhang/hoa-don";  // Thay thế với đường dẫn đến trang JSP hóa đơn
    return "redirect:/banhang/hien-thi";
}
    @GetMapping("/remove")
    public String removeItemFromCart(@RequestParam("id") Integer id) {
        list_CartItem.removeIf(cartItem -> {
            if (cartItem.getSanPhamChiTietId().equals(id)) {
                spchitiet sanPhamChiTiet = sanPhamChiTietRepository.getReferenceById(id);
                sanPhamChiTiet.setSoluong(sanPhamChiTiet.getSoluong() + cartItem.getSoLuong());
                sanPhamChiTietRepository.save(sanPhamChiTiet);
                return true;
            }
            return false;
        });
        return "redirect:/banhang/add";
    }
@GetMapping("/hoadon")
    public String hoadon(@ModelAttribute hoadon hoaDon,Model model){

    model.addAttribute("list_CartItem", list_CartItem);
    model.addAttribute("idSanPhamChiTiet", "Tổng: " + list_CartItem.size() + " mặt hàng");
    model.addAttribute("soLuongForm", "Tổng: " + getTongSoLuong() + " sản phẩm!");
    model.addAttribute("list_SanPhamChiTiet", sanPhamChiTietRepository.findAll());
    model.addAttribute("ngayHienTai", ngayHienTai);
    model.addAttribute("tongTien", getTongTien());
        return "banhang/hoa-don";

}



}
