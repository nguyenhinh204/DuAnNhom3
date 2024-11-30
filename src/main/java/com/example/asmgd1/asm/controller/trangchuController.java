package com.example.asmgd1.asm.controller;

import com.example.asmgd1.Service.*;
import com.example.asmgd1.asm.Repository.*;
import com.example.asmgd1.asm.model.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/asm")
public class trangchuController {
    @Autowired
    private SanPhamService sanPhamRepository;
    @Autowired
    private KhachHangService khachHangRepository;
    @Autowired
    private NhanVienService nhanVienRepository;
    @Autowired
    private SanPhamChiTietService spctRepository;
    @Autowired
    private KichThuocService kichThuocRepository;
    @Autowired
    private MauSacService mauSacRepository;
    @Autowired
    private HoaDonService hoaDonRepository;
    @Autowired
    private HoaDonRepository hdr;
    @Autowired
    private HoaDonChiTietService hdctRepository;

    //    view hien thi
    @GetMapping("/view")
    public String showDs(Model model,
                         @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon) {
        if (idHoaDon != null) {
            // Tìm hóa đơn chi tiết theo ID và thêm vào model
            model.addAttribute("list_HoaDon", hdctRepository.getHoaDonChiTietByHoaDonId(idHoaDon));
        } else {
            // Nếu không có ID, lấy tất cả hóa đơn chi tiết
            model.addAttribute("list_HoaDon", hdctRepository.getAllHoaDonChiTiet());
        }
        return "/asm/trang-chu";
    }

    // sản phẩm
    @GetMapping("/search")
    public String search(@RequestParam("id") Integer hihi,Model model){


        List<sanpham> resultsp=sanPhamRepository.searchSanPham(hihi);
        model.addAttribute("listSP", resultsp);
        return "/asm/hienthi-san-pham";

    }
    @GetMapping("/hienthi-san-pham")
    public String hienThisp(@RequestParam(defaultValue = "0") int page,  Model model) {
//        Pageable pageable= PageRequest.of(page,5);// 5 phan tu tren 1 trang
//        Page<sanpham> sanphamPage=sanPhamRepository.findall(pageable);


//        model.addAttribute("currentPage",sanphamPage.getContent());
//        model.addAttribute("totalPages",sanphamPage.getTotalPages());
//        model.addAttribute("sanpham", new sanpham());
//
        model.addAttribute("listSP", sanPhamRepository.getAllSanPham());
       // model.addAttribute("listSP", sanphamPage.getContent()); // Lấy danh sách sản phẩm trong trang hiện tại
        model.addAttribute("currentPage", page); // Trang hiện tại
      //  model.addAttribute("totalPages", sanphamPage.getTotalPages()); // Tổng số trang
        model.addAttribute("sanpham", new sanpham());
        return "/asm/hienthi-san-pham";
    }

    @GetMapping("/xoa")
    public String deletesp(@RequestParam("id") Integer id) {
        sanPhamRepository.deleteSanPham(id);
        return "redirect:/asm/hienthi-san-pham";
    }

    @GetMapping("/addSP")
    public String addsp(Model model) {
        model.addAttribute("sanpham", new sanpham());
        return "/asm/add-sp";
    }

