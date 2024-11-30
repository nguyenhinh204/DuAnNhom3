package com.example.asmgd1.asm.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class loginTest {

    @BeforeEach
    void setUp() {
        // Setup trước khi mỗi test case được chạy (có thể là khởi tạo dữ liệu mẫu)
    }

    @AfterEach
    void tearDown() {
        // Cleanup sau khi mỗi test case được chạy (có thể là xóa dữ liệu hoặc khôi phục trạng thái)
    }

    @Test
    void testLoginValidCredentials() {
        // Test với thông tin đăng nhập hợp lệ
        String username = "validUser";
        String password = "validPassword";
        assertTrue(login(username, password), "Login should be successful with valid credentials.");
    }

    @Test
    void testLoginInvalidUsername() {
        // Test với tên người dùng không hợp lệ
        String username = "invalidUser";
        String password = "validPassword";
        assertFalse(login(username, password), "Login should fail with an invalid username.");
    }

    @Test
    void testLoginInvalidPassword() {
        // Test với mật khẩu không hợp lệ
        String username = "validUser";
        String password = "invalidPassword";
        assertFalse(login(username, password), "Login should fail with an invalid password.");
    }

    @Test
    void testLoginEmptyUsername() {
        // Test với tên người dùng để trống
        String username = "";
        String password = "validPassword";
        assertFalse(login(username, password), "Login should fail with an empty username.");
    }

    @Test
    void testLoginEmptyPassword() {
        // Test với mật khẩu để trống
        String username = "validUser";
        String password = "";
        assertFalse(login(username, password), "Login should fail with an empty password.");
    }

    @Test
    void testLoginNullUsername() {
        // Test với tên người dùng là null
        String username = null;
        String password = "validPassword";
        assertFalse(login(username, password), "Login should fail with a null username.");
    }

    @Test
    void testLoginNullPassword() {
        // Test với mật khẩu là null
        String username = "validUser";
        String password = null;
        assertFalse(login(username, password), "Login should fail with a null password.");
    }

    @Test
    void testLoginInvalidCredentialsFormat() {
        // Test với định dạng tên người dùng hoặc mật khẩu sai (ví dụ, ký tự đặc biệt không hợp lệ)
        String username = "user@name";
        String password = "pass@word";
        assertFalse(login(username, password), "Login should fail with invalid format credentials.");
    }

    @Test
    void testLoginSQLInjectionAttempt() {
        // Test với cố gắng SQL Injection trong tên người dùng
        String username = "validUser' OR '1'='1";
        String password = "password";
        assertFalse(login(username, password), "Login should fail with a SQL injection attempt.");
    }

    @Test
    void testLoginCaseSensitivity() {
        // Test với sự phân biệt chữ hoa chữ thường trong tên người dùng và mật khẩu
        String username = "ValidUser";
        String password = "ValidPassword";
        assertTrue(login(username, password), "Login should be case-sensitive and successful with correct credentials.");
    }

    // Giả sử phương thức login() là phương thức kiểm tra đăng nhập
    private boolean login(String username, String password) {
        // Đây là ví dụ phương thức đăng nhập, cần thay thế bằng logic thực tế
        if (username == null || password == null) {
            return false;
        }
        if (username.equals("validUser") && password.equals("validPassword")) {
            return true;
        }
        return false;
    }
}
