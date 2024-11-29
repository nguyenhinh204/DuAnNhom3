package com.example.asmgd1.asm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPChiTiet")
@Entity
public class spchitiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@NotNull(message = "khong duooc de trong id")
    private Integer id;

    @Column(name = "MaSPCT")
    @NotBlank(message = "khong duooc de trong ma")
    private String ma;


    @ManyToOne
    @JoinColumn(name = "IdKichThuoc", referencedColumnName = "id")

    private kichthuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "IdMauSac", referencedColumnName = "id")

    private mausac mauSac;

    @ManyToOne
    @JoinColumn(name = "IdSanPham", referencedColumnName = "id")

    private sanpham sanPham;

    @Column(name = "SoLuong")
   // @NotBlank(message = "khong duooc de trong so luong")
    private Integer soluong;

    @Column(name = "DonGia")
    @NotNull(message = "khong duoc de trong don gia")
   // @DecimalMin(value = "10", message = "don gia phai lon hon 10")

    private Double dongia;

    @Column(name = "TrangThai")
    private boolean trangThai;
    public spchitiet(int i) {
        this.id=id;
    }


}