    @PostMapping("/addSP")
    public String addsp(
            @Valid @ModelAttribute("sanpham") sanpham sp, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listSP", sanPhamRepository.getAllSanPham());
            return "/asm/add-sp";
        }
        sanPhamRepository.addSanPham(sp);
        return "redirect:/asm/hienthi-san-pham";
    }

    @GetMapping("/updateSP")
    public String updatesp(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("sp", sanPhamRepository.getSanPhamById(id));

        return "/asm/update-sp";
    }

    @GetMapping("/detailsSP")
    public String detailssp(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("sp", sanPhamRepository.getSanPhamById(id));

        return "/asm/details-sp";
    }

    @PostMapping("/updateSP")
    public String updatesp(@PathVariable Integer id,sanpham sp) {
        sanPhamRepository.updateSanPham(id,sp);
        return "redirect:/asm/hienthi-san-pham";
    }

    // sản phẩm chi tiết
    @GetMapping("/searchspct")
    public String searchspct(@RequestParam("id") Integer id,Model model){
        List<spchitiet> resultspct=spctRepository.searchSanPhamChiTiet(id);
        model.addAttribute("listSPCT", resultspct);
        return "/asm/hienthi-spct";

    }
    @GetMapping("/ht-spct")
    public String htspct(Model model) {
        model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
        return "/asm/hienthi-spct";
    }

    @GetMapping("/addspct")
    public String addspct(Model model) {
        model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
        model.addAttribute("listms", mauSacRepository.getAllMauSac());
        model.addAttribute("listSP", sanPhamRepository.getAllSanPham());
        model.addAttribute("SPCT", new spchitiet());


        return "/asm/add-spct";
    }

    @PostMapping("/addspct")
    public String addspct(
            @Valid @ModelAttribute("SPCT") spchitiet spct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
            model.addAttribute("listms", mauSacRepository.getAllMauSac());
            model.addAttribute("listSP", sanPhamRepository.getAllSanPham());
            model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
            return "/asm/add-spct";
        }
        spctRepository.saveSanPhamChiTiet(spct);
        return "redirect:/asm/ht-spct";
    }

    @GetMapping("/updatespct")
    public String updatespct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("spct", spctRepository.getSanPhamChiTietById(id));


        // Thêm danh sách kích thước, màu sắc và sản phẩm vào model
        model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
        model.addAttribute("listms", mauSacRepository.getAllMauSac());
        model.addAttribute("listSP", sanPhamRepository.getAllSanPham());

        return "/asm/update-spct"; // Trả về trang cập nhật sản phẩm chi tiết
    }

    @GetMapping("/detailsspct")
    public String detailsspct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("spct", spctRepository.getSanPhamChiTietById(id));

        model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
        model.addAttribute("listms", mauSacRepository.getAllMauSac());
        model.addAttribute("listSP", sanPhamRepository.getAllSanPham());
        return "/asm/details-spct";
    }

    @PostMapping("/updatespct")
    public String updatespct(spchitiet spct) {
        spctRepository.saveSanPhamChiTiet(spct);
        return "redirect:/asm/ht-spct";
    }

    @GetMapping("/xoaspct")
    public String deletespct(@RequestParam("id") Integer id) {
        spctRepository.deleteSanPhamChiTiet(id);
        return "redirect:/asm/ht-spct";
    }


    // khách hàng

