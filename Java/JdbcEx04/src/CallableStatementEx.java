import java.sql.*;

public class CallableStatementEx {

	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott","tiger");
			
			System.out.println("------호출전---------------");
			String sql = "select name, pay from salary";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.print("name: " + rs.getString(1) + ", ");
				System.out.println("pay: " + rs.getInt(2));
			}
			
			cstmt = con.prepareCall("{call adjust(?,?)}");
			sql = "select name from salary";
//			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cstmt.setString(1,  rs.getString("name"));
				cstmt.setFloat(2,  (float)0.05);
				cstmt.executeUpdate();
			}
			
			System.out.println("------호출후---------------");
//			sql = "select name, pay from salary";
//			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.print("name: " + rs.getString(1) + ", ");
				System.out.println("pay: " + rs.getInt(2));
			}
		}catch(SQLException sqle){
			System.out.println("Connection Error");
			sqle.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(cstmt != null) cstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){}
		}

	}

}
