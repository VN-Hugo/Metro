/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import Connection.ConnectionUtils;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
/**
 *
 *
 */
public class TaiKhoan {
    public int KiemTraTrungLap(String TenDangNhap, String Gmail) throws SQLException, ClassNotFoundException {
    int result = 2; // Kết quả mặc định (không trùng)

    try (Connection conn = ConnectionUtils.getMyConnection()) {
        String sql = "SELECT TenDangNhap, Gmail FROM TaiKhoan WHERE TenDangNhap = ? OR Gmail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, TenDangNhap);
            stmt.setString(2, Gmail);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String matchedTenDangNhap = rs.getString("TenDangNhap");
                    String matchedGmail = rs.getString("Gmail");

                    if (TenDangNhap.equals(matchedTenDangNhap)) {
                        result = -2000; // Trùng TenDangNhap
                    } else if (Gmail.equals(matchedGmail)) {
                        result = -3000; // Trùng Gmail
                    }
                }
            }
        }
    } catch (SQLException e) {
        // Xử lý lỗi và in chi tiết lỗi
        System.out.println("Error Code: " + e.getErrorCode());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Message: " + e.getMessage());
        throw e; // Có thể ném lại ngoại lệ nếu cần
    }

    return result;
}
      public int KiemTraTK(String Username, String Password, String Gmail) throws SQLException, ClassNotFoundException {
    int i = 3;

    try (Connection conn = ConnectionUtils.getMyConnection()) {
      
    } catch (SQLException e) {
        if(e.getErrorCode()== 1)
        {
            i= -2000;
        }
        // Print error details
        System.out.println("Error Code: " + e.getErrorCode());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Message: " + e.getMessage());
    }

    return i;
}
    public int themTaiKhoan(String Username, String Password,String Gmail) throws SQLException, ClassNotFoundException {
    int i = 0;

    try (Connection con = ConnectionUtils.getMyConnection()) {
        // Step 1: Find the largest MaTaiKhoan
        String getAllIdsQuery = "SELECT MaTaiKhoan FROM TaiKhoan";
        PreparedStatement getAllIdsStmt = con.prepareStatement(getAllIdsQuery);
        ResultSet rs = getAllIdsStmt.executeQuery();

        int maxId = 0; // Lưu giá trị lớn nhất
        while (rs.next()) {
            // Lấy phần số trong MaTaiKhoan (bỏ đi ký tự đầu)
            String maTaiKhoan = rs.getString("MaTaiKhoan");
            if (maTaiKhoan.startsWith("TKKH")) { // Kiểm tra nếu mã tài khoản bắt đầu bằng "TK"
                
                    maxId +=1 ; 
                
            }
        }


        // Step 2: Generate new MaTaiKhoan
        String newMaTaiKhoan = "TKKH" + String.format("%02d", maxId+1); // Format to "TKXX"

        // Step 3: Insert new account
        String query = "INSERT INTO TaiKhoan(MaTaiKhoan, TenDangNhap, MatKhau, Gmail) VALUES(?, ?, ?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, newMaTaiKhoan); // Auto-generated MaTaiKhoan
        ps.setString(2, Username);
        ps.setString(3, Password);
        ps.setString(4, Gmail);

        i = ps.executeUpdate();

        if (i > 0) {
            System.out.println("Thêm tài khoản mới thành công với Mã tài khoản: " + newMaTaiKhoan);
        }   
    } catch (SQLException e) {
        if(e.getErrorCode()== 1)
        {
            i= -2000;
        }
        // Print error details
        System.out.println("Error Code: " + e.getErrorCode());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Message: " + e.getMessage());
    }

    return i;
}
    public UserResponse DangNhapTaiKhoan(String username, String password) throws SQLException, ClassNotFoundException {
       // boolean isAuthenticated = false;
        UserResponse UserResponse= new UserResponse();
        try (Connection con = ConnectionUtils.getMyConnection()) {
            String query = "SELECT MaTaiKhoan FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next() ) { // If COUNT(*) > 0, user exists
                UserResponse.setStatus(true);
                UserResponse.setUserId(rs.getString("MaTaiKhoan"));
               System.out.println("Satus: " + UserResponse.isStatus());
               System.out.println("Used ID: " + UserResponse.getUserId());
                return UserResponse;
            }
            else
            {
                UserResponse.setStatus(false);
                 System.out.println("Loi");
                UserResponse.setUserId("0");
                return UserResponse;
            }
        } catch (SQLException e) {
               System.out.println("Error Code: " + e.getErrorCode());
               System.out.println("SQL State: " + e.getSQLState());
               System.out.println("Error Message: " + e.getMessage());
               System.out.println("Satus: " + UserResponse.isStatus());
               System.out.println("Used ID: " + UserResponse.getUserId());
             
        }

             UserResponse.setStatus(false);
             UserResponse.setUserId("0");
             return UserResponse;
    }
public int CapMoiTaiKhoan(String Username, String Password, String MaPQ) throws SQLException, ClassNotFoundException {
    int i = 0;

    try (Connection con = ConnectionUtils.getMyConnection()) {
        // Step 1: Find the largest MaTaiKhoan
        String getAllIdsQuery = "SELECT MaTaiKhoan FROM TaiKhoan";
        PreparedStatement getAllIdsStmt = con.prepareStatement(getAllIdsQuery);
        ResultSet rs = getAllIdsStmt.executeQuery();

        int maxId = 0; // Lưu giá trị lớn nhất
        while (rs.next()) {
            // Lấy phần số trong MaTaiKhoan (bỏ đi ký tự đầu)
            String maTaiKhoan = rs.getString("MaTaiKhoan");
            if (maTaiKhoan.startsWith("TKNV")) { // Kiểm tra nếu mã tài khoản bắt đầu bằng "TK"
                int id = Integer.parseInt(maTaiKhoan.substring(2)); // Lấy phần số
                if (id > maxId) {
                    maxId = id; // Cập nhật giá trị lớn nhất
                }
            }
        }


        // Step 2: Generate new MaTaiKhoan
        String newMaTaiKhoan = "TKNV" + String.format("%02d", maxId+1); // Format to "TKXX"

        // Step 3: Insert new account
        String query = "INSERT INTO TaiKhoan(MaTaiKhoan, TenDangNhap, MatKhau, MaPhanQuyen) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, newMaTaiKhoan); // Auto-generated MaTaiKhoan
        ps.setString(2, Username);
        ps.setString(3, Password);
        ps.setString(4, MaPQ);
        i = ps.executeUpdate();

        if (i > 0) {
            System.out.println("Thêm tài khoản mới thành công với Mã tài khoản: " + newMaTaiKhoan);
        }   
    } catch (SQLException e) {
        if(e.getErrorCode()== 1)
        {
            i= -2000;
        }
        // Print error details
        System.out.println("Error Code: " + e.getErrorCode());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Message: " + e.getMessage());
    }

    return i;
}

   
}