//    @GetMapping("/searchkh")
//    public String searchkh(@RequestParam("id") Integer id,Model model){
//        ArrayList<khachhang> resultkh=khachHangRepository.searchBykh(id);
//        model.addAttribute("listkh", resultkh);
//        return "/asm/hienthi-kh";
//
//    }

    @GetMapping("/ht-khachhang")
    public String khachhangview(Model model) {

        model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
        return "/asm/hienthi-kh";
    }

    @GetMapping("/addkh")
    public String addkh(Model model) {
        model.addAttribute("khachhang", new khachhang());
        return "/asm/add-kh";
    }

    @PostMapping("/addkh")
    public String addkh(
            @Valid @ModelAttribute("khachhang") khachhang kh, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
            return "/asm/add-kh";
        }
        khachHangRepository.saveKhachHang(kh);
        return "redirect:/asm/ht-khachhang";
    }

    @GetMapping("/updatekh")
    public String updatekh(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("kh", khachHangRepository.getKhachHangById(id));

        return "/asm/update-kh";

    }

    @GetMapping("/detailskh")
    public String detailskh(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("kh", khachHangRepository.getKhachHangById(id));

        return "/asm/details-kh";
    }

    @PostMapping("/updatekh")
    public String updatekh(khachhang kh) {
        khachHangRepository.updateKhachHang(kh.getId(),kh);
        return "redirect:/asm/ht-khachhang";
    }

    @GetMapping("/xoakh")
    public String xoakh(@RequestParam("id") Integer id) {
        khachHangRepository.deleteKhachHang(id);
        return "redirect:/asm/ht-khachhang";
    }


    //nhân viên

    @GetMapping("/searchnv")
    public String searchnv(@RequestParam("id") Integer id,Model model){
        List<NhanVien> resultnv=nhanVienRepository.findNhanviensByid(id);
        model.addAttribute("listnv", resultnv);
        return "/asm/hienthi-nv";

    }

    @GetMapping("/ht-nv")
    public String nhanvienview(Model model) {
        model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
        return "/asm/hienthi-nv";
    }

    @GetMapping("/addnv")
    public String addnv(Model model) {
        model.addAttribute("nhanvien", new NhanVien());
        return "/asm/add-nv";
    }

    @PostMapping("/addnv")
    public String addnv(
            @Valid @ModelAttribute("nhanvien") NhanVien nv, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
            return "/asm/add-nv";
        }
        nhanVienRepository.saveNhanVien(nv);
        return "redirect:/asm/ht-nv";
    }

    @GetMapping("/updatenv/{id}")
    public String updatenv(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("nv", nhanVienRepository.getNhanVienById(id));


        return "/asm/update-nv";
    }

    @GetMapping("/detailsnv")
    public String detailsnv(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("nv", nhanVienRepository.getNhanVienById(id));


        return "/asm/details-nv";
    }

    @PostMapping("/updatenv/{id}")
    public String updatenv(@PathVariable Integer id, NhanVien nv) {
        nhanVienRepository.updateNhanVien(id,nv);
        return "redirect:/asm/ht-nv";
    }

    @GetMapping("/xoanv")
    public String xoanv(@RequestParam("id") Integer id) {
        nhanVienRepository.deleteNhanVien(id);
        return "redirect:/asm/ht-nv";
    }


    //hoá đơn

    @GetMapping("/searchhd")
    public String searchhd(@RequestParam("id") Integer id,Model model){
        ArrayList<hoadon> resulthd=hdr.searchByhd(id);
        model.addAttribute("listhd", resulthd);
        return "/asm/hienthi-hd";

    }

    @GetMapping("/ht-hd")
    public String hoadonview(Model model) {
        model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
        return "/asm/hienthi-hd";
    }

    @GetMapping("/addhd")
    public String addhd(Model model) {
        model.addAttribute("hd", new hoadon());
        model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
        model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
        return "/asm/add-hd";
    }

    @PostMapping("/addhd")
    public String addhd(
            @Valid @ModelAttribute("hd") hoadon hd, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
            model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
            model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
            return "/asm/add-hd";
        }
        hoaDonRepository.saveHoaDon(hd);
        return "redirect:/asm/ht-hd";
    }

    @GetMapping("/updatehd")
    public String updatehd(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("hd", hoaDonRepository.getHoaDonById(id));

        model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
        model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
        return "/asm/update-hd";
    }

    @GetMapping("/detailshd")
    public String detailshd(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("hd", hoaDonRepository.getHoaDonById(id));

        model.addAttribute("listnv", nhanVienRepository.getAllNhanVien());
        model.addAttribute("listkh", khachHangRepository.getAllKhachHang());
        return "/asm/details-hd";
    }

    @PostMapping("/updatehd")
    public String updatehd(hoadon hd) {
        hoaDonRepository.saveHoaDon(hd);
        return "redirect:/asm/ht-hd";
    }

    //hoá đơn chi tiết

    @GetMapping("/searchhdct")
    public String searchhdct(@RequestParam("id") Integer id,Model model){
        List<hdchitiet> resulthdct=hdctRepository.searchByIdHdct(id);
        model.addAttribute("listhdct", resulthdct);
        return "/asm/hienthi-hdct";

    }

    @GetMapping("/ht-hdct")
    public String hoadonchitietview(Model model) {
        model.addAttribute("listhdct", hdctRepository.getAllHoaDonChiTiet());
        return "/asm/hienthi-hdct";
    }

    @GetMapping("/addhdct")
    public String addhdct(Model model) {
        model.addAttribute("hdct", new hdchitiet());
        model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
        model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
        return "/asm/add-hdct";
    }

    @PostMapping("/addhdct")
    public String addhdct(
            @Valid @ModelAttribute("hdct") hdchitiet hdct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listhdct", hdctRepository.getAllHoaDonChiTiet());
            model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
            model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
            return "/asm/add-hdct";
        }
        hdctRepository.saveHoaDonChiTiet(hdct);
        return "redirect:/asm/ht-hdct";
    }

    @GetMapping("/updatehdct")
    public String updatehdct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("hdct", hdctRepository.getHoaDonChiTietById(id));
