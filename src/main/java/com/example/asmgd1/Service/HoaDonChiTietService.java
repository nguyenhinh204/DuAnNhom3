package com.example.asmgd1.Service;

import com.example.asmgd1.asm.Repository.HDCTRepository;
import com.example.asmgd1.asm.model.hdchitiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonChiTietService {

    @Autowired
    private HDCTRepository hoaDonChiTietRepository;

    public List<hdchitiet> getAllHoaDonChiTiet() {
        List<hdchitiet> hoaDonChiTietList = hoaDonChiTietRepository.findAll();
        if (hoaDonChiTietList == null) {
            throw new NullPointerException("Dữ liệu từ repository trả về null");
        }
        return hoaDonChiTietList;
    }

    public hdchitiet getHoaDonChiTietById(Integer id) {
        Optional<hdchitiet> optionalHoaDonChiTiet = hoaDonChiTietRepository.findById(id);
        if (optionalHoaDonChiTiet == null) {
            throw new NullPointerException("Dữ liệu từ repository trả về null");
        }
        return optionalHoaDonChiTiet.orElse(null);
    }

    public hdchitiet saveHoaDonChiTiet(hdchitiet hdct) {
        if (hdct.getHoaDon()==null){
            throw new IllegalArgumentException("Không được phép lưu khi hóa đơn là null.");
        }
        if (hdct.getSpChiTiet()==null){
            throw new IllegalArgumentException("spct trong ne");
        }
        if (hdct.getSoluong()==null){
            throw new IllegalArgumentException("Số lượng không thể trống.");
        }
        if (hdct.getDongia()==null){
            throw new IllegalArgumentException("Đơn giá không thể trôngs.");
        }

        if (hdct.getSoluong()<0){
            throw new IllegalArgumentException("Số lượng không thể âm.");
        }
        if (hdct.isTrangThai()==false){
            throw new IllegalArgumentException("Trạng thái không được để trống");
        }


        if (hdct.getDongia()<0){
            throw new IllegalArgumentException("Đơn giá không thể âm.");
        }


        return hoaDonChiTietRepository.save(hdct);
    }

    public List<hdchitiet> searchByIdHdct(Integer id) {
        if (id==null){
            throw new NullPointerException("ngoại lệ khi ID là null.");
        }
        return hoaDonChiTietRepository.searchByhdct(id);
    }
    public hdchitiet updateHoaDonChiTiet(hdchitiet hoaDonChiTiet) {
        if (hoaDonChiTiet.getHoaDon() == null) {
            throw new IllegalArgumentException("Hoá đơn không được để trống");
        }


        if (hoaDonChiTiet.getSpChiTiet() == null) {
            throw new IllegalArgumentException("Sản phẩm chi tiết không được để trống");
        }
        if (hoaDonChiTiet.getSoluong() == null || hoaDonChiTiet.getSoluong() <= 0) {

            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        if (hoaDonChiTiet.getDongia() == null || hoaDonChiTiet.getDongia() < 0) {
            throw new IllegalArgumentException("Đơn giá không được trống và âm");
        }

        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    public List<hdchitiet> getHoaDonChiTietByHoaDonId(Integer hoaDonId) {
        if (hoaDonId==null){
            throw new NullPointerException("id hoa don bị null");
        }
        return hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
    }
}
