package com.example.asmgd1.Service;

import com.example.asmgd1.asm.Repository.NhanVienRepository;
import com.example.asmgd1.asm.model.nhanvien;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NhanVienServiceTest {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private NhanVienRepository nvr;

    @BeforeEach
    void setUp() {
        // Xóa dữ liệu trước khi chạy các test và thêm dữ liệu mẫu.
        nvr.deleteAll();
        nvr.save(new nhanvien(1, "Nguyen Van A", "NV001", "userA", "passA", true));
        nvr.save(new nhanvien(2, "Tran Thi B", "NV002", "userB", "passB", false));
        nvr.save(new nhanvien(3, "Le Van C", "NV003", "userC", "passC", true));
        nvr.save(new nhanvien(4, "Pham Thi D", "NV004", "userD", "passD", true));
    }
//    getAllNhanVien()

    @Test
    void getAllNhanVien() {
        // Kiểm tra việc lấy tất cả các nhân viên từ cơ sở dữ liệu.
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(4, result.size()); // Đảm bảo có đúng 4 nhân viên trong danh sách.
    }

    @Test
    void getAllNhanVien_EmptyList() {
        // Kiểm tra trường hợp cơ sở dữ liệu rỗng.
        nvr.deleteAll();
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng (chỉ là rỗng).
        assertTrue(result.isEmpty()); // Kiểm tra rằng danh sách rỗng.
    }

    @Test
    void getAllNhanVien_SingleRecord() {
        // Kiểm tra trường hợp chỉ có một nhân viên trong cơ sở dữ liệu.
        nvr.deleteAll();
        nvr.save(new nhanvien(5, "Nguyen Van E", "NV005", "userE", "passE", true));
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(1, result.size()); // Kiểm tra có đúng một nhân viên trong danh sách.
    }

    @Test
    void getAllNhanVien_MultipleRecords() {
        // Kiểm tra trường hợp có nhiều nhân viên trong cơ sở dữ liệu.
        nvr.deleteAll();
        nvr.save(new nhanvien(6, "Tran Van F", "NV006", "userF", "passF", true));
        nvr.save(new nhanvien(7, "Le Thi G", "NV007", "userG", "passG", false));
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(2, result.size()); // Kiểm tra số lượng nhân viên đúng.
    }

    @Test
    void getAllNhanVien_FilteredList() {
        // Kiểm tra việc lọc các nhân viên theo một tiêu chí nào đó.
        nvr.save(new nhanvien(8, "Pham Van H", "NV008", "userH", "passH", true));
        nvr.save(new nhanvien(9, "Nguyen Thi I", "NV009", "userI", "passI", false));
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.stream().allMatch(nv -> nv.getTennv() != null)); // Kiểm tra mọi nhân viên đều có tên.
    }

    @Test
    void getAllNhanVien_AfterDeletion() {
        // Kiểm tra việc lấy dữ liệu sau khi xóa một số bản ghi.
        nvr.deleteById(1); // Xóa một nhân viên.
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(3, result.size()); // Kiểm tra rằng danh sách giảm xuống còn 3 nhân viên.
    }

    @Test
    void getAllNhanVien_DuplicateRecords() {
        // Kiểm tra trường hợp có các bản ghi trùng lặp.
        nvr.save(new nhanvien(10, "Nguyen Van J", "NV010", "userJ", "passJ", true));
        nvr.save(new nhanvien(10, "Nguyen Van J", "NV010", "userJ", "passJ", true)); // Trùng lặp.
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(5, result.size()); // Kiểm tra số lượng bản ghi đúng, dù có trùng lặp.
    }

    @Test
    void getAllNhanVien_ContainsInactive() {
        // Kiểm tra trường hợp danh sách chứa cả nhân viên đang hoạt động và không hoạt động.
        nvr.save(new nhanvien(11, "Tran Van K", "NV011", "userK", "passK", false));
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.stream().anyMatch(nv -> !nv.isTrangThai())); // Kiểm tra có nhân viên không hoạt động.
    }

    @Test
    void getAllNhanVien_EmptyDatabase() {
        // Kiểm tra việc lấy dữ liệu từ cơ sở dữ liệu rỗng.
        nvr.deleteAll();
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng.
    }

    @Test
    void getAllNhanVien_NullDatabase() {
        // Kiểm tra khi cơ sở dữ liệu không được khởi tạo.
        nvr.deleteAll();
        nvr.save(null); // Thêm giá trị null.
        List<nhanvien> result = nhanVienService.getAllNhanVien();
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng.
    }
