package com.example.asmgd1.Service;

import com.example.asmgd1.asm.model.NhanVien;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NhanVienServiceTest {
    NhanVienService service ;
    String  expected, actual;;
    @BeforeEach
    void setUp() {
        service  = new NhanVienService();
        service.addNhanVien(new NhanVien(1, "Nguyen Van A", "NV01", "nva", "123456", true));
        service.addNhanVien(new NhanVien(2, "Nguyen Thi B", "NV02", "ntb", "abcdef", true));
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    //Thêm Nhân Viên thành công
    @Test
    void addNhanVien_successful() {
        NhanVien nv = new NhanVien(3, "Nguyen Van A", "NV01", "nva", "123456", true);
        expected = "Thêm nhân viên thành công";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công với tên nhân viên null
    @Test
    void addNhanVien_missingName() {
        NhanVien nv = new NhanVien(4, null, "NV02", "nvb", "password", true);
        expected = "Tên nhân viên không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công với tên nhân viên để trống
    @Test
    void addNhanVien_emptyName() {
        NhanVien nv = new NhanVien(5, "", "NV03", "nvc", "password", true);
        expected = "Tên nhân viên không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công do trường mã nhân viên null
    @Test
    void addNhanVien_missingCode() {
        NhanVien nv = new NhanVien(6, "Nguyen Van D", null, "nvd", "password", true);
        expected = "Mã nhân viên không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    //Thêm nhân viên không thành công với trường mã nhân viên để trống và các trường còn lại hợp lệ
    @Test
    void addNhanVien_emptyCode() {
        NhanVien nv = new NhanVien(7, "Nguyen Van E", "", "nve", "password", true);
        expected = "Mã nhân viên không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công với trường tên đăng nhập null và các trường còn lại hợp lệ
    @Test
    void addNhanVien_missingUsername() {
        NhanVien nv = new NhanVien(8, "Nguyen Van F", "NV06", null, "password", true);
        expected = "Tên đăng nhập không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }

    // Thêm nhân viên không thành công với trường tên đăng nhập bỏ trống và các trường còn lại hợp lệ
    @Test
    void addNhanVien_emptyUsername() {
        NhanVien nv = new NhanVien(9, "Nguyen Van G", "NV07", "", "password", true);
        expected = "Tên đăng nhập không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công với trường mật khẩu null và các trường còn lại hợp lệ
    @Test
    void addNhanVien_missingPassword() {
        NhanVien nv = new NhanVien(10, "Nguyen Van H", "NV08", "nvh", null, true);
        expected = "Mật khẩu không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công với trường mật khẩu bỏ trống và các trường còn lại hợp lệ
    @Test
    void addNhanVien_emptyPassword() {
        NhanVien nv = new NhanVien(11, "Nguyen Van I", "NV09", "nvi", "", true);
        expected = "Mật khẩu không được để trống";
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }


    // Thêm nhân viên không thành công do ID đã tồn tại
    @Test
    void addNhanVien_duplicateId() {
        NhanVien nv = new NhanVien(3, "Nguyen Van K", "NV11", "nvk", "123456", true);  // Same ID
        expected = "Thêm nhân viên không thành công id đã tồn tại "; // Chưa kiểm tra trùng ID trong logic mẫu
        actual = service.addNhanVien(nv);
        assertEquals(expected, actual);
    }

    //Done TestThem




    // Sua
    @Test
    void updateNhanVien_validData() {
        NhanVien nv = new NhanVien(1, "Nguyen Van A", "NV01", "nva", "123456", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(1, "Nguyen Van A Updated", "NV01", "nva_updated", "654321", false);
        expected = "Cập nhật nhân viên thành công";
        actual = service.updateNhanVien(1, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_invalidName() {
        NhanVien nv = new NhanVien(2, "Nguyen Van B", "NV02", "nvb", "123456", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(2, "", "NV02", "nvb", "654321", true);
        expected = "Tên nhân viên không được để trống";
        actual = service.updateNhanVien(2, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_invalidCode() {
        NhanVien nv = new NhanVien(3, "Nguyen Van C", "NV03", "nvc", "123456", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(3, "Nguyen Van C", "", "nvc", "654321", true);
        expected = "Mã nhân viên không được để trống";
        actual = service.updateNhanVien(3, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_missingEmployee() {
        NhanVien updatedInfo = new NhanVien(99, "Nguyen Van D", "NV99", "nvd", "123456", true);
        expected = "Không tìm thấy nhân viên với ID 99";
        actual = service.updateNhanVien(99, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_invalidUsername() {
        NhanVien nv = new NhanVien(4, "Nguyen Van E", "NV04", "nve", "password", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(4, "Nguyen Van E", "NV04", "", "654321", true);
        expected = "Tên đăng nhập không được để trống";
        actual = service.updateNhanVien(4, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_invalidPassword() {
        NhanVien nv = new NhanVien(5, "Nguyen Van F", "NV05", "nvf", "123456", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(5, "Nguyen Van F", "NV05", "nvf", "", true);
        expected = "Mật khẩu không được để trống";
        actual = service.updateNhanVien(5, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_toggleStatus() {
        NhanVien nv = new NhanVien(6, "Nguyen Van G", "NV06", "nvg", "password", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(6, "Nguyen Van G", "NV06", "nvg", "password", false);
        expected = "Cập nhật nhân viên thành công";
        actual = service.updateNhanVien(6, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_checkStatusChange() {
        NhanVien nv = new NhanVien(7, "Nguyen Van H", "NV07", "nvh", "123456", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(7, "Nguyen Van H", "NV07", "nvh", "123456", false);
        service.updateNhanVien(7, updatedInfo);

        // After update, the status should be false
        assertFalse(service.findById(7).isTrangThai());
    }

    @Test
    void updateNhanVien_sameDataNoChange() {
        NhanVien nv = new NhanVien(8, "Nguyen Van I", "NV08", "nvi", "654321", true);
        service.addNhanVien(nv);  // Add initial employee

        NhanVien updatedInfo = new NhanVien(8, "Nguyen Van I", "NV08", "nvi", "654321", true); // No change
        expected = "Dữ liệu không thay đổi";
        actual = service.updateNhanVien(8, updatedInfo);
        assertEquals(expected, actual);
    }

    @Test
    void updateNhanVien_invalidId() {
        NhanVien updatedInfo = new NhanVien(0, "Nguyen Van J", "NV09", "nvj", "password", true);
        expected = "ID không hợp lệ";
        actual = service.updateNhanVien(0, updatedInfo);
        assertEquals(expected, actual);
    }
    // Done update







    //Xoa Min 1 Max 2
    @Test
    void deleteNhanVien_existingEmployee() {
        expected = "Xóa nhân viên thành công";
        actual = service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_nonExistingEmployee() {
        expected = "Không tìm thấy nhân viên với ID 999";
        actual = service.deleteNhanVien(999);  // Xóa nhân viên không tồn tại
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_multipleDelete() {
        service.deleteNhanVien(1);  // Xóa nhân viên ID = 1
        expected = "Không tìm thấy nhân viên với ID 1";
        actual = service.deleteNhanVien(1);  // Thử xóa lại nhân viên ID = 1
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_firstEmployee() {
        expected = "Xóa nhân viên thành công";
        actual = service.deleteNhanVien(1);  // Xóa nhân viên đầu tiên
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_lastEmployee() {
        expected = "Xóa nhân viên thành công";
        actual = service.deleteNhanVien(2);  // Xóa nhân viên cuối cùng
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_emptyList() {
        service.deleteNhanVien(1);  // Xóa nhân viên đầu tiên
        service.deleteNhanVien(2);  // Xóa nhân viên cuối cùng
        expected = "Không tìm thấy nhân viên với ID 1";
        actual = service.deleteNhanVien(1);  // Thử xóa khi danh sách đã trống
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_noEmployeeInList() {
        service.deleteNhanVien(1);  // Xóa nhân viên đầu tiên
        expected = "Không tìm thấy nhân viên với ID 2";
        actual = service.deleteNhanVien(2);  // Thử xóa nhân viên không tồn tại trong danh sách
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_checkListSize() {
        service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        service.deleteNhanVien(2);  // Xóa nhân viên với ID = 2
        assertTrue(service.getAllNhanVien().isEmpty());  // Kiểm tra danh sách đã trống
    }

    @Test
    void deleteNhanVien_checkRemainingAfterDelete() {
        service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        expected = "Xóa nhân viên thành công";
        actual = service.deleteNhanVien(2);  // Xóa nhân viên với ID = 2
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }

    @Test
    void deleteNhanVien_checkDeleteEffectiveness() {
        expected = "Xóa nhân viên thành công";
        actual = service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        assertEquals(expected, actual);  // Kiểm tra kết quả
    }
    //Done delete




    // Hiển thị danh sách
    @Test
    void getAllNhanVien_emptyList() {
        service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        service.deleteNhanVien(2);  // Xóa nhân viên với ID = 2
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách nhân viên
        assertTrue(actual.isEmpty());  // Kiểm tra nếu danh sách trống
    }


    @Test
    void getAllNhanVien_twoEmployees() {
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách với 2 nhân viên
        assertEquals(2, actual.size());  // Kiểm tra số lượng nhân viên
        assertTrue(actual.stream().anyMatch(nv -> nv.getTennv().equals("Nguyen Van A")));  // Kiểm tra nếu có nhân viên "Nguyen Van A"
        assertTrue(actual.stream().anyMatch(nv -> nv.getTennv().equals("Nguyen Thi B")));  // Kiểm tra nếu có nhân viên "Nguyen Thi B"
    }



    @Test
    void getAllNhanVien_checkEmployeeOrder() {
        service.addNhanVien(new NhanVien(3, "Tran Thi C", "NV03", "ttc", "123123", true));  // Thêm nhân viên
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy tất cả nhân viên
        assertEquals("Nguyen Van A", actual.get(0).getTennv());  // Kiểm tra nhân viên đầu tiên trong danh sách là "Nguyen Van A"
    }


    @Test
    void getAllNhanVien_checkMultipleEmployees() {
        service.addNhanVien(new NhanVien(3, "Tran Thi C", "NV03", "ttc", "123123", true));  // Thêm nhân viên
        service.addNhanVien(new NhanVien(4, "Le Thi E", "NV04", "lte", "qwerty", true));  // Thêm nhân viên
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy tất cả nhân viên
        assertTrue(actual.size() > 2);  // Kiểm tra nếu có ít nhất 3 nhân viên
    }

    @Test
    void getAllNhanVien_checkEmployeePresenceInList() {
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách tất cả nhân viên
        assertTrue(actual.stream().anyMatch(nv -> nv.getTennv().equals("Nguyen Van A")));  // Kiểm tra xem nhân viên "Nguyen Van A" có trong danh sách không
    }

    @Test
    void getAllNhanVien_checkEmployeeNotPresent() {
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách tất cả nhân viên
        assertFalse(actual.stream().anyMatch(nv -> nv.getTennv().equals("Nguyen Thi X")));  // Kiểm tra xem nhân viên "Nguyen Thi X" có trong danh sách không
    }

    @Test
    void getAllNhanVien_checkForNewEmployee() {
        service.addNhanVien(new NhanVien(5, "Pham Thi F", "NV05", "ptf", "xyz123", true));  // Thêm nhân viên mới
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách nhân viên
        assertTrue(actual.stream().anyMatch(nv -> nv.getTennv().equals("Pham Thi F")));  // Kiểm tra xem nhân viên "Pham Thi F" có trong danh sách
    }

    @Test
    void getAllNhanVien_checkEmployeeOrderAfterDeletion() {
        service.deleteNhanVien(2);  // Xóa nhân viên với ID = 2
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách nhân viên
        assertEquals("Nguyen Van A", actual.get(0).getTennv());  // Kiểm tra nếu nhân viên đầu tiên trong danh sách là "Nguyen Van A"
        assertEquals("Nguyen Thi B", actual.get(1).getTennv());  // Kiểm tra nếu nhân viên thứ hai là "Nguyen Thi B"
    }

    @Test
    void getAllNhanVien_checkEmployeeCountAfterAddition() {
        service.addNhanVien(new NhanVien(3, "Le Thi G", "NV06", "ltg", "qwerty", true));  // Thêm một nhân viên
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách nhân viên
        assertEquals(3, actual.size());  // Kiểm tra số lượng nhân viên sau khi thêm mới
    }

    @Test
    void getAllNhanVien_checkListAfterClear() {
        service.deleteNhanVien(1);  // Xóa nhân viên với ID = 1
        service.deleteNhanVien(2);  // Xóa nhân viên với ID = 2
        List<NhanVien> actual = service.getAllNhanVien();  // Lấy danh sách nhân viên
        assertTrue(actual.isEmpty());  // Kiểm tra nếu danh sách trống sau khi xóa tất cả nhân viên
    }

    // Done hiển thị




    // Tìm kiếm
    @Test
    void testGetNhanVienById_existingId() {
        NhanVien actualNhanVien = service.getNhanVienById(1);
        expected = "Nguyen Van A";
        assertNotNull(actualNhanVien);
        actual = actualNhanVien.getTennv();
        assertEquals(expected, actual);
    }

    @Test
    void testGetNhanVienById_nonExistingId() {
        NhanVien actualNhanVien = service.getNhanVienById(4);
        expected = null;
        assertNull(actualNhanVien);
    }

    @Test
    void testGetNhanVienById_checkInactiveEmployee() {
        NhanVien actualNhanVien = service.getNhanVienById(3);
        expected = "Tran Thi C";
        assertNotNull(actualNhanVien);
        actual = actualNhanVien.getTennv();
        assertEquals(expected, actual);
        assertFalse(actualNhanVien.isTrangThai());
    }

    @Test
    void testGetNhanVienById_checkAfterDelete() {
        service.deleteNhanVien(1);
        NhanVien actualNhanVien = service.getNhanVienById(1);
        expected = null;
        assertNull(actualNhanVien);
    }

    // Test case tìm kiếm theo tên
    @Test
    void testGetNhanVienById_invalidId() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getNhanVienById(-1);  // Thử với ID không hợp lệ
        });
        assertEquals("ID phải là một số dương.", exception.getMessage());
    }

    @Test
    void testGetNhanVienByName_invalidName_empty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getNhanVienByName("");  // Thử với tên rỗng
        });
        assertEquals("Tên không được để trống.", exception.getMessage());
    }

    @Test
    void testGetNhanVienByName_invalidName_numbers() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getNhanVienByName("123456");  // Thử với tên là số
        });
        assertEquals("Tên không thể chứa toàn bộ số.", exception.getMessage());
    }

    @Test
    void testGetNhanVienById_validId() {
        NhanVien actual = service.getNhanVienById(1);  // Tìm kiếm nhân viên có ID = 1
        assertNotNull(actual);  // Kiểm tra xem nhân viên có được tìm thấy không
        assertEquals("Nguyen Van A", actual.getTennv());  // Kiểm tra tên nhân viên
    }

    @Test
    void testGetNhanVienById_notFound() {
        NhanVien actual = service.getNhanVienById(999);  // Tìm kiếm nhân viên không tồn tại
        assertNull(actual);  // Kiểm tra kết quả trả về là null (không tìm thấy nhân viên)
    }


    @Test
    void testGetNhanVienByName_validName() {
        List<NhanVien> actual = service.getNhanVienByName("Nguyen Thi B");  // Tìm kiếm nhân viên có tên "Nguyen Thi B"
        assertNotNull(actual);  // Kiểm tra danh sách không null
        assertEquals(1, actual.size());  // Kiểm tra chỉ có 1 nhân viên trong danh sách
        assertEquals("Nguyen Thi B", actual.get(0).getTennv());  // Kiểm tra tên nhân viên
    }


    @Test
    void testGetNhanVienByName_notFound() {
        List<NhanVien> actual = service.getNhanVienByName("Nguyen Thi C");  // Tìm kiếm nhân viên không tồn tại
        assertTrue(actual.isEmpty());  // Kiểm tra kết quả trả về là danh sách rỗng
    }



    @Test
    void testGetNhanVienByName_ignoreCase() {
        List<NhanVien> actual = service.getNhanVienByName("nguyen thi b");  // Tìm kiếm nhân viên với tên viết thường
        assertNotNull(actual);  // Kiểm tra danh sách không null
        assertEquals(1, actual.size());  // Kiểm tra chỉ có 1 nhân viên trong danh sách
        assertEquals("Nguyen Thi B", actual.get(0).getTennv());  // Kiểm tra tên nhân viên
    }

    @Test
    void testGetNhanVienByName_emptyName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getNhanVienByName("");  // Tìm kiếm với tên rỗng
        });
        assertEquals("Tên không được để trống.", exception.getMessage());
    }

    @Test
    void testGetNhanVienByName_nameIsNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getNhanVienByName("12345");  // Tìm kiếm với tên chỉ chứa số
        });
        assertEquals("Tên không thể chứa toàn bộ số.", exception.getMessage());
    }



}