
package Connection;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionUtils {

       /**
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
   public static Connection getMyConnection() throws SQLException,
          ClassNotFoundException {
      // Sử dụng Oracle.
      // Bạn có thể thay thế bởi Database nào đó.
      return ConnectionOracle.getOracleConnection();
  }
 
  //
  // Test Connection ...
  //
  public static void main(String[] args) throws SQLException,
          ClassNotFoundException {
 
      System.out.println("Get connection ... ");
 
      // Lấy ra đối tượng Connection kết nối vào database.
      Connection conn = ConnectionUtils.getMyConnection();
 
      System.out.println("Get connection " + conn);
 
      System.out.println("Thành công!");
  }
}