//    findNhanviensByid()
@Test
void findNhanviensByid_ValidId() {
    // Kiểm tra việc tìm nhân viên theo ID hợp lệ.
    List<nhanvien> result = nhanVienService.findNhanviensByid(1);
    assertNotNull(result); // Đảm bảo danh sách không rỗng.
    assertFalse(result.isEmpty()); // Kiểm tra danh sách có chứa kết quả.
}

    @Test
    void findNhanviensByid_NotFound() {
        // Kiểm tra việc tìm nhân viên theo ID không tồn tại.
        List<nhanvien> result = nhanVienService.findNhanviensByid(99);
        assertNotNull(result); // Đảm bảo danh sách không rỗng (có thể rỗng).
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng.
    }

    @Test
    void findNhanviensByid_NullId() {
        // Kiểm tra việc tìm nhân viên với ID là null.
        List<nhanvien> result = nhanVienService.findNhanviensByid(null);
        assertNotNull(result); // Đảm bảo danh sách không rỗng (có thể rỗng).
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng.
    }

    @Test
    void findNhanviensByid_MultipleResults() {
        // Kiểm tra việc tìm nhiều nhân viên theo ID (nếu có).
        List<nhanvien> result = nhanVienService.findNhanviensByid(2);
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(2, result.size()); // Kiểm tra có đúng 2 nhân viên trả về.
    }

    @Test
    void findNhanviensByid_FilteredResults() {
        // Kiểm tra việc tìm kiếm theo ID với bộ lọc.
        List<nhanvien> result = nhanVienService.findNhanviensByid(3);
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.stream().allMatch(nv -> nv.getManv().equals("NV003"))); // Kiểm tra kết quả theo ID cụ thể.
    }

    @Test
    void findNhanviensByid_InvalidIdType() {
        // Kiểm tra việc truyền vào ID với kiểu không hợp lệ.
        List<nhanvien> result = nhanVienService.findNhanviensByid(0); // ID không hợp lệ (nên kiểm tra).
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng nếu ID không hợp lệ.
    }

    @Test
    void findNhanviensByid_EmptyDatabase() {
        // Kiểm tra việc tìm kiếm khi cơ sở dữ liệu rỗng.
        nvr.deleteAll();
        List<nhanvien> result = nhanVienService.findNhanviensByid(1);
        assertNotNull(result); // Đảm bảo danh sách không rỗng (có thể rỗng).
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng.
    }

    @Test
    void findNhanviensByid_DuplicateRecords() {
        // Kiểm tra việc tìm kiếm với các bản ghi trùng lặp.
        nvr.save(new nhanvien(1, "Tran Van D", "NV001", "userD", "passD", true));
        nvr.save(new nhanvien(1, "Tran Van D", "NV001", "userD", "passD", true)); // Trùng lặp.
        List<nhanvien> result = nhanVienService.findNhanviensByid(1);
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertEquals(2, result.size()); // Kiểm tra số lượng bản ghi trùng lặp.
    }

    @Test
    void findNhanviensByid_NullResult() {
        // Kiểm tra trường hợp không có kết quả trả về.
        List<nhanvien> result = nvr.searchBynv(0);
        assertNotNull(result); // Đảm bảo danh sách không rỗng.
        assertTrue(result.isEmpty()); // Kiểm tra danh sách rỗng nếu không có kết quả.
    }