//        Optional<HDChiTiet> optionalHdct = hdctRepository.findById(id);
//        if (optionalHdct.isPresent()) {
//            model.addAttribute("hdct", optionalHdct.get());
//        } else {
//            model.addAttribute("errorMessage", "Không tìm thấy hóa đơn chi tiết với ID: " + id);
//        }
        model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
        model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
        return "/asm/update-hdct";
    }

    @GetMapping("/detailshdct")
    public String detailshdct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("hdct", hdctRepository.getHoaDonChiTietById(id));

//        Optional<HDChiTiet> optionalHdct = hdctRepository.findById(id);
//        if (optionalHdct.isPresent()) {
//            model.addAttribute("hdct", optionalHdct.get());
//        } else {
//            model.addAttribute("errorMessage", "Không tìm thấy hóa đơn chi tiết với ID: " + id);
//        }
        model.addAttribute("listhd", hoaDonRepository.getAllHoaDon());
        model.addAttribute("listSPCT", spctRepository.getAllSanPhamChiTiet());
        return "/asm/details-hdct";
    }

    @PostMapping("/updatehdct")
    public String updatehdct(hdchitiet hdct) {
        hdctRepository.saveHoaDonChiTiet(hdct);
        return "redirect:/asm/ht-hdct";
    }

//    @GetMapping("/xoahdct")
//    public String deletehdct(@RequestParam("id") Integer id) {
//        hdctRepository.deleteById(id);
//        return "redirect:/asm/ht-hdct";
//    }


    //màu sắc

    @GetMapping("/searchms")
    public String searchms(@RequestParam("id") Integer id,Model model){
        List<mausac> resultms=mauSacRepository.getMauSacsByid(id);
        model.addAttribute("listms", resultms);
        return "/asm/hienthi-mausac";

    }

    @GetMapping("/ht-ms")
    public String mausacview(Model model) {
        model.addAttribute("listms", mauSacRepository.getAllMauSac());
        return "/asm/hienthi-mausac";
    }

    @GetMapping("/addms")
    public String addms(Model model) {
        model.addAttribute("mausac", new mausac());
        return "/asm/add-ms";
    }

    @PostMapping("/addms")
    public String addms(
            @Valid @ModelAttribute("mausac") mausac ms, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listms", mauSacRepository.getAllMauSac());
            return "/asm/add-ms";
        }
        mauSacRepository.createMauSac(ms);
        return "redirect:/asm/ht-ms";
    }

    @GetMapping("/updatems")
    public String updatems(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("ms", mauSacRepository.getMauSacById(id));
//    Optional<MauSac> optionalMs = mauSacRepository.findById(id);
//    if (optionalMs.isPresent()) {
//        model.addAttribute("ms", optionalMs.get());
//    } else {
//        model.addAttribute("errorMessage", "Không tìm thấy màu sắc với ID: " + id);
//    }
        return "/asm/update-ms";
    }

    @GetMapping("/detailsms")
    public String detailsms(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("ms", mauSacRepository.getMauSacById(id));

//        Optional<MauSac> optionalMs = mauSacRepository.findById(id);
//        if (optionalMs.isPresent()) {
//            model.addAttribute("ms", optionalMs.get());
//        } else {
//            model.addAttribute("errorMessage", "Không tìm thấy màu sắc với ID: " + id);
//        }
        return "/asm/details-ms";
    }

    @PostMapping("/updatems")
    public String updatems(mausac ms) {
        mauSacRepository.createMauSac(ms);
        return "redirect:/asm/ht-ms";
    }

    @GetMapping("/xoams")
    public String xoams(@RequestParam("id") Integer id) {
        mauSacRepository.deleteMauSac(id);
        return "redirect:/asm/ht-ms";
    }

    //kích thước

    @GetMapping("/searchkt")
    public String searchkt(@RequestParam("id") Integer id,Model model){
        List<kichthuoc> resultkt=kichThuocRepository.searchKichThuoc(id);
        model.addAttribute("listkt", resultkt);
        return "/asm/hienthi-kichthuoc";

    }

    @GetMapping("/ht-kt")
    public String kichthuocview(Model model) {
        model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
        return "/asm/hienthi-kichthuoc";
    }

    @GetMapping("/addkt")
    public String addkt(Model model) {
        model.addAttribute("kichthuoc", new kichthuoc());
        return "/asm/add-kt";
    }

    @PostMapping("/addkt")
    public String addkt(
            @Valid @ModelAttribute("kichthuoc") kichthuoc kt, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listkt", kichThuocRepository.getAllKichThuoc());
            return "/asm/add-kt";
        }
        kichThuocRepository.saveKichThuoc(kt);
        return "redirect:/asm/ht-kt";
    }

    @GetMapping("/updatekt")
    public String updatekt(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("kt", kichThuocRepository.getKichThuocById(id));
//        Optional<KichThuoc> optionalKt = kichThuocRepository.findById(id);
//        if (optionalKt.isPresent()) {
//            model.addAttribute("kt", optionalKt.get());
//        } else {
//            model.addAttribute("errorMessage", "Không tìm thấy kích thước với ID: " + id);
//        }
        return "/asm/update-kt";
    }

    @GetMapping("/detailskt")
    public String detailskt(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("kt", kichThuocRepository.getKichThuocById(id));

//        Optional<KichThuoc> optionalKt = kichThuocRepository.findById(id);
//        if (optionalKt.isPresent()) {
//            model.addAttribute("kt", optionalKt.get());
//        } else {
//            model.addAttribute("errorMessage", "Không tìm thấy kích thước với ID: " + id);
//        }
        return "/asm/details-kt";
    }

    @PostMapping("/updatekt/{id}")
    public String updatekt(@PathVariable Integer id, @ModelAttribute kichthuoc kt) {
        kichThuocRepository.updateKichThuoc(id,kt);
        return "redirect:/asm/ht-kt";
    }

    @GetMapping("/xoakt")
    public String xoakt(@RequestParam("id") Integer id) {
        kichThuocRepository.deleteKichThuoc(id);
        return "redirect:/asm/ht-kt";
    }

    //login
    @GetMapping("/login")
    public String login() {
        return "/asm/login";
    }

    @PostMapping("/login")
    public String login(login loginRequest, Model model) {
        if (loginRequest.getUsername().equals("123") && loginRequest.getPassword().equals("123")) {
            model.addAttribute("username", loginRequest.getUsername());
            model.addAttribute("password", loginRequest.getPassword());

            return "asm/trang-chu";
//            return "redirect:/asm/ht-kt";
        }
        model.addAttribute("error", "thong tin dang nhap sai");
        return "asm/login";


    }

}
