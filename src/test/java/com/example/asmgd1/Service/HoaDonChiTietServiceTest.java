package com.example.asmgd1.Service;

import com.example.asmgd1.asm.Repository.HDCTRepository;
import com.example.asmgd1.asm.Repository.HoaDonRepository;
import com.example.asmgd1.asm.Repository.SPCTRepository;
import com.example.asmgd1.asm.model.hdchitiet;
import com.example.asmgd1.asm.model.hoadon;
import com.example.asmgd1.asm.model.spchitiet;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback
class HoaDonChiTietServiceTest {
    @Autowired
    private HoaDonChiTietService hdctr1;
    @Autowired
    private SPCTRepository SPCTR;
    @Autowired
    private HoaDonRepository hdr;
    @Autowired
    private HDCTRepository hdctRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    //---------------------GetAll-------------------------------
    @Test
    void testGetAllHoaDonChiTiet_Size() {
//        List<hdchitiet> hdctrlist = hdctr1.getAllHoaDonChiTiet();
//        assertEquals(16, hdctrlist.size(), "Danh sách phải có 16 hóa đơn chi tiết.");
        // Test khi có dữ liệu
        hdchitiet hoaDonChiTiet1 = new hdchitiet();
        hoaDonChiTiet1.setSoluong(10);
        hoaDonChiTiet1.setDongia(20.0);
        hoaDonChiTiet1.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet1);