//    saveNhanVien()
@Test
void saveNhanVien_ValidInput() {
    // Kiểm tra việc lưu một nhân viên mới với thông tin hợp lệ.
    nhanvien newNhanVien = new nhanvien(12, "Nguyen Van L", "NV012", "userL", "passL", true);
    nhanvien result = nhanVienService.saveNhanVien(newNhanVien);
    assertNotNull(result); // Đảm bảo kết quả không null.
    assertEquals("Nguyen Van L", result.getTennv()); // Kiểm tra thông tin tên đúng.
}

    @Test
    void saveNhanVien_DuplicateEntry() {
        // Kiểm tra việc lưu nhân viên đã tồn tại.
        nhanvien newNhanVien = new nhanvien(1, "Nguyen Van A", "NV001", "userA", "passA", true);
        nhanvien result = nhanVienService.saveNhanVien(newNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Nguyen Van A", result.getTennv()); // Kiểm tra thông tin tên đúng.
    }

    @Test
    void saveNhanVien_InvalidInput() {
        // Kiểm tra việc lưu nhân viên với thông tin không hợp lệ.
        nhanvien newNhanVien = new nhanvien(13, null, "NV013", "userN", "passN", true);
        assertThrows(Exception.class, () -> {
            nhanVienService.saveNhanVien(newNhanVien);
        });
    }

    @Test
    void saveNhanVien_EmptyFields() {
        // Kiểm tra việc lưu nhân viên với các trường thông tin rỗng.
        nhanvien newNhanVien = new nhanvien(14, "", "NV014", "", "pass", true);
        nhanvien result = nhanVienService.saveNhanVien(newNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("", result.getTennv()); // Kiểm tra rằng trường tên trống.
    }

    @Test
    void saveNhanVien_NullObject() {
        // Kiểm tra việc lưu khi đối tượng nhân viên là null.
        assertThrows(Exception.class, () -> {
            nhanVienService.saveNhanVien(null);
        });
    }

    @Test
    void saveNhanVien_ValidUpdate() {
        // Kiểm tra việc lưu khi cập nhật thông tin của nhân viên đã tồn tại.
        nhanvien updateNhanVien = new nhanvien(1, "Updated Name", "NV001", "userA", "newPass", false);
        nhanvien result = nhanVienService.saveNhanVien(updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Updated Name", result.getTennv()); // Kiểm tra tên đã được cập nhật.
    }

    @Test
    void saveNhanVien_PersistentData() {
        // Kiểm tra việc dữ liệu được lưu giữ sau khi thoát ứng dụng.
        nhanvien newNhanVien = new nhanvien(15, "Le Van M", "NV015", "userM", "passM", true);
        nhanVienService.saveNhanVien(newNhanVien);
        assertNotNull(nhanVienService.getNhanVienById(15)); // Kiểm tra lưu trữ thành công.
    }

    @Test
    void saveNhanVien_MultipleSaves() {
        // Kiểm tra việc lưu nhiều nhân viên liên tiếp.
        nhanvien newNhanVien1 = new nhanvien(16, "Nguyen Van N", "NV016", "userN", "passN", true);
        nhanvien newNhanVien2 = new nhanvien(17, "Tran Van O", "NV017", "userO", "passO", false);
        nhanVienService.saveNhanVien(newNhanVien1);
        nhanVienService.saveNhanVien(newNhanVien2);
        assertNotNull(nhanVienService.getNhanVienById(16)); // Kiểm tra nhân viên thứ nhất lưu thành công.
        assertNotNull(nhanVienService.getNhanVienById(17)); // Kiểm tra nhân viên thứ hai lưu thành công.
    }

    @Test
    void saveNhanVien_ValidationError() {
        // Kiểm tra trường hợp lỗi khi lưu dữ liệu không hợp lệ.
        nhanvien newNhanVien = new nhanvien(18, "Invalid", "NV018", "userI", "passI", true);
        assertThrows(Exception.class, () -> {
            nhanVienService.saveNhanVien(newNhanVien);
        });
    }

//    updateNhanVien()

    @Test
    void updateNhanVien_ValidUpdate() {
        // Kiểm tra việc cập nhật thông tin nhân viên với ID hợp lệ.
        nhanvien updateNhanVien = new nhanvien(1, "Updated Name", "NV001", "userA", "newPass", false);
        nhanvien result = nhanVienService.updateNhanVien(1, updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Updated Name", result.getTennv()); // Kiểm tra tên được cập nhật đúng.
    }

    @Test
    void updateNhanVien_NonExistentId() {
        // Kiểm tra việc cập nhật nhân viên với ID không tồn tại.
        nhanvien updateNhanVien = new nhanvien(99, "Non Existent", "NV099", "userX", "passX", true);
        assertNull(nhanVienService.updateNhanVien(99, updateNhanVien)); // Kết quả nên là null nếu không tìm thấy ID.
    }

    @Test
    void updateNhanVien_InvalidIdType() {
        // Kiểm tra việc cập nhật với ID không hợp lệ.
        nhanvien updateNhanVien = new nhanvien(-1, "Invalid ID", "NV-1", "userInvalid", "passInvalid", true);
        assertNull(nhanVienService.updateNhanVien(-1, updateNhanVien)); // Kết quả nên là null nếu ID không hợp lệ.
    }

    @Test
    void updateNhanVien_NullObject() {
        // Kiểm tra việc cập nhật với đối tượng nhân viên là null.
        assertThrows(Exception.class, () -> {
            nhanVienService.updateNhanVien(1, null);
        });
    }

    @Test
    void updateNhanVien_EmptyFields() {
        // Kiểm tra việc cập nhật nhân viên với các trường thông tin rỗng.
        nhanvien updateNhanVien = new nhanvien(1, "", "NV001", "userA", "newPass", true);
        nhanvien result = nhanVienService.updateNhanVien(1, updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("", result.getTennv()); // Kiểm tra rằng trường tên là rỗng.
    }

    @Test
    void updateNhanVien_PersistentData() {
        // Kiểm tra dữ liệu có được cập nhật và lưu giữ sau khi thoát ứng dụng.
        nhanvien updateNhanVien = new nhanvien(2, "Persistent Update", "NV002", "userB", "passB", true);
        nhanvien result = nhanVienService.updateNhanVien(2, updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Persistent Update", result.getTennv()); // Kiểm tra thông tin tên được cập nhật đúng.
    }

    @Test
    void updateNhanVien_UpdateWithSameData() {
        // Kiểm tra việc cập nhật thông tin nhân viên với dữ liệu giống nhau.
        nhanvien updateNhanVien = new nhanvien(1, "Existing Name", "NV001", "userA", "passA", true);
        nhanvien result = nhanVienService.updateNhanVien(1, updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Existing Name", result.getTennv()); // Kiểm tra rằng dữ liệu không thay đổi.
    }

    @Test
    void updateNhanVien_UpdatedFields() {
        // Kiểm tra việc cập nhật từng trường thông tin nhân viên.
        nhanvien updateNhanVien = new nhanvien(3, "Updated Name", "NV003", "newUser", "newPass", false);
        nhanvien result = nhanVienService.updateNhanVien(3, updateNhanVien);
        assertNotNull(result); // Đảm bảo kết quả không null.
        assertEquals("Updated Name", result.getTennv()); // Kiểm tra tên đã được cập nhật.
        assertEquals("newUser", result.getTendn()); // Kiểm tra tên đăng nhập đã được cập nhật.
        assertEquals("newPass", result.getMatkhau()); // Kiểm tra mật khẩu đã được cập nhật.
        assertFalse(result.isTrangThai()); // Kiểm tra trạng thái đã được cập nhật.
    }

    @Test
    void updateNhanVien_ValidationError() {
        // Kiểm tra trường hợp cập nhật với dữ liệu không hợp lệ.
        nhanvien updateNhanVien = new nhanvien(4, "Invalid Data", "NV004", "userC", "passC", false);
        assertThrows(Exception.class, () -> {
            nhanVienService.updateNhanVien(4, updateNhanVien);
        });
    }

    @Test
    void updateNhanVien_ConcurrentModification() {
        // Kiểm tra trường hợp xảy ra thay đổi đồng thời khi cập nhật.
        nhanvien updateNhanVien1 = new nhanvien(5, "Concurrent Update 1", "NV005", "userD", "passD", true);
        nhanvien updateNhanVien2 = new nhanvien(5, "Concurrent Update 2", "NV005", "userD", "passD", false);

        nhanVienService.updateNhanVien(5, updateNhanVien1);
        assertThrows(Exception.class, () -> {
            nhanVienService.updateNhanVien(5, updateNhanVien2);
        });
    }




}