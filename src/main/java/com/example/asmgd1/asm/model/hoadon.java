package com.example.asmgd1.asm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HoaDon")
@Entity
public class hoadon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @NotNull(message = "khong duoc de trong id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang", referencedColumnName = "id")
    private khachhang khachHang;


    @Column(name = "NgayMuaHang")
    //@NotBlank(message = "vui long nhap ngay mua hang")
    private LocalDate ngaymh;
    @Column(name = "TrangThai")
    private boolean trangThai;
}