        hdchitiet hoaDonChiTiet2 = new hdchitiet();
        hoaDonChiTiet2.setSoluong(20);
        hoaDonChiTiet2.setDongia(30.0);
        hoaDonChiTiet2.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet2);

        hdchitiet hoaDonChiTiet3 = new hdchitiet();
        hoaDonChiTiet3.setSoluong(30);
        hoaDonChiTiet3.setDongia(60.0);
        hoaDonChiTiet3.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet3);

        hdchitiet hoaDonChiTiet4 = new hdchitiet();
        hoaDonChiTiet4.setSoluong(40);
        hoaDonChiTiet4.setDongia(80.0);
        hoaDonChiTiet4.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet3);

        List<hdchitiet> hoaDonChiTiets = hdctr1.getAllHoaDonChiTiet();
        assertFalse(hoaDonChiTiets.isEmpty(), "Danh sách không được rỗng khi có dữ liệu");
        assertEquals(19, hoaDonChiTiets.size());
    }

    @Test
    void testGetAllHoaDonChiTiet_NotNull() {
        List<hdchitiet> hdctrlist = hdctr1.getAllHoaDonChiTiet();
        assertNotNull(hdctrlist, "Danh sách không được null.");
    }
    @Test
    void testGetAllHoaDonChiTiet_ValidData() {
        List<hdchitiet> hdctrlist = hdctr1.getAllHoaDonChiTiet();
        hdchitiet hdct = hdctrlist.get(0);
        assertEquals(1, hdct.getId(), "Mã hóa đơn không đúng.");
        assertNotNull(hdct.getSpChiTiet(), "Sản phẩm không được null.");
    }
    @Test
    void testGetAllHoaDonChiTiet_ZeroQuantity() {
        List<hdchitiet> hdctrlist = hdctr1.getAllHoaDonChiTiet();
        for (hdchitiet hdct : hdctrlist) {
            assertTrue(hdct.getSoluong() >= 0, "Số lượng sản phẩm không thể nhỏ hơn 0.");
        }
    }
    @Test
    void testgetAllHoaDonChiTiet_1() {


        List<hdchitiet> hdctrlist=hdctr1.getAllHoaDonChiTiet();
        assertTrue(hdctrlist.size()>0,"Danh sách hóa đơn phải có ít nhất một hóa đơn.");
    }

    @Test
    void testGetAllHoaDonChiTiet_5() {
        // Kiểm tra với số lượng âm
        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setSoluong(-10);
        hoaDonChiTiet.setDongia(50.0);
        hoaDonChiTiet.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet);

        List<hdchitiet> hoaDonChiTiets = hdctr1.getAllHoaDonChiTiet();
        assertFalse(hoaDonChiTiets.get(5).getSoluong() < 0, "Số lượng có giá trị âm");
    }
    @Test
    void testGetAllHoaDonChiTiet_2() {
        //  dữ liệu trùng lặp
        hdchitiet hoaDonChiTiet1 = new hdchitiet();
        hoaDonChiTiet1.setSoluong(9);
        hoaDonChiTiet1.setDongia(50.0);
        hoaDonChiTiet1.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet1);

        hdchitiet hoaDonChiTiet2 = new hdchitiet();
        hoaDonChiTiet2.setSoluong(9);
        hoaDonChiTiet2.setDongia(50.0);
        hoaDonChiTiet2.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet2);

        List<hdchitiet> hoaDonChiTiets = hdctr1.getAllHoaDonChiTiet();
        assertEquals(18, hoaDonChiTiets.size(), "list phải có bản ghi trùng");
    }
    @Test
    void testGetAllHoaDonChiTiet_3() {
        hdchitiet hoaDonChiTiet2 = new hdchitiet();
        hoaDonChiTiet2.setSoluong(Integer.MAX_VALUE);
        hoaDonChiTiet2.setDongia(100.0);
        hoaDonChiTiet2.setTrangThai(true);
        hdctRepository.save(hoaDonChiTiet2);

        List<hdchitiet> hoaDonChiTiets = hdctr1.getAllHoaDonChiTiet();
        assertEquals(17, hoaDonChiTiets.size(), "list phải có bản ghi trùng");
    }

    //    //-----------------------------getHoaDonChiTietById-------------------------------------------------------
    @Test
    void testGetHoaDonChiTietById_ValidId() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(1); // ID hợp lệ
        assertNotNull(hdct, "Kết quả không được null.");
        assertEquals(1, hdct.getId(), "ID không đúng.");
    }
    @Test
    void testGetHoaDonChiTietById_IdNotExist() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(6789); // ID không tồn tại
        assertNull(hdct, "Kết quả phải là null khi ID không tồn tại.");
    }

    @Test
    void testGetHoaDonChiTietById_NegativeId() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(-1);
        assertNull(hdct, "Kết quả phải là null khi ID âm.");
    }


    @Test
    void testGetHoaDonChiTietById_IncompleteData() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(1); // Giả sử ID 1 đầy đủ
        assertNotNull(hdct.getSpChiTiet(), "Sản phẩm chi tiết không được null.");
        assertNotNull(hdct.getHoaDon(), "Hóa đơn không được null.");
    }

    @Test
    void testGetHoaDonChiTietById_BoundaryValue() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(1); // Giả sử 1 là giá trị biên
        assertNotNull(hdct, "Kết quả không được null với giá trị biên hợp lệ.");
    }

    @Test
    void testGetHoaDonChiTietById_MaxId() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(Integer.MAX_VALUE);
        assertNull(hdct, "Kết quả phải là null khi ID vượt giới hạn tối đa.");
    }
    @Test
    void testGetHoaDonChiTietById_MinId() {
        hdchitiet hdct = hdctr1.getHoaDonChiTietById(Integer.MIN_VALUE);
        assertNull(hdct, "Kết quả phải là null khi ID vượt giới hạn tối đa.");
    }
    @Test
    void testGetHoaDonChiTietById_IDBang0() {
        hdchitiet result = hdctr1.getHoaDonChiTietById(0);
        assertNull(result);
    }
    //------------------------------addHoaDonChiTiet------------------------------------------------------------------

    @Test
    void saveHoaDonChiTiet() {

        hoadon hoaDon = new hoadon();
        hoaDon.setId(1);
        spchitiet spChiTiet = new spchitiet();
        spChiTiet.setId(1);
        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(5);
        hdct.setDongia(600.0);
        hdct.setTrangThai(true);
        hdchitiet savedHdct = hdctr1.saveHoaDonChiTiet(hdct);
        assertNotNull(savedHdct, "Hóa đơn chi tiết đã lưu không được null.");
        assertEquals(hoaDon.getId(), savedHdct.getHoaDon().getId(), "ID hóa đơn không đúng.");
        assertEquals(spChiTiet.getId(), savedHdct.getSpChiTiet().getId(), "ID sản phẩm chi tiết không đúng.");
    }
    @Test
    void testSaveHoaDonChiTiet_NullHoaDon() {
        spchitiet spChiTiet = new spchitiet(1);

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(null);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(5);
        hdct.setDongia(10.0);
        hdct.setTrangThai(true);

        assertThrows(Exception.class, () -> hdctr1.saveHoaDonChiTiet(hdct), "Không được phép lưu khi hóa đơn là null.");
    }
    @Test
    void testSaveHoaDonChiTiet_NullSpChiTiet() {
        hoadon hoaDon = new hoadon(1);

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(null);
        hdct.setSoluong(5);
        hdct.setDongia(20.0);
        hdct.setTrangThai(true);

        assertThrows(Exception.class, () -> hdctr1.saveHoaDonChiTiet(hdct), "spct trong ne");
    }
    @Test
    void testSaveHoaDonChiTiet_SoluongAm() {
        hoadon hoaDon = new hoadon(1);
        spchitiet spChiTiet = new spchitiet(1);

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(-1);
        hdct.setDongia(1000.0);
        hdct.setTrangThai(true);

        assertThrows(Exception.class, () -> hdctr1.saveHoaDonChiTiet(hdct), "Số lượng không thể âm.");
    }
    @Test
    void testSaveHoaDonChiTiet_soluongnull() {
        hoadon hoaDon = new hoadon(1);
        spchitiet spChiTiet = new spchitiet(1);

        hdchitiet hdct2 = new hdchitiet();
        hdct2.setHoaDon(hoaDon);
        hdct2.setSpChiTiet(spChiTiet);
        hdct2.setSoluong(null);
        hdct2.setDongia(2000.0);
        hdct2.setTrangThai(true);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hdct2);
        });
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size());
        assertEquals("Số lượng không thể trống.", exception.getMessage());

    }
    @Test
    void testSaveHoaDonChiTiet_DongiaAm() {
        hoadon hoaDon = new hoadon(1);
        spchitiet spChiTiet = new spchitiet(1);

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(5);
        hdct.setDongia(-100.0);
        hdct.setTrangThai(true);

        assertThrows(Exception.class, () -> hdctr1.saveHoaDonChiTiet(hdct), "Đơn giá không thể âm.");
    }
    @Test
    void testSaveHoaDonChiTiet_dongianull() {
        hoadon hoaDon = new hoadon(1);
        spchitiet spChiTiet = new spchitiet(2);

        hdchitiet hdct2 = new hdchitiet();
        hdct2.setHoaDon(hoaDon);
        hdct2.setSpChiTiet(spChiTiet);
        hdct2.setSoluong(10);
        hdct2.setDongia(null);
        hdct2.setTrangThai(true);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hdct2);
        });
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size());
        assertEquals("Đơn giá không thể trôngs.", exception.getMessage());

    }
    @Test
    void testSaveHoaDonChiTiet_NoId() {
        hdchitiet hdct = new hdchitiet();
        hoadon hoaDon = new hoadon(1);
        spchitiet spChiTiet = new spchitiet(1);

        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(5);
        hdct.setDongia(100000.0);
        hdct.setTrangThai(true);

        hdchitiet savedHdct = hdctr1.saveHoaDonChiTiet(hdct);

        assertNotNull(savedHdct.getId(), "ID tự sinh không được null.");
    }
    @Test
    void testSaveHoaDonChiTiet_AllNull() {
        hdchitiet hdct = new hdchitiet();

        assertThrows(Exception.class, () -> hdctr1.saveHoaDonChiTiet(hdct), "Không được phép lưu với dữ liệu null.");
    }


