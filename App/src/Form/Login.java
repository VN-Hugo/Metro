package Form;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import Form.FormManager;
import Form.MainForm;
import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Login extends JPanel {

    public Login() {
        init();
        cmdLogin.addActionListener(e -> {
        FormManager.getInstance().showForm(new MainForm()); 
        });
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Ghi nhớ đăng nhập");
        cmdLogin = new JButton("Đăng nhập");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tài khoản");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mật khẩu");

        JLabel lbTitle = new JLabel("Chào mừng đến Smart Metro");
        JLabel description = new JLabel("Vui lòng kết nối tài khoản của bạn !");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Tài khoản"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Mật khẩu"), "gapy 8");
        panel.add(txtPassword);
        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 10");
        cmdLogin.addActionListener(e -> {
    // Lấy thông tin đăng nhập từ các trường giao diện
    String username = txtUsername.getText();
    String password = new String(txtPassword.getPassword());

    // Hash mật khẩu
    String hashedPassword = Hashpassword(password);

    // Giả lập kiểm tra tài khoản
    boolean isSuccess = false;
    int userId = -1;

    try {
        // Điều kiện giả lập: nếu username và hashedPassword khớp với giá trị giả định
        if ("admin".equals(username) && "hashed_admin_password".equals(hashedPassword)) {
            isSuccess = true; // Đăng nhập thành công
            userId = 1; // ID người dùng giả lập
        } else {
            isSuccess = false; // Sai thông tin đăng nhập
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi kiểm tra tài khoản: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    if (isSuccess) {
        // Hiển thị thông báo thành công
        JOptionPane.showMessageDialog(this, "Đăng nhập thành công! ID: " + userId);
        FormManager.getInstance().showForm(new MainForm()); // Chuyển sang giao diện chính
    } else {
        // Hiển thị thông báo thất bại
        JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
});
        
        panel.add(CreateSignupLabel(), "gapy 10");
        add(panel);
    }
   
      private String Hashpassword(String Password)
    {
            try {
        // Get an instance of SHA-256 MessageDigest
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Perform the hash operation
        byte[] hashBytes = digest.digest(Password.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        // Print the resulting hash in hexadecimal form
       // System.out.println("SHA-256 Hash: " + hexString.toString());
        return hexString.toString();
         } 
            catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
    }
        return null;
    }
    private Component CreateSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdRegister = new JButton("<html><a href=\"#\">Đăng ký</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(e -> {
            FormManager.getInstance().showForm(new Register());
        });
        JLabel label = new JLabel("Bạn chưa có tài khoản ?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
}