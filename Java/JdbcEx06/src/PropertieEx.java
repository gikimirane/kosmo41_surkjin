import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class PropertieEx {

	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Properties pro = null;
		FileInputStream fis = null;
		
		
		try {
			
			pro = new Properties() ;
			fis = new FileInputStream("D:\\jsj\\study\\Java\\jdbc.properties");
			pro.load(fis);
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String pswd = pro.getProperty("password");
			
			con = DriverManager.getConnection(url, user, pswd);

			String sql = "select * from employee";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.print("eno: " + rs.getInt(1) + ", ");
				System.out.println("enmae: " + rs.getString(2));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){}
		}	
	}

}