//------------------------------------------------------------------------------------

    @Test
    void testSearchByIdHdct_ValidId() {
        Integer validId = 1; // ID hợp lệ tồn tại trong database
        List<hdchitiet> result = hdctr1.searchByIdHdct(validId);

        assertNotNull(result, "Kết quả không được null.");
        assertFalse(result.isEmpty(), "Danh sách kết quả không được rỗng.");
        assertEquals(validId, result.get(0).getId(), "ID của hóa đơn chi tiết không khớp.");
    }
    @Test
    void testSearchByIdHdct_InvalidId() {
        Integer invalidId = 98765; // ID không tồn tại
        List<hdchitiet> result = hdctr1.searchByIdHdct(invalidId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), "Kết quả phải rỗng khi ID không tồn tại.");
    }
    @Test
    void testSearchByIdHdct_NullId() {
        Integer nullId = null;

        assertThrows(Exception.class, () -> hdctr1.searchByIdHdct(nullId), "ngoại lệ khi ID là null.");
    }
    @Test
    void testSearchByIdHdct_NegativeId() {
        Integer negativeId = -99; // ID không hợp lệ
        List<hdchitiet> result = hdctr1.searchByIdHdct(negativeId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), "Kết quả phải rỗng khi ID là số âm.");
    }
    @Test
    void testSearchByIdHdct_MaxId() {
        Integer maxId = Integer.MAX_VALUE;
        List<hdchitiet> result = hdctr1.searchByIdHdct(maxId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), "Kết quả phải rỗng khi ID lớn nhất không tồn tại.");
    }
    @Test
    void testSearchByIdHdct_MinId() {
        Integer minId = Integer.MIN_VALUE;
        List<hdchitiet> result = hdctr1.searchByIdHdct(minId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), "Kết quả phải rỗng khi ID nhỏ nhất không tồn tại.");
    }
    @Test
    void testSearchByIdHdct_MultipleRecords() {
        Integer idtrue = 2;
        List<hdchitiet> result = hdctr1.searchByIdHdct(idtrue);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.size() > 1, "Kết quả phải trả về nhiều bản ghi.");
        assertEquals(idtrue, result.get(0).getId(), "ID của bản ghi đầu tiên không khớp.");
    }
    @Test
    void testSearchByIdHdct_TrangThaiFalse() {
        Integer id = 3; // ID hợp lệ có bản ghi với `trangThai = false`
        List<hdchitiet> result = hdctr1.searchByIdHdct(id);

        assertNotNull(result, "Kết quả không được null.");
        assertFalse(result.isEmpty(), "Danh sách không được rỗng.");
        assertFalse(result.get(0).isTrangThai(), "Trạng thái của bản ghi đầu tiên phải là false.");
    }

    //------------------------------------------------------------
    @Test
    void testGetHoaDonChiTietByHoaDonId_ValidHoaDonId() {
        Integer validHoaDonId = 1; // ID của hóa đơn hợp lệ
        List<hdchitiet> result = hdctr1.getHoaDonChiTietByHoaDonId(validHoaDonId);

        assertNotNull(result, "Kết quả không được null.");
        assertFalse(result.isEmpty(), "Danh sách kết quả không được rỗng.");
        assertEquals(validHoaDonId, result.get(0).getHoaDon().getId(), "ID hóa đơn không khớp.");
    }
    @Test
    void testGetHoaDonChiTietByHoaDonId_InvalidHoaDonId() {
        Integer invalidHoaDonId = 999; // ID không tồn tại
        List<hdchitiet> result = hdctr1.getHoaDonChiTietByHoaDonId(invalidHoaDonId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), "Danh sách kết quả phải rỗng khi hóa đơn không tồn tại.");
    }
    @Test
    void testGetHoaDonChiTietByHoaDonId_NullHoaDonId() {
        Integer nullHoaDonId = null;

        assertThrows(Exception.class, () -> hdctr1.getHoaDonChiTietByHoaDonId(nullHoaDonId), "id hoa don bị null");
    }
    @Test
    void testGetHoaDonChiTietByHoaDonId_NegativeHoaDonId() {
        Integer negativeHoaDonId = -99; // ID không hợp lệ
        List<hdchitiet> result = hdctr1.getHoaDonChiTietByHoaDonId(negativeHoaDonId);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.isEmpty(), " hoaDonId là số âm.");
    }
    @Test
    void testGetHoaDonChiTietByHoaDonId_MultipleRecords() {
        Integer hoaDonIdWithMultipleRecords = 2; // ID có nhiều bản ghi
        List<hdchitiet> result = hdctr1.getHoaDonChiTietByHoaDonId(hoaDonIdWithMultipleRecords);

        assertNotNull(result, "Kết quả không được null.");
        assertTrue(result.size() > 1, "Danh sách kết quả phải chứa nhiều bản ghi.");
        assertEquals(hoaDonIdWithMultipleRecords, result.get(0).getHoaDon().getId(), "ID của hóa đơn không khớp.");
    }

    //------------------------------------------------------------------------------------
    @Test
    void testUpdateHoaDonChiTiet_ValidData(){
        spchitiet sanPhamChiTiet = SPCTR.findById(1).get();
        hoadon hoaDon = hdr.findById(1).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(sanPhamChiTiet);
        hoaDonChiTiet.setSoluong(22);
        hoaDonChiTiet.setDongia(22000.0);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size() + 1);
        assertEquals(22, hoaDonChiTiet.getSoluong());
        assertEquals(22000.0, hoaDonChiTiet.getDongia());
        assertEquals(true, hoaDonChiTiet.isTrangThai());

    }

    @Test
    void testUpdateHoaDonChiTiet_SoLuongnull(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(2).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(spChiTiet);
        hoaDonChiTiet.setSoluong(null);
        hoaDonChiTiet.setDongia(10.0);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> List = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        });
        List<hdchitiet>List1 = hdctr1.getAllHoaDonChiTiet();
        assertEquals(List1.size(), List.size());
        assertEquals("Số lượng không thể trống.", exception.getMessage());

    }
    @Test
    void testUpdateHoaDonChiTiet_SoLuongAm(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(1).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(spChiTiet);
        hoaDonChiTiet.setSoluong(-89);
        hoaDonChiTiet.setDongia(220.0);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> List = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        });

        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), List.size());
        assertEquals("Số lượng không thể âm.", exception.getMessage());

    }
    @Test
    void testUpdateHoaDonChiTiet_sspctnull(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(2).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(null);
        hoaDonChiTiet.setSoluong(9);
        hoaDonChiTiet.setDongia(10.0);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> List = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        });
        List<hdchitiet>List1 = hdctr1.getAllHoaDonChiTiet();
        assertEquals(List1.size(), List.size());
        assertEquals("spct trong ne", exception.getMessage());

    }
    @Test
    void testUpdateHoaDonChiTiet_hoadonnull(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(2).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(null);
        hoaDonChiTiet.setSpChiTiet(spChiTiet);
        hoaDonChiTiet.setSoluong(9);
        hoaDonChiTiet.setDongia(10.0);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> List = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        });
        List<hdchitiet>List1 = hdctr1.getAllHoaDonChiTiet();
        assertEquals(List1.size(), List.size());
        assertEquals("Không được phép lưu khi hóa đơn là null.", exception.getMessage());

    }



    @Test
    void testUpdateHoaDonChiTiet_DonGiaNull(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(1).get();

        hdchitiet hoaDonChiTiet = new hdchitiet();
        hoaDonChiTiet.setHoaDon(hoaDon);
        hoaDonChiTiet.setSpChiTiet(spChiTiet);
        hoaDonChiTiet.setSoluong(22);
        hoaDonChiTiet.setDongia(null);
        hoaDonChiTiet.setTrangThai(true);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hoaDonChiTiet);
        });
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size());
        assertEquals("Đơn giá không thể trôngs.", exception.getMessage());

    }
    @Test
    void testUpdateHoaDonChiTiet_DonGiaAm(){
        hoadon hoaDon = hdr.findById(1).get();
        spchitiet spChiTiet = SPCTR.findById(1).get();

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spChiTiet);
        hdct.setSoluong(17);
        hdct.setDongia(-240.0);
        hdct.setTrangThai(true);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hdct);
        });
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size());
        assertEquals("Đơn giá không thể âm.", exception.getMessage());
    }

    @Test
    void testUpdateHoaDonChiTiet_NullTrangThai() {
        spchitiet spct = SPCTR.findById(1).get();
        hoadon hoaDon = hdr.findById(2).get();

        hdchitiet hdct = new hdchitiet();
        hdct.setHoaDon(hoaDon);
        hdct.setSpChiTiet(spct);
        hdct.setSoluong(5);
        hdct.setDongia(4000.0);
        hdct.setTrangThai(false);

        List<hdchitiet> oldList = hdctr1.getAllHoaDonChiTiet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hdctr1.saveHoaDonChiTiet(hdct);
        });
        List<hdchitiet> newList = hdctr1.getAllHoaDonChiTiet();
        assertEquals(newList.size(), oldList.size());
        assertEquals("Trạng thái không được để trống", exception.getMessage());
    }

}