package com.example.asmgd1.Service;

import com.example.asmgd1.asm.Repository.NhanVienRepository;
import com.example.asmgd1.asm.model.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NhanVienService {
    List<NhanVien> nhanVienList = new ArrayList<>();

    public List<NhanVien> getAllNhanVien() {
        return nhanVienList;
    }

    private String validateNhanVien(NhanVien nv) {
        if (nv.getTennv() == null || nv.getTennv().trim().isEmpty()) {
            return "Tên nhân viên không được để trống";
        }
        if (nv.getManv() == null || nv.getManv().trim().isEmpty()) {
            return "Mã nhân viên không được để trống";
        }
        if (nv.getTendn() == null || nv.getTendn().trim().isEmpty()) {
            return "Tên đăng nhập không được để trống";
        }
        if (nv.getMatkhau() == null || nv.getMatkhau().trim().isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        return null; // Không có lỗi
    }

    // Thêm nhân viên
    public String addNhanVien(NhanVien nv) {
        String validationError = validateNhanVien(nv);
        if (validationError != null) {
            return validationError;
        }

        nhanVienList.add(nv);
        return "Thêm nhân viên thành công";
    }

    // Sửa nhân viên
    public String updateNhanVien(int id, NhanVien newInfo) {
        Optional<NhanVien> nv = nhanVienList.stream()
                .filter(nhanVien -> nhanVien.getId() == id)
                .findFirst();

        if (nv.isPresent()) {
            String validationError = validateNhanVien(newInfo);
            if (validationError != null) {
                return validationError;
            }

            NhanVien existing = nv.get();
            existing.setTennv(newInfo.getTennv());
            existing.setManv(newInfo.getManv());
            existing.setTendn(newInfo.getTendn());
            existing.setMatkhau(newInfo.getMatkhau());
            existing.setTrangThai(newInfo.isTrangThai());
            return "Cập nhật nhân viên thành công";
        }

        return "Không tìm thấy nhân viên với ID " + id;
    }


    public NhanVien findById(int id) {
        for (NhanVien nv : nhanVienList) {
            if (nv.getId() == id) {
                return nv;  // Trả về nhân viên nếu tìm thấy
            }
        }
        return null;  // Trả về null nếu không tìm thấy
    }

    // Xóa nhân viên
    public String deleteNhanVien(int id) {
        NhanVien nv = findById(id);  // Tìm nhân viên theo ID
        if (nv != null) {
            nhanVienList.remove(nv);  // Nếu tìm thấy, xóa nhân viên khỏi danh sách
            return "Xóa nhân viên thành công";
        } else {
            return "Không tìm thấy nhân viên với ID " + id;  // Nếu không tìm thấy nhân viên
        }
    }


    public NhanVien getNhanVienById(int id) {
        if (id <= 0) {  // Kiểm tra nếu ID không hợp lệ
            throw new IllegalArgumentException("ID phải là một số dương.");
        }
        return nhanVienList.stream()
                .filter(nv -> nv.getId() == id)
                .findFirst()
                .orElse(null);  // Trả về null nếu không tìm thấy nhân viên với ID đó
    }

    public List<NhanVien> getNhanVienByName(String name) {
        if (name == null || name.trim().isEmpty()) {  // Kiểm tra tên không được null hoặc trống
            throw new IllegalArgumentException("Tên không được để trống.");
        }

        // Kiểm tra nếu tên chứa toàn bộ là số
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("Tên không thể chứa toàn bộ số.");
        }

        return nhanVienList.stream()
                .filter(nv -> nv.getTennv().equalsIgnoreCase(name))
                .collect(Collectors.toList());  // Trả về danh sách nhân viên có tên trùng khớp
    }
}
