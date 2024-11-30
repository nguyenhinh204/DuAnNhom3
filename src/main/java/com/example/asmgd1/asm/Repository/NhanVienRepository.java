package com.example.asmgd1.asm.Repository;

import com.example.asmgd1.asm.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query(nativeQuery = true,
            value ="SELECT * FROM nhanvien x WHERE  x.id LIKE %:id%")
    ArrayList<NhanVien> searchBynv(Integer id);
}
